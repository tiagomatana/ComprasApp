package com.edina.compras;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.edina.compras.adapter.ItemAdapter;
import com.edina.compras.dao.ItemDAO;
import com.edina.compras.dao.UserDAO;
import com.edina.compras.model.Item;
import com.edina.compras.model.User;

import java.util.ArrayList;
import java.util.List;

public class InicioActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserDAO userDAO;
    private ItemDAO itemDAO;
    private List<User> usuarios;
    private List<User> usuariosFiltrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        User logado = (User) getIntent().getSerializableExtra("USUARIO");
        if (!logado.isLogged()) {
            startActivity(new Intent(this, MainActivity.class));
        }
        getSupportActionBar().setTitle("Bem-vindo " + logado.getNome().toUpperCase());
        recyclerView = findViewById(R.id.recyclerView);
        itemDAO = new ItemDAO(this);
        ArrayList<Item> items = itemDAO.load(logado);

        ItemAdapter adapter = new ItemAdapter(items);
        recyclerView.setAdapter(adapter);
        items = itemDAO.load(logado);



    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(myIntent);
        return true;
    }
}