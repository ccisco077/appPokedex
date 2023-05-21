package com.example.apppokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private Button btnSearch;
    private ImageView imgView;
    private EditText inputSearch;
    private TextView txtName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSearch = findViewById(R.id.btnSearch);
        imgView = findViewById(R.id.imageView);
        inputSearch = findViewById(R.id.editTextText);
        txtName = findViewById(R.id.txtName);
        btnSearch.setOnClickListener(v -> {
            String API_URL = "https://pokeapi.co/api/v2/pokemon/"+inputSearch.getText().toString();
            // Crear la cola de solicitudes de Volley
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            // Crear la solicitud GET utilizando JsonObjectRequest
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, API_URL, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Procesar la respuesta JSON
                            Log.d("Respuesta", "Respuesta: " + response.toString());
                            // Hacer algo con la respuesta JSON
                            try {
                                txtName.setText(response.getString("name"));
                                Picasso.get().load(response.getJSONObject("sprites").getString("front_default")).into(imgView);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            imgView.setImageDrawable(getDrawable(R.drawable.d5s04qj_d26a072a_3294_4da7_8ab9_a1be78141275));
                            // Manejar el error de la solicitud
                            Log.e("Error", "Error en la solicitud: " + error.getMessage());

                            imgView.setImageDrawable(getDrawable(R.drawable.d5s04qj_d26a072a_3294_4da7_8ab9_a1be78141275));
                            txtName.setText("No se pudo encontrar");
                        }
                    });
            // Agregar la solicitud a la cola de solicitudes de Volley
            requestQueue.add(jsonObjectRequest);
        });
    }
}