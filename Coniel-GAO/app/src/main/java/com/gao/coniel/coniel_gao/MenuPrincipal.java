package com.gao.coniel.coniel_gao;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MenuPrincipal extends Fragment{
    Fragment fragment;
    Button btnIngreso, btnBuscar, btnChat, btnFotos, btnUbicacion, btnAjustes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_menu, container, false);

        btnIngreso = (Button) rootView.findViewById(R.id.btnIngresar);
        btnBuscar = (Button) rootView.findViewById(R.id.btnBuscar);
        btnChat = (Button) rootView.findViewById(R.id.btnChat);
        btnFotos = (Button) rootView.findViewById(R.id.btnFotos);
        btnUbicacion = (Button) rootView.findViewById(R.id.btnUbicacion);
        btnAjustes = (Button) rootView.findViewById(R.id.btnAjustes);

        btnIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new Buscar();
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new Buscar();
            }
        });
        return rootView;
    }
}
