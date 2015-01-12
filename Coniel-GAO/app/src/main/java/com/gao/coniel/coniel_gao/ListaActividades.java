package com.gao.coniel.coniel_gao;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import clases.AdaptadorListaActividades;
import clases.ItemListaActividades;


public class ListaActividades extends android.support.v4.app.Fragment {

    ListView listView;
    ArrayList<ItemListaActividades> listaActividades;

    // Creamos un adapter personalizado
    AdaptadorListaActividades adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_lista_actividades, container, false);

        listView = (ListView) v.findViewById(R.id.listActividadesHoy);
        listaActividades = new ArrayList<ItemListaActividades>();

        // Al adapter personalizado le pasamos el contexto y la lista que contiene
        // Añadimos el adapter al listview
        adapter = new AdaptadorListaActividades(getActivity(), listaActividades);
        listView.setAdapter(adapter);

        listaActividades.add(new ItemListaActividades("Andrea", "Loaiza", "Andrea", "David"));
        listaActividades.add(new ItemListaActividades("Andrea", "David", "Andrea", "David"));

        return v;
    }


}
