package ca.qc.bdeb.sim.tp_pratique;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

public class PoissonOr extends ObjetDuJeu {
    Random rand = new Random();

    PoissonOr() {
        image = new Image("poissonOr.png");
        position = new Point2D(rand.nextDouble(0, MainJavaFX.w * 8),rand.nextDouble(0, MainJavaFX.h));
        taille = new Point2D(100,100);
        acceleration = new Point2D(0,0);
        velocite = new Point2D(0,0);
    }

    @Override
    public void update(double deltaTemps) {
        super.update(deltaTemps);

       
    }

    @Override
    public boolean verifierCollision(ObjetDuJeu autre) {
        return super.verifierCollision(autre);
    }

    @Override
    public void draw(GraphicsContext context, Camera camera) {
        super.draw(context, camera);
    }

    @Override
    protected void updatePhysique(double deltaTemps) {
        super.updatePhysique(deltaTemps);
    }
}
