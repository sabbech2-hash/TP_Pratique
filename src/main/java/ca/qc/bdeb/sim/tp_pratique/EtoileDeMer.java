package ca.qc.bdeb.sim.tp_pratique;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class EtoileDeMer extends Projectiles {

    public EtoileDeMer(Point2D centreGonrtand) {
        position = new Point2D(centreGonrtand.getX(), centreGonrtand.getY());   // PAS TOTALEMENT centrer
        image = new Image("etoile.png");
        velocite = new Point2D(800,0);
        taille =  new Point2D(36,35);
        acceleration = new Point2D(0,0);

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


    @Override
    public void update(double deltaTemps) {
        super.update(deltaTemps);
    }
}
