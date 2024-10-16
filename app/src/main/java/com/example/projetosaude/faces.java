package com.example.projetosaude;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class faces extends AppCompatActivity {

    private ImageView imgFaces;
    private CheckBox[] checkBoxesGroup1;
    private CheckBox[] checkBoxesGroup2;
    private Button btnEnviar;

    private enum GroupLimit {
        GROUP_1(2),
        GROUP_2(3);

        final int limit;

        GroupLimit(int limit) {
            this.limit = limit;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_faces);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String nome = getNomeUsuario();

        // Exibir o nome em um TextView
        TextView txtNome = findViewById(R.id.txtNome);
        txtNome.setText("Olá, " + nome + "!");

        // Recuperar telefone do SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MeuApp", MODE_PRIVATE);
        String telefone = sharedPreferences.getString("telefoneUsuario", "Telefone não informado");

        imgFaces = findViewById(R.id.imgFaces);
        checkBoxesGroup1 = new CheckBox[] {
                findViewById(R.id.chMFeliz),
                findViewById(R.id.chFeliz),
                findViewById(R.id.chIndiferente),
                findViewById(R.id.chTriste),
                findViewById(R.id.chMTriste)
        };

        checkBoxesGroup2 = new CheckBox[] {
                findViewById(R.id.chAnsiedade),
                findViewById(R.id.chFaltaDeAr),
                findViewById(R.id.chAlegria),
                findViewById(R.id.chApatico),
                findViewById(R.id.chOtimista),
                findViewById(R.id.chDeprimido)
        };

        btnEnviar = findViewById(R.id.btnEnviar);
        btnEnviar.setOnClickListener(v -> onEnviarClicked());

        setupCheckBoxListeners(checkBoxesGroup1, GroupLimit.GROUP_1.limit);
        setupCheckBoxListeners(checkBoxesGroup2, GroupLimit.GROUP_2.limit);
    }

    private void setupCheckBoxListeners(CheckBox[] checkBoxes, int limit) {
        for (CheckBox checkBox : checkBoxes) {
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (getCheckedCount(checkBoxes) > limit) {
                    buttonView.setChecked(false);
                    Toast.makeText(this, "Selecione até " + limit + " opções.", Toast.LENGTH_SHORT).show();
                }
                toggleCheckBoxStates(checkBoxes);
            });
        }
    }

    private int getCheckedCount(CheckBox[] checkBoxes) {
        int count = 0;
        for (CheckBox cb : checkBoxes) {
            if (cb.isChecked()) {
                count++;
            }
        }
        return count;
    }

    private void toggleCheckBoxStates(CheckBox[] checkBoxes) {
        boolean canEnable = getCheckedCount(checkBoxes) > 0;

        for (CheckBox cb : checkBoxes) {
            cb.setEnabled(!cb.isChecked() || canEnable);
        }
    }

    private void onEnviarClicked() {
        if (getCheckedCount(checkBoxesGroup1) <= GroupLimit.GROUP_1.limit && getCheckedCount(checkBoxesGroup2) <= GroupLimit.GROUP_2.limit) {
            checkSelection();
        } else {
            Toast.makeText(this, "Por favor, respeite os limites de seleção.", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkSelection() {
        // Verifica se o grupo 1 está triste
        if (checkBoxesGroup1[3].isChecked() && checkBoxesGroup1[4].isChecked() &&
                (checkBoxesGroup2[0].isChecked() || checkBoxesGroup2[1].isChecked() ||
                        checkBoxesGroup2[3].isChecked() || checkBoxesGroup2[5].isChecked())) {
            startActivityWithMessage(Triste.class, "Estamos com você! A sua saúde mental importa!");
        }
        // Verifica se o grupo 1 está feliz
        else if (checkBoxesGroup1[1].isChecked() && checkBoxesGroup1[0].isChecked() &&
                checkBoxesGroup2[2].isChecked() && checkBoxesGroup2[4].isChecked()) {
            startActivityWithMessage(Feliz.class, "Estamos felizes com você ;) A sua saúde mental importa!");
        }
        // Se não se encaixar em nenhuma das opções acima
        else {
            startActivityWithMessage(Anotacoes.class, "Estamos com você! A sua saúde mental importa!");
        }
    }


    private boolean isGroup1Happy() {
        return checkBoxesGroup1[0].isChecked() && checkBoxesGroup1[1].isChecked() &&
                checkBoxesGroup2[2].isChecked() && checkBoxesGroup2[4].isChecked();
    }

    private boolean isGroup2Sad() {
        return (checkBoxesGroup1[3].isChecked() || checkBoxesGroup1[4].isChecked()) &&
                checkBoxesGroup2[0].isChecked() && checkBoxesGroup2[1].isChecked() &&
                (checkBoxesGroup2[3].isChecked() || checkBoxesGroup2[5].isChecked());
    }

    private void startActivityWithMessage(Class<?> cls, String message) {
        Intent intent = new Intent(this, cls);
        intent.putExtra("message", message);
        startActivity(intent);
    }

    private String getNomeUsuario() {
        SharedPreferences sharedPreferences = getSharedPreferences("MeuApp", MODE_PRIVATE);
        return sharedPreferences.getString("nomeUsuario", "Usuário");
    }
}

