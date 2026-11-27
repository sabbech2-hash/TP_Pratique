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


    private boolean jeuTermine;
    private boolean jeuEstEnPause;
    private boolean modeDebogage;
    private boolean dEtaitEnfonce;
    private boolean tEtaitEnfonce;
    private boolean fEtaitEnfonce;
    private boolean barilEstUtilise;
    private boolean mannaEstUtilise;
    public static Gontrand gontrand;
    private ArrayList<PoissonEnnemi> poissonsEnnemi;
    private ArrayList<Projectile> projectiles;
    private Manna manna;
    private static Image imageProjectileActuel;
    private int numeroProjectileGontrand;
    private Baril baril;
    private Camera camera;
    private int numeroDeNiveau;  // a changer, c est pas 1, juste pour tester (on le fait pas dans scene de Jeu, car sceneJeu est appeler une seule fois
    private static double tempsDepuisDebutNiveau;
    private static double tempsDepuisMortGontrand;
    private double tempsEntreGroupePoissons;
    private double tempsDepuisDernierGroupePoissons;
    private double tempsDepuisDernierTir;  // different de creation du projectile
    private static Color couleurFond;
    private ArrayList<Algue> algues;
    Random random = new Random();


    public Partie() {
        //this.gontrand = new Gontrand(new Point2D(0, 200), new Point2D(0, 0), new Point2D(0, 0), new Point2D(102, 90));
        //this.poissonsEnnemi = new ArrayList<>(numeroDeNiveau);
        //this.camera = new Camera();
        //algues = positionAlgues();
        //couleurFond = Color.hsb(random.nextInt(190, 270), 0.84, 1.00);
        jeuTermine = false;
        jeuEstEnPause = false;
        modeDebogage = false;
        dEtaitEnfonce = false;
        tEtaitEnfonce = false;
        fEtaitEnfonce = false;
        barilEstUtilise = false;
        mannaEstUtilise = false;
        gontrand = new Gontrand(new Point2D(0, 200), new Point2D(0, 0), new Point2D(0, 0), new Point2D(102, 90));
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

    public void collisionGontrandEtPoissonsEnnemis() {
        for (PoissonEnnemi poisson : poissonsEnnemi) {
            if (gontrand.verifierCollision(poisson) && !gontrand.estInvincible()) {
                gontrand.perdreVie();

            }
        }

    }

    public void collisionAvecPoissonsEnnemis() {
        for (int i = poissonsEnnemi.size() - 1; i >= 0; i--) {
            PoissonEnnemi poisson = poissonsEnnemi.get(i);
            for (Projectile projectile : projectiles) {
                if (projectile.verifierCollision(poisson)) {
                    poissonsEnnemi.remove(poisson);  // vu qu'on le remove, il ne fait plus partie du ArrayList qu'on draw constamment = disparait
                }
            }
        }

    }

    public void collisionGontrandAvecBaril() {


        int ancienProjectile = numeroProjectileGontrand;
        if (gontrand.verifierCollision(baril)) {

            barilEstUtilise = true;

            do {
                numeroProjectileGontrand = random.nextInt(3);

            } while (numeroProjectileGontrand == ancienProjectile);
            // generate un random projectiles parmi les projectiles que Gontrand n'a pas
        }
    }

    public void collisionGontrandEtManna() {
        if (gontrand.verifierCollision(manna)) {
            Gontrand.setVie(gontrand.getVie() + 1);   // faut mettre une limite a 4 vies sinon ca s eternalise
            mannaEstUtilise = true;
        }

    }

    public void draw(GraphicsContext context) {

        dessinerFondEcran(context, algues);


        baril.draw(context, camera);
        manna.draw(context, camera);

        // ESSENTIEL: vu que c un pane root, on dessine dans le bon ordre, pas hasard

        if (tempsDepuisDebutNiveau < 4) {
            context.setFont(Font.font("Purisa", 50));
            context.setFill(Color.BLACK);
            context.fillText("NIVEAU " + numeroDeNiveau, (MainJavaFX.w) / 2 - 100, (MainJavaFX.h) / 2);
        }


        gontrand.draw(context, camera);


        for (int i = 0; i < poissonsEnnemi.size(); i++) {
            poissonsEnnemi.get(i).draw(context, camera);
        }

        gontrand.draw(context, camera);

        for (Projectile projectile : projectiles) {
            projectile.draw(context, camera);
        }

        if (jeuTermine && tempsDepuisMortGontrand < 3) {

            context.setFont(Font.font("BOLD", 50));
            context.setFill(Color.DARKRED);
            context.fillText("FIN DE PARTIE", MainJavaFX.w / 2 - 150, (MainJavaFX.h) / 2);
        }

        if (jeuEstEnPause) {
            context.setFill(Color.BLACK);
            context.setFont(Font.font("BOLD", 50));
            context.fillText("PAUSE", MainJavaFX.w / 2 - 50, (MainJavaFX.h) / 2);
        }

        if (modeDebogage) {
            dessinerDebogage(context);
        }

        if (Input.isKeyPressed(KeyCode.F) && !fEtaitEnfonce) {
            for (double x = 0; x < MainJavaFX.w; x += 50) {
                for (double y = 0; y < MainJavaFX.h; y += 50) {
                    var positionMonde = new Point2D(x, y);
                    var positionEcran = positionMonde.add(camera.getPositionCamera());
                    System.out.println(positionEcran);
                    // TODO: Seulement faire ça si la position (x, y) est visible dans l'écran
                    Point2D force = champElectrique(poissonsEnnemi, positionEcran);
                    UtilitairesDessins.dessinerVecteurForce(positionMonde, force, context);
                }
            }
        }

        gontrand.drawBarreDeVie(context);

    }

    public void update(double deltaTemps) {

        if (jeuTermine) {
            tempsDepuisMortGontrand += deltaTemps;
        }

        if (jeuEstEnPause) {
            return;   // ca arrete pas le temps, c ca la confusion A VOIR
        }

        switch (numeroProjectileGontrand) {
            case 0:
                imageProjectileActuel = new Image("etoile.png");
                break;

            case 1:
                imageProjectileActuel = new Image("hippocampe.png");
                break;

            default:
                imageProjectileActuel = new Image("sardines.png");
        }


        tempsDepuisDebutNiveau += deltaTemps;
        tempsDepuisDernierTir += deltaTemps;  // juste quand on appuie sur ESPACE

        gontrand.update(deltaTemps);
        camera.update(deltaTemps);

        baril.update(deltaTemps);


        if (barilEstUtilise) {
            baril.barilUtilise();
        } else {
            collisionGontrandAvecBaril();
        }

        if (mannaEstUtilise) {
            manna.mannaUtilise();
        } else {
            collisionGontrandEtManna();
        }


        // À voir si on met ça dans Gontrand ou ici
        arreteSiClamp(gontrand);
        clamp(gontrand);

        boolean creerPoisson = creerGroupeDePoissons(deltaTemps);
        if (creerPoisson) {
            int nombreDePoissons = random.nextInt(1, 5);
            for (int i = 0; i < nombreDePoissons; i++) {
                poissonsEnnemi.add(new PoissonEnnemi(numeroDeNiveau, camera));   // on ajoute des poissons avec l initialisation
            }
        }
        for (int i = 0; i < poissonsEnnemi.size(); i++) {
            poissonsEnnemi.get(i).update(deltaTemps);
        }

        enleverObjetsHorsEcran();

        if (gontrand.getDroite() >= camera.getLargeurNiveau()) {
            nouveauNiveau();
            barilEstUtilise = false;
            mannaEstUtilise = false;
        }

        if (Input.isKeyPressed(KeyCode.SPACE) && tempsDepuisDernierTir >= 0.5) {    // ajuster en fonction de Input, jsp pas trop comment, alors on test pour voir mtn

            tempsDepuisDernierTir = 0;

            if (numeroProjectileGontrand == 0) {
                projectiles.add(new EtoileDeMer(gontrand.getCentre()));  // a changer 100%%%%%%%%%%
            } else if (numeroProjectileGontrand == 1) {
                int i = 0;
                while (i < 3) {
                    i++;
                    projectiles.add(new Hippocampe(gontrand.getCentre()));
                }
            } else if (numeroProjectileGontrand == 2) {
                projectiles.add(new BoiteSardine(gontrand.getCentre()));
            }
        }

        for (Projectile projectile : projectiles) {
            if (projectile instanceof EtoileDeMer || projectile instanceof Hippocampe) {
                projectile.update(deltaTemps);
            } else if (projectile instanceof BoiteSardine) {
                projectile.acceleration = forceElectrique(poissonsEnnemi, projectile.getPosition());
                System.out.println(projectile.acceleration);
                projectile.update(deltaTemps);
            }
        }

        manna.update(deltaTemps);

        collisionGontrandEtPoissonsEnnemis();
        collisionAvecPoissonsEnnemis();


        verifierEtatDePartie();
        modeDebogage();

    }

    public void enleverObjetsHorsEcran() {
        double cameraEnX = camera.getPositionCamera().getX();
        for (int i = poissonsEnnemi.size() - 1; i >= 0; i--) {
            PoissonEnnemi poisson = poissonsEnnemi.get(i);
            if (poisson.getDroite() - cameraEnX < 0 || poisson.getBas() < 0 || poisson.getHaut() > MainJavaFX.h) {
                poissonsEnnemi.remove(poisson);
            }
        }

        for (int i = projectiles.size() - 1; i >= 0; i--) {
            Projectile projectile = projectiles.get(i);
            if (projectile.getGauche() - cameraEnX > MainJavaFX.w || projectile.getDroite() - cameraEnX < 0 || projectile.getBas() < 0 || projectile.getHaut() > MainJavaFX.h) {
                projectiles.remove(projectile);
            }
        }
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
                        MainJavaFX.h - objetDuJeu.getTaille().getY()
                )
        );
    }

    private void arreteSiClamp(ObjetDuJeu objetDuJeu) {
        boolean estClampX = Math.clamp(objetDuJeu.position.getX(), camera.getPositionCamera().getX(), camera.getLargeurNiveau() - gontrand.getTaille().getX()) != gontrand.position.getX();
        boolean estClampY = Math.clamp(objetDuJeu.position.getY(), camera.getPositionCamera().getY(), camera.getLargeurNiveau() - gontrand.getTaille().getY()) != gontrand.position.getY();

        if (estClampX) objetDuJeu.velocite = new Point2D(0, objetDuJeu.velocite.getY());
        if (estClampY) objetDuJeu.velocite = new Point2D(objetDuJeu.velocite.getX(), 0);
    }


    private void nouveauNiveau() {
        gontrand.position = new Point2D(0, 200);
        camera = new Camera();
        tempsDepuisDebutNiveau = 0;
        tempsDepuisMortGontrand = 0;
        numeroDeNiveau++;
        algues = positionAlgues();
        couleurFond = Color.hsb(random.nextInt(190, 270), 0.84, 1.00);
        poissonsEnnemi = new ArrayList<>(numeroDeNiveau);
        projectiles = new ArrayList<>();
        manna = new Manna();
        baril = new Baril();

    }

    private ArrayList<Algue> positionAlgues() {


        Image[] imagesDecor = {
                new Image("decor1.png"),
                new Image("decor2.png"),
                new Image("decor3.png"),
                new Image("decor4.png"),
                new Image("decor5.png"),
                new Image("decor6.png"),

        };


        int position = 0;

        ArrayList<Algue> algues = new ArrayList();

        while (position < MainJavaFX.w * 8) {
            algues.add(new Algue(position, imagesDecor[random.nextInt(imagesDecor.length)]));
            position += 80 + random.nextInt(50, 101);
        }
        return algues;

    }

    public boolean changerEtatPause() {
        jeuEstEnPause = !jeuEstEnPause;
        return jeuEstEnPause;
    }

    public boolean partieEstEnPause() {
        return jeuEstEnPause;
    }


    public boolean verifierEtatDePartie() {
        if (gontrand.getVie() == 0) {
            jeuTermine = true;
        }
        return jeuTermine;
    }


    private void dessinerFondEcran(GraphicsContext context, ArrayList<Algue> algues) {

        context.setFill(couleurFond);
        context.fillRect(0, 0, MainJavaFX.w, MainJavaFX.h);

        for (Algue a : algues) {
            a.draw(context, camera);
        }


    }

    public Point2D champElectrique(ArrayList<PoissonEnnemi> poissons, Point2D point) {

        Point2D champElectrique = new Point2D(0, 0);

        double k = 200;
        double absQPoisson = 900; //car tous les poissons ennemis ont une charge de -900

        for (PoissonEnnemi poisson : poissons) {

            Point2D posPoisson = poisson.getCentre();

            double distance = (posPoisson.distance(point));
            distance = Math.max(distance, 1); // Limite la distance à au moins 1 pixel.

            Point2D direction = posPoisson.subtract(point).normalize();

            double moduleChampE = (k * absQPoisson) / Math.pow(distance, 2);
            Point2D champUnPoisson = direction.multiply(moduleChampE);

            champElectrique = champElectrique.add(champUnPoisson);
        }
        System.out.println(champElectrique);

        return champElectrique;
    }

    public Point2D forceElectrique(ArrayList<PoissonEnnemi> poissons, Point2D point) {
        double qSardine = 900; //Car la charge d'une boite de sardine est toujours de 900

        Point2D forceE = champElectrique(poissons, point).multiply(qSardine);

        return forceE;
    }



    public void modeDebogage() {

        //Sauter un niveau

        if (Input.isKeyPressed(KeyCode.T) && !tEtaitEnfonce) {
            nouveauNiveau();
        }
        tEtaitEnfonce = Input.isKeyPressed(KeyCode.T);

        //Redonner un maximum de vies à Gontrand

        if (Input.isKeyPressed(KeyCode.R)) {
            gontrand.setVie(4);
        }

        //Donner les sardines comme projectile

        if (Input.isKeyPressed(KeyCode.E)) {
            numeroProjectileGontrand = 2;
        }

        //Donner les hippocampes comme projectile

        if (Input.isKeyPressed(KeyCode.W)) {
            numeroProjectileGontrand = 1;
        }

        //Donner l'étoile de mer comme projectile

        if (Input.isKeyPressed(KeyCode.Q)) {
            numeroProjectileGontrand = 0;
        }

        if (Input.isKeyPressed(KeyCode.D) && !dEtaitEnfonce) {
            modeDebogage = !modeDebogage;

        }
        dEtaitEnfonce = Input.isKeyPressed(KeyCode.D);

    }

    public void dessinerDebogage(GraphicsContext context) {
        context.setFill(Color.WHITE);
        context.setFont(Font.font("Arial", 12));
        context.fillText("NB Poissons: " + poissonsEnnemi.size(), 10, 60);
        context.fillText("NB Projectiles: " + projectiles.size(), 10, 75);
        context.fillText("Position Gontrand: " + ((gontrand.getPositionX() / (MainJavaFX.w * 8)) * 100) + "%", 10, 90);

    }

    public static double getTempsDepuisDebutNiveau() {
        return tempsDepuisDebutNiveau;
    }

    public static double getTempsDepuisMortGontrand() {
        return tempsDepuisMortGontrand;
    }

    public static Image getImageProjectileActuel() {
        return imageProjectileActuel;
    }
}
