package com.gao.coniel.coniel_gao;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;

import gif.decoder.GifRun;
import serviciosWeb.SW;
import serviciosWeb.Tupla;


public class Login extends Activity {

    static String name = "andrea";
    static String pass = "1234";
    EditText editName;
    EditText editPass;
    Button btnInicio;
    SurfaceView sfvTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editName = (EditText) findViewById(R.id.txtusuario);
        editPass = (EditText) findViewById(R.id.txtclave);
        btnInicio = (Button) findViewById(R.id.btnInicio);

        sfvTrack  = (SurfaceView)findViewById(R.id.cargando);
        sfvTrack.setZOrderOnTop(true);    // necessary
        SurfaceHolder sfhTrackHolder = sfvTrack.getHolder();
        sfhTrackHolder.setFormat(PixelFormat.TRANSPARENT);

        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                asyncLogin auth = new asyncLogin(
//                        sfvTrack
//                );
//
//                auth.execute(
//                        editName.getText().toString(),
//                        editPass.getText().toString()
//                );

                if(editName.getText().toString().equals(name) && editPass.getText().toString().equals(pass)){

                    Intent intent = new Intent(Login.this , Contenedor.class);
                    startActivity(intent);
                    finish();

                }
                else{
    //                    Toast.makeText(getApplicationContext(), "El usuario introducido no es correcto", Toast.LENGTH_LONG).show();
                       alerta("Nombre de Usuario o Contraseña incorrecta");
                    }
                }

        });
    }


    public void alerta(String cadena) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(cadena);
        dialog.setCancelable(true).setTitle("Error de Inicio de Sesión");
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog,int id){
                        dialog.cancel();
                    }
                });
                dialog.create().show();
    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the Menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }





    //EN SEGUNDO PLANO
    private class asyncLogin extends AsyncTask<String, Float, Integer>{

        private SurfaceView img;
        private String toast="Bienvenido(a) ";
        private String[] datos_de_Sesion=null;
        GifRun w;

        private asyncLogin(SurfaceView imgCargando){
            img = imgCargando;
            w = new  GifRun();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            w.LoadGiff(img, getApplicationContext(), R.drawable.gifload);
        }

        @Override
        protected Integer doInBackground(String... params) {
            SW acc = new SW("usuarios.wsdl", "login");
            acc.asignarPropiedades(
                new Tupla[]{
                        new Tupla<String, Object>("u", params[0]),
                        new Tupla<String, Object>("p", params[1]),
                }
            );
            Object r = acc.ajecutar();
            try{
                SoapObject data = (SoapObject)r;
                datos_de_Sesion = new String[data.getPropertyCount()];
                for (int i=0 ; i<data.getPropertyCount() ; i++){
                    datos_de_Sesion[i]=""+data.getProperty(i);
                }
            }catch (Exception e){
                toast = "Error, No se ha podido Iniciar Sesion, Verifique su conexión a internet y vuelva a intentarlo";
                this.cancel(true);
            }

        return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if (datos_de_Sesion.length>0){
                if (datos_de_Sesion[0].equals("True")){
                    toast+=datos_de_Sesion[4];
                    Intent intent = new Intent(Login.this , Contenedor.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    toast = datos_de_Sesion[0];
                }

                Toast t = Toast.makeText(
                        getApplicationContext(),
                        toast,
                        Toast.LENGTH_SHORT
                );
                t.show();
            }
            w.DestroyGiff(img);
        }

        @Override
        protected void onCancelled() {
            w.DestroyGiff(img);
        }
    }
}




