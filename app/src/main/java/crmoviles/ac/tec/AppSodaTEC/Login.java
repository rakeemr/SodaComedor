package crmoviles.ac.tec.AppSodaTEC;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.net.wifi.WifiManager;

import com.squareup.okhttp.OkHttpClient;

import Clases.Persona;
import Consultas.Conexion;
import retrofit.RetrofitError;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.RestAdapter;
import retrofit.client.OkClient;


public class Login extends AppCompatActivity {

    EditText carnet, contraseña;
    String nombre;
    int id, saldo;
    TextView titulo;
    String baseurl = "http://172.19.32.10:80/WebServiceSodaTec";//"http://172.24.20.59:80";
    private Conexion serverApi;
    CheckBox estadoCB;
    Button btnLogin;
    NetworkInfo mWifi;
    WifiManager wifiManager;
    ImageView imgFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carnet = (EditText) findViewById(R.id.txtCarnet);
        contraseña = (EditText) findViewById(R.id.txtContraseña);
        titulo = (TextView) findViewById(R.id.txtTitulo);

        estadoCB =(CheckBox) findViewById(R.id.cbEstado);
        btnLogin = (Button) findViewById(R.id.btnAceptar);
        btnLogin.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttoncolorchange));
        imgFoto = (ImageView) findViewById(R.id.btimgImagenFondo);

        // poner los metodos para escuchar acciones
        carnet.addTextChangedListener(contadorLetras);
        contraseña.addTextChangedListener(contadorLetras);

        cargarDatos();
        tituloSODATEC();


        // abre el metodo
        verificarEspaciosVacios();
        estadoCB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                guardarDatos();
            }
        });

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(baseurl)
                .setClient(new OkClient(new OkHttpClient()));
        serverApi = builder.build().create(Conexion.class);
    }



    public void tituloSODATEC()
    {
        Spannable primera = new SpannableString("Soda");

        primera.setSpan(new ForegroundColorSpan(Color.rgb(11,7,95)), 0, primera.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        titulo.setText(primera);
        Spannable segunda = new SpannableString("|");

        segunda.setSpan(new ForegroundColorSpan(Color.rgb(11,7,95)), 0, segunda.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        titulo.append(segunda);

        Spannable tercera = new SpannableString("TEC");
        tercera.setSpan(new ForegroundColorSpan(Color.rgb(11,7,95)),0,tercera.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        titulo.append(tercera);
    }

    /*
    metodo que verifica la cantidad de letras ingresadas por el usuario
    despues de haber llenado ambos cuadros de texto se llama el boton verificarEspacioVacio
     */
    private TextWatcher contadorLetras = new TextWatcher() {
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // check Fields For Empty Values
            verificarEspaciosVacios();
        }
    };

    void verificarEspaciosVacios() {
        Button btnAceptar = (Button) findViewById(R.id.btnAceptar);
        if(carnet.length()==10 && contraseña.length()==10) {
            btnAceptar.setEnabled(true);
        }
        else {
            btnAceptar.setEnabled(false);
        }
    }

    /*private boolean verificarConexionWifi() {
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        if (wifiManager.isWifiEnabled()) { // Wi-Fi está prendido

            WifiInfo wifiInfo = wifiManager.getConnectionInfo();

            if( wifiInfo.getNetworkId() == -1 ){
                return false; //  no hay conexion con ningun wifi
            }
            return true; // Hay conexion a una red
        }
        else {
            return false; // adaptador wifi está apagado
        }
    }//verifica si el adaptador wifi está encendido y si al menos está conectado a una red
    public boolean verificarRedWiFi()//verifica que la red sea la del tec
    {
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo;
        String idRed = "";

        wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo.getSupplicantState() == SupplicantState.COMPLETED) {
            idRed = wifiInfo.getSSID();
            idRed = idRed.replaceAll("\"", "");
            if(idRed.equals("") || !idRed.equals("wTEC-Estudiantes"))
                return false;
            else if(idRed.equals("wTEC-Estudiantes"))//aqui va el SSID de la red
                return true;

        }
        return false;
    }*/

    /*
        metodo que busca en la base de datos y verifica si el id ingresado es correcto
        este metodo funciona cuando el boton aceptar es presionado
     */
    public void getPersonaInfo(View view){
        try{
            int id = Integer.parseInt(carnet.getText().toString());
            int pass = Integer.parseInt(contraseña.getText().toString());
            if(id!=pass)
            {
                Toast.makeText(getApplicationContext(),"El Usuario y la Contraseña no coinciden",Toast.LENGTH_LONG).show();
            }
            else if(id==pass) //&& verificarConexionWifi() && verificarRedWiFi())
            {
                serverApi.getPersona(id, new Callback<Persona>() {
                    @Override
                    public void success(Persona persona, Response response) {
                        if(persona!=null) {
                            nombre = persona.getNombre();
                            saldo = persona.getSaldo();
                            Toast.makeText(getApplicationContext(),"Bienvenido "+nombre,Toast.LENGTH_LONG).show();
                            Intent datos = new Intent(getBaseContext(), MenuComidas.class);
                            datos.putExtra("Nombre", nombre);
                            datos.putExtra("Saldo", saldo);
                            startActivity(datos);
                        }
                        else
                            Toast.makeText(getApplicationContext(), "No existe ese usuario", Toast.LENGTH_LONG).show();

                    }
                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getApplicationContext(), "Error en la consulta de datos", Toast.LENGTH_LONG).show();
                    }
                });
            }
            /*else if(!verificarConexionWifi())
            {
                Toast.makeText(getApplicationContext(), "Adaptador WiFi apagado", Toast.LENGTH_LONG).show();
            }
            else if(!verificarRedWiFi())
            {
                Toast.makeText(getApplicationContext(), "No está conectado a la red wTEC-Estudiantes", Toast.LENGTH_LONG).show();
            }*/
        }catch (NumberFormatException io)
        {
            Toast.makeText(getApplicationContext(), "Error en la consulta de datos", Toast.LENGTH_LONG).show();
        }
    }
    public void cargarDatos()
    {
        SharedPreferences misPreferencias = getSharedPreferences("misPreferencias", Context.MODE_PRIVATE);
        String carn=misPreferencias.getString("carnet", "");
        String contra=misPreferencias.getString("contraseña", "");
        boolean state =misPreferencias.getBoolean("checked", false);
        if(state && carn.length()==10 && contra.length()==10)
        {
            carnet.setText(carn);
            contraseña.setText(contra);
            estadoCB.setChecked(true);
        }
        else {
            estadoCB.setChecked(false);
        }
    }
    public void guardarDatos()
    {
        SharedPreferences misPreferencias = getSharedPreferences("misPreferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = misPreferencias.edit();
        boolean estado = estadoCB.isChecked();
        String carn =carnet.getText().toString();
        String contra = contraseña.getText().toString();

            editor.putBoolean("checked", estado);
            editor.putString("carnet", carn);
            editor.putString("contraseña", contra);
            editor.commit();


    }
    public void borrarPreferences()
    {
        SharedPreferences misPreferencias = getSharedPreferences("misPreferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = misPreferencias.edit();
        misPreferencias.edit().remove("carnet");
        misPreferencias.edit().remove("contraseña");
        misPreferencias.edit().remove("checked");
        editor.commit();
    }

}
