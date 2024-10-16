package com.example.projetosaude;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Triste extends AppCompatActivity {

    private TextView txtIntro;
    private EditText edtAnot;
    private Button btnEnviarAnot;
    private Button btnInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_triste);

        // Inicializar os componentes da interface
        txtIntro = findViewById(R.id.txtIntro);
        edtAnot = findViewById(R.id.edtAnot);
        btnEnviarAnot = findViewById(R.id.btnEnviarAnot);
        btnInicio = findViewById(R.id.btnInicio);

        // Recuperar nome do usuário
        String nome = getNomeUsuario();

        // Recuperar telefone do SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MeuApp", MODE_PRIVATE);
        String telefone = sharedPreferences.getString("telefoneUsuario", "Telefone não informado");

        // Formatar a mensagem
        String mensagem = nome + ", em até 20 minutos, dois dos profissionais abaixo (um(a) Psicólogo(a) e um(a) Psiquiatra) entrarão em contato com você pelo seu telefone: " + telefone;

        // Definir a mensagem no TextView
        txtIntro.setText(mensagem);

        // Configurar o botão para enviar anotações
        btnEnviarAnot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarAnotacoes();
            }
        });

        // Configurar o botão para voltar à tela inicial
        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voltarParaInicio();
            }
        });
    }

    // Método para recuperar o nome do usuário
    private String getNomeUsuario() {
        SharedPreferences sharedPreferences = getSharedPreferences("MeuApp", MODE_PRIVATE);
        return sharedPreferences.getString("nomeUsuario", "Usuário");
    }

    // Método para enviar anotações
    private void enviarAnotacoes() {
        String anotacao = edtAnot.getText().toString();
        if (!anotacao.isEmpty()) {
            // Aqui você pode adicionar a lógica para armazenar a anotação, por exemplo, em um banco de dados
            Toast.makeText(this, "Anotação enviada: " + anotacao, Toast.LENGTH_SHORT).show();
            edtAnot.setText(""); // Limpar o campo após enviar
        } else {
            // Exibir uma mensagem caso o campo esteja vazio
            Toast.makeText(this, "Por favor, insira uma anotação antes de enviar.", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para voltar à tela inicial
    private void voltarParaInicio() {
        Intent intent = new Intent(this, faces.class);
        startActivity(intent);
        finish(); // Finaliza a atividade atual
    }
}




