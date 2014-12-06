package com.gao.coniel.coniel_gao;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MenuPrincipal extends Fragment {
    private String[] sesion = null;
    Fragment fragment;
    Button btnIngreso, btnBuscar, btnFotos, btnUbicacion, btnAjustes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_menu, container, false);

        try{
            sesion = getActivity().getIntent().getExtras().getStringArray("user");
        }catch (Exception e){
            Log.e("Error al Cargar datos de sesion: ", "" + e);
        }

        btnIngreso = (Button) rootView.findViewById(R.id.btnIngresar);
        btnBuscar = (Button) rootView.findViewById(R.id.btnBuscar);
        btnFotos = (Button) rootView.findViewById(R.id.btnFotos);
        btnUbicacion = (Button) rootView.findViewById(R.id.btnUbicacion);
        btnAjustes = (Button) rootView.findViewById(R.id.btnAjustes);

        btnIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ((Contenedor)getActivity()).displayView(1, null);
                }catch (Exception ignored){}
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ((Contenedor)getActivity()).displayView(2, null);
                }catch (Exception ignored){}
            }
        });

        btnFotos.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                try {
                    ((Contenedor)getActivity()).displayView(3, null);
                }catch (Exception ignored){}
            }
        });

        btnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    ((Contenedor)getActivity()).displayView(4, null);
                }catch (Exception ignored){}
            }
        });

        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().finish();
    }
}
