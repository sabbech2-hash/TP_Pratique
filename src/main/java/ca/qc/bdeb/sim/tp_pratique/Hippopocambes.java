package ca.qc.bdeb.sim.tp_pratique;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

public class Hippopocambes extends Projectiles {

    Random rand = new Random();

    public Hippopocambes (Point2D centreGontrand) {
        image = new Image("hippopocambes.png");
        taille = new Point2D(20, 36);
        velocite = new Point2D(500,0);
        position = new Point2D(centreGontrand.getX(), centreGontrand.getY());
        acceleration = new Point2D(0,0);  // obligatoire, encore une fois

    }

    @Override
    public void update(double deltaTemps) {
        super.update(deltaTemps);

        velocite = new Point2D(velocite.getX(), rand.nextDouble(-30));

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
        super.draw(context, camera);
    }
}
