package com.apps.lucas.hello.Modelo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Lucas on 26/08/2016.
 */
public class Usuario implements Serializable {

    private String facebookProfileId;
    private String firebaseAuthId;
    private String gcmId;

    private String nome;
    private String descSimples;
    private String descCompleta;
    private String avatar;
    private int idade;
    private Date DataNasc;
    private String genero;
    private String localizacao;

    private String generoBuscado;
    private String idadeMimMax;
    private int distanciaMax;

    private Date dataCadastro;

    // talvez tenha


    // private String foto;

    //construtor vazio eh requisito para o cadastro do objeto no firebase
    public Usuario(){
    }


    public String getFacebookProfileId() {
        return facebookProfileId;
    }

    public void setFacebookProfileId(String facebookProfileId) {
        this.facebookProfileId = facebookProfileId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescSimples() {
        return descSimples;
    }

    public void setDescSimples(String descSimples) {
        this.descSimples = descSimples;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public Date getDataNasc() {
        return DataNasc;
    }

    public void setDataNasc(Date dataNasc) {
        DataNasc = dataNasc;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getGeneroBuscado() {
        return generoBuscado;
    }

    public void setGeneroBuscado(String generoBuscado) {
        this.generoBuscado = generoBuscado;
    }

    public String getIdadeMimMax() {
        return idadeMimMax;
    }

    public void setIdadeMimMax(String idadeMimMax) {
        this.idadeMimMax = idadeMimMax;
    }

    public int getDistanciaMax() {
        return distanciaMax;
    }

    public void setDistanciaMax(int distanciaMax) {
        this.distanciaMax = distanciaMax;
    }

    public String getGcmId() {
        return gcmId;
    }

    public void setGcmId(String gcmId) {
        this.gcmId = gcmId;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getFirebaseAuthId() {
        return firebaseAuthId;
    }

    public void setFirebaseAuthId(String firebaseAuthId) {
        this.firebaseAuthId = firebaseAuthId;
    }

    public String getDescCompleta() {
        return descCompleta;
    }

    public void setDescCompleta(String descCompleta) {
        this.descCompleta = descCompleta;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
