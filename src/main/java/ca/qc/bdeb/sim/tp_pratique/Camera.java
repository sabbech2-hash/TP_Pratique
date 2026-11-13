package ca.qc.bdeb.sim.tp_pratique;

import javafx.geometry.Point2D;

public class Camera {
    private Point2D positionCamera;
    private double limiteMax;
    private double limiteMin;
    private double largeurNiveau;

    public Camera(){
        positionCamera = new Point2D(0,0);
        limiteMax = (double) HelloApplication.w / 5;
        limiteMin = 0.0;
        largeurNiveau = HelloApplication.w * 8;
    }

    public Point2D coordoEcran(Point2D positionMonde) {
        return positionMonde.subtract(positionCamera);
    }

    public void update (double deltaTemps) {

        if (Partie.gontrand.getPosition().getX() - positionCamera.getX() > limiteMax) {
            positionCamera = new Point2D(Partie.gontrand.getPosition().getX() - limiteMax, positionCamera.getY());
        }

        // La caméra ne peut jamais reculer
        // et ne peut jamais dépasser la fin du niveau (8*w)
        positionCamera = new Point2D(Math.max(limiteMin, Math.min(positionCamera.getX(), largeurNiveau - HelloApplication.w)), positionCamera.getY());

    }

    public double getLargeurNiveau() {
        return largeurNiveau;
    }

    public Point2D getPositionCamera() {
        return positionCamera;
    }
}