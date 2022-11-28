package com.edina.compras;

import com.edina.compras.model.Item;

public interface Callback {
    void editarItem(Item item);
    void removerItem(Item item);
}
