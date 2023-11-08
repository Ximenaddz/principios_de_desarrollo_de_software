package com.example.miawhorros;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;




public class Ahorros extends AppCompatActivity {
    private EditText savingsInput;
    private TextView totalSavingsTextView;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ahorros);
        savingsInput = findViewById(R.id.savingsInput);
        totalSavingsTextView = findViewById(R.id.totalSavingsTextView);

        sharedPreferences = getSharedPreferences("SavingsPrefs", MODE_PRIVATE);

        // Cargar los ahorros guardados y mostrarlos
        float totalSavings = sharedPreferences.getFloat("totalSavings", 0.0f);
        totalSavingsTextView.setText("Ahorros totales: $" + totalSavings);
    }
    public void addSavings(View view) {
        String savingsStr = savingsInput.getText().toString();
        if (!savingsStr.isEmpty()) {
            float savingsAmount = Float.parseFloat(savingsStr);

            // Obtener los ahorros totales actuales
            float totalSavings = sharedPreferences.getFloat("totalSavings", 0.0f);

            // Actualizar los ahorros totales
            totalSavings += savingsAmount;

            // Guardar los ahorros totales actualizados en SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat("totalSavings", totalSavings);
            editor.apply();

            // Actualizar la vista
            totalSavingsTextView.setText("Ahorros totales: $" + totalSavings);
            savingsInput.getText().clear();

            Toast.makeText(this, "Ahorros agregados con éxito.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Por favor, ingrese la cantidad de ahorros.", Toast.LENGTH_SHORT).show();
        }
    }
}
