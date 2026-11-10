package ca.qc.bdeb.sim.tp_pratique;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
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
        Random random = new Random();


        public Partie() {
            this.gontrand = new Gontrand(new Point2D(0,200), new Point2D(0,0), new Point2D(0,0),  new Point2D(102,90));
            this.poissonsEnnemi = new ArrayList<>(numeroDeNiveau);
            this.camera = new Camera();
        }

    public boolean creerGroupeDePoissons(double deltaTemps) {

        tempsDepuisDernierGroupePoissons += deltaTemps;
        tempsEntreGroupePoissons = 0.75 + 1/Math.sqrt(numeroDeNiveau);

        if (tempsDepuisDernierGroupePoissons >= tempsEntreGroupePoissons) {   // c pas le delta temps, parce va toujours etre plus petit ici
            tempsDepuisDernierGroupePoissons = 0;
            return true;

        }
        return false;
    }


    public void draw(GraphicsContext context) {


        gontrand.draw(context, camera);

        for (int i  = 0; i < poissonsEnnemi.size(); i++) {
            poissonsEnnemi.get(i).draw(context, camera);
        }

    }

    public void update(double deltaTemps) {

            camera.update(deltaTemps);   // ca marche pas comme je pensais
            gontrand.update(deltaTemps);
            boolean creerPoisson = creerGroupeDePoissons(deltaTemps);
            if (creerPoisson) {
                int nombreDePoissons = random.nextInt(1,5);
               for (int  i = 0; i < nombreDePoissons; i++) {
                    poissonsEnnemi.add(new PoissonsEnnemi(numeroDeNiveau));   // on ajoute des poissons avec l initialisation
                }
          }
           for (int i  = 0; i < poissonsEnnemi.size(); i++) {
               poissonsEnnemi.get(i).update(deltaTemps);
           }


    }




}
