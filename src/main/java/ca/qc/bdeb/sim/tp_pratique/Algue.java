package ca.qc.bdeb.sim.tp_pratique;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Algue extends ObjetDuJeu {

    public Algue(int position, Image image) {
        this.position = new Point2D(position, MainJavaFX.h - 109);
        this.image = image;
        taille = new Point2D(80, 119);
    }

}