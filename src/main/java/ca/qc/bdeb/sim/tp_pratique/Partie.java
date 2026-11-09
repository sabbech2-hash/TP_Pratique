package ca.qc.bdeb.sim.tp_pratique;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Partie {

        private Gontrand gontrand;
        private PoissonsEnnemi poissonsEnnemi;
        private Color couleurFond;
        private int numeroDeNiveau = 1;



      //  private Camera camera;


        public Partie() {
            this.gontrand = new Gontrand(new Point2D(0,200), new Point2D(0,0), new Point2D(0,0),  new Point2D(102,90));
            this.poissonsEnnemi = new PoissonsEnnemi(numeroDeNiveau);
        }

    public void verifierCollision () {
//
    }

    public void drawFondNiveau(GraphicsContext context) {


        context.setFill(couleurFond);
        context.fillRect(0, 0, HelloApplication.w, HelloApplication.h);

    }

    public void draw(GraphicsContext context){

        gontrand.draw(context);
        poissonsEnnemi.draw(context);

    }

    public void update(double deltaTemps) {
            gontrand.update(deltaTemps);
            poissonsEnnemi.update(deltaTemps);

    }




}
