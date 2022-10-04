package com.apps.drpersonal.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.apps.drpersonal.R;
import com.apps.drpersonal.model.Exercise;
import com.apps.drpersonal.ui.adapter.ExerciciosAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExerciciosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciciosFragment extends Fragment {

    private RecyclerView recyclerExerc;
    private ExerciciosAdapter adapterExerc;
    private List<Exercise> exercises = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExerciciosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment exerciciosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExerciciosFragment newInstance(String param1, String param2) {
        ExerciciosFragment fragment = new ExerciciosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercicios, container, false);
        recyclerExerc = view.findViewById(R.id.recyclerExercicios);
        recyclerExerc.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerExerc.setHasFixedSize(true);
        recyclerExerc.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        loadExercises();
        adapterExerc = new ExerciciosAdapter(exercises);
        recyclerExerc.setAdapter(adapterExerc);
        return view;
    }

    private void loadExercises() {
        exercises.add(new Exercise(R.drawable.logo_circular,"Supino Reto"));
        exercises.add(new Exercise(R.drawable.logo_circular,"Supino Inclinado"));
        exercises.add(new Exercise(R.drawable.logo_circular,"Voador"));
        exercises.add(new Exercise(R.drawable.logo_circular,"Desenvolvimento"));
        exercises.add(new Exercise(R.drawable.logo_circular,"Elevação Lataral"));
    }
}