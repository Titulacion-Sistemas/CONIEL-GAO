package com.gao.coniel.coniel_gao;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;


public class BusquedaDatosMedidor extends Fragment {

    ListView listaContenido, listaCabecera;
    View viewRows;

    ArrayList<HashMap<String, String>> miLista, miListaCabecera;

    ListAdapter adapterCabecera, adapterContenido;

    HashMap<String, String> map1, map2;

    //String [] datos ;

    String [] tipoMedidor ={"India", "Pakistan", "China", "Bangladesh","Afghanistan" };
    String [] numMedidor={"New Delhi", "Islamabad", "Beijing", "Dhaka"," Kabul"};
    String [] marca ={"India", "Pakistan", "China", "Bangladesh","Afghanistan" };
    String [] fechaInst={"New Delhi", "Islamabad", "Beijing", "Dhaka"," Kabul"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_busqueda_datos_medidor, container, false);

        listaCabecera = (ListView) rootView.findViewById(R.id.listaCabecera);
        listaContenido = (ListView) rootView.findViewById(R.id.listaContenido);

        showActivity();


        return rootView;
    }

    public void showActivity() {

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
        catch (Exception e) {}

        /********************************************************/
        /**********Display the contents************/

        for (int i = 0; i < tipoMedidor.length; i++) {
            map2 = new HashMap<String, String>();

            //map2.put("cliente", String.valueOf(i + 1));
            map2.put("tipo", tipoMedidor[i]);
            map2.put("numero", numMedidor[i]);
            map2.put("marca", marca[i]);
            map2.put("fecha", fechaInst[i]);
            miLista.add(map2);
        }

        try {
            adapterContenido = new SimpleAdapter(getActivity().getApplicationContext(), miLista, R.layout.rows,
                    new String[] {"tipo", "numero", "marca", "fecha"}, new int[] {
                    R.id.textViewDato1, R.id.textViewDato2, R.id.textViewDato3, R.id.textViewDato4});
            listaContenido.setAdapter(adapterContenido);
        } catch (Exception e) {

        }

        /********************************************************/

    }

}
