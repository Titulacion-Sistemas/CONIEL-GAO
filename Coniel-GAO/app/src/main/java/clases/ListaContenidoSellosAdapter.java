package clases;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.gao.coniel.coniel_gao.R;

import java.util.ArrayList;

/**
 * Created by Andreita on 11/01/2015.
 */
public class ListaContenidoSellosAdapter extends ArrayAdapter<ContenidoSellos> {
    Activity context;
    ArrayList<ContenidoSellos> contenidoSello;

    // Le pasamos al constructor el contecto y la lista de contactos
    public ListaContenidoSellosAdapter(Activity context, ArrayList<ContenidoSellos> contenidoSello) {
        super(context, R.layout.filasmateriales, contenidoSello);
        this.context = context;
        this.contenidoSello = contenidoSello;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        // Rescatamos cada item del listview y lo inflamos con nuestro layout
        View item = convertView;
        item = context.getLayoutInflater().inflate(R.layout.filasmateriales, null);

        ContenidoSellos c = contenidoSello.get(position);

        // Definimos los elementos que tiene nuestro layout

        EditText sellos = (EditText) item.findViewById(R.id.edtSelloDes);
        EditText ubicacion = (EditText) item.findViewById(R.id.edtUbicacionDes);


        sellos.setText(c.getDato2());
        ubicacion.setText(c.getDato3());

        return (item);
    }

}
