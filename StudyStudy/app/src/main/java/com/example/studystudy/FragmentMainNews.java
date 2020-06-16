package com.example.studystudy;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMainNews extends Fragment {


    public FragmentMainNews() {
        // Required empty public constructor
    }

    private RecyclerView recyclerViewNotes;
    private final ArrayList<Note> notes = new ArrayList<>();//тут только заметки
    private NotesAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = getView();
        if (view != null) {
            Log.i("Null","reallno null");
            recyclerViewNotes = view.findViewById(R.id.recyclerViewNotes);
            notes.add(new Note("Privet!","test",1,2));
            adapter = new NotesAdapter(notes);
            recyclerViewNotes.setLayoutManager(new LinearLayoutManager(getContext()));//как хотим распологать элементы построчно
            recyclerViewNotes.setAdapter(adapter);
        }else {
            Log.i("Null","yes");
        }
        return inflater.inflate(R.layout.fragment_fragment_main_news, container, false);

    }

    public  void onClickAddNews (View view){
        Intent intent = new Intent(this.getActivity(), AddNewsActivity.class);
//        Intent intent1 = new Intent(this.getContext(), AddNewsActivity.class);
        startActivity(intent);

//        Intent intent= new Intent(view.getContext(), AddNewsActivity.class);
//        view.getContext().startActivity(intent);
    }

}
