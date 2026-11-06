package ca.qc.bdeb.sim.tp_pratique;

import javafx.geometry.Point2D;
import javafx.scene.Camera;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Gontrand extends ObjetDuJeu {

    private Image image;

    public Gontrand(Point2D position, Point2D velocite, Point2D acceleration, Point2D taille) {
        this.position = position;
        this.velocite = velocite;
        this.acceleration = acceleration;
        this.taille = taille;
        this.image = new Image("gontrand.png");
    }

    @Override
    public void update(double deltaTemps) {
        updatePhysique(deltaTemps);

    }

    protected void updatePhysique(double deltaTemps) {
        velocite = velocite.add(acceleration.multiply(deltaTemps));
        position = position.add(velocite.multiply(deltaTemps));
    }

    @Override
    public void draw(GraphicsContext contexte, Camera camera) {

    }

    public Image getImage() {
        return image;
    }

}
   /* public void update(double deltaTemps) {


        // Si un flocon sort de l'écran
        if (horsDeLEcran()) {
            collisionAvecSol();
        }
    }

    protected void collisionAvecSol() {
        recommencer();
    }

    private boolean horsDeLEcran() {
        return position.getY() + taille.getY() > MainJavaFX.HEIGHT;
    }

    public void recommencer() {
        // Déplace les flocons vers le haut
        position = new Point2D(Math.random() * MainJavaFX.WIDTH, -Math.random() * 30);
        velocite = VELOCITE_INITIALE.add(new Point2D(Math.random() * 50 - 25, 0));
    }

    public void draw(GraphicsContext context) {
        context.setFill(color);
        context.fillRect(
                position.getX(),
                position.getY(),
                taille.getX(),
                taille.getY());
    }
}*/