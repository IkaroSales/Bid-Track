package com.rgames.guilherme.bidtruck.view.romaneios.entrega;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.rgames.guilherme.bidtruck.R;
import com.rgames.guilherme.bidtruck.facade.Facade;
import com.rgames.guilherme.bidtruck.model.basic.Destinatario;
import com.rgames.guilherme.bidtruck.model.basic.Entrega;
import com.rgames.guilherme.bidtruck.model.basic.MyProgressBar;
import com.rgames.guilherme.bidtruck.model.basic.Romaneio;
import com.rgames.guilherme.bidtruck.model.basic.StatusEntrega;
import com.rgames.guilherme.bidtruck.model.dao.http.HttpEntrega;
import com.rgames.guilherme.bidtruck.model.dao.http.HttpRomaneio;
import com.rgames.guilherme.bidtruck.model.repositors.DestinatarioRep;
import com.rgames.guilherme.bidtruck.model.repositors.EntregaRep;
import com.rgames.guilherme.bidtruck.model.repositors.StatusEntregaRep;

import java.util.List;

public class EntregaActivity extends AppCompatActivity {

    private MyProgressBar myProgressBar;
    private Romaneio mRomaneio;
    private List<Entrega> mListEntregas;
    private boolean tem_romaneio;
    private boolean finish = true;
    private boolean atualizadaEntrega = true;
    private RetornaListaTask mRetornaTask;
    private Context context;
    private EntregaRep entregaRep;
    private DestinatarioRep destinatarioRep;
    private StatusEntregaRep statusEntregaRep;
    private Facade facade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
         entregaRep = new EntregaRep(context);
         destinatarioRep = new DestinatarioRep(context);
         statusEntregaRep = new StatusEntregaRep();
        facade = new Facade(this);
        try {
            if (getIntent().getExtras() != null) {
                mRomaneio = getIntent().getExtras().getParcelable(Romaneio.PARCEL);
                initToobal();
               // initList();

            } else {
                Toast.makeText(this, getString(R.string.app_err_null_romaneio), Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onResume(){
        super.onResume();

        try {
            if(finish == true){
                if(!facade.isConnected(this)){
                    List<Entrega> entregas = entregaRep.buscarEntrega();
                     if(entregas != null && entregas.size() > 0){
                         Log.i("Chaves2", "Inseriu " + entregas.size());
                         initRecyclerView(entregas);
                         finish = false;
                     }
                }else{
                    initList();
                    finish = false;
                }
            }
            else {
                    initRecyclerView(null);
                    mRomaneio = getIntent().getExtras().getParcelable(Romaneio.PARCEL);
                    mRetornaTask = new RetornaListaTask();
                    mRetornaTask.execute();

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    private void initViewPager() {
//        mViewPager = (ViewPager) findViewById(R.id.viewpager);
//        mViewPager.setAdapter(new AdapterViewPager(getSupportFragmentManager(), this, mEntrega));
//        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
//        mTabLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                mTabLayout.setupWithViewPager(mViewPager);
//            }
//        });
//    }

    private void initToobal() throws Exception {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(
                getResources().getString(R.string.menu_drw_entrega));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initList() {
        new AsyncTask<Void, Void, List<Entrega>>() {
            @Override
            protected void onPreExecute() {
                try {
                    initProgressBar();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected List<Entrega> doInBackground(Void... String) {
                Facade facade = new Facade(EntregaActivity.this);
                try {
                    return facade.selectEntrega();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<Entrega> entregas) {
                try {
                    if (entregas == null || entregas.size() == 0){
                        emptyView(true);
                        // mListEntregas = entregas;
                    }else {
                        entregaRep.inserirEntrega(entregas, mRomaneio);;
                        initRecyclerView(entregas);
                        finishProgressBar();
                    }
                         //inserir banco local

                  /*  for(Entrega ent : entregas) {

                        Entrega delivery = new Entrega();
                        Destinatario destinatario = new Destinatario();
                        StatusEntrega statusEntrega = new StatusEntrega();

                       //delivery.setCodigo(ent.getCodigo());
                        delivery.setNota_fiscal(ent.getNota_fiscal());
                        delivery.setPeso(ent.getPeso());
                        delivery.setSeq_entrega(ent.getSeq_entrega());

                        destinatario.setId(ent.getDestinatario().getEmpresa().getCodigo());
                        destinatario.setBairro(ent.getDestinatario().getBairro());
                        destinatario.setCEP(ent.getDestinatario().getCEP());
                        destinatario.setCidade(ent.getDestinatario().getCidade());
                        destinatario.setNome_fantasia(ent.getDestinatario().getNome_fantasia());
                        destinatario.setRazao_social(ent.getDestinatario().getRazao_social());
                        destinatario.setLogradouro(ent.getDestinatario().getLogradouro());
                        destinatario.setUF(ent.getDestinatario().getUF());
                        destinatario.setTelefone(ent.getDestinatario().getTelefone());
                        destinatario.setLatitude(ent.getDestinatario().getLatitude());
                        destinatario.setLongitude(ent.getDestinatario().getLongitude());
                        delivery.setDestinatario(destinatario);


                        statusEntrega.setCodigo(ent.getStatusEntrega().getCodigo());
                        statusEntrega.setDescricao(ent.getStatusEntrega().getDescricao());
                        delivery.setStatusEntrega(statusEntrega);


                        entregaRep.inserirEntrega(delivery, mRomaneio);
                        destinatarioRep.inserirDestinatario(destinatario);
                        statusEntregaRep.preencheStatusEntrega();


                        Toast.makeText(getBaseContext(), "Entrega inserida no banco com sucesso!", Toast.LENGTH_LONG).show();
                    }*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    class RetornaListaTask extends AsyncTask<Void, Void, List<Entrega>> {

       @Override
       protected void onPreExecute(){
           try{
               super.onPreExecute();
               // emptyView(true);
               initProgressBar();
           }catch (Exception e){
               e.printStackTrace();
           }
       }

        @Override
            protected List<Entrega> doInBackground(Void... String) {
            Facade facade = new Facade(EntregaActivity.this);
            try {
                return facade.selectEntrega();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Entrega> entregas) {
            try {
                if (entregas == null || entregas.size() == 0)
                    emptyView(true);
                initRecyclerView(entregas);
                finishProgressBar();
                mListEntregas = entregas;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void emptyView(boolean isVisible) {
        findViewById(R.id.txt_empty).setVisibility((isVisible) ? View.VISIBLE : View.GONE);
    }

    private void initRecyclerView(List<Entrega> entregas) throws Exception {
        RecyclerView r = (RecyclerView) findViewById(R.id.recyclerview);
        r.setLayoutManager(new LinearLayoutManager(this));
        mRomaneio.setEntregaList(entregas);
        r.setAdapter(new AdapterRecyclerDelivery(mRomaneio, this));
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(this, R.anim.list_layout);
        r.setLayoutAnimation(controller);
    }

    private void initProgressBar() throws ClassCastException, NullPointerException {
        if (myProgressBar == null)
            myProgressBar = new MyProgressBar((FrameLayout) findViewById(R.id.frame_progress));
    }

    private void finishProgressBar() throws Exception {
        if (myProgressBar != null) {
            myProgressBar.onFinish();
        }
    }
}