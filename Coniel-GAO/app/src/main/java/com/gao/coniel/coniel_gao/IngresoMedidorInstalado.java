package com.gao.coniel.coniel_gao;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import clases.SessionManagerIngreso;


public class IngresoMedidorInstalado extends Fragment {

    Spinner spMedidoresBodega;
    EditText edtFabricaBodega, edtSerieBodega, edtMarcaBodega, edtTipoBodega, edtLecturaBodega;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ingreso_medidor_instalado, container, false);

        spMedidoresBodega = (Spinner) view.findViewById(R.id.spinnerMedidoresBodega);
        edtFabricaBodega = (EditText) view.findViewById(R.id.edtFabricaBodega);
        edtSerieBodega = (EditText) view.findViewById(R.id.edtSerieBodega);
        edtMarcaBodega = (EditText) view.findViewById(R.id.edtMarcaBodega);
        edtTipoBodega = (EditText) view.findViewById(R.id.edtTipoBodega);
        edtLecturaBodega = (EditText) view.findViewById(R.id.edtLecturaBodega);

       //Guardar Variables de Sesion
        SessionManagerIngreso s = SessionManagerIngreso.getManager(getActivity().getApplicationContext());
        spMedidoresBodega.setSelection(s.getIntKey("MEDIDORESBODEGA"));
        edtFabricaBodega.setText(s.getStringKey("NUMFABRICABODEGA"));
        edtSerieBodega.setText(s.getStringKey("SERIEBODEGA"));
        edtMarcaBodega.setText(s.getStringKey("MARCABODEGA"));
        edtTipoBodega.setText(s.getStringKey("TIPOBODEGA"));
        edtLecturaBodega.setText(s.getStringKey("LECTURABODEGA"));

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
                .saveKey("MEDIDORESBODEGA", spMedidoresBodega.getSelectedItemPosition())
                .saveKey("NUMFABRICABODEGA", edtFabricaBodega.getText().toString())
                .saveKey("SERIEBODEGA", edtSerieBodega.getText().toString())
                .saveKey("MARCABODEGA", edtMarcaBodega.getText().toString())
                .saveKey("TIPOBODEGA", edtTipoBodega.getText().toString())
                .saveKey("LECTURABODEGA",edtLecturaBodega.getText().toString());
    }
}
