package com.example.heavenskitchendef;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Edit extends AppCompatActivity {
    EditText input_title, input_recipe, input_type;
    Button cancel, submit, delete;
    String id, title, type, recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        input_title = findViewById(R.id.dish_name);
        input_recipe = findViewById(R.id.edit_recipe);
        input_type = findViewById(R.id.edit_type);

        cancel = findViewById(R.id.see_back);
        // Botão para cancelar a edição e voltar para a MainActivity
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Edit.this, MainActivity.class);
                startActivity(intent);
            }
        });

        submit = findViewById(R.id.submit_button);
        getAndSetIntentData();
        // Botão para salvar as alterações e atualizar os dados no banco de dados
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = input_title.getText().toString().trim();
                type = input_type.getText().toString().trim();
                recipe = input_recipe.getText().toString().trim();

                MyDataBaseHelper myDB = new MyDataBaseHelper(Edit.this);
                myDB.updateData(id, title, type, recipe);
                Intent intent = new Intent(Edit.this, MainActivity.class);
                startActivity(intent);
            }
        });

        delete = findViewById(R.id.delete_button);
        // Botão para excluir o prato, exibindo uma caixa de diálogo de confirmação
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
    }

    // Método para obter os dados do Intent e definir nos EditTexts
    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") &&
                getIntent().hasExtra("title") &&
                getIntent().hasExtra("type") &&
                getIntent().hasExtra("recipe")) {
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            type = getIntent().getStringExtra("type");
            recipe = getIntent().getStringExtra("recipe");

            input_title.setText(title);
            input_recipe.setText(recipe);
            input_type.setText(type);
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para exibir uma caixa de diálogo de confirmação para excluir o prato
    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + "?");
        builder.setMessage("Are you sure you want to delete " + title + "?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDataBaseHelper myDB = new MyDataBaseHelper(Edit.this);
                myDB.deleteOneRow(id);
                Intent intent = new Intent(Edit.this, MainActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        });
        builder.create().show();
    }
}
