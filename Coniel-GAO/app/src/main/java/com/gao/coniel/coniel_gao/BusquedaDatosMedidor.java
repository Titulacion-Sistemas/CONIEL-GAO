package com.gao.coniel.coniel_gao;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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

    View rootView;

    public BusquedaDatosMedidor(Medidor[] medidors){
        this.medidors = medidors;
    }
    public BusquedaDatosMedidor(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_busqueda_datos_medidor, container, false);


        listaCabecera = (ListView) rootView.findViewById(R.id.listaCabeceraMedidor);
        listaContenido = (ListView) rootView.findViewById(R.id.listaContenidoMedidor);

        listaContenido.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (medidors!=null){
                    rellenarMedidor(medidors[position]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (medidors!=null){
            rellenar(medidors);
        }

        Log.i("Info", "creado fragment 2");
        return rootView;
    }


    private void rellenarMedidor(Medidor m){
        EditText et = ((EditText)rootView.findViewById(R.id.EditTextnumFabrica));
        et.setText(m.getNumFabrica(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextnumSerie)).setText(m.getNumSerie(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextMarca)).setText(m.getMarca(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextTipoMedidor)).setText(m.getTipoMedidor(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextTecnologia)).setText(m.getTecnologia(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextTension)).setText(m.getTension(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextAmperaje)).setText(m.getAmperaje(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextFases)).setText(m.getFase(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextHilos)).setText(m.getHilos(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextDigitos)).setText(m.getDigitos(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextfechaInst)).setText(m.getFechaDesinst(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextlecturaInst)).setText(m.getLecturaInst(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextfechaDesinst)).setText(m.getFechaDesinst(), TextView.BufferType.EDITABLE);
        ((EditText)rootView.findViewById(R.id.EditTextlecturaDesinst)).setText(m.getLecturaDesinst(), TextView.BufferType.EDITABLE);
        Log.i("Info", "rellenado fragment 2(Texts)");
    }

    public void rellenar(Medidor[] medidors) {

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
        Log.i("Info", "rellenado fragment 2(Lista)");
        /********************************************************/

        for (Medidor m : medidors){
            if(m.getFechaDesinst().equals("0/00/0000")){
                rellenarMedidor(m);
                break;
            }
        }

    }

}
