package com.gao.coniel.coniel_gao;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import clases.SessionManagerIngreso;


public class IngresoDatosAbonado extends android.support.v4.app.Fragment {

    private String [] datosAbonados = new String[2];
    EditText Cuenta,Cedula, Nombre, Apellido, Celular, Direccion;
    // private OnArticuloSelectedListener listener;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_ingreso_datos_abonado, container, false);

        //Guardar Sesion para evitar cierre
        SessionManagerIngreso s = SessionManagerIngreso.getManager(getActivity().getApplicationContext());
        /*Cuenta.setText(s.getStringKey("CUENTA"));
        Cedula.setText(s.getStringKey("CEDULA"));
        Nombre.setText(s.getStringKey("NOMBRE"));
        Apellido.setText(s.getStringKey("APELLIDO"));
        Celular.setText(s.getStringKey("CELULAR"));
        Direccion.setText(s.getStringKey("DIRECCION"));*/

        return view;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i("Se ha ejecutado el ", "  ONATTACH");
        }

    //El Fragment ha sido quitado de su Activity y ya no está disponible
    @Override
    public void onStop(){
        super.onStop();
        Log.i("Se ha ejecutado el ", "  ONSTOP");
        //Guardar Sesion para evitar cierre
        //SessionManagerIngreso.getManager(getActivity().getApplicationContext())
               /* .saveKey("Coniel-GAO", true)
                .saveKey("CUENTA", Cuenta.getText().toString())
                .saveKey("CEDULA", Cedula.getText().toString())
                .saveKey("NOMBRE", Nombre.getText().toString())
                .saveKey("APELLIDO", Apellido.getText().toString())
                .saveKey("CELULAR", Celular.getText().toString())
                .saveKey("DIRECCION", Direccion.getText().toString());*/
    }
}
