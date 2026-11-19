package ca.qc.bdeb.sim.tp_pratique;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

public class Baril extends ObjetDuJeu {

    Random rand = new Random();
    double largeurNiveau = HelloApplication.w * 8; //jsp si on peut faire cela (variable de classe)

    public Baril() {
        image = new Image("baril.png");
        taille = new Point2D(70, 83);
        position = new Point2D(rand.nextDouble(0,largeurNiveau), 100);
    }

    @Override
    public void update(double deltaTemps) {
        super.update(deltaTemps);
    }

    @Override
    protected void updatePhysique(double deltaTemps) {
        super.updatePhysique(deltaTemps);
    }

    @Override
    public void draw(GraphicsContext context, Camera camera) {
        super.draw(context, camera);
    }

    @Override
    public boolean verifierCollision(ObjetDuJeu autre) {
        return super.verifierCollision(autre);
    }
}
