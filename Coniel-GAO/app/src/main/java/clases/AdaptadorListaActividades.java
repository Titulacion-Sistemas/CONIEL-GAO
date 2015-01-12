package clases;

/**
 * Created by Andreita on 09/01/2015.
 */

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.gao.coniel.coniel_gao.R;


public class AdaptadorListaActividades extends ArrayAdapter<ItemListaActividades>{

    Activity context;
    ArrayList<ItemListaActividades> listaActividades;

    // Le pasamos al constructor el contecto y la lista de contactos

    public AdaptadorListaActividades(Activity context, ArrayList<ItemListaActividades> listaActividades) {
        super(context, R.layout.filalistaactividades, listaActividades);
        this.context = context;
        this.listaActividades = listaActividades;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        // Rescatamos cada item del listview y lo inflamos con nuestro layout
        View item = convertView;
        item = context.getLayoutInflater().inflate(R.layout.filalistaactividades, null);

        ItemListaActividades c = listaActividades.get(position);

        // Definimos los elementos que tiene nuestro layout
        EditText cuenta = (EditText) item.findViewById(R.id.edtCuentaLista);
        EditText nombre = (EditText) item.findViewById(R.id.edtNombreLista);
        EditText solicitud = (EditText) item.findViewById(R.id.edtSolicitudLista);
        EditText medidor = (EditText) item.findViewById(R.id.edtMedidorLista);

        cuenta.setText(c.getCuenta());
        nombre.setText(c.getNombre());
        solicitud.setText(c.getSolicitud());
        medidor.setText(c.getMedidor());

        return (item);
    }

}

