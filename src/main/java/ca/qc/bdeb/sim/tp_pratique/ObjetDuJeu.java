package ca.qc.bdeb.sim.tp_pratique;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class ObjetDuJeu {
    protected Point2D position;
    protected Point2D velocite;
    protected Point2D acceleration;
    protected Point2D taille;

    public void update(double deltaTemps) {

        updatePhysique(deltaTemps);   // va faire le update dependamment de l'objet

    }

    protected void updatePhysique (double deltaTemps) {

        velocite = velocite.add(acceleration.multiply(deltaTemps));
        position = position.add(velocite.multiply(deltaTemps));       // un update qui s'applique a tous (lois physiques)

    }

    public abstract void draw(GraphicsContext context, Camera camera);

    public boolean verifierCollision(ObjetDuJeu autre) {
       //
        return true;
    }

    public Point2D getTaille() {
        return taille;
    }

    public Point2D getPosition() {
        return position;
    }

    public Point2D getVelocite() {
        return velocite;
    }

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