package com.apps.lucas.hello.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.lucas.hello.Adapter.ConversasRVAdapter;
import com.apps.lucas.hello.R;
import com.apps.lucas.hello.Util.FirebaseDB;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Lucas on 29/08/2016.
 */
public class ConversasFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private TextView txtSemConversas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_conversas, container, false);


        mRecyclerView = (RecyclerView) fragment.findViewById(R.id.recyclerViewConversas);
        txtSemConversas = (TextView) fragment.findViewById(R.id.txtSemConversas);

        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
     //   FirebaseDB fireDB = new FirebaseDB();
       // FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
       // fireDB.getConversas(fireUser.getUid());



        //Bundles Ficticios

        String name = "Lucas Dias";
        Date data = new Date();
        int qtdMsg = 2;
        String profPic = "";


        Bundle bundle1 = new Bundle();
        bundle1.putInt("qtdMsg", qtdMsg);
        bundle1.putString("profPic", profPic);
        bundle1.putString("nome", name);
        bundle1.putSerializable("data", data);

        Bundle bundle2 = new Bundle();
        bundle2.putInt("qtdMsg", qtdMsg);
        bundle2.putString("profPic", profPic);
        bundle2.putString("nome", name);
        bundle2.putSerializable("data", data);

        Bundle bundle3 = new Bundle();
        bundle3.putInt("qtdMsg", qtdMsg);
        bundle3.putString("profPic", profPic);
        bundle3.putString("nome", name);
        bundle3.putSerializable("data", data);
        Bundle bundle4 = new Bundle();

        bundle4.putInt("qtdMsg", qtdMsg);
        bundle4.putString("profPic", profPic);
        bundle4.putString("nome", name);
        bundle4.putSerializable("data", data);

        ArrayList<Bundle> list = new ArrayList<Bundle>();
        list.add(bundle1);
        list.add(bundle2);
        list.add(bundle3);
        list.add(bundle4);

        //fim


        //Texto s conversas

        if(!list.isEmpty()) {
            txtSemConversas.setVisibility(View.GONE);
        }

        //Lista

        ConversasRVAdapter adapter = new ConversasRVAdapter(getActivity(), list);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);

    }
}
