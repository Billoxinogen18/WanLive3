package com.ogalo.partympakache.wanlive;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ogalo.partympakache.wanlive.app.AppController;
import com.ogalo.partympakache.wanlive.data.Post;
import com.ogalo.partympakache.wanlive.data.WanItem;

import java.util.HashMap;
import java.util.Map;

import static java.security.AccessController.getContext;

public class DetailsActivity extends AppCompatActivity {

    private String uid;

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private TextView timer;
    private RatingBar ratings;
    private TextView status;
    private TextView costs;
    private DatabaseReference mDatabase;
    private FloatingActionButton floaty;

    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();



        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        WanImageView feedImageView = findViewById(R.id.header_image);

        Intent i = getIntent();
        // getting attached intent data

        String longitude = i.getStringExtra("longitude");
        String latitude = i.getStringExtra("latitude");
        floaty=(FloatingActionButton)findViewById(R.id.fab);
        String cost = i.getStringExtra("cost");
        String rating = i.getStringExtra("rating");
        String time = i.getStringExtra("time");
        String imge =i.getStringExtra("imge");
        String titles = i.getStringExtra("titles");
        String content = i.getStringExtra("status");
        WanItem items=new WanItem();



        String checking=i.getStringExtra("checkeds");
        uid = current_user.getUid();
        Boolean bools=items.getChecked();

//
//        Toast.makeText(this, checking, Toast.LENGTH_SHORT).show();


        if(checking.equals("true"))
        {

            floaty.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.heart_liked));

//            writeNewPost(uid, content, titles, cost, rating);
//            Toast.makeText(this, "Its true", Toast.LENGTH_SHORT).show();


        }
        else if(checking.equals("false"))
        {

            floaty.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.heart_notliked));


//            Toast.makeText(this, "Nothing bro", Toast.LENGTH_SHORT).show();

        }
        else
        {
//
//            Toast.makeText(this, "Not specified", Toast.LENGTH_SHORT).show();

        }


        status=(TextView)findViewById(R.id.main);
        timer=(TextView)findViewById(R.id.timesn);
        title=(TextView)findViewById(R.id.titles);
        costs=(TextView)findViewById(R.id.costi);
        ratings=(RatingBar) findViewById(R.id.myratingto);

        status.setText(content);
        timer.setText(time);
        costs.setText(cost);
        title.setText(titles);


        ratings.setRating(Float.parseFloat(rating));

        LayerDrawable stars = (LayerDrawable) ratings.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);


//        Toast.makeText(this, "Longitude is "+longitude+" Latitude is "+latitude+" Cost is "+cost+" Rating is "+rating, Toast.LENGTH_SHORT).show();


floaty.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {






    }
});


        feedImageView.setImageUrl(imge, imageLoader);
        feedImageView.setVisibility(View.VISIBLE);
        feedImageView
                .setResponseObserver(new WanImageView.ResponseObserver() {
                    @Override
                    public void onError() {
                        Toast.makeText(DetailsActivity.this, "Check your connection", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess() {
                    }
                });


    }







    private void writeNewPost(String userId, String username, String title, String body, String rating) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        mDatabase=FirebaseDatabase.getInstance().getReference();
        String key = mDatabase.child("posts").push().getKey();
        Post post = new Post(userId, username, title, body,  rating);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/favos/" + key, postValues);
        childUpdates.put("/user-favos/" + userId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }

}
