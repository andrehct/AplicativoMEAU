package com.example.meau;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCadAdocAnim extends Fragment{


    public FragmentCadAdocAnim() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cad_adoc_anim, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button proximo;
        proximo = (Button)view.findViewById(R.id.id_btnColocarAdoc);

        proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CadastroAnimalFinalizado.class);
                startActivity(intent);
            }
        });

    }

}
