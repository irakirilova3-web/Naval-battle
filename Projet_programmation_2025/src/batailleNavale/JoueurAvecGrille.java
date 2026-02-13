package batailleNavale;

/**
 * Classe abstraite pour un joueur possédant une grille navale.
 * Gère la défense contre les tirs adverses.
 */
public abstract class JoueurAvecGrille extends Joueur {
    protected GrilleNavale grille;

    /**
     * Constructeur avec grille et nom.
     *
     * @param grille la grille navale du joueur
     * @param nom    le nom du joueur
     */
    public JoueurAvecGrille(GrilleNavale grille, String nom) {
        super(grille.getTaille(), nom);
        this.grille = grille;
    }

    /**
     * Constructeur avec grille (nom par défaut).
     *
     * @param grille la grille navale du joueur
     */
    public JoueurAvecGrille(GrilleNavale grille) {
        this(grille, "Joueur");
    }

    /**
     * Traite un tir adverse et retourne le résultat.
     * Ordre de priorité :
     * 1. Vérifier si le joueur a perdu (tous les navires coulés)
     * 2. Vérifier si un navire vient de couler
     * 3. Vérifier si un navire est touché
     * 4. Sinon, le tir est à l'eau
     *
     * @param c la coordonnée visée
     * @return l'état du tir (TOUCHE, COULE, A_L_EAU, ou GAMEOVER)
     */
    @Override
    public int defendre(Coordonnee c) {
        boolean touche = grille.recoitTir(c);

        if (touche) {
            if (grille.perdu()) {
                return GAMEOVER;
            }
            if (grille.estCoule(c)) {
                return COULE;
            }
            return TOUCHE;
        }
        return A_L_EAU;
    }
}