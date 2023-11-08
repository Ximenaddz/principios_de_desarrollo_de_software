package com.example.miawhorros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    private static final String PREFS_NAME = "MisPreferencias";
    private static final String SALDO_KEY = "saldo";


    FirebaseAuth auth;
    FirebaseUser user;
    TextView textView;
    Button button;

    ImageButton btn_colchon;
    ImageButton btn_bolsillos;
    ImageButton btn_ahorros;
    ImageButton btn_finance;

    Button btn_actualizarsaldo;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SALDO_KEY, Integer.parseInt("0"));
        editor.apply();

        int saldo= sharedPreferences.getInt(SALDO_KEY, 0); // 0.0f es un valor predeterminado en caso de que no haya saldo guardado aún
        //tvsaldo.setText(saldo);
        final TextView tvSaldo = (TextView) findViewById(R.id.saldomain);




        tvSaldo.setText(String.valueOf(saldo));

        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.logout);
        textView = findViewById(R.id.user_details);
        user = auth.getCurrentUser();
        btn_colchon= findViewById(R.id.btn_colchon);
        btn_bolsillos= findViewById(R.id.btn_bolsillos);
        btn_ahorros= findViewById(R.id.btn_ahorros);
        btn_finance= findViewById(R.id.btn_finance);
        btn_actualizarsaldo=findViewById(R.id.btn_actualizarsaldo);

        if (user == null){
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
            finish();
        }

        else{
            textView.setText(user.getEmail());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
                finish();
            }
        });

        btn_actualizarsaldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText mEdit   = (EditText)findViewById(R.id.editarsaldo);
                String value = mEdit.getText().toString();
                actualizarSaldo(value);



            }
        });

        btn_colchon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Colchon.class);
                startActivity(i);


            }
        });
        btn_bolsillos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Bolsillos.class);
                startActivity(i);

            }
        });
        btn_ahorros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Ahorros.class);
                startActivity(i);

            }
        });
        btn_finance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Finanzas.class);
                startActivity(i);

            }
        });

    }
    private void actualizarSaldo(String nuevoSaldo) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SALDO_KEY, nuevoSaldo);
        editor.apply();

        TextView textViewSaldo = findViewById(R.id.saldomain);
        textViewSaldo.setText("Saldo: " + nuevoSaldo);
    }

}
