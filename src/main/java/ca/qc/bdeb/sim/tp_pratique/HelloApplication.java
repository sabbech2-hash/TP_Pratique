package ca.qc.bdeb.sim.tp_pratique;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class HelloApplication extends Application {

    Random random = new Random();

    int h = 900;
    int w = 520;
    @Override
    public void start(Stage stage)  {
        Pane root = new Pane(); //
        Canvas canvas = new Canvas(w, h);
        root.getChildren().add(canvas);

        GraphicsContext context = canvas.getGraphicsContext2D();

        context.setFill(Color.hsb(random.nextInt(190, 270) ,0.84, 1.00));
        context.fillRect(0, 0, w, h);
















        Scene scene = new Scene(root, w, h);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
