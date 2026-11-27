package ca.qc.bdeb.sim.tp_pratique;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.util.Random;

public class PoissonEnnemi extends ObjetDuJeu {

    Random random = new Random();
    private Image [] imagesPoissons = {
            new Image("poisson1.png"),
            new Image("poisson2.png"),
            new Image("poisson3.png"),
            new Image("poisson4.png"),
            new Image("poisson5.png"),
    };



    public PoissonEnnemi(int niveau, Camera camera) {

        double positionYInitiale = random.nextDouble(0.2 * MainJavaFX.h, 0.8 * MainJavaFX.h);

        position = new Point2D(MainJavaFX.w + camera.getPositionCamera().getX(), positionYInitiale);// techniquement pas besoin de this.

        double velociteXInitiale = -(100 * Math.pow(niveau, 0.33) + 200);
        double velociteYInitiale = random.nextDouble(-100,100);
        velocite = new Point2D(velociteXInitiale, velociteYInitiale);
        acceleration = new Point2D(-500,0);

        double hauteur = random.nextDouble(50,120);
        double largeur =  hauteur * 120.0/104.0;  // .0 pour que ca fait pas une division entiere
        this.taille = new Point2D(largeur,hauteur);

        this.image = imagesPoissons[random.nextInt(imagesPoissons.length)];

    }

    @Override
    public void update(double deltaTemps) {
        super.update(deltaTemps);
    }
}
