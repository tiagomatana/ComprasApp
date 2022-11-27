package com.edina.compras;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class AdicionarItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_item);

        findViewById(R.id.btAdicionar).setOnClickListener(view -> {
            Toast.makeText(this, "TESTE", Toast.LENGTH_SHORT).show();
        });
    }
}