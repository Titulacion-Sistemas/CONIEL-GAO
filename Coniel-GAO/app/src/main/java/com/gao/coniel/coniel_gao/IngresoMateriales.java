package com.gao.coniel.coniel_gao;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

import clases.ContenidoMaterialesLista;
import clases.ContenidoSellos;
import clases.ListaContenidoSellosAdapter;
import clases.ListaMaterialesAdapter;
import clases.SessionManager;
import clases.SessionManagerIngreso;
import clases.Tupla;
import serviciosWeb.SW;


public class IngresoMateriales extends Fragment {

    Spinner spMateriales,spSellos, spUbicacionSello;
    NumberPicker edtCant;
    ImageButton btnAgregar, btnAgregarSello;
    ListView listaMat, listaSello;
    CheckBox checkDirecto, checkReubicacion, checkContrastacion;
    ListView listView, listViewMateriales;
    ArrayList<ContenidoSellos> contenidoSellos;
    ArrayList<ContenidoMaterialesLista> contenidoMaterialesLista;

    // Creamos un adapter personalizado
    ListaContenidoSellosAdapter adapter;
    ListaMaterialesAdapter adapterMateriales;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ingreso_materiales, container, false);

        spMateriales = (Spinner) view.findViewById(R.id.spinnerMateriales);
        edtCant = (NumberPicker) view.findViewById(R.id.edtCantidad);
        edtCant.setMaxValue(100);
        edtCant.setMinValue(1);
        edtCant.setValue(1);
        btnAgregar = (ImageButton) view.findViewById(R.id.btnAgregar);
        //listaMat = (ListView) view.findViewById(R.id.listMatAgregados);
        checkDirecto = (CheckBox) view.findViewById(R.id.checkdirecto);
        checkContrastacion = (CheckBox) view.findViewById(R.id.checkcontrastacion);
        checkReubicacion = (CheckBox) view.findViewById(R.id.checkreubicacion);
        spSellos = (Spinner) view.findViewById(R.id.spinnerSellos);
        spUbicacionSello = (Spinner) view.findViewById(R.id.spinnerUbicacionSello);
        btnAgregarSello = (ImageButton) view.findViewById(R.id.btnAgregarSello);
        //listaSello = (ListView) view.findViewById(R.id.listaSellos);

      //Guardar Variables de Sesion
        SessionManagerIngreso s = SessionManagerIngreso.getManager(getActivity().getApplicationContext());
        String selection = s.getIntKey("MEDIDORES")+"";
        if (!selection.equals("")){
            spMateriales.setSelection(Integer.parseInt(selection));
        }
        try {
            edtCant.setValue(s.getStringKey("CANTIDAD").equals("") ? 1 : Integer.parseInt(s.getStringKey("CANTIDAD")));
        }catch (Exception ignored){}

        checkDirecto.setChecked(s.getBooleanKey("CHECKDIRECTO"));
        checkContrastacion.setChecked(s.getBooleanKey("CHECKCONTRASTACION"));
        checkReubicacion.setChecked(s.getBooleanKey("CHECKREUBICACION"));
        spSellos.setSelection(s.getIntKey("SELLOS"));
        spUbicacionSello.setSelection(s.getIntKey("UBICACIONSELLO"));
        //FALTA EL SET DE LA LISTA


        //Lista Sellos
        listView = (ListView) view.findViewById(R.id.listaSellos);
        contenidoSellos = new ArrayList<ContenidoSellos>();

        // Al adapter personalizado le pasamos el contexto y la lista que contiene
        // Añadimos el adapter al listview
        adapter = new ListaContenidoSellosAdapter(getActivity(), contenidoSellos);
        listView.setAdapter(adapter);

        contenidoSellos.add(new ContenidoSellos("Andrea", "Loaiza", "Gonzaga"));
        contenidoSellos.add(new ContenidoSellos("Boba", "Burra", "No sabe nada"));

        //Lista Materiales
        listViewMateriales = (ListView) view.findViewById(R.id.listMatAgregados);
        contenidoMaterialesLista = new ArrayList<ContenidoMaterialesLista>();

        // Al adapter personalizado le pasamos el contexto y la lista que contiene
        // Añadimos el adapter al listview
        adapterMateriales = new ListaMaterialesAdapter(getActivity(), contenidoMaterialesLista);
        listViewMateriales.setAdapter(adapterMateriales);

        contenidoMaterialesLista.add(new ContenidoMaterialesLista("Andrea", "Loaiza", "Gonzaga"));
        contenidoMaterialesLista.add(new ContenidoMaterialesLista("Boba", "Burra", "No sabe nada"));

        asyncLoad al = new asyncLoad();
        al.execute(
                SessionManager.getManager(getActivity().getApplicationContext()).getStringKey("contrato")
        );

        return view;
    }

    //El Fragment ha sido quitado de su Activity y ya no está disponible
    @Override
    public void onStop(){
        super.onStop();
        Log.i("Se ha ejecutado el ", "  ONSTOP");

        //Guardar variables
        SessionManagerIngreso s = SessionManagerIngreso.getManager(getActivity().getApplicationContext())
                .saveKey("MEDIDORES", spMateriales.getSelectedItemPosition())
                .saveKey("CANTIDAD", edtCant.getValue()+"")
                .saveKey("CHECKDIRECTO", checkDirecto.isChecked())
                .saveKey("CHECKCONTRASTACION", checkContrastacion.isChecked())
                .saveKey("CHECKREUBICACION", checkReubicacion.isChecked());
                //.saveKey("SELLOS", spSellos.getSelectedItemPosition());



    }



    //EN SEGUNDO PLANO
    private class asyncLoad extends AsyncTask<String, Float, Object> {

        String toast="";

        @Override
        protected Object doInBackground(String... params) {
            SW acc = new SW("ingresos.wsdl", "ingresoMateriales");
            acc.asignarPropiedades(
                    new Tupla[]{
                            new Tupla<String, Object>("contrato", params[0])
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
                            addDynamic(spMateriales, tmp);
                        }catch (Exception e){
                            Log.e("Error al Cargar spMateriales: ",""+e);
                        }
                    case 1:
                        try{
                            addDynamic(spSellos, tmp);
                        }catch (Exception e){
                            Log.e("Error al Cargar spSellos: ",""+e);
                        }
                    case 2:
                        try{
                            addDynamic(spUbicacionSello, tmp);
                        }catch (Exception e){
                            Log.e("Error al Cargar spUbicacionSello: ",""+e);
                        }

                }
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


    // Metodo Agregar datos a Spinner
    public void addDynamic(Spinner sp, ArrayList<String> dynamicList){

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, dynamicList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(dataAdapter);

    }




}
