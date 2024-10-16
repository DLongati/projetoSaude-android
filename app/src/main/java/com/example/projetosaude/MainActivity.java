package com.example.projetosaude;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText edtNome;
    private EditText edtTel;
    private ImageView imgLogo;
    private Button btnIniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtNome = findViewById(R.id.edtNome);
        edtTel = findViewById(R.id.edtTel);
        imgLogo = findViewById(R.id.imgLogo);
        btnIniciar = findViewById(R.id.btnIniciar);

        edtNome.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (edtNome.getText().length() > 0) {
                    btnIniciar.setEnabled(true);
                } else {
                    btnIniciar.setEnabled(false);
                }
                return false;
            }
        });
    }

    public String getNomeUsuario() {
        SharedPreferences sharedPreferences = getSharedPreferences("MeuApp", MODE_PRIVATE);
        return sharedPreferences.getString("nomeUsuario", "Usuário");
    }

    public void iniciar(View view) {
        String nomeUsuario = edtNome.getText().toString();
        String telefoneUsuario = edtTel.getText().toString();

        // Armazenar os dados no SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MeuApp", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nomeUsuario", nomeUsuario);
        editor.putString("telefoneUsuario", telefoneUsuario);
        editor.apply();

        Intent intent = new Intent(this, faces.class);
        startActivity(intent);
    }
}