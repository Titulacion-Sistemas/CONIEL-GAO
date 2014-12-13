package com.gao.coniel.coniel_gao;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import clases.SessionManagerIngreso;


public class IngresoDatosAbonado extends android.support.v4.app.Fragment {

    private String [] datosAbonados = new String[2];
    EditText cuenta,cedula, nombre, estado, telefono, lugar, calle, geocodigo, fabrica, serial, marca, lectura;
    Button btnBuscarDatos;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.activity_ingreso_datos_abonado, container, false);

        cuenta = (EditText) view.findViewById(R.id.edtCuenta);
        cedula = (EditText) view.findViewById(R.id.edtCedula);
        nombre = (EditText) view.findViewById(R.id.edtNombre);
        estado = (EditText) view.findViewById(R.id.edtEstado);
        telefono = (EditText) view.findViewById(R.id.edtTelefono);
        lugar = (EditText) view.findViewById(R.id.edtLugar);
        calle = (EditText) view.findViewById(R.id.edtCalle);
        geocodigo = (EditText) view.findViewById(R.id.edtGeocodigo);
        fabrica = (EditText) view.findViewById(R.id.edtFabrica);
        serial = (EditText) view.findViewById(R.id.edtSerial);
        marca = (EditText) view.findViewById(R.id.edtMarca);
        lectura = (EditText) view.findViewById(R.id.edtLectura);
        btnBuscarDatos = (Button) view.findViewById(R.id.btnBuscarDatos);

        //Guardar Sesion para evitar cierre
        SessionManagerIngreso s = SessionManagerIngreso.getManager(getActivity().getApplicationContext());
        cuenta.setText(s.getStringKey("CUENTA"));
        cedula.setText(s.getStringKey("CEDULA"));
        nombre.setText(s.getStringKey("NOMBRE"));
        estado.setText(s.getStringKey("ESTADO"));
        telefono.setText(s.getStringKey("TELEFONO"));
        lugar.setText(s.getStringKey("LUGAR"));
        calle.setText(s.getStringKey("CALLE"));
        geocodigo.setText(s.getStringKey("GEOCODIGO"));
        fabrica.setText(s.getStringKey("FABRICA"));
        serial.setText(s.getStringKey("SERIAL"));
        marca.setText(s.getStringKey("MARCA"));
        lectura.setText(s.getStringKey("LECTURA"));

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
        SessionManagerIngreso.getManager(getActivity().getApplicationContext())
                .saveKey("Coniel-GAO", true)
                .saveKey("CUENTA", cuenta.getText().toString())
                .saveKey("CEDULA", cedula.getText().toString())
                .saveKey("NOMBRE", nombre.getText().toString())
                .saveKey("ESTADO", estado.getText().toString())
                .saveKey("TELEFONO", telefono.getText().toString())
                .saveKey("LUGAR", lugar.getText().toString())
                .saveKey("CALLE", calle.getText().toString())
                .saveKey("GEOCODIGO", geocodigo.getText().toString())
                .saveKey("FABRICA", fabrica.getText().toString())
                .saveKey("SERIAL", serial.getText().toString())
                .saveKey("MARCA", marca.getText().toString())
                .saveKey("LECTURA", lectura.getText().toString());
    }
}
