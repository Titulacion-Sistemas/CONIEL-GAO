package com.gao.coniel.coniel_gao;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


public class Buscar extends Fragment {

    private Spinner spinnerBuscar;
    ListView listaCont, listaCabecera;
    View viewRows;

    ArrayList<HashMap<String, String>> miLista, miListaCabecera;

    ListAdapter adapterTitulo, adapter;

    HashMap<String, String> map1, map2;

    //String [] datos ;

    String [] cliente ={"India", "Pakistan", "China", "Bangladesh","Afghanistan" };
    String [] nombre={"New Delhi", "Islamabad", "Beijing", "Dhaka"," Kabul"};
    String [] direccion ={"India", "Pakistan", "China", "Bangladesh","Afghanistan" };
    String [] deuda={"New Delhi", "Islamabad", "Beijing", "Dhaka"," Kabul"};
    String [] pendiente ={"India", "Pakistan", "China", "Bangladesh","Afghanistan" };
    String [] ruta={"New Delhi", "Islamabad", "Beijing", "Dhaka"," Kabul"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_buscar, container, false);
        viewRows = inflater.inflate(R.layout.rows, container, false);

        spinnerBuscar = (Spinner) rootView.findViewById(R.id.spinnerBuscar);
        spinnerBuscar.setOnItemSelectedListener(myItemSelected);
        listaCabecera = (ListView) rootView.findViewById(R.id.listaCabecera);
        listaCont = (ListView) rootView.findViewById(R.id.listaContenido);

        showActivity();

        return rootView;
    }

    private AdapterView.OnItemSelectedListener myItemSelected = new Spinner.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(parent.getContext(), "Item Seleccionado: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    public void showActivity() {

        miLista = new ArrayList<HashMap<String, String>>();
        miListaCabecera = new ArrayList<HashMap<String, String>>();

        /**********Display the headings************/

        map1 = new HashMap<String, String>();

        map1.put("cliente", "Cliente");
        map1.put("nombre", " Nombre");
        map1.put("direccion", " Dirección");
        map1.put("deuda", "Deuda");
        map1.put("pendiente", "Pendiente");
        map1.put("ruta", "Ruta");

        miListaCabecera.add(map1);

        try {

            adapterTitulo = new SimpleAdapter(getActivity().getApplicationContext(), miListaCabecera, R.layout.rows,
                    new String[]{"cliente", "nombre", "direccion", "deuda", "pendiente", "ruta"}, new int[]{
                    R.id.textViewDato1, R.id.textViewDato2, R.id.textViewDato3, R.id.textViewDato4, R.id.textViewDato5, R.id.textViewDato6});
            listaCabecera.setAdapter(adapterTitulo);
        }
        catch (Exception e) {}

        /********************************************************/
        /**********Display the contents************/

        for (int i = 0; i < cliente.length; i++) {
            map2 = new HashMap<String, String>();

            //map2.put("cliente", String.valueOf(i + 1));
            map2.put("cliente", cliente[i]);
            map2.put("nombre", nombre[i]);
            map2.put("direccion", direccion[i]);
            map2.put("deuda", deuda[i]);
            map2.put("pendiente", pendiente[i]);
            map2.put("ruta", ruta[i]);
            miLista.add(map2);
        }

        try {
            adapter = new SimpleAdapter(getActivity().getApplicationContext(), miLista, R.layout.rows,
                    new String[] { "cliente", "nombre", "direccion", "deuda", "pendiente", "ruta" }, new int[] {
                    R.id.textViewDato1, R.id.textViewDato2, R.id.textViewDato3, R.id.textViewDato4, R.id.textViewDato5, R.id.textViewDato6});
            listaCont.setAdapter(adapter);
        } catch (Exception e) {

        }

        /********************************************************/

    }

}

