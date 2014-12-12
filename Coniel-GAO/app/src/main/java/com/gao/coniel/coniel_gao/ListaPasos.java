package com.gao.coniel.coniel_gao;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import clases.Pasos;


public class ListaPasos extends android.support.v4.app.Fragment {

    private android.support.v4.app.Fragment[] fragmentos = new android.support.v4.app.Fragment[2];
    private OnPasoSelectedListener listener;
    String c,n;
    ListView listView;
    ArrayList<Pasos> listaPasos;

    // Creamos un adapter personalizado
    ListaPasosAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_lista_pasos, container, false);
        fragmentos [0] = new IngresoDatosAbonado();
       // fragmentos [1] = new PasoDetalleInstalacion();

       listView = (ListView) v.findViewById(R.id.lista);
        //setListAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, valores));
        listaPasos = new ArrayList<Pasos>();
        // Al adapter personalizado le pasamos el contexto y la lista que contiene
        // Añadimos el adapter al listview
        adapter = new ListaPasosAdapter(getActivity(), listaPasos);
        listView.setAdapter(adapter);
        //setListAdapter(adapter);
        listaPasos.add(new Pasos(getResources().getDrawable(R.drawable.ic_usuario), "Datos de Abonado", "Digitar o Consultar datos requeridos"));
        listaPasos.add(new Pasos(getResources().getDrawable(R.drawable.ic_tools), "Detalle de Instalación", "Seleccionar o Digitar el detalle de la instalación"));
        listaPasos.add(new Pasos(getResources().getDrawable(R.drawable.ic_tools2), "Medidores", "Revisar o Digitar medidor"));
        listaPasos.add(new Pasos(getResources().getDrawable(R.drawable.ic_materiales), "Materiales", "Digitar el material utilizado"));


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int ide = (int)id;
                listener.OnPasoSelected(fragmentos[position]);
                Log.e("Item seleccionado", String.valueOf(+ide));
            }
        });
        return v;
    }

  /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }*/


    public interface OnPasoSelectedListener {
        public void OnPasoSelected(android.support.v4.app.Fragment id);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnPasoSelectedListener) activity;
        } catch (ClassCastException e) {}
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        int ide = (int)id;
        listener.OnPasoSelected(fragmentos[position]);
        Log.e("Item seleccionado", String.valueOf(+ide));
    }


}
