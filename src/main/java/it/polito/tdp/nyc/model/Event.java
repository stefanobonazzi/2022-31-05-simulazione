package it.polito.tdp.nyc.model;

public class Event implements Comparable<Event> {
	
	public enum EventType {
		INIZIO_HS, // tecnico inizia inizia a lavorare su un h.s.
		FINE_HS, // tecnico termina il lavoro su un h.s.
		NUOVO_QUARTIERE, // la squadra si sposta in un nuovo quartiere
	}
	
	private int time ;
	private EventType type ;
	private int tecnico ; // numero corrispondente al tecnico
	
	
	
	public Event(int time, EventType type, int tecnico) {
		super();
		this.time = time;
		this.type = type;
		this.tecnico = tecnico;
	}

	


	public int getTime() {
		return time;
	}




	public EventType getType() {
		return type;
	}




	public int getTecnico() {
		return tecnico;
	}




	@Override
	public int compareTo(Event o) {
		return this.time - o.time;
	}

}
