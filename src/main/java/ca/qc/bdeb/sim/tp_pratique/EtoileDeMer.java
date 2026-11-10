package ca.qc.bdeb.sim.tp_pratique;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class EtoileDeMer extends Projectiles {

    public EtoileDeMer() {
        image = new Image("etoile.png");
        velocite = new Point2D(600,0);
        taille =  new Point2D(36,35);

    }

    @Override
    public void enCollisionAvecPoisson() {
        super.enCollisionAvecPoisson();
    }
}
