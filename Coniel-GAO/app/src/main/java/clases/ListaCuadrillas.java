package clases;

/**
 * Created by Andreita on 20/01/2015.
 */
public class ListaCuadrillas {

    String nombre, lat,longitud;
    public ListaCuadrillas(String nombre, String lat, String longitud) {
        super();
        this.nombre = nombre;
        this.lat = lat;
        this.longitud = longitud;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getLat() {
        return Double.parseDouble(lat) ;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Double getLongitud() {
        return Double.parseDouble(longitud);
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
