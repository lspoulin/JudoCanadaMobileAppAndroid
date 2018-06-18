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

    public void addToCart(Product product, int qte) {
        for(Products p : products){
            if (p.product.getEntityId() == product.getEntityId()){
                p.quantity+=qte;
                return;
            }
        }
        products.add(new Products(product, qte));
    }

    public Products getProducts(int i){
        return products.get(i);
    }


    public class Products{
        public Product product;
        public int quantity;
        public Products(){}
        public Products(Product product, int qte){
            this.product = product;
            this.quantity = qte;
        }

    }

    public int getCount(){
        return products.size();
    }
}
