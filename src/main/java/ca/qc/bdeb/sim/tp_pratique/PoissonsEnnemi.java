package ca.qc.bdeb.sim.tp_pratique;

import javafx.scene.Camera;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

public class PoissonsEnnemi extends ObjetDuJeu {

    private Image image;

    Random random = new Random();
    private Image [] imagesPoissons = {

            new Image("poisson1.png"),
            new Image("poisson2.png"),
            new Image("poisson3.png"),
            new Image("poisson4.png"),
            new Image("poisson5.png"),

    };

    public PoissonsEnnemi() {
        this.image = imagesPoissons[random.nextInt(imagesPoissons.length)];
    }


    @Override
    public void draw(GraphicsContext context, Camera camera) {

        context.drawImage(image, 0, 0);

    }
}
