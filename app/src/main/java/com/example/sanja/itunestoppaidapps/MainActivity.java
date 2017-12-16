package com.example.sanja.itunestoppaidapps;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements FetchDataAsync.IData {

    ArrayList<App> appArrayList = new ArrayList<>();
    ListView listView;
    ArrayList<App> favArrayList ;
    AppAdapter adapter;
    public static final String favorite_key = "favorite";

    ArrayList<App> appSortIncrease = new ArrayList<>();
    ArrayList<App> appSortDecrease = new ArrayList<>();
    ProgressBar pb;
    TextView load;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("iTunes Top Paid Apps");
        pb = (ProgressBar)findViewById(R.id.progressBar);
        load = (TextView)findViewById(R.id.textView);
        listView = (ListView)findViewById(R.id.listView);
        pb.setVisibility(View.VISIBLE);
        load.setVisibility(View.VISIBLE);
        new FetchDataAsync(MainActivity.this).execute("https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json");




    }

    @Override
    protected void onResume() {
        super.onResume();

        adapter = new AppAdapter(this, R.layout.row_item_layout, appArrayList);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout,menu);
        sharedpreferences = this.getSharedPreferences(MyPREFERENCES, Context.MODE_APPEND);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.refresh_list:
                Log.d("demo","Refreshing List");
                new FetchDataAsync(MainActivity.this).execute("https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json");
                if(appArrayList.size()==0){
                    Toast.makeText(getApplicationContext(),"Try again",Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.d("demo","Success!!");
                    adapter = new AppAdapter(this, R.layout.row_item_layout, appArrayList);
                    listView.setAdapter(adapter);
                    adapter.setNotifyOnChange(true);
                }
                break;

            case R.id.favorites:
                Log.d("demo","Showing Favorites");
                ///
                favArrayList = new ArrayList<>();
                for(int i=0; i<appArrayList.size();i++){
                    App app =new App();
                    app = appArrayList.get(i);
                    if(sharedpreferences.contains(app.getName())&& !favArrayList.contains(app.getName())){
                       // if(!favArrayList.contains(app.getImageUrl()))
                        favArrayList.add(app);
                        Log.d("demo","Success size!!............."+favArrayList.size());

                    }else{

                        Log.d("demo","Success!!............."+i);
                    }
                }


                ///
                //favArrayList = adapter.favorites;
                Log.d("demo","Showing Favorites"+favArrayList);
                Intent intent = new Intent(getApplicationContext(),Favorites.class);
                intent.putExtra(favorite_key, favArrayList);
                startActivity(intent);

                break;

            case R.id.sort_increase:
                Log.d("demo","Sorting Increasingly");
                appSortIncrease = appArrayList;

                Collections.sort(appSortIncrease, new Comparator<App>() {
                    @Override
                    public int compare(App p1, App p2) {
                        return p1.getPrice().compareTo(p2.getPrice());
                    }
                });
                adapter = new AppAdapter(this, R.layout.row_item_layout, appSortIncrease);
                listView.setAdapter(adapter);
                adapter.setNotifyOnChange(true);

                break;

            case R.id.sort_decrease:
                Log.d("demo","Sorting Decreasingly");
                appSortDecrease = appArrayList;

                Collections.sort(appSortDecrease, new Comparator<App>() {
                    @Override
                    public int compare(App p1, App p2) {
                        return p2.getPrice().compareTo(p1.getPrice());
                    }
                });
                adapter = new AppAdapter(this, R.layout.row_item_layout, appSortDecrease);
                listView.setAdapter(adapter);
                adapter.setNotifyOnChange(true);

                break;

            default:
                Log.d("demo","Something is Wrong!!");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setUpData(ArrayList<App> apps) {
        appArrayList = apps;
        if(appArrayList.size()==0){
            Toast.makeText(getApplicationContext(),"Try again",Toast.LENGTH_SHORT).show();
        }
        else{
            Log.d("demo","Success!!");
            adapter = new AppAdapter(this, R.layout.row_item_layout, appArrayList);
            pb.setVisibility(View.INVISIBLE);
            load.setVisibility(View.INVISIBLE);
            listView.setAdapter(adapter);
            adapter.setNotifyOnChange(true);
        }

    }

    @Override
    public Context getContext() {
        return null;
    }
}
