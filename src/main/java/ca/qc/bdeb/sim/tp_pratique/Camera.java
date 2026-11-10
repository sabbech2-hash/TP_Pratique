package ca.qc.bdeb.sim.tp_pratique;

import javafx.geometry.Point2D;

public class Camera {
    private Point2D positionCamera = new Point2D(0,0);
    private Point2D velocite =  new Point2D(0,0);

    public Point2D coordoEcran(Point2D positionMonde) {
        return positionMonde.subtract(positionCamera);
    }

    public void update (double deltaTemps) {
        positionCamera = positionCamera.add(Partie.gontrand.getVelocite().multiply(deltaTemps));
    }
}