package com.gao.coniel.coniel_gao;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import clases.Medidor;


public class BusquedaDatosMedidor extends Fragment {

    private Medidor[] medidors=null;
    ListView listaContenido, listaCabecera;
    View viewRows;

    ArrayList<HashMap<String, String>> miLista, miListaCabecera;

    ListAdapter adapterCabecera, adapterContenido;

    HashMap<String, String> map1, map2;

    public BusquedaDatosMedidor(Medidor[] medidors){
        this.medidors = medidors;
    }
    public BusquedaDatosMedidor(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_busqueda_datos_medidor, container, false);

        listaCabecera = (ListView) rootView.findViewById(R.id.listaCabeceraMedidor);
        listaContenido = (ListView) rootView.findViewById(R.id.listaContenidoMedidor);

        if (medidors!=null)
        rellenar();


        return rootView;
    }

    public void rellenar() {

        miLista = new ArrayList<HashMap<String, String>>();
        miListaCabecera = new ArrayList<HashMap<String, String>>();

        /**********Display the headings************/

        map1 = new HashMap<String, String>();

        map1.put("tipo", "Tipo Med.");
        map1.put("numero", " Nro. Med");
        map1.put("marca", " Marca");
        map1.put("fecha", "Fecha Inst.");

        miListaCabecera.add(map1);

        try {

            adapterCabecera= new SimpleAdapter(getActivity().getApplicationContext(), miListaCabecera, R.layout.rowsmedidores,
                    new String[]{"tipo", "numero", "marca", "fecha"}, new int[]{
                    R.id.textViewDato1, R.id.textViewDato2, R.id.textViewDato3, R.id.textViewDato4});
            listaCabecera.setAdapter(adapterCabecera);
        }
        catch (Exception e) {
            Log.e("Error : ", e.toString());
        }

        /********************************************************/
        /**********Display the contents************/

        for (Medidor medidor : medidors) {
            map2 = new HashMap<String, String>();

            map2.put("tipo", medidor.getTipoMedidor());
            map2.put("numero", medidor.getNumFabrica());
            map2.put("marca", medidor.getMarca());
            map2.put("fecha", medidor.getFechaDesinst());
            miLista.add(map2);
        }

        try {
            adapterContenido = new SimpleAdapter(getActivity().getApplicationContext(), miLista, R.layout.rows,
                    new String[] {"tipo", "numero", "marca", "fecha"}, new int[] {
                    R.id.textViewDato1, R.id.textViewDato2, R.id.textViewDato3, R.id.textViewDato4});
            listaContenido.setAdapter(adapterContenido);
        } catch (Exception e) {
            Log.e("Error : ", e.toString());
        }

        /********************************************************/

    }

}
