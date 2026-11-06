package ca.qc.bdeb.sim.tp_pratique;

import javafx.geometry.Point2D;
import javafx.scene.Camera;
import javafx.scene.canvas.GraphicsContext;

public abstract class ObjetDuJeu {
    protected Point2D position;
    protected Point2D velocite;
    protected Point2D acceleration;
    protected Point2D taille;

    public void update(double deltaTemps) {
    }

    protected void updatePhysique (double deltaTemps) {
    }

    public abstract void draw(GraphicsContext contexte, Camera camera);

    public double getHaut() {
        return 0.0;
    }

    public double getBas() {
        return 0.0;
    }

    public double getGauche() {
        return 0.0;
    }

    public double getDroite() {
        return 0.0;
    }

    public Point2D getCentre() {
        return new Point2D(0,0);
    }
}