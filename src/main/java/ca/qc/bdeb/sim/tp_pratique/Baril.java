package ca.qc.bdeb.sim.tp_pratique;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

public class Baril extends ObjetDuJeu {

    Random rand = new Random();
    double largeurNiveau = HelloApplication.w * 8; //jsp si on peut faire cela (variable de classe)

    public Baril() {
        image = new Image("baril.png");
        taille = new Point2D(70, 83);
        position = new Point2D(rand.nextDouble(0.20 * largeurNiveau, 0.8 *largeurNiveau), 100);
        velocite = new Point2D(0,0); // banale en premiere vue, mais sinon ca fait une exception
        acceleration = new Point2D(0,0);   // banale en premiere vue, mais sinon ca fait une exception
    }

    @Override
    public void update(double deltaTemps) {
        super.update(deltaTemps);

        // peut stocker gros calcul dans variable positionY...
        position = new Point2D(position.getX(), (HelloApplication.h - taille.getY())/2* Math.sin((2*Math.PI*Partie.getTempsDepuisDebutNiveau())/3) + (HelloApplication.h - taille.getY())/2);

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
}
