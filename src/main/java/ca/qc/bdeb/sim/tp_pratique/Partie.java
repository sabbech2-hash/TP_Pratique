package ca.qc.bdeb.sim.tp_pratique;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

    public class Partie {

        private Gontrand gontrand;

      //  private Camera camera;


        public Partie() {
            this.gontrand = new Gontrand(new Point2D(0,200), new Point2D(0,0), new Point2D(0,0),  new Point2D(102,90));
        }

    public void draw(GraphicsContext context){

        gontrand.draw(context);
    }

    public void update(double deltaTemps) {

    }


}
