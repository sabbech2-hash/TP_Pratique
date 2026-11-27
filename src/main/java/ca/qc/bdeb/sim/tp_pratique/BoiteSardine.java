package ca.qc.bdeb.sim.tp_pratique;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BoiteSardine extends Projectile {

    public BoiteSardine(Point2D centreGonrtand) {
        position = new Point2D(centreGonrtand.getX(), centreGonrtand.getY());   // PAS TOTALEMENT centrer
        image = new Image("sardines.png");
        velocite = new Point2D(0,0);
        taille =  new Point2D(35,29);
        acceleration = new Point2D(0,0);

    }


    @Override
    protected void updatePhysique(double deltaTemps) {

        velocite = velocite.add(acceleration.multiply(deltaTemps));
        if (velocite.magnitude() > 400)
            velocite = velocite.normalize().multiply((400));

        position = position.add(velocite.multiply(deltaTemps));
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

