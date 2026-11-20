package ca.qc.bdeb.sim.tp_pratique;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Algue extends ObjetDuJeu {

    public Algue(int position, Image image) {
        this.position = new Point2D(position, HelloApplication.h - 109);
        this.image = image;
        taille = new Point2D(80,119);
    }

    /*public void draw(GraphicsContext context, Camera camera) {


        Point2D positionEcran = camera.coordoEcran(position);

        context.drawImage(
                image,
                positionEcran.getX(),
                positionEcran.getY(),
                taille.getX(),
                taille.getY()
        );
    }*/

    /*public Point2D getPosition() {
        return position;
    }*/

    /*public Image getImage() {
        return image;
    }*/
}
