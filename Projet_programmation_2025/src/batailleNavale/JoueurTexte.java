package batailleNavale;

import java.util.Scanner;

/**
 * Joueur humain qui joue via la console.
 * Gère les entrées clavier pour les attaques et affiche les résultats à l'écran.
 */
public class JoueurTexte extends JoueurAvecGrille {
    private static final int[] FLOTTE_STANDARD = {5, 4, 3, 3, 2};

    private Scanner sc;

    /**
     * Constructeur avec grille existante et nom.
     *
     * @param grille la grille navale du joueur
     * @param nom    le nom du joueur
     */
    public JoueurTexte(GrilleNavale grille, String nom) {
        super(grille, nom);
        this.sc = new Scanner(System.in);
    }

    /**
     * Constructeur avec grille existante (nom par défaut).
     *
     * @param grille la grille navale du joueur
     */
    public JoueurTexte(GrilleNavale grille) {
        this(grille, "Joueur");
    }

    /**
     * Constructeur créant une nouvelle grille avec une flotte standard.
     *
     * @param nom    le nom du joueur
     * @param taille la dimension de la grille
     */
    public JoueurTexte(String nom, int taille) {
        super(new GrilleNavale(taille, FLOTTE_STANDARD), nom);
        this.sc = new Scanner(System.in);
        
        // Place automatiquement les navires sur la grille
        this.grille.placementAuto(FLOTTE_STANDARD);
    }

    /**
     * Affiche le résultat d'une attaque effectuée par ce joueur.
     *
     * @param c    la coordonnée visée
     * @param etat le résultat du tir (TOUCHE, COULE, A_L_EAU, GAMEOVER)
     */
    @Override
    protected void retourAttaque(Coordonnee c, int etat) {
        switch (etat) {
            case TOUCHE:
                System.out.println("Touché en " + c);
                break;
            case COULE:
                System.out.println("Navire coulé en " + c + "!");
                break;
            case A_L_EAU:
                System.out.println("Tir à l'eau en " + c);
                break;
            case GAMEOVER:
                System.out.println("Vous avez gagné!");
                break;
        }
    }

    /**
     * Affiche le résultat d'une attaque subie par ce joueur (défense).
     *
     * @param c    la coordonnée visée par l'adversaire
     * @param etat le résultat du tir subi
     */
    @Override
    protected void retourDefense(Coordonnee c, int etat) {
        switch (etat) {
            case TOUCHE:
                System.out.println("Aïe! Votre bateau touché en " + c);
                break;
            case COULE:
                System.out.println("Catastrophe! Votre bateau coule en " + c);
                break;
            case A_L_EAU:
                System.out.println("Ouf! L'adversaire a tiré dans l'eau");
                break;
            case GAMEOVER:
                System.out.println("Malheureusement vous avez perdu...");
                break;
        }
    }

    /**
     * Demande à l'utilisateur de saisir une coordonnée pour attaquer.
     *
     * @return la coordonnée choisie (ex: "A5")
     */
    @Override
    public Coordonnee choixAttaque() {
        System.out.print("Attaquez (ex: A5): ");
        String input = sc.next().toUpperCase();
        return new Coordonnee(input);
    }
}