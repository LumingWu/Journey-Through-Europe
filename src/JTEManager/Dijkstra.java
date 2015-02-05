package JTEManager;

import JTEComponents.Edge;
import JTEComponents.Vertex;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import properties_manager.PropertiesManager;


public class Dijkstra {

    private ArrayList<Vertex> _vertices;
    private int _counter;
    public Dijkstra() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ArrayList<String> red = props.getPropertyOptionsList("RED_CARD_NAME_OPTIONS");
        ArrayList<String> yellow = props.getPropertyOptionsList("YELLOW_CARD_NAME_OPTIONS");
        ArrayList<String> green = props.getPropertyOptionsList("GREEN_CARD_NAME_OPTIONS");
        ArrayList<String> names = new ArrayList<String>();
        for (int i = 0; i < red.size(); i++) {
            names.add(red.get(i));
        }
        for (int i = 0; i < green.size(); i++) {
            names.add(green.get(i));
        }
        for (int i = 0; i < yellow.size(); i++) {
            names.add(yellow.get(i));
        }
        _vertices = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            _vertices.add(new Vertex(names.get(i)));
        }
        for (int i = 0; i < _vertices.size(); i++) {
            ArrayList<Edge> edges = new ArrayList<Edge>();
            ArrayList<String> itslandnames = props.getPropertyOptionsList(_vertices.get(i).toString() + "_LAND");
            for (int j = 0; j < itslandnames.size(); j++) {
                for (int k = 0; k < _vertices.size(); k++) {
                    if (itslandnames.get(j).equals(_vertices.get(k).toString())) {
                        edges.add(new Edge(_vertices.get(k), 1));
                    }
                }
            }
            ArrayList<String> itsseanames = props.getPropertyOptionsList(_vertices.get(i).toString() + "_SEA");
            for (int j = 0; j < itsseanames.size(); j++) {
                for (int k = 0; k < _vertices.size(); k++) {
                    if (itsseanames.get(j).equals(_vertices.get(k).toString())) {
                        edges.add(new Edge(_vertices.get(k), 6));
                    }
                }
            }
            Edge[] Edges = new Edge[edges.size()];
            for (int l = 0; l < edges.size(); l++) {
                Edges[l] = edges.get(l);
            }
            _vertices.get(i).adjacencies = Edges;
            _counter=0;
            /*
             for (Vertex v : _vertices) {
             System.out.println("Distance from "+_vertices.get(0)+" to " + v + ": " + v.minDistance);
             List<Vertex> path = getShortestPathTo(v);
            
             System.out.println("Path: " + path);
             */
        }

    }

    public ArrayList<String> getNextCityName(String start, String end) {
        Vertex _start = new Vertex(null);
        Vertex _end = new Vertex(null);
        for (int i = 0; i < _vertices.size(); i++) {
            if (_vertices.get(i).toString().equals(start)) {
                _start = _vertices.get(i);
            }
            if (_vertices.get(i).toString().equals(end)) {
                _end = _vertices.get(i);
            }
        }
        computePaths(_start);
        List<Vertex> path = getShortestPathTo(_end);
        ArrayList<String> _path=new ArrayList<String>() {};
        for(int i=1;i<path.size();i++){
            _path.add(path.get(i).toString());
        }
        return _path;
    }

    public void computePaths(Vertex source) {
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
        vertexQueue.add(source);
        while (!vertexQueue.isEmpty()) {
            Vertex u = vertexQueue.poll();
            for (Edge e : u.adjacencies) {
                Vertex v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
                if (distanceThroughU < v.minDistance) {
                    vertexQueue.remove(v);
                    v.minDistance = distanceThroughU;
                    v.previous = u;
                    vertexQueue.add(v);
                }
            }
        }
    }

    public List<Vertex> getShortestPathTo(Vertex target) {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous) {
            path.add(vertex);
        }
        Collections.reverse(path);
        return path;
    }
}
