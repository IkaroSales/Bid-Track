package com.rgames.guilherme.bidtruck.view.romaneios.entrega.pagerdetalhes.pager.ocorrencia;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rgames.guilherme.bidtruck.R;
import com.rgames.guilherme.bidtruck.controller.ControllerLogin;
import com.rgames.guilherme.bidtruck.controller.ControllerOcorrencia;
import com.rgames.guilherme.bidtruck.facade.Facade;
import com.rgames.guilherme.bidtruck.model.basic.Entrega;
import com.rgames.guilherme.bidtruck.model.basic.MyProgressBar;
import com.rgames.guilherme.bidtruck.model.basic.Ocorrencia;
import com.rgames.guilherme.bidtruck.model.basic.Romaneio;
import com.rgames.guilherme.bidtruck.model.basic.TipoOcorrencia;
import com.rgames.guilherme.bidtruck.model.errors.EmpresaNullException;
import com.rgames.guilherme.bidtruck.model.errors.EntregaNullException;
import com.rgames.guilherme.bidtruck.view.romaneios.entrega.pagerdetalhes.pager.AdapterRecyclerOcorrencia;

import java.util.List;

public class OcorrenciaActivity extends AppCompatActivity {

    private int seq_entrega;
    private int romaneio;
    private ControllerOcorrencia controllerOcorrencia;
    private ControllerLogin controllerLogin;
    private MyProgressBar myProgressBar;
    private AdapterRecyclerTipoOcorrencia adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_occurrence);
        try {
            if (getIntent().getExtras() != null) {
                seq_entrega = getIntent().getExtras().getInt(Entrega.PARCEL);
                romaneio = getIntent().getExtras().getInt(Romaneio.PARCEL);
                initToolbar();
                controllerOcorrencia = new ControllerOcorrencia();
                controllerLogin = new ControllerLogin(OcorrenciaActivity.this);
            } else {
                onBackPressed();
                throw new NullPointerException("Dados não foram informados");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initList();
        initButton();
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

    private void initButton() {
        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask<Void, Void, Boolean>() {
                    String descrip;
                    String msg = "";

                    @Override
                    protected void onPreExecute() {
                        initProgressBar();
                        descrip = ((TextView) findViewById(R.id.edit_description)).getText().toString();
                    }

                    @Override
                    protected Boolean doInBackground(Void... voids) {
                        try {
                            return controllerOcorrencia.insert(new Ocorrencia(controllerLogin.getIdEmpresa()
                                    , seq_entrega
                                    , romaneio
                                    , adapter.getCodigoSelecionado()
                                    , descrip));
                        } catch (Exception e) {
                            e.printStackTrace();
                            msg = e.getMessage();
                            return false;
                        }
                    }

                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        try {
                            if (msg.equals(""))
                                if (aBoolean) {
                                    Toast.makeText(OcorrenciaActivity.this, "Ocorrência cadastrada.", Toast.LENGTH_LONG).show();
                                    onBackPressed();
                                } else {
                                    Toast.makeText(OcorrenciaActivity.this, "Falha ao tentar cadastrar a ocorrência.", Toast.LENGTH_LONG).show();
                                }
                            else
                                Toast.makeText(OcorrenciaActivity.this, msg, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                finishProgressBar();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.execute();
            }
        });
    }

    private void initList() {
        new AsyncTask<Void, Void, List<TipoOcorrencia>>() {
            String msg = "";

            @Override
            protected void onPreExecute() {
                initProgressBar();
            }

            @Override
            protected List<TipoOcorrencia> doInBackground(Void... voids) {
                try {
                    return controllerOcorrencia.selectTipo(controllerLogin.getIdEmpresa());
                } catch (EmpresaNullException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(List<TipoOcorrencia> tipoOcorrencia) {
                try {
                    if (tipoOcorrencia != null && tipoOcorrencia.size() > 0 && msg.equals("")) {
                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
                        recyclerView.setLayoutManager(new LinearLayoutManager(OcorrenciaActivity.this));
                        adapter = new AdapterRecyclerTipoOcorrencia(tipoOcorrencia);
                        recyclerView.setAdapter(adapter);
                    } else
                        initEmpty(false);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        finishProgressBar();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.execute();
    }

    private void initEmpty(boolean b) {
        findViewById(R.id.txt_empty).setVisibility((b) ? View.VISIBLE : View.GONE);
    }

    private void initToolbar() throws Exception {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
