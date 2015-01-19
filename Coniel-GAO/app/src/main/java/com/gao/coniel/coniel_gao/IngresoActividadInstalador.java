package com.gao.coniel.coniel_gao;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

import clases.SessionManagerIngreso;
import clases.Tupla;
import serviciosWeb.SW;


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

        asyncLoad al = new asyncLoad();
        al.execute();

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

        //Guardar variables
        SessionManagerIngreso.getManager(getActivity().getApplicationContext())
                .saveKey("Coniel-GAO", true)
                .saveKey("FECHA", fecha.getDayOfMonth() + "/" + fecha.getMonth() + "/" + fecha.getYear())
                .saveKey("HORA", tiempo.getCurrentHour() + ":" + tiempo.getCurrentMinute())
                .saveKey("SOLICITUD", spinerSolicitud.getSelectedItemPosition())
                .saveKey("INSTALADOR", spinerInstalador.getSelectedItemPosition())
                .saveKey("CUADRILLA", spinerCuadrilla.getSelectedItemPosition());
    }

    // Metodo Agregar datos a Spinner
    public void addDynamic(Spinner sp, ArrayList<String> dynamicList){

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, dynamicList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(dataAdapter);

    }



    //EN SEGUNDO PLANO
    private class asyncLoad extends AsyncTask<String, Float, Object> {

        String toast="";

        @Override
        protected Object doInBackground(String... params) {
            SW acc = new SW("ingresos.wsdl", "ingresoActividadInstalador");
            /*acc.asignarPropiedades(
                    new Tupla[]{
                            new Tupla<String, Object>("idUsuario", params[0]),
                            new Tupla<String, Object>("contrato", params[1])
                    }
            );*/
            Object r = acc.ajecutar();
            try{
                return r;
            }catch (Exception e){
                toast = "Error, No se pudo cargar los datos requeridos";
                this.cancel(true);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object r) {
            super.onPostExecute(r);

            System.out.print(r);

            SoapObject data = (SoapObject)r;
            System.out.print(data);

            ArrayList<String> instalador = new ArrayList<String>();
            for (int j=0 ; j<((SoapObject)data.getProperty(0)).getPropertyCount() ; j++){
                instalador.add(""+(((SoapObject)data.getProperty(0)).getProperty(j)));
            }

            try{
                addDynamic(spinerInstalador, instalador);
            }catch (Exception e){
                Log.e("Error al Cargar Instaladores: ",""+e);
            }

            ArrayList<String> cuadrilla = new ArrayList<String>();
            for (int j=0 ; j<((SoapObject)data.getProperty(1)).getPropertyCount() ; j++){
                cuadrilla.add(""+(((SoapObject)data.getProperty(1)).getProperty(j)));
            }

            try{
                addDynamic(spinerCuadrilla, cuadrilla);
            }catch (Exception e){
                Log.e("Error al Cargar Cuadrillas: ",""+e);
            }

            ArrayList<String> tSolicitud = new ArrayList<String>();
            for (int j=0 ; j<((SoapObject)data.getProperty(2)).getPropertyCount() ; j++){
                tSolicitud.add(""+(((SoapObject)data.getProperty(2)).getProperty(j)));
            }

            try{
                addDynamic(spinerSolicitud, tSolicitud);
            }catch (Exception e){
                Log.e("Error al Cargar Tipos de Solicitud: ",""+e);
            }

            recuperar();

        }

        protected void onCancelled() {
            Toast t = Toast.makeText(
                    getActivity().getApplicationContext(),
                    toast,
                    Toast.LENGTH_SHORT
            );
            t.show();

        }
    }

    private void recuperar() {

        SessionManagerIngreso s = SessionManagerIngreso.getManager(getActivity().getApplicationContext());

        if  ((s.getStringKey("IDACTIVIDADSELECCIONADA1")+"").equals("")){

            String[] se = s.getStringKey("FECHA").split("/");
            if (se.length == 3) {
                Log.i("infoFecha", se.length + ", " + se[2] + "/" + se[1] + "/" + se[0]);
                fecha.updateDate(Integer.parseInt(se[2]), Integer.parseInt(se[1]), Integer.parseInt(se[0]));
            }

            String[] st = s.getStringKey("HORA").split(":");
            if (st.length == 2) {
                Log.i("infoHora", st.length + ", " + st[0] + ":" + st[1]);
                tiempo.setCurrentHour(Integer.parseInt(st[0]));
                tiempo.setCurrentMinute(Integer.parseInt(st[1]));
            }

            spinerSolicitud.setSelection(s.getIntKey("SOLICITUD"));
            spinerInstalador.setSelection(s.getIntKey("INSTALADOR"));
            spinerCuadrilla.setSelection(s.getIntKey("CUADRILLA"));

        }
        else {

            asyncRecuperar ar = new asyncRecuperar();
            ar.execute(s.getStringKey("IDACTIVIDADSELECCIONADA")+"");

        }
    }


    //EN SEGUNDO PLANO
    private class asyncRecuperar extends AsyncTask<String, Float, Object> {

        String toast="";

        @Override
        protected Object doInBackground(String... params) {
            SW acc = new SW("ingresos.wsdl", "ingresoActividadInstaladorActividadSeleccionada");
            acc.asignarPropiedades(
                    new Tupla[]{
                            new Tupla<String, Object>("ide", params[0])
                    }
            );
            Object r = acc.ajecutar();
            try{
                return r;
            }catch (Exception e){
                toast = "Error, No se pudo cargar los datos requeridos";
                this.cancel(true);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object r) {
            super.onPostExecute(r);

            System.out.print(r);

            SoapObject data = (SoapObject)r;
            System.out.print(data);

            ArrayAdapter<String> ad = (ArrayAdapter<String>)spinerInstalador.getAdapter();
            int pos = ad.getPosition(data.getProperty(0).toString());
            spinerInstalador.setSelection(pos);

            ad = (ArrayAdapter<String>)spinerCuadrilla.getAdapter();
            pos = ad.getPosition(data.getProperty(1).toString());
            spinerInstalador.setSelection(pos);

            ad = (ArrayAdapter<String>)spinerSolicitud.getAdapter();
            pos = ad.getPosition(data.getProperty(2).toString());
            spinerSolicitud.setSelection(pos);

            String[] se = data.getProperty(3).toString().split("-");
            fecha.updateDate(Integer.parseInt(se[0]), Integer.parseInt(se[1])-1, Integer.parseInt(se[2]));

            String[] st = data.getProperty(4).toString().split(":");
            tiempo.setCurrentHour(Integer.parseInt(st[0]));
            tiempo.setCurrentMinute(Integer.parseInt(st[1]));


            SessionManagerIngreso.getManager(getActivity().getApplicationContext()).saveKey("IDACTIVIDADSELECCIONADA1","");


        }

        protected void onCancelled() {
            Toast t = Toast.makeText(
                    getActivity().getApplicationContext(),
                    toast,
                    Toast.LENGTH_SHORT
            );
            t.show();

        }
    }

}
