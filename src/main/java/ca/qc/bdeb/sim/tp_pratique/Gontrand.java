package ca.qc.bdeb.sim.tp_pratique;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Gontrand extends ObjetDuJeu {

    private static int vie = 4;   // nombre de vie de Gontrand n'est pas attribut de classe

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
        boolean gauche = Input.isKeyPressed(KeyCode.LEFT);
        boolean droite = Input.isKeyPressed(KeyCode.RIGHT);
        boolean haut = Input.isKeyPressed(KeyCode.UP);
        boolean bas = Input.isKeyPressed(KeyCode.DOWN);

        if (gauche) {
            acceleration = new Point2D(-1000, acceleration.getY());
        }
        if (velocite.getX() < -300) {
            velocite = new Point2D(-300, velocite.getY());
        }


        if (droite) {
            acceleration = new Point2D(1000, acceleration.getY());
        }
        if (velocite.getX() > 300) {
            velocite = new Point2D(300, velocite.getY());
        }


        if (bas) {
            acceleration = new Point2D(acceleration.getX(), 1000);
        }
        if (velocite.getY() > 300) {
            velocite = new Point2D(velocite.getX(), 300);
        }


        if (haut) {
            acceleration = new Point2D(acceleration.getX(), -1000);
        }
        if (velocite.getY() < -300) {
            velocite = new Point2D(velocite.getX(), -300);
        }


        if (!(droite || gauche) && velocite.getX() > 0) {
            acceleration = new Point2D(-100, acceleration.getY());
        }


        if  (!(gauche || droite) && velocite.getX() < 0) {
            acceleration = new Point2D(100, acceleration.getY());
        }


        if (!(bas || haut) && velocite.getY() > 0) {
            acceleration = new Point2D(acceleration.getX(), -100);
        }


        if (!(haut || bas) && velocite.getY() < 0) {
            acceleration = new Point2D(acceleration.getX(), 100);
        }


        updatePhysique(deltaTemps);  //

    }

    @Override
    protected void updatePhysique(double deltaTemps) {
        super.updatePhysique(deltaTemps);

    }

    @Override
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

        // barre de vie de Gontrand
        context.setStroke(Color.WHITE);
        context.strokeRect(10,10,150,35);

        // methode pour verifier si Gontrand perdu des vies

        context.setFill(Color.WHITE);
        context.fillRect(10, 10, 150, 35);
        context.drawImage(new Image("etoile.png"),  170, 12);

    }

    public void perduVie() {
        // doit voir si Gontrand est rentrer en collision
    }
    public void perduDroite() {}

    public Image getImage() {
        return image;
    }

    //public void enCollision



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