package crmoviles.ac.tec.AppSodaTEC;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;

import Clases.Comidas;
import Consultas.Conexion;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;


public class MenuComidas extends AppCompatActivity {

    String baseurl = "http://172.24.4.67:80/WebServiceSodaTec";//"http://172.24.20.59:80";
    private Conexion serverApi;
    Spinner spinner1;
    ListView listaComidas;
    private ArrayList<Comidas> arrayComidas = new ArrayList<>();
    ArrayAdapter<Comidas> adapterLV;
    TextView sumaPrecios,nomEst,salEst,txtSubT;
    MenuItem btnLimpiar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_comidas);

        Toolbar myChildToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myChildToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle datos = getIntent().getExtras();
        String nombreUsuario = datos.getString("Nombre");
        int saldoUsuario = datos.getInt("Saldo");

        //this.setTheme(R.style.spinner_style);

        txtSubT = (TextView) findViewById(R.id.textoSubT);
        sumaPrecios = (TextView)findViewById(R.id.sumaPrecios);
        sumaPrecios.setText("$"+"0");
        nomEst = (TextView) findViewById(R.id.nombreEst);
        nomEst.setText(nombreUsuario);
        salEst = (TextView) findViewById(R.id.saldoEst);
        salEst.setText("$"+String.valueOf(saldoUsuario));
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        String[] opciones = {"Desayuno","Almuerzo","Cena"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.spinner_text,opciones);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinner1.setAdapter(adapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sumaPrecios.setText("0");
                String opcion = spinner1.getItemAtPosition(position).toString();
                if (opcion.equals("Desayuno"))
                    getDesayuno();
                else if (opcion.equals("Almuerzo"))
                    getAlmuerzo();
                else if (opcion.equals("Cena"))
                    getCena();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        listaComidas = (ListView) findViewById(R.id.listView);
        arrayComidas = new ArrayList<>();
        adapterLV = new MyListAdapter();
        listaComidas.setAdapter(adapterLV);
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(baseurl)
                .setClient(new OkClient(new OkHttpClient()));
        serverApi = builder.build().create(Conexion.class);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        btnLimpiar = menu.getItem(0);
        return true;
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
               startActivity(new Intent(this,Login.class));
            case R.id.about:
                startActivity(new Intent(this, MainAcercaDe.class));
                break;
            case R.id.cleanCash:
                sumaPrecios.setText("0");
                btnLimpiar.setVisible(false);
                txtSubT.setTextColor(getResources().getColor(R.color.gris));
                sumaPrecios.setTextColor(getResources().getColor(R.color.gris));
            default:
                return false;
        }

        return true;
    }
    public void getDesayuno(){
        serverApi.getDesayuno(new Callback<ArrayList<Comidas>>() {
            @Override
            public void success(ArrayList<Comidas> comidas, Response response) {
                arrayComidas = comidas;
                adapterLV = new MyListAdapter();
                listaComidas.setAdapter(adapterLV);
            }
            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "Error en la consulta de datos", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getAlmuerzo(){
        serverApi.getAlmuerzo(new Callback<ArrayList<Comidas>>() {
            @Override
            public void success(ArrayList<Comidas> comidas, Response response) {
                arrayComidas = comidas;
                adapterLV = new MyListAdapter();
                listaComidas.setAdapter(adapterLV);
            }
            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "Error en la consulta de datos", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getCena(){
        serverApi.getCena(new Callback<ArrayList<Comidas>>() {
            @Override
            public void success(ArrayList<Comidas> comidas, Response response) {
                arrayComidas = comidas;
                adapterLV = new MyListAdapter();
                listaComidas.setAdapter(adapterLV);
            }
            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "Error en la consulta de datos", Toast.LENGTH_LONG).show();
            }
        });
    }

    private class MyListAdapter extends ArrayAdapter<Comidas> {

        public MyListAdapter() {
            super(MenuComidas.this, R.layout.templateitem, arrayComidas);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.templateitem, parent, false);
            }
            Comidas CurrentComida = arrayComidas.get(position);

            final TextView comidaNombre = (TextView) itemView.findViewById(R.id.lblComida);
            comidaNombre.setText(CurrentComida.getNombre());

            final TextView comidaPrecio = (TextView) itemView.findViewById(R.id.lblPrecio);
            comidaPrecio.setText("$"+Integer.toString(CurrentComida.getPrecio()));

            ImageView image = (ImageView) itemView.findViewById(R.id.imgFotos);
            byte[] arrayBytes = CurrentComida.getImagen();
            Bitmap mapaBits = BitmapFactory.decodeByteArray(arrayBytes, 0, arrayBytes.length);
            Bitmap resizeBits = Bitmap.createScaledBitmap(mapaBits,700,700,true);
            image.setImageBitmap(getCircularBitmapWithWhiteBorder(resizeBits,10));

            final Button botonAgregar = (Button) itemView.findViewById(R.id.btnAgregar);
            botonAgregar.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    String valor = comidaPrecio.getText().toString().substring(1);
                    int sumaMonto = Integer.parseInt(sumaPrecios.getText().toString()) + Integer.parseInt(comidaPrecio.getText().toString().substring(1));
                    sumaPrecios.setText(Integer.toString(sumaMonto));
                    btnLimpiar.setVisible(true);
                    if(sumaMonto>Integer.parseInt(salEst.getText().toString().substring(1)))
                    {
                        txtSubT.setTextColor(getResources().getColor(R.color.rojo));
                        sumaPrecios.setTextColor(getResources().getColor(R.color.rojo));
                    }
                }
            });

            return itemView;

        }

        public Bitmap getCircularBitmapWithWhiteBorder(Bitmap bitmap,
                                                              int borderWidth) {
            if (bitmap == null || bitmap.isRecycled()) {
                return null;
            }

            final int width = bitmap.getWidth() + borderWidth;
            final int height = bitmap.getHeight() + borderWidth;

            Bitmap canvasBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(shader);

            Canvas canvas = new Canvas(canvasBitmap);
            float radius = width > height ? ((float) height) / 2f : ((float) width) / 2f;
            canvas.drawCircle(width / 2, height / 2, radius, paint);
            paint.setShader(null);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.TRANSPARENT);
            paint.setStrokeWidth(borderWidth);
            canvas.drawCircle(width / 2, height / 2, radius - borderWidth / 2, paint);
            return canvasBitmap;
        }
    }

}
