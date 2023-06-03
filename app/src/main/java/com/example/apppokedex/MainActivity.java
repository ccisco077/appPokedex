package com.example.apppokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public Button btnSearch;
    public EditText inputSearch;
    public TextView txtNamePokemon, txtWeightPokemon, txtHeightPokemon, txtTypePokemon, txtCantHpPokemon, txtCantAttackPokemon, txtCantDefPokemon;
    public ImageView imageView;
    public Spinner optionsSelector;
    public String[] options = {"Buscar item", "Buscar pokemon"};
    public ProgressBar barLife, barAttack, barDef;
    public TableLayout tableLayoutDetails;
    public int OptionSelection = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtNamePokemon = findViewById(R.id.txtName);
        imageView = findViewById(R.id.imgPokedex);
        inputSearch = findViewById(R.id.inputSearch);
        btnSearch = findViewById(R.id.btnSearch);
        txtWeightPokemon = findViewById(R.id.txtWeight);
        txtHeightPokemon = findViewById(R.id.txtHeigth);
        txtTypePokemon = findViewById(R.id.txtType);
        txtCantHpPokemon = findViewById(R.id.cantHP);
        txtCantAttackPokemon = findViewById(R.id.cantAttack);
        txtCantDefPokemon = findViewById(R.id.cantDef);
        optionsSelector = findViewById(R.id.spinnerOptions);
        tableLayoutDetails = findViewById(R.id.tablePokemonDetails);
        barLife = findViewById(R.id.barHP);
        barAttack = findViewById(R.id.barAttack);
        barDef = findViewById(R.id.barDef);
        barLife.setMax(100);
        barAttack.setMax(100);
        barDef.setMax(100);

        btnSearch.setOnClickListener(view -> {
            getPokemon(inputSearch.getText().toString());
        });

        optionsSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
            {
                Object item = parent.getItemAtPosition(pos);
                if (pos == 1) {
                    tableLayoutDetails.setVisibility(View.VISIBLE);
                }
                else {
                    tableLayoutDetails.setVisibility(View.GONE);
                }
                OptionSelection = pos;
            }
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        optionsSelector.setAdapter(adapter);
    }
    void getPokemon(String name) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://pokeapi.co/api/v2/pokemon/" + name)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    // Convierte la respuesta en un objeto Pokemon
                    Gson gson = new Gson();
                    final Pokemon pokemon = gson.fromJson(response.body().string(), Pokemon.class);
                    // Actualiza la interfaz de usuario
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txtNamePokemon.setText(pokemon.name);
                            txtWeightPokemon.setText("Peso: " + pokemon.weight + " Kg.");
                            txtHeightPokemon.setText("Altura: " + pokemon.height + " metros.");
                            txtTypePokemon.setText(pokemon.types[0].type.name);

                            barLife.setProgress(pokemon.stats[0].base_stat);
                            barAttack.setProgress(pokemon.stats[1].base_stat);
                            barDef.setProgress(pokemon.stats[2].base_stat);

                            txtCantHpPokemon.setText(pokemon.stats[0].base_stat + " %");
                            txtCantAttackPokemon.setText(pokemon.stats[1].base_stat + " %");
                            txtCantDefPokemon.setText(pokemon.stats[2].base_stat + " %");

                            Picasso.get().load(pokemon.sprites.front_default).into(imageView);
                        }
                    });
                }
                response.close();
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }
    void getItem(String name) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://pokeapi.co/api/v2/item/" + name)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    // Convierte la respuesta en un objeto Pokemon
                    Gson gson = new Gson();
                    final Pokemon pokemon = gson.fromJson(response.body().string(), Pokemon.class);
                    // Actualiza la interfaz de usuario
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txtNamePokemon.setText(pokemon.name);
                            txtWeightPokemon.setText("Peso: " + pokemon.weight + " Kg.");
                            txtHeightPokemon.setText("Altura: " + pokemon.height + " metros.");
                            txtTypePokemon.setText(pokemon.types[0].type.name);

                            barLife.setProgress(pokemon.stats[0].base_stat);
                            barAttack.setProgress(pokemon.stats[1].base_stat);
                            barDef.setProgress(pokemon.stats[2].base_stat);

                            txtCantHpPokemon.setText(pokemon.stats[0].base_stat + " %");
                            txtCantAttackPokemon.setText(pokemon.stats[1].base_stat + " %");
                            txtCantDefPokemon.setText(pokemon.stats[2].base_stat + " %");

                            Picasso.get().load(pokemon.sprites.front_default).into(imageView);
                        }
                    });
                }
                response.close();
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }
}