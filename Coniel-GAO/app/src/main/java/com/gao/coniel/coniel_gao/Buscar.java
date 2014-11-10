package com.gao.coniel.coniel_gao;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import org.ksoap2.serialization.SoapObject;
import java.util.ArrayList;
import java.util.HashMap;
import clases.Abonado;
import clases.Medidor;
import serviciosWeb.SW;
import serviciosWeb.Tupla;


public class Buscar extends Fragment {

    public static String ARG_SECTION_NUMBER;
    private Abonado[] clientes=null;
    private int t=0;
    private static String[][] CABECERAS ={
            new String[]{"Cliente", "Nombre del Cliente", "Dirección", "Deuda", "Pendiente"},
            new String[]{"N° de Medidor", "Estado", "Cliente", "Nombredel Cliente", "Direccion"},
            new String[]{"Nombre del Cliente", "Dirección", "Cliente", "Deuda", "Pendiente"},
            new String[]{"Secuencia", "Cliente", "Nombredel Cliente", "Dirección", "Medidor", "Deuda"}
    } ;

    String[] sesion=null;

    public Buscar(String [] sesion){
        this.sesion = sesion;
    }

    private Spinner spinnerBuscar;
    ListView listaCont, listaCabecera;
    private TextView tvData;
    View viewRows;

    ArrayList<HashMap<String, String>> miLista, miListaCabecera;

    ListAdapter adapterTitulo, adapter;

    HashMap<String, String> map1, map2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_buscar, container, false);
        viewRows = inflater.inflate(R.layout.rows, container, false);

        spinnerBuscar = (Spinner) rootView.findViewById(R.id.spinnerBuscar);
        spinnerBuscar.setOnItemSelectedListener(myItemSelected);
        listaCabecera = (ListView) rootView.findViewById(R.id.listaCabecera);
        listaCont = (ListView) rootView.findViewById(R.id.listaContenido);
        Button btnBuscar = (Button) rootView.findViewById(R.id.btnBuscarDatos);
        tvData=(TextView) rootView.findViewById(R.id.datoBuscar);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 for (Fragment f:getActivity().getSupportFragmentManager().getFragments())
                     try {
                         ((ContenedorBusqueda) f).reconstruirPager();
                     } catch (Exception ignored) {
                     }
                 Log.e((spinnerBuscar.getSelectedItemPosition() + 1) + "", "posi");
                 asyncBuscar asb = new asyncBuscar();
                 asb.execute(
                         sesion[1],
                         sesion[3],
                         (spinnerBuscar.getSelectedItemPosition() + 1) + "",
                         getTvData().getText().toString()
                 );
             }
         }
        );

        if (clientes!=null)
            rellenar();

        Log.i("Info", "creado fragment 0");
        return rootView;
    }

    private AdapterView.OnItemSelectedListener myItemSelected = new Spinner.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //Toast.makeText(parent.getContext(),
            //        "Item Seleccionado: " + parent.getItemAtPosition(position).toString(),
            //        Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    public Abonado[] getClientes() {
        return clientes;
    }

    public void setClientes(Abonado[] clientes) {
        this.clientes = clientes;
    }



    public void rellenar(){
        miLista = new ArrayList<HashMap<String, String>>();
        miListaCabecera = new ArrayList<HashMap<String, String>>();
        int[][] ints = new int[][]{
                new int[] {
                        R.id.textViewDato1, R.id.textViewDato2, R.id.textViewDato3, R.id.textViewDato4, R.id.textViewDato5
                },
                new int[] {
                        R.id.textViewDato1, R.id.textViewDato2, R.id.textViewDato3, R.id.textViewDato4, R.id.textViewDato5, R.id.textViewDato6
                }
        };

        /**********Display the headings************/

        map1 = new HashMap<String, String>();

        map1.put(CABECERAS[t-1][0], CABECERAS[t-1][0]);
        map1.put(CABECERAS[t-1][1], CABECERAS[t-1][1]);
        map1.put(CABECERAS[t-1][2], CABECERAS[t-1][2]);
        map1.put(CABECERAS[t-1][3], CABECERAS[t-1][3]);
        map1.put(CABECERAS[t-1][4], CABECERAS[t-1][4]);
        if (t==4) map1.put(CABECERAS[t-1][5], CABECERAS[t-1][5]);

        miListaCabecera.add(map1);
        try {
            adapterTitulo = new SimpleAdapter(
                    getActivity().getApplicationContext(),
                    miListaCabecera,
                    R.layout.rows,
                    CABECERAS[t-1],
                    t==4?ints[1]:ints[0]
            );
            listaCabecera.setAdapter(adapterTitulo);
        }

        catch (Exception e) {
            Log.e("Error : ", e.toString());
        }

        /********************************************************/
        /**********Display the contents************/

        for (Abonado cliente : getClientes()) {
            map2 = new HashMap<String, String>();
            switch (t) {
                case 1:
                    map2.put(CABECERAS[t - 1][0], cliente.getCuenta() + "");
                    map2.put(CABECERAS[t - 1][1], cliente.getNombre());
                    map2.put(CABECERAS[t - 1][2], cliente.getDireccion());
                    map2.put(CABECERAS[t - 1][3], cliente.getDeuda());
                    map2.put(CABECERAS[t - 1][4], cliente.getMesesAdeudado() + "");
                    break;
                case 2:
                    map2.put(CABECERAS[t - 1][0], cliente.getMedidores()[0].getNumFabrica() + "");
                    map2.put(CABECERAS[t - 1][1], cliente.getMedidores()[0].getEstado());
                    map2.put(CABECERAS[t - 1][2], cliente.getCuenta() + "");
                    map2.put(CABECERAS[t - 1][3], cliente.getNombre());
                    map2.put(CABECERAS[t - 1][4], cliente.getDireccion());
                    break;
                case 3:
                    map2.put(CABECERAS[t - 1][0], cliente.getNombre());
                    map2.put(CABECERAS[t - 1][1], cliente.getDireccion());
                    map2.put(CABECERAS[t - 1][2], cliente.getCuenta() + "");
                    map2.put(CABECERAS[t - 1][3], cliente.getDeuda());
                    map2.put(CABECERAS[t - 1][4], cliente.getMesesAdeudado() + "");
                    break;
                case 4:
                    map2.put(CABECERAS[t - 1][0], cliente.getGeocodigo());
                    map2.put(CABECERAS[t - 1][1], cliente.getCuenta() + "");
                    map2.put(CABECERAS[t - 1][2], cliente.getNombre());
                    map2.put(CABECERAS[t - 1][3], cliente.getDireccion());
                    map2.put(CABECERAS[t - 1][4], cliente.getMedidores()[0].getNumFabrica() + "");
                    map2.put(CABECERAS[t - 1][5], cliente.getDeuda());
                    break;
                default:
                    break;
            }
            miLista.add(map2);
        }

        try {
            adapter = new SimpleAdapter(
                    getActivity().getApplicationContext(),
                    miLista,
                    R.layout.rows,
                    CABECERAS[t-1], t==4?ints[1]:ints[0]
            );
            listaCont.setAdapter(adapter);
        } catch (Exception e) {
            Log.e("Error : ", e.toString());
        }
        Log.i("Info", "rellenado fragment 0");
        /********************************************************/
    }

    public TextView getTvData() {
        return tvData;
    }



    //EN SEGUNDO PLANO
    private class asyncBuscar extends AsyncTask<String, Float, Integer> {

        String toast=null;
        int tipo =0;

        @Override
        protected Integer doInBackground(String... params) {
            SW acc = new SW("busquedas.wsdl", "buscarMovil");

            acc.asignarPropiedades(
                    new Tupla[]{
                            new Tupla<String, Object>("idUsuario", params[0]),
                            new Tupla<String, Object>("sesion", params[1]),
                            new Tupla<String, Object>("tipo", params[2]),
                            new Tupla<String, Object>("dato", params[3])
                    }
            );
            try{
                SoapObject r = (SoapObject)acc.ajecutar();
                tipo = Integer.parseInt(params[2]);
                //Llenando abonados...
                SoapObject data = (SoapObject)r.getProperty(0);
                int ncoincidencias = data.getPropertyCount()/(tipo ==4?6:5);
                setClientes(new Abonado[ncoincidencias]);
                for (int i=0; i<ncoincidencias; i++) {
                    switch (tipo) {
                        case 1:
                            getClientes()[i] = new Abonado(
                                    null, Integer.parseInt(data.getProperty(4 + (i * 5)).toString().trim()),
                                    Integer.parseInt(data.getProperty(i * 5).toString().trim()),
                                    data.getProperty(1 + (i * 5)).toString().trim(), null,
                                    data.getProperty(2 + (i * 5)).toString().trim(), null, null, null,
                                    data.getProperty(3 + (i * 5)).toString().trim(), null
                            );
                            break;
                        case 2:
                            getClientes()[i] = new Abonado(
                                    null, 0,
                                    Integer.parseInt(data.getProperty(2 + (i * 5)).toString().trim()),
                                    data.getProperty(3 + (i * 5)).toString().trim(),
                                    null, data.getProperty(4 + (i * 5)).toString(), null, null,
                                    null, null,
                                    new Medidor[]{
                                            new Medidor(
                                                    data.getProperty(i * 5).toString().trim(),
                                                    null, null, null, null, null, null, null, null, null, null, null,
                                                    data.getProperty(1 + (i * 5)).toString().trim(),
                                                    null, null
                                            )
                                    }
                            );
                            break;
                        case 3:
                            getClientes()[i] = new Abonado(
                                    null, Integer.parseInt(data.getProperty(4 + (i * 5)).toString().trim()),
                                    Integer.parseInt(data.getProperty(2 + (i * 5)).toString().trim()),
                                    data.getProperty(i * 5).toString().trim(),
                                    null,
                                    data.getProperty(1 + (i * 5)).toString().trim(), null, null, null,
                                    data.getProperty(3 + (i * 5)).toString().trim(), null
                            );
                            break;
                        case 4:
                            getClientes()[i] = new Abonado(
                                    null, 0,
                                    Integer.parseInt(data.getProperty(1 + (i * 6)).toString().trim()),
                                    data.getProperty(2 + (i * 6)).toString().trim(),
                                    data.getProperty(i * 6).toString().trim(),
                                    data.getProperty(3 + (i * 6)).toString().trim(),
                                    null, null, null,
                                    data.getProperty(5 + (i * 6)).toString().trim(),
                                    new Medidor[]{
                                            new Medidor(
                                                    data.getProperty(4 + (i * 6)).toString().trim(),
                                                    null, null, null, null, null, null, null, null, null, null, null, null, null, null
                                            )
                                    }
                            );
                            break;
                        default:
                            cancel(true);
                            break;

                    }
                }

                data=(SoapObject)r.getProperty(1);
                getClientes()[0].setCi(data.getPropertyAsString(0).trim());
                getClientes()[0].setCuenta(Integer.parseInt(data.getPropertyAsString(1).trim()));
                getClientes()[0].setNombre(data.getPropertyAsString(2).trim());
                getClientes()[0].setDireccion(data.getPropertyAsString(3).trim());
                getClientes()[0].setInterseccion(data.getPropertyAsString(4).trim());
                getClientes()[0].setUrbanizacion(data.getPropertyAsString(5).trim());
                getClientes()[0].setEstado(data.getPropertyAsString(6).trim());
                getClientes()[0].setGeocodigo(data.getPropertyAsString(7).trim());
                getClientes()[0].setMesesAdeudado(Integer.parseInt(data.getPropertyAsString(8).trim()));
                getClientes()[0].setDeuda(data.getPropertyAsString(9).trim());

                data = (SoapObject)r.getProperty(2);
                ncoincidencias=data.getPropertyCount()/14;
                Medidor[] m = new Medidor[ncoincidencias];
                for(int u=0;u<ncoincidencias;u++){
                    m[u]=new Medidor(
                            data.getProperty(8+(u*14)).toString().trim(),
                            data.getProperty(9+(u*14)).toString().trim(),
                            data.getProperty(12+(u*14)).toString().trim(),
                            data.getProperty(13+(u*14)).toString().trim(),
                            data.getProperty(11+(u*14)).toString().trim(),
                            data.getProperty(6+(u*14)).toString().trim(),
                            data.getProperty(7+(u*14)).toString().trim(),
                            data.getProperty(u*14).toString().trim(),
                            data.getProperty(10+(u*14)).toString().trim(),
                            data.getProperty(1+(u*14)).toString().trim(),
                            data.getProperty(2+(u*14)).toString().trim(),
                            data.getProperty(3+(u*14)).toString().trim(),
                            null,
                            data.getProperty(4+(u*14)).toString().trim(),
                            data.getProperty(5+(u*14)).toString().trim()
                    );
                }
                getClientes()[0].setMedidores(m);
                for (Fragment f:getActivity().getSupportFragmentManager().getFragments())
                    try {
                        ((ContenedorBusqueda) f).cargarPager();
                    } catch (Exception ignored) {
                    }

            }catch (Exception e){
                toast = "Error, No se ha podido Buscar...";
                Log.e("Error : ", e.toString());
                this.cancel(true);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            //Rellenando coincidencias

            t=tipo;
            rellenar();

        }

        @Override
        protected void onCancelled() {
            Toast t = Toast.makeText(
                    getActivity().getApplicationContext(),
                    toast,
                    Toast.LENGTH_SHORT
            );
            t.show();
        }
    }




}

