package com.rgames.guilherme.bidtruck.view.romaneios.entrega.pagerdetalhes;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.rgames.guilherme.bidtruck.R;
import com.rgames.guilherme.bidtruck.model.basic.Entrega;
import com.rgames.guilherme.bidtruck.model.basic.Romaneio;
import com.rgames.guilherme.bidtruck.view.romaneios.entrega.pagerdetalhes.pager.DetalhesPagerFragment;
import com.rgames.guilherme.bidtruck.view.romaneios.entrega.pagerdetalhes.pager.OcorrenciaPagerFragment;
import com.rgames.guilherme.bidtruck.view.romaneios.entrega.pagerdetalhes.pager.RotaPagerFragment;

public class AdapterViewPager extends FragmentStatePagerAdapter {
    private Romaneio mRomaneio;
    private String[] mTitles;
    private int COUNT = 3;
    private Entrega mEntrega;
    private double[] mInicio, mFim;

    public AdapterViewPager(FragmentManager fm, Context context, Romaneio romaneio, Entrega entrega, double[] inicio, double[] fim) {
        super(fm);
        mInicio = inicio;
        mFim = fim;
        mRomaneio = romaneio;
        mEntrega = entrega;
        mTitles = context.getResources().getStringArray(R.array.app_tablayout_entrega_detalhes);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return DetalhesPagerFragment.newInstance(mRomaneio, mEntrega);
            case 1:
                return RotaPagerFragment.newInstance(mInicio, mFim);
            case 2:
                return OcorrenciaPagerFragment.newInstance(mEntrega.getSeq_entrega(), mRomaneio.getCodigo());
            default:
                return DetalhesPagerFragment.newInstance(mRomaneio, mEntrega);
        }
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
