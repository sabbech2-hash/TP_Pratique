package ca.qc.bdeb.sim.tp_pratique;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Algues {
    private Point2D position;
    private Image image;

    public Algues(int position, Image image) {
        this.position = new Point2D(position, HelloApplication.h - 109);
        this.image = image;
    }


    public Point2D getPosition() {
        return position;
    }

    public Image getImage() {
        return image;
    }
}
