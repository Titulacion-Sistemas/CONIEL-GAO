package com.gao.coniel.coniel_gao;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import clases.GuardarFotos;
import clases.MyLocation;
import clases.SessionManager;
import clases.Tupla;
import serviciosWeb.SW;


public class CapturarFotos extends Fragment {

    private List<String> fileList = new ArrayList<String>();
    ListView listaGaleriaCuenta;
    Button boton;
    File mi_foto;
    File directorio , directorioc;
    ArrayList<Integer> aEliminar = new ArrayList<Integer>();
    View rootView;
    String contrato;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i("Info","Pasando por onCreate de CapturarFotos");

        rootView = inflater.inflate(R.layout.activity_capturar_fotos, container, false);

        //Captura de datos enviados de la actividad anterior
       final String fecha = getArguments().getString("fecha"); //getIntent().getStringExtra("fecha");

        getActivity().getActionBar().setTitle(fecha);
        getActivity().getActionBar().setSubtitle("Fotos tomadas esta fecha...");

        listaGaleriaCuenta = (ListView)rootView.findViewById(R.id.lvCuentasGaleria);

        boton = (Button) rootView.findViewById(R.id.btnSiguiente);

        contrato = SessionManager.getManager(getActivity().getApplicationContext()).getStringKey("contrato");

        directorio = new File(Environment
                .getExternalStoragePublicDirectory((Environment.DIRECTORY_DCIM) + "/CONIEL/"+contrato+"/" + fecha + "/")
                .getAbsolutePath());

        ListDir(directorio);

        //accion para el boton
        boton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView txtNumCuenta = (TextView) rootView.findViewById(R.id.editTextCuenta);

                if(! txtNumCuenta.getText().toString().equals("")) {
                    // Agregue
                    directorioc = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/CONIEL/"+contrato+"/" + fecha + "/" + txtNumCuenta.getText() + "/");
                    directorioc.mkdirs();
                    //Si no existe crea la carpeta donde se guardaran las fotos
                    File file = new File(directorioc, getCode() + ".jpg");

                    mi_foto = new File(file + "");

                    Uri uri = Uri.fromFile(mi_foto);

                    Log.e("ERROR ", "Uri:" + uri);

                    //Abre la camara para tomar la foto
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    //txtNumCuenta.setText("");

                    //Guarda imagen
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                    //Retorna a la actividad
                    startActivityForResult(cameraIntent, 0);

                }
                else{
                    Toast mensaje =
                            Toast.makeText(getActivity().getApplicationContext(),
                                    " Ingrese una Cuenta Por Favor ", Toast.LENGTH_SHORT);

                    mensaje.show();
                }
            }
        });

        listaGaleriaCuenta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textView = (TextView) view.findViewById(R.id.txtCuentaLista);

                ListaGaleriaCuentaAdapter lg = (ListaGaleriaCuentaAdapter) listaGaleriaCuenta.getAdapter();

                /*Intent i = new Intent(CapturarFotos.this , Galeria.class);
                i.putExtra("ruta", lg.itemCuenta.get(position));
                startActivity(i);*/

                Log.i("Seleccion...","Se seleccionó el elemento "+lg.itemCuenta.get(position)+" en la posicion "+position);
                Bundle parametro = new Bundle();
                parametro.putString("ruta",lg.itemCuenta.get(position));
                ((Contenedor) getActivity()).displayView(9, parametro);
              //  finish();

            }
        });

        listaGaleriaCuenta.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Button btnBorrar = (Button) rootView.findViewById(R.id.btnBorrar);
                try {
                    Log.i("Child...",""+listaGaleriaCuenta.getChildAt(position)+"");
                    if (aEliminar.indexOf((Object)position)==-1) {
                        aEliminar.add(position);
                        listaGaleriaCuenta.getChildAt(position).setBackgroundColor(Color.LTGRAY);
                        btnBorrar.setVisibility(View.VISIBLE);
                    }else{
                        aEliminar.remove((Object)position);
                        listaGaleriaCuenta.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
                        if (aEliminar.size()==0){
                            btnBorrar.setVisibility(View.INVISIBLE);
                        }
                    }
                }catch (Exception e){
                    Log.e("Error al Seleccionar",""+e.toString());
                    aEliminar=new ArrayList<Integer>();
                    ListaGaleriaCuentaAdapter lg = (ListaGaleriaCuentaAdapter) listaGaleriaCuenta.getAdapter();
                    listaGaleriaCuenta.setAdapter(lg);
                    btnBorrar.setVisibility(View.INVISIBLE);
                    getToast("Error al Seleccionar...");
                }

                return true;
            }
        });

        ((Button) rootView.findViewById(R.id.btnBorrar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarDir();
            }
        });
        return rootView;
    }

    ////////

    private void eliminarDir() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Eliminar");
        builder.setMessage("¿Esta seguro que desea elimiar el directorio y todo su contenido?");
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    ListaGaleriaCuentaAdapter lg = (ListaGaleriaCuentaAdapter) listaGaleriaCuenta.getAdapter();
                    for (Integer aItemaEliminar : aEliminar) {
                        File f = new File(
                            directorio.getPath()+
                            "/"+
                            ((TextView)(listaGaleriaCuenta.getChildAt(aItemaEliminar)).findViewById(R.id.txtCuentaLista)).getText().toString()
                        );

                        for(File foto:f.listFiles())
                            Log.i("Borro el Archivo: "+foto.getPath()+"?",foto.delete()+"");

                        Log.i("Borro el Directorio: "+f.getPath()+"?",f.delete()+"");
                        lg.remove(f.getPath());
                    }

                    listaGaleriaCuenta.setAdapter(lg);
                    //((ListaGaleriaCuentaAdapter)listaGaleriaCuenta.getAdapter()).notifyDataSetChanged();
                    aEliminar=new ArrayList<Integer>();
                } catch (Exception ex) {
                    Log.e("ERROR ", "catch :" + ex);
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Code that is executed when clicking NO
                ListaGaleriaCuentaAdapter lg = (ListaGaleriaCuentaAdapter) listaGaleriaCuenta.getAdapter();
                listaGaleriaCuenta.setAdapter(lg);
                aEliminar=new ArrayList<Integer>();
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    //Esto tambien agregue
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Comprobamos que la foto se a realizado

        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            Log.e("ERROR ", "Error:" + mi_foto);

            try {
                mi_foto.createNewFile();

                try {

                    MyLocation.LocationResult locationResult = new MyLocation.LocationResult(){
                        @Override
                        public void gotLocation(Location location){
                            //Got the location!
                            if(location!=null) {

                                Log.i("Ultima localización conocida:", "Latitud: " + location.getLatitude());
                                Log.i("Ultima localización conocida:", "Longitud: " + location.getLongitude());
                                GuardarFotos.getManager(directorioc)
                                        .saveKey("Latitud", location.getLatitude() + "")
                                        .saveKey("Longitud", location.getLongitude() + "");

                                asyncLoad al = new asyncLoad();

                                Time today = new Time(Time.getCurrentTimezone());
                                today.setToNow();
                                String dia = today.monthDay+"";
                                String mes = (today.month+1)+"";
                                String anio = today.year+"";
                                String hora = today.format("%k:%M:%S")+"";
                                al.execute(
                                        SessionManager.getManager(getActivity().getApplicationContext()).getStringKey(SessionManager.LOGIN_KEY),
                                        anio+"-"+mes+"-"+dia+" "+hora+"-05",
                                        location.getLatitude() + "",
                                        location.getLongitude() + ""
                                );



                            }else{
                                getToast("Error, Ubicación NO detectada");
                                GuardarFotos.getManager(directorioc)
                                        .saveKey("Latitud", "Por favor, actualice")
                                        .saveKey("Longitud", "datos de ubicación...");
                            }

                        }
                    };
                    MyLocation myLocation = new MyLocation();
                    myLocation.getLocation(getActivity().getApplicationContext(), locationResult);
                    getToast("Esperando ubicación...");

                }catch (Exception ignored){
                    ignored.printStackTrace();
                }

                ListaGaleriaCuentaAdapter lg = (ListaGaleriaCuentaAdapter) listaGaleriaCuenta.getAdapter();
                lg.add(directorioc.getPath());

                listaGaleriaCuenta.setAdapter(lg);

            } catch (IOException ex) {
                Log.e("ERROR ", "catch :" + ex);
            }
        }
    }

    private void getToast(String s){
        Toast.makeText(
                getActivity().getApplicationContext(),
                s,
                Toast.LENGTH_SHORT
        ).show();
    }


    private String getCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        String date = dateFormat.format(new Date());
        Log.i("INFORMACION", "Codigo :" + date);
        return date;
    }

    /////////////////////////////////

    void ListDir(File directorio){
        File[] files = ordenarPrFecha(directorio.listFiles());
        Log.i("Informacion", "valor files" +files);
        fileList.clear();
        for (File file : files) {
            fileList.add(file.getPath());
        }
        if(fileList != null) {
            listaGaleriaCuenta.setAdapter(new ListaGaleriaCuentaAdapter(getActivity().getApplicationContext(), fileList));
        }
    }

    private File[] ordenarPrFecha(File[] sortedByDate) {
        if (sortedByDate != null && sortedByDate.length > 1) {
            Arrays.sort(sortedByDate, new Comparator()
            {
                public int compare(final Object o1, final Object o2) {
                    return new Long(((File)o2).lastModified()).compareTo
                            (new Long(((File) o1).lastModified()));
                }
            });
            return sortedByDate;
        }
        return sortedByDate;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        ActionBar bar = getActivity().getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        bar.setTitle("Fotos");
        bar.setSubtitle(" Gestión de Actividades Operativas ");

        Log.i("Información", "Dtach de Capturar Fotos");
    }



    //EN SEGUNDO PLANO
    private class asyncLoad extends AsyncTask<String, Float, Object> {

        String toast="";

        @Override
        protected Object doInBackground(String... params) {
            SW acc = new SW("ingresos.wsdl", "guardarUbicacion");
            acc.asignarPropiedades(
                    new Tupla[]{
                            new Tupla<String, Object>("idUsuario", params[0]),
                            new Tupla<String, Object>("fechahora", params[1]),
                            new Tupla<String, Object>("latitud", params[2]),
                            new Tupla<String, Object>("longitud", params[3])
                    }
            );

            try{
                Object r = acc.ajecutar();
                SoapObject data = (SoapObject)r;
                Log.i("Info-Retorno", data.toString());

                    return data;

            }catch (Exception ignored){}
            toast = "Error, No se pudo cargar los datos requeridos";
            this.cancel(true);
            return null;
        }

        @Override
        protected void onPostExecute(Object r) {
            super.onPostExecute(r);
            if(r != null){
                Boolean valor = (Boolean) r;

                if (valor) {
                    getToast("La ubicación de la foto ha sido almacenada");
                } else {
                    getToast("Error, Ubicación NO detectada");
                    GuardarFotos.getManager(directorioc)
                            .saveKey("Latitud", "Por favor, actualice")
                            .saveKey("Longitud", "datos de ubicación...");
                }
            }
        }

        protected void onCancelled() {
            getToast(toast);

        }
    }


}
