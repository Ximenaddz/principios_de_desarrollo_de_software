package com.example.miawhorros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Colchon extends AppCompatActivity {

    Button btn_saldocolchon;
    private SharedPreferences sharedPreferences;

    private static final String preferencias = "MisPreferencias";
    private static final String saldoameter = "$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colchon);

        btn_saldocolchon=findViewById(R.id.btn_saldocolchon);


        sharedPreferences = getSharedPreferences(preferencias, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(saldoameter, Integer.parseInt("0"));
        editor.apply();

        int saldo= sharedPreferences.getInt(saldoameter, 0); // 0.0f es un valor predeterminado en caso de que no haya saldo guardado aún
        //tvsaldo.setText(saldo);
        final TextView tvSaldo = (TextView) findViewById(R.id.saldomete);

        btn_saldocolchon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText mEdit   = (EditText)findViewById(R.id.editarcolchon);
                String value = mEdit.getText().toString();
                actualizarSaldo(value);



            }
        });






    }
    private void actualizarSaldo(String nuevoSaldo) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(saldoameter, nuevoSaldo);
        editor.apply();

        TextView textViewSaldo = findViewById(R.id.saldomete);
        textViewSaldo.setText("Saldo: " + nuevoSaldo);
    }
}
