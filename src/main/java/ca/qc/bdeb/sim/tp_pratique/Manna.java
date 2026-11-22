package ca.qc.bdeb.sim.tp_pratique;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Manna extends ObjetDuJeu {

    double largeurDuNiveau = MainJavaFX.w * 8;
    double yInitial;


    public Manna() {
        image = new Image("manna.png");
        taille = new Point2D(50, 50);
        yInitial = 300;
        position = new Point2D(largeurDuNiveau, yInitial);  // on peut changer comme on veux
        velocite = new Point2D(-150, 0);
        acceleration = new Point2D(0, 0);
    }

    @Override
    public void update(double deltaTemps) {
        super.update(deltaTemps);
        position = new Point2D(position.getX(), 8 * Math.sin(2*Math.PI*Partie.getTempsDepuisDebutNiveau()/2.5) + yInitial );
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
    public void mannaUtilise () {
        image = null;
    }
    }

