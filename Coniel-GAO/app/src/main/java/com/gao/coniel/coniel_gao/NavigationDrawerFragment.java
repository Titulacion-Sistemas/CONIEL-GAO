package com.gao.coniel.coniel_gao;


import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class NavigationDrawerFragment {

    private String titulo;
    private int icono;

    public NavigationDrawerFragment(){}

    public NavigationDrawerFragment(String titulo, int icono){
        this.titulo = titulo;
        this.icono = icono;
    }

    public String getTitulo(){
        return this.titulo;
    }

    public int getIcono(){
        return this.icono;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public void setIcono(int icono){
        this.icono = icono;
    }

}
