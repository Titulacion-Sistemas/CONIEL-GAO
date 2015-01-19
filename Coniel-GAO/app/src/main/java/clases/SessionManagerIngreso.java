
package clases;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;


public class SessionManagerIngreso {

    private static SessionManagerIngreso INSTANCE = null;
    private static String ID_STRING = "Ingresos";
    private static String LOG_TAG = "SessionManager";

    // APP STANDARD KEY VALUES
    public static final String LOGIN_KEY = "login";
    public static final String REGISTER_KEY = "register";
    public static final String USER_KEY = "username";
    public static final String EMAIL_KEY = "email";

    private static SharedPreferences prefs;
    private static SharedPreferences.Editor edit;

    private SessionManagerIngreso(Context ctx){
        prefs = ctx.getSharedPreferences(ID_STRING, Context.MODE_PRIVATE);
    }

    public static SessionManagerIngreso getManager(Context ctx) {
        if (INSTANCE == null) {
            INSTANCE = new SessionManagerIngreso(ctx);
        }
        return INSTANCE;
    }

    // saveKey methods
    /**
     * Save an integer value in the default preferences
     * @param key The name for the key-value pair, consider using one of the standard values
     * @param value The integer to be stored
     * @return The actual SessionManager singleton object
     */
    public SessionManagerIngreso saveKey(String key, int value) {

        checkStandarKey(key);

        edit = prefs.edit();
        edit.putInt(key, value);
        edit.commit();

        return INSTANCE;

    }

    /**
     * Save a boolean value in the default preferences
     * @param key The name for the key-value pair, consider using one of the standard values
     * @param value The boolean to be stored
     * @return The actual SessionManager singleton object
     */
    public SessionManagerIngreso saveKey(String key, boolean value) {

        checkStandarKey(key);

        edit = prefs.edit();
        edit.putBoolean(key, value);
        edit.commit();

        return INSTANCE;

    }

    /**
     * Save a float value in the default preferences
     * @param key The name for the key-value pair, consider using one of the standard values
     * @param value The float to be stored
     * @return The actual SessionManager singleton object
     */
    public SessionManagerIngreso saveKey(String key, float value) {

        checkStandarKey(key);

        edit = prefs.edit();
        edit.putFloat(key, value);
        edit.commit();

        return INSTANCE;

    }

    /**
     * Save a long value in the default preferences
     * @param key The name for the key-value pair, consider using one of the standard values
     * @param value The long to be stored
     * @return The actual SessionManager singleton object
     */
    public SessionManagerIngreso saveKey(String key, long value) {

        checkStandarKey(key);

        edit = prefs.edit();
        edit.putLong(key, value);
        edit.commit();

        return INSTANCE;

    }

    /**
     * Save a string in the default preferences
     * @param key The name for the key-value pair, consider using one of the standard values
     * @param value The string to be stored
     * @return The actual SessionManager singleton object
     */
    public SessionManagerIngreso saveKey(String key, String value) {

        checkStandarKey(key);

        edit = prefs.edit();
        edit.putString(key, value);
        edit.commit();

        return INSTANCE;

    }

    // getKey methods
    /**
     * Retrieve an integer value from app's default preferences
     * @param key The name of the value that is requested
     * @return The key's value or -1 if key does not exists
     */
    public int getIntKey(String key) {

        checkStandarKey(key);

        if (!prefs.contains(key)) {
            Log.e(LOG_TAG, "The requested key does not exists in preferences");
            return -1;
        }

        return prefs.getInt(key, -1);

    }

    /**
     * Retrieve a boolean value from app's default preferences
     * @param key The name of the value that is requested
     * @return The key's value or false if key does not exists
     */
    public boolean getBooleanKey(String key) {

        checkStandarKey(key);

        if (!prefs.contains(key)) {
            Log.e(LOG_TAG, "The requested key does not exists in preferences");
            return false;
        }

        return prefs.getBoolean(key, false);

    }

    /**
     * Retrieve a float value from app's default preferences
     * @param key The name of the value that is requested
     * @return The key's value or -1 if key does not exists
     */
    public float getFloatKey(String key) {

        checkStandarKey(key);

        if (!prefs.contains(key)) {
            Log.e(LOG_TAG, "The requested key does not exists in preferences");
            return -1f;
        }

        return prefs.getFloat(key, -1f);

    }

    /**
     * Retrieve a long value from app's default preferences
     * @param key The name of the value that is requested
     * @return The key's value of -1 if key does not exists
     */
    public long getLongKey(String key) {

        checkStandarKey(key);

        if (!prefs.contains(key)) {
            Log.e(LOG_TAG, "The requested key does not exists in preferences");
            return -1;
        }

        return prefs.getLong(key, -1);

    }

    /**
     * Retrieve a string value from app's default preferences
     * @param key The name of the value that is requested
     * @return The key's value or an empty string if key does not exists
     */
    public String getStringKey(String key) {

        checkStandarKey(key);

        if (!prefs.contains(key)) {
            Log.e(LOG_TAG, "The requested key does not exists in preferences");
            return "";
        }

        return prefs.getString(key, "");

    }

    /**
     * Display an error message if passed key is not part of
     * the standard key-set
     * @param key The key to test
     */
    private void checkStandarKey(String key) {
        if (!key.equals(LOGIN_KEY) &&
                !key.equals(REGISTER_KEY) &&
                !key.equals(USER_KEY) &&
                !key.equals(EMAIL_KEY)) {
            Log.w(LOG_TAG, "The passed key is not part of the standars key-set. Consider use another one of the standar set");
        }
    }



    public void borrarDatos(){
        //ACTIVIDAD A REALIZAR - INSTALADOR ENCARGADO
        saveKey("INSTALADOR",0);
        saveKey("CUADRILLA", 0);
        saveKey("SOLICITUD", 2);
        saveKey("FECHA", "");
        saveKey("HORA", "");

        //DATOS ABONADOS
        saveKey("CUENTA", "");
        saveKey("CEDULA", "");
        saveKey("NOMBRE", "");
        saveKey("ESTADO", "");
        saveKey("TELEFONO", "");
        saveKey("LUGAR", "");
        saveKey("CALLE", "");
        saveKey("GEOCODIGO", "");
        saveKey("FABRICA", "");
        saveKey("SERIAL", "");
        saveKey("MARCA", "");
        saveKey("LECTURA", "");

        //DETALLE DE INSTALACION
        saveKey("MATERIALRED", 0);
        saveKey("FORMACONEXION", 3);
        saveKey("ESTADOINST", 0);
        saveKey("TIPOCONST", 4);
        saveKey("UBICACIONMED", 4);
        saveKey("TIPOACOMETIDA", 1);
        saveKey("CALIBRERED", 9);
        saveKey("USOENERGIA", 0);
        saveKey("CLASERED", 0);
        saveKey("TIPOSERVICIO", 3);
        saveKey("USOINMUEBLE", 3);
        saveKey("DEMANDA", 0);
        saveKey("NIVELSOCIO", 1);

        //MATERIALES
        saveKey("CHECKDIRECTO", false);
        saveKey("CHECKCONTRASTACION", false);
        saveKey("CHECKREUBICACION", false);

        //MEDIDOR INSTALADO
        saveKey("MEDIDORESBODEGA", 0);
        saveKey("NUMFABRICABODEGA", "");
        saveKey("SERIEBODEGA", "");
        saveKey("MARCABODEGA", "");
        saveKey("TIPOBODEGA", "");
        saveKey("LECTURABODEGA","");

        //REFERENCIA
        saveKey("FABRICAREF", "");
        saveKey("SERIALREF", "");
        saveKey("MARCAREF", "");
        saveKey("CUENTAREF", "");

    }

    public SessionManagerIngreso saveKey(String key, ArrayList<String []> value) {


        ArrayList<String[]> variable = getListKey(key);
        if (variable != null) {
            for (int i = 0; i < variable.size(); i++) {
                for (int j = 0; j < variable.get(i).length; j++) {
                    edit.remove(key + "[" + i + "][" + j + "]");
                }
            }
        }

        if (value!=null){
            edit = prefs.edit();
            for (int i=0; i<value.size(); i++){
                for(int j=0; j<value.get(i).length;j++){
                    edit.putString(key + "[" + i + "][" + j + "]", value.get(i)[j]);
                }
            }
            edit.commit();
        }
        return INSTANCE;

    }

    public ArrayList<String[]> getListKey(String key) {
        ArrayList<String> var = new ArrayList<String>();
        ArrayList<String[]> variable = new ArrayList<String[]>();
        checkStandarKey(key);

        if (!prefs.contains(key+"[0][0]")) {
            Log.e(LOG_TAG, "The requested key does not exists in preferences");
            return null;
        }
        int i=0, j=0;

        while (prefs.contains(key+"["+i+"]["+0+"]"))
        {
            while(prefs.contains(key+"["+i+"]["+j+"]")){
                var.add(getStringKey(key+"["+i+"]["+j+"]"));
                j++;
            }
            variable.add((String[]) var.toArray());
            i++;
            j=0;
        }
        return variable;

    }



}