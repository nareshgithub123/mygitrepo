package com.example.ahd.productdetails;

import android.util.Log;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONProductsHandler {

	
	private static String hostName = "www.gigathinking.com";
	private static String jSonProductsContentURL = "https://www.gigathinking.com/wc-api/v2/products?consumer_key=ck_073a0441e91396486d5e056c9043c9f7&consumer_secret=cs_ce0ad0531305f86fd53ec746c53a042a";
	private static String jSonStrArrayofProducts = "products";
	
	final public int PRODUCT_TITLE_REQUIRED = 1;
	final public int PRODUCT_PERMALINK_REQUIRED = 2;
	final public int PRODUCT_OFFER_PRICE_REQUIRED = 3;
	final public int PRODUCT_MARKET_PRICE_REQUIRED = 4;
	final public int PRODUCT_IMAGE_REQUIRED = 5;
	final public int PRODUCT_RATING_REQUIRED = 6;
	final public int PRODUCT_ID_REQUIRED = 7;
	final public int PRODUCT_INSTOCK_REQUIRED = 8;
	final public int PRODUCT_DESCRIPTION_REQUIRED = 9;
	final public int PRODUCT_RATING_COUNT_REQUIRED = 10;
	final public int PRODUCT_TOTAL_SALES_REQUIRED = 11;


	/*
	 * Static Block: To verify Hostname before opening url connection
	 */
	static {
	    HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier()
	        {
	            public boolean verify(String hostname, SSLSession session)
	            {
	                    if (hostname.equals(hostName))
	                    return true;
	                return false;
	            }

		    });
	}
	
	public String getProductDetails(JSONObject product, int requiredItem) throws JSONException
	{
		switch(requiredItem)
		{
			case PRODUCT_TITLE_REQUIRED:
				return product.getString("title");
			case PRODUCT_PERMALINK_REQUIRED:
				return product.getString("permalink");
			case PRODUCT_OFFER_PRICE_REQUIRED:
				return product.getString("sale_price");
			case PRODUCT_MARKET_PRICE_REQUIRED:
				return product.getString("regular_price");
			case PRODUCT_IMAGE_REQUIRED:
				JSONArray imageDetails = product.getJSONArray("images");
				JSONObject imageObj = imageDetails.getJSONObject(0);
				if(imageObj != null)
					return imageObj.getString("src");

			case PRODUCT_RATING_REQUIRED:
				return product.getString("average_rating");
			case PRODUCT_ID_REQUIRED:
				return product.getString("id");
			case PRODUCT_INSTOCK_REQUIRED:
				return Boolean.toString(product.getBoolean("in_stock"));
			case PRODUCT_DESCRIPTION_REQUIRED:
				return product.getString("description");
			case PRODUCT_RATING_COUNT_REQUIRED:
				return Integer.toString(product.getInt("rating_count"));
			case PRODUCT_TOTAL_SALES_REQUIRED:
				return Integer.toString(product.getInt("total_sales"));
			default: return null;
		}
	}

	private static String extractJsonStrFromURL(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read); 

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }

    }

	public String getListOfProducts() throws Exception {
		String jSonProductsStr = extractJsonStrFromURL(jSonProductsContentURL);
		return jSonProductsStr;
	}
	
	public ArrayList<String> getProductBasicDetails(int positionOfProduct) throws Exception {
		ArrayList<String> productBasicDetails = new ArrayList<>();
		String jSonProductsStr = extractJsonStrFromURL(jSonProductsContentURL);
		JSONObject jSonProductObject = new JSONObject(jSonProductsStr);
		JSONArray jSonProductsArray = jSonProductObject.getJSONArray(jSonStrArrayofProducts);
		final JSONObject product1 = jSonProductsArray.getJSONObject(positionOfProduct);
		JSONProductsHandler jsonProdHandler = new JSONProductsHandler();
		String prodTitle = jsonProdHandler.getProductDetails(product1, jsonProdHandler.PRODUCT_TITLE_REQUIRED);
		String prodRating = jsonProdHandler.getProductDetails(product1, jsonProdHandler.PRODUCT_RATING_REQUIRED);
		String prodImage = jsonProdHandler.getProductDetails(product1, jsonProdHandler.PRODUCT_IMAGE_REQUIRED);
		productBasicDetails.add(0, prodTitle);
		productBasicDetails.add(1, prodRating);
		productBasicDetails.add(2, prodImage);
		return productBasicDetails;
	}

	public int getNumberOfAvailableProducts() throws Exception {
		String jSonProductsStr = extractJsonStrFromURL(jSonProductsContentURL);
		JSONObject jSonProductObject = new JSONObject(jSonProductsStr);
		JSONArray jSonProductsArray = jSonProductObject.getJSONArray(jSonStrArrayofProducts);
		return jSonProductsArray.length();
	}

	public String[] getProductAllImages(JSONObject product) throws JSONException {
		JSONArray imageDetails = product.getJSONArray("images");
		String[] productAllImages = new String[imageDetails.length()];
		for (int i = 0; i < imageDetails.length(); i++)
		{
			JSONObject imageObj = imageDetails.getJSONObject(i);
			productAllImages[i] = imageObj.getString("src");
		}
		return productAllImages;
	}

	public JSONObject getIndividualProductObject(int positionOfProduct) throws Exception
	{
		String jSonProductsStr = extractJsonStrFromURL(jSonProductsContentURL);
		JSONObject jSonProductObject = new JSONObject(jSonProductsStr);
		JSONArray jSonProductsArray = jSonProductObject.getJSONArray(jSonStrArrayofProducts);
		JSONObject product = jSonProductsArray.getJSONObject(positionOfProduct);
		return product;
	}

}

