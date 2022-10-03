package com.apps.drpersonaltrainer.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.drpersonaltrainer.model.Aluno;
import com.apps.drpersonaltrainer.ui.adapter.BuscarAlunoAdapter;

import java.util.ArrayList;
import java.util.List;

/*
 * A simple {@link Fragment} subclass.
 * Use the {@link BuscarAlunoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuscarAlunoFragment extends Fragment {

    private RecyclerView recyclerView;
    private BuscarAlunoAdapter alunoAdapter;
    private List<Aluno> alunos = new ArrayList<>();

    /*
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BuscarAlunoFragment() {
        // Required empty public constructor
    }

     */

    /*
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BuscarAlunoFragment.
     */

    /*
    // TODO: Rename and change types and number of parameters
    public static BuscarAlunoFragment newInstance(String param1, String param2) {
        BuscarAlunoFragment fragment = new BuscarAlunoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_buscar_alunos_adapter);

/*
        //Configurar Adapter
        alunoAdapter = new BuscarAlunoAdapter(alunos);
        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(alunoAdapter);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

         */
    }

    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buscar_aluno, container, false);
    }

     */
}