package clases;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.gao.coniel.coniel_gao.R;

import java.util.ArrayList;

/**
 * Created by Andreita on 11/01/2015.
 */
public class ListaMaterialesAdapter extends ArrayAdapter<ContenidoMaterialesLista> {

    Activity context;
    ArrayList<ContenidoMaterialesLista> contenidoMaterialesLista;

    // Le pasamos al constructor el contecto y la lista de contactos
    public ListaMaterialesAdapter(Activity context, ArrayList<ContenidoMaterialesLista> contenidoMaterialesLista) {
        super(context, R.layout.filasmateriales, contenidoMaterialesLista);
        this.context = context;
        this.contenidoMaterialesLista = contenidoMaterialesLista;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // Rescatamos cada item del listview y lo inflamos con nuestro layout
        View item = convertView;
        item = context.getLayoutInflater().inflate(R.layout.filasmateriales, null);

        ContenidoMaterialesLista c = contenidoMaterialesLista.get(position);

        // Definimos los elementos que tiene nuestro layout
        //EditText numItem = (EditText) item.findViewById(R.id.edtItemDes);
        EditText descripcion = (EditText) item.findViewById(R.id.edtSelloDes);
        EditText cant = (EditText) item.findViewById(R.id.edtUbicacionDes);
        ImageButton btnQuitar = (ImageButton) item.findViewById(R.id.btnQuitar);

        btnQuitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Spinner sp = (Spinner) context.findViewById(R.id.spinnerMateriales);
                ArrayAdapter<String> miad = (ArrayAdapter<String>)sp.getAdapter();
                miad.add(contenidoMaterialesLista.get(position).getCantidad());
                miad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp.setAdapter(miad);

                contenidoMaterialesLista.remove(position);
                notifyDataSetChanged();
            }
        });

        //numItem.setText(c.getItemMateriales());
        descripcion.setText(c.getDescripcion());
        cant.setText(c.getCantidad());
        return (item);
    }
}
