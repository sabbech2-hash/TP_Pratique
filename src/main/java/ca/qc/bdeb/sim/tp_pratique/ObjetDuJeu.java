package ca.qc.bdeb.sim.tp_pratique;

import javafx.geometry.Point2D;

public class ObjetDuJeu {

    protected Point2D position;
    protected Point2D velocite;
    protected Point2D taille = new Point2D(0,0);
//j'ai modifié la taille




    public void updatePosition(double deltaTemps) {
        position = position.add(velocite.multiply(deltaTemps));
    }


}
