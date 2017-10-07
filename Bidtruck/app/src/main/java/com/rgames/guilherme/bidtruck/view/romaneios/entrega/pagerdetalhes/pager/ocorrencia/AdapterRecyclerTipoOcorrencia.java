package com.rgames.guilherme.bidtruck.view.romaneios.entrega.pagerdetalhes.pager.ocorrencia;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rgames.guilherme.bidtruck.R;
import com.rgames.guilherme.bidtruck.model.basic.TipoOcorrencia;

import java.util.List;

/**
 * Created by Guilherme on 07/10/2017.
 */

class AdapterRecyclerTipoOcorrencia extends RecyclerView.Adapter<AdapterRecyclerTipoOcorrencia.MyViewHolder> {

    private List<TipoOcorrencia> mList;
    private static int pos;
    private static int idTipo;

    public AdapterRecyclerTipoOcorrencia(List<TipoOcorrencia> tipoOcorrencia) {
        mList = tipoOcorrencia;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_recyler_tipoocorrencia, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.txt_tipo.setText(mList.get(position).getDescription());
        if (pos == position) {
            holder.lay.setBackgroundColor(Color.argb(255, 153, 153, 153));
            idTipo = mList.get(position).getCodigo();
        }else
            holder.lay.setBackgroundColor(Color.WHITE);
        holder.lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pos = position;
                notifyDataSetChanged();
            }
        });
    }

    public int getCodigoSelecionado(){
        return idTipo;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_tipo;
        LinearLayout lay;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_tipo = itemView.findViewById(R.id.txt_tipo);
            lay = itemView.findViewById(R.id.lay);
        }
    }
}
