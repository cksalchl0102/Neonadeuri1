package com.example.neonadeuri.commomNeonaderi;

public class ProductDTO {
    private String productName;
    private String productPrice;
    private String productInfo;
    private int productNumber;

    public ProductDTO() {
    }

    public ProductDTO(String productName, String productPrice, String productInfo,String productNumber){
        this.productName = productName;
        this.productPrice = productPrice;
        this.productInfo = productInfo;
        this.productNumber = Integer.parseInt(productNumber);
    }
}
