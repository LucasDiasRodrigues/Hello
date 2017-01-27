package com.apps.lucas.hello.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apps.lucas.hello.Activities.ChatActivity;
import com.apps.lucas.hello.Modelo.Mensagem;
import com.apps.lucas.hello.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Lucas on 29/08/2016.
 */
public class ConversasRVAdapter extends RecyclerView.Adapter<ConversasRVAdapter.MyViewHolder> {
    private static Activity activity;
    private static List<Bundle> bundles;
    private LayoutInflater layoutInflater;


    public ConversasRVAdapter(Activity activity, List<Bundle> bundles) {
        this.activity = activity;
        this.bundles = bundles;
        layoutInflater = activity.getLayoutInflater();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_list_conversas, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if ((!bundles.get(position).getString("profPic").equals("")) && (bundles.get(position).getString("profPic") != null)) {
            Picasso.with(activity).load("URL_DA_IMAGEM").into(holder.imagemPerfil);
        }
        holder.txtNome.setText(bundles.get(position).getString("nome", ""));
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        holder.txtData.setText(dateFormat.format(bundles.get(position).getSerializable("data")));

        if(bundles.get(position).getInt("qtdMsg") > 0){
            holder.icMsgRecebida.setVisibility(View.VISIBLE);
            holder.icMsgRecebida.setText(Integer.toString(bundles.get(position).getInt("qtdMsg")));
        } else {
            holder.icMsgRecebida.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return bundles.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView imagemPerfil;
        private TextView txtNome;
        private TextView txtData;
        private TextView icMsgRecebida;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imagemPerfil = (CircleImageView) itemView.findViewById(R.id.circleView);
            txtNome = (TextView) itemView.findViewById(R.id.txtNome);
            txtData = (TextView) itemView.findViewById(R.id.txtData);
            icMsgRecebida = (TextView) itemView.findViewById(R.id.icMsgRecebida);
        }

        @Override
        public void onClick(View v) {

            Log.i("conversas","click na lista");

            Intent intent = new Intent(activity, ChatActivity.class);
            intent.putExtra("nomeCrush", bundles.get(getAdapterPosition()).getString("nome",""));
            intent.putExtra("avatarCrush", bundles.get(getAdapterPosition()).getString("profPic",""));
            intent.putExtra("idCrush", bundles.get(getAdapterPosition()).getString("id",""));
            activity.startActivity(intent);

        }
    }
}
