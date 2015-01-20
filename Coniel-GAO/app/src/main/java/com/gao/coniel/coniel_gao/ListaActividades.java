package com.gao.coniel.coniel_gao;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

import clases.AdaptadorListaActividades;
import clases.ItemListaActividades;
import clases.SessionManager;
import clases.SessionManagerIngreso;
import clases.Tupla;
import serviciosWeb.SW;


public class ListaActividades extends android.support.v4.app.Fragment {

    ListView listView;
    ArrayList<ItemListaActividades> listaActividades;

    // Creamos un adapterSellos personalizado
    AdaptadorListaActividades adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_lista_actividades, container, false);

        listView = (ListView) v.findViewById(R.id.listActividadesHoy);
        listaActividades = new ArrayList<ItemListaActividades>();

        asyncLoad al = new asyncLoad();
        al.execute(
            SessionManager.getManager(getActivity().getApplicationContext()).getStringKey(SessionManager.LOGIN_KEY),
            SessionManager.getManager(getActivity().getApplicationContext()).getStringKey("contrato")
        );

        return v;
    }


    //EN SEGUNDO PLANO
    private class asyncLoad extends AsyncTask<String, Float, Object> {

        String toast="";

        @Override
        protected Object doInBackground(String... params) {
            SW acc = new SW("ingresos.wsdl", "realizados");
            Log.i("info-params",params[0] + " " + params[1]);
            acc.asignarPropiedades(
                    new Tupla[]{
                            new Tupla<String, Object>("idUsuario", params[0]),
                            new Tupla<String, Object>("contrato", params[1])
                    }
            );
            Object r = acc.ajecutar();
            try{
                listaActividades.add(new ItemListaActividades("Id", "Cuenta", "Nombre", "Actividad", "Medidor"));
                SoapObject data = (SoapObject)r;
                System.out.print(data);

                for (int i=0 ; i<data.getPropertyCount() ; i++){
                    listaActividades.add(
                            new ItemListaActividades(
                                ""+(((SoapObject)data.getProperty(i)).getProperty(0)),
                                ""+(((SoapObject)data.getProperty(i)).getProperty(1)),
                                ""+(((SoapObject)data.getProperty(i)).getProperty(2)),
                                ""+(((SoapObject)data.getProperty(i)).getProperty(3)),
                                ""+(((SoapObject)data.getProperty(i)).getProperty(4))
                            )
                    );
                }

                return listaActividades;
            }catch (Exception e){
                toast = "Error, No se pudo consultar las actividades";
                this.cancel(true);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object data) {
            super.onPostExecute(data);

            System.out.print(data);

            // Al adapterSellos personalizado le pasamos el contexto y la lista que contiene
            // Añadimos el adapterSellos al listview
            adapter = new AdaptadorListaActividades(getActivity(), listaActividades);
            listView.setAdapter(adapter);
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    SessionManagerIngreso s = SessionManagerIngreso.getManager(getActivity().getApplicationContext());

                    if(!(s.getStringKey("IDACTIVIDADSELECCIONADA").toString().equals(listaActividades.get(position).getIde()))
                            && position>0 ) {
                        s.saveKey("IDACTIVIDADSELECCIONADA", listaActividades.get(position).getIde());
                        s.saveKey("IDACTIVIDADSELECCIONADA1", listaActividades.get(position).getIde());
                        s.saveKey("IDACTIVIDADSELECCIONADA2", listaActividades.get(position).getIde());
                        s.saveKey("IDACTIVIDADSELECCIONADA3", listaActividades.get(position).getIde());
                        s.saveKey("IDACTIVIDADSELECCIONADA4", listaActividades.get(position).getIde());
                        s.saveKey("IDACTIVIDADSELECCIONADA5", listaActividades.get(position).getIde());
                        s.saveKey("IDACTIVIDADSELECCIONADA6", listaActividades.get(position).getIde());
                        Toast t = Toast.makeText(
                                getActivity().getApplicationContext(),
                                "Actividad Cargada...",
                                Toast.LENGTH_SHORT
                        );
                        t.show();
                    }else{
                        s.saveKey("IDACTIVIDADSELECCIONADA", "");
                        s.saveKey("IDACTIVIDADSELECCIONADA1", "");
                        s.saveKey("IDACTIVIDADSELECCIONADA2", "");
                        s.saveKey("IDACTIVIDADSELECCIONADA3", "");
                        s.saveKey("IDACTIVIDADSELECCIONADA4", "");
                        s.saveKey("IDACTIVIDADSELECCIONADA5", "");
                        s.saveKey("IDACTIVIDADSELECCIONADA6", "");
                    }

                    adapter = new AdaptadorListaActividades(getActivity(), listaActividades);
                    listView.setAdapter(adapter);

                    return false;

                }
            });

        }


        protected void onCancelled() {


        }
    }


}