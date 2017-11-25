package com.example.amank.credsnapdeal;

import android.nfc.Tag;
import android.util.Log;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by amank on 25-11-2017.
 */

public class WebpageSourceCodeReader {
    private static String TAG = WebpageSourceCodeReader.class.getSimpleName();

    private static String xmlImageElementsPath1 = "//img[@class='product-image']";
    private static String xmlImageElementsPath2 = "//img[@class='product-image lazy-load']";

    private static HtmlCleaner cleaner;
    private static CleanerProperties properties;
    private static TagNode node;

    private static void setupHtmlCleaner(){
        cleaner = new HtmlCleaner();
        properties = cleaner.getProperties();
        properties.setAllowHtmlInsideAttributes(true);
        properties.setAllowMultiWordAttributes(true);
        properties.setRecognizeUnicodeChars(true);
        properties.setOmitComments(true);
    }
    public static ArrayList<ProductModel> read(HttpURLConnection con){
        setupHtmlCleaner();
        try {
            node = cleaner.clean(new InputStreamReader(con.getInputStream()));
            return parseHtml();
        } catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    private static ArrayList<ProductModel> parseHtml(){
        ArrayList<Object[]> imgElementsList = new ArrayList<>();
        try {
            imgElementsList.add(node.evaluateXPath(xmlImageElementsPath1));
            imgElementsList.add(node.evaluateXPath(xmlImageElementsPath2));
        } catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
        return parseToProducts(imgElementsList);
    }

    private static ArrayList<ProductModel> parseToProducts(ArrayList<Object[]> tags){
        ArrayList<ProductModel> products = new ArrayList<>();

        for(Object[] items : tags){
            int len = items.length;
            for(int i = 0; i < len; i++){
                TagNode node = (TagNode) items[i];
                String src = node.getAttributeByName("src");
                if(src == null){
                    src = node.getAttributeByName("data-src");
                }
                ProductModel product = new ProductModel(src, node.getAttributeByName("title"));
                products.add(product);
            }
        }
        return  products;
    }
}
