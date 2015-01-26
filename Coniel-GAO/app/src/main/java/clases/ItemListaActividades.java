package clases;

public class ItemListaActividades {
    private String ide;
    private String cuenta;
    private String nombre;
    private String solicitud;
    private String medidor;
    private String idSolicitud;

    public ItemListaActividades(String ide, String cuenta, String nombre, String solicitud, String medidor, String idSolicitud) {
        this.ide=ide;
        this.cuenta = cuenta;
        this.nombre = nombre;
        this.solicitud = solicitud;
        this.medidor = medidor;
        this.idSolicitud = idSolicitud;
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

    public String getIde() {
        return ide;
    }

    public void setIde(String ide) {
        this.ide = ide;
    }

    public String getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }
}
