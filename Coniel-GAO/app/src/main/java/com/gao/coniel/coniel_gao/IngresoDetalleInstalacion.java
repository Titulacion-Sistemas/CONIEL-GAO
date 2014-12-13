package com.gao.coniel.coniel_gao;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import clases.SessionManagerIngreso;


public class IngresoDetalleInstalacion extends android.support.v4.app.Fragment {

    Spinner spMaterialRed, spFormaConexion, spEstadoInst, spTipoConst, spUbicacionMed,
            spTipoAcometida, spCalibreRed, spTipoServicio, spUsoInmueble, spDemanda,
            spNivelSocioEconomico;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ingreso_detalle_instalacion, container, false);

        spMaterialRed = (Spinner) view.findViewById(R.id.spinnerMaterialRed);
        spFormaConexion = (Spinner) view.findViewById(R.id.spinnerFormaConexion);
        spEstadoInst = (Spinner) view.findViewById(R.id.spinnerEstadoInstalacion);
        spTipoConst = (Spinner) view.findViewById(R.id.spinnerTipoConst);
        spUbicacionMed = (Spinner) view.findViewById(R.id.spinnerMaterialRed);
        spTipoAcometida = (Spinner) view.findViewById(R.id.spinnerTipoAcom);
        spCalibreRed = (Spinner) view.findViewById(R.id.spinnerCalibreRed);
        spTipoServicio = (Spinner) view.findViewById(R.id.spinnerTipoServicio);
        spUsoInmueble = (Spinner) view.findViewById(R.id.spinnerUsoInmueble);
        spDemanda = (Spinner) view.findViewById(R.id.spinnerDemanda);
        spNivelSocioEconomico = (Spinner) view.findViewById(R.id.spinnerNivelSocioeconomico);

        //Guardar Variables de Sesion
        SessionManagerIngreso s = SessionManagerIngreso.getManager(getActivity().getApplicationContext());
        spMaterialRed.setSelection(s.getIntKey("MATERIALRED"));
        spFormaConexion.setSelection(s.getIntKey("FORMACONEXION"));
        spEstadoInst.setSelection(s.getIntKey("ESTADOINST"));
        spTipoConst.setSelection(s.getIntKey("TIPOCONST"));
        spUbicacionMed.setSelection(s.getIntKey("UBICACIONMED"));
        spTipoAcometida.setSelection(s.getIntKey("TIPOACOMETIDA"));
        spCalibreRed.setSelection(s.getIntKey("CALIBRERED"));
        spTipoServicio.setSelection(s.getIntKey("TIPOSERVICIO"));
        spUsoInmueble.setSelection(s.getIntKey("USOINMUEBLE"));
        spDemanda.setSelection(s.getIntKey("DEMANDA"));
        spNivelSocioEconomico.setSelection(s.getIntKey("NIVELSOCIO"));

        return view;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i("Se ha ejecutado el ", "  ONATTACH del detalle");
    }

    //El Fragment ha sido quitado de su Activity y ya no está disponible
    @Override
    public void onStop(){
        super.onStop();
        Log.i("Se ha ejecutado el ", "  ONSTOP");
        //Guardar Sesion para evitar cierre
        SessionManagerIngreso.getManager(getActivity().getApplicationContext())
                .saveKey("Coniel-GAO", true)
                .saveKey("MATERIALRED", spMaterialRed.getSelectedItemPosition())
                .saveKey("FORMACONEXION", spFormaConexion.getSelectedItemPosition())
                .saveKey("ESTADOINST", spEstadoInst.getSelectedItemPosition())
                .saveKey("TIPOCONST", spTipoConst.getSelectedItemPosition())
                .saveKey("UBICACIONMED", spUbicacionMed.getSelectedItemPosition())
                .saveKey("TIPOACOMETIDA", spTipoAcometida.getSelectedItemPosition())
                .saveKey("CALIBRERED", spCalibreRed.getSelectedItemPosition())
                .saveKey("TIPOSERVICIO", spTipoServicio.getSelectedItemPosition())
                .saveKey("USOINMUEBLE", spUsoInmueble.getSelectedItemPosition())
                .saveKey("DEMANDA", spDemanda.getSelectedItemPosition())
                .saveKey("NIVELSOCIO", spNivelSocioEconomico.getSelectedItemPosition());
    }

}
