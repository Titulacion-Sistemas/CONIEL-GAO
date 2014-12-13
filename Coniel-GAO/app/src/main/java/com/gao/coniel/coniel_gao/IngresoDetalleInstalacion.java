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


public class IngresoDetalleInstalacion extends Fragment {

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
        spMaterialRed.setSelection(Integer.parseInt(s.getStringKey("MATERIALRED")));
        spFormaConexion.setSelection(Integer.parseInt(s.getStringKey("FORMACONEXION")));
        spEstadoInst.setSelection(Integer.parseInt(s.getStringKey("ESTADOINST")));
        spTipoConst.setSelection(Integer.parseInt(s.getStringKey("TIPOCONST")));
        spUbicacionMed.setSelection(Integer.parseInt(s.getStringKey("UBICACIONMED")));
        spTipoAcometida.setSelection(Integer.parseInt(s.getStringKey("TIPOACOMETIDA")));
        spCalibreRed.setSelection(Integer.parseInt(s.getStringKey("CALIBRERED")));
        spTipoServicio.setSelection(Integer.parseInt(s.getStringKey("TIPOSERVICIO")));
        spUsoInmueble.setSelection(Integer.parseInt(s.getStringKey("USOINMUEBLE")));
        spDemanda.setSelection(Integer.parseInt(s.getStringKey("DEMANDA")));
        spNivelSocioEconomico.setSelection(Integer.parseInt(s.getStringKey("NIVELSOCIO")));

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
                .saveKey("MATERIALRED", spMaterialRed.getSelectedItem().toString())
                .saveKey("FORMACONEXION", spFormaConexion.getSelectedItem().toString())
                .saveKey("ESTADOINST", spEstadoInst.getSelectedItem().toString())
                .saveKey("TIPOCONST", spTipoConst.getSelectedItem().toString())
                .saveKey("UBICACIONMED", spUbicacionMed.getSelectedItem().toString())
                .saveKey("TIPOACOMETIDA", spTipoAcometida.getSelectedItem().toString())
                .saveKey("CALIBRERED", spCalibreRed.getSelectedItem().toString())
                .saveKey("TIPOSERVICIO", spTipoServicio.getSelectedItem().toString())
                .saveKey("USOINMUEBLE", spUsoInmueble.getSelectedItem().toString())
                .saveKey("DEMANDA", spDemanda.getSelectedItem().toString())
                .saveKey("NIVELSOCIO", spNivelSocioEconomico.getSelectedItem().toString());
    }

}
