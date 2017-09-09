package com.rgames.guilherme.bidtruck.model.basic;

import android.icu.util.Calendar;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class StatusEntrega implements Parcelable {

    private int codigo;
    private Ocorrencia ocorrencia;
    private List<Entrega> entregaList;
    private Calendar date;

    public StatusEntrega() {
    }

    public StatusEntrega(int codigo, List<Entrega> entregaList, Ocorrencia ocorrencia, Calendar date) {
        this.codigo = codigo;
        this.ocorrencia = ocorrencia;
        this.entregaList = entregaList;
        this.date = date;
    }

    protected StatusEntrega(Parcel in) {
        codigo = in.readInt();
        ocorrencia = in.readParcelable(Ocorrencia.class.getClassLoader());
        //ta bugando, reolver dps error: Unmarshalling unknown type code 115 at offset 820
//        setEntregaList(new ArrayList<Entrega>());
//        in.readList(getEntregaList(), Entrega.class.getClassLoader());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            date = (Calendar) in.readSerializable();
        }
    }

    public static final Creator<StatusEntrega> CREATOR = new Creator<StatusEntrega>() {
        @Override
        public StatusEntrega createFromParcel(Parcel in) {
            return new StatusEntrega(in);
        }

        @Override
        public StatusEntrega[] newArray(int size) {
            return new StatusEntrega[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(codigo);
        parcel.writeParcelable(ocorrencia, i);
//        parcel.writeList(entregaList);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //Requer a api na versao 24
            //usar o getMilles tbm requer a 24
            parcel.writeSerializable(date);
        }
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Ocorrencia getOcorrencia() {
        return ocorrencia;
    }

    public void setOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencia = ocorrencia;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public List<Entrega> getEntregaList() {
        return entregaList;
    }

    public void setEntregaList(List<Entrega> entregaList) {
        this.entregaList = entregaList;
    }
}
