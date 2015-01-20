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
    public View getView(final int position, View convertView, final ViewGroup parent)
    {

        // Rescatamos cada item del listview y lo inflamos con nuestro layout
        View item = convertView;
        item = context.getLayoutInflater().inflate(R.layout.filasmateriales, null);

        ContenidoSellos c = contenidoSello.get(position);

        // Definimos los elementos que tiene nuestro layout

        EditText sellos = (EditText) item.findViewById(R.id.edtSelloDes);
        EditText ubicacion = (EditText) item.findViewById(R.id.edtUbicacionDes);
        ImageButton btnQuitar = (ImageButton) item.findViewById(R.id.btnQuitar);

        btnQuitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner sp = (Spinner) context.findViewById(R.id.spinnerSellos);
                ArrayAdapter<String> miad = (ArrayAdapter<String>)sp.getAdapter();
                miad.insert(
                        contenidoSello.get(position).getDato2(),
                        Integer.parseInt(contenidoSello.get(position).getDato1())
                );
                miad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp.setAdapter(miad);

                contenidoSello.remove(position);
                notifyDataSetChanged();
            }
        });


        sellos.setText(c.getDato2());
        ubicacion.setText(c.getDato3());

        return (item);
    }

}