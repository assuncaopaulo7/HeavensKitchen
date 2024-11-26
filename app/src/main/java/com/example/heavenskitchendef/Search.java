// Search.java
package com.example.heavenskitchendef;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    // Declaração de variáveis de interface do usuário e banco de dados
    private EditText searchInput;
    private Button searchButton;
    private ImageButton homeButton;
    private RecyclerView searchResults;
    private MyDataBaseHelper myDB;
    private ArrayList<String> dish_id, dish_title, dish_type, dish_recipe;
    private CustomAdapter customAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);

        // Inicialização dos componentes da interface do usuário
        searchInput = findViewById(R.id.search_input);
        searchButton = findViewById(R.id.search_button);
        homeButton = findViewById(R.id.imageButtonsss);
        searchResults = findViewById(R.id.search_result);

        // Inicialização do banco de dados e listas de dados
        myDB = new MyDataBaseHelper(Search.this);
        dish_id = new ArrayList<>();
        dish_title = new ArrayList<>();
        dish_type = new ArrayList<>();
        dish_recipe = new ArrayList<>();

        // Configuração do botão de retorno para a tela inicial
        homeButton = findViewById(R.id.imageButtonsss);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Search.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Configuração do botão de busca
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtém o termo de busca digitado pelo usuário
                String query = searchInput.getText().toString().trim();
                if (!query.isEmpty()) {
                    // Realiza a busca no banco de dados
                    searchDishes(query);
                } else {
                    // Exibe uma mensagem se o campo de busca estiver vazio
                    Toast.makeText(Search.this, "Please enter a search term", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Método para buscar pratos no banco de dados
    private void searchDishes(String query) {
        // Busca pratos pelo nome ou tipo no banco de dados
        Cursor cursor = myDB.searchDishByNameOrType(query);
        if (cursor.getCount() == 0) {
            // Exibe uma mensagem se nenhum dado for encontrado
            Toast.makeText(this, "No data found.", Toast.LENGTH_SHORT).show();
        } else {
            // Limpa as listas de dados antes de adicionar novos dados
            dish_id.clear();
            dish_title.clear();
            dish_type.clear();
            dish_recipe.clear();
            // Adiciona os dados encontrados às listas
            while (cursor.moveToNext()) {
                dish_id.add(cursor.getString(0));
                dish_title.add(cursor.getString(1));
                dish_type.add(cursor.getString(2));
                dish_recipe.add(cursor.getString(3));
            }
            // Configura o adaptador do RecyclerView com os dados encontrados
            customAdapter = new CustomAdapter(Search.this, this, dish_id, dish_title, dish_type, dish_recipe);
            searchResults.setAdapter(customAdapter);
            searchResults.setLayoutManager(new LinearLayoutManager(Search.this));
        }
    }
}
