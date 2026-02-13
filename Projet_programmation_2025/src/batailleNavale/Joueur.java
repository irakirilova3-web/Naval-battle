package batailleNavale;

/**
 * Classe abstraite représentant un joueur de Bataille Navale.
 * Gère le déroulement du jeu et l'alternance des tours entre deux joueurs.
 */
public abstract class Joueur {
    // États possibles après un tir
    public static final int TOUCHE = 1;
    public static final int COULE = 2;
    public static final int A_L_EAU = 3;
    public static final int GAMEOVER = 4;

    private Joueur adversaire;
    private int tailleGrille;
    private String nom;

    /**
     * Constructeur avec taille de grille et nom du joueur.
     *
     * @param tailleGrille la dimension de la grille
     * @param nom          le nom du joueur
     */
    public Joueur(int tailleGrille, String nom) {
        this.tailleGrille = tailleGrille;
        this.nom = nom;
    }

    /**
     * Constructeur avec taille de grille (nom par défaut).
     *
     * @param tailleGrille la dimension de la grille
     */
    public Joueur(int tailleGrille) {
        this(tailleGrille, "Joueur");
    }

    public int getTailleGrille() {
        return tailleGrille;
    }

    public String getNom() {
        return nom;
    }

    /**
     * Lance une partie entre ce joueur et un adversaire.
     *
     * @param adversaire l'adversaire
     */
    public void jouerAvec(Joueur adversaire) {
        this.adversaire = adversaire;
        adversaire.adversaire = this;
        deroulementJeu(this, adversaire);
    }

    /**
     * Gère la boucle principale du jeu.
     * Les joueurs alternent entre attaque et défense jusqu'à la victoire d'un d'eux.
     *
     * @param attaquant le joueur qui attaque
     * @param defenseur le joueur qui défend
     */
    private static void deroulementJeu(Joueur attaquant, Joueur defenseur) {
        int resultat = 0;

        while (resultat != GAMEOVER) {
            // L'attaquant choisit une coordonnée
            Coordonnee coordonnee = attaquant.choixAttaque();

            // Le défenseur traite le tir
            resultat = defenseur.defendre(coordonnee);

            // Mise à jour de l'affichage pour les deux joueurs
            attaquant.retourAttaque(coordonnee, resultat);
            defenseur.retourDefense(coordonnee, resultat);

            // Alternance des rôles
            Joueur temp = attaquant;
            attaquant = defenseur;
            defenseur = temp;
        }
    }

    /**
     * Appelée après une attaque pour mettre à jour l'affichage.
     *
     * @param coordonnee la coordonnée visée
     * @param etat       le résultat du tir (TOUCHE, COULE, A_L_EAU, GAMEOVER)
     */
    protected abstract void retourAttaque(Coordonnee coordonnee, int etat);

    /**
     * Appelée après une défense pour mettre à jour l'affichage.
     *
     * @param coordonnee la coordonnée visée
     * @param etat       le résultat du tir (TOUCHE, COULE, A_L_EAU, GAMEOVER)
     */
    protected abstract void retourDefense(Coordonnee coordonnee, int etat);

    /**
     * Demande au joueur de choisir une coordonnée pour tirer.
     *
     * @return la coordonnée choisie
     */
    public abstract Coordonnee choixAttaque();

    /**
     * Traite un tir adverse et retourne le résultat.
     *
     * @param coordonnee la coordonnée visée
     * @return l'état résultant (TOUCHE, COULE, A_L_EAU, ou GAMEOVER)
     */
    public abstract int defendre(Coordonnee coordonnee);
}