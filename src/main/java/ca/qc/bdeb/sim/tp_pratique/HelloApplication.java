package ca.qc.bdeb.sim.tp_pratique;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
        btnJouer.setOnMouseClicked(e -> {
            stage.setScene(sceneJeu());
        });

        var btnInfo = new Button("Infos");
        btnInfo.setOnAction(e -> {
            stage.setScene(sceneInfo());
        });

        optionsAccueil.getChildren().addAll(btnJouer, btnInfo);

        root.getChildren().addAll(canvas, optionsAccueil);


        return scene;


    }

    private Scene sceneInfo () {

        Pane root = new Pane();

        Scene scene = new Scene(root, w, h);

        Canvas canvas = new Canvas(w, h);   // reste a voir si peut creer un pour tout

        root.getChildren().add(canvas);

        GraphicsContext context = canvas.getGraphicsContext2D();

        context.setFill(Color.hsb(random.nextInt(190, 270) ,0.84, 1.00));
        context.fillRect(0, 0, w, h);

        context.setFill(Color.BLACK);
        context.setFont(Font.font("Purisa", 50));
        context.fillText("Gontrand le Flétan", w/2 - 200 , 50);  // faudrait trouver facon de mieux centrer en X

        Random random = new Random();
        Image [] imagesPoissons = {

                new Image("poisson1.png"),
                new Image("poisson2.png"),
                new Image("poisson3.png"),
                new Image("poisson4.png"),
                new Image("poisson5.png"),

        };

        context.drawImage(imagesPoissons[random.nextInt(imagesPoissons.length)], w/2 - 70 , 100);  // meme chose pour position en X

        context.setFont(Font.font("Arial", 25));
        context.fillText("Par", w/2 - 150, 300);
        context.setFont(Font.font("Verdana", 30));
        context.fillText("Thomas Benoit",  w/2 - 100 , 300);
        context.setFont(Font.font("Arial", 25));
        context.fillText("et",w/2 - 140, 360 );
        context.setFont(Font.font("Verdana", 30));
        context.fillText("Bechara Saba", w/2 - 100, 360);

        var btnRetour = new Button("Retour");
        root.getChildren().add(btnRetour);   // meme chose, le bouton est en (0,0), j arrive pas a deplacer

        scene.setOnKeyPressed(e -> {
            if  (e.getCode() == KeyCode.ESCAPE) {
                stage.setScene(sceneAccueil());
            }
        });








        return scene;
    }

    private Scene sceneJeu() {

        Partie premiere = new Partie();

        Pane root = new Pane();
        Canvas canvas = new Canvas(w, h);

        Scene  scene = new Scene(root, w, h);

        GraphicsContext context = canvas.getGraphicsContext2D();

        root.getChildren().add(canvas);

        context.setFill(Color.hsb(random.nextInt(190, 270) ,0.84, 1.00));
        context.fillRect(0, 0, w, h);

        scene.setOnKeyPressed((e)-> {
            if (e.getCode() == KeyCode.ESCAPE) {

                Platform.exit();
            } else {
                Input.setKeyPressed(e.getCode(), true);
            }
        });
        scene.setOnKeyReleased((e)-> {
            Input.setKeyPressed(e.getCode(),false);
        });

        AnimationTimer timer = new AnimationTimer() {
            private long dernierTemps = System.nanoTime();
            @Override
            public void handle(long temps) {
                double deltaTemps = (temps - dernierTemps)  * 1e-9;

                context.fillRect(0, 0, w, h);

                premiere.draw(context);
                premiere.update(deltaTemps);





                dernierTemps = temps;
            }
        };
        timer.start();








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
