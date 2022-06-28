package it.polito.tdp.nyc.model;

import java.util.ArrayList;
import java.util.List;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;
import it.polito.tdp.nyc.db.NYCDao;

public class Model {
	
	private NYCDao dao;
	private Graph<String, DefaultWeightedEdge> graph;
	private List<String> vertices;
	
	public Model() {
		this.dao = new NYCDao();
	}

	public String creaGrafo(String p) {
		this.graph = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		this.vertices = this.dao.getVertices(p);
		
		Graphs.addAllVertices(this.graph, this.vertices);
		
		List<MediaHotspot> locations = this.dao.getMedieHotspot(p);
		
		for(MediaHotspot m1: locations) {
			for(MediaHotspot m2: locations) {
				if(!m1.equals(m2)) {
					DefaultWeightedEdge edge = this.graph.getEdge(m1.getCity(), m2.getCity());
					if(edge == null) {
						edge = this.graph.addEdge(m1.getCity(), m2.getCity());
						LatLng c1 = new LatLng(m1.getLat(), m1.getLon());
						LatLng c2 = new LatLng(m2.getLat(), m2.getLon());
						double weight = LatLngTool.distance(c1, c2, LengthUnit.KILOMETER);
						this.graph.setEdgeWeight(edge, weight);
					}
				}
			}
		}
		
		String res = "Grafo creato!\n#Vertici: "+this.graph.vertexSet().size()+"\n#Archi: "+this.graph.edgeSet().size();
		return res;
	}
	
	public List<Adiacenza> quartieriAdiacenti(String q) {
		List<Adiacenza> adiacenze = new ArrayList<Adiacenza>();
		
		for(String q2: Graphs.neighborListOf(this.graph, q)) {
			double distanza = this.graph.getEdgeWeight(this.graph.getEdge(q, q2));
			Adiacenza a = new Adiacenza(q2, distanza);
			adiacenze.add(a);
		}
	
		return adiacenze;
	}
	
	public String simula(String p, String q, int n) {
		List<Hotspot> hotspots = this.dao.getHotspot(p);
		Simulator sim = new Simulator();
		sim.initialise(hotspots, q, n, this.graph);
		sim.run();
		
		Double temp = sim.getTotTime();

		int hours = (int)(temp/60);
		String text = hours + ":";
		temp = temp%60;
		int minutes = temp.intValue();
		if(minutes < 10)
			text += "0" + minutes;
		else 
			text += minutes + ":";
		temp -= temp.intValue();
		Double seconds = temp * 60;
		if(seconds.intValue() < 10)
			text += "0" + seconds;
		else 
			text += seconds;
		
		String res = "Revisione completata in: "+sim.getTotTime()+" minuti --> "+text
				+ "\nRevisioni per ogni tecnico:\n";
		
		for(int i: sim.getRevisionati().keySet()) 
			res += "Tecnico n°"+i+" --> Hotspots revisonati: "+sim.getRevisionati().get(i)+"\n";
		
		return res;
	}
	
	public List<String> getProviders() {
		return this.dao.getProviders();
	}

	public List<String> getVertices() {
		return this.vertices;
	}

}
