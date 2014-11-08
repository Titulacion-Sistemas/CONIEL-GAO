package com.gao.coniel.coniel_gao;

import android.app.ActionBar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import clases.Abonado;
import clases.SmartFragmentStatePagerAdapter;


public class ContenedorBusqueda extends Fragment implements ActionBar.TabListener, ViewPager.OnPageChangeListener {

    String[] sesion = null;

    public ContenedorBusqueda(String[] sesion){
        this.sesion = sesion;
    }

    public ContenedorBusqueda(){}

        SectionsPagerAdapter mSectionsPagerAdapter;
        ActionBar actionBar ;
        ViewPager mViewPager;
        View rootView;

    public View onCreateView (LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_contenedor_busqueda);
        rootView = inflater.inflate(R.layout.activity_contenedor_busqueda, container, false);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(this);

        actionBar = getActivity().getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.addTab(actionBar.newTab().setText("Búsqueda").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Datos de Abonado").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Detalle Medidor").setTabListener(this));

        return rootView;
    }
    private Abonado[] ab = null;
    public class SectionsPagerAdapter extends SmartFragmentStatePagerAdapter {
        Buscar b ;
        BusquedaDatosAbonado b1;
        BusquedaDatosMedidor b2;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            b = new Buscar(sesion);
            b1 = new BusquedaDatosAbonado();
            b2 = new BusquedaDatosMedidor();
        }

        @Override
        public Fragment getItem(int arg0) {
            switch (arg0) {
                case 0:
                    if(ab!=null){
                        b.setClientes(ab);
                        b1=new BusquedaDatosAbonado(b.getClientes()[0]);
                        b2=new BusquedaDatosMedidor(b.getClientes()[0].getMedidores());
                    }
                    Log.i("Info","C0");
                    return b;
                case 1:
                    if (b.getClientes()!=null ){
                        b1=new BusquedaDatosAbonado(b.getClientes()[0]);
                        b2=new BusquedaDatosMedidor(b.getClientes()[0].getMedidores());
                        ab=b.getClientes();
                    }else{
                        b1 = new BusquedaDatosAbonado();
                    }
                    Log.i("Info","C1");
                    return b1;
                case 2:
                    if (b.getClientes()!=null){
                        b1=new BusquedaDatosAbonado(b.getClientes()[0]);
                        getRegisteredFragment(1).onResume();
                        b2=new BusquedaDatosMedidor(b.getClientes()[0].getMedidores());
                        ab=b.getClientes();
                    }
                    else if( ab!=null ){
                        b1=new BusquedaDatosAbonado(ab[0]);
                        b2=new BusquedaDatosMedidor(ab[0].getMedidores());
                    }else{
                        b2 = new BusquedaDatosMedidor();
                    }
                    Log.i("Info","C2");
                    return b2;

                default:
                    Log.i("Info","Default");
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    //implements on pager selected
    @Override
    public void onPageScrolled(int i, float v, int i2) {  }


    @Override
    public void onPageSelected(int i) {
        getActivity().getActionBar().setSelectedNavigationItem(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {}





    @Override
    public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {

    }


}