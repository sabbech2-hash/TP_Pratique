package ca.qc.bdeb.sim.tp_pratique;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Manna extends ObjetDuJeu {


    public Manna() {
        image = new Image("manna.png");
        taille = new Point2D(30, 30);
        // position
        velocite = new Point2D(-150, 0);
        acceleration = new Point2D(0, 0);
    }

    @Override
    public void update(double deltaTemps) {
        super.update(deltaTemps);
    }

    @Override
    public void draw(GraphicsContext context, Camera camera) {
        super.draw(context, camera);
    }

    @Override
    protected void updatePhysique(double deltaTemps) {
        super.updatePhysique(deltaTemps);
    }

    @Override
    public boolean verifierCollision(ObjetDuJeu autre) {
        return super.verifierCollision(autre);
    }
    }

