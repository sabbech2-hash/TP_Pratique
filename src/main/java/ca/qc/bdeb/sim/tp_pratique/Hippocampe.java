package ca.qc.bdeb.sim.tp_pratique;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

public class Hippocampe extends Projectile {

    Random rand = new Random();
    boolean estPositif = rand.nextBoolean();  // jsp si on peux mettre en variable de classe

    double amplitudeSinus = rand.nextDouble(30,60);
    double tempsOscillation = rand.nextDouble(1,3);

    private Point2D centreGontrand1;

    private double tempsDepuisCreationProjectile = 0;

    public Hippocampe(Point2D centreGontrand) {

        centreGontrand1 = centreGontrand;

        image = new Image("hippocampe.png");
        taille = new Point2D(20, 36);
        velocite = new Point2D(500, 0);
        position = new Point2D(centreGontrand1.getX(), centreGontrand1.getY());


        if (!estPositif) {
            amplitudeSinus = -amplitudeSinus;
        }

        acceleration = new Point2D(0,0);  // obligatoire, encore une fois

    }

    @Override
    public void update(double deltaTemps) {

        tempsDepuisCreationProjectile += deltaTemps;

        super.update(deltaTemps);

        // peut stocker gros calcul dans variable positionY
        position = new Point2D(position.getX(),amplitudeSinus * Math.sin((2*Math.PI*tempsDepuisCreationProjectile)/ (tempsOscillation)) + centreGontrand1.getY() );
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
