package pack;
import java.lang.Math;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class Cell extends JLabel {

    /**
     * Variablen über existenz von Walls in allen richtungen
     */
    private int north = 1;
    private int south = 1;
    private int east = 1;
    private int west = 1;

    public int getNorth() {
        return north;
    }

    /**
     * setter und getter
     * für die setter wird die Border immer entsprechend aktualisiert
     * @param north
     */
    public void setNorth(int north) {
        this.north = north;
        setBorder(BorderFactory.createMatteBorder(this.north,this.west,this.south,this.east,Color.BLUE));

    }

    public int getSouth() {
        return south;

    }

    public void setSouth(int south) {
        this.south = south;
        setBorder(BorderFactory.createMatteBorder(this.north,this.west,this.south,this.east,Color.BLUE));

    }

    public int getEast() {
        return east;
    }

    public void setEast(int east) {
        this.east = east;
        setBorder(BorderFactory.createMatteBorder(this.north,this.west,this.south,this.east,Color.BLUE));

    }

    public int getWest() {
        return west;
    }

    public void setWest(int west) {
        this.west = west;
        setBorder(BorderFactory.createMatteBorder(this.north,this.west,this.south,this.east,Color.BLUE));

    }

    /**
     * isOnSP = isOnShortestPath
     */
    private boolean isOnSP;

    public boolean isOnSP() {
        return isOnSP;
    }

    public void setOnSP(boolean onSP) {
        isOnSP = onSP;
    }

    private int index;

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    /**
     * wird eigentlich nicht genutzt, signum funktion um eingaben wie 5 oder -5 statt eins abzufangen.
     * @param north
     * @param west
     * @param south
     * @param east
     */
    public void updateBorder(int north, int west, int south, int east){

        int sigNorth = (int) Math.signum(north * north);
        int sigSouth = (int) Math.signum(south * south);
        int sigWest = (int) Math.signum(west * west);
        int sigEast = (int) Math.signum(east * east);

        this.north = sigNorth;
        this.west = sigWest;
        this.south = sigSouth;
        this.east = sigEast;

        setBorder(BorderFactory.createMatteBorder(this.north,this.west,this.south,this.east,Color.BLUE));
    }

    /**
     * Konstruktor
     * bei einer Cell handelt es sich um ein JLabel mit einer MatteBorder, bei diesem Border Typ lassen sich die Wände an jeder Seite der Zelle einfügen/entfernen
     * dadurch lassen sich alle entsprechenden Operationen bei den die Wände einer Cell relevant sind über die Variablen north, south, east, west regeln
     * @param index
     */
    public Cell(int index){
        super();

        this.index = index;

        setBorder(BorderFactory.createMatteBorder(north,west,south,east, Color.BLUE));

    }

}
