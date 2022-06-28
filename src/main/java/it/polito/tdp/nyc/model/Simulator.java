package it.polito.tdp.nyc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Simulator {

	private PriorityQueue<Event> queue;
	
	private Map<Integer, Integer> revisionati;
	private Double totTime;
	
	private List<Integer> tecnici;
	private List<String> quartieriVisitati;
	private List<Hotspot> hotspots;
	private List<Hotspot> daRevisionare;
	private Graph<String, DefaultWeightedEdge> graph;
	
	public Simulator() {
		this.queue = new PriorityQueue<Event>();
		this.totTime = 0.0;
	}
	
	public void initialise(List<Hotspot> hotspots, String q, int n, Graph<String, DefaultWeightedEdge> graph) {
		this.hotspots = hotspots;
		this.quartieriVisitati = new ArrayList<String>();
		this.tecnici = new ArrayList<>();
		this.revisionati = new TreeMap<>();
		this.daRevisionare = new ArrayList<>();
		this.graph = graph;
		
		this.quartieriVisitati.add(q);
		
		for(int i=1; i<=n; i++) {
			this.tecnici.add(i);
			this.revisionati.put(i, 0);
		}
		
		for(Hotspot h: this.hotspots) {
			if(h.getCity().equals(q)) {
				this.daRevisionare.add(h);
			}
		}
		
		int k = Math.min(this.daRevisionare.size(), n);
		for(int j=1; j<=k; j++) {
			Hotspot h = this.daRevisionare.get((int) (Math.random()*this.daRevisionare.size()));
			this.daRevisionare.remove(h);
			
			Double time = 0.0;
			
			if(Math.random() <= 0.1)
				time = 25.0;
			else
				time = 10.0;
			
			this.revisionati.put(j, this.revisionati.get(j)+1);
			Event e = new Event(j, h, time);
			this.queue.add(e);
		}
		
	}

	public void run() {
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			this.processEvent(e);
		}
	}

	private void processEvent(Event e) {
		if(this.daRevisionare.isEmpty()) {
			if(this.queue.isEmpty()) {
				double minDistanza = 10000.0;
				String qu = "";
				for(String q2: Graphs.neighborListOf(this.graph, e.getHotspot().getCity())) {
					if(!this.quartieriVisitati.contains(q2)) {
						double d = this.graph.getEdgeWeight(this.graph.getEdge(e.getHotspot().getCity(), q2));
						if(d < minDistanza) {
							minDistanza = d;
							qu = q2;
						}
					}
				}

				if(qu.equals("")) {
					this.totTime = e.getTime();
					return;
				}
				
				Double time = e.getTime()+(minDistanza*50/60);
				
				for(Hotspot h: this.hotspots) {
					if(h.getCity().equals(qu)) {
						this.daRevisionare.add(h);
					}
				}
				
				int k = Math.min(this.daRevisionare.size(), this.tecnici.size());
				for(int j=1; j<=k; j++) {
					Hotspot h = this.daRevisionare.get((int) (Math.random()*this.daRevisionare.size()));
					this.daRevisionare.remove(h);
					
					if(Math.random() <= 0.1)
						time += 25.0;
					else
						time += 10.0;
					
					this.revisionati.put(j, this.revisionati.get(j)+1);
					Event ee = new Event(j, h, time);
					this.queue.add(ee);
				}
			}
		} else {
			Hotspot h = this.daRevisionare.get((int) (Math.random()*this.daRevisionare.size()));
			this.daRevisionare.remove(h);
			
			Double time = e.getTime()+(Math.random()*11)+10;
			
			if(Math.random() <= 0.1)
				time += 25.0;
			else
				time += 10.0;
			
			this.revisionati.put(e.getTecnico(), this.revisionati.get(e.getTecnico())+1);
			Event ev = new Event(e.getTecnico(), h, time);
			this.queue.add(ev);
		}
	}

	public Map<Integer, Integer> getRevisionati() {
		return revisionati;
	}

	public Double getTotTime() {
		return totTime;
	}
	
}