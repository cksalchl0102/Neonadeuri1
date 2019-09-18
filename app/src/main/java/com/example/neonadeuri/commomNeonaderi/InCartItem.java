package com.example.neonadeuri.commomNeonaderi;

public class InCartItem {
    private String itemName;
    private String itemPrice;
    private String itemNumber;
    private String itemInfo;

    public InCartItem() {
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public String getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(String itemInfo) {
        this.itemInfo = itemInfo;
    }
    public void setItemNumber(){
        int i = 0;
        i = Integer.parseInt(this.itemNumber);
        i++;
        this.itemNumber = String.valueOf(i);
    }
}
