import java.util.*;

public class Tarjan {

    private HashMap<Node, Integer> visitedTime;
    private HashMap<Node, Integer> lowTime;
    private HashSet<Node> onStack;
    private LinkedList<Node> stack;
    private HashSet<Node> visited;
    private ArrayList<HashSet<Node>> result;
    private int time;

    public ArrayList<HashSet<Node>> cfc(Graph graph) {
        time = 0;
        visitedTime = new HashMap<>();
        lowTime = new HashMap<>(); //alamacena el tiempo del primer nodo del dfs que se esta ejecutando,
                                   // es decir el minimo tiempo de descubrimiento del subarbol dfs producido
        stack = new LinkedList<>();
        onStack = new HashSet<>(); //indica si el nodo se encuentra en el stack
        visited = new HashSet<>();
        result = new ArrayList<>();


        for (Node nodo : graph.getNodes()) {
            if(visited.contains(nodo)) {
                continue;
            }
            makeCFC(nodo);
        }

        return result;
    }

    private void makeCFC(Node nodo) {

        visited.add(nodo);
        visitedTime.put(nodo, time);
        lowTime.put(nodo, time);
        time++;
        stack.addFirst(nodo);
        onStack.add(nodo);

        for (Node ady : nodo.getAdyacentes()) {
            //Si el adyacente no fue visitado entonces lo visito y reviso si tiene un camino de vuelta hacia el nodo padre entonces seteo su low time igual
            //a del tiempo de visita del padre
            if (!visited.contains(ady)) {
                makeCFC(ady);
                //seteo el low time de nodo como el minimo entre el mismo y el low time de ady;
                lowTime.compute(nodo, (n, low) ->
                        Math.min(low, lowTime.get(ady))
                );
            } 
            //reviso si ady esta en el stack y si fue visitado antes que el low time de nod
            //es decir, reviso si ady es la raiz del arbol dfs
            else if (onStack.contains(ady)) {
                //seteo el low time de nodo como el minimo entre el mismo y el visitedTime de ady;
                lowTime.compute(nodo, (n, low) -> Math.min(low, visitedTime.get(ady))
                );
            }
        }

        
        //si el low time de nodo es el mismo que el visited time entonces es el nodo de inicio(raiz) de la componente fuertemente conectada
        if (visitedTime.get(nodo) == lowTime.get(nodo)) {
            HashSet<Node> ComponenteFuertementeConectada = new HashSet<>();
            Node n;
            do {
                n = stack.pollFirst();
                onStack.remove(n);
                ComponenteFuertementeConectada.add(n);
            } while (!nodo.equals(n));
            //agrego nodos a la CFC mientras que sean distintos de nodo
            result.add(ComponenteFuertementeConectada);
        }
    }

}