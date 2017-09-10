package com.rgames.guilherme.bidtruck.model.basic;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Classe da entrega.
 */
public class Entrega implements Parcelable {

    public static final String PARCEL = "parcel_delivery";
    private int codigo;
    //    private int sequence_delivery;
//    private int NSF;
    private String titulo;
//    private Romaneio romaneio;
    private Destinatario destinatario;
    private StatusEntrega statusEntrega;
    private float peso;
    private Bitmap image;
    private boolean situacao;

    public Entrega() {
    }

    public Entrega(int codigo, String titulo, Destinatario destinatario, StatusEntrega statusEntrega, float peso, Bitmap image, boolean situacao) {
        this.codigo = codigo;
        this.titulo = titulo;
//        this.romaneio = romaneio;
        this.destinatario = destinatario;
        this.statusEntrega = statusEntrega;
        this.peso = peso;
        this.image = image;
        this.situacao = situacao;
    }

    protected Entrega(Parcel in) {
        codigo = in.readInt();
        titulo = in.readString();
//        romaneio = in.readParcelable(Romaneio.class.getClassLoader());
        destinatario = in.readParcelable(Destinatario.class.getClassLoader());
        statusEntrega = in.readParcelable(StatusEntrega.class.getClassLoader());
        peso = in.readFloat();
        situacao = in.readByte() > 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(codigo);
        dest.writeString(titulo);
//        dest.writeParcelable(romaneio, flags);
        dest.writeParcelable(destinatario, flags);
        dest.writeParcelable(statusEntrega, flags);
        dest.writeFloat(peso);
        dest.writeByte((byte) (situacao ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Entrega> CREATOR = new Creator<Entrega>() {
        @Override
        public Entrega createFromParcel(Parcel in) {
            return new Entrega(in);
        }

        @Override
        public Entrega[] newArray(int size) {
            return new Entrega[size];
        }
    };

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
//
//    public Romaneio getRomaneio() {
//        return romaneio;
//    }
//
//    public void setRomaneio(Romaneio romaneio) {
//        this.romaneio = romaneio;
//    }

    public Destinatario getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Destinatario destinatario) {
        this.destinatario = destinatario;
    }

    public StatusEntrega getStatusEntrega() {
        return statusEntrega;
    }

    public void setStatusEntrega(StatusEntrega statusEntrega) {
        this.statusEntrega = statusEntrega;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public boolean isSituacao() {
        return situacao;
    }

    public void setSituacao(boolean situacao) {
        this.situacao = situacao;
    }
}