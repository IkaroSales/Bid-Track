package com.rgames.guilherme.bidtruck.facade;

import android.content.Context;

import com.rgames.guilherme.bidtruck.controller.ControllerLogin;
import com.rgames.guilherme.bidtruck.model.basic.Entrega;
import com.rgames.guilherme.bidtruck.model.basic.Motorista;
import com.rgames.guilherme.bidtruck.model.basic.Romaneio;
import com.rgames.guilherme.bidtruck.model.dao.http.HttpConnection;
import com.rgames.guilherme.bidtruck.model.dao.http.HttpEntrega;
import com.rgames.guilherme.bidtruck.model.dao.http.HttpRomaneio;

import java.util.List;

public class Facade implements IFacade {

    private Context mContext;
    private HttpRomaneio httpRomaneio;
    private HttpEntrega httpEntrega;
    private ControllerLogin controllerLogin;

    public Facade(Context context) {
        mContext = context;
    }

    @Override
    public String connectionTest() {
        if (HttpConnection.isConnected(mContext))
            return HttpConnection.ConnecetinTest();
        else return "Sem conexão, tente novamente! ";
    }

    @Override
    public List<Romaneio> selectRomaneio() throws Exception {
        if (httpRomaneio == null)
            httpRomaneio = new HttpRomaneio(mContext);
        return httpRomaneio.select();
    }


    public List<Entrega> selectEntrega() throws Exception {
        if (httpEntrega == null)
            httpEntrega = new HttpEntrega(mContext);
        return httpEntrega.select();

    }

    @Override
    public Motorista login(String email, String senha) throws Exception {
        if (!HttpConnection.isConnected(mContext))
            throw new NullPointerException("Sem conexão");
        if (controllerLogin == null) controllerLogin = new ControllerLogin(mContext);
        return controllerLogin.login(email, senha);
    }

    @Override
    public Motorista isLogged() throws Exception{
        if (controllerLogin == null) controllerLogin = new ControllerLogin(mContext);
        return controllerLogin.isLogged();
    }

    @Override
    public void setLogged(Motorista motorista) throws Exception{
        if (controllerLogin == null) controllerLogin = new ControllerLogin(mContext);
        controllerLogin.setLogged(motorista);
    }

    @Override
    public boolean isMatenhaConectado() throws Exception {
        if (controllerLogin == null) controllerLogin = new ControllerLogin(mContext);
        return controllerLogin.isMatenhaConectado();
    }

    @Override
    public void setMatenhaConectado(boolean isConnected) throws Exception {
        if (controllerLogin == null) controllerLogin = new ControllerLogin(mContext);
        controllerLogin.setMatenhaConectado(isConnected);
    }


}
