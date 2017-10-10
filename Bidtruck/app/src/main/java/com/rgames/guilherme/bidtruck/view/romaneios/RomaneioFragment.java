package com.rgames.guilherme.bidtruck.view.romaneios;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.rgames.guilherme.bidtruck.R;
import com.rgames.guilherme.bidtruck.facade.Facade;
import com.rgames.guilherme.bidtruck.model.basic.Empresa;
import com.rgames.guilherme.bidtruck.model.basic.Motorista;
import com.rgames.guilherme.bidtruck.model.basic.MyProgressBar;
import com.rgames.guilherme.bidtruck.model.basic.Romaneio;
import com.rgames.guilherme.bidtruck.model.dao.http.HttpRomaneio;
import com.rgames.guilherme.bidtruck.model.errors.EmpresaNullException;
import com.rgames.guilherme.bidtruck.model.errors.MotoristaNaoConectadoException;

import java.util.List;

/**
 * Created by Guilherme on 05/09/2017.
 */

public class RomaneioFragment extends Fragment {

    private View mView;
    private MyProgressBar myProgressBar;
    private Facade mFacade;
    private Empresa empresa;
    private Motorista motoristas;
    private boolean finishRomaneio = true;
    private List<Romaneio> romaneioList;
    private ListaTask mListaTaskRomaneio;
    private List<Romaneio> romaneioList2;

    public RomaneioFragment() {

    }

    public static RomaneioFragment newInstance(Empresa empresa) {
        RomaneioFragment fragment = new RomaneioFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Empresa.PARCEL_EMPRESA, empresa);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (((AppCompatActivity) getActivity()).getSupportActionBar() != null)
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(
                        getActivity().getResources().getString(R.string.menu_drw_romaneio));
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        pegarEmpresa();
    }

    private void pegarEmpresa() {
        if (getArguments() != null && getArguments().getParcelable(Empresa.PARCEL_EMPRESA) != null) {
            empresa = getArguments().getParcelable(Empresa.PARCEL_EMPRESA);
        } else {
            Toast.makeText(getContext(), "Empresa não encontrada. - Err 1", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            mFacade = new Facade(getActivity());
            if (empresa == null) {
                pegarEmpresa();
                emptyView(true);
            } else {
                if (!mFacade.isConnected(getActivity())) {
                    Toast.makeText(getActivity(), getString(R.string.app_err_exc_semConexao), Toast.LENGTH_LONG).show();
                    emptyView(true);
                } else if (finishRomaneio == true) {
                    init();
                    finishRomaneio = false;
                }else{
                   mListaTaskRomaneio = new ListaTask();
                   mListaTaskRomaneio.execute();

                }



            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {

        return mView = inflater.inflate(R.layout.fragment_romaneio, container, false);
    }

    //AQUI O METODO QUE RECEBE OS DADOS DA ACTIVITY
   /* @Override
    public void passaInformacao(Empresa informacao) {
        if (informacao != null) {
            empresa = informacao;

        }

    }*/


    private void init() {
        new AsyncTask<Void, Void, List<Romaneio>>() {
            String msg = "";

            @Override
            protected void onPreExecute() {
                try {
                    initProgressBar();
                    emptyView(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected List<Romaneio> doInBackground(Void... voids) {
                try {
                    return mFacade.selectRomaneio(empresa, mFacade.isLogged());
                } catch (MotoristaNaoConectadoException e) {
                    msg = e.getMessage();
                    e.printStackTrace();
                } catch (EmpresaNullException e) {
                    msg = e.getMessage();
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<Romaneio> romaneios) {
                try {
                    if (romaneios != null && romaneios.size() == 0)
                        emptyView(true);
                    else if (romaneioList == null) {

                        romaneioList = romaneios;
                        if (romaneioList != null) {

                            /*for (Romaneio mRomaneio : romaneioList) {

                                if (mRomaneio.getStatus_romaneio().getCodigo() == 4) {
                                    Toast.makeText(getActivity(), getString(R.string.app_err_input_vazio), Toast.LENGTH_LONG).show();
                                    // emptyViewRomaneio(true);
                                    finishProgressBar();
                                if (mRomaneio.getStatus_romaneio().getCodigo() != 4) {
                                    initRecyclerView(romaneioList);
                                    finishProgressBar();
                                }*/
								
								   initRecyclerView(romaneioList);
                                    finishProgressBar();

                            }
                        }
                    }

                    if (romaneioList == null) {
                        if (msg != null && !msg.equals(""))
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        finishProgressBar();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }


    class ListaTask extends AsyncTask<Void, Void, List<Romaneio>> {

        String msg = "";

            @Override
            protected void onPreExecute() {
                try {
                    initProgressBar();
                    emptyView(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        @Override
        protected List<Romaneio> doInBackground(Void... voids) {
            try {

               // return mFacade.selectRomaneio(empresa, mFacade.isLogged());
                return mFacade.selectNovo(empresa, mFacade.isLogged());
            } catch (MotoristaNaoConectadoException e) {
                msg = e.getMessage();
                e.printStackTrace();
            } catch (EmpresaNullException e) {
                msg = e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Romaneio> romaneios) {
            try {
                if (romaneios != null && romaneios.size() == 0)
                    emptyView(true);
                else if (romaneioList2 == null) {

                    romaneioList2 = romaneios;
                    if (romaneioList2 != null) {

                        /*for (Romaneio mRomaneio : romaneioList2) {

                            if (mRomaneio.getStatus_romaneio().getCodigo() == 4) {

                                Toast.makeText(getActivity(), getString(R.string.app_err_input_vazio), Toast.LENGTH_LONG).show();
                                emptyView(true);
                                finishProgressBar();
                            }
                              //  initRecyclerView(romaneioList);
                              //  finishProgressBar();*/
							  
						      
								   initRecyclerView(romaneioList);
                                    finishProgressBar();
	


                        }
                    }
                }

                if (romaneioList == null) {
                    if (msg != null && !msg.equals(""))
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    finishProgressBar();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    private void emptyView(boolean isVisible) {
        mView.findViewById(R.id.txt_empty).setVisibility((isVisible) ? View.VISIBLE : View.GONE);
    }



    private void initRecyclerView(List<Romaneio> list) throws Exception {
        if (mView != null && getActivity() != null) {
            RecyclerView r = mView.findViewById(R.id.recyclerview);
            r.setLayoutManager(new LinearLayoutManager(getActivity()));
            r.setAdapter(new AdapterRecyclerRomaneio(getActivity(), list));
        } else throw new NullPointerException("View/Contexto Nulo");
    }

    private void initProgressBar() throws ClassCastException, NullPointerException {
        if (myProgressBar == null)
            myProgressBar = new MyProgressBar((FrameLayout) mView.findViewById(R.id.frame));
    }

    private void finishProgressBar() throws Exception {
        if (myProgressBar != null) {
            myProgressBar.onFinish();
        }
    }


}

