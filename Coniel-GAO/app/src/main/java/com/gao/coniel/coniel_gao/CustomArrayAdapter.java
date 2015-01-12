package com.gao.coniel.coniel_gao;

/**
 * Created by Andreita on 04/01/2015.
 */
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/*import com.danielme.tipsandroid.listviewheaders.model.Content;
import com.danielme.tipsandroid.listviewheaders.model.Header;
import com.danieme.tipsandroid.listviewheaders.R;*/

import clases.CabeceraMateriales;
import clases.ContenidoSellos;


/**
 * Custom adapter with "View Holder Pattern".
 *
 * @author danielme.com
 *
 */
public class CustomArrayAdapter extends ArrayAdapter<Object>
{

    private LayoutInflater layoutInflater;


    public CustomArrayAdapter(Context context, List<Object> objects)
    {
        super(context, 0, objects);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //header
        if (getItem(position) instanceof CabeceraMateriales)
        {
            //check if this view exists or contains a content
            if (convertView == null || convertView.findViewById(R.id.txtItem) == null || convertView.findViewById(R.id.txtSello) == null
                    || convertView.findViewById(R.id.txtUbicacion) == null)
            {
                convertView = layoutInflater.inflate(R.layout.filacabeceramateriales, null);
            }
            TextView textViewItem = (TextView) convertView.findViewById(R.id.txtItem);
            TextView textViewSello = (TextView) convertView.findViewById(R.id.txtSello);
            TextView textViewUbicacion = (TextView) convertView.findViewById(R.id.txtUbicacion);
            CabeceraMateriales header = (CabeceraMateriales) getItem(position);
            textViewItem.setText(header.getTitulo1());
            textViewSello.setText(header.getTitulo2());
            textViewUbicacion.setText(header.getTitulo3());
        }
        else //ViewHolder Pattern for content
        {
            Holder holder = null;
            //check if this view exists or contains a header
            if (convertView == null || convertView.findViewById(R.id.txtItem) != null || convertView.findViewById(R.id.txtSello) != null
                    || convertView.findViewById(R.id.txtUbicacion) != null)
            {
                holder = new Holder();

                convertView = layoutInflater.inflate(R.layout.filasmateriales, null);
                holder.setTextView1((TextView) convertView.findViewById(R.id.txtItemDes));
                holder.setTextView2((TextView) convertView.findViewById(R.id.txtSelloDes));
                holder.setTextView3((TextView) convertView.findViewById(R.id.txtUbicacionDes));
                convertView.setTag(holder);
            }
            else
            {
                holder = (Holder) convertView.getTag();
            }
           ContenidoSellos content = (ContenidoSellos) getItem(position);
            holder.getTextView1().setText(content.getDato1());
            holder.getTextView2().setText(content.getDato2());
            holder.getTextView3().setText(content.getDato3());
        }
        return convertView;
    }

}

class Holder
{

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;

    public TextView getTextView1()
    {
        return textView1;
    }

    public void setTextView1(TextView textView)
    {
        this.textView1 = textView;
    }

    public TextView getTextView2()
    {
        return textView2;
    }

    public void setTextView2(TextView textView)
    {
        this.textView2 = textView;
    }

    public TextView getTextView3()
    {
        return textView3;
    }

    public void setTextView3(TextView textView)
    {
        this.textView3 = textView;
    }

}