package ca.qc.bdeb.sim.tp_pratique;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Gontrand extends ObjetDuJeu {

    private static int vie = 4;   // nombre de vie de Gontrand n'est pas attribut de classe
    private double tempsInvincible = 0;
    private double tempsDepuisCollision = 0;

    private Image image;    //???????????

    public Gontrand(Point2D position, Point2D velocite, Point2D acceleration, Point2D taille) {
        this.position = position;
        this.velocite = velocite;
        this.acceleration = acceleration;
        this.taille = taille;
        this.image = new Image("gontrand.png");
    }

    @Override
    public void update(double deltaTemps) {


        boolean estGauche = Input.isKeyPressed(KeyCode.LEFT);
        boolean estDroite = Input.isKeyPressed(KeyCode.RIGHT);
        boolean estHaut = Input.isKeyPressed(KeyCode.UP);
        boolean estBas = Input.isKeyPressed(KeyCode.DOWN);
        boolean estStableEnX = !(estDroite || estGauche);
        boolean estStableEnY = !(estHaut || estBas);

        acceleration = new Point2D(mouvementBidirectionnel(estGauche, estDroite),
                mouvementBidirectionnel(estHaut, estBas));

        velocite = new Point2D(velociteMax(velocite.getX()), velociteMax(velocite.getY()));

        //if (gauche) {
          //  acceleration = new Point2D(-1000, acceleration.getY());
        //}

        //if (velocite.getX() < -300) {
         //   velocite = new Point2D(-300, velocite.getY());
       // }


        //if (droite) {
          //  acceleration = new Point2D(1000, acceleration.getY());
       // }
       // if (velocite.getX() > 300) {
         //   velocite = new Point2D(300, velocite.getY());
       // }


       // if (bas) {
          //  acceleration = new Point2D(acceleration.getX(), 1000);
        //}
       // if (velocite.getY() > 300) {
         //   velocite = new Point2D(velocite.getX(), 300);
        //}


        //if (haut) {
           // acceleration = new Point2D(acceleration.getX(), -1000);
       // }
        //if (velocite.getY() < -300) {
          //  velocite = new Point2D(velocite.getX(), -300);
        //}


        if (estStableEnX && velocite.getX() > 0) {
            acceleration = new Point2D(-400, acceleration.getY());
        } else if  (estStableEnX && velocite.getX() < 0) {
            acceleration = new Point2D(400, acceleration.getY());
        }


        if (estStableEnY && velocite.getY() > 0) {
            acceleration = new Point2D(acceleration.getX(), -400);
        }  else if (estStableEnY && velocite.getY() < 0) {
            acceleration = new Point2D(acceleration.getX(), 400);
        }

        if (tempsInvincible > 0) {
            tempsInvincible -= deltaTemps;
            tempsDepuisCollision += deltaTemps;


            if (tempsInvincible < 0) {
               image = new Image("gontrand.png");
            }
        }


        updatePhysique(deltaTemps);

    }

    protected double mouvementBidirectionnel(boolean inverse, boolean normal) {
        if (normal) {
            return 1000;
        } else if (inverse) {
            return -1000;
        } else {
            return 0;
        }

    }

    protected double velociteMax(double velocite) {
        if (velocite >= 0) {
            return Math.min(velocite, 300);
        } else {
            return Math.max(velocite, -300);
        }
    }

    @Override
    protected void updatePhysique(double deltaTemps) {
        super.updatePhysique(deltaTemps);

    }

    @Override
    public void draw(GraphicsContext context, Camera camera) {

        Point2D positionEcran = camera.coordoEcran(position);

     if (tempsInvincible > 0) {

        if ((tempsDepuisCollision % 0.5) < 0.25) {
            context.drawImage(
                    image,
                    positionEcran.getX(),
                    positionEcran.getY(),
                    taille.getX(),
                    taille.getY());
        }

        } else {

            //  image de Gontrand
            context.drawImage(
                    new Image("gontrand.png"),
                    positionEcran.getX(),
                    positionEcran.getY(),
                    taille.getX(),
                    taille.getY());
        }

        // barre de vie de Gontrand  À DÉPLACER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        context.setStroke(Color.WHITE);
        context.strokeRect(10,10,150,35);


        context.setFill(Color.WHITE);
        double largeurEnFctVie = vie * 37.5;
        context.fillRect(10, 10, largeurEnFctVie, 35);
        context.drawImage(Partie.getImageProjectileActuel(),  170, 10);  // a changer en fonction du projectile actuel

    }


    @Override
    public boolean verifierCollision(ObjetDuJeu autre) {
        return super.verifierCollision(autre);
    }


    public boolean estInvincible() {
        return tempsInvincible > 0;
    }

    public void perdreVie() {
       vie--;

       image = new  Image("gontrand-outch.png");
       tempsInvincible = 2;
    }

    public int getVie() {
        return vie;
    }

    public static void setVie(int vie) {
        if (vie < 4) {
            Gontrand.vie = vie;
        } else {
            Gontrand.vie = 4;
        }

    }

    public Image getImage() {
        return image;
    }

    public  Point2D getCentre () {
        return new Point2D(position.getX() + taille.getX()/2, position.getY() + taille.getY()/2);
    }

    public double getPositionX () {
        return position.getX();
    }
}