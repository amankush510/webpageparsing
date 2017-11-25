package com.example.amank.credsnapdeal;

/**
 * Created by amank on 25-11-2017.
 */

public class ProductModel {
    private String url;
    private String title;

    public ProductModel(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }
}
