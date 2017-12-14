package com.example.amank.credsnapdeal;

/**
 * Created by amank on 14-12-2017.
 */

public class UrlCreator {
    public static String getFirsstRequestUrl(String keyword){
        String url = String.format("https://www.snapdeal.com/search?keyword=%s&sort=rlvncy", keyword);
        return url;
    }

    public static String getLoadMoreUrl(Integer value, String keyword){
        String url = String.format("https://www.snapdeal.com/acors/json/product/get/search/0/%s/20?q=&sort=rlvncy&brandPageUrl=&keyword=%s&searchState=previousRequest=true|serviceabilityUsed=false|filterState=null&pincode=&vc=&webpageName=searchResult&campaignId=&brandName=&isMC=false&clickSrc=unknown&showAds=true&cartId=&page=srp", value.toString(), keyword);
        return url;
    }
}
