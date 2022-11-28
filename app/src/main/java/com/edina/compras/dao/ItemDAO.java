package com.edina.compras.dao;

import android.annotation.SuppressLint;
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

    @SuppressLint("Range")
    public ArrayList<Item> load(User user) {
        ArrayList<Item> items = new ArrayList<>();

        String query = "SELECT * FROM items WHERE email = '" + user.getEmail() + "'";
        Cursor cursor = banco.rawQuery(query, null);
        while (cursor.moveToNext()){
            Item item = new Item();
            item.setId(cursor.getInt(cursor.getColumnIndex("id")));
            item.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            item.setStatus(cursor.getInt(cursor.getColumnIndex("status")) == 1 ? true : false);
            item.setQuantidade(cursor.getInt(cursor.getColumnIndex("quantidade")));
            item.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            items.add(item);
        }

        return items;
    }

    public Long save(Item item) {
        ContentValues values = new ContentValues();
        values.put("descricao", item.getDescricao());
        values.put("email", item.getEmail());
        values.put("status", item.getStatus());
        values.put("quantidade", item.getQuantidade());
        return banco.insert("items", null, values);
    }

    public void update(Item item) {
        ContentValues values = new ContentValues();
        values.put("descricao", item.getDescricao());
        values.put("status", item.getStatus() == true ? 1 : 0);
        values.put("quantidade", item.getQuantidade());
        banco.update("items", values, "id = ?", new String[]{Integer.toString(item.getId())});
    }

    public void remove(Item item) {
        banco.delete("items", "id = ?", new String[]{Integer.toString(item.getId())});
    }
}
