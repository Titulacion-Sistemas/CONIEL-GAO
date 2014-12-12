package com.gao.coniel.coniel_gao;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import clases.SessionManagerIngreso;


public class IngresoActividadInstalador extends Fragment {
    DatePicker fecha;
    TimePicker tiempo;
    Spinner spinerSolicitud, spinerInstalador, spinerCuadrilla;
    private String [] datosAbonados = new String[5];

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_ingreso_actividad_instalador, container, false);

        fecha = (DatePicker) view.findViewById(R.id.fecha);
        tiempo = (TimePicker) view.findViewById(R.id.hora);
        spinerSolicitud = (Spinner) view.findViewById(R.id.spinnerSolicitud);
        spinerInstalador = (Spinner) view.findViewById(R.id.spinnerInstalador);
        spinerCuadrilla = (Spinner) view.findViewById(R.id.spinnerCuadrilla);


        SessionManagerIngreso s = SessionManagerIngreso.getManager(getActivity().getApplicationContext());
        String [] se = s.getStringKey("FECHA").split("/");
        fecha.updateDate(Integer.parseInt(se[2]),Integer.parseInt(se[1]), Integer.parseInt(se[0]));
        //tiem
       // Nombre.setText(s.getStringKey("NOMBRE"));

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
                .saveKey("FECHA", fecha.getDayOfMonth() + "/" + fecha.getMonth() + "/" + fecha.getYear())
                .saveKey("TIEMPO", tiempo.getCurrentHour() + tiempo.getCurrentMinute())
                .saveKey("SOLICITUD", spinerSolicitud.getSelectedItem().toString())
                .saveKey("INSTALADOR", spinerInstalador.getSelectedItem().toString())
                .saveKey("CUADRILLA", spinerCuadrilla.getSelectedItem().toString());
    }
}
