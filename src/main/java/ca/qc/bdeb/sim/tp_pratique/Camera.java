package ca.qc.bdeb.sim.tp_pratique;

import javafx.geometry.Point2D;

public class Camera {
    private double positionCamera;
    private Point2D velocite;
    private double limiteMax;
    private double limiteMin;
    private double largeurNiveau;

    public Camera(){
        positionCamera = 0.0;
        velocite =  new Point2D(0,0);
        limiteMax = (double) HelloApplication.w / 5;
        limiteMin = 0.0;
        largeurNiveau = HelloApplication.w * 8;
    }

    public double coordoEcran(Point2D positionMonde) {
        return positionMonde.getX() - positionCamera;
    }

    public void update (double deltaTemps) {

        if (Partie.gontrand.getPosition().getX() - positionCamera > limiteMax) {
            positionCamera = Partie.gontrand.getPosition().getX() - limiteMax;
        }

        // La caméra ne peut jamais reculer
        // et ne peut jamais dépasser la fin du niveau (8*w)
        positionCamera = Math.max(limiteMin, Math.min(positionCamera, largeurNiveau - HelloApplication.w));

    }
}