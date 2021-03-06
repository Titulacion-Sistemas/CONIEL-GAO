package com.gao.coniel.coniel_gao;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;

import java.util.Timer;
import java.util.TimerTask;

import clases.SessionManager;
import serviciosWeb.SW;


public class splash extends Activity {

    String[] contratos = new String[0];
    private static final long SPLASH_SCREEN_DELAY = 3000;
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

        if(!SessionManager.getManager(getApplicationContext()).getBooleanKey("Coniel-GAO")) {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    asyncContratos atc = new asyncContratos();
                    atc.execute();
                    finish();
                }
            };

            // Simulate a long loading process on application startup.
            Timer timer = new Timer();
            timer.schedule(task, SPLASH_SCREEN_DELAY);
        }
        else{
            Intent intent = new Intent(splash.this , Contenedor.class);
            //Guardar Sesion para evitar cierre
            SessionManager s = SessionManager.getManager(getApplicationContext());
            String [] datos_de_Sesion = {
                    s.getBooleanKey("Coniel-GAO")+"",
                    s.getStringKey(SessionManager.LOGIN_KEY),
                    s.getStringKey(SessionManager.USER_KEY),
                    s.getStringKey(SessionManager.SESSION_KEY),
                    s.getStringKey(SessionManager.NAME_KEY)
            };
            intent.putExtra("user", datos_de_Sesion);
            Toast.makeText(
                    this,
                    "Bienvenido de nuevo "+datos_de_Sesion[4],
                    Toast.LENGTH_LONG
            ).show();
            finish();
            startActivity(intent);

        }
    }


    //EN SEGUNDO PLANO
    private class asyncContratos extends AsyncTask<String, Float, Integer> {
        public String toast;

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(3500); // As I am using LENGTH_LONG in Toast
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

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
                // Close the activity so the user won'tipo able to go back this
                // activity pressing Back button
                finish();
            }
            else {
                Toast t = Toast.makeText(
                        getApplicationContext(),
                        "No existen Contratos disponibles...",
                        Toast.LENGTH_LONG
                );
                t.show();
                thread.start();
            }
        }

        @Override
        protected void onCancelled() {
            Toast t = Toast.makeText(
                    getApplicationContext(),
                    toast,
                    Toast.LENGTH_LONG
            );
            t.show();

            thread.start();
        }
    }


}
