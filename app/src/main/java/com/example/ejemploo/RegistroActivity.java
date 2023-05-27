package com.example.ejemploo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_activity_main);


        DBcontactos registrar = new DBcontactos(RegistroActivity.this);

        EditText txtUsuario = findViewById(R.id.edit_Usuario);
        EditText txtNombre = findViewById(R.id.edit_Nombre);
        EditText txtContra = findViewById(R.id.edit_Contra);
        EditText txtApellidos = findViewById(R.id.edit_Apellidos);
        EditText txtEdad = findViewById(R.id.edit_Edad);


        Button btn_registrar = findViewById(R.id.btn_registrar);
        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   if (!txtUsuario.getText().toString().equals("") && !txtContra.getText().toString().equals("") && !txtTelefono.getText().toString().equals("") && !txtCorreo.getText().toString().equals("")) {

                    DBcontactos dBcontactos = new DBcontactos(RegistroActivity.this);

                    long id = dBcontactos.insertarContacto(txtUsuario.getText().toString(), txtContra.getText().toString(), txtTelefono.getText().toString(), txtCorreo.getText().toString());
                    {
                        if (id >= 0) {
                            Toast.makeText(RegistroActivity.this, "REGISTRO COMPLETO", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegistroActivity.this, "ERROR EN EL REGISTRO", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }*/
                String nombreUsuario = txtUsuario.getText().toString();
                String nombre = txtUsuario.getText().toString();
                String apellidos = txtApellidos.getText().toString();
                String edad = txtEdad.getText().toString();
                String contraseña = txtContra.getText().toString();
                registrarUsuario(nombreUsuario, nombre, apellidos, edad, contraseña);
            }
        });
    }
        final String URL_API = "https://api/iniciar_sesion"; // URL de la API

        private void registrarUsuario (String txtUsuario, String txtNombre, String txtApellidos, String txtEdad, String txtContra){
            // Construir el objeto JSON con los datos del usuario a registrar
            JSONObject jsonDatos = new JSONObject();
            try {
                jsonDatos.put("nombreUsuario", txtUsuario);
                jsonDatos.put("nombre", txtNombre);
                jsonDatos.put("apellidos", txtApellidos);
                jsonDatos.put("edad", txtEdad);
                jsonDatos.put("contraseña", txtContra);
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
                                boolean registroExitoso = response.getBoolean("registroExitoso");
                                if (registroExitoso) {
                                    // Registro exitoso
                                    Toast.makeText(RegistroActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Error en el registro
                                    String mensajeError = response.getString("mensajeError");
                                    Toast.makeText(RegistroActivity.this, "Error en el registro: " + mensajeError, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Manejar errores de la solicitud
                            Log.e("Volley Error", error.toString());
                            Toast.makeText(RegistroActivity.this, "Error en la solicitud", Toast.LENGTH_SHORT).show();
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

            // Agregar la solicitud a la cola de solicitudes de Volley
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonObjectRequest);

        }
    }


