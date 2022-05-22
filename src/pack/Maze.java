package pack;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.ArrayList;
import java.lang.Integer;
import java.lang.Math;

public class Maze extends JLabel {


    private Integer[][] randomMaze = new Integer[81][81];

    private ArrayList<Cell> cells = new ArrayList<>();


    /**
     * Maze Konstruktor
     * zunächst wird ein panel erzeugt, dem dann in einem GridLayout die 81 Cells hinzugefügt werden
     * die Borders Der Zellen werden mit fitMatrix an das Random Maze angepasst.
     */
    public Maze(){
        super();
        generateMaze();
        setLayout(new GridLayout(9,9));
        for(int i = 0; i < 81; i++){
            cells.add(new Cell(i));
        }
        for(Cell c : cells){
            fitMatrix(c, randomMaze);
            add(c);
        }

        for(int i = 0; i < 81; i++){
            for(int l = 0; l < 81; l++){
                System.out.print(randomMaze[i][l]);
                if (l == 80){
                    System.out.println("\n");
                }
            }
        }


    }


    /**
     * mit Fit meine ich hier, dass die Border der Cell so  gesetzt wird wie es die mitgegebene matrix vorgibt
     * in der Matrix wird also zum Beispiel nachgesehen ob ev eine Kante zu der Cell, die im Grid über der mitgegebenen Cell c liegt und falls nicht wird die Border (north)  an dieser stelle entfernt.
     * @param c
     * @param matrix
     */
    public void fitMatrix(Cell c, Integer[][] matrix){
        int l = (int) Math.sqrt(matrix.length);
        int i = c.getIndex();

        if((i - l) >= 0){
            if (matrix[i][i-l] == 0){
                c.setNorth(0);
            }
        }

        if((i + l) < matrix.length){
            if (matrix[i][i+l] == 0){
                c.setSouth(0);
            }
        }

        if(((i +1 ) % l) != 0){
            if (matrix[i][i+1] == 0){
                c.setEast(0);
            }
        }

        if((i+l) % l != 0){
            if (matrix[i][i-1] == 0){
                c.setWest(0);
            }
        }
    }




    /*public Integer[][] generateRandomGridGraph(int size, int origin, int bound){

        int squaredSize = size* size;
        Integer[][] grid = new Integer[squaredSize][squaredSize];

        for (int i = 0; i < size; i++) {

            Random rand = new Random();
            grid[i][i] = 1;

            if ((i + size) < squaredSize) {
                grid[i][i + 3] = 1;

            }
            if ((i - size) >= 0) {
                grid[i][i - 3] = 1;
            }
            if ((i+1) % size != 0) {
                grid[i][i + 1] = 1;

            }
            if ((i + size) % size != 0) {
                grid[i][i - 1] = 1;
            }
        }

        for(int i = 0; i < squaredSize; i++){
            for( int l = 0; l < squaredSize; l++){
                Random rand = new Random();
                int r = rand.nextInt(origin,bound);
                if(grid[i][l] != null) {
                    if (grid[i][l] == 1) {
                        grid[i][l] = r;
                        grid[l][i] = r;
                    }
                }
            }
        }
        return grid;
    }*/


    /**
     * generateMaze erzeugt in randomMaze ein zufälliges Labyrinth in Adjazenzmatrixdarstellung
     */
    public void generateMaze() {

        /**
         * hier wird zunächst das Zweidimensionale Array randomMaze so gefüllt, dass es ein 9x9 Raster darstellt
         * Dieser Teil wird der Übersichtlichkeit halber evtl. noch zu einer eigenen Methode
         */
        for (int i = 0; i < 81; i++) {

            Random rand = new Random();
            randomMaze[i][i] = 1;

            if ((i + 9) < 81) {
                randomMaze[i][i + 9] = 1;

            }
            if ((i - 9) >= 0) {
                randomMaze[i][i - 9] = 1;
            }
            if ((i+1) % 9 != 0) {
                randomMaze[i][i + 1] = 1;

            }
            if ((i + 9) % 9 != 0) {
                randomMaze[i][i - 1] = 1;
            }
        }


        /**
         * im Raster werden allen Wänden (Knoten im Graphen) zufällige Gewichte zugewiesen
         */
        for(int i = 0; i < 81; i++){
            for( int l = 0; l < 81; l++){
                Random rand = new Random();
                int r = rand.nextInt(2,200);
                if(randomMaze[i][l] != null) {
                    if (randomMaze[i][l] == 1) {
                        randomMaze[i][l] = r;
                        randomMaze[l][i] = r;
                    }
                }
            }
        }

        /**
         * ausgabe auf konsole zum testen
         */
        for(int i = 0; i < 81; i++){
            for(int l = 0; l < 81; l++){
                System.out.print(randomMaze[i][l] + " | ");
                if (l == 80){
                    System.out.println("\n");
                }
            }
        }

        /**
         * Null values werden mit 1000 gefüllt (da 1000 größer als der upper bound der zufälligen Kantengewichte ist kann man es hier als unendlich betrachten
         */
        int infinite = 1000;

        for (int i = 0; i < 81; i++){
            for(int t = 0; t < 81; t++){
                if(randomMaze[i][t] == null){
                    randomMaze[i][t] = infinite;
                }
                /*else {
                    randomMaze[i][t] = 0;
                }*/
            }
        }

        /**
         * erstellung minimaler Spannbaum durch prims Algorithmus
         * Kanten auf dem MST werden auf infinite gesetzt und auf diese Weise "entfernt"
         */
        ArrayList<Integer> visited = new ArrayList<>();
        visited.add(0);
        while(visited.size() < 81) {
            int index = 0;
            int parent = 0;
            int weight = infinite;
            for (int i : visited) {
                for (int t = 0; t < 81; t++) {
                    if(randomMaze[i][t] < weight && !visited.contains(t)){
                        index = t;
                        parent = i;
                        weight = randomMaze[i][t];
                    }
                }
            }

            visited.add(index);
            randomMaze[parent][index] = infinite;
            randomMaze[index][parent] = infinite;


        }

        /**
         * in der Matrix wird infinite wieder auf 0 und alle verbliebenen zufälligen KAntengewichte auf 1 gesetzt
         * danach entspricht eine 1 in randomMaze einer Wand und eine 0 einem Durchgang.
         */
        for (int i = 0; i < 81; i++){
            for(int t = 0; t < 81; t++){
                if(randomMaze[i][t] == infinite){
                    randomMaze[i][t] = 0;
                }
                else {
                    randomMaze[i][t] = 1;
                }
            }
        }


    }
}




