package com.gao.coniel.coniel_gao;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import clases.Abonado;
import clases.PagerItem;


public class ContenedorBusqueda extends Fragment implements ActionBar.TabListener, ViewPager.OnPageChangeListener {

    String[] sesion = null;

    ActionBar actionBar ;
    ViewPager mViewPager;
    View rootView;


    public ContenedorBusqueda(String[] sesion){
        this.sesion = sesion;
    }

    public ContenedorBusqueda(){}


    public View onCreateView (LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){

        rootView = inflater.inflate(R.layout.activity_contenedor_busqueda, container, false);

        ArrayList<PagerItem> pagerItems = new ArrayList<PagerItem>();
        pagerItems.add(new PagerItem("Fragment0", new Buscar(sesion)));

        mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
        mViewPager.setAdapter(new MiPagerAdapter(
                getActivity().getSupportFragmentManager(),
                pagerItems
        ));
        mViewPager.setOnPageChangeListener(this);

        getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar bar = getActivity().getActionBar();
        bar.addTab(bar.newTab().setText("Búsqueda").setTabListener(this));
        bar.addTab(bar.newTab().setText("Datos de Abonado").setTabListener(this));
        bar.addTab(bar.newTab().setText("Detalle Medidor").setTabListener(this));

        return rootView;
    }

    public void reconstruirPager(){
        MiPagerAdapter sa = (MiPagerAdapter) mViewPager.getAdapter();
        ArrayList<PagerItem> pagerItems = new ArrayList<PagerItem>();
        pagerItems.add(new PagerItem("Fragment0", sa.getItem(0)));
        sa.notifyDataSetChanged();
        mViewPager.setAdapter(new MiPagerAdapter(
                getActivity().getSupportFragmentManager(),
                pagerItems
        ));
    }

    public void cargarPager() {
        MiPagerAdapter sa = (MiPagerAdapter) mViewPager.getAdapter();
        sa.setAb(((Buscar) sa.getItem(0)).getClientes());

        ArrayList<PagerItem> pagerItems = new ArrayList<PagerItem>();
        pagerItems.add(new PagerItem("Fragment0", sa.getItem(0)));
        pagerItems.add(new PagerItem("Fragment1", new BusquedaDatosAbonado(sa.getAb()[0])));
        pagerItems.add(new PagerItem("Fragment2", new BusquedaDatosMedidor(sa.getAb()[0].getMedidores())));
        sa.setmPagerItems(pagerItems);
        sa.notifyDataSetChanged();


        mViewPager.setAdapter(
                new MiPagerAdapter(
                        getActivity().getSupportFragmentManager(),
                        pagerItems
                )
        );
    }

    public class MiPagerAdapter extends FragmentStatePagerAdapter {

        private FragmentManager mFragmentManager;
        private ArrayList<PagerItem> mPagerItems;
        private Abonado[] ab = null;

        public MiPagerAdapter(FragmentManager fragmentManager, ArrayList<PagerItem> pagerItems) {
            super(fragmentManager);
            mFragmentManager = fragmentManager;
            setmPagerItems(pagerItems);
        }


        @Override
        public Fragment getItem(int position) {

            // getItem is called to instantiate the fragment for the given page.
            // Return a DummySectionFragment (defined as a static inner class
            // below) with the page number as its lone argument.
            return getmPagerItems().get(position).getFragment();
            /*Fragment fragment = null;
            Bundle args = null;
            switch (position) {
                case 0:
                    fragment = new Buscar(sesion);
                    args = new Bundle();
                    args.putInt(Buscar.ARG_SECTION_NUMBER, position + 1);
                    break;
                case 1:
                    if (ab!=null) fragment = new BusquedaDatosAbonado(ab[0]);
                    else fragment = new BusquedaDatosAbonado();
                    args = new Bundle();
                    args.putInt(BusquedaDatosAbonado.ARG_SECTION_NUMBER, position + 1);
                    //args.putString(FragmentGraph.linkGraph, graphLink);
                    break;
                case 2:
                    if (ab!=null) fragment = new BusquedaDatosMedidor(ab[0].getMedidores());
                    else fragment = new BusquedaDatosMedidor();
                    args = new Bundle();
                    args.putInt(BusquedaDatosMedidor.ARG_SECTION_NUMBER, position + 1);
                    break;
            }
            fragment.setArguments(args);
            return fragment;*/
        }

        @Override
        public int getCount() {
            return mPagerItems.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public ArrayList<PagerItem> getmPagerItems() {
            return mPagerItems;
        }

        public void setmPagerItems(ArrayList<PagerItem> mPagerItems) {
            this.mPagerItems = mPagerItems;
        }

        public Abonado[] getAb() {
            return ab;
        }

        public void setAb(Abonado[] ab) {
            this.ab = ab;
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
        if(((MiPagerAdapter)mViewPager.getAdapter()).getmPagerItems().size()>tab.getPosition()) {
            mViewPager.setCurrentItem(tab.getPosition());
        }else{
            Toast t = Toast.makeText(
                    getActivity().getApplicationContext(),
                    "Debe realizar una busqueda...",
                    Toast.LENGTH_SHORT
            );
            t.show();
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {

    }


}