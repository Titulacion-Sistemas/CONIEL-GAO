package com.gao.coniel.coniel_gao;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import clases.SessionManagerIngreso;


public class IngresosReferencias extends Fragment {

    EditText edtFabricaRef, edtSerialRef, edtMarcaRef, edtCuentaRef;
    Button btnBuscarRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ingresos_referencias, container, false);

        btnBuscarRef = (Button) view.findViewById(R.id.btnBuscarRef);
        edtFabricaRef = (EditText) view.findViewById(R.id.edtFabricaRef);
        edtSerialRef = (EditText) view.findViewById(R.id.edtSerialRef);
        edtMarcaRef = (EditText) view.findViewById(R.id.edtMarcaRef);
        edtCuentaRef = (EditText) view.findViewById(R.id.edtCuentaRef);

       //Guardar Variables de Sesion
        SessionManagerIngreso s = SessionManagerIngreso.getManager(getActivity().getApplicationContext());
        edtFabricaRef.setText(s.getStringKey("FABRICAREF"));
        edtSerialRef.setText(s.getStringKey("SERIALREF"));
        edtMarcaRef.setText(s.getStringKey("MARCAREF"));
        edtCuentaRef.setText(s.getStringKey("CUENTAREF"));

        return view;
    }

    //El Fragment ha sido quitado de su Activity y ya no está disponible
    @Override
    public void onStop(){
        super.onStop();
        Log.i("Se ha ejecutado el ", "  ONSTOP");

       //Guardar Sesion para evitar cierre
        SessionManagerIngreso.getManager(getActivity().getApplicationContext())
                .saveKey("Coniel-GAO", true)
                .saveKey("FABRICAREF", edtFabricaRef.getText().toString())
                .saveKey("SERIALREF", edtSerialRef.getText().toString())
                .saveKey("MARCAREF", edtMarcaRef.getText().toString())
                .saveKey("CUENTAREF", edtCuentaRef.getText().toString());
    }


}
