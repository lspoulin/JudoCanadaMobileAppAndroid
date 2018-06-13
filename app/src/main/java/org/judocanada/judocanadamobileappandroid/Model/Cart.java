package org.judocanada.judocanadamobileappandroid.Model;

import java.util.ArrayList;

/**
 * Created by lspoulin on 2018-06-13.
 */

public class Cart {
    private static Cart instance = null;
    private ArrayList<Products> products = null;


    public static Cart getInstance(){
        if(instance == null)
            instance = new Cart();
        return instance;
    }

    private Cart(){
        products = new ArrayList<Products>();

    }

    public void addToCart(int entityId, int qte) {
        for(Products p : products){
            if (p.entityId == entityId){
                p.quantity+=qte;
                return;
            }
        }
        products.add(new Products(entityId, qte));
    }


    private class Products{
        int entityId;
        int quantity;
        public Products(){}
        public Products(int entityId, int qte){
            this.entityId = entityId;
            this.quantity = qte;
        }

    }

    public int getCount(){
        return products.size();
    }
}
