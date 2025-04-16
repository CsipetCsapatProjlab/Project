package model.grid;

import interfaces.IDiscoverLogic;

import java.util.*;

public class GridUtils {
    /**
     * Ez egy static class, nem tudjuk instanciálni
     */
    private GridUtils(){}

    public static class GridPathFinder {

        /**
         * Megkeresi a legrövidebb utat két rács csomópont között Dijkstra algoritmusával.
         *
         * @param kezdo A kezdő rács csomópont.
         * @param cel A cél rács csomópont.
         * @param maxCumulativeWeight Az út maximálisan megengedett összsúlya.
         * @param dLogic Az IDiscoverLogic példány, amely meghatározza a rács csomópontok közötti mozgás súlyát.
         * @return A Grid csomópontok listája, amely a kezdő csomóponttól a cél csomópontig tartó legrövidebb utat képviseli.
         *         Ha nem található út a maximális összsúlyon belül, üres listát ad vissza.
         *
         * Ez a metódus Dijkstra algoritmusát használja a kezdő és a cél csomópont közötti legrövidebb út megtalálására.
         * Figyelembe veszi a csomópontok közötti mozgás súlyát, amelyet az IDiscoverLogic implementáció határoz meg.
         * Az elérhetetlen csomópontokat (amelyek mozgási költsége Double.POSITIVE_INFINITY) kihagyja.
         * Az út csak akkor épül fel, ha az összsúly nem haladja meg a megadott maximumot.
         */
        public static List<Grid> gridPathFind(Grid kezdo, Grid cel, double maxCumulativeWeight, IDiscoverLogic dLogic) {
            PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingDouble(node -> node.weight));
            Map<Grid, Double> distances = new HashMap<>();
            Map<Grid, Grid> previous = new HashMap<>();

            distances.put(kezdo, 0.0);
            queue.add(new Node(kezdo, 0.0,0));

            while (!queue.isEmpty()) {
                Node current = queue.poll();

                if (current.grid.equals(cel)) {
                    return reconstructPath(previous, cel);
                }

                for (Grid neighbor : current.grid.getNeighbors()) {
                    if (neighbor == null) continue;

                    double moveCost = dLogic.canMove(current.grid, neighbor);
                    if (moveCost == Double.POSITIVE_INFINITY) continue; // Skip if movement is not possible

                    double newWeight = current.weight + moveCost;

                    if (newWeight <= maxCumulativeWeight && (!distances.containsKey(neighbor) || newWeight < distances.get(neighbor))) {
                        distances.put(neighbor, newWeight);
                        previous.put(neighbor, current.grid);
                        queue.add(new Node(neighbor, newWeight,0));
                    }
                }
            }

            return new ArrayList<>(); // No path found
        }

        /**
         * Szélességi keresést (BFS) hajt végre, hogy megtalálja az összes elérhető rács csomópontot egy kezdő csomópontból egy megadott mélységig.
         *
         * @param kezdo A kezdő rács csomópont.
         * @param depth A maximális mélység, amelyet a kezdő csomóponttól felfedezünk.
         * @param dLogic Az IDiscoverLogic példány, amely meghatározza, hogy lehetséges-e a mozgás a rács csomópontok között.
         * @return A Grid csomópontok listája, amelyek elérhetők a kezdő csomópontból a megadott mélységen belül.
         *
         * Ez a metódus BFS-t használ, hogy felfedezze az összes csomópontot, amely elérhető a kezdő csomópontból a megadott mélységig.
         * Az IDiscoverLogic implementációt használja annak meghatározására, hogy lehetséges-e a mozgás egy szomszédos csomóponthoz.
         * Az elérhetetlen csomópontokat (amelyek mozgási költsége Double.POSITIVE_INFINITY) kihagyja.
         * A metódus biztosítja, hogy minden csomópontot csak egyszer látogasson meg, hogy elkerülje a végtelen ciklusokat.
         */
        public static List<Grid> gridFindAll(Grid kezdo, int depth, IDiscoverLogic dLogic) {
            List<Grid> result = new ArrayList<>();
            Queue<Node> queue = new LinkedList<>();
            Set<Grid> visited = new HashSet<>();

            queue.add(new Node(kezdo,0.0, 0));
            visited.add(kezdo);

            while (!queue.isEmpty()) {
                Node current = queue.poll();
                result.add(current.grid);

                if (current.depth >= depth) {
                    continue;
                }

                for (Grid neighbor : current.grid.getNeighbors()) {
                    if (neighbor == null || visited.contains(neighbor)) continue;

                    double moveCost = dLogic.canMove(current.grid, neighbor);
                    if (moveCost == Double.POSITIVE_INFINITY) continue; // Skip if movement is not possible

                    visited.add(neighbor);
                    queue.add(new Node(neighbor, 0.0,current.depth + 1));
                }
            }

            return result;
        }


        private static List<Grid> reconstructPath(Map<Grid, Grid> previous, Grid cel) {
            List<Grid> path = new LinkedList<>();
            for (Grid at = cel; at != null; at = previous.get(at)) {
                path.add(0, at);
            }
            return path;
        }

        private static class Node {
            Grid grid;
            double weight;
            int depth;

            Node(Grid grid, double weight, int depth) {
                this.grid = grid;
                this.weight = weight;
                this.depth = depth;
            }
        }
    }
}
