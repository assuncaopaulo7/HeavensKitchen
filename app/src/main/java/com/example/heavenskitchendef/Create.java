package com.example.heavenskitchendef;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Create extends AppCompatActivity {

    private Button move, save;
    private EditText title, type, recipe;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Habilita o modo Edge-to-Edge para esta atividade.
        EdgeToEdge.enable(this);

        // Define o layout para esta atividade.
        setContentView(R.layout.activity_create);

        // Configura a manipulação dos insets de janela para ajustar o layout conforme necessário.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //listener para retornar à MainActivity.
        move = findViewById(R.id.see_back);
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Create.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Inicializa os inputs de título, tipo e receita.
        title = findViewById(R.id.dish_name);
        recipe = findViewById(R.id.create_recipe);
        type = findViewById(R.id.create_type);

        //listener para salvar os dados na BD.
        save = findViewById(R.id.submit_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDataBaseHelper myDB = new MyDataBaseHelper(Create.this);
                myDB.addDish(title.getText().toString().trim(),
                        type.getText().toString().trim(),
                        recipe.getText().toString().trim());
                Intent intent = new Intent(Create.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
