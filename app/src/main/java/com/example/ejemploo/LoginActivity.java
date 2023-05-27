package com.example.ejemploo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    ImageButton imageButtonFacebook;
    ImageButton imageButtonGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_main);

        imageButtonFacebook = (ImageButton) findViewById(R.id.imageButtonFacebook);
        imageButtonFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  DBase dbejemplo = new DBase(LoginActivity.this);
                SQLiteDatabase db = dbejemplo.getWritableDatabase();

                if (db != null) {
                    Toast.makeText(LoginActivity.this, "Base de datos creada", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "ERROR: Base de datos no creada", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        imageButtonGoogle = (ImageButton) findViewById(R.id.imageButtonGoogle);
        imageButtonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(i);
            }
        });

        Button btn_Iniciar;
        EditText editText_Usuario;
        EditText editText_Contra;

        editText_Usuario = findViewById(R.id.editText_Usuario);
        editText_Contra = findViewById(R.id.editText_Contra);
        btn_Iniciar = findViewById(R.id.btn_Iniciar);

        btn_Iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*   if (!editText_Usuario.getText().toString().equals("") && !editText_Contra.getText().toString().equals("")) {
                    DBcontactos dbselect = new DBcontactos(LoginActivity.this);

                    dbselect.Buscar(editText_Usuario.getText().toString(), editText_Contra.getText().toString());

                    Toast.makeText(LoginActivity.this, "bien", Toast.LENGTH_SHORT).show();
                   Intent i = new Intent(LoginActivity.this, MainActivityInterfazMA.class);
                    startActivity(i);

                    if (editText_Usuario.getText().toString().equals(" ") || editText_Contra.getText().toString().equals(" ")) {
                        Toast.makeText(LoginActivity.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
                    }*/

                String nombreUsuario = editText_Usuario.getText().toString();
                String contraseña = editText_Contra.getText().toString();
                iniciarSesion(nombreUsuario, contraseña);
            }
        });
    }
        final String URL_API = "https://api/iniciar_sesion"; // URL de la API
        private void iniciarSesion(String editText_Usuario, String editText_Contra){
            // Construir objeto JSON con los datos de inicio de sesión
            JSONObject jsonDatos = new JSONObject();
            try {
                jsonDatos.put("nombreUsuario", editText_Usuario);
                jsonDatos.put("contraseña", editText_Contra);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Crear la solicitud POST
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL_API, jsonDatos,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Procesar la respuesta de la API
                            try {
                                boolean inicioSesionExitoso = response.getBoolean("inicioSesionExitoso");
                                if (inicioSesionExitoso) {
                                    // Inicio de sesión exitoso
                                    Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Inicio de sesión fallido
                                    Toast.makeText(LoginActivity.this, "Inicio de sesión fallido", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Error en la solicitud
                            Toast.makeText(LoginActivity.this, "Error en la solicitud", Toast.LENGTH_SHORT).show();
                            Log.e("Volley Error", error.toString());
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    // Agregar encabezados si es necesario
                    Map<String, String> headers = new HashMap<>();
                    // headers.put("Key", "Value");
                    return headers;
                }
            };
            // Agregar solicitud a la cola de solicitudes de Volley
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonObjectRequest);
        }
    }


