package com.example.neonadeuri.commomNeonaderi;

public class InCartItem {
    private String itemName;
    private String itemPrice;
    private String itemNative;
    private String itemInfo;

    public InCartItem() {
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setItemNative(String itemNative) {
        this.itemNative = itemNative;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getItemNative() {
        return itemNative;
    }

    public String getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(String itemInfo) {
        this.itemInfo = itemInfo;
    }


}
