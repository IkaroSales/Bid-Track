package com.rgames.guilherme.bidtruck.view.romaneios.entrega.pagerdetalhes.pager.ocorrencia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rgames.guilherme.bidtruck.R;
import com.rgames.guilherme.bidtruck.controller.ControllerLogin;
import com.rgames.guilherme.bidtruck.controller.ControllerOcorrencia;
import com.rgames.guilherme.bidtruck.facade.Facade;
import com.rgames.guilherme.bidtruck.model.basic.Entrega;
import com.rgames.guilherme.bidtruck.model.basic.ImagemOcorrencia;
import com.rgames.guilherme.bidtruck.model.basic.MyProgressBar;
import com.rgames.guilherme.bidtruck.model.basic.Ocorrencia;
import com.rgames.guilherme.bidtruck.model.basic.Romaneio;
import com.rgames.guilherme.bidtruck.model.basic.TipoOcorrencia;
import com.rgames.guilherme.bidtruck.model.dao.http.HttpImagem;
import com.rgames.guilherme.bidtruck.model.errors.EmpresaNullException;
import com.rgames.guilherme.bidtruck.model.errors.EntregaNullException;
import com.rgames.guilherme.bidtruck.view.romaneios.entrega.pagerdetalhes.pager.AdapterRecyclerOcorrencia;
import com.vlk.multimager.activities.MultiCameraActivity;
import com.vlk.multimager.adapters.GalleryImagesAdapter;
import com.vlk.multimager.utils.Constants;
import com.vlk.multimager.utils.Image;
import com.vlk.multimager.utils.Params;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class OcorrenciaActivity extends AppCompatActivity {

    private int seq_entrega;
    private int romaneio;
    private ControllerOcorrencia controllerOcorrencia;
    private ControllerLogin controllerLogin;
    private HttpImagem httpImagem;
    private MyProgressBar myProgressBar;
    private AdapterRecyclerTipoOcorrencia adapter;
    private Button fab_photo;
    private Button teste;
    private RecyclerView rv;
    String codado;
    private ArrayList<String> listImagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_occurrence);
        try {
            if (getIntent().getExtras() != null) {
                seq_entrega = getIntent().getExtras().getInt(Entrega.PARCEL);
                romaneio = getIntent().getExtras().getInt(Romaneio.PARCEL);
                initToolbar();
                httpImagem = new HttpImagem();
                listImagem = new ArrayList<>();
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

        initFab();
        clickfloat();
        initList();
        initButton();
        clickTeste();
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
                // initFoto();
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

    private void initFab() {
        fab_photo = (Button) findViewById(R.id.fab_photo);
        rv = (RecyclerView) findViewById(R.id.rv_photo);
        teste = (Button) findViewById(R.id.testb);
    }

    private void clickfloat() {
        fab_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OcorrenciaActivity.this, MultiCameraActivity.class);
                Params params = new Params();
                params.setCaptureLimit(10);
                intent.putExtra(Constants.KEY_PARAMS, params);
                startActivityForResult(intent, Constants.TYPE_MULTI_CAPTURE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case Constants.TYPE_MULTI_CAPTURE:
                handleResponseIntent(data);
                break;

        }
    }

    private int getColumnCount() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        float thumbnailDpWidth = getResources().getDimension(R.dimen.thumbnail_width) / displayMetrics.density;
        return (int) (dpWidth / thumbnailDpWidth);
    }

    private void handleResponseIntent(Intent intent) {
        ArrayList<Image> imagesList = intent.getParcelableArrayListExtra(Constants.KEY_BUNDLE_LIST);


         for (int i = 0; i < imagesList.size(); i++) {
        String caminho = imagesList.get(0).imagePath;
        Bitmap bit = BitmapFactory.decodeFile(caminho);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        byte[] fotoB = stream.toByteArray();
        codado = Base64.encodeToString(fotoB, Base64.DEFAULT);
        listImagem.add(codado);
        }

        rv.setHasFixedSize(true);
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(getColumnCount(), GridLayoutManager.VERTICAL);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        rv.setLayoutManager(mLayoutManager);
        GalleryImagesAdapter imageAdapter = new GalleryImagesAdapter(this, imagesList, getColumnCount(), new Params());
        rv.setAdapter(imageAdapter);
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

    private void initFoto() {
        new AsyncTask<Void, Void, Boolean>() {
            ProgressDialog dialog;
            String msg = "";

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = ProgressDialog.show(OcorrenciaActivity.this, "fotos", "Enviando Fotos", true);
            }

            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    if (listImagem != null) {
                        return httpImagem.insert(1, "IKARO JR.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    msg = e.getMessage();
                }

                return false;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                dialog.dismiss();

                try {
                    if (msg.equals(""))
                        if (aBoolean) {
                            Toast.makeText(OcorrenciaActivity.this, "Foto Enviada.", Toast.LENGTH_LONG).show();
                            onBackPressed();
                        } else {
                            Toast.makeText(OcorrenciaActivity.this, "Falha ao tentar cadastrar a foto.", Toast.LENGTH_LONG).show();
                        }
                    else
                        Toast.makeText(OcorrenciaActivity.this, msg, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();


    }

    private void clickTeste() {
        teste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initFoto();
            }
        });
    }
}
