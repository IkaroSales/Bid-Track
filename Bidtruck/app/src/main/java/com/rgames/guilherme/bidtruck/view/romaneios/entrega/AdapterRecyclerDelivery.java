package com.rgames.guilherme.bidtruck.view.romaneios.entrega;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rgames.guilherme.bidtruck.R;
import com.rgames.guilherme.bidtruck.model.basic.Entrega;
import com.rgames.guilherme.bidtruck.model.basic.Romaneio;
import com.rgames.guilherme.bidtruck.model.errors.ContextNullException;
import com.rgames.guilherme.bidtruck.view.romaneios.entrega.pagerdetalhes.DetalhesEntregaActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdapterRecyclerDelivery extends RecyclerView.Adapter<AdapterRecyclerDelivery.MyViewHolder> {

    private Romaneio mRomaneio;
    private List<Entrega> mListEntrega;
    private Context mContext;
    private Integer entregas;

    public AdapterRecyclerDelivery(Romaneio romaneio, Context context) throws ContextNullException {
        if (romaneio != null) {
            mListEntrega = (romaneio.getEntregaList() != null) ? romaneio.getEntregaList() : new ArrayList<Entrega>();
            mRomaneio = romaneio;
        } else
            Toast.makeText(context, context.getString(R.string.app_err_null_romaneio), Toast.LENGTH_SHORT).show();
        if (context != null) mContext = context;
        else throw new ContextNullException();
    }

    public AdapterRecyclerDelivery(Romaneio romaneio) {
        if (romaneio != null) {
            mListEntrega = (romaneio.getEntregaList() != null) ? romaneio.getEntregaList() : new ArrayList<Entrega>();
            mRomaneio = romaneio;
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_recycler_entregas, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {

            holder.codigo.setText(String.valueOf(mListEntrega.get(holder.getAdapterPosition()).getCodigo()));
            holder.razao_social.setText(mListEntrega.get(holder.getAdapterPosition()).getDestinatario().getRazao_social());
            holder.logradouro.setText(mListEntrega.get(holder.getAdapterPosition()).getDestinatario().getLogradouro() + ", ");
            holder.bairro.setText((mListEntrega.get(holder.getAdapterPosition()).getDestinatario().getBairro()));
            holder.numero.setText(mListEntrega.get(holder.getAdapterPosition()).getDestinatario().getNumero() + " - ");
            String input = mListEntrega.get(holder.getAdapterPosition()).getStatusEntrega().getDescricao();
            input = input.toUpperCase();
            if (input.equals("EM VIAGEM")) {
                holder.status_entrega.setTextColor(Color.parseColor("#FF9800"));
                holder.status_entrega.setText(input);
                holder.viewLateral.setBackgroundColor(Color.parseColor("#FF9800"));
            } else if (input.equals("FINALIZADO")) {
                holder.status_entrega.setTextColor(Color.parseColor("#D32F2F"));
                holder.status_entrega.setText(input);
                holder.viewLateral.setBackgroundColor(Color.parseColor("#D32F2F"));
            } else if (input.equals("LIBERADO")) {
                holder.status_entrega.setTextColor(Color.parseColor("#303F9F"));
                holder.status_entrega.setText(input);
                holder.viewLateral.setBackgroundColor(Color.parseColor("#303F9F"));
            }

            Log.i("teste", mListEntrega.get(holder.getAdapterPosition()).getDestinatario().getLatitude() + " - "
                    + mListEntrega.get(holder.getAdapterPosition()).getDestinatario().getLongitude());

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        /*Vou passar o index pois tive problemas com a passagem de dois Parcelables.. talvez pq o bundle
                        * sobreescreva o put e a utilização do arrayParce tbm teve problemas*/
                        Intent intent = new Intent(mContext, DetalhesEntregaActivity.class);
                        //Intent intent = new Intent(mContext, FinalizaEntrega.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Entrega.PARCEL, mListEntrega.get(holder.getAdapterPosition()));
                        bundle.putParcelable(Romaneio.PARCEL,  mRomaneio);
                        double[] inicio = new double[2];
                        double[] fim = new double[2];
                        if (mListEntrega.get(holder.getAdapterPosition()).getSeq_entrega() == 1) {
                            inicio[0] = mRomaneio.getEstabelecimento().getLatitude();
                            inicio[1] = mRomaneio.getEstabelecimento().getLongitude();
                            fim[0] = mListEntrega.get(holder.getAdapterPosition()).getDestinatario().getLatitude();
                            fim[1] = mListEntrega.get(holder.getAdapterPosition()).getDestinatario().getLongitude();
                        } else {
                            inicio[0] = mListEntrega.get(holder.getAdapterPosition() - 1).getDestinatario().getLatitude();
                            inicio[1] = mListEntrega.get(holder.getAdapterPosition() - 1).getDestinatario().getLongitude();
                            fim[0] = mListEntrega.get(holder.getAdapterPosition()).getDestinatario().getLatitude();
                            fim[1] = mListEntrega.get(holder.getAdapterPosition()).getDestinatario().getLongitude();
                        }
                        bundle.putDoubleArray("arg1", inicio);
                        bundle.putDoubleArray("arg2", fim);
                        mContext.startActivity(intent.putExtras(bundle));
                    } catch (
                            Exception e)

                    {
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }


            });


        } catch (
                Exception e)

        {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mListEntrega.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView codigo, titulo, seq_entrega, razao_social, logradouro, bairro, numero, status_entrega;
        public View viewLateral;
        public CardView cardView;
        //final TextView cod_romaneio;

        public MyViewHolder(View itemView) {
            super(itemView);
            // titulo = itemView.findViewById(R.id.titulo);
            //seq_entrega =  itemView.findViewById(R.id.txtSequencia);
            viewLateral = (View) itemView.findViewById(R.id.viewLateral);
            codigo = itemView.findViewById(R.id.txtSequencia);
            razao_social = itemView.findViewById(R.id.txtRazao);
            logradouro = itemView.findViewById(R.id.txtLogradouro);
            numero = itemView.findViewById(R.id.txtNumero);
            bairro = itemView.findViewById(R.id.txtBairro);
            status_entrega = itemView.findViewById(R.id.txtStatusEntrega);
            cardView = itemView.findViewById(R.id.cardview);


        }
    }
}
