package com.example.heavenskitchendef;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "heavenskitchen2.db";
    private static final int DATABASE_VERSION = 10;

    private static final String TABLE_NAME = "pratos";
    private static final String COLUMN_ID = "dish_id";
    private static final String COLUMN_TITLE = "dish_name";
    private static final String COLUMN_TYPE = "dish_type";
    private static final String COLUMN_RECIPE = "dish_recipe";

    MyDataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Método chamado quando o banco de dados é criado pela primeira vez
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Cria a tabela "pratos" com colunas para ID, nome do prato, tipo de prato e receita
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_RECIPE + " TEXT);";
        db.execSQL(query);

        // Insere dados iniciais na tabela
        insertInitialData(db);
    }

    // Método para inserir dados iniciais na tabela
    private void insertInitialData(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        // Insere a receita "Lasanha Simples"
        cv.put(COLUMN_TITLE, "Lasanha Simples");
        cv.put(COLUMN_TYPE, "Italiana");
        cv.put(COLUMN_RECIPE, "1. Pré-aqueça o forno a 190°C. 2. Cozinhe as folhas de lasanha. 3. Prepare o molho e a mistura de queijo. 4. Alterne camadas de folhas de lasanha, molho e queijo. 5. Asse por 45 minutos.");
        db.insert(TABLE_NAME, null, cv);

        // Insere a receita "Frango Alfredo"
        cv.put(COLUMN_TITLE, "Frango Alfredo");
        cv.put(COLUMN_TYPE, "Italiana");
        cv.put(COLUMN_RECIPE, "1. Cozinhe a massa. 2. Prepare o molho Alfredo. 3. Cozinhe o frango. 4. Combine a massa, o molho e o frango.");
        db.insert(TABLE_NAME, null, cv);

        // Insere a receita "Bacalhau à Brás"
        cv.put(COLUMN_TITLE, "Bacalhau à Brás");
        cv.put(COLUMN_TYPE, "Portuguesa");
        cv.put(COLUMN_RECIPE, "1. Desfie o bacalhau e coza-o. 2. Frite as batatas em palitos finos. 3. Refogue a cebola e o alho em azeite. 4. Adicione o bacalhau e misture. 5. Junte as batatas fritas e envolva. 6. Adicione os ovos batidos e mexa até os ovos estarem cozidos. 7. Decore com salsa picada e azeitonas.");
        db.insert(TABLE_NAME, null, cv);
    }

    // Método chamado quando o banco de dados é atualizado
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Apaga a tabela existente e cria uma nova
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Método para adicionar um novo prato ao banco de dados
    public void addDish(String title, String type, String recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_TYPE, type);
        cv.put(COLUMN_RECIPE, recipe);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para ler todos os dados da tabela
    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    // Método para atualizar os dados de um prato específico
    void updateData(String row_id, String title, String type, String recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_TYPE, type);
        cv.put(COLUMN_RECIPE, recipe);

        long result = db.update(TABLE_NAME, cv, "dish_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Update.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para deletar um prato específico
    void deleteOneRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "dish_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted!", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para pesquisar um prato pelo nome ou tipo
    public Cursor searchDishByNameOrType(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        String searchQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + " LIKE ? OR " + COLUMN_TYPE + " LIKE ?";
        return db.rawQuery(searchQuery, new String[]{"%" + query + "%", "%" + query + "%"});
    }
}
