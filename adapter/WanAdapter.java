package com.ogalo.partympakache.wanlive.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ogalo.partympakache.wanlive.Payments;
import com.ogalo.partympakache.wanlive.R;
import com.ogalo.partympakache.wanlive.WanImageView;
import com.ogalo.partympakache.wanlive.app.AppController;
import com.ogalo.partympakache.wanlive.data.Post;
import com.ogalo.partympakache.wanlive.data.WanItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WanAdapter extends BaseAdapter {
    private String uid;
    private DatabaseReference mDatabase;
    private DatabaseReference firebref;
	private Activity activity;
	private LayoutInflater inflater;
	private List<WanItem> wanItems;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public WanAdapter(Activity activity, List<WanItem> wanItems) {
		this.activity = activity;
		this.wanItems = wanItems;
	}

	@Override
	public int getCount() {
		return wanItems.size();
	}

	@Override
	public Object getItem(int location) {
		return wanItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.feetitemnew, null);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();

		TextView name = (TextView) convertView.findViewById(R.id.titles);
        Button buy=(Button) convertView.findViewById(R.id.buy);
        TextView time = (TextView) convertView.findViewById(R.id.time);
		TextView cost = (TextView) convertView.findViewById(R.id.cost);
        TextView longitude = (TextView) convertView.findViewById(R.id.longitude);
        TextView latitude = (TextView) convertView.findViewById(R.id.latitude);
        TextView rating = (TextView) convertView.findViewById(R.id.rating);
		TextView status = (TextView) convertView.findViewById(R.id.statuses);
        TextView imge = (TextView) convertView.findViewById(R.id.imge);
        final ToggleButton toggle=(ToggleButton) convertView.findViewById(R.id.toggleButton);
        final ToggleButton toggla=(ToggleButton) convertView.findViewById(R.id.toggl);
        RatingBar rates=(RatingBar)convertView.findViewById(R.id.myrating);
        firebref=mDatabase=FirebaseDatabase.getInstance().getReference();

		LayerDrawable stars = (LayerDrawable) rates.getProgressDrawable();
		stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);





        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        uid = current_user.getUid();

//		TextView timestamp = (TextView) convertView
//				.findViewById(R.id.timestamp);
//		TextView statusMsg = (TextView) convertView
//				.findViewById(R.id.txtStatusMsg);
//		TextView locations = (TextView) convertView.findViewById(R.id.locations);
//		TextView times = (TextView) convertView
//				.findViewById(R.id.times);
//		TextView cost = (TextView) convertView
//				.findViewById(R.id.cost);
//		TextView url = (TextView) convertView.findViewById(R.id.txtUrl);
//		NetworkImageView profilePic = (NetworkImageView) convertView
//				.findViewById(R.id.profilePic);
		WanImageView feedImageView = (WanImageView) convertView
				.findViewById(R.id.feedImage1);



		final WanItem item = wanItems.get(position);







        firebref.child("user-favos").child(uid).child(item.getName()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    toggle.setBackgroundResource(R.drawable.heart_liked);
                    toggle.setChecked(true);
                }
                else
                {
                    toggle.setChecked(false);
                    toggle.setBackgroundResource(R.drawable.heart_notliked);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });








        toggla.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    item.setChecked(true);







//                    writeNewPost(uid, item.getStatus(), item.getName(), item.getCost(), item.getRating());
                    toggla.setBackgroundResource(R.drawable.bg_greenf);



                    // The toggle is enabled means "like" in your case // call api for like
                } else if
                        (!isChecked){

//                    mDatabase.child("favos").child(item.getName()).removeValue();
//                    mDatabase.child("user-favos").child(uid).child(item.getName()).removeValue();
                    item.setChecked(false);

                    toggla.setBackgroundResource(R.drawable.bg_greenreal);

                    // The toggle is disabled means "dislike" in your case // call api for dislike
                }
            }
        });













        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    item.setChecked(true);







                    writeNewPost(uid, item.getStatus(), item.getName(), item.getCost(), item.getRating());
                    toggle.setBackgroundResource(R.drawable.heart_liked);



                    // The toggle is enabled means "like" in your case // call api for like
                } else if
                    (!isChecked){

                    mDatabase.child("favos").child(item.getName()).removeValue();
                    mDatabase.child("user-favos").child(uid).child(item.getName()).removeValue();
                    item.setChecked(false);

                    toggle.setBackgroundResource(R.drawable.heart_notliked);

                    // The toggle is disabled means "dislike" in your case // call api for dislike
                }
            }
        });





		final String costens=item.getCost();




        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(activity,Payments.class);
                i.putExtra("price", costens);
                activity.startActivity(i);
            }
        });


//        buy.setText("Buy tickets at "+item.getCost()+" /=");
buy.setText("Buy");









		name.setText(item.getName());

        imge.setText(item.getImge());
        cost.setText("Ksh. "+item.getCost());
        time.setText(item.getTimes());
        longitude.setText(item.getLongitude());
        latitude.setText(item.getLatitude());
        rating.setText(item.getRating());


        rates.setRating(Float.parseFloat(item.getRating()));

        status.setText(item.getStatus());




//		name.setText(item.getName());

		// Converting timestamp into x ago format
//		CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
//				Long.parseLong(item.getTimeStamp()),
//				System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
//		timestamp.setText(timeAgo);

		// Chcek for empty status message
//		if (!TextUtils.isEmpty(item.getStatus())) {
//			statusMsg.setText(item.getStatus());
//			statusMsg.setVisibility(View.VISIBLE);
//		}
//
// else {
//			// status is empty, remove from view
//			statusMsg.setHeight(0);
//			statusMsg.setWidth(0);
//			statusMsg.setTextColor(R.color.white);
//			statusMsg.setVisibility(View.GONE);
//		}





//        // Chcek for empty location
//        if (!TextUtils.isEmpty(item.getLocations())) {
//            locations.setText(item.getLocations());
//            locations.setVisibility(View.VISIBLE);
//        }
//
//        else {
//            // location is empty, remove from view
//
//            locations.setVisibility(View.GONE);
//        }




        // Chcek for empty time
//        if (!TextUtils.isEmpty(item.getTimes())) {
//            times.setText(item.getTimes());
//            times.setVisibility(View.VISIBLE);
//        }
//
//        else {
//            // time is empty, remove from view
//            times.setVisibility(View.GONE);
//        }

        // Chcek for empty status message
//        if (!TextUtils.isEmpty(item.getCost())) {
//            cost.setText(item.getCost());
//            cost.setVisibility(View.VISIBLE);
//        }
//
//        else {
//            // cost is empty, remove from view
//
//            cost.setVisibility(View.GONE);
//        }


//		if (!TextUtils.isEmpty(item.getName())) {
//			name.setText(item.getName());
//			name.setVisibility(View.VISIBLE);
//		}
//
//		else {
//			// cost is empty, remove from view
//
//			name.setVisibility(View.GONE);
//		}



		// Checking for null feed url
//		if (item.getUrl() != null) {
//			url.setText(Html.fromHtml("<a href=\"" + item.getUrl() + "\">"
//					+ item.getUrl() + "</a> "));
//
//			// Making url clickable
//			url.setMovementMethod(LinkMovementMethod.getInstance());
//			url.setVisibility(View.VISIBLE);
//		} else {
//			// url is null, remove from the view
//			url.setVisibility(View.GONE);
//		}
//
//		// user profile pic
//		profilePic.setImageUrl(item.getProfilePic(), imageLoader);

		// Feed image
		if (item.getImge() != null) {
			feedImageView.setImageUrl(item.getImge(), imageLoader);
			feedImageView.setVisibility(View.VISIBLE);
			feedImageView
					.setResponseObserver(new WanImageView.ResponseObserver() {
						@Override
						public void onError() {
						}

						@Override
						public void onSuccess() {
						}
					});
		} else {
			feedImageView.setVisibility(View.GONE);
		}

		return convertView;
	}


    private void writeNewPost(String userId, String username, String title, String body, String rating) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        mDatabase=FirebaseDatabase.getInstance().getReference();
        String key = mDatabase.child("posts").push().getKey();
        Post post = new Post(userId, username, title, body,  rating);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/favos/" + title, postValues);
        childUpdates.put("/user-favos/" + userId + "/" + title, postValues);

        mDatabase.updateChildren(childUpdates);
    }

    private void delete()
    {

    }



}
