package ca.qc.bdeb.sim.tp_pratique;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.Random;

public class Partie {


    private boolean jeuTermine = false;
    public static Gontrand gontrand;
    private ArrayList<PoissonsEnnemi> poissonsEnnemi;
    private ArrayList<Projectiles> projectiles;
    private Baril baril;
    private Camera camera;
    private int numeroDeNiveau = 0;  // a changer, c est pas 1, juste pour tester (on le fait pas dans scene de Jeu, car sceneJeu est appeler une seule fois
    private static double tempsDepuisDebutNiveau = 0;
    private double tempsEntreGroupePoissons;
    private double tempsDepuisDernierGroupePoissons;
    private double tempsDepuisDernierTir;
    private static Color couleurFond;
    private ArrayList<Algues> algues;
    Random random = new Random();


    public Partie() {
        //this.gontrand = new Gontrand(new Point2D(0, 200), new Point2D(0, 0), new Point2D(0, 0), new Point2D(102, 90));
        //this.poissonsEnnemi = new ArrayList<>(numeroDeNiveau);
        //this.camera = new Camera();
        //algues = positionAlgues();
        //couleurFond = Color.hsb(random.nextInt(190, 270), 0.84, 1.00);
        nouveauNiveau();
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

    public void collisionGontrandEtPoissonsEnnemis () {
        for (PoissonsEnnemi poisson : poissonsEnnemi) {
            if (gontrand.verifierCollision(poisson) && !gontrand.estInvincible()) {
                gontrand.perdreVie();

            }
        }

    }


    public void collisionAvecPoissonsEnnemis () {
        for (PoissonsEnnemi poisson : poissonsEnnemi) {
            for (Projectiles projectile : projectiles) {
               if (projectile.verifierCollision(poisson)) {
                   poissonsEnnemi.remove(poisson);  // vu qu'on le remove, il ne fait plus partie du ArrayList qu'on draw constamment = disparait
               }
            }
        }

    }

    public void collisionGontrandAvecBaril() {
        if (gontrand.verifierCollision(baril)) {
            // generate un random projectiles parmi la liste de projectiles en haut
        }
    }

    public void draw(GraphicsContext context) {

        dessinerFondEcran(context, algues);

        baril.draw(context, camera);

        if (jeuTermine) {

            context.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
            context.setFill(Color.DARKRED);
            context.fillText("FIN DE PARTIE", (HelloApplication.w/2) - 180, (HelloApplication.h/2));
        }

        // ESSENTIEL: vu que c un pane root, on dessine dans le bon ordre, pas hasard

        if (tempsDepuisDebutNiveau < 4) {
            context.setFont(Font.font("Purisa", 50));
            context.setFill(Color.BLACK);
            context.fillText("NIVEAU " + numeroDeNiveau, (HelloApplication.w)/2 - 100, (HelloApplication.h)/2);
        }


        gontrand.draw(context, camera);


        for (int i = 0; i < poissonsEnnemi.size(); i++) {
            poissonsEnnemi.get(i).draw(context, camera);
        }

        gontrand.draw(context, camera);

        for (Projectiles projectile : projectiles) {
            projectile.draw(context, camera);
        }
    }

    public void update(double deltaTemps) {

        if (jeuTermine) {
            //
        }


        tempsDepuisDebutNiveau += deltaTemps;
        tempsDepuisDernierTir += deltaTemps;

        gontrand.update(deltaTemps);
        camera.update(deltaTemps);

        baril.update(deltaTemps);

        // À voir si on met ça dans Gontrand ou ici

        arreteSiClamp(gontrand);
        clamp(gontrand);

        boolean creerPoisson = creerGroupeDePoissons(deltaTemps);
        if (creerPoisson) {
            int nombreDePoissons = random.nextInt(1, 5);
            for (int i = 0; i < nombreDePoissons; i++) {
                poissonsEnnemi.add(new PoissonsEnnemi(numeroDeNiveau, camera));   // on ajoute des poissons avec l initialisation
            }
        }
        for (int i = 0; i < poissonsEnnemi.size(); i++) {
            poissonsEnnemi.get(i).update(deltaTemps);
        }

        if (gontrand.getDroite() >= camera.getLargeurNiveau()) {
            nouveauNiveau();
        }

        if (Input.isKeyPressed(KeyCode.SPACE) && tempsDepuisDernierTir >= 0.5) {    // ajuster en fonction de Input, jsp pas trop comment, alors on test pour voir mtn
            projectiles.add(new EtoileDeMer(gontrand.getCentre()));  // a changer 100%%%%%%%%%%
            tempsDepuisDernierTir = 0;
        }

        for (Projectiles projectile : projectiles) {
            projectile.update(deltaTemps);
        }


        verifierEtatDePartie();

    }

    private void clamp(ObjetDuJeu objetDuJeu) {
        objetDuJeu.position = new Point2D(Math.clamp(
                objetDuJeu.position.getX(),
                camera.getPositionCamera().getX(),
                camera.getLargeurNiveau() - objetDuJeu.getTaille().getX()
        ),
                Math.clamp(
                        objetDuJeu.position.getY(),
                        0,
                        HelloApplication.h - objetDuJeu.getTaille().getY()
                )
        );
    }

    private void arreteSiClamp(ObjetDuJeu objetDuJeu) {
        boolean estClampX = Math.clamp(objetDuJeu.position.getX(), camera.getPositionCamera().getX(), camera.getLargeurNiveau() - gontrand.getTaille().getX()) != gontrand.position.getX();
        boolean estClampY = Math.clamp(objetDuJeu.position.getY(), camera.getPositionCamera().getY(), camera.getLargeurNiveau() - gontrand.getTaille().getY()) != gontrand.position.getY();

        if (estClampX)  objetDuJeu.velocite = new Point2D(0, objetDuJeu.velocite.getY());
        if (estClampY)  objetDuJeu.velocite = new Point2D(objetDuJeu.velocite.getX(), 0);
    }


    private void nouveauNiveau() {
        gontrand = new Gontrand(new Point2D(0, 200), new Point2D(0, 0), new Point2D(0, 0), new Point2D(102, 90));
        camera = new Camera();
        tempsDepuisDebutNiveau = 0;
        numeroDeNiveau++;
        algues = positionAlgues();
        couleurFond = Color.hsb(random.nextInt(190, 270), 0.84, 1.00);
        poissonsEnnemi = new ArrayList<>(numeroDeNiveau);
        projectiles = new ArrayList<>();
        baril = new  Baril();

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

    public void verifierEtatDePartie() {
        if (gontrand.getVie() == 0) {
            jeuTermine = true;
        }
    }


    private void dessinerFondEcran(GraphicsContext context, ArrayList<Algues> algues) {

        context.setFill(couleurFond);
        context.fillRect(0, 0, HelloApplication.w, HelloApplication.h);

        for (Algues a : algues) {
            a.draw(context, camera);
        }


    }

    public Point2D champElectrique(ArrayList<PoissonsEnnemi> poissons, Point2D point) {
        Point2D champElectrique = new Point2D(0,0);

        double k = (9.0)*(10^9);
        double absQPoisson = 900; //car tous les poissons ennemis ont une charge de -900

        for (PoissonsEnnemi poisson : poissons) {
            Point2D posPoisson = poisson.getPosition();
            double distance = (posPoisson.distance(point));
            double angleChamp = (point.subtract(posPoisson)).angle(new Point2D(1,0));
            double moduleE = (k*absQPoisson)/Math.pow(distance, 2); 
            champElectrique.add(new Point2D(
                    moduleE*Math.cos(angleChamp),
                    moduleE*Math.sin(angleChamp)));
        }
        
        return champElectrique;
    }
    
    public Point2D forceElectrique(ArrayList<PoissonsEnnemi> poissons, Point2D point) {
        double qSardine = 900; //Car la charge d'une boite de sardine est toujours de 900 
        Point2D forceE = champElectrique(poissons, point).multiply(qSardine);
        
        return forceE;
    }


    public static double getTempsDepuisDebutNiveau(){
        return tempsDepuisDebutNiveau;
    }

}
