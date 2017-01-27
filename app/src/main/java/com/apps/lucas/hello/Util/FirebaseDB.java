package com.apps.lucas.hello.Util;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.apps.lucas.hello.Activities.LoginActivity;
import com.apps.lucas.hello.Modelo.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lucas on 29/08/2016.
 */
public class FirebaseDB {

    String TAG = "FirebaseDB";
    private DatabaseReference myRef;

    //para o metido isCadastrado e cadastrarUser
    private boolean cadastrado;


    public FirebaseDB(){
        myRef = FirebaseDatabase.getInstance().getReference();
    }


    public void isCadastrado(String userKey, final LoginActivity activity){

        myRef.child("user").orderByKey().equalTo(userKey).limitToFirst(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.i(TAG,dataSnapshot.toString());

                if(dataSnapshot.exists()){
                    Log.i(TAG,"usuario ja existe");

                    activity.onLogin();


                    cadastrado = true;
                } else {

                    activity.cadastrarNovoUsuarioFireDB();
                    cadastrado = false;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.i("erro no firebase",databaseError.toString());

            }
        });


    }

    public void cadastrarNewUser(String userKey, Usuario usuario, final LoginActivity activity){
        cadastrado = false;

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(userKey,usuario);

        myRef.child("user").updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                cadastrado = true;
                activity.onLogin();
            }
        });



    }

    public void getUser(String userKey){


        myRef.child("user").child(userKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG+"getUser",dataSnapshot.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void getConversas(String userId) {
        List<Bundle> bundles = new ArrayList<>();

        Log.i("userFire", userId);

        final List<String> users = new ArrayList<>();

        myRef.child("user").child(userId).child("liked").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("RetornoValueConversas", dataSnapshot.toString());
                Log.i("RetornoValueConversas2", String.valueOf(dataSnapshot.getChildrenCount()));
                Log.i("RetornoValueConversas2", String.valueOf(dataSnapshot.getValue().toString()));
                try {
                    JSONArray ja = new JSONArray(dataSnapshot.getValue().toString());
                    for(int i=0; i < ja.length(); i++){
                        String item = ja.getString(i);
                        if(item.contains("true")){
                            String aux = item.replace("=true","");
                            users.add(aux);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                myRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("RetornoValueerro", databaseError.toString());
            }
        });

        Log.i("usersss", users.toString());
    }

}
