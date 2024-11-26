package com.example.heavenskitchendef;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{

    private Context context;
    private Activity activity;
    private ArrayList dish_id, dish_title, dish_type, dish_recipe;

    // Construtor para inicializar o adaptador com os dados necessários
    CustomAdapter(Activity activity,
                  Context context,
                  ArrayList dish_id,
                  ArrayList dish_title,
                  ArrayList dish_type,
                  ArrayList dish_recipe){

        this.activity = activity;
        this.context = context;
        this.dish_id = dish_id;
        this.dish_title = dish_title;
        this.dish_type = dish_type;
        this.dish_recipe = dish_recipe;
    }

    // Inflar o layout do item da lista e criar um ViewHolder
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    // Vincular os dados aos elementos da interface do item da lista
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.dish_title_txt.setText(String.valueOf(dish_title.get(position)));
        holder.dish_type_txt.setText(String.valueOf(dish_type.get(position)));
        holder.dish_id_txt.setText(String.valueOf(dish_id.get(position)));

        // Configurar um clique no item da lista para editar o prato
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPosition = holder.getAdapterPosition();
                Intent intent = new Intent(context, Edit.class);
                intent.putExtra("id", String.valueOf(dish_id.get(currentPosition)));
                intent.putExtra("title", String.valueOf(dish_title.get(currentPosition)));
                intent.putExtra("type", String.valueOf(dish_type.get(currentPosition)));
                intent.putExtra("recipe", String.valueOf(dish_recipe.get(currentPosition)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    // Retornar o número de itens na lista
    @Override
    public int getItemCount() {
        return dish_title.size();
    }

    // ViewHolder para gerenciar as views dos itens da lista
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView dish_title_txt, dish_type_txt, dish_id_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dish_title_txt = itemView.findViewById(R.id.dish_name_txt);
            dish_type_txt = itemView.findViewById(R.id.dish_tipo_txt);
            dish_id_txt = itemView.findViewById(R.id.dish_id_row);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
