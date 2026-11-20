package ca.qc.bdeb.sim.tp_pratique;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class Projectile extends ObjetDuJeu {


    @Override
    public void update(double deltaTemps) {
        super.update(deltaTemps);
    }

    @Override
    protected void updatePhysique(double deltaTemps) {
        super.updatePhysique(deltaTemps);
    }

    @Override
    public boolean verifierCollision(ObjetDuJeu autre) {
        return super.verifierCollision(autre);
    }

    @Override
    public void draw(GraphicsContext context, Camera camera) {

        Point2D positionEcran = camera.coordoEcran(position);

        context.drawImage(image, positionEcran.getX(), positionEcran.getY(), taille.getX(),  taille.getY());

    }
}
