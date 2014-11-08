package com.gao.coniel.coniel_gao;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import clases.Abonado;


public class BusquedaDatosAbonado extends Fragment {

    private Abonado abonado=null;

    public BusquedaDatosAbonado(Abonado abonado) {
        this.abonado = abonado;
    }

    public BusquedaDatosAbonado() {}

    View rootView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_busqueda_datos_abonado, container, false);
        if (abonado!=null) {
            rellenar();
        }
        Log.i("Info", "creado fragment 1");
        return rootView;
    }

    public void rellenar() {
        ((EditText)rootView.findViewById(R.id.EditTextCuenta)).setText(abonado.getCuenta());
        ((EditText)rootView.findViewById(R.id.EditTextAbonado)).setText(abonado.getNombre());
        ((EditText)rootView.findViewById(R.id.EditTextCedulaRuc)).setText(abonado.getCi());
        ((EditText)rootView.findViewById(R.id.EditTextGeocodigo)).setText(abonado.getGeocodigo());
        ((EditText)rootView.findViewById(R.id.EditTextDireccion)).setText(abonado.getDireccion());
        ((EditText)rootView.findViewById(R.id.EditTextInterseccion)).setText(abonado.getInterseccion());
        ((EditText)rootView.findViewById(R.id.EditTextUrbanizacion)).setText(abonado.getUrbanizacion());
        ((EditText)rootView.findViewById(R.id.EditTextEstado)).setText(abonado.getEstado());
        ((EditText)rootView.findViewById(R.id.EditTextDeuda)).setText(abonado.getDeuda());
        ((EditText)rootView.findViewById(R.id.EditTextMesDeuda)).setText(abonado.getMesesAdeudado());
        Log.i("Info", "rellenado fragment 1");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (abonado!=null) {
            rellenar();
        }
    }
}
