
package clases;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


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

}