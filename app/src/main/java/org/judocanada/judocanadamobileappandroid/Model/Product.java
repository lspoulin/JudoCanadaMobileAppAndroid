package org.judocanada.judocanadamobileappandroid.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by lspoulin on 2018-06-07.
 */

public class Product  implements Mappable, Parcelable {
    private int entityId;
    private String typeId, sku, description, metaKeyword, shortDescription, name, metaTitle, metaDescription, imageUrl;
    private double regularPriceWithTax, regularPriceWithoutTax, finalPriceWithTax, finalPriceWithoutTax;
    private boolean isSaleable;

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMetaKeyword() {
        return metaKeyword;
    }

    public void setMetaKeyword(String metaKeyword) {
        this.metaKeyword = metaKeyword;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getRegularPriceWithTax() {
        return regularPriceWithTax;
    }

    public void setRegularPriceWithTax(double regularPriceWithTax) {
        this.regularPriceWithTax = regularPriceWithTax;
    }

    public double getRegularPriceWithoutTax() {
        return regularPriceWithoutTax;
    }

    public void setRegularPriceWithoutTax(double regularPriceWithoutTax) {
        this.regularPriceWithoutTax = regularPriceWithoutTax;
    }

    public double getFinalPriceWithTax() {
        return finalPriceWithTax;
    }

    public void setFinalPriceWithTax(double finalPriceWithTax) {
        this.finalPriceWithTax = finalPriceWithTax;
    }

    public double getFinalPriceWithoutTax() {
        return finalPriceWithoutTax;
    }

    public void setFinalPriceWithoutTax(double finalPriceWithoutTax) {
        this.finalPriceWithoutTax = finalPriceWithoutTax;
    }

    public boolean isSaleable() {
        return isSaleable;
    }

    public void setSaleable(boolean saleable) {
        isSaleable = saleable;
    }

    public Product(){}

    @Override
    public int describeContents() {
        return 0;
    }



    @Override
    public void mapJSON(JSONObject object) {
        try {
            entityId = object.getInt("entity_id");
            typeId = object.getString("type_id");
            sku = object.getString("sku");
            description = object.getString("description");
            metaKeyword = object.getString("meta_keyword");
            shortDescription = object.getString("short_description");
            name = object.getString("name");
            metaTitle = object.getString("meta_title");
            metaDescription = object.getString("meta_description");
            regularPriceWithTax = object.getDouble("regular_price_with_tax");
            regularPriceWithoutTax = object.getDouble("regular_price_without_tax");
            finalPriceWithTax = object.getDouble("final_price_with_tax");
            finalPriceWithoutTax = object.getDouble("final_price_without_tax");
            isSaleable = object.getInt("is_saleable")==1;
            imageUrl = object.getString("image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toJSON() {
        return null;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(entityId);
        parcel.writeString(typeId);
        parcel.writeString(sku);
        parcel.writeString(description);
        parcel.writeString(metaKeyword);
        parcel.writeString(shortDescription);
        parcel.writeString(name);
        parcel.writeString(metaTitle);
        parcel.writeString(metaDescription);
        parcel.writeDouble(regularPriceWithTax);
        parcel.writeDouble(regularPriceWithoutTax);
        parcel.writeDouble(finalPriceWithTax);
        parcel.writeDouble(finalPriceWithoutTax);
        parcel.writeInt((isSaleable)?1:0);
        parcel.writeString(imageUrl);
    }

    public static final Parcelable.Creator<Product> CREATOR = new Creator<Product>() {

        @Override
        public Product createFromParcel(Parcel parcel) {
            Product product = new Product();

            product.setEntityId(parcel.readInt());
            product.setTypeId(parcel.readString());
            product.setSku(parcel.readString());
            product.setDescription(parcel.readString());
            product.setMetaKeyword(parcel.readString());
            product.setShortDescription(parcel.readString());
            product.setName(parcel.readString());
            product.setMetaTitle(parcel.readString());
            product.setMetaDescription(parcel.readString());
            product.setRegularPriceWithTax(parcel.readDouble());
            product.setRegularPriceWithTax(parcel.readDouble());
            product.setFinalPriceWithTax(parcel.readDouble());
            product.setFinalPriceWithoutTax(parcel.readDouble());
            product.setSaleable(parcel.readInt()==1);
            product.setImageUrl(parcel.readString());

            return product;
        }

        @Override
        public Product[] newArray(int i) {
            return new Product[i];
        }
    };
}
