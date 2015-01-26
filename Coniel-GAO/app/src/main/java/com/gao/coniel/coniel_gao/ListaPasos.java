package com.gao.coniel.coniel_gao;

import android.app.ActionBar;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

import clases.Pasos;
import clases.SessionManagerIngreso;
import serviciosWeb.SWAct;


public class ListaPasos extends android.support.v4.app.Fragment {

    private android.support.v4.app.Fragment[] fragmentos = new android.support.v4.app.Fragment[6];
    private OnPasoSelectedListener listener;
    ListView listView, listViewActividades;
    ArrayList<Pasos> listaPasos;

    // Creamos un adapterSellos personalizado
    ListaPasosAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_lista_pasos, container, false);

        SessionManagerIngreso sessionManagerIngreso = SessionManagerIngreso.getManager(getActivity().getApplicationContext());
        int solicitud = sessionManagerIngreso.getIntKey("SOLICITUD");
        int idSol = Integer.parseInt(sessionManagerIngreso.getStringKey("IDSOLICITUD"));

        fragmentos [0] = new IngresoActividadInstalador();
        fragmentos [1] = new IngresoDatosAbonado();
        fragmentos [2] = new IngresoDetalleInstalacion();
        fragmentos [3] = new IngresoMateriales();
        if(!(solicitud==1 || idSol==11))
            fragmentos [4] = new IngresoMedidorInstalado();
        if (solicitud==0 || idSol==1)
            fragmentos [5] = new IngresosReferencias();


        TextView text = (TextView)v.findViewById(R.id.texto);
        TextView desc = (TextView)v.findViewById(R.id.desc);



        desc.setText("( Actividad Nueva )");
        if(solicitud==0 || idSol==1){
            text.setText("Servicio Nuevo");
            if(!sessionManagerIngreso.getStringKey("IDACTIVIDADSELECCIONADA").equals(""))
                desc.setText("Editando Actividad");

        }else if (solicitud==1 || idSol==11){
            text.setText("Cambio de Materiales");
            if(!sessionManagerIngreso.getStringKey("IDACTIVIDADSELECCIONADA").equals(""))
                desc.setText(""+sessionManagerIngreso.getStringKey("CUENTA"));

        }else if (solicitud==2 || idSol==13){
            text.setText("Cambio de Medidor");
            if(!sessionManagerIngreso.getStringKey("IDACTIVIDADSELECCIONADA").equals(""))
                desc.setText(""+sessionManagerIngreso.getStringKey("CUENTA"));
        }

        listView = (ListView) v.findViewById(R.id.lista);
        listViewActividades = (ListView) v.findViewById(R.id.listaActividades);
        //setListAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, valores));
        listaPasos = new ArrayList<Pasos>();
        // Al adapterSellos personalizado le pasamos el contexto y la lista que contiene
        // Añadimos el adapterSellos al listview
        adapter = new ListaPasosAdapter(getActivity(), listaPasos);
        listView.setAdapter(adapter);
        //setListAdapter(adapterSellos);
        listaPasos.add(new Pasos(getResources().getDrawable(R.drawable.ic_nueva), "Actividad a realizar - Instalador encargado", "Seleccionar la actividad a realizar e instalador encargado"));
        listaPasos.add(new Pasos(getResources().getDrawable(R.drawable.ic_usu_act), "Datos de Abonado", "Digitar o Consultar datos requeridos"));
        listaPasos.add(new Pasos(getResources().getDrawable(R.drawable.ic_detalle), "Detalle de Instalación", "Seleccionar o Digitar el detalle de la instalación"));
        listaPasos.add(new Pasos(getResources().getDrawable(R.drawable.ic_materiales), "Materiales", "Digitar el material utilizado"));
        if (!(solicitud==1 || idSol==11))
            listaPasos.add(new Pasos(getResources().getDrawable(R.drawable.ic_medidor), "Medidor Instalado", "Digitar el medidor instalado"));
        if (solicitud==0 || idSol==1)
            listaPasos.add(new Pasos(getResources().getDrawable(R.drawable.ic_referencia), "Referencias", "Digitar nro° de medidor de referencia"));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int ide = (int)id;
                listener.OnPasoSelected(fragmentos[position]);
                Log.e("Item seleccionado", String.valueOf(+ide));
            }
        });

        listaPasos = new ArrayList<Pasos>();
        // Al adapterSellos personalizado le pasamos el contexto y la lista que contiene
        // Añadimos el adapterSellos al listview
        adapter = new ListaPasosAdapter(getActivity(), listaPasos);
        listViewActividades.setAdapter(adapter);
        //setListAdapter(adapterSellos);
        listaPasos.add(new Pasos(getResources().getDrawable(R.drawable.ic_action_copy), "Actividad Realizadas por el usuario", "Seleccionar una actividad para modificar su contenido"));

        listViewActividades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //int ide = (int)id;
                listener.OnPasoSelected(new ListaActividades());
                Log.e("Item seleccionado", "Lista de Actividades...");
            }
        });

        Button guar = (Button)v.findViewById(R.id.btnGuardar);
        guar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asyncLoad a = new asyncLoad();
                a.execute();
            }
        });

        return v;
    }


    public interface OnPasoSelectedListener {
        public void OnPasoSelected(android.support.v4.app.Fragment id);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnPasoSelectedListener) activity;
        } catch (ClassCastException ignored) {}
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        int ide = (int)id;
        listener.OnPasoSelected(fragmentos[position]);
        Log.e("Item seleccionado", String.valueOf(+ide));
    }





    //EN SEGUNDO PLANO
    private class asyncLoad extends AsyncTask<String, Float, Object> {

        String toast="";

        @Override
        protected Object doInBackground(String... params) {
            SoapObject  prueba  = new SoapObject();
            SoapObject  dentro  = new SoapObject();
            dentro.addProperty("name","Jhonsson");
            dentro.addProperty("name1","Jhonsson");
            prueba.addProperty("name", dentro);
            PropertyInfo p = new PropertyInfo();
            p.setValue(prueba);
            p.setName("arr");

            SWAct acc = new SWAct("ingresos.wsdl", "prueba_array");
            acc.asignarPropiedades(
                    p
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


    @Override
    public void onDetach() {
        super.onDetach();

        ActionBar bar = getActivity().getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        bar.removeAllTabs();
        bar.setTitle(R.string.title_activity_contenedor);
        bar.setSubtitle(" Gestión de Actividades Operativas ");
        try {
            Contenedor c = ((Contenedor)getActivity());
            c.getmDrawerList().setItemChecked(0, true);
            c.getmDrawerList().setSelection(0);
            c.setTitle(R.string.title_activity_contenedor);
        }catch (Exception ignored){}

        Log.i("Información", "Dtach de ListaPasos");
    }

}
