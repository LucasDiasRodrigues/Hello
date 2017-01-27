package com.apps.lucas.hello.Activities;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.lucas.hello.Fragments.EscolherAvatarBottomSheetFragment;
import com.apps.lucas.hello.R;
import com.apps.lucas.hello.Util.Preferencias;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilActivity extends AppCompatActivity {

    private CircleImageView circleViewAvatar;
    private TextInputEditText txtNome;
    private TextInputEditText txtDescricaoSimples;
    private TextInputEditText txtDescricaoCompleta;

    private String avatar;

    private Preferencias preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        preferencias = new Preferencias(this);


        circleViewAvatar = (CircleImageView) findViewById(R.id.avatar);
        txtNome = (TextInputEditText) findViewById(R.id.txtNome);
        txtDescricaoSimples = (TextInputEditText) findViewById(R.id.txtDescSimples);
        txtDescricaoCompleta = (TextInputEditText) findViewById(R.id.txtDescCompleta);

        circleViewAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("parent", "PerfilActivity");
                BottomSheetDialogFragment bottomSheetDialogFragment = new EscolherAvatarBottomSheetFragment();
                bottomSheetDialogFragment.setArguments(args);
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        });

        preencheCampos();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvaInformacoes();
            }
        });
    }


    public void preencheCampos() {
        Bundle bundle = preferencias.getPerfil();
        txtNome.setText(bundle.getString("nomePerfil"));
        txtDescricaoSimples.setText(bundle.getString("descSimples"));
        txtDescricaoCompleta.setText(bundle.getString("descCompleta"));

        if (!bundle.getString("avatar").equals(""))
            avatar = bundle.getString("avatar");


    }

    public void salvaInformacoes() {

        //Por enquanto salva nas prefs
        boolean confirm = preferencias.savePerfil(txtNome.getText().toString(), txtDescricaoSimples.getText().toString(),
                txtDescricaoCompleta.getText().toString(), avatar);

        if (confirm) {
            Toast.makeText(this, "Perfil atualizado:", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Ops...", Toast.LENGTH_SHORT).show();
        }

    }


    //Utilizado pelo DialogFragment para a escolha de um novo avatar
    public void setNewAvatar(String avatar) {
        this.avatar = avatar;
        Toast.makeText(this, "recebido:" + avatar, Toast.LENGTH_SHORT).show();
    }


}
