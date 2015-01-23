package clases;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gao.coniel.coniel_gao.R;

import java.util.ArrayList;

/**
 * Created by Andreita on 20/01/2015.
 */
public class AdapaterSpinnerCuadrillas extends ArrayAdapter<ListaCuadrillas> {
    Activity context;
    ArrayList<ListaCuadrillas> listaCuadrillas;
    String longitud, latitud;

    // Le pasamos al constructor el contecto y la lista de contactos
    public AdapaterSpinnerCuadrillas(Activity context, ArrayList<ListaCuadrillas> listaCuadrillas) {
        super(context, R.layout.filaspcuadrillas, listaCuadrillas);
        this.context = context;
        this.listaCuadrillas = listaCuadrillas;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // Rescatamos cada item del listview y lo inflamos con nuestro layout
        View item = convertView;
        item = context.getLayoutInflater().inflate(R.layout.filaspcuadrillas, null);

        ListaCuadrillas lc = listaCuadrillas.get(position);

        // Definimos los elementos que tiene nuestro layout
        //EditText numItem = (EditText) item.findViewById(R.id.edtItemDes);
        TextView nomCuadrilla = (TextView) item.findViewById(R.id.txtCuad);

        //numItem.setText(c.getItemMateriales());
        nomCuadrilla.setText(lc.getNombre());
        longitud = lc.getLongitud()+"";
        latitud = lc.getLat()+"";
        return (item);
    }

    //de un Adapter de un ListView ordinario
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        if (row == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.filaspcuadrillas, parent, false);
        }

        if (row.getTag() == null)
        {
            CuadrillasHolder cuadrillasHolder = new CuadrillasHolder();
            //cuadrillasHolderHolder.setIcono((ImageView) row.findViewById(R.id.icono));
            cuadrillasHolder.setTextView((TextView) row.findViewById(R.id.txtCuad));
            row.setTag(cuadrillasHolder);
        }

        //rellenamos el layout con los datos de la fila que se está procesando
        ListaCuadrillas lista = listaCuadrillas.get(position);
        ((CuadrillasHolder) row.getTag()).getTextView().setText(lista.getNombre());

        return row;
    }

    /**
     * Holder para el Adapter del Spinner
     * @author danielme.com
     *
     */
    private class CuadrillasHolder
    {
        private TextView textView;

        public TextView getTextView()
        {
            return textView;
        }

        public void setTextView(TextView textView)
        {
            this.textView = textView;
        }

    }
}
