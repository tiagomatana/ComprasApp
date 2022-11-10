package com.edina.compras;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btEntrar;
    private Button btCadastrar;
    private Button btListar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0000ff")));
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.abs_layout);


        btEntrar = findViewById(R.id.btEntrar);
        btCadastrar = findViewById(R.id.btCadastrar);
        btListar = findViewById(R.id.btListar);

        btEntrar.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
        });

        btCadastrar.setOnClickListener(view -> {
            startActivity(new Intent(this, CadastroActivity.class));
        });

        btListar.setOnClickListener(view -> {
            startActivity(new Intent(this, InicioActivity.class));
        });
    }

//    public boolean onOptionsItemSelected(MenuItem item){
//        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
//        startActivity(myIntent);
//        return true;
//    }
}