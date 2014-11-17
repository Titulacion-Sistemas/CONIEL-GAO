package com.gao.coniel.coniel_gao;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import org.ksoap2.serialization.SoapPrimitive;
import java.util.ArrayList;

import clases.SessionManager;
import serviciosWeb.SW;
import clases.Tupla;


public class Contenedor extends FragmentActivity {
    private DrawerLayout mDrawerLayout;;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] sesion= new  String[0];
    // used to store app title
    private CharSequence mTitle;
    // slide menu items
    private String[] navMenuTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setTitle("Coniel - GAO");
        getActionBar().setSubtitle(" Gestión de Actividades Operativas ");
        setContentView(R.layout.activity_contenedor);

        mTitle = getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        TypedArray navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setmDrawerList((ListView) findViewById(R.id.list_slidermenu));
        ArrayList<NavigationDrawerFragment> navDrawerItems = new ArrayList<NavigationDrawerFragment>();

        for (int i=0;i<navMenuTitles.length;i++)
            navDrawerItems.add(new NavigationDrawerFragment(navMenuTitles[i], navMenuIcons.getResourceId(i, -1)));

        //Obtener datos de sesion
        try{
            sesion = getIntent().getExtras().getStringArray("user");
        }catch (Exception e){
            Log.e("Error al Cargar datos de sesion: ",""+e);
        }

        // Recycle the typed array
        navMenuIcons.recycle();

        getmDrawerList().setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        NavigationDrawerListAdapter adapter = new NavigationDrawerListAdapter(
                getApplicationContext(),
                navDrawerItems
        );
        getmDrawerList().setAdapter(adapter);

        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(navMenuTitles[0]);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }

        setTitle(R.string.title_activity_contenedor);


        Log.i("Información", "Creada Activity Contenedor");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Información", "Continuando Activity Contenedor");
    }

    public ListView getmDrawerList() {
        return mDrawerList;
    }

    public void setmDrawerList(ListView mDrawerList) {
        this.mDrawerList = mDrawerList;
    }

    /**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    public void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new MenuPrincipal();
                break;
            case 1:
                //fragment = new ContenedorBusqueda();
                break;
            case 2:
                fragment = new ContenedorBusqueda();
                break;
            /*case 2:
                fragment = new Seccion3();
                break;*/
            case 7:
                asyncLogout acl = new asyncLogout();
                acl.execute(sesion[1], sesion[2], sesion[3]);
                return;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
            getmDrawerList().setItemChecked(position, true);
            getmDrawerList().setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(getmDrawerList());
            Log.e("Andrea", "Contenedor creado");
        }

        else {
            // error in creating fragment
            Log.e("Andrea", "Contenedor - Error cuando se creo el fragment");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    //EN SEGUNDO PLANO
    private class asyncLogout extends AsyncTask<String, Float, Integer> {

        String toast="";

        @Override
        protected Integer doInBackground(String... params) {
            SW acc = new SW("usuarios.wsdl", "logout");
            acc.asignarPropiedades(
                    new Tupla[]{
                            new Tupla<String, Object>("id", params[0]),
                            new Tupla<String, Object>("u", params[1]),
                            new Tupla<String, Object>("s", params[2])
                    }
            );
            Object r = acc.ajecutar();
            try{
                SoapPrimitive data = (SoapPrimitive)r;
                if (Boolean.parseBoolean(data+"")){
                    return 0;
                }
            }catch (Exception e){
                toast = "Error, No se ha podido Cerrar la sesión satisfactoriamente";
                this.cancel(true);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

                cerrar();

        }

        private void cerrar(){
            SessionManager.getManager(getApplicationContext())
                    .saveKey("Coniel-GAO", "")
                    .saveKey(SessionManager.LOGIN_KEY, "")
                    .saveKey(SessionManager.USER_KEY, "")
                    .saveKey(SessionManager.SESSION_KEY, "")
                    .saveKey(SessionManager.NAME_KEY, "");
            finish();
        }

        @Override
        protected void onCancelled() {
            cerrar();
            Toast t = Toast.makeText(
                    getApplicationContext(),
                    toast,
                    Toast.LENGTH_SHORT
            );
            t.show();

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
