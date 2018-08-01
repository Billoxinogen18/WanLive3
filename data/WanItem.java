package com.ogalo.partympakache.wanlive.data;

public class WanItem {
	private int id;
    private Boolean checked;
	private String name, status, image, profilePic, timeStamp, url,locations, times, cost, longitude, latitude, rating;

	public WanItem() {
	}

	public WanItem(int id, String name, String image, String status,
				   String profilePic, String timeStamp, String locations, String times, String cost, String url, String longitude, String latitude,
				   String rating, Boolean checked)
	{
		super();
		this.checked=checked;

        this.longitude =longitude ;
        this.latitude = latitude;
        this.rating = rating;

		this.id = id;
		this.name = name;
		this.image = image;
		this.status = status;
		this.profilePic = profilePic;
		this.timeStamp = timeStamp;
		this.url = url;
		this.locations = locations;
		this.times = times;
		this.cost = cost;
	}

	public Boolean getChecked() {return checked;}
    public void setChecked(Boolean checked) {
        this.checked =checked;
    }



    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }









	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImge() {
		return image;
	}

	public void setImge(String image) {
		this.image = image;
	}

	public String getStatus() {
		return status;
	}





	public String getLocations() {
		return locations;
	}

	public void setLocations(String locations) {
		this.locations = locations;
	}



	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}



	public void setStatus(String status) {
		this.status = status;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
