package com.gao.coniel.coniel_gao;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

import clases.SessionManager;
import clases.SessionManagerIngreso;
import clases.Tupla;
import serviciosWeb.SW;


public class IngresoMedidorInstalado extends Fragment {
    String id ="";
    Spinner spMedidoresBodega;
    EditText edtFabricaBodega, edtSerieBodega, edtMarcaBodega, edtTipoBodega, edtLecturaBodega;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ingreso_medidor_instalado, container, false);

        spMedidoresBodega = (Spinner) view.findViewById(R.id.spinnerMedidoresBodega);
        edtFabricaBodega = (EditText) view.findViewById(R.id.edtFabricaBodega);
        edtSerieBodega = (EditText) view.findViewById(R.id.edtSerieBodega);
        edtMarcaBodega = (EditText) view.findViewById(R.id.edtMarcaBodega);
        edtTipoBodega = (EditText) view.findViewById(R.id.edtTipoBodega);
        edtLecturaBodega = (EditText) view.findViewById(R.id.edtLecturaBodega);

        asyncLoad al = new asyncLoad();
        al.execute(
                SessionManager.getManager(getActivity().getApplicationContext()).getStringKey("contrato")
        );

        spMedidoresBodega.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleccion = spMedidoresBodega.getItemAtPosition(position).toString();
                Log.i("Info - medidor Seleccionado", seleccion);
                asyncSeleccion as = new asyncSeleccion();
                as.execute(
                    SessionManager.getManager(getActivity().getApplicationContext()).getStringKey("contrato"),
                    seleccion
                );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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
                .saveKey("MEDIDORESBODEGA", spMedidoresBodega.getSelectedItemPosition())
                .saveKey("NUMFABRICABODEGA", edtFabricaBodega.getText().toString())
                .saveKey("SERIEBODEGA", edtSerieBodega.getText().toString())
                .saveKey("MARCABODEGA", edtMarcaBodega.getText().toString())
                .saveKey("TIPOBODEGA", edtTipoBodega.getText().toString())
                .saveKey("IDMEDIDORSELECCIONADO",id)
                .saveKey("LECTURABODEGA", edtLecturaBodega.getText().toString());
    }



    //EN SEGUNDO PLANO
    private class asyncLoad extends AsyncTask<String, Float, Object> {

        String toast="";

        @Override
        protected Object doInBackground(String... params) {
            SW acc = new SW("ingresos.wsdl", "ingresoMedidorInstalado");
            acc.asignarPropiedades(
                    new Tupla[]{
                            new Tupla<String, Object>("contrato", params[0])
                    }
            );
            Object r = acc.ajecutar();
            try{
                SoapObject data = (SoapObject)r;
                Log.i("Info-Retorno", data.toString());

                ArrayList<String> valores = new ArrayList<String>();
                for (int i=0 ; i<data.getPropertyCount() ; i++){
                    valores.add(data.getProperty(i).toString());
                }
                if (valores.size()>0)
                    return valores;
            }catch (Exception ignored){}
            toast = "Error, No se pudo cargar los datos requeridos";
            this.cancel(true);
            return null;
        }

        @Override
        protected void onPostExecute(Object r) {
            super.onPostExecute(r);

            ArrayList<String> valores = (ArrayList<String>)r;

            if (valores != null)
                try{
                    addDynamic(spMedidoresBodega, valores);
                }catch (Exception e){
                    Log.e("Error al Cargar spMedidoresBodega: ",""+e);
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
        //Recuperar Variables de Sesion
        SessionManagerIngreso s = SessionManagerIngreso.getManager(getActivity().getApplicationContext());
        spMedidoresBodega.setSelection(s.getIntKey("MEDIDORESBODEGA"));
        edtFabricaBodega.setText(s.getStringKey("NUMFABRICABODEGA"));
        edtSerieBodega.setText(s.getStringKey("SERIEBODEGA"));
        edtMarcaBodega.setText(s.getStringKey("MARCABODEGA"));
        edtTipoBodega.setText(s.getStringKey("TIPOBODEGA"));
        edtLecturaBodega.setText(s.getStringKey("LECTURABODEGA"));
    }


    // Metodo Agregar datos a Spinner
    public void addDynamic(Spinner sp, ArrayList<String> dynamicList){

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, dynamicList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(dataAdapter);

    }



    private class asyncSeleccion extends AsyncTask<String, Float, Object> {

        String toast="";

        @Override
        protected Object doInBackground(String... params) {
            SW acc = new SW("ingresos.wsdl", "ingresoMedidorInstaladoSeleccionado");
            acc.asignarPropiedades(
                    new Tupla[]{
                            new Tupla<String, Object>("contrato", params[0]),
                            new Tupla<String, Object>("med", params[1])
                    }
            );
            Object r = acc.ajecutar();
            try{
                SoapObject data = (SoapObject)r;
                Log.i("Info-Retorno", data.toString());

                ArrayList<String> valores = new ArrayList<String>();
                for (int i=0 ; i<data.getPropertyCount() ; i++){
                    valores.add(data.getProperty(i).toString());
                }
                if (valores.size()>0)
                    return valores;
            }catch (Exception ignored){}
            toast = "Error, No se pudo cargar los datos requeridos";
            this.cancel(true);
            return null;
        }

        @Override
        protected void onPostExecute(Object r) {
            super.onPostExecute(r);

            ArrayList<String> valores = (ArrayList<String>)r;

            if (valores != null){
                edtFabricaBodega.setText(valores.get(1));
                edtSerieBodega.setText(valores.get(2));
                edtMarcaBodega.setText(valores.get(3));
                edtTipoBodega.setText(valores.get(4));
                id=valores.get(0);
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




}
