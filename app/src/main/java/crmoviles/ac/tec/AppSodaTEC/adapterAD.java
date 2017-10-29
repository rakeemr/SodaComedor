package crmoviles.ac.tec.AppSodaTEC;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Josue on 22/10/2016.
 */
public class adapterAD extends BaseExpandableListAdapter {
    private Context c;
    private ArrayList<dataProviderAD> info;
    private LayoutInflater inflater;
    private MainAcercaDe aux;


    public adapterAD(Context c, ArrayList<dataProviderAD> i)
    {
        this.c = c;
        this.info = i;
        this.inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return info.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return info.get(groupPosition).datos.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return info.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return info.get(groupPosition).datos.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.parent,null);
        }
        dataProviderAD dP = (dataProviderAD) getGroup(groupPosition);
        TextView text = (TextView) convertView.findViewById(R.id.txtParent);

        String name = dP.name;
        text.setText(name);

        convertView.setBackgroundColor(Color.LTGRAY);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if(convertView==null)
        {
            convertView = inflater.inflate(R.layout.child,null);
        }

        String child = (String) getChild(groupPosition,childPosition);
        TextView text = (TextView) convertView.findViewById(R.id.txtChild);
        ImageView imgFoto = (ImageView) convertView.findViewById(R.id.imgFoto);
        text.setText(child);

        String student = getGroup(groupPosition).toString();

        if(student == "Desarrolladores")
        {
            if(child=="Josué David Arce González")
            {
                imgFoto.setImageResource(R.drawable.josue);
            }
            else if(child == "Edward Andrey Murillo Castro")
            {
                imgFoto.setImageResource(R.drawable.andrey);
            }
            else if(child == "Esteban Gabriel Blanco Espinoza")
            {
                imgFoto.setImageResource(R.drawable.esteban);
            }
        }
        else if(student == "Coordinador")
        {
            if(child == "Ing. Diego Rojas Vega")
                imgFoto.setImageResource(R.drawable.diego);
        }
        else if(student == "Comunidad Dispositivos Móviles")
        {
            if(child == "comunidadaplicacionesmoviles@gmail.com")
                imgFoto.setImageResource(R.drawable.logo);
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
