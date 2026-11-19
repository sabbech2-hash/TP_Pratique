package ca.qc.bdeb.sim.tp_pratique;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ObjetDuJeu {
    protected Point2D position;
    protected Point2D velocite;
    protected Point2D acceleration;
    protected Point2D taille;
    protected Image image;

    public void update(double deltaTemps) {

        updatePhysique(deltaTemps);   // va faire le update dependamment de l'objet

    }

    protected void updatePhysique (double deltaTemps) {

        velocite = velocite.add(acceleration.multiply(deltaTemps));
        position = position.add(velocite.multiply(deltaTemps));       // un update qui s'applique a tous (lois physiques)

    }

    public void draw(GraphicsContext context, Camera camera){
        Point2D positionEcran = camera.coordoEcran(position);


        // image de Gontrand
        context.drawImage(
                image,
                positionEcran.getX(),
                positionEcran.getY(),
                taille.getX(),
                taille.getY()
        );
    }

    public boolean verifierCollision(ObjetDuJeu autre) {
        boolean estEnCollison;
       if (getBas() < autre.getHaut() || getHaut() > autre.getBas() || getDroite() < autre.getGauche() || getGauche() > autre.getDroite()) {
           estEnCollison = false;
       } else {
           estEnCollison = true;
       }
       return estEnCollison;
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
        return position.getY();
    }

    public double getBas() {
        return position.getY() + taille.getY();
    }

    public double getGauche() {
        return position.getX();
    }

    public double getDroite() {
        return position.getX() + taille.getX();
    }

    public Point2D getCentre() {
        return new Point2D(0,0);
    }
}