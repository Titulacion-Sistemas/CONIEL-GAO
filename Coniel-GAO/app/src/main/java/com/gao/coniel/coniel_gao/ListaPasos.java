package com.gao.coniel.coniel_gao;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Time;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import clases.MyLocation;
import clases.Pasos;
import clases.SessionManager;
import clases.SessionManagerIngreso;
import clases.ValidaCedula;
import clases.ValidaRucEP;
import clases.ValidaRucSociedades;
import serviciosWeb.SWAct;


public class ListaPasos extends android.support.v4.app.Fragment {

    private android.support.v4.app.Fragment[] fragmentos = new android.support.v4.app.Fragment[7];
    private OnPasoSelectedListener listener;
    private EditText observaciones;
    ListView listView, listViewActividades;
    ArrayList<Pasos> listaPasos;

    // Creamos un adapterSellos personalizado
    ListaPasosAdapter adapter;
    Boolean Tsol;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_lista_pasos, container, false);

        SessionManagerIngreso sessionManagerIngreso = SessionManagerIngreso.getManager(getActivity().getApplicationContext());
        int solicitud = sessionManagerIngreso.getIntKey("SOLICITUD");
        int idSol = Integer.parseInt(sessionManagerIngreso.getStringKey("IDSOLICITUD"));
        getActivity().setTitle("Ingreso");

        fragmentos [0] = new IngresoActividadInstalador();
        fragmentos [1] = new IngresoDatosAbonado();
        fragmentos [2] = new IngresoDetalleInstalacion();
        fragmentos [3] = new IngresoMateriales();
        if(!(solicitud==1 || idSol==11)) {
            fragmentos[4] = new IngresoMedidorInstalado();

            if (sessionManagerIngreso.getStringKey("IDACTIVIDADSELECCIONADA").equals("") &&
                    !sessionManagerIngreso.getStringKey("IDCLIENTE").equals("")
            )
                fragmentos[5] = new Galeria();

        }
        if (solicitud==0 || idSol==1) {
            fragmentos[5] = new IngresosReferencias();

            if (sessionManagerIngreso.getStringKey("IDACTIVIDADSELECCIONADA").equals("") &&
                    !sessionManagerIngreso.getStringKey("NUMFABRICABODEGA").equals("")
            )
                fragmentos[6] = new Galeria();

        }
        if (solicitud==1 || idSol==13) {
            if (sessionManagerIngreso.getStringKey("IDACTIVIDADSELECCIONADA").equals("") &&
                    !sessionManagerIngreso.getStringKey("IDCLIENTE").equals("")
                    )
                fragmentos[4] = new Galeria();
        }

        TextView text = (TextView)v.findViewById(R.id.texto);
        TextView desc = (TextView)v.findViewById(R.id.desc);
        observaciones = (EditText)v.findViewById(R.id.edtObservaciones);

        Log.i("Info - Observaciones",sessionManagerIngreso.getStringKey("OBSERVACIONESACT")+"");



        desc.setText("( Actividad Nueva )");
        if(solicitud==0 || idSol==1){
            text.setText("Servicio Nuevo");

            if(!sessionManagerIngreso.getStringKey("IDACTIVIDADSELECCIONADA").equals("")) {
                desc.setText("Editando Actividad");
                Tsol = sessionManagerIngreso.getStringKey("IDCLIENTE").trim().length()>0;
            }
            else{
                Tsol = true;
            }

            Tsol = Tsol &&
                    sessionManagerIngreso.getStringKey("IDREFERENCIA").trim().length()>0 &&
                 sessionManagerIngreso.getStringKey("NUMFABRICABODEGA").trim().length()>0;

        }else if (solicitud==1 || idSol==11){
            text.setText("Cambio de Materiales");
            if(!sessionManagerIngreso.getStringKey("IDACTIVIDADSELECCIONADA").equals(""))
                desc.setText(""+sessionManagerIngreso.getStringKey("CUENTA"));

            Tsol = sessionManagerIngreso.getStringKey("IDCLIENTE").trim().length()>0;

        }else if (solicitud==2 || idSol==13){
            text.setText("Cambio de Medidor");
            if(!sessionManagerIngreso.getStringKey("IDACTIVIDADSELECCIONADA").equals(""))
                desc.setText(""+sessionManagerIngreso.getStringKey("CUENTA"));

            Tsol = sessionManagerIngreso.getStringKey("NUMFABRICABODEGA").trim().length()>0&&
                    sessionManagerIngreso.getStringKey("IDCLIENTE").trim().length()>0;
        }

        listView = (ListView) v.findViewById(R.id.lista);
        listViewActividades = (ListView) v.findViewById(R.id.listaActividades);
        //setListAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, valores));
        listaPasos = new ArrayList<Pasos>();

        listaPasos.add(new Pasos(getResources().getDrawable(R.drawable.ic_nueva), "Actividad a realizar - Instalador encargado", "Seleccionar la actividad a realizar e instalador encargado"));
        listaPasos.add(new Pasos(getResources().getDrawable(R.drawable.ic_usu_act), "Datos de Abonado", "Digitar o Consultar datos requeridos"));
        listaPasos.add(new Pasos(getResources().getDrawable(R.drawable.ic_detalle), "Detalle de Instalación", "Seleccionar o Digitar el detalle de la instalación"));
        listaPasos.add(new Pasos(getResources().getDrawable(R.drawable.ic_materiales), "Materiales", "Digitar el material utilizado"));
        if (!(solicitud==1 || idSol==11)) {
            listaPasos.add(new Pasos(getResources().getDrawable(R.drawable.ic_medidor), "Medidor Instalado", "Digitar el medidor instalado"));

            if (sessionManagerIngreso.getStringKey("IDACTIVIDADSELECCIONADA").equals("") &&
                    !sessionManagerIngreso.getStringKey("IDCLIENTE").equals("")
                    )
                listaPasos.add(new Pasos(getResources().getDrawable(R.drawable.ic_action_camera), "Fotografias", "Para comprobar la actividad realizada"));
        }
        if (solicitud==0 || idSol==1) {
            listaPasos.add(new Pasos(getResources().getDrawable(R.drawable.ic_referencia), "Referencias", "Digitar nro° de medidor de referencia"));

            if (sessionManagerIngreso.getStringKey("IDACTIVIDADSELECCIONADA").equals("") &&
                    !sessionManagerIngreso.getStringKey("NUMFABRICABODEGA").equals("")
                    )
                listaPasos.add(new Pasos(getResources().getDrawable(R.drawable.ic_action_camera), "Fotografias", "Para comprobar la actividad realizada"));
        }
        if (solicitud==1 || idSol==13) {
            if (sessionManagerIngreso.getStringKey("IDACTIVIDADSELECCIONADA").equals("") &&
                    !sessionManagerIngreso.getStringKey("IDCLIENTE").equals("")
                    )
                listaPasos.add(new Pasos(getResources().getDrawable(R.drawable.ic_action_camera), "Fotografias", "Para comprobar la actividad realizada"));
        }

        adapter = new ListaPasosAdapter(getActivity(), listaPasos);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int ide = (int)id;
                if(fragmentos[position] instanceof Galeria) {
                    SessionManagerIngreso s = SessionManagerIngreso.getManager(getActivity().getApplicationContext());
                    String fecha="tmp",
                            carpeta="tmpc",
                            contrato = SessionManager.getManager(getActivity().
                                    getApplicationContext()).getStringKey("contrato");

                    Date today = new Date();
                    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
                    fecha = DATE_FORMAT.format(today);

                    if (position==5 || position==4){
                        carpeta=s.getStringKey("CUENTA");
                    }else{
                        carpeta="S-N "+s.getStringKey("NUMFABRICABODEGA");
                    }
                    Bundle parametro = new Bundle();
                    File f = new File(
                            Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_DCIM
                            ) + "/CONIEL/"+
                                    contrato+"/" +
                                    fecha + "/" +
                                    carpeta + "/"
                    );
                    f.mkdirs();
                    s.saveKey("RUTAACT", f.getPath());
                    parametro.putString("ruta", f.getPath());
                    fragmentos[position].setArguments(parametro);
                }
                listener.OnPasoSelected(fragmentos[position]);
                Log.e("Item seleccionado", String.valueOf(+ide));
            }
        });

        listaPasos = new ArrayList<Pasos>();
        // Al adapterSellos personalizado le pasamos el contexto y la lista que contiene
        // Añadimos el adapterSellos al listview
        adapter = new ListaPasosAdapter(getActivity(), listaPasos);
        listViewActividades.setAdapter(adapter);
        //setListAdapter(adapterSellos);
        listaPasos.add(new Pasos(getResources().getDrawable(R.drawable.ic_action_copy), "Actividad Realizadas por el usuario", "Seleccionar una actividad para modificar su contenido"));

        listViewActividades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //int ide = (int)id;
                listener.OnPasoSelected(new ListaActividades());
                Log.e("Item seleccionado", "Lista de Actividades...");
            }
        });

        Button guar = (Button)v.findViewById(R.id.btnGuardar);
        guar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SessionManagerIngreso s = SessionManagerIngreso.getManager(
                        getActivity().
                                getApplicationContext()
                );

                Log.w("A wardar", Tsol+"");
                String er = null;
                if (fragmentos[5] instanceof IngresosReferencias){
                    String c = s.getStringKey("CEDULA").trim();
                    if (c.length()==10){
                        ValidaCedula vc = new ValidaCedula();
                        if (!vc.validacionCedula(c))
                            er = "Cédula de Abonado no válida";
                    }
                    else if (c.length()==13){
                        ValidaRucSociedades vrs = new ValidaRucSociedades();
                        ValidaRucEP vrep = new ValidaRucEP();
                        if (!(vrs.validacionRUC(c) || vrep.validaRucEP(c)))
                            er = "Ruc no válido";
                    }else {
                        er = "Valor de cédula no válido";
                    }
                }

                if(
                    !s.getStringKey("OBJINSTALADOR").trim().equals("") &&
                    //!s.getStringKey("CEDULA").trim().equals("") &&
                    er == null &&
                    !s.getStringKey("OBJESTADOINST").trim().equals("") &&
                    s.getListKey("LISTAMATERIALES").size()>0 &&
                    s.getListKey("LISTASELLOS").size()>0 &&
                    Tsol
                ) {

                    try {

                        MyLocation.LocationResult locationResult = new MyLocation.LocationResult(){
                            @Override
                            public void gotLocation(Location location){
                                //Got the location!
                                if(location!=null) {

                                    Log.i("Ultima localización conocida:", "Latitud: " + location.getLatitude());
                                    Log.i("Ultima localización conocida:", "Longitud: " + location.getLongitude());

                                    asyncLoad al = new asyncLoad();

                                    Time today = new Time(Time.getCurrentTimezone());
                                    today.setToNow();
                                    String dia = today.monthDay+"";
                                    String mes = (today.month+1)+"";
                                    String anio = today.year+"";
                                    String hora = today.format("%k:%M:%S")+"";
                                    al.execute(
                                            SessionManager.getManager(getActivity()
                                                    .getApplicationContext())
                                                    .getStringKey(SessionManager.LOGIN_KEY),
                                            anio+"-"+mes+"-"+dia+" "+hora+"-05",
                                            location.getLatitude() + "",
                                            location.getLongitude() + ""
                                    );

                                }else{
                                    error(
                                            "Error de geolocalización, Por favor active el GPS en su dispositivo"
                                    );
                                }

                            }
                        };
                        MyLocation myLocation = new MyLocation();
                        myLocation.getLocation(getActivity().getApplicationContext(), locationResult);


                    }catch (Exception ignored){
                        ignored.printStackTrace();
                        error(null);
                    }

                }else{
                    error(er);
                }
            }
        });

        Button eli = (Button)v.findViewById(R.id.btnEliminarAct);
        if(sessionManagerIngreso.getStringKey("IDACTIVIDADSELECCIONADA").equals("")){
            eli.setText("Vaciar Formularios");
        }

        eli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManagerIngreso sessionManagerIngreso = SessionManagerIngreso.getManager(getActivity().getApplicationContext());
                if(!sessionManagerIngreso.getStringKey("IDACTIVIDADSELECCIONADA").equals("")) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setMessage("¿Desea eliminar la actividad?");
                    alertDialog.setTitle("Eliminar Actividad");
                    alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                    alertDialog.setCancelable(false);
                    alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            asyncElim ae = new asyncElim();
                            ae.execute(
                                    SessionManagerIngreso.getManager(getActivity().getApplicationContext())
                                            .getStringKey("IDACTIVIDADSELECCIONADA")
                            );
                        }
                    });
                    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //código java si se ha pulsado no
                        }
                    });
                    alertDialog.show();

                }
                else{
                    resetFormulario();
                    observaciones.setText("");
                    Toast t = Toast.makeText(
                            getActivity().getApplicationContext(),
                            "Se ha vaciado los formularios......",
                            Toast.LENGTH_SHORT
                    );
                    t.show();
                }
            }
        });

        observaciones.setText(sessionManagerIngreso.getStringKey("OBSERVACIONESACT")+"");

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        observaciones.setText(
                SessionManagerIngreso.getManager(getActivity().getApplicationContext())
                        .getStringKey("OBSERVACIONESACT")+""
        );
    }

    private void error(String data) {
        if(data==null)
            data="No se puede guardar aun, verifique los datos de actividad que desea " +
                "guardar, su conexión a internet y el GPS...";

        Toast t = Toast.makeText(
                getActivity().getApplicationContext(),
                data,
                Toast.LENGTH_LONG
        );
        t.show();
    }


    public interface OnPasoSelectedListener {
        public void OnPasoSelected(android.support.v4.app.Fragment id);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnPasoSelectedListener) activity;
        } catch (ClassCastException ignored) {}
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        int ide = (int)id;
        listener.OnPasoSelected(fragmentos[position]);
        Log.e("Item seleccionado", String.valueOf(+ide));
    }





    //EN SEGUNDO PLANO
    private class asyncLoad extends AsyncTask<String, Float, Object> {

        String toast="";
        SessionManagerIngreso s;

        @Override
        protected Object doInBackground(String... params) {
            s = SessionManagerIngreso.getManager(
                    getActivity().
                    getApplicationContext()
            );

            SoapObject  contenedor  = new SoapObject();
            SoapObject  cero  = new SoapObject();
            SoapObject  uno  = new SoapObject();
            SoapObject  dos  = new SoapObject();
            SoapObject  tres  = new SoapObject();
            SoapObject  cuatro  = new SoapObject();

            cero.addProperty("name0", s.getStringKey("OBJSOLICITUD"));
            cero.addProperty("name1", s.getStringKey("IDCLIENTE"));
            cero.addProperty("name2", s.getStringKey("IDACTIVIDADSELECCIONADA"));
            cero.addProperty("name3", s.getStringKey("LECTURA"));
            cero.addProperty("name4", s.getStringKey("CEDULA"));
            cero.addProperty("name5", s.getStringKey("NOMBRE"));
            cero.addProperty("name6", s.getStringKey("TELEFONO"));
            cero.addProperty("name7", s.getStringKey("IDREFERENCIA"));
            cero.addProperty("name8", s.getStringKey("FABRICAREF"));
            cero.addProperty("name9", s.getStringKey("FABRICA"));
            cero.addProperty("name10", s.getStringKey("CUENTA"));
            cero.addProperty("name11", SessionManager.getManager(getActivity()).getStringKey("contrato"));
            cero.addProperty("name12", s.getStringKey("OBJTIPOCONST"));
            cero.addProperty("name13", s.getStringKey("OBJINSTALADOR"));
            cero.addProperty("name14", s.getStringKey("OBJCUADRILLA"));
            cero.addProperty("name15", s.getStringKey("OBJUBICACIONMED"));
            cero.addProperty("name16", s.getStringKey("OBJCLASERED"));
            cero.addProperty("name17", s.getStringKey("OBJNIVELSOCIO"));
            cero.addProperty("name18", s.getStringKey("OBJCALIBRERED"));
            cero.addProperty("name19", s.getStringKey("OBJESTADOINST"));
            cero.addProperty("name20", s.getStringKey("OBJTIPOACOMETIDA"));
            cero.addProperty("name21", s.getStringKey("OBJTIPOSERVICIO"));
            cero.addProperty("name22", s.getStringKey("FECHA"));
            cero.addProperty("name23", s.getStringKey("HORA"));
            cero.addProperty("name24", s.getStringKey("OBJUSOENERGIA"));
            cero.addProperty("name25", s.getStringKey("OBJUSOINMUEBLE"));
            cero.addProperty("name26", s.getStringKey("OBJFORMACONEXION"));
            cero.addProperty("name27", s.getStringKey("OBJDEMANDA"));
            cero.addProperty("name28", "2  ");
            cero.addProperty("name29", s.getStringKey("OBJSOLICITUD"));
            cero.addProperty("name30", s.getStringKey("OBJMATERIALRED"));
            cero.addProperty("name31", observaciones.getText()+"");
            cero.addProperty("name32", s.getStringKey("SERIAL"));
            cero.addProperty("name33", s.getStringKey("NUMFABRICABODEGA"));
            cero.addProperty("name34", s.getStringKey("SERIEBODEGA"));
            cero.addProperty("name35", s.getStringKey("LECTURABODEGA"));
            cero.addProperty("name36", s.getBooleanKey("CHECKCONTRASTACION")+"");
            cero.addProperty("name37", s.getBooleanKey("CHECKREUBICACION")+"");
            cero.addProperty("name38", s.getBooleanKey("CHECKDIRECTO")+"");
            cero.addProperty("name39", params[0]);
            cero.addProperty("name40", params[1]);
            cero.addProperty("name41", params[2]);
            cero.addProperty("name42", params[3]);

            ArrayList<String[]> listaSellos = s.getListKey("LISTASELLOS");
            for (int i=0; i<listaSellos.size(); i++){
                uno.addProperty("name"+i, listaSellos.get(i)[1]);
                dos.addProperty("name" + i, listaSellos.get(i)[2]);
            }

            ArrayList<String[]> listaMateriales = s.getListKey("LISTAMATERIALES");
            for (int i=0; i<listaMateriales.size(); i++){
                tres.addProperty("name"+i, listaMateriales.get(i)[2]);
                cuatro.addProperty("name"+i, listaMateriales.get(i)[1]);
            }

            contenedor.addProperty("name0", cero);
            contenedor.addProperty("name1", uno);
            contenedor.addProperty("name2", dos);
            contenedor.addProperty("name3", tres);
            contenedor.addProperty("name4", cuatro);

            PropertyInfo param = new PropertyInfo();
            param.setValue(contenedor);
            param.setName("datas");


            SWAct acc = new SWAct("ingresos.wsdl", "guardarActividad");
            acc.asignarPropiedades(
                    param
            );

            try{
                Object r = acc.ajecutar();
                return r;
            }catch (Exception e){
                toast = "Error, No se pudo cargar los datos requeridos";
                this.cancel(true);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object r) {
            super.onPostExecute(r);

            System.out.print(r);
            if (r != null) {
                SoapObject data = (SoapObject) r;
                System.out.print(data);

                try {
                    if (Integer.parseInt(data.getProperty(0).toString()) > 0) {

                        try {
                            if (!s.getStringKey("RUTAACT").equals("")) {
                                File[] imagenes = new File(s.getStringKey("RUTAACT")).listFiles();
                                for (File i : imagenes) {
                                    String path = (i.getPath());
                                    path = path.substring(path.length() - 3, path.length());
                                    Log.i("Extencion de Archivo", path);
                                    if (path.equals("jpg")) {
                                        asyncFotos af = new asyncFotos();
                                        af.execute(
                                                data.getProperty(0).toString(),
                                                i.getPath()
                                        );
                                    }
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error-Enviar-Imagen", e.getMessage());
                        }

                        resetFormulario();

                        observaciones.setText("");
                        Toast t = Toast.makeText(
                                getActivity().getApplicationContext(),
                                data.getProperty(1).toString(),
                                Toast.LENGTH_SHORT
                        );
                        t.show();
                    }
                } catch (Exception e) {
                    Toast t = Toast.makeText(
                            getActivity().getApplicationContext(),
                            "Error al guardar la actividad solicitada..., vuelva a intentarlo más tarde...",
                            Toast.LENGTH_SHORT
                    );
                    t.show();
                }
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


    private String imgToStr(String path){
        try {
            Bitmap bm = BitmapFactory.decodeFile(path);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
            byte[] b = baos.toByteArray();
            return Base64.encodeToString(b, Base64.DEFAULT);
        }catch (Exception e){
            Log.e("Error-Transformando imagen...", e.getMessage());
            return null;
        }
    }


    //ENVIAR FOTOS EN SEGUNDO PLANO
    private class asyncFotos extends AsyncTask<String, Float, Object> {


        @Override
        protected Object doInBackground(String... params) {

            try {

                PropertyInfo param = new PropertyInfo();
                param.setValue(params[0]);
                param.setName("pk");

                PropertyInfo param1 = new PropertyInfo();
                param1.setValue(imgToStr(params[1]));
                param1.setName("strFoto");

                PropertyInfo param2 = new PropertyInfo();
                String[] nombre = params[1].split("/");
                param2.setValue(nombre[nombre.length-1].replace(".jpg",".png"));
                param2.setName("nombreFoto");

                SWAct acc = new SWAct("ingresos.wsdl", "guardarFoto");
                acc.asignarPropiedades(param1);
                acc.asignarPropiedades(param);
                acc.asignarPropiedades(param2);
                return acc.ajecutar();

            }catch (Exception e){
                Log.e("Error al ejecurar SW", "Error, No se pudo cargar los datos requeridos: "+e);
                this.cancel(true);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object r) {
            super.onPostExecute(r);
            System.out.print(r);
            try {

                String data = (String)r;
                System.out.print(data);

                if(!data.equals("")){

                    Toast t = Toast.makeText(
                            getActivity().getApplicationContext(),
                            data,
                            Toast.LENGTH_SHORT
                    );
                    t.show();
                }
            }catch (Exception e){
                Log.e("Error al ejecurar On Post", ""+e.getMessage());
            }
        }

        protected void onCancelled() {
            Log.w("Cancelled", "asyncFoto - Cancelado");
        }
    }



    private class asyncElim extends AsyncTask<String, Float, Object> {

        String toast="";

        @Override
        protected Object doInBackground(String... params) {

            PropertyInfo param = new PropertyInfo();
            param.setValue(params[0]);
            param.setName("pk");

            SWAct acc = new SWAct("ingresos.wsdl", "eliminarActividad");
            acc.asignarPropiedades(
                    param
            );
            Object r = acc.ajecutar();
            try{
                return r;
            }catch (Exception e){
                toast = "Error, No se pudo cargar los datos requeridos";
                this.cancel(true);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object r) {
            super.onPostExecute(r);

            System.out.print(r);

            SoapObject data = (SoapObject)r;
            System.out.print(data);

            try {
                if(Integer.parseInt(data.getProperty(0).toString())>=0){
                    resetFormulario();
                    observaciones.setText("");
                    Toast t = Toast.makeText(
                            getActivity().getApplicationContext(),
                            data.getProperty(1).toString(),
                            Toast.LENGTH_SHORT
                    );
                    t.show();

                }
            }catch (Exception e){
                Toast t = Toast.makeText(
                        getActivity().getApplicationContext(),
                        "Error al eliminar la actividad solicitada..., vuelva a intentarlo...",
                        Toast.LENGTH_SHORT
                );
                t.show();
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


    @Override
    public void onDetach() {
        super.onDetach();

        ActionBar bar = getActivity().getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        bar.removeAllTabs();
        bar.setTitle(R.string.title_activity_contenedor);
        bar.setSubtitle(" Gestión de Actividades Operativas ");
        try {
            Contenedor c = ((Contenedor)getActivity());
            c.getmDrawerList().setItemChecked(0, true);
            c.getmDrawerList().setSelection(0);
            c.setTitle(R.string.title_activity_contenedor);
        }catch (Exception ignored){}

        Log.i("Información", "Dtach de ListaPasos");
    }

    @Override
    public void onStop() {
        super.onStop();
        SessionManagerIngreso.getManager(getActivity().getApplicationContext()).saveKey(
                "OBSERVACIONESACT",
                observaciones.getText()+""
        );
    }

    private void resetFormulario(){
        SessionManagerIngreso.getManager(
                getActivity().
                        getApplicationContext()
        ).borrarDatos();
        for (int i = 4; i < fragmentos.length; i++) {
            if(fragmentos[i] instanceof Galeria) {
                fragmentos[i] = null;
                ListaPasosAdapter la = (ListaPasosAdapter)listView.getAdapter();
                la.remove(la.getItem(i));
                la.notifyDataSetChanged();
                break;
            }
        }

    }

}
