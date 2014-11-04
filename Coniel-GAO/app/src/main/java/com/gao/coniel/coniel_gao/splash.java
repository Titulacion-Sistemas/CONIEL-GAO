package com.gao.coniel.coniel_gao;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.Window;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;

import java.util.Timer;
import java.util.TimerTask;

import gif.decoder.GifRun;
import serviciosWeb.SW;
import serviciosWeb.Tupla;


public class splash extends Activity {

    String[] contratos = new String[0];

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Hide title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        asyncContratos atc = new asyncContratos();
        atc.execute();
    }


    //EN SEGUNDO PLANO
    private class asyncContratos extends AsyncTask<String, Float, Integer> {
        public String toast;
        @Override
        protected Integer doInBackground(String... params) {
            SW acc = new SW("usuarios.wsdl", "getContratos");
            Object r = acc.ajecutar();
            Log.e("data : ",""+r);
            contratos = new String[0];
            try{
                SoapObject data = (SoapObject)r;
                contratos = new String[data.getPropertyCount()*2];
                for (int i=0 ; i<data.getPropertyCount() ; i++){
                    contratos[i*2]=""+(((SoapObject)data.getProperty(i)).getProperty(0));
                    contratos[(i*2)+1]=""+(((SoapObject)data.getProperty(i)).getProperty(1));
                    Log.e(contratos[i*2], contratos[(i*2) + 1]);
                }
            }catch (Exception e){
                toast = "Error, No se ha podido Iniciar Sesion, Verifique su conexión a internet y vuelva a intentarlo";
                Log.e("Error: ", e.toString());
                this.cancel(true);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if (contratos.length>1){
                // Start the next activity
                Intent mainIntent = new Intent().setClass(
                        splash.this, Login.class);
                mainIntent.putExtra("contratos", contratos);
                startActivity(mainIntent);
                // Close the activity so the user won't able to go back this
                // activity pressing Back button
                finish();
            }
            else {
                Toast t = Toast.makeText(
                        getApplicationContext(),
                        "No existen Contratos disponibles...",
                        Toast.LENGTH_SHORT
                );
                t.show();
                System.exit(0);
            }
        }

        @Override
        protected void onCancelled() {
            Toast t = Toast.makeText(
                    getApplicationContext(),
                    toast,
                    Toast.LENGTH_SHORT
            );
            t.show();

        }
    }


}
