package com.apps.lucas.hello.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.apps.lucas.hello.Activities.PerfilActivity;
import com.apps.lucas.hello.R;
import com.google.android.gms.drive.internal.CheckResourceIdsExistRequest;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Lucas on 08/12/2016.
 */

public class EscolherAvatarBottomSheetFragment extends BottomSheetDialogFragment {


    private String parentActivity;
    private RecyclerView recyclerView;


    public EscolherAvatarBottomSheetFragment() {

    }


    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        Bundle args = getArguments();
        if (args != null) {
            parentActivity = args.getString("parent");
        }

        //Inflando a View e colocando no dialog
        View contentView = View.inflate(getContext(), R.layout.bottom_sheet_dialog_change_avatar, null);

        recyclerView = (RecyclerView) contentView.findViewById(R.id.recycler_view_avatar);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 6);
        recyclerView.setLayoutManager(layoutManager);

        //listaFake
        ArrayList<String> avatars = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            String aux = "nomeavatar " + i;
            avatars.add(aux);
        }
        GridRecyclerViewAvatarAdapter adapter = new GridRecyclerViewAvatarAdapter(getActivity(), avatars, parentActivity, this);
        recyclerView.setAdapter(adapter);

        dialog.setContentView(contentView);

        // CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View)contentView.getParent()).getLayoutParams();
        //CoordinatorLayout.Behavior behavior = params.getBehavior();
    }


    //Adapter
    public class GridRecyclerViewAvatarAdapter extends RecyclerView.Adapter<GridRecyclerViewAvatarAdapter.MyViewHolder> {


        private Activity activity;
        private ArrayList<String> avatars;
        private String activityName;

        //qnd chamado por dialogs
        private DialogFragment dialogFragment;
        private boolean dialog = false;

        public GridRecyclerViewAvatarAdapter(Activity activity, ArrayList<String> avatars, String activityName) {
            this.activity = activity;
            this.avatars = avatars;
            this.activityName = activityName;
        }

        public GridRecyclerViewAvatarAdapter(Activity activity, ArrayList<String> avatars, String activityName, DialogFragment dialogFragment) {
            this.activity = activity;
            this.avatars = avatars;
            this.activityName = activityName;
            this.dialogFragment = dialogFragment;
            dialog = true;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = activity.getLayoutInflater().inflate(R.layout.item_list_avatar_grid, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            //Setar imagens diferentes para cada item da lista
            holder.avatar.setImageResource(R.drawable.avatar1);


        }

        @Override
        public int getItemCount() {
            return avatars.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            CircleImageView avatar;

            public MyViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);

                avatar = (CircleImageView) itemView.findViewById(R.id.gridAvatar);
            }

            @Override
            public void onClick(View view) {

                //enviar o avatar clicado para a activity
                if (activityName != null) {
                    if (activityName.equals("PerfilActivity")) {
                        ((PerfilActivity) activity).setNewAvatar(avatars.get(getAdapterPosition()));

                        if (dialog) {
                            dialogFragment.dismiss();
                        }

                    }
                }

            }
        }

    }


}
