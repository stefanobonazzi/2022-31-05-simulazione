package it.polito.tdp.nyc.model;

public class MediaHotspot {

	private String city;
	private double lat;
	private double lon;
	
	public MediaHotspot(String city, double lat, double lon) {
		this.city = city;
		this.lat = lat;
		this.lon = lon;
	}

	public String getCity() {
		return city;
	}

	public double getLat() {
		return lat;
	}

	public double getLon() {
		return lon;
	}
	
}
