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
import android.widget.LinearLayout;
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
    ImageButton btnAgregarMaterial, btnAgregarSello;
    CheckBox checkDirecto, checkReubicacion, checkContrastacion;
    ListView listViewSellos, listViewMateriales;
    ArrayList<ContenidoSellos> contenidoSellos;
    ArrayList<ContenidoMaterialesLista> contenidoMaterialesLista;

    // Creamos un adapterSellos personalizado
    ListaContenidoSellosAdapter adapterSellos;
    ListaMaterialesAdapter adapterMateriales;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ingreso_materiales, container, false);

        spMateriales = (Spinner) view.findViewById(R.id.spinnerMateriales);
        edtCant = (NumberPicker) view.findViewById(R.id.edtCantidad);
        edtCant.setMaxValue(100);
        edtCant.setMinValue(1);
        edtCant.setValue(1);
        btnAgregarMaterial = (ImageButton) view.findViewById(R.id.btnAgregar);
        checkDirecto = (CheckBox) view.findViewById(R.id.checkdirecto);
        checkContrastacion = (CheckBox) view.findViewById(R.id.checkcontrastacion);
        checkReubicacion = (CheckBox) view.findViewById(R.id.checkreubicacion);
        spSellos = (Spinner) view.findViewById(R.id.spinnerSellos);
        spUbicacionSello = (Spinner) view.findViewById(R.id.spinnerUbicacionSello);
        btnAgregarSello = (ImageButton) view.findViewById(R.id.btnAgregarSello);
        listViewSellos = (ListView) view.findViewById(R.id.listaSellos);
        listViewMateriales = (ListView) view.findViewById(R.id.listMatAgregados);

        contenidoSellos = new ArrayList<ContenidoSellos>();
        contenidoMaterialesLista = new ArrayList<ContenidoMaterialesLista>();

        asyncLoad al = new asyncLoad();
        al.execute(
                SessionManager.getManager(getActivity()).getStringKey("contrato")
        );
        return view;
    }

    //El Fragment ha sido quitado de su Activity y ya no está disponible
    @Override
    public void onStop(){
        super.onStop();
        Log.i("Se ha ejecutado el ", "  ONSTOP");

        //Guardar variables
        SessionManagerIngreso s = SessionManagerIngreso.getManager(getActivity().getApplicationContext());
        try {
            s.saveKey("CHECKDIRECTO", checkDirecto.isChecked())
                    .saveKey("CHECKCONTRASTACION", checkContrastacion.isChecked())
                    .saveKey("CHECKREUBICACION", checkReubicacion.isChecked());

            ListaMaterialesAdapter ad = (ListaMaterialesAdapter) listViewMateriales.getAdapter();
            ArrayList<String[]> lista = null;
            //Log.i("Info-ListViewMateriales-Count", ad.getCount()+"");
            if (ad != null) {
                lista = new ArrayList<String[]>();
                for (int i = 0; i < ad.getCount(); i++)
                    lista.add(
                            new String[]{
                                    ad.getItem(i).getItemMateriales(),
                                    ad.getItem(i).getDescripcion(),
                                    ad.getItem(i).getCantidad()
                            }
                    );
            }
            s.saveKey("LISTAMATERIALES", lista);
            lista = null;

            ListaContenidoSellosAdapter ads = (ListaContenidoSellosAdapter) listViewSellos.getAdapter();
            if (ads != null) {
                lista = new ArrayList<String[]>();
                for (int i = 0; i < ads.getCount(); i++)
                    lista.add(
                            new String[]{
                                    ads.getItem(i).getDato1(),
                                    ads.getItem(i).getDato2(),
                                    ads.getItem(i).getDato3()
                            }
                    );
            }
            s.saveKey("LISTASELLOS", lista);
        }catch (Exception e){
           s.saveKey("CHECKDIRECTO", false)
            .saveKey("CHECKCONTRASTACION", false)
            .saveKey("CHECKREUBICACION", false)
            .saveKey("LISTAMATERIALES", new ArrayList<String[]>())
            .saveKey("LISTASELLOS", new ArrayList<String[]>());

            s.saveKey(
                    "IDACTIVIDADSELECCIONADA4",
                    s.getStringKey("IDACTIVIDADSELECCIONADA")
            );
        }
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
            recuperar();
            eventos();
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
            SW acc = new SW("ingresos.wsdl", "ingresoMaterialesSeleccionados");
            acc.asignarPropiedades(
                    new Tupla[]{
                            new Tupla<String, Object>("ide", params[0]),
                            new Tupla<String, Object>("cont", params[1])
                    }
            );
            Log.i("CONT: ",""+params[1]);
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

            SessionManagerIngreso s = SessionManagerIngreso.getManager(getActivity().getApplicationContext());

            try{
                SoapObject reubicacion = (SoapObject)data.getProperty(0);
                s.saveKey("CHECKREUBICACION", Boolean.parseBoolean(reubicacion.getProperty(0).toString()));

            }catch (Exception e){
                Log.e("Error al Cargar reubicacion: ",""+e);
            }

            try{
                SoapObject contrastacion = (SoapObject)data.getProperty(1);
                s.saveKey("CHECKCONTRASTACION", Boolean.parseBoolean(contrastacion.getProperty(0).toString()));
            }catch (Exception e){
                Log.e("Error al Cargar contrastacion: ",""+e);
            }

            try{
                SoapObject directo = (SoapObject)data.getProperty(2);
                s.saveKey("CHECKDIRECTO",Boolean.parseBoolean(directo.getProperty(0).toString()));
            }catch (Exception e){
                Log.e("Error al Cargar directo: ",""+e);
            }

            try{
                ArrayAdapter<String> dataAdapter = (ArrayAdapter<String>) spMateriales.getAdapter();
                SoapObject cntmate = (SoapObject)data.getProperty(3);
                SoapObject mate = (SoapObject)data.getProperty(4);
                ArrayList<String[]> lista = new ArrayList<String[]>();
                for (int i = 0; i < mate.getPropertyCount(); i++) {
                    lista.add(
                            new String[]{
                                    dataAdapter.getPosition(mate.getProperty(i).toString())+"",
                                    cntmate.getProperty(i).toString(),
                                    mate.getProperty(i).toString()
                            }
                    );
                }
                s.saveKey("LISTAMATERIALES", lista);
            }catch (Exception e){
                Log.e("Error al Cargar MATERIALES: ",""+e);
            }

            try{
                SoapObject ubisell = (SoapObject)data.getProperty(5);
                SoapObject sell = (SoapObject)data.getProperty(6);
                ArrayList<String[]> lista = new ArrayList<String[]>();
                for (int i = 0; i < sell.getPropertyCount(); i++) {
                    lista.add(
                            new String[]{
                                    "0",
                                    ubisell.getProperty(i).toString(),
                                    sell.getProperty(i).toString()
                            }
                    );
                }
                s.saveKey("LISTASELLOS", lista);
            }catch (Exception e){
                Log.e("Error al Cargar SELLOS: ",""+e);
            }

            SessionManagerIngreso.getManager(getActivity().getApplicationContext()).saveKey("IDACTIVIDADSELECCIONADA4","");
            recuperarDeArchivo();

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

    private void recuperarDeArchivo() {

        SessionManagerIngreso s = SessionManagerIngreso.getManager(getActivity().getApplicationContext());
        //Recuperar Variables de Sesion
        checkDirecto.setChecked(s.getBooleanKey("CHECKDIRECTO"));
        checkContrastacion.setChecked(s.getBooleanKey("CHECKCONTRASTACION"));
        checkReubicacion.setChecked(s.getBooleanKey("CHECKREUBICACION"));

        ArrayList<String[]> lista = s.getListKey("LISTAMATERIALES");

        if (lista != null) {
            ArrayAdapter<String> dataAdapter = (ArrayAdapter<String>) spMateriales.getAdapter();
            contenidoMaterialesLista.clear();
            for (String[] aLista1 : lista) {
                contenidoMaterialesLista.add(
                        new ContenidoMaterialesLista(
                                aLista1[0],
                                aLista1[1],
                                aLista1[2]
                        )
                );
                dataAdapter.remove(aLista1[2]);
            }

            adapterMateriales = new ListaMaterialesAdapter(getActivity(), contenidoMaterialesLista);
            listViewMateriales.setAdapter(adapterMateriales);
            listViewMateriales.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.FILL_PARENT,
                            contenidoMaterialesLista.size() * 50
                    )
            );
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spMateriales.setAdapter(dataAdapter);
        }

        lista = s.getListKey("LISTASELLOS");
        if (lista != null) {
            ArrayAdapter<String> dataAdapter = (ArrayAdapter<String>) spSellos.getAdapter();
            contenidoSellos.clear();
            for (String[] aLista : lista) {
                contenidoSellos.add(
                        new ContenidoSellos(
                                aLista[0],
                                aLista[1],
                                aLista[2]
                        )
                );
                dataAdapter.remove(aLista[1]);
            }

            adapterSellos = new ListaContenidoSellosAdapter(getActivity(), contenidoSellos);
            listViewSellos.setAdapter(adapterSellos);
            listViewSellos.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.FILL_PARENT,
                            contenidoSellos.size() * 50
                    )
            );
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spSellos.setAdapter(dataAdapter);
        }

    }


    private void eventos() {

        //Lista Sellos
        btnAgregarSello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    contenidoSellos.add(
                            new ContenidoSellos(
                                    ""+spSellos.getSelectedItemPosition(),
                                    spSellos.getSelectedItem().toString(),
                                    spUbicacionSello.getSelectedItem().toString()
                            )
                    );
                    adapterSellos = new ListaContenidoSellosAdapter(getActivity(), contenidoSellos);
                    listViewSellos.setAdapter(adapterSellos);
                    listViewSellos.setLayoutParams(
                            new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.FILL_PARENT,
                                    contenidoSellos.size() * 50
                            )
                    );
                    ArrayAdapter<String> dataAdapter = (ArrayAdapter<String>)spSellos.getAdapter();
                    dataAdapter.remove(spSellos.getSelectedItem().toString());
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spSellos.setAdapter(dataAdapter);
                }catch (Exception e){}
            }
        });

        //Lista Materiales
        btnAgregarMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    contenidoMaterialesLista.add(
                            new ContenidoMaterialesLista(
                                    "" + spMateriales.getSelectedItemPosition(),
                                    edtCant.getValue() + "",
                                    spMateriales.getSelectedItem().toString()
                            )
                    );
                    edtCant.setValue(1);
                    adapterMateriales = new ListaMaterialesAdapter(
                            getActivity(),
                            contenidoMaterialesLista
                    );
                    listViewMateriales.setAdapter(adapterMateriales);
                    listViewMateriales.setLayoutParams(
                            new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.FILL_PARENT,
                                    contenidoMaterialesLista.size() * 50
                            )
                    );

                    ArrayAdapter<String> dataAdapter = (ArrayAdapter<String>) spMateriales.getAdapter();
                    dataAdapter.remove(spMateriales.getSelectedItem().toString());
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spMateriales.setAdapter(dataAdapter);
                }catch (Exception e){}
            }
        });

    }

    private void recuperar() {
        try {
            SessionManagerIngreso s = SessionManagerIngreso.getManager(getActivity().getApplicationContext());

            if (!((s.getStringKey("IDACTIVIDADSELECCIONADA4") + "").equals(""))) {

                asyncRecuperar asb = new asyncRecuperar();
                asb.execute(
                        s.getStringKey("IDACTIVIDADSELECCIONADA") + "",
                        SessionManager.getManager(getActivity()).getStringKey("contrato")
                );

            } else {

                recuperarDeArchivo();

            }
        }catch (Exception e){
            Log.e("Recuperar","Error, no se pudo recuperar: "+e.getMessage());
        }
    }


    // Metodo Agregar datos a Spinner
    public void addDynamic(Spinner sp, ArrayList<String> dynamicList){

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, dynamicList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(dataAdapter);

    }

}
