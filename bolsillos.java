package com.example.miawhorros;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Bolsillos extends AppCompatActivity {
    private EditText pocketNameInput;
    private Spinner pocketSpinner;
    private EditText balanceInput;  // Nuevo campo para el saldo
    private SharedPreferences sharedPreferences;
    private Map<String, Float> pocketsMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bolsillos);
        pocketNameInput = findViewById(R.id.pocketNameInput);
        balanceInput = findViewById(R.id.balanceInput);  // Vincula el EditText de saldo
        pocketSpinner = findViewById(R.id.pocketSpinner);

        sharedPreferences = getSharedPreferences("PocketsPrefs", MODE_PRIVATE);
        pocketsMap = new HashMap<>();  // Inicializa el HashMap

        // Cargar los nombres de los bolsillos y los saldos guardados en el Spinner y el HashMap
        loadPocketsData();
    }
    public void addPocket(View view) {
        String pocketName = pocketNameInput.getText().toString();
        String balanceStr = balanceInput.getText().toString();

        if (!pocketName.isEmpty() && !balanceStr.isEmpty()) {
            // Agregar el nombre del bolsillo y su saldo al HashMap
            pocketsMap.put(pocketName, Float.parseFloat(balanceStr));

            // Guardar los datos actualizados en SharedPreferences
            savePocketsData();

            // Actualizar la lista de nombres de bolsillos en el Spinner
            loadPocketsData();

            // Limpiar los campos de entrada
            pocketNameInput.getText().clear();
            balanceInput.getText().clear();

            Toast.makeText(this, "Bolsillo creado con éxito.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Por favor, ingrese el nombre y el saldo del bolsillo.", Toast.LENGTH_SHORT).show();
        }
    }

    public void viewPocket(View view) {
        String selectedPocketName = pocketSpinner.getSelectedItem().toString();
        // Implementa la lógica para ver o gestionar el bolsillo seleccionado
        // Puedes abrir una nueva actividad o fragmento para ello.
        float selectedBalance = pocketsMap.get(selectedPocketName);  // Obtener el saldo del bolsillo
        Toast.makeText(this, "Seleccionaste el bolsillo: " + selectedPocketName + " con saldo: $" + selectedBalance, Toast.LENGTH_SHORT).show();
    }

    private void loadPocketsData() {
        // Cargar los nombres de los bolsillos y los saldos guardados en el Spinner y el HashMap
        pocketsMap.clear();
        Map<String, ?> allEntries = sharedPreferences.getAll();
        List<String> pocketNames = new ArrayList<>();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getValue() instanceof Float) {
                pocketsMap.put(entry.getKey(), (Float) entry.getValue());
                pocketNames.add(entry.getKey());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pocketNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pocketSpinner.setAdapter(adapter);
    }

    private void savePocketsData() {
        // Guardar los nombres de los bolsillos y los saldos en SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();  // Borrar las entradas anteriores
        for (Map.Entry<String, Float> entry : pocketsMap.entrySet()) {
            editor.putFloat(entry.getKey(), entry.getValue());
        }
        editor.apply();
    }
