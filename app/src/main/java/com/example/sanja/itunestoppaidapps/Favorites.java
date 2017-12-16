package com.example.sanja.itunestoppaidapps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class Favorites extends AppCompatActivity {
    ListView listView;
    ArrayList<App> favList = new ArrayList<>();
    FavAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        favList = (ArrayList<App>) getIntent().getSerializableExtra(MainActivity.favorite_key);
        Log.d("demo","fav activity "+favList.toString());


        listView = (ListView)findViewById(R.id.listViewFav);
        adapter = new FavAdapter(this, R.layout.row_item_layout, favList);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);

    }
}
