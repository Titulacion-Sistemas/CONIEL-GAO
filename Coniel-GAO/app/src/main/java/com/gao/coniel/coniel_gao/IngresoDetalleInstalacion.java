package com.gao.coniel.coniel_gao;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

import clases.SessionManagerIngreso;
import clases.Tupla;
import serviciosWeb.SW;


public class IngresoDetalleInstalacion extends android.support.v4.app.Fragment {

    Spinner spMaterialRed, spFormaConexion, spEstadoInst, spTipoConst, spUbicacionMed,
            spTipoAcometida, spCalibreRed, spTipoServicio, spUsoInmueble, spDemanda,
            spNivelSocioEconomico, spUsoDeEnergia, spClaseDeRed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ingreso_detalle_instalacion, container, false);

        spMaterialRed = (Spinner) view.findViewById(R.id.spinnerMaterialRed);
        spFormaConexion = (Spinner) view.findViewById(R.id.spinnerFormaConexion);
        spEstadoInst = (Spinner) view.findViewById(R.id.spinnerEstadoInstalacion);
        spTipoConst = (Spinner) view.findViewById(R.id.spinnerTipoConst);
        spUbicacionMed = (Spinner) view.findViewById(R.id.spinnerUbicacionMedidor);
        spTipoAcometida = (Spinner) view.findViewById(R.id.spinnerTipoAcom);
        spCalibreRed = (Spinner) view.findViewById(R.id.spinnerCalibreRed);
        spTipoServicio = (Spinner) view.findViewById(R.id.spinnerTipoServicio);
        spUsoInmueble = (Spinner) view.findViewById(R.id.spinnerUsoInmueble);
        spDemanda = (Spinner) view.findViewById(R.id.spinnerDemanda);
        spNivelSocioEconomico = (Spinner) view.findViewById(R.id.spinnerNivelSocioeconomico);
        spUsoDeEnergia = (Spinner) view.findViewById(R.id.spinnerUsoEnergia);
        spClaseDeRed = (Spinner) view.findViewById(R.id.spinnerClaseRed);

        asyncLoad al = new asyncLoad();
        al.execute();

        return view;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i("Se ha ejecutado el ", "  ONATTACH del detalle");
    }

    //El Fragment ha sido quitado de su Activity y ya no está disponible
    @Override
    public void onStop(){
        super.onStop();
        Log.i("Se ha ejecutado el ", "  ONSTOP");
        //Guardar Sesion para evitar cierre
        SessionManagerIngreso.getManager(getActivity().getApplicationContext())
                .saveKey("MATERIALRED", spMaterialRed.getSelectedItemPosition())
                .saveKey("FORMACONEXION", spFormaConexion.getSelectedItemPosition())
                .saveKey("ESTADOINST", spEstadoInst.getSelectedItemPosition())
                .saveKey("TIPOCONST", spTipoConst.getSelectedItemPosition())
                .saveKey("UBICACIONMED", spUbicacionMed.getSelectedItemPosition())
                .saveKey("TIPOACOMETIDA", spTipoAcometida.getSelectedItemPosition())
                .saveKey("CALIBRERED", spCalibreRed.getSelectedItemPosition())
                .saveKey("TIPOSERVICIO", spTipoServicio.getSelectedItemPosition())
                .saveKey("USOINMUEBLE", spUsoInmueble.getSelectedItemPosition())
                .saveKey("DEMANDA", spDemanda.getSelectedItemPosition())
                .saveKey("NIVELSOCIO", spNivelSocioEconomico.getSelectedItemPosition())
                .saveKey("USOENERGIA", spUsoDeEnergia.getSelectedItemPosition())
                .saveKey("CLASERED", spClaseDeRed.getSelectedItemPosition());
    }




    //EN SEGUNDO PLANO
    private class asyncLoad extends AsyncTask<String, Float, Object> {

        String toast="";

        @Override
        protected Object doInBackground(String... params) {
            SW acc = new SW("ingresos.wsdl", "ingresoDetalleInstalacion");
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

            SoapObject data = (SoapObject)r;
            Log.i("Info-Retorno", data.toString());

            //ArrayList<ArrayList<String>> valores = new ArrayList<ArrayList<String>>();
            for (int i=0 ; i<data.getPropertyCount() ; i++){
                ArrayList<String> tmp = new ArrayList<String>();
                for (int j=0 ; j<((SoapObject)data.getProperty(i)).getPropertyCount() ; j++){
                    tmp.add(""+(((SoapObject)data.getProperty(i)).getProperty(j)));
                }
                //valores.add(tmp);
                switch (i){
                    case 0:
                        try{
                            addDynamic(spMaterialRed, tmp);
                        }catch (Exception e){
                            Log.e("Error al Cargar spMaterialRed: ",""+e);
                        }
                    case 1:
                        try{
                            addDynamic(spFormaConexion, tmp);
                        }catch (Exception e){
                            Log.e("Error al Cargar spFormaConexion: ",""+e);
                        }
                    case 2:
                        try{
                            addDynamic(spEstadoInst, tmp);
                        }catch (Exception e){
                            Log.e("Error al Cargar spEstadoInst: ",""+e);
                        }
                    case 3:
                        try{
                            addDynamic(spTipoConst, tmp);
                        }catch (Exception e){
                            Log.e("Error al Cargar spTipoConst: ",""+e);
                        }
                    case 4:
                        try{
                            addDynamic(spUbicacionMed, tmp);
                        }catch (Exception e){
                            Log.e("Error al Cargar spUbicacionMed: ",""+e);
                        }
                    case 5:
                        try{
                            addDynamic(spTipoAcometida, tmp);
                        }catch (Exception e){
                            Log.e("Error al Cargar spTipoAcometida: ",""+e);
                        }
                    case 6:
                        try{
                            addDynamic(spCalibreRed, tmp);
                        }catch (Exception e){
                            Log.e("Error al Cargar spCalibreRed: ",""+e);
                        }
                    case 7:
                        try{
                            addDynamic(spUsoDeEnergia, tmp);
                        }catch (Exception e){
                            Log.e("Error al Cargar spUsoDeEnergia: ",""+e);
                        }
                    case 8:
                        try{
                            addDynamic(spClaseDeRed, tmp);
                        }catch (Exception e){
                            Log.e("Error al Cargar spClaseDeRed: ",""+e);
                        }
                    case 9:
                        try{
                            addDynamic(spTipoServicio, tmp);
                        }catch (Exception e){
                            Log.e("Error al Cargar spTipoServicio: ",""+e);
                        }
                    case 10:
                        try{
                            addDynamic(spUsoInmueble, tmp);
                        }catch (Exception e){
                            Log.e("Error al Cargar spUsoInmueble: ",""+e);
                        }
                    case 11:
                        try{
                            addDynamic(spDemanda, tmp);
                        }catch (Exception e){
                            Log.e("Error al Cargar spDemanda: ",""+e);
                        }
                    case 12:
                        try{
                            addDynamic(spNivelSocioEconomico, tmp);
                        }catch (Exception e){
                            Log.e("Error al Cargar spNivelSocioEconomico: ",""+e);
                        }
                }

                recuperar();
            }

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

    private class asyncRecuperar extends AsyncTask<String, Float, Object> {

        String toast="";

        @Override
        protected Object doInBackground(String... params) {
            SW acc = new SW("ingresos.wsdl", "ingresoDatosAbonadoSeleccionado");
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
            ArrayAdapter<String> ad=null;
            int pos=0;
            try{
                ad = (ArrayAdapter<String>)spMaterialRed.getAdapter();
                pos = ad.getPosition(data.getProperty(0).toString());
                spMaterialRed.setSelection(pos);
            }catch (Exception e){
                Log.e("Error al Cargar spMaterialRed: ",""+e);
            }

            try{
                ad = (ArrayAdapter<String>)spFormaConexion.getAdapter();
                pos = ad.getPosition(data.getProperty(1).toString());
                spFormaConexion.setSelection(pos);
            }catch (Exception e){
                Log.e("Error al Cargar spFormaConexion: ",""+e);
            }

            try{
                ad = (ArrayAdapter<String>)spEstadoInst.getAdapter();
                pos = ad.getPosition(data.getProperty(2).toString());
                spEstadoInst.setSelection(pos);
            }catch (Exception e){
                Log.e("Error al Cargar spEstadoInst: ",""+e);
            }

            try{
                ad = (ArrayAdapter<String>)spTipoConst.getAdapter();
                pos = ad.getPosition(data.getProperty(3).toString());
                spTipoConst.setSelection(pos);
            }catch (Exception e){
                Log.e("Error al Cargar spTipoConst: ",""+e);
            }

            try{
                ad = (ArrayAdapter<String>)spUbicacionMed.getAdapter();
                pos = ad.getPosition(data.getProperty(4).toString());
                spUbicacionMed.setSelection(pos);
            }catch (Exception e){
                Log.e("Error al Cargar spUbicacionMed: ",""+e);
            }

            try{
                ad = (ArrayAdapter<String>)spTipoAcometida.getAdapter();
                pos = ad.getPosition(data.getProperty(5).toString());
                spTipoAcometida.setSelection(pos);
            }catch (Exception e){
                Log.e("Error al Cargar spTipoAcometida: ",""+e);
            }

            try{
                ad = (ArrayAdapter<String>)spCalibreRed.getAdapter();
                pos = ad.getPosition(data.getProperty(6).toString());
                spCalibreRed.setSelection(pos);
            }catch (Exception e){
                Log.e("Error al Cargar spCalibreRed: ",""+e);
            }

            try{
                ad = (ArrayAdapter<String>)spUsoDeEnergia.getAdapter();
                pos = ad.getPosition(data.getProperty(7).toString());
                spUsoDeEnergia.setSelection(pos);
            }catch (Exception e){
                Log.e("Error al Cargar spUsoDeEnergia: ",""+e);
            }

            try{
                ad = (ArrayAdapter<String>)spClaseDeRed.getAdapter();
                pos = ad.getPosition(data.getProperty(8).toString());
                spClaseDeRed.setSelection(pos);
            }catch (Exception e){
                Log.e("Error al Cargar spClaseDeRed: ",""+e);
            }

            try{
                ad = (ArrayAdapter<String>)spTipoServicio.getAdapter();
                pos = ad.getPosition(data.getProperty(9).toString());
                spTipoServicio.setSelection(pos);
            }catch (Exception e){
                Log.e("Error al Cargar spTipoServicio: ",""+e);
            }

            try{
                ad = (ArrayAdapter<String>)spUsoInmueble.getAdapter();
                pos = ad.getPosition(data.getProperty(10).toString());
                spUsoInmueble.setSelection(pos);
            }catch (Exception e){
                Log.e("Error al Cargar spUsoInmueble: ",""+e);
            }

            try{
                ad = (ArrayAdapter<String>)spDemanda.getAdapter();
                pos = ad.getPosition(data.getProperty(11).toString());
                spDemanda.setSelection(pos);
            }catch (Exception e){
                Log.e("Error al Cargar spDemanda: ",""+e);
            }

            try{
                ad = (ArrayAdapter<String>)spNivelSocioEconomico.getAdapter();
                pos = ad.getPosition(data.getProperty(12).toString());
                spNivelSocioEconomico.setSelection(pos);
            }catch (Exception e){
                Log.e("Error al Cargar spNivelSocioEconomico: ",""+e);
            }

            SessionManagerIngreso.getManager(getActivity().getApplicationContext()).saveKey("IDACTIVIDADSELECCIONADA3","");

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

        if (!((s.getStringKey("IDACTIVIDADSELECCIONADA3")+"").equals(""))){

            asyncRecuperar asb = new asyncRecuperar();
            asb.execute(
                    s.getStringKey("IDACTIVIDADSELECCIONADA")+""
            );

        }
        else {

            spMaterialRed.setSelection(s.getIntKey("MATERIALRED"));
            spFormaConexion.setSelection(s.getIntKey("FORMACONEXION"));
            spEstadoInst.setSelection(s.getIntKey("ESTADOINST"));
            spTipoConst.setSelection(s.getIntKey("TIPOCONST"));
            spUbicacionMed.setSelection(s.getIntKey("UBICACIONMED"));
            spTipoAcometida.setSelection(s.getIntKey("TIPOACOMETIDA"));
            spCalibreRed.setSelection(s.getIntKey("CALIBRERED"));
            spTipoServicio.setSelection(s.getIntKey("TIPOSERVICIO"));
            spUsoInmueble.setSelection(s.getIntKey("USOINMUEBLE"));
            spDemanda.setSelection(s.getIntKey("DEMANDA"));
            spNivelSocioEconomico.setSelection(s.getIntKey("NIVELSOCIO"));
            spUsoDeEnergia.setSelection(s.getIntKey("USOENERGIA"));
            spClaseDeRed.setSelection(s.getIntKey("CLASERED"));
        }

    }


    // Metodo Agregar datos a Spinner
    public void addDynamic(Spinner sp, ArrayList<String> dynamicList){

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, dynamicList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(dataAdapter);

    }


}
