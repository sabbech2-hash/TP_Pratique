package ca.qc.bdeb.sim.tp_pratique;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Algues {
    private Point2D position;
    private Image image;
    private Point2D taille;

    public Algues(int position, Image image) {
        this.position = new Point2D(position, HelloApplication.h - 109);
        this.image = image;
        taille = new Point2D(80,119);
    }

    public void draw(GraphicsContext context, Camera camera) {


        double positionEcran = camera.coordoEcran(position);

        // image de Gontrand
        context.drawImage(
                image,
                positionEcran,
                position.getY(),
                taille.getX(),
                taille.getY()
        );
    }

    public Point2D getPosition() {
        return position;
    }

    public Image getImage() {
        return image;
    }
}
