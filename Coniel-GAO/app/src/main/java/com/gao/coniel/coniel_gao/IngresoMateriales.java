package com.gao.coniel.coniel_gao;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import clases.SessionManagerIngreso;


public class IngresoMateriales extends Fragment {

    Spinner spMedidores,spSellos, spUbicacionSello;
    EditText edtCant;
    Button btnAgregar, btnAgregarSello;
    ListView listaMat, listaSello;
    CheckBox checkDirecto, checkReubicacion, checkContrastacion;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ingreso_materiales, container, false);

        spMedidores = (Spinner) view.findViewById(R.id.spinnerMedidores);
        edtCant = (EditText) view.findViewById(R.id.edtCantidad);
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
        spMedidores.setSelection(Integer.parseInt(s.getStringKey("MEDIDORES")));
        edtCant.setText(s.getStringKey("CANTIDAD"));
        checkDirecto.setChecked(s.getBooleanKey("CHECKDIRECTO"));
        checkContrastacion.setChecked(s.getBooleanKey("CHECKCONTRASTACION"));
        checkReubicacion.setChecked(s.getBooleanKey("CHECKREUBICACION"));
        spSellos.setSelection(s.getIntKey("SELLOS"));
        spUbicacionSello.setSelection(s.getIntKey("UBICACIONSELLO"));
        //FALTA EL SET DE LA LISTA
        return view;
    }

    //El Fragment ha sido quitado de su Activity y ya no está disponible
    @Override
    public void onStop(){
        super.onStop();
        Log.i("Se ha ejecutado el ", "  ONSTOP");
        //Guardar Sesion para evitar cierre
        SessionManagerIngreso.getManager(getActivity().getApplicationContext())
                .saveKey("Coniel-GAO", true)
                .saveKey("MEDIDORES", spMedidores.getSelectedItem().toString())
                .saveKey("CANTIDAD", edtCant.getText().toString())
                //.saveKey("LISTAMATERIALES", listaMat.get)
                //.saveKey("LISTASELLOS", lista)
                .saveKey("CHECKDIRECTO", checkDirecto.isChecked())
                .saveKey("CHECKCONTRASTACION", checkContrastacion.isChecked())
                .saveKey("CHECKREUBICACION", checkReubicacion.isChecked())
                .saveKey("SELLOS",spSellos.getSelectedItemPosition())
                .saveKey("UBICACIONSELLO", spUbicacionSello.getSelectedItemPosition());
        //falta la lista
    }
}
