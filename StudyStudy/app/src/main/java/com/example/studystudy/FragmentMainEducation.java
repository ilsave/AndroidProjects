package com.example.studystudy;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Objects;


public class FragmentMainEducation extends Fragment {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;


    private ListView listViewSeansons;
    private ImageView imageViewUserPhoto;
    private TextView  textViewStudentName;
    private TextView  textViewFaculityName;
    private TextView  textViewStudentGroup;

    public FragmentMainEducation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_main_education, container, false);
        listViewSeansons = view.findViewById(R.id.listViewSeasons);
        imageViewUserPhoto = view.findViewById(R.id.imageViewUserPhoto);
        textViewStudentName = view.findViewById(R.id.textViewStudentName);
        textViewFaculityName = view.findViewById(R.id.textViewFaculityName);
        textViewStudentGroup = view.findViewById(R.id.textViewStudentGroup);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



        if (user != null) {
            textViewStudentName.setText(user.getEmail());
            //textViewFaculityName.setText()
        } else {
            //Toast.makeText(FragmentMainEducation, "fdghgfd", Toast.LENGTH_LONG).show();
            Log.i("kapec", "kapec");
        }

        //listViewSeansons.setOnItemClickListener()

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("teachers").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (queryDocumentSnapshots != null) {
                    List<UserTeacher> teachers = queryDocumentSnapshots.toObjects(UserTeacher.class);
                    if (teachers.size() == 0){
                        Log.i("teachers","empty");
                    }else{
                        Log.i("teachers",teachers.size()+"");
                    }
                    //if (mAuth.getCurrentUser().getEmail() == null){
                    //  Log.i("Email", "null");
                    //}
                    for( UserTeacher a : teachers) {
                        if (user.getEmail() != null && a.getEmail() != null) {
                            if (a.getEmail().equals(mAuth.getCurrentUser().getEmail())) {
                                textViewFaculityName.setText(a.getFacultatyName());
                                textViewStudentGroup.setText(a.getKafedraName()+" , "+a.getJobType());
                                Log.i("if?", "da");
                            } else {
                                Log.i("if", "net!");
                            }
                        }
                    }
                }
            }
        });
    }
}
