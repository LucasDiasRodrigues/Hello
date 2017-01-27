package com.apps.lucas.hello.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.apps.lucas.hello.R;
import com.squareup.picasso.Picasso;

import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private EditText txtMensagem;
    private RecyclerView recyclerViewMensagens;
    private FloatingActionButton fabEnviar;
    private LinearLayoutManager mLayoutManager;
    private String myId;
    private String idCrush;
    private String avatarCrush;
    private String nomeCrush;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        //Receber parametros
        Intent intent = getIntent();
        nomeCrush = intent.getStringExtra("nomeCrush");
        avatarCrush = intent.getStringExtra("avatarCrush");
        idCrush = intent.getStringExtra("idCrush");



        //Configurando a toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // LayoutPersonalizado
       // LayoutInflater mInflater = LayoutInflater.from(this);
        //View mCustomView = mInflater.inflate(R.layout.toolbar_chat_layout, null);
        //CircleImageView imagemDestinatario = (CircleImageView) mCustomView.findViewById(R.id.circularImageView);
       // if (!profPicDestinatario.equals("")) {
        //    Picasso.with(this).load(this.getResources().getString(R.string.imageservermini) + profPicDestinatario).into(imagemDestinatario);
        //}
        //TextView txtNomeDestinatario = (TextView) mCustomView.findViewById(R.id.title_text);
        //txtNomeDestinatario.setText(nomeCrush);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setTitle(nomeCrush);



        txtMensagem = (EditText) findViewById(R.id.mensagem);
        recyclerViewMensagens = (RecyclerView) findViewById(R.id.recycler_view_mensagens);

        mLayoutManager = new LinearLayoutManager(this);
        recyclerViewMensagens.setLayoutManager(mLayoutManager);
        recyclerViewMensagens.setItemAnimator(new DefaultItemAnimator());
        recyclerViewMensagens.setHasFixedSize(true);


        fabEnviar = (FloatingActionButton) findViewById(R.id.fabEnviar);
        fabEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!txtMensagem.getText().toString().equals("")) {

                    //Enviar aqui

                    txtMensagem.setText("");

                }
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
              onBackPressed();
        }
        return (super.onOptionsItemSelected(menuItem));
    }

}
