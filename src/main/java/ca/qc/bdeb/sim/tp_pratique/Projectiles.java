package ca.qc.bdeb.sim.tp_pratique;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Projectiles extends ObjetDuJeu {

    protected Image image;    // si on donne attribut image a ObjetDuJeu, on sera pas obliger de faire ca


    public void enCollisionAvecPoisson () {   // methode commune pour toutes les projectiles

    }


    @Override
    public void draw(GraphicsContext context, Camera camera) {

    }
}
