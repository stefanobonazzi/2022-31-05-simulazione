package it.polito.tdp.nyc.model;

public class Event implements Comparable<Event> {
	
	private Integer tecnico;
	private Hotspot hotspot;
	private Double time;

	public Event(int tecnico, Hotspot hotspot, Double time) {
		this.tecnico = tecnico;
		this.hotspot = hotspot;
		this.time = time;
	}

	public Integer getTecnico() {
		return tecnico;
	}

	public void setTecnico(Integer tecnico) {
		this.tecnico = tecnico;
	}

	public Hotspot getHotspot() {
		return hotspot;
	}

	public void setHotspot(Hotspot hotspot) {
		this.hotspot = hotspot;
	}

	public Double getTime() {
		return time;
	}

	public void setTime(Double time) {
		this.time = time;
	}

	@Override
	public int compareTo(Event o) {
		return this.time.compareTo(o.getTime());
	}

}
