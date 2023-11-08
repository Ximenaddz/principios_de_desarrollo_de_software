package com.example.miawhorros;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Finanzas extends AppCompatActivity {
    private Map<String, Double> gastos;
    private Spinner categorySpinner;
    private EditText amountEditText;
    private ListView expensesListView;
    private ArrayAdapter<String> listAdapter;
    private List<String> expenseList;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finanzas);
        sharedPreferences = getSharedPreferences("GastosAppPrefs", MODE_PRIVATE);

        // Recupera los gastos guardados o crea un nuevo mapa
        gastos = (Map<String, Double>) sharedPreferences.getAll();
        if (gastos.isEmpty()) {
            // Si no hay gastos previos, inicializa algunas categorías
            gastos.put("Comida", 0.0);
            gastos.put("Arriendo", 0.0);
            gastos.put("Transporte", 0.0);
            gastos.put("Deudas", 0.0);
        }

        // Vincula elementos de la interfaz con variables
        categorySpinner = findViewById(R.id.categorySpinner);
        amountEditText = findViewById(R.id.amountEditText);
        expensesListView = findViewById(R.id.expensesListView);
        expenseList = new ArrayList<>();
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, expenseList);
        expensesListView.setAdapter(listAdapter);

        // Configura el adaptador para el Spinner con las categorías disponibles
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        // Carga los gastos guardados en la lista
        loadExpensesToList();
    }
    public void onAddExpense(View view) {
        // Obtiene la categoría y el monto del usuario
        String categoria = categorySpinner.getSelectedItem().toString();
        String montoStr = amountEditText.getText().toString();

        if (montoStr.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese el monto del gasto.", Toast.LENGTH_SHORT).show();
            return;
        }

        double monto = Double.parseDouble(montoStr);

        // Actualiza el monto de gasto en la categoría
        double montoActual = gastos.get(categoria);
        gastos.put(categoria, montoActual + monto);

        // Guarda los gastos en SharedPreferences
        saveExpensesToSharedPreferences();

        // Agrega el gasto a la lista
        expenseList.add(categoria + ": $" + monto);
        listAdapter.notifyDataSetChanged();

        // Limpia el campo de entrada
        amountEditText.getText().clear();
    }

    private void saveExpensesToSharedPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (Map.Entry<String, Double> entry : gastos.entrySet()) {
            editor.putFloat(entry.getKey(), entry.getValue().floatValue());

        }
        editor.apply();
    }

    private void loadExpensesToList() {
        for (Map.Entry<String, Double> entry : gastos.entrySet()) {
            expenseList.add(entry.getKey() + ": $" + entry.getValue());
        }
        listAdapter.notifyDataSetChanged();
    }
}
