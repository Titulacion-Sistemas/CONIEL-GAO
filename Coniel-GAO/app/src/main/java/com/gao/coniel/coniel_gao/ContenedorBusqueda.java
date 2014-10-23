package com.gao.coniel.coniel_gao;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;


public class ContenedorBusqueda extends FragmentActivity implements ActionBar.TabListener, ViewPager.OnPageChangeListener {

        SectionsPagerAdapter mSectionsPagerAdapter;
        ActionBar actionBar;
        ViewPager mViewPager;

    public void onCreate ( Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenedor_busqueda);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOnPageChangeListener(this);
        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tab = actionBar.newTab().setText("Búsqueda").setTabListener(this);
        actionBar.addTab(tab);

        tab = actionBar.newTab().setText("Datos de Abonado").setTabListener(this);
        actionBar.addTab(tab);

        tab = actionBar.newTab().setText("Detalle Medidor").setTabListener(this);
        actionBar.addTab(tab);
    }

    public class SectionsPagerAdapter extends android.support.v13.app.FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) { super(fm);}

        @Override
        public Fragment getItem(int arg0) {

            switch (arg0) {
                case 0:
                    return new Buscar();
                case 1:
                    //return new Fragment_productos();
                case 2:
                    //return new Fragment_pedidos();
                default:
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
        getActionBar().setSelectedNavigationItem(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {}

    //implement tab listener
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }


}