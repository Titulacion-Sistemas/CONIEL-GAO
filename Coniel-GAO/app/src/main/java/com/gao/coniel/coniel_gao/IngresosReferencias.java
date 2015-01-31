package com.gao.coniel.coniel_gao;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

import clases.Abonado;
import clases.Medidor;
import clases.SessionManager;
import clases.SessionManagerIngreso;
import clases.Tupla;
import gif.decoder.GifRun;
import serviciosWeb.SW;


public class IngresosReferencias extends Fragment {

    private SurfaceView sfvTrack;
    EditText edtFabricaRef, edtSerialRef, edtMarcaRef, edtCuentaRef;
    ImageButton btnBuscarRef;
    private Abonado cliente=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ingresos_referencias, container, false);

        btnBuscarRef = (ImageButton) view.findViewById(R.id.btnBuscarRef);
        edtFabricaRef = (EditText) view.findViewById(R.id.edtFabricaRef);
        edtSerialRef = (EditText) view.findViewById(R.id.edtSerialRef);
        edtMarcaRef = (EditText) view.findViewById(R.id.edtMarcaRef);
        edtCuentaRef = (EditText) view.findViewById(R.id.edtCuentaRef);
        sfvTrack = (SurfaceView) view.findViewById(R.id.cargandoD);
        sfvTrack.setZOrderOnTop(true);

       recuperar();



        btnBuscarRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager s = SessionManager.getManager(getActivity().getApplicationContext());
                asyncBuscar asb = new asyncBuscar(sfvTrack);


                if (validar(1, edtFabricaRef.getText().toString())){
                    habilitarComponentes(false);
                    asb.execute(
                            s.getStringKey(SessionManager.LOGIN_KEY),
                            s.getStringKey(SessionManager.SESSION_KEY),
                            "2",
                            edtFabricaRef.getText().toString()
                    );
                }
            }
        });

        return view;
    }

    //El Fragment ha sido quitado de su Activity y ya no está disponible
    @Override
    public void onStop(){
        super.onStop();
        Log.i("Se ha ejecutado el ", "  ONSTOP");

       //Guardar Sesion para evitar cierre
        SessionManagerIngreso.getManager(getActivity().getApplicationContext())
                .saveKey("FABRICAREF", edtFabricaRef.getText().toString())
                .saveKey("SERIALREF", edtSerialRef.getText().toString())
                .saveKey("MARCAREF", edtMarcaRef.getText().toString())
                .saveKey("CUENTAREF", edtCuentaRef.getText().toString());
    }

    private void recuperar() {

        SessionManagerIngreso s = SessionManagerIngreso.getManager(getActivity().getApplicationContext());

        if (!((s.getStringKey("IDACTIVIDADSELECCIONADA6")+"").equals(""))){

            asyncSeleccion asb = new asyncSeleccion();
            asb.execute(
                    s.getStringKey("IDACTIVIDADSELECCIONADA")+""
            );

        }
        else {

            //recuperar Variables de Sesion
            edtFabricaRef.setText(s.getStringKey("FABRICAREF"));
            edtSerialRef.setText(s.getStringKey("SERIALREF"));
            edtMarcaRef.setText(s.getStringKey("MARCAREF"));
            edtCuentaRef.setText(s.getStringKey("CUENTAREF"));

        }

    }


    //EN SEGUNDO PLANO
    private class asyncBuscar extends AsyncTask<String, Float, Integer> {

        private String toast=null;
        private int tipo =0;
        private SurfaceView img;
        private GifRun w;

        public asyncBuscar(SurfaceView sfvTrack) {
            img = sfvTrack;
            w=new GifRun();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            w.LoadGiff(img, getActivity().getApplicationContext(), R.drawable.gifload);
        }

        @Override
        protected Integer doInBackground(String... params) {
            SW acc = new SW("busquedas.wsdl", "buscarDjango");

            acc.asignarPropiedades(
                    new Tupla[]{
                            new Tupla<String, Object>("idUsuario", params[0]),
                            new Tupla<String, Object>("sesion", params[1]),
                            new Tupla<String, Object>("tipo", params[2]),
                            new Tupla<String, Object>("dato", params[3]),
                            new Tupla<String, Object>("esIngreso", true)
                    }
            );
            try{
                SoapObject r = (SoapObject)acc.ajecutar();
                tipo = Integer.parseInt(params[2]);
                //Llenando abonados...
                SoapObject data = (SoapObject)r.getProperty(0);
                int ncoincidencias = data.getPropertyCount()/(tipo ==4?6:5);
                //setClientes(new Abonado[ncoincidencias]);
                for (int i=0; i<1; i++) {
                    switch (tipo) {
                        case 1:
                            cliente = new Abonado(
                                    null, Integer.parseInt(data.getProperty(4 + (i * 5)).toString().trim()),
                                    Integer.parseInt(data.getProperty(i * 5).toString().trim()),
                                    data.getProperty(1 + (i * 5)).toString().trim(), null,
                                    data.getProperty(2 + (i * 5)).toString().trim(), null, null, null,
                                    data.getProperty(3 + (i * 5)).toString().trim(), null
                            );
                            break;
                        case 2:
                            cliente = new Abonado(
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
                            cliente = new Abonado(
                                    null, Integer.parseInt(data.getProperty(4 + (i * 5)).toString().trim()),
                                    Integer.parseInt(data.getProperty(2 + (i * 5)).toString().trim()),
                                    data.getProperty(i * 5).toString().trim(),
                                    null,
                                    data.getProperty(1 + (i * 5)).toString().trim(), null, null, null,
                                    data.getProperty(3 + (i * 5)).toString().trim(), null
                            );
                            break;
                        case 4:
                            cliente = new Abonado(
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
                cliente.setCi(data.getPropertyAsString(0).trim());
                cliente.setCuenta(Integer.parseInt(data.getPropertyAsString(1).trim()));
                cliente.setNombre(data.getPropertyAsString(2).trim());
                cliente.setDireccion(data.getPropertyAsString(3).trim());
                cliente.setInterseccion(data.getPropertyAsString(4).trim());
                cliente.setUrbanizacion(data.getPropertyAsString(5).trim());
                cliente.setEstado(data.getPropertyAsString(6).trim());
                cliente.setGeocodigo(data.getPropertyAsString(7).trim());
                cliente.setMesesAdeudado(Integer.parseInt(data.getPropertyAsString(8).trim()));
                cliente.setDeuda(data.getPropertyAsString(9).trim());

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
                cliente.setMedidores(m);

                try{
                    data = (SoapObject) r.getProperty(3);
                    SessionManagerIngreso.getManager(getActivity().getApplicationContext()).
                            saveKey("IDREFERENCIA", data.getProperty(0).toString());
                    Log.i("Log-Guardado Cliente Buscado", "SE guardo REFERENCIA");
                }catch (Exception ignored) {
                    Log.i("Log-Guardado Cliente Buscado", "NO SE guardo REFERENCIA");
                }

            }catch (Exception e){
                toast = "Error, No se ha podido Buscar...";
                Log.e("Error : ", e.toString());
                cancel(true);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            //Rellenando coincidencias
            //t=tipo;
            rellenar();
            w.DestroyGiff(img);
            habilitarComponentes(true);
        }

        @Override
        protected void onCancelled() {
            Toast t = Toast.makeText(
                    getActivity().getApplicationContext(),
                    toast,
                    Toast.LENGTH_SHORT
            );
            t.show();
            w.DestroyGiff(img);
            habilitarComponentes(true);
        }
    }

    private class asyncSeleccion extends AsyncTask<String, Float, Object> {

        String toast="";

        @Override
        protected Object doInBackground(String... params) {
            SW acc = new SW("ingresos.wsdl", "ingresosReferenciaSeleccionada");
            acc.asignarPropiedades(
                    new Tupla[]{
                            new Tupla<String, Object>("ide", params[0])
                    }
            );
            Object r = acc.ajecutar();
            try{
                SoapObject data = (SoapObject)r;

                ArrayList<String> valores = new ArrayList<String>();
                for (int i=0 ; i<data.getPropertyCount() ; i++){
                    valores.add(data.getProperty(i).toString());
                }
                if (valores.size()>0)
                    return valores;
            }catch (Exception ignored){}
            toast = "Error, No se pudo cargar los datos requeridos";
            this.cancel(true);
            return null;
        }

        @Override
        protected void onPostExecute(Object r) {
            super.onPostExecute(r);

            ArrayList<String> valores = (ArrayList<String>)r;

            if (valores != null){
                edtFabricaRef.setText(valores.get(0));
                edtSerialRef.setText(valores.get(1));
                edtMarcaRef.setText(valores.get(2));
                edtCuentaRef.setText(valores.get(3));
                SessionManagerIngreso.getManager(getActivity().getApplicationContext())
                        .saveKey("IDACTIVIDADSELECCIONADA6","")
                        .saveKey("IDREFERENCIA",valores.get(4).replace("anyType{}",""));
            }



        }

        protected void onCancelled() {
            Toast t = Toast.makeText(
                    getActivity().getApplicationContext(),
                    toast,
                    Toast.LENGTH_SHORT
            );
            t.show();

        }
    }

    private void rellenar() {
        if (cliente != null){

            edtCuentaRef.setText(cliente.getCuenta()+"");

            for (Medidor m : cliente.getMedidores()){
                if(m.getFechaDesinst().equals("0/00/0000")){
                    edtFabricaRef.setText(m.getNumFabrica()+"");
                    edtSerialRef.setText(m.getNumSerie()+"");
                    edtMarcaRef.setText(m.getMarca()+"");
                    //lectura.setText(m.getLecturaDesinst());
                    break;
                }
            }
        }
    }



    private void habilitarComponentes(Boolean h){
        btnBuscarRef.setEnabled(h);
    }

    private boolean validar(int tipo, String dato){

        String t = null;
        switch (tipo){
            case 0:
                if(dato.isEmpty() || !isNumeric(dato) || dato.length()>8) {
                    t = "Error, Cuenta ingresada no válida";
                }
                break;

            case 1:
                if (dato.isEmpty() || !isNumeric(dato) || dato.length() > 11) {
                    t="Error, Número de medidor ingresado no válido.";
                }
                break;

            case 2:
                if (isNumeric(dato) || dato.length()>17) {
                    t="Error, Nombre ingresado no válido.";
                }
                break;

            case 3:
                String[] sp = dato.split("\\.");
                if (sp.length != 5
                        || (!isNumeric(sp[0]))
                        || (!isNumeric(sp[1]))
                        || (!isNumeric(sp[2]))
                        || (!isNumeric(sp[3]))
                        || (!isNumeric(sp[4])))

                    t="Error, Geocódigo incorrecto.";

                else if(sp[0].length() > 2 || sp[0].length() < 1
                        || sp[1].length() > 2 || sp[1].length() < 1
                        || sp[2].length() > 2 || sp[1].length() < 1
                        || sp[3].length() > 3 || sp[1].length() < 1
                        || sp[4].length() > 7 || sp[1].length() < 1)

                    t="Error, Geocódigo no válido.";
                break;

            default:
                return false;

        }
        if(t!=null) {
            (Toast.makeText(
                    getActivity().getApplicationContext(),
                    t,
                    Toast.LENGTH_LONG
            )).show();
            return false;
        }
        return true;
    }

    private boolean isNumeric(String cadena){
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }



}
