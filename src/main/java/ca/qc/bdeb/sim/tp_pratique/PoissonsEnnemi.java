package ca.qc.bdeb.sim.tp_pratique;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

public class PoissonsEnnemi extends ObjetDuJeu {


    private Image image;
    Random random = new Random();
    private Image [] imagesPoissons = {

            new Image("poisson1.png"),
            new Image("poisson2.png"),
            new Image("poisson3.png"),
            new Image("poisson4.png"),
            new Image("poisson5.png"),

    };

    public PoissonsEnnemi(int niveau) {



        double positionYInitiale = random.nextDouble(0.2 * HelloApplication.h, 0.8 * HelloApplication.h);

        position = new Point2D(HelloApplication.w, positionYInitiale);// techniquement pas besoin de this.

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

    @Override
    public void updatePhysique(double deltaTemps) {
        super.updatePhysique(deltaTemps);
    }

    @Override
    public void draw(GraphicsContext context) {
        int nombreDePoisson = random.nextInt(1,5);

        context.drawImage(image, position.getX(), position.getY(), taille.getX(), taille.getY());

    }
}
