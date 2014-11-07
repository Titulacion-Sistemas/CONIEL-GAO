package com.gao.coniel.coniel_gao;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import clases.Abonado;


public class BusquedaDatosAbonado extends Fragment {

    private Abonado abonado=null;

    public BusquedaDatosAbonado(Abonado abonado) {
        this.abonado = abonado;
    }

    public BusquedaDatosAbonado() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_busqueda_datos_abonado, container, false);
        if (abonado!=null)
            rellenar();
        return rootView;
    }

    public void rellenar() {

    }
}
