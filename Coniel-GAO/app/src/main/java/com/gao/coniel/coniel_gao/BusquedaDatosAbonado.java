package com.gao.coniel.coniel_gao;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

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
            rellenar(abonado);
        }
        Log.i("Info", "creado fragment 1");
        return rootView;
    }

    public void rellenar(Abonado abonado) {
        EditText et = ((EditText)rootView.findViewById(R.id.EditTextCuenta));
        et.setText(abonado.getCuenta(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextAbonado)).setText(abonado.getNombre(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextCedulaRuc)).setText(abonado.getCi(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextGeocodigo)).setText(abonado.getGeocodigo(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextDireccion)).setText(abonado.getDireccion(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextInterseccion)).setText(abonado.getInterseccion(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextUrbanizacion)).setText(abonado.getUrbanizacion(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextEstado)).setText(abonado.getEstado(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextDeuda)).setText(abonado.getDeuda(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextMesDeuda)).setText(abonado.getMesesAdeudado(), TextView.BufferType.EDITABLE);
        Log.i("Info", "rellenado fragment 1");
        this.abonado=abonado;
    }

}
