package com.edina.compras.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.edina.compras.connection.Conexao;
import com.edina.compras.model.Item;
import com.edina.compras.model.User;

import java.util.ArrayList;

public class ItemDAO {

    private Conexao conexao;
    private SQLiteDatabase banco;

    public ItemDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public ArrayList<Item> load(User user) {
        ArrayList<Item> items = new ArrayList<>();

        ContentValues values = new ContentValues();
        values.put("descricao", "Livro");
        values.put("email", user.getEmail());
        values.put("status", Boolean.TRUE);
        values.put("quantidade", 4);
        banco.insert("items", null, values);
        String query = "SELECT * FROM items WHERE email = '" + user.getEmail() + "'";
        Cursor cursor = banco.rawQuery(query, null);
        while (cursor.moveToNext()){

            Item item = new Item();
            item.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            item.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
            item.setQuantidade(cursor.getInt(cursor.getColumnIndex("quantidade")));
            item.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            items.add(item);

        }

        return items;
    }
}
