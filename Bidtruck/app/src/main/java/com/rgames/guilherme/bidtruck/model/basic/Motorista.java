package com.rgames.guilherme.bidtruck.model.basic;

import android.os.Parcel;

/**
 * Created by Guilherme on 08/09/2017.
 */

public class Motorista extends Base {

    public static final String PARCEL_MOTORISTA = "parcel_motorista";
    private Empresa empresa;
    private String nome;
    private String cpf;
    private int pontuacao;
    private String email;
    private String senha;
    private String tipo_carteira;
    private String validade_carteira;
   // private Double nota;

    public Motorista(int codigo, String nome) {
        setCodigo(codigo);
        setNome(nome);
       // setNota(nota);
    }
    public Motorista(){}



    protected Motorista(Parcel in) {
        super(in);
        //  empresa = in.readParcelable(Empresa.class.getClassLoader());
        nome = in.readString();
        cpf = in.readString();
        pontuacao = in.readInt();
        email = in.readString();
        senha = in.readString();
        tipo_carteira = in.readString();
        validade_carteira = in.readString();
     //   nota = in.readDouble();
    }

    public static final Creator<Motorista> CREATOR = new Creator<Motorista>() {
        @Override
        public Motorista createFromParcel(Parcel in) {
            return new Motorista(in);
        }

        @Override
        public Motorista[] newArray(int size) {
            return new Motorista[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeParcelable(empresa, i);
        parcel.writeString(nome);
        parcel.writeString(cpf);
        parcel.writeInt(pontuacao);
        parcel.writeString(email);
        parcel.writeString(senha);
        parcel.writeString(tipo_carteira);
        parcel.writeString(validade_carteira);
       // parcel.writeDouble(nota);

    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getTipo_carteira() {
        return tipo_carteira;
    }

    public void setTipo_carteira(String tipo_carteira) {
        this.tipo_carteira = tipo_carteira;
    }

    public String getValidade_carteira() {
        return validade_carteira;
    }

    public void setValidade_carteira(String validade_carteira) {
        this.validade_carteira = validade_carteira;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

   /* public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }*/
}
