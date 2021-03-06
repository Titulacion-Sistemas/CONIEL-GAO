package com.gao.coniel.coniel_gao;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import clases.GuardarFotos;


public class Galeria extends Fragment{

    GridView gridView;
    ArrayList<File> aEliminar = new ArrayList<File>();
    ArrayList<Integer> ItemaEliminar = new ArrayList<Integer>();
    String ruta;
    File mi_foto;
    View rootView;
    AlertDialog.Builder builder;
    private boolean gps_enabled=false;
    private boolean network_enabled=false;
    private DetenerBusqueda astd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        //getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActivity().getActionBar().setHomeButtonEnabled(true);
        rootView = inflater.inflate(R.layout.activity_galeria, container, false);
        ruta = getArguments().getString("ruta");
        gridView = (GridView) rootView.findViewById(R.id.gridViewGaleria);

       /* ActionBar act = getActivity().getActionBar();
        act.setIcon(R.drawable.ic_action_new_picture);*/

        //el número de columnas se calculará en función del tamaño de pantalla y la posición
        boolean bigScreen = (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            if (bigScreen)  gridView.setNumColumns(4);
            else            gridView.setNumColumns(3);
        else
            if (bigScreen)  gridView.setNumColumns(3);
            else            gridView.setNumColumns(2);

        gridView.setAdapter(new GalleryAdapter(getActivity(), ordenarPrFecha((new File(ruta)).listFiles())));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                openImg(
                    new  File(
                            ruta+"/"+((TextView) view.findViewById(R.id.txtNombreFoto)).getText().toString()+".jpg"
                    )
                );
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                File f = new  File(
                        ruta+"/"+((TextView) view.findViewById(R.id.txtNombreFoto)).getText().toString()+".jpg"
                );
                if (aEliminar.indexOf(f)==-1) {
                    aEliminar.add(f);
                    ItemaEliminar.add(position);
                    gridView.getChildAt(position).setBackgroundColor(Color.LTGRAY);
                }else{
                    aEliminar.remove(f);
                    ItemaEliminar.remove(position);
                    gridView.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
                }
                return true;
            }
        });

        String[] r = ruta.split("/");
        getActivity().setTitle(r[r.length - 1]);
        cambiarLatLong();

        try {
            getActivity().getActionBar().setHomeButtonEnabled(true);
        }catch (Exception ignored){}
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       inflater = getActivity().getMenuInflater();
       inflater.inflate(R.menu.galeria, menu);
       super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                nuevaFoto();
                return true;
            case R.id.menu_geo:
                if(getLocation()) getToast("Esperando Ubicacion...");
                else getToast("Imposible iniciar búsqueda, habilite dispositivos de ubicación......");
                return true;
            case R.id.menu_new:
                eliminarFotos();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getToast(String s){
        Toast.makeText(
                getActivity(),
                s,
                Toast.LENGTH_SHORT
        ).show();
    }

    LocationManager lm;
    public boolean getLocation()
    {
        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        try{gps_enabled=lm.isProviderEnabled(LocationManager.GPS_PROVIDER);}catch(Exception ex){}
        try{network_enabled=lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);}catch(Exception ex){}

        if(!gps_enabled && !network_enabled)
            return false;

        if(gps_enabled)
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
        if(network_enabled)
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);

        astd = new DetenerBusqueda();
        astd.execute(30000);

        return true;
    }

    LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {
            astd.cancel(true);
            actualizarLoc(location);
            lm.removeUpdates(this);
            lm.removeUpdates(locationListenerNetwork);
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            astd.cancel(true);
            actualizarLoc(location);
            lm.removeUpdates(this);
            lm.removeUpdates(locationListenerGps);
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };


    private void cambiarLatLong() {
        ((TextView) rootView.findViewById(R.id.lat)).setText(
                "Latitud : "+ GuardarFotos.getManager(new File(ruta)).getStringKey("Latitud")
        );
        ((TextView) rootView.findViewById(R.id.longit)).setText(
                "Longitud : " + GuardarFotos.getManager(new File(ruta)).getStringKey("Longitud")
        );
    }

    private void eliminarFotos() {
        if(aEliminar.size()>0) {
            builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Eliminar");
            builder.setMessage("¿Esta seguro que desea elimiar?");
            builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (File aItemaEliminar : aEliminar) {
                        aItemaEliminar.delete();
                    }
                    try {
                        gridView.setAdapter(new GalleryAdapter(getActivity(), ordenarPrFecha((new File(ruta)).listFiles())));
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
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else{
            (Toast.makeText(getActivity(),
                    " No hay elementos seleccionados para eliminar ", Toast.LENGTH_SHORT)).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            try {
                mi_foto.createNewFile();
                gridView.setAdapter(new GalleryAdapter(getActivity(), ordenarPrFecha((new File(ruta)).listFiles())));
            } catch (IOException ex) {
                Log.e("ERROR ", "catch :" + ex);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void nuevaFoto() {

        File directorioc = new File(ruta);

        //Si no existe crea la carpeta donde se guardaran las fotos
        File file = new File(directorioc, getCode() + ".jpg");

        mi_foto = new File(file + "");

        Log.e("ERROR ", "Error:" + mi_foto);

        Uri uri = Uri.fromFile(mi_foto);

        Log.i("Uri ", "Uri:" + uri);

        //Abre la camara para tomar la foto
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //Guarda imagen
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        //Retorna a la actividad
        startActivityForResult(cameraIntent, 0);

    }

    private String getCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        String date = dateFormat.format(new Date());
        Log.i("INFORMACION", "Codigo :" + date);
        return date;
    }

    void openImg(File file)
    {
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file),"image/jpeg");
        target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(target);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(),
                    "No se ha podido abrir archivo: "+file.getPath(),
                    Toast.LENGTH_SHORT
            ).show();
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

    private void actualizarLoc(Location location){
        if(location!=null){
            Log.i("Localización Obtenida:","Latitud: "+ location.getLatitude());
            Log.i("Localización Obtenida:","Longitud: "+ location.getLongitude());
            GuardarFotos.getManager(new File(ruta))
                    .saveKey("Latitud", location.getLatitude()+"")
                    .saveKey("Longitud", location.getLongitude()+"");

            cambiarLatLong();
            getToast("Exito...");

        }else{
            getToast("NO se pudo localizar el dispositivo intentelo nuevamente");
        }
    }


    private class DetenerBusqueda extends AsyncTask<Integer, Object, Object>{

        @Override
        protected Object doInBackground(Integer... params) {
            try {
                try {
                    Thread.sleep(params[0]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                lm.removeUpdates(locationListenerGps);
                lm.removeUpdates(locationListenerNetwork);

                Location net_loc = null, gps_loc = null;
                if (gps_enabled)
                    gps_loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (network_enabled)
                    net_loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                //if there are both values use the latest one
                if (gps_loc != null && net_loc != null) {
                    if (gps_loc.getTime() > net_loc.getTime())
                        actualizarLoc(gps_loc);
                    else
                        actualizarLoc(net_loc);
                    return null;
                }

                if (gps_loc != null) {
                    actualizarLoc(gps_loc);
                    return null;
                }
                if (net_loc != null) {
                    actualizarLoc(net_loc);
                    return null;
                }

            } catch (Exception e) {
                Toast.makeText(getActivity().getApplicationContext(), "Ha ocurrido un error inesperado",
                        Toast.LENGTH_LONG).show();
            }
            actualizarLoc(null);
            return null;
        }
    }
}
