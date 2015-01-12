package com.gao.coniel.coniel_gao;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;

import java.util.ArrayList;

import clases.ContenidoMaterialesLista;
import clases.ContenidoSellos;
import clases.ListaContenidoSellosAdapter;
import clases.ListaMaterialesAdapter;
import clases.SessionManagerIngreso;


public class IngresoMateriales extends Fragment {

    Spinner spMedidores,spSellos, spUbicacionSello;
    NumberPicker edtCant;
    Button btnAgregar, btnAgregarSello;
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

        spMedidores = (Spinner) view.findViewById(R.id.spinnerMedidores);
        edtCant = (NumberPicker) view.findViewById(R.id.edtCantidad);
        edtCant.setMaxValue(100);
        edtCant.setMinValue(1);
        edtCant.setValue(1);
        btnAgregar = (Button) view.findViewById(R.id.btnAgregar);
        listaMat = (ListView) view.findViewById(R.id.listMatAgregados);
        checkDirecto = (CheckBox) view.findViewById(R.id.checkdirecto);
        checkContrastacion = (CheckBox) view.findViewById(R.id.checkcontrastacion);
        checkReubicacion = (CheckBox) view.findViewById(R.id.checkreubicacion);
        spSellos = (Spinner) view.findViewById(R.id.spinnerSellos);
        spUbicacionSello = (Spinner) view.findViewById(R.id.spinnerUbicacionSello);
        btnAgregarSello = (Button) view.findViewById(R.id.btnAgregarSello);
        listaSello = (ListView) view.findViewById(R.id.listaSellos);

      //Guardar Variables de Sesion
        SessionManagerIngreso s = SessionManagerIngreso.getManager(getActivity().getApplicationContext());
        String selection = s.getIntKey("MEDIDORES")+"";
        if (!selection.equals("")){
            spMedidores.setSelection(Integer.parseInt(selection));
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

        return view;
    }

    //El Fragment ha sido quitado de su Activity y ya no está disponible
    @Override
    public void onStop(){
        super.onStop();
        Log.i("Se ha ejecutado el ", "  ONSTOP");

        //Guardar variables
        SessionManagerIngreso s = SessionManagerIngreso.getManager(getActivity().getApplicationContext())
                .saveKey("MEDIDORES", spMedidores.getSelectedItemPosition())
                .saveKey("CANTIDAD", edtCant.getValue()+"")
                .saveKey("CHECKDIRECTO", checkDirecto.isChecked())
                .saveKey("CHECKCONTRASTACION", checkContrastacion.isChecked())
                .saveKey("CHECKREUBICACION", checkReubicacion.isChecked());
                //.saveKey("SELLOS", spSellos.getSelectedItemPosition());



    }
}
