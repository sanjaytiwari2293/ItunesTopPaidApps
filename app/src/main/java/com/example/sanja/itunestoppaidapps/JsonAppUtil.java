package com.example.sanja.itunestoppaidapps;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sanja on 2/23/2017.
 */

public class JsonAppUtil {

    public static ArrayList<App> parser(String data){
        ArrayList<App> appArrayList = new ArrayList<>();

        JSONObject root = null;
        try {
            root = new JSONObject(data);
            JSONObject feed = root.getJSONObject("feed");
            JSONArray jsonArray = feed.getJSONArray("entry");

            for(int i=0;i<jsonArray.length();i++){

                JSONObject appJsonObject = jsonArray.getJSONObject(i);
                App app = new App();

                JSONObject name = appJsonObject.getJSONObject("im:name");
                String nameLabel = name.getString("label");

                JSONArray image = appJsonObject.getJSONArray("im:image");
                JSONObject img = image.getJSONObject(0);

                String imgUrl = img.getString("label");
                Log.d("demo","toString method url  "+imgUrl);

                JSONObject price = appJsonObject.getJSONObject("im:price");
                JSONObject attribute = price.getJSONObject("attributes");
                String priceAmt = attribute.getString("amount");
                String priceCurrency = attribute.getString("currency");
                //String fullPrice = priceCurrency+" "+ priceAmt;

                app.setName(nameLabel);
                app.setImageUrl(imgUrl);
                app.setPrice(priceAmt);
                //Log.d("demo","App DETAILS....->  "+app.toString());
                Log.d("demo","toString method  DONE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1 "+app.toString());

                appArrayList.add(app);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }




        return appArrayList;
    }
}
