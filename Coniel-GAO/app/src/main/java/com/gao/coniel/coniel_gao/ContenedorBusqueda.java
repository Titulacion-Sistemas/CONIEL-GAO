package com.gao.coniel.coniel_gao;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import clases.Abonado;
import clases.PagerItem;
import clases.SmartFragmentStatePagerAdapter;


public class ContenedorBusqueda extends Fragment implements ActionBar.TabListener, ViewPager.OnPageChangeListener {

    String[] sesion = null;
    private Abonado[] ab = null;
    MiPagerAdapter mSectionsPagerAdapter;
    ActionBar actionBar ;
    ViewPager mViewPager;
    View rootView;


    public ContenedorBusqueda(String[] sesion){
        this.sesion = sesion;
    }

    public ContenedorBusqueda(){}


    public View onCreateView (LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_contenedor_busqueda);
        rootView = inflater.inflate(R.layout.activity_contenedor_busqueda, container, false);

        ArrayList<PagerItem> pagerItems = new ArrayList<PagerItem>();
        pagerItems.add(new PagerItem("Fragment0", new Buscar(sesion)));
        pagerItems.add(new PagerItem("Fragment1", new BusquedaDatosAbonado()));
        pagerItems.add(new PagerItem("Fragment2", new BusquedaDatosMedidor()));

        mSectionsPagerAdapter = new MiPagerAdapter(
                getActivity().getSupportFragmentManager(),
                pagerItems
        );

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(this);


        actionBar = getActivity().getActionBar();
        assert actionBar != null;
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.addTab(actionBar.newTab().setText("Búsqueda").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Datos de Abonado").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Detalle Medidor").setTabListener(this));

        return rootView;
    }

    public class MiPagerAdapter extends FragmentStatePagerAdapter {

        private FragmentManager mFragmentManager;
        private ArrayList<PagerItem> mPagerItems;

        public MiPagerAdapter(FragmentManager fragmentManager, ArrayList<PagerItem> pagerItems) {
            super(fragmentManager);
            mFragmentManager = fragmentManager;
            mPagerItems = pagerItems;
        }

        @Override
        public Fragment getItem(int arg0) {
            return mPagerItems.get(arg0).getFragment();
            /*switch (arg0) {
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
            }*/
        }

        @Override
        public int getCount() {
            return mPagerItems.size();
        }
    }

    //implements on pager selected
    @Override
    public void onPageScrolled(int i, float v, int i2) {  }


    @Override
    public void onPageSelected(int i) {
        try {
            if(i>0){
                ab=((Buscar)mSectionsPagerAdapter.getItem(0)).getClientes();
                if (ab!=null && !(((Buscar)mSectionsPagerAdapter.getItem(0)).getTvData()).getText().toString().equals("")) {

                /**/    ArrayList<PagerItem> pagerItems = new ArrayList<PagerItem>();
                    pagerItems.add(new PagerItem("Fragment0", mSectionsPagerAdapter.getItem(0)));
                    pagerItems.add(new PagerItem("Fragment1", new BusquedaDatosAbonado(ab[0])));
                    pagerItems.add(new PagerItem("Fragment2", new BusquedaDatosMedidor(ab[0].getMedidores())));

                    //mViewPager.setAdapter(new MiPagerAdapter(getActivity().getSupportFragmentManager(), pagerItems));
                    (((Buscar)mSectionsPagerAdapter.getItem(0)).getTvData()).setText("");
                    ((BusquedaDatosAbonado)mSectionsPagerAdapter.getItem(1)).rellenar(ab[0]);
                    Log.i("",(((Buscar)mSectionsPagerAdapter.getItem(0)).getTvData()).getText().toString()+"");
                    //mViewPager.setCurrentItem(i);
                }
            }
            ab=null;
        }catch (Exception ignored){}
        getActivity().getActionBar().setSelectedNavigationItem(i);

    }

    @Override
    public void onPageScrollStateChanged(int i) {}





    @Override
    public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {

    }


}