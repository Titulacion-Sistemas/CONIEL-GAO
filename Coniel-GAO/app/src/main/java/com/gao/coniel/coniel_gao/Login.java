package com.gao.coniel.coniel_gao;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Login extends Activity {

    static String name = "andrea";
    static String pass = "1234";
    EditText editName;
    EditText editPass;
    Button btnInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editName = (EditText) findViewById(R.id.txtusuario);
        editPass = (EditText) findViewById(R.id.txtclave);
        btnInicio = (Button) findViewById(R.id.btnInicio);

        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editName.getText().toString().equals(name) && editPass.getText().toString().equals(pass)){
                    Intent intent = new Intent(Login.this , Contenedor.class);
                    startActivity(intent);
                    finish();
                   /* Intent intent = new Intent(activity_menu.this, Menu.class);
                        startActivity(intent);
                        finish();*/
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
