package ca.qc.bdeb.sim.tp_pratique;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Random;

public class Partie {


    private boolean jeuTermine = false;
    private boolean jeuEstEnPause = false;
    private boolean barilEstUtilise = false;
    public static Gontrand gontrand;
    private ArrayList<PoissonEnnemi> poissonsEnnemi;
    private ArrayList<Projectile> projectiles;
    private static Image imageProjectileActuel;
    private int numeroProjectileGontrand = 0;
    private Baril baril;
    private Camera camera;
    private int numeroDeNiveau = 0;  // a changer, c est pas 1, juste pour tester (on le fait pas dans scene de Jeu, car sceneJeu est appeler une seule fois
    private static double tempsDepuisDebutNiveau = 0;
    private static double tempsDepuisMortGontrand = 0;
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
        for (PoissonEnnemi poisson : poissonsEnnemi) {
            if (gontrand.verifierCollision(poisson) && !gontrand.estInvincible()) {
                gontrand.perdreVie();

           }
        }

   }

    public void collisionAvecPoissonsEnnemis () {
        for (int i = poissonsEnnemi.size() -1; i >= 0; i--) {
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

            barilEstUtilise  = true;

            do {
                numeroProjectileGontrand = random.nextInt(3);

            } while (numeroProjectileGontrand == ancienProjectile);
            // generate un random projectiles parmi les projectiles que Gontrand n'a pas
        }
    }

    public void draw(GraphicsContext context) {

        dessinerFondEcran(context, algues);



        baril.draw(context, camera);

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

        for (Projectile projectile : projectiles) {
            projectile.draw(context, camera);
        }

        if (jeuTermine && tempsDepuisMortGontrand < 3) {

            context.setFont(Font.font("BOLD", 50));
            context.setFill(Color.DARKRED);
            context.fillText("FIN DE PARTIE", HelloApplication.w/2 - 150, (HelloApplication.h)/2);
        }

        if (jeuEstEnPause) {
            context.setFill(Color.BLACK);
            context.setFont(Font.font("BOLD", 50));
            context.fillText("PAUSE", HelloApplication.w/2 - 50, (HelloApplication.h)/2);
        }

    }

    public void update(double deltaTemps) {

        if (jeuTermine) {
            tempsDepuisMortGontrand += deltaTemps;
        }

        if (jeuEstEnPause) {
            return;   // ca arrete pas le temps, c ca la confusion A VOIR
        }

        switch (numeroProjectileGontrand)
        {
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
        }

        if (Input.isKeyPressed(KeyCode.SPACE) && tempsDepuisDernierTir >= 0.5) {    // ajuster en fonction de Input, jsp pas trop comment, alors on test pour voir mtn

            tempsDepuisDernierTir = 0;

            if (numeroProjectileGontrand == 0) {
                projectiles.add(new EtoileDeMer(gontrand.getCentre()));  // a changer 100%%%%%%%%%%
            } else if (numeroProjectileGontrand == 1 ) {
                int i = 0;
                while (i < 3){
                    i++;
                    projectiles.add(new Hippocampe(gontrand.getCentre()));
                }
            }  else if (numeroProjectileGontrand == 2) {
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

        collisionGontrandEtPoissonsEnnemis();
        collisionAvecPoissonsEnnemis();

        verifierEtatDePartie();

    }

    public void enleverObjetsHorsEcran() {
        double cameraEnX = camera.getPositionCamera().getX();
        for (int i = poissonsEnnemi.size() - 1; i >= 0; i--) {
            PoissonEnnemi poisson = poissonsEnnemi.get(i);
            if (poisson.getDroite() - cameraEnX < 0 || poisson.getBas() < 0 || poisson.getHaut() > HelloApplication.h) {
                poissonsEnnemi.remove(poisson);
            }
        }

        for (int i = projectiles.size() - 1; i >= 0; i--) {
            Projectile projectile = projectiles.get(i);
            if(projectile.getGauche() - cameraEnX > HelloApplication.w || projectile.getDroite() - cameraEnX < 0 || projectile.getBas() < 0 || projectile.getHaut() > HelloApplication.h) {
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
        tempsDepuisMortGontrand = 0;
        numeroDeNiveau++;
        algues = positionAlgues();
        couleurFond = Color.hsb(random.nextInt(190, 270), 0.84, 1.00);
        poissonsEnnemi = new ArrayList<>(numeroDeNiveau);
        projectiles = new ArrayList<>();
        numeroProjectileGontrand = numeroProjectileGontrand;
        baril = new  Baril();

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

        while (position < HelloApplication.w * 8) {
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
        context.fillRect(0, 0, HelloApplication.w, HelloApplication.h);

        for (Algue a : algues) {
            a.draw(context, camera);
        }


    }

    public Point2D champElectrique(ArrayList<PoissonEnnemi> poissons, Point2D point) {

        Point2D champElectrique = new Point2D(0,0);

        double k = 9.0*Math.pow(10,9);
        double absQPoisson = 900; //car tous les poissons ennemis ont une charge de -900

        for (PoissonEnnemi poisson : poissons) {

            Point2D posPoisson = poisson.getPosition();

            double distance = (posPoisson.distance(point));
            distance = Math.max(distance, 1); // Limite la distance à au moins 1 pixel.

            Point2D direction = posPoisson.subtract(point).normalize();

            double moduleChampE = (k*absQPoisson)/Math.pow(distance, 2);

            Point2D champUnPoisson = direction.multiply(moduleChampE);

            champElectrique = champElectrique.add(champUnPoisson);
        }


        return champElectrique;
    }
    
    public Point2D forceElectrique(ArrayList<PoissonEnnemi> poissons, Point2D point) {
        double qSardine = 900; //Car la charge d'une boite de sardine est toujours de 900

        Point2D forceE = champElectrique(poissons, point).multiply(qSardine);
        
        return forceE;
    }


    public static double getTempsDepuisDebutNiveau(){
        return tempsDepuisDebutNiveau;
    }

    public static double getTempsDepuisMortGontrand(){
        return tempsDepuisMortGontrand;
    }

    public static Image getImageProjectileActuel() {
        return imageProjectileActuel;
    }
}
