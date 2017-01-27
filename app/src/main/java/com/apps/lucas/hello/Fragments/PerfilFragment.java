package com.apps.lucas.hello.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.apps.lucas.hello.Activities.MainActivity;
import com.apps.lucas.hello.R;
import com.apps.lucas.hello.Util.FirebaseDB;
import com.apps.lucas.hello.Util.Preferencias;
import com.facebook.Profile;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Lucas on 05/12/2016.
 */

public class PerfilFragment extends Fragment {


    private String userId;

    private FirebaseDB firebaseDB;

    private Preferencias preferencias;


    private TextView txtNome;
    private TextView txtDescSimples;
    private TextView txtDescCompleta;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferencias = new Preferencias(getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_perfil, container, false);


        if(getArguments() != null) {
            Bundle bundle = getArguments();
            userId = bundle.getString("userId");
            firebaseDB = new FirebaseDB();
            firebaseDB.getUser(FirebaseAuth.getInstance().getCurrentUser().getUid());
        }


        txtNome = (TextView) fragment.findViewById(R.id.txtNomeUsuario);
        txtDescSimples = (TextView) fragment.findViewById(R.id.txtDescSimples);
        txtDescCompleta = (TextView) fragment.findViewById(R.id.txtDescCompleta);


        return fragment;
    }


    @Override
    public void onResume() {
        super.onResume();
        preencheCampos();
    }

    public void preencheCampos(){

        Bundle bundle = preferencias.getPerfil();
        txtNome.setText(bundle.getString("nomePerfil"));

        Profile profile = ((MainActivity)getActivity()).getUserProfile();
        if(txtNome.getText().toString().equals("")){
            txtNome.setText(profile.getName());
        }

        if(!bundle.getString("descSimples").equals("")){
            txtDescSimples.setText(bundle.getString("descSimples"));
        }

        if(!bundle.getString("descCompleta").equals("")){
            txtDescCompleta.setText(bundle.getString("descCompleta"));
        }

    }

}
