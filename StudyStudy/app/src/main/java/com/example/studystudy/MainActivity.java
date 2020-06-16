package com.example.studystudy;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final int RC_SIGN_IN = 123;
    private Toolbar mToolbar;
    private TabLayout mTablayout;
    private TabItem TabItemMyNews;
    private TabItem TabItemMyChats;
    private TabItem TabItemMyAccount;
    private ViewPager ViewPagerMyPager;
    private PageController mPageController;
    private FirebaseFirestore db;
    private int onlineIdentifictor = 0;
   // public RecyclerView recyclerViewNotes;

    public String facultai;
    public ArrayList<UserTeacher> teacherList = new ArrayList<>();
    public ArrayList<UserStudent> studentList = new ArrayList<>();

    //registration
    private FirebaseAuth mAuth;


    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);



            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(this, user.getEmail()+" is logged ", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (response != null) {
                    Toast.makeText(this, "error" + response.getError(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itemSignOut){
            mAuth.signOut();
            signOut();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // recyclerViewNotes = findViewById(R.id.recyclerViewNotes);

        db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();//tk singleton

        mToolbar = findViewById(R.id.toolBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("StudyStudy");

        mTablayout = findViewById(R.id.tabLayout);
        TabItemMyNews = findViewById(R.id.tabItemNews);
        TabItemMyChats = findViewById(R.id.tabItemChats);
        TabItemMyAccount = findViewById(R.id.tabItemMyaccount);
        ViewPagerMyPager = findViewById(R.id.viewPager);
      //  Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentMainEducation);
        //fragment.set
        mPageController = new PageController(getSupportFragmentManager(), mTablayout.getTabCount());
        ViewPagerMyPager.setAdapter(mPageController);
        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ViewPagerMyPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        ViewPagerMyPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTablayout));

        if (mAuth.getCurrentUser() != null) {
            Toast.makeText(this, "this user is logged", Toast.LENGTH_SHORT).show();
        } else {
            signOut();
        }
        //FragmentManager fragmentManager = getSupportFragmentManager();




    }

    public  void onClickAddNews (View view){
        Intent intent = new Intent(this, AddNewsActivity.class);
//        Intent intent1 = new Intent(this.getContext(), AddNewsActivity.class);
        startActivity(intent);

//        Intent intent= new Intent(view.getContext(), AddNewsActivity.class);
//        view.getContext().startActivity(intent);


    }

    private void signOut(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}
