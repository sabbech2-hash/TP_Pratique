package ca.qc.bdeb.sim.tp_pratique;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class HelloApplication extends Application {

    private Stage stage;
    Random random = new Random();
    private static Color couleurFond;


    public ArrayList<Algues> positionAlgues () {


        Image [] imagesDecor = {
                new Image("decor1.png"),
                new Image("decor2.png"),
                new Image("decor3.png"),
                new Image("decor4.png"),
                new Image("decor5.png"),
                new Image("decor6.png"),

        };


        int position = 0;

        ArrayList <Algues> algues = new ArrayList();

        while (position < w * 8) {
            algues.add(new  Algues(position, imagesDecor[random.nextInt(imagesDecor.length)]));
            position += 80 + random.nextInt(50,101);
        }
        return algues;

    }


    public void dessinerFondEcran (GraphicsContext context, ArrayList<Algues> algues) {

        context.setFill(couleurFond);
        context.fillRect(0, 0, w, h);

        for (Algues a : algues) {
            context.drawImage(a.getImage(), a.getPosition().getX(), a.getPosition().getY(), 80, 119);
        }


    }


    private Scene sceneAccueil() {

        var root = new StackPane();
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, w, h);

        Canvas canvas = new Canvas(w, h);

        GraphicsContext context = canvas.getGraphicsContext2D();

       // context.setFill(Color.hsb(random.nextInt(190, 270) ,0.84, 1.00));
        context.setFill(Color.web("#2A7FFF"));
        context.fillRect(0, 0, w, h);

        //context.drawImage(new Image("logo.png"), 275,35, 330, 350);

        VBox nodesAccueil = new VBox();
        nodesAccueil.setSpacing(10);
        nodesAccueil.setAlignment(Pos.TOP_CENTER);

        Image imgLogo = new Image("logo.png");

        ImageView imgViewLogo = new ImageView(imgLogo);
        imgViewLogo.setFitWidth(470);
        imgViewLogo.setFitHeight(550);
        imgViewLogo.setPreserveRatio(true);

        HBox optionsAccueil = new HBox();
        optionsAccueil.setSpacing(10);
        optionsAccueil.setAlignment(Pos.CENTER);

        var btnJouer = new Button("Jouer!");
        btnJouer.setOnMouseClicked(e -> {
            stage.setScene(sceneJeu());
        });

        var btnInfo = new Button("Infos");
        btnInfo.setOnAction(e -> {
            stage.setScene(sceneInfo());
        });

        optionsAccueil.getChildren().addAll(btnJouer, btnInfo);

        nodesAccueil.getChildren().addAll(imgViewLogo, optionsAccueil);

        root.getChildren().addAll(canvas, nodesAccueil);

        //root.getChildren().addAll(canvas, optionsAccueil);


        return scene;


    }

    private Scene sceneInfo () {

        StackPane root = new StackPane();

        Scene scene = new Scene(root, w, h);

        Canvas canvas = new Canvas(w, h);   // reste a voir si peut creer un pour tout

        GraphicsContext context = canvas.getGraphicsContext2D();

        context.setFill(Color.hsb(random.nextInt(190, 270) ,0.84, 1.00));
        context.fillRect(0, 0, w, h);

        Text titre = new Text("Gontrand le Flétand");
        titre.setFont(Font.font("Purisa", 50));

        Random random = new Random();
        Image [] imagesPoissons = {

                new Image("poisson1.png"),
                new Image("poisson2.png"),
                new Image("poisson3.png"),
                new Image("poisson4.png"),
                new Image("poisson5.png"),

        };

        ImageView imgViewPoissonAleatoire = new ImageView(imagesPoissons[random.nextInt(imagesPoissons.length)]);
        imgViewPoissonAleatoire.setFitWidth(150);
        imgViewPoissonAleatoire.setFitHeight(300);
        imgViewPoissonAleatoire.setPreserveRatio(true);
        Text[] textesNom = {
                new Text("Thomas Benoit"),
                new Text("Bechara Saba")
        };

        for (Text text : textesNom) {
            text.setFont(Font.font("Verdana", 30));
        }

        Text [] textesPetits = {
                new Text("Par"),
                new Text("et")
        };

        for (Text text : textesPetits) {
            text.setFont(Font.font("Arial", 25));
        }

        Text texteExplicatif = new Text("Travail remis à Nicolas Hurtubise et Raouf Babari. Graphismes adaptés de https://game-icon.net/ et de " +
                "https:openclipart.org/. Développé dans le cadre du cours 420-SF3-RE - Développement d'applications dans un " +
                "environnement graphique, au Collège de Bois-de-Boulogne.");
        texteExplicatif.setFont(Font.font("Arial", 15));
        texteExplicatif.setWrappingWidth(800);

        var btnRetour = new Button("Retour");

        HBox parPlusNom = new HBox();
        parPlusNom.setAlignment(Pos.CENTER);
        parPlusNom.setSpacing(15);
        parPlusNom.getChildren().addAll(textesPetits[0], textesNom[0]);

        HBox etPlusNom = new HBox();
        etPlusNom.setAlignment(Pos.CENTER);
        etPlusNom.setSpacing(15);
        etPlusNom.getChildren().addAll(textesPetits[1], textesNom[1]);

        VBox nodesScenesInfos = new VBox();
        nodesScenesInfos.setAlignment(Pos.TOP_CENTER);
        nodesScenesInfos.setSpacing(25);
        nodesScenesInfos.setPadding(new Insets(10,25,25,25));
        nodesScenesInfos.getChildren().addAll(titre, imgViewPoissonAleatoire,
                parPlusNom, etPlusNom, texteExplicatif, btnRetour);

        btnRetour.setOnAction(e -> {
            stage.setScene(sceneAccueil());
        });

        scene.setOnKeyPressed(e -> {
            if  (e.getCode() == KeyCode.ESCAPE) {
                stage.setScene(sceneAccueil());
            }
        });

        root.getChildren().addAll(canvas, nodesScenesInfos);






        return scene;
    }

    private Scene sceneJeu() {


        Partie premiere = new Partie();
        numeroNiveau = 0;

        Pane root = new Pane();
        Canvas canvas = new Canvas(w, h);

        Scene  scene = new Scene(root, w, h);

        GraphicsContext context = canvas.getGraphicsContext2D();

        root.getChildren().add(canvas);

        ArrayList <Algues> algues = positionAlgues();

        couleurFond = Color.hsb(random.nextInt(190, 270) ,0.84, 1.00);



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


                dessinerFondEcran(context, algues);

                premiere.update(deltaTemps);
                premiere.draw(context);


                dernierTemps = temps;
            }
        };
        timer.start();








        return scene;
    }

    public static final int  h = 520;
    public static final int w = 900;
    private static int numeroNiveau;

    @Override
    public void start(Stage primaryStage)  {

        this.stage = primaryStage;
        stage.setTitle("Gontrand le Flétan");
        stage.setResizable(false);

        stage.setScene(sceneAccueil());

        stage.show();
    }
}
