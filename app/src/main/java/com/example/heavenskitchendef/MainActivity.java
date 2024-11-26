package com.example.heavenskitchendef;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton add_button;
    private ImageButton search;
    RecyclerView recyclerView;
    MyDataBaseHelper myDB;
    ArrayList<String> dish_id, dish_title, dish_type, dish_recipe;
    CustomAdapter customAdapter;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        // Configura o RecyclerView para exibir a lista de pratos
        recyclerView = findViewById(R.id.recyclerView);

        // Configura o botão de adicionar prato
        add_button = findViewById(R.id.add);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Create.class);
                startActivity(intent);
            }
        });

        // Configura o botão de busca
        search = findViewById(R.id.search_btn);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Search.class);
                startActivity(intent);
            }
        });

        // Inicializa o banco de dados e os arrays que armazenam os dados dos pratos
        myDB = new MyDataBaseHelper(MainActivity.this);
        dish_id = new ArrayList<>();
        dish_title = new ArrayList<>();
        dish_type = new ArrayList<>();
        dish_recipe = new ArrayList<>();

        // Preenche os arrays com dados do banco de dados
        storeDataInArrays();

        // Configura o adaptador personalizado para o RecyclerView
        customAdapter = new CustomAdapter(MainActivity.this, this, dish_id, dish_title, dish_type, dish_recipe);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    // Método para tratar o resultado de atividades iniciadas para espera de resultado
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            // Recria a atividade para atualizar a lista de pratos após uma edição
            recreate();
        }
    }

    // Método para armazenar dados do banco de dados em arrays
    void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        } else {
            // Itera pelos resultados do cursor e adiciona os dados aos arrays
            while (cursor.moveToNext()) {
                dish_id.add(cursor.getString(0));
                dish_title.add(cursor.getString(1));
                dish_type.add(cursor.getString(2));
                dish_recipe.add(cursor.getString(3));
            }
        }
    }
}
