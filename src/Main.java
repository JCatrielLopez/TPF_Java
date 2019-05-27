import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.image.AreaAveragingScaleFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    private void save_to_file(ArrayList<ArrayList<Node>> cycles){

    }

    public static void main(String[] args) {

        long inicio = System.currentTimeMillis();


        UserHandler my_handler = new UserHandler();
        ODEMSAXParser parser;

        try {

            parser = new ODEMSAXParser(my_handler,
                    "odems/apache-cxf-2.0.6.odem");
            parser.parse();

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }


        HashMap<String, ArrayList<String>> pkgs = my_handler.getPKGS();

        Graph graph = new Graph();


        for (String pkg : pkgs.keySet()) {
            Node node = new Node(pkg);
            graph.addElement(node);
            for (String pkg_ady : pkgs.get(pkg)) {
                if ((pkg_ady != null)) {
                    Node ady;
                    if (graph.contains(pkg_ady)) {
                        ady = graph.get(pkg_ady);
                    } else {
                        ady = new Node(pkg_ady);
                        graph.addElement(ady);
                    }

                    if (!ady.equals(node)) {
                        graph.addEdge(node, ady);
                    }
                }
            }
        }

        graph.print();
        System.out.println("Size: " + graph.getSize());
        System.out.println(" -------------------------- \n");


        long fin = System.currentTimeMillis();
        System.out.println("Demora de generacion de grafo (milis): " + (fin - inicio));
    }
}
