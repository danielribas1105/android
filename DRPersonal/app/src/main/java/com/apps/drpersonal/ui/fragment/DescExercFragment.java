package com.apps.drpersonal.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.drpersonal.R;
import com.apps.drpersonal.config.ConfigFirebase;
import com.apps.drpersonal.helper.Base64Custom;
import com.apps.drpersonal.model.Exercise;
import com.apps.drpersonal.model.ExerciseInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class DescExercFragment extends Fragment {

    ImageView imgExerc;
    TextView descExerc;
    private FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();
    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
    private DatabaseReference infoExerc;
    private String idAluno;
    private ValueEventListener valueEventListenerInfoExerc;
    private List<ExerciseInfo> exerciseInfos = new ArrayList<>();

    public DescExercFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_desc_exerc, container, false);

        imgExerc = view.findViewById(R.id.imgExerc);
        descExerc = view.findViewById(R.id.descExerc);

        loadInfoExercise();

        return view;
    }

    public void loadInfoExercise(){
        idAluno = Base64Custom.codeToBase64(auth.getCurrentUser().getEmail());
        infoExerc = reference.child("exerciciosInfo").child("idRefExerc1");

        valueEventListenerInfoExerc = infoExerc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                exerciseInfos.clear();
                for(DataSnapshot exercInfo: snapshot.getChildren()){
                    Log.i("info",exercInfo.toString());
                    //ExerciseInfo exerciseInfo = exercInfo.getValue(ExerciseInfo.class);
                   // exerciseInfos.add(exerciseInfo);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}