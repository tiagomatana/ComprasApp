package com.edina.compras;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.service.controls.actions.FloatAction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edina.compras.adapter.ItemAdapter;
import com.edina.compras.dao.ItemDAO;
import com.edina.compras.model.Item;
import com.edina.compras.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class InicioActivity extends AppCompatActivity implements Callback {

    private RecyclerView recyclerView;
    private ItemDAO itemDAO;
    private AlertDialog adicionarItem;
    // variables for our buttons.
    FloatingActionButton generatePDFbtn;

    // declaring width and height
    // for our PDF file.
    int pageHeight = 1120;
    int pagewidth = 792;

    // creating a bitmap variable
    // for storing our images
    Bitmap bmp, scaledbmp;

    // constant code for runtime permissions
    private static final int PERMISSION_REQUEST_CODE = 200;


    public void lista(User logado) {
        recyclerView = findViewById(R.id.recyclerView);
        itemDAO = new ItemDAO(this);
        ArrayList<Item> items = itemDAO.load(logado);

        ItemAdapter adapter = new ItemAdapter(items, this);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        User logado = (User) getIntent().getSerializableExtra("USUARIO");
        if (!logado.isLogged()) {
            startActivity(new Intent(this, MainActivity.class));
        }
        getSupportActionBar().setTitle("Bem-vindo");
        lista(logado);

        findViewById(R.id.btAddItem).setOnClickListener(view -> {
            this.adicionarItem(logado);
        });

        generatePDFbtn = findViewById(R.id.idBtnGeneratePDF);
//        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.shopping_cart);
//        scaledbmp = Bitmap.createScaledBitmap(bmp, 140, 140, false);

        // below code is used for
        // checking our permissions.
        if (checkPermission()) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }

        generatePDFbtn.setOnClickListener(v -> {
            // calling method to
            // generate our PDF file.
            generatePDF();
//            share();
        });
    }

    public void adicionarItem(User logado) {
        LayoutInflater li = getLayoutInflater();
        View v = li.inflate(R.layout.activity_gerenciar_item, null);
        EditText descricao = v.findViewById(R.id.addDescricaoText);
        EditText quantidade = v.findViewById(R.id.addQuantidadeText);
        Item item = new Item();
        item.setEmail(logado.getEmail());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Adicionar Item");
        builder.setCancelable(Boolean.TRUE);
        builder.setView(v);
        builder.setPositiveButton("Salvar", (dialogInterface, i) -> {
            item.setQuantidade(Integer.parseInt(quantidade.getText().toString()));
            item.setDescricao(descricao.getText().toString());
            itemDAO.save(item);
            Toast.makeText(this, "Alterado com sucesso.", Toast.LENGTH_SHORT).show();
            logado.setEmail(item.getEmail());
            lista(logado);
        });
        builder.setNegativeButton("Cancelar", (dialogInterface, i) -> {
            lista(logado);
        });
        adicionarItem = builder.create();
        adicionarItem.show();
    }

    public void editarItem(Item item) {
        LayoutInflater li = getLayoutInflater();
        View v = li.inflate(R.layout.activity_gerenciar_item, null);
        EditText descricao = v.findViewById(R.id.addDescricaoText);
        EditText quantidade = v.findViewById(R.id.addQuantidadeText);
        descricao.setText(item.getDescricao());
        quantidade.setText(Integer.toString(item.getQuantidade()));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Adicionar Item");
        User logado = new User();
        builder.setPositiveButton("Atualizar", (dialogInterface, i) -> {
            item.setQuantidade(Integer.parseInt(quantidade.getText().toString()));
            item.setDescricao(descricao.getText().toString());
            itemDAO.update(item);
            Toast.makeText(this, "Alterado com sucesso.", Toast.LENGTH_SHORT).show();
            logado.setEmail(item.getEmail());
            lista(logado);
        });
        builder.setNegativeButton("Cancelar", (dialogInterface, i) -> {

        });
        builder.setView(v);
        adicionarItem = builder.create();
        adicionarItem.show();
    }

    public void removerItem(Item item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("REMOVER ITEM");
        builder.setMessage("Tem certeza que deseja remover o item " + item.getDescricao() +"?");
        User logado = new User();
        builder.setPositiveButton("Remover", (dialogInterface, i) -> {
            itemDAO.remove(item);
            Toast.makeText(this, "Removido com sucesso.", Toast.LENGTH_SHORT).show();
            logado.setEmail(item.getEmail());
            lista(logado);
        });
        builder.setNegativeButton("Cancelar", (dialogInterface, i) -> {

        });
        adicionarItem = builder.create();
        adicionarItem.show();
    }


    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(myIntent);
        return true;
    }

    private void generatePDF() {
        // creating an object variable
        // for our PDF document.
        PdfDocument pdfDocument = new PdfDocument();

        // two variables for paint "paint" is used
        // for drawing shapes and we will use "title"
        // for adding text in our PDF file.
        Paint paint = new Paint();
        Paint title = new Paint();

        // we are adding page info to our PDF file
        // in which we will be passing our pageWidth,
        // pageHeight and number of pages and after that
        // we are calling it to create our PDF.
        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();

        // below line is used for setting
        // start page for our PDF file.
        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);

        // creating a variable for canvas
        // from our page of PDF.
        Canvas canvas = myPage.getCanvas();

        // below line is used to draw our image on our PDF file.
        // the first parameter of our drawbitmap method is
        // our bitmap
        // second parameter is position from left
        // third parameter is position from top and last
        // one is our variable for paint.
//        canvas.drawBitmap(scaledbmp, 56, 40, paint);

        // below line is used for adding typeface for
        // our text which we will be adding in our PDF file.
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // below line is used for setting text size
        // which we will be displaying in our PDF file.
        title.setTextSize(15);

        // below line is sued for setting color
        // of our text inside our PDF file.
        title.setColor(ContextCompat.getColor(this, R.color.green_200));

        // below line is used to draw text in our PDF file.
        // the first parameter is our text, second parameter
        // is position from start, third parameter is position from top
        // and then we are passing our variable of paint which is title.
        canvas.drawText("A portal for IT professionals.", 209, 100, title);
        canvas.drawText("Geeks for Geeks", 209, 80, title);

        // similarly we are creating another text and in this
        // we are aligning this text to center of our PDF file.
        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        title.setColor(ContextCompat.getColor(this, R.color.green_200));
        title.setTextSize(15);

        // below line is used for setting
        // our text to center of PDF.
        title.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("This is sample document which we have created.", 396, 560, title);

        // after adding all attributes to our
        // PDF file we will be finishing our page.
        pdfDocument.finishPage(myPage);

        // below line is used to set the name of
        // our PDF file and its path.
//        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "GFG.pdf");
        File file = new File(Environment.getExternalStorageDirectory(), "GFG.pdf");
        try {
            // after creating a file name we will
            // write our PDF file to that location.
            pdfDocument.writeTo(new FileOutputStream(file));

            // below line is to print toast message
            // on completion of PDF generation.
            Toast.makeText(InicioActivity.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // below line is used
            // to handle error
            e.printStackTrace();
        }
        // after storing our pdf to that
        // location we are closing our PDF file.
        pdfDocument.close();
        Uri uri = Uri.fromFile(file);
        share(uri);

    }

    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denined.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    public void share (Uri file) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
//        sendIntent.putExtra(Intent.EXTRA_TEXT,"1x TEST \n3x app");
        sendIntent.putExtra(Intent.EXTRA_FROM_STORAGE,file);
//        sendIntent.setType("application/pdf");
        sendIntent.setType("text/plain");
//        sendIntent.setPackage("com.whatsapp");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }
}