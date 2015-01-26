package com.gao.coniel.coniel_gao;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Acercade extends Fragment {

    View rootView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_acercade, container, false);
        return rootView;
    }


    @Override
    public void onDetach() {
        super.onDetach();

        ActionBar bar = getActivity().getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        bar.removeAllTabs();
        bar.setTitle(R.string.title_activity_contenedor);
        bar.setSubtitle(" Gestión de Actividades Operativas ");
        try {
            Contenedor c = ((Contenedor)getActivity());
            c.getmDrawerList().setItemChecked(0, true);
            c.getmDrawerList().setSelection(0);
            c.setTitle(R.string.title_activity_contenedor);
        }catch (Exception ignored){}

        Log.i("Información", "Dtach de ContenedorBusqueda");
    }


}
