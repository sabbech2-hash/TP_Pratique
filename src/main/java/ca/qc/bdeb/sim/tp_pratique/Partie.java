package ca.qc.bdeb.sim.tp_pratique;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class Partie {

    public static Gontrand gontrand;
    private ArrayList<PoissonsEnnemi> poissonsEnnemi;
    private Camera camera;
    private int numeroDeNiveau = 1;  // a changer, c est pas 1, juste pour tester
    private double tempsEntreGroupePoissons;
    private double tempsDepuisDernierGroupePoissons;
    private static Color couleurFond;
    private ArrayList<Algues> algues;
    Random random = new Random();


    public Partie() {
        this.gontrand = new Gontrand(new Point2D(0, 200), new Point2D(0, 0), new Point2D(0, 0), new Point2D(102, 90));
        this.poissonsEnnemi = new ArrayList<>(numeroDeNiveau);
        this.camera = new Camera();
        algues = positionAlgues();
        couleurFond = Color.hsb(random.nextInt(190, 270), 0.84, 1.00);
    }

    public boolean creerGroupeDePoissons(double deltaTemps) {

        tempsDepuisDernierGroupePoissons += deltaTemps;
        tempsEntreGroupePoissons = 0.75 + 1 / Math.sqrt(numeroDeNiveau);

        if (tempsDepuisDernierGroupePoissons >= tempsEntreGroupePoissons) {   // c pas le delta temps, parce va toujours etre plus petit ici
            tempsDepuisDernierGroupePoissons = 0;
            return true;

        }
        return false;
    }


    public void draw(GraphicsContext context) {


        dessinerFondEcran(context, algues);


        gontrand.draw(context, camera);

        for (int i = 0; i < poissonsEnnemi.size(); i++) {
            poissonsEnnemi.get(i).draw(context, camera);
        }

    }

    public void update(double deltaTemps) {

        gontrand.update(deltaTemps);
        // À voir si on met ça dans Gontrand ou ici
        gontrand.position = new Point2D(Math.clamp(
                gontrand.position.getX(),
                camera.getPositionCamera().getX(),
                camera.getLargeurNiveau() - gontrand.getTaille().getX()
        ),
                Math.clamp(
                        gontrand.position.getY(),
                        0,
                        HelloApplication.h - gontrand.getTaille().getY()
                )
        );

        camera.update(deltaTemps);

        boolean creerPoisson = creerGroupeDePoissons(deltaTemps);
        if (creerPoisson) {
            int nombreDePoissons = random.nextInt(1, 5);
            for (int i = 0; i < nombreDePoissons; i++) {
                poissonsEnnemi.add(new PoissonsEnnemi(numeroDeNiveau));   // on ajoute des poissons avec l initialisation
            }
        }
        for (int i = 0; i < poissonsEnnemi.size(); i++) {
            poissonsEnnemi.get(i).update(deltaTemps);
        }


    }

    private ArrayList<Algues> positionAlgues() {


        Image[] imagesDecor = {
                new Image("decor1.png"),
                new Image("decor2.png"),
                new Image("decor3.png"),
                new Image("decor4.png"),
                new Image("decor5.png"),
                new Image("decor6.png"),

        };


        int position = 0;

        ArrayList<Algues> algues = new ArrayList();

        while (position < HelloApplication.w * 8) {
            algues.add(new Algues(position, imagesDecor[random.nextInt(imagesDecor.length)]));
            position += 80 + random.nextInt(50, 101);
        }
        return algues;

    }


    public void dessinerFondEcran(GraphicsContext context, ArrayList<Algues> algues) {

        context.setFill(couleurFond);
        context.fillRect(0, 0, HelloApplication.w, HelloApplication.h);

        for (Algues a : algues) {
            a.draw(context, camera);
        }


    }


}
