package ca.qc.bdeb.sim.tp_pratique;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class HelloApplication extends Application {

    private Stage stage;
    Random random = new Random();


    private Scene sceneAccueil() {

        var root = new StackPane();
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, w, h);

       Canvas canvas = new Canvas(w, h);

       GraphicsContext context = canvas.getGraphicsContext2D();

        context.setFill(Color.hsb(random.nextInt(190, 270) ,0.84, 1.00));
        context.fillRect(0, 0, w, h);

        context.drawImage(new Image("logo.png"), 275,35, 330, 350);

        HBox optionsAccueil = new HBox();
        optionsAccueil.setSpacing(10);

        var btnJouer = new Button("Jouer!");
        var btnInfo = new Button("Infos");

        optionsAccueil.getChildren().addAll(btnJouer, btnInfo);

        root.getChildren().addAll(canvas, optionsAccueil);

        return scene;


    }

    int h = 520;
    int w = 900;
    @Override
    public void start(Stage primaryStage)  {

        this.stage = primaryStage;
        stage.setTitle("Gontrand le Flétan");
        stage.setResizable(false);

        stage.setScene(sceneAccueil());



















        stage.show();
    }
}
