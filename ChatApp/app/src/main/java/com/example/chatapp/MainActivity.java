package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123 ;
    private static final int RC_GET_IMAGE = 1234 ;
    private FirebaseFirestore db; //получаем ссылку на базу данных

    private RecyclerView recyclerViewMessages;
    private MessagesAdapter adapter;

    private EditText editTextmessage;
    private ImageView imageViewSendmessage;
    private ImageView imageViewAddImage;

    private FirebaseAuth mAuth;

    private SharedPreferences preferences;

    private FirebaseStorage storage ;
    private StorageReference reference;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() ==  R.id.itemSignOut){
            mAuth.signOut();//выходим
            signOut();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        reference = storage.getReference();//получили доступ к хранилищу гугла,получили доступ к главной папке, в которой все лежит
        StorageReference referenceToImages = reference.child("images");//мы создали папку для хранения изображений
        recyclerViewMessages = findViewById(R.id.recyclerViewMessages);
        editTextmessage = findViewById(R.id.editTextMessage);
        imageViewSendmessage = findViewById(R.id.imageViewSendMessage);
        imageViewAddImage = findViewById(R.id.imageViewAddImage);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        adapter = new MessagesAdapter(this);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMessages.setAdapter(adapter);
        editTextmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewMessages.smoothScrollToPosition(adapter.getItemCount()-1);
            }
        });
        imageViewAddImage.setOnClickListener(new View.OnClickListener() {
            //это чтобы открыть менюшку с фотографиями
            @Override
            public void onClick(View view) {
                //неявный интент на получение контента
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//указываем что хотим получить контент
                intent.setType("image/jpeg");//указываеи какой контент именно нужен
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(intent, RC_GET_IMAGE);//число тут это уникальный код
            }
        });
        imageViewSendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(null);
            }
        });

        if (mAuth.getCurrentUser() != null ){
            Toast.makeText(this, "Logged", Toast.LENGTH_SHORT).show();
            preferences.edit().putString("author", mAuth.getCurrentUser().getEmail()).apply();
        } else{
            Toast.makeText(this, "Not Register", Toast.LENGTH_SHORT).show();
            signOut();
        }
    }

    private void sendMessage(String urlToImage){
        final String textOfMessage = editTextmessage.getText().toString().trim();
        Message message = null;
        String author = preferences.getString("author", "Anonim");
        if (!textOfMessage.isEmpty()){
            message = new Message(author, textOfMessage, System.currentTimeMillis(), null);
            recyclerViewMessages.scrollToPosition(adapter.getItemCount()-1);
        }else if (urlToImage != null && !urlToImage.isEmpty()){
            message = new Message(author, null, System.currentTimeMillis(), urlToImage);
            recyclerViewMessages.scrollToPosition(adapter.getItemCount()-1);
        }
        db.collection("messages").add(message).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                editTextmessage.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Сообщение не отправлено", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.
     */
    @Override
    protected void onResume() {
        super.onResume();
        db.collection("messages").orderBy("date").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (queryDocumentSnapshots != null) {
                    List<Message> messages = queryDocumentSnapshots.toObjects(Message.class);
                    adapter.setMessages(messages);
                    //recyclerViewMessages.scrollToPosition(adapter.getItemCount()-1);
                    recyclerViewMessages.smoothScrollToPosition(adapter.getItemCount()-1);
                }
                //toObjects передаем туда класс а возвращаем лист
            }
        });
    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */

    //вот тут requestCode это 123 !!!!!
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //что мы получаем результат из активности с кодом имаге и результат выполнения этой активности положительный
        if (requestCode == RC_GET_IMAGE && resultCode == RESULT_OK){
            if (data != null) {
                Uri uri = data.getData();//адрес этого файла на телефоне
                if (uri != null) {
                   // Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show();
                    final StorageReference referenceToImage = reference.child("images/"+ uri.getLastPathSegment());//ссылка для загрузки файла
                    referenceToImage.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            // Continue with the task to get the download URL
                            return referenceToImage.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();//получаем загрузочный путь
                                if (downloadUri != null) {
                                    Log.i("UrlTest", downloadUri.toString());
                                    sendMessage(downloadUri.toString());
                                }
                            } else {
                                // Handle failures
                                // ...
                            }
                        }
                    });

                }
            }
        }


        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(this, "user logged" + user.getEmail(), Toast.LENGTH_SHORT).show();
                    preferences.edit().putString(("author"),user.getEmail()).apply();
                    // ...
                }
            } else {
                if (response != null) {
                    Toast.makeText(this, "ERROR:" + response.getError(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
        }     //
    }

    private void signOut() {
        AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    // Choose authentication providers
                    List<AuthUI.IdpConfig> providers = Arrays.asList(
                            new AuthUI.IdpConfig.EmailBuilder().build(),
                            new AuthUI.IdpConfig.GoogleBuilder().build());

                    // Create and launch sign-in intent
                    //то есть запускаем активити только для одного клика
                    //внизу код по которому будем проверять результат работы метода
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(providers)
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        });


    }
}
