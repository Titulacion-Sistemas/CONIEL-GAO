package com.gao.coniel.coniel_gao;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;

import clases.Abonado;
import clases.Medidor;
import clases.SessionManager;
import clases.SessionManagerIngreso;
import clases.Tupla;
import gif.decoder.GifRun;
import serviciosWeb.SW;


public class IngresoDatosAbonado extends android.support.v4.app.Fragment {

    private Abonado cliente=null;
    private SurfaceView sfvTrack;
    private String [] datosAbonados = new String[2];
    EditText cuenta,cedula, nombre, estado, telefono, lugar, calle, geocodigo, fabrica, serial, marca, lectura;
    Button btnBuscarDatos;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.activity_ingreso_datos_abonado, container, false);

        cuenta = (EditText) view.findViewById(R.id.edtCuenta);
        cedula = (EditText) view.findViewById(R.id.edtCedula);
        nombre = (EditText) view.findViewById(R.id.edtNombre);
        estado = (EditText) view.findViewById(R.id.edtEstado);
        telefono = (EditText) view.findViewById(R.id.edtTelefono);
        lugar = (EditText) view.findViewById(R.id.edtLugar);
        calle = (EditText) view.findViewById(R.id.edtCalle);
        geocodigo = (EditText) view.findViewById(R.id.edtGeocodigo);
        fabrica = (EditText) view.findViewById(R.id.edtFabrica);
        serial = (EditText) view.findViewById(R.id.edtSerial);
        marca = (EditText) view.findViewById(R.id.edtMarca);
        lectura = (EditText) view.findViewById(R.id.edtLectura);
        btnBuscarDatos = (Button) view.findViewById(R.id.btnBuscarDatos);
        sfvTrack = (SurfaceView) view.findViewById(R.id.cargandoC);
        sfvTrack.setZOrderOnTop(true);



        //Guardar Sesion para evitar cierre
        SessionManagerIngreso s = SessionManagerIngreso.getManager(getActivity().getApplicationContext());
        cuenta.setText(s.getStringKey("CUENTA"));
        cedula.setText(s.getStringKey("CEDULA"));
        nombre.setText(s.getStringKey("NOMBRE"));
        estado.setText(s.getStringKey("ESTADO"));
        telefono.setText(s.getStringKey("TELEFONO"));
        lugar.setText(s.getStringKey("LUGAR"));
        calle.setText(s.getStringKey("CALLE"));
        geocodigo.setText(s.getStringKey("GEOCODIGO"));
        fabrica.setText(s.getStringKey("FABRICA"));
        serial.setText(s.getStringKey("SERIAL"));
        marca.setText(s.getStringKey("MARCA"));
        lectura.setText(s.getStringKey("LECTURA"));

        btnBuscarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager s = SessionManager.getManager(getActivity().getApplicationContext());
                asyncBuscar asb = new asyncBuscar(sfvTrack);
                String tipo ="", dato="";
                if (validar(1, cuenta.getText()+"")){
                    tipo="1";
                    dato = cuenta.getText()+"";
                }else if (validar(2, fabrica.getText()+"")){
                    tipo="2";
                    dato = fabrica.getText()+"";
                }else if (validar(3, nombre.getText()+"")){
                    tipo="3";
                    dato = nombre.getText()+"";
                }else if (validar(4, geocodigo.getText()+"")){
                    tipo="4";
                    dato = geocodigo.getText()+"";
                }

                if (!tipo.equals("")){
                    habilitarComponentes(false);
                    asb.execute(
                            s.getStringKey(SessionManager.LOGIN_KEY),
                            s.getStringKey(SessionManager.SESSION_KEY),
                            tipo,
                            dato
                    );
                }

            }
        });

        return view;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i("Se ha ejecutado el ", "  ONATTACH");
        }

    //El Fragment ha sido quitado de su Activity y ya no está disponible
    @Override
    public void onStop(){
        super.onStop();
        Log.i("Se ha ejecutado el ", "  ONSTOP");
        //Guardar Sesion para evitar cierre
       SessionManagerIngreso.getManager(getActivity().getApplicationContext())
                .saveKey("Coniel-GAO", true)
                .saveKey("CUENTA", cuenta.getText().toString())
                .saveKey("CEDULA", cedula.getText().toString())
                .saveKey("NOMBRE", nombre.getText().toString())
                .saveKey("ESTADO", estado.getText().toString())
                .saveKey("TELEFONO", telefono.getText().toString())
                .saveKey("LUGAR", lugar.getText().toString())
                .saveKey("CALLE", calle.getText().toString())
                .saveKey("GEOCODIGO", geocodigo.getText().toString())
                .saveKey("FABRICA", fabrica.getText().toString())
                .saveKey("SERIAL", serial.getText().toString())
                .saveKey("MARCA", marca.getText().toString())
                .saveKey("LECTURA", lectura.getText().toString());
    }



    //////////////////////////////////buscar


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

    private void rellenar() {
        if (cliente != null){

            cuenta.setText(cliente.getCuenta());
            cedula.setText(cliente.getCi());
            nombre.setText(cliente.getNombre());
            estado.setText(cliente.getEstado());
            //telefono.setText(cliente.getTelefono);
            lugar.setText(cliente.getDireccion());
            calle.setText(cliente.getInterseccion());
            geocodigo.setText(cliente.getGeocodigo());

            for (Medidor m : cliente.getMedidores()){
                if(m.getFechaDesinst().equals("0/00/0000")){
                    fabrica.setText(m.getNumFabrica());
                    serial.setText(m.getNumSerie());
                    marca.setText(m.getMarca());
                    //lectura.setText(m.getLecturaDesinst());
                    break;
                }
            }
        }
    }


    private void habilitarComponentes(Boolean h){
        btnBuscarDatos.setEnabled(h);
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



    ////////////////////////////////






}
