package crmoviles.ac.tec.AppSodaTEC;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import crmoviles.ac.tec.AppSodaTEC.MenuComidas;

public class SeleccionarComida extends AppCompatActivity {

    RadioGroup radioGroup1;
    RadioButton desayuno, almuerzo, cena;
    int opcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_comida);

        radioGroup1 = (RadioGroup) findViewById(R.id.opciones);
        desayuno = (RadioButton) findViewById(R.id.desayuno);
        almuerzo = (RadioButton) findViewById(R.id.almuerzo);
        cena = (RadioButton) findViewById(R.id.cena);

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(desayuno.isChecked()){
                    opcion = 0;
                }
                if(almuerzo.isChecked()){
                    opcion = 1;
                }
                if(cena.isChecked()){
                    opcion = 2;
                }
            }

        });

        /*Intent intent = new Intent(getBaseContext(), MenuComidas.class);
        Bundle datos = new Bundle();
        datos.putInt("Opcion_Menu", opcion);
        intent.putExtras(datos);
        finish();
        startActivity(intent);*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
