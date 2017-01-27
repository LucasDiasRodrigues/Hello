package com.apps.lucas.hello.Util;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by Lucas on 25/08/2016.
 */
public class Preferencias {

    private Activity activity;

    public Preferencias(Activity activity){
        this.activity = activity;
    }


    public void saveFacebookUserId (String id){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("UserId", id);
        editor.apply();
    }
    public void saveFacebookAccessToken(String token){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("token", token);
        editor.apply();
    }

    public String getToken() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        return sp.getString("token", null);
    }

    public void clearToken() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }


    public void saveConfigPref(String chave, int valor){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt(chave, valor);
        editor.apply();

        Log.i("save prefs", chave+ "  "+String.valueOf(valor));
    }

    public void saveConfigPref(String chave, Boolean valor){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putBoolean(chave, valor);
        editor.apply();
    }

    public Bundle getConfigs(){
        Bundle bundle = new Bundle();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);

        bundle.putBoolean("homens", sp.getBoolean("homens", false));
        bundle.putBoolean("mulheres", sp.getBoolean("mulheres", false));
        bundle.putBoolean("combinacao", sp.getBoolean("combinacao", true));
        bundle.putBoolean("mensagem", sp.getBoolean("mensagem", true));

        bundle.putInt("min_idade", sp.getInt("min_idade", 25));
        bundle.putInt("max_idade", sp.getInt("max_idade", 45));
        bundle.putInt("area", sp.getInt("area", 30));

        //
        Log.i("idades get", String.valueOf(sp.getInt("min_idade", 0))+ "  "+String.valueOf(sp.getInt("max_idade", 0)));

        return bundle;
    }

    public boolean savePerfil(String nome, String descSimples, String descCompleta, String avatar){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("nomePerfil", nome);
        editor.putString("descSimples", descSimples);
        editor.putString("descCompleta", descCompleta);
        editor.putString("avatar", avatar);

        editor.apply();

        return true;
    }

    public Bundle getPerfil(){
        Bundle bundle = new Bundle();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);

        bundle.putString("nomePerfil", sp.getString("nomePerfil", ""));
        bundle.putString("descSimples", sp.getString("descSimples", ""));
        bundle.putString("descCompleta", sp.getString("descCompleta", ""));
        bundle.putString("avatar", sp.getString("avatar", ""));


        Log.i("getPerfil", sp.getString("nomePerfil", "semNome")+ "  "+sp.getString("avatar", "semAvatar"));

        return  bundle;
    }



}
