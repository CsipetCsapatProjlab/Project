package model.grid;

import interfaces.GameObjectVisitor;
import interfaces.GridVisitor;
import interfaces.IDiscoverLogic;

import java.util.*;

import jdk.jshell.spi.ExecutionControl;
import model.enums.Hatas;
import model.exceptions.IncompatibleGameObjectException;
import model.gameobjects.GameObject;

public abstract class Grid {
    Grid[] neighbours;
    boolean zarolva;
    protected List<GameObject> gameObjects;
    protected Grid(){
        zarolva = false;
        gameObjects = new ArrayList<>();
    }
    protected Grid(List<GameObject> g) {
        this.gameObjects = g;
    }
    public List<GameObject> getGameObject(){return gameObjects;}

    /**
     * Megtisztitja a mezot minden rajta elhelyezheto GameObject-tol
     */
    public int clear(){
        List<GameObject> tmp = new ArrayList<>();
        int numsdeleted=0;
        tmp.addAll(gameObjects);
        for (GameObject g : tmp) {
            numsdeleted++;
            try {
                g.remove();
            } catch (IncompatibleGameObjectException e) {numsdeleted--;}
        }
        gameObjects.clear();
        return numsdeleted;
    }

    /**
     * Beallitja a mezo szomszedos mezoit
     * @param arr Grid tomb, amely a mezoket tartalmazza
     */
    public void setNeighbours(Grid[] arr){
        neighbours = arr;
    }

    /**
     * GameObject hozzaadasa a mezohoz
     * @param gameObject Mit adjon hozza
     */
    public boolean hozzaAd(GameObject gameObject){
        if(elfogadGameObject(gameObject)){
            return gameObjects.add(gameObject);
        }
        return false;
    }

    public abstract boolean elfogadGameObject(GameObject gameObject);

    /**
     * GameObject torlese a mezorol
     * @param g Mit toroljon
     */
    public boolean torol(GameObject g)  {
        return gameObjects.remove(g);
    }

    public List<Grid> gridPathFind(Grid kezdo, Grid cel, double maxCumulativeWeight, IDiscoverLogic dLogic){
        return gridPathFind(kezdo, cel, maxCumulativeWeight, dLogic);
    }

    private class GridPathFinder {

        /**
         * Dijkstra féle legrövidebb út implementáció
         * @param kezdo innen jön
         * @param cel ide érkezik
         * @param maxCumulativeWeight Ennél tovább nem mehet
         * @param dLogic Func<double, Grid, Grid>, ami meghatározza az élsúlyt
         * @return Utvonal lista, ami tartalmazza a kezdő és a végpontokat. Ha nincs ilyen, egy üres listát ad vissza.
         */
        public List<Grid> gridPathFind(Grid kezdo, Grid cel, double maxCumulativeWeight, IDiscoverLogic dLogic) {
            PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingDouble(node -> node.weight));
            Map<Grid, Double> distances = new HashMap<>();
            Map<Grid, Grid> previous = new HashMap<>();

            distances.put(kezdo, 0.0);
            queue.add(new Node(kezdo, 0.0));

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
                        queue.add(new Node(neighbor, newWeight));
                    }
                }
            }

            return new ArrayList<>(); // No path found
        }

        private List<Grid> reconstructPath(Map<Grid, Grid> previous, Grid cel) {
            List<Grid> path = new LinkedList<>();
            for (Grid at = cel; at != null; at = previous.get(at)) {
                path.add(0, at);
            }
            return path;
        }

        private static class Node {
            Grid grid;
            double weight;

            Node(Grid grid, double weight) {
                this.grid = grid;
                this.weight = weight;
            }
        }
    }

    /**
     * Megtalalja a kezdo pontbol, adott melysegig 
     * @param kezdo Kezdo mezo
     * @param depth Milyen tavolsagban (melysegben)
     * @param dLogic Felderitesi logika
     */
    public List<Grid> gridFindAll(Grid kezdo, int depth, IDiscoverLogic dLogic) {
        return null;
    }

    /**
     * A mezo szomszedait lekerdezi
     * @return Mezo tomb a szomszedokkal
     */
    public Grid[] getNeighbors() {return neighbours;}
    public abstract Hatas getHatas();

    void forduloUtan(){}

    /**
     * Fogadja a jatekobjektum visitort a mezon
     * @param visitor
     */
    public abstract void accept(GameObjectVisitor visitor);

    /**
     * Fogadja a mezo visitort a mezon
     * @param visitor
     */
    public abstract void accept(GridVisitor visitor);
}
