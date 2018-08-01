package com.ogalo.partympakache.wanlive;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ogalo.partympakache.wanlive.adapter.WanAdapter;
import com.ogalo.partympakache.wanlive.app.AppController;
import com.ogalo.partympakache.wanlive.data.WanItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;



public class MainFeed extends AppCompatActivity  {
    private static final String TAG = "WanLive";
    private ListView listView;
    private String ischecked;
    private WanAdapter listAdapter;
    private List<WanItem> feedItems;
    private ProgressDialog mRegProgress;
    private String URL_FEED = "http://www.wayawaya.co.ke/wayawaya.co.ke/bill/wanlive/wanlive_thebalanceofdestiny.json";

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main10);

        mRegProgress = new ProgressDialog(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        FloatingActionButton settimgs=(FloatingActionButton)findViewById(R.id.settingsview);
//        FloatingActionButton favours=(FloatingActionButton)findViewById(R.id.favourites);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//             startActivity(new Intent(getApplicationContext(), WanMaps.class));
//            }
//        });

//        settimgs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
//            }
//        });

//        favours.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "You still have no favourites in your feed. Explore and tap thumbs up for more.", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        listView = (ListView) findViewById(R.id.listi);

        feedItems = new ArrayList<WanItem>();

        listAdapter = new WanAdapter(this, feedItems);
        listView.setAdapter(listAdapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String titles = ((TextView) view.findViewById(R.id.titles)).getText().toString();
                String longitude = ((TextView) view.findViewById(R.id.longitude)).getText().toString();
                String latitude = ((TextView) view.findViewById(R.id.latitude)).getText().toString();
                String rating = ((TextView) view.findViewById(R.id.rating)).getText().toString();
                String cost = ((TextView) view.findViewById(R.id.cost)).getText().toString();
                String time = ((TextView) view.findViewById(R.id.time)).getText().toString();
                String imge = ((TextView) view.findViewById(R.id.imge)).getText().toString();
                ToggleButton butchecked=((ToggleButton) view.findViewById(R.id.toggleButton));
                Boolean ischeckeds=butchecked.isChecked();
                ischecked=ischeckeds.toString();

                Toast.makeText(MainFeed.this, ischecked, Toast.LENGTH_SHORT).show();
                String status = ((TextView) view.findViewById(R.id.statuses)).getText().toString();






                   Intent i=new Intent(getApplicationContext(), DetailsActivity.class);
                   i.putExtra("checkeds", ischecked);
                i.putExtra("longitude", longitude);
                i.putExtra("status", status);
                i.putExtra("latitude", latitude);
                i.putExtra("rating", rating);
                i.putExtra("cost", cost);
                i.putExtra("time", time);
                i.putExtra("imge", imge);
                i.putExtra("titles", titles);



                    startActivity(i);





            }
        });


        // These two lines not needed,
        // just to get the look of facebook (changing background color & hiding the icon)


        // We first check for cached request
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URL_FEED);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonFeed(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            // making fresh volley request and getting json

            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    URL_FEED, null, new Response.Listener<JSONObject>() {


                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG, "Response: " + response.toString());
                    if (response != null) {
                        parseJsonFeed(response);
                    }


                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                }
            });

            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReq);
        }

    }

    /**
     * Parsing json reponse and passing the data to feed view list adapter
     * */
    private void parseJsonFeed(JSONObject response) {
        try {

            mRegProgress.setTitle("WanLive");
            mRegProgress.setMessage("Loading Feed");

            mRegProgress.setCanceledOnTouchOutside(false);
            mRegProgress.show();
            JSONArray feedArray = response.getJSONArray("feed");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                WanItem item = new WanItem();
                item.setId(feedObj.getInt("id"));
                item.setName(feedObj.getString("name"));
//                Toast.makeText(this, ischecked, Toast.LENGTH_SHORT).show();
                item.setCost(feedObj.getString("cost"));

                item.setTimes(feedObj.getString("matime"));
                item.setStatus(feedObj.getString("status"));

                item.setRating(feedObj.getString("rating"));
                item.setLatitude(feedObj.getString("latitude"));
                item.setLongitude(feedObj.getString("longitude"));

                String longitudef=feedObj.getString("latitude");
//                 String longituded=feedObj.getString("longitude");
//                Toast.makeText(this, item.getLatitude(), Toast.LENGTH_SHORT).show();



                String name=feedObj.getString("name");
                String longitude=feedObj.getString("longitude");




                // Image might be null sometimes
                String image = feedObj.isNull("image") ? null : feedObj
                        .getString("image");
                item.setImge(image);

                item.setProfilePic(feedObj.getString("profilePic"));
                item.setTimeStamp(feedObj.getString("timeStamp"));
                item.setTimeStamp(feedObj.getString("timeStamp"));

                // url might be null sometimes
                String feedUrl = feedObj.isNull("url") ? null : feedObj
                        .getString("location");
                item.setUrl(feedUrl);



                feedItems.add(item);
            }

            // notify data changes to list adapater
            listAdapter.notifyDataSetChanged();
            mRegProgress.dismiss();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}

