
package JTEComponents;
public class Edge {

    public final JTEComponents.Vertex target;
    public final double weight;

    public Edge(JTEComponents.Vertex argTarget, double argWeight) {
        target = argTarget;
        weight = argWeight;
    }
}
