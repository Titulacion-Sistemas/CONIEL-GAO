package clases;

/**
 * Created by Andreita on 11/01/2015.
 */
public class ContenidoMaterialesLista {

    String itemMateriales, descripcion, cantidad;

    public ContenidoMaterialesLista(String itemMateriales, String descripcion, String cantidad){
        super();
        this.itemMateriales = itemMateriales;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }

    public String getItemMateriales() {
        return itemMateriales;
    }

    public void setItemMateriales(String itemMateriales) {
        this.itemMateriales = itemMateriales;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}
