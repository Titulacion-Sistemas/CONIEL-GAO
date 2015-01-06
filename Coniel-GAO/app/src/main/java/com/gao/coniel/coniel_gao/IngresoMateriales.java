package com.gao.coniel.coniel_gao;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import clases.SessionManagerIngreso;


public class IngresoMateriales extends Fragment {

    Spinner spMedidores,spSellos, spUbicacionSello;
    EditText edtCant;
    Button btnAgregar, btnAgregarSello;
    ListView listaMat, listaSello;
    CheckBox checkDirecto, checkReubicacion, checkContrastacion;
    private List<Object> dataset = getContent();



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


        listaSello.setAdapter(new CustomArrayAdapter(getActivity(), dataset));

        listaSello.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @SuppressLint("NewApi")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = dataset.get(position);
                if (item instanceof CabeceraMateriales) {
                    Toast.makeText(getActivity(), ((CabeceraMateriales) item).getTitulo1(), Toast.LENGTH_SHORT).show();
                    // back to header, see
                    // http://danielme.com/tip-android-17-listview-back-to-top-volver-al-inicio/
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                        listaSello.setSelection(position);
                    } else {
                        listaSello.smoothScrollToPositionFromTop(position, 0, 300);
                    }
                } else {
                    Toast.makeText(getActivity(), ((ContenidoMateriales) item).getDato1(), Toast.LENGTH_SHORT).show();
                }

            }
        });


        return view;
    }

    ////////

    private List<Object> getContent()
    {
        List<Object> list = new ArrayList<Object>(60);
        ContenidoMateriales content = null;
        CabeceraMateriales header = null;
        int j = 1;

        for (int i = 0; i < 50; i++)
        {
            // set a new header or section every five rows
            if (i % 5 == 0)
            {
                header = new CabeceraMateriales();
                header.setTitulo1("Titulo Numero " + j);
                j++;
                list.add(header);
            }

            content = new ContenidoMateriales();
            content.setDato1("Text 1-" + (i + 1));
            content.setDato2("Text 2-" + (i + 1));
            list.add(content);
        }

        return list;

    }
    /////

    //El Fragment ha sido quitado de su Activity y ya no está disponible
    @Override
    public void onStop(){
        super.onStop();
        Log.i("Se ha ejecutado el ", "  ONSTOP");
        /*//Guardar Sesion para evitar cierre
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
        //falta la lista*/
    }
}
