package com.apps.lucas.hello.Modelo;

import java.io.Serializable;

/**
 * Created by Lucas on 29/08/2016.
 */
public class Mensagem implements Serializable {

    private int id;
    private String dthoraRecebida;
    private String dthoraEnviada;
    private String dthoraVisualizada;
    private String remetente;
    private String texto;

    public Mensagem(){

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDthoraRecebida() {
        return dthoraRecebida;
    }

    public void setDthoraRecebida(String dthoraRecebida) {
        this.dthoraRecebida = dthoraRecebida;
    }

    public String getDthoraEnviada() {
        return dthoraEnviada;
    }

    public void setDthoraEnviada(String dthoraEnviada) {
        this.dthoraEnviada = dthoraEnviada;
    }

    public String getDthoraVisualizada() {
        return dthoraVisualizada;
    }

    public void setDthoraVisualizada(String dthoraVisualizada) {
        this.dthoraVisualizada = dthoraVisualizada;
    }

    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
