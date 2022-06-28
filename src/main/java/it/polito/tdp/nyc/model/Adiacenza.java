package it.polito.tdp.nyc.model;

public class Adiacenza implements Comparable<Adiacenza> {

	private String city;
	private Double distanza;
	
	public Adiacenza(String city, Double distanza) {
		this.city = city;
		this.distanza = distanza;
	}
	
	public String getCity() {
		return this.city;
	}
	
	public Double getDistanza() {
		return this.distanza;
	}

	@Override
	public int compareTo(Adiacenza o) {
		return this.distanza.compareTo(o.getDistanza());
	}
	
}
