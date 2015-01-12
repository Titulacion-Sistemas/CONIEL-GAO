package clases;

/**
 * Created by Andreita on 09/01/2015.
 */
public class ItemListaActividades {
    private String cuenta, nombre, solicitud, medidor;

    public ItemListaActividades(String cuenta, String nombre, String solicitud, String medidor) {

        super();
        this.cuenta = cuenta;
        this.nombre = nombre;
        this.solicitud = solicitud;
        this.medidor = medidor;

    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(String solicitud) {
        this.solicitud = solicitud;
    }

    public String getMedidor() {
        return medidor;
    }

    public void setMedidor(String medidor) {
        this.medidor = medidor;
    }
}
