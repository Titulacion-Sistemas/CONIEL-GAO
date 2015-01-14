package clases;

/**
 * Created by Andreita on 09/01/2015.
 */

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.gao.coniel.coniel_gao.R;

import java.util.ArrayList;


public class AdaptadorListaActividades extends ArrayAdapter<ItemListaActividades>{

    Activity context;
    ArrayList<ItemListaActividades> listaActividades;

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


        if (position==0){
            item.setBackgroundColor(Color.LTGRAY);
        }else {
            cuenta.setTextSize(15);
            nombre.setTextSize(15);
            solicitud.setTextSize(15);
            medidor.setTextSize(15);
        }

        cuenta.setText(c.getCuenta());
        nombre.setText(c.getNombre());
        solicitud.setText(c.getSolicitud());
        medidor.setText(c.getMedidor());

        return (item);
    }

}

