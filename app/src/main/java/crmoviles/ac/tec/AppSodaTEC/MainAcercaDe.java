package crmoviles.ac.tec.AppSodaTEC;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import java.util.ArrayList;

public class MainAcercaDe extends AppCompatActivity {
    private int lastExpandedPosition = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityacercade);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbarAD);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ExpandableListView elv = (ExpandableListView) findViewById(R.id.lvVisor);
        final ArrayList<dataProviderAD> dataP = addGetData();

        this.setTheme(R.style.AlertDialog);
        final adapterAD adap = new adapterAD(this,dataP);
        elv.setAdapter(adap);
        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String persona = (String) adap.getChild(groupPosition, childPosition);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainAcercaDe.this);
                builder.setTitle("Información");
                builder.setMessage(persona);
                if (persona == "Josué David Arce González") {
                    builder.setIcon(R.drawable.josue);
                } else if (persona == "Edward Andrey Murillo Castro") {
                    builder.setIcon(R.drawable.andrey);
                } else if (persona == "Esteban Gabriel Blanco Espinoza") {
                    builder.setIcon(R.drawable.esteban);
                } else if (persona == "Ing. Diego Rojas Vega") {
                    builder.setIcon(R.drawable.diego);
                } else if (persona == "comunidadaplicacionesmoviles@gmail.com") {
                    builder.setIcon(R.drawable.logo);
                }
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                    }
                });
                builder.create();
                builder.show();
                return false;
            }
        });
        elv.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                    elv.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });

        elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //here you can check whether your parent contains a child or not by getting the parent position.
                if (parent.getExpandableListAdapter().getChildrenCount(groupPosition) == 0) {
                    elv.collapseGroup(groupPosition);
                }
                return false;
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbarmad, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        //startActivity(new Intent(this,MenuComidas.class));
        return true;
    }

    private ArrayList<dataProviderAD>  addGetData()
    {
        dataProviderAD students = new dataProviderAD("Desarrolladores");
        students.datos.add("Josué David Arce González");
        students.datos.add("Esteban Gabriel Blanco Espinoza");
        students.datos.add("Edward Andrey Murillo Castro");

        dataProviderAD coordinador = new dataProviderAD("Coordinador");
        coordinador.datos.add("Ing. Diego Rojas Vega");

        dataProviderAD info = new dataProviderAD("Comunidad Dispositivos Móviles");
        info.datos.add("comunidadaplicacionesmoviles@gmail.com");

        ArrayList<dataProviderAD> all = new ArrayList<dataProviderAD>();
        all.add(students);
        all.add(coordinador);
        all.add(info);

        return all;
    }

}
