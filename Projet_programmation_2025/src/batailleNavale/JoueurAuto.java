package batailleNavale;

import java.util.*;

/**
 * Joueur automatique avec intelligence tactique.
 * Combine recherche aléatoire avec poursuite ciblée quand un navire est touché.
 */
public class JoueurAuto extends JoueurAvecGrille {
    // Constantes pour les directions
    private static final int NORD = 0;
    private static final int EST = 1;
    private static final int SUD = 2;
    private static final int OUEST = 3;

    // Exclusions efficaces
    private Set<Coordonnee> exclues;

    // État du jeu
    private int tailleGrille;
    private Coordonnee prochainTir;

    // État de la chasse
    private int casesTouchees;
    private Coordonnee origine;
    private int direction;
    private Random random;

    /**
     * Constructeur avec grille existante.
     *
     * @param grille la grille navale
     */
    public JoueurAuto(GrilleNavale grille) {
        super(grille);
        initialiser(grille);
    }

    /**
     * Constructeur complet.
     *
     * @param grille la grille navale
     * @param nom    le nom du joueur
     */
    public JoueurAuto(GrilleNavale grille, String nom) {
        super(grille, nom);
        initialiser(grille);
    }

    /**
     * Initialise l'état du joueur automatique.
     *
     * @param grille la grille navale
     */
    private void initialiser(GrilleNavale grille) {
        this.exclues = new HashSet<>();
        this.tailleGrille = grille.getTaille();
        this.random = new Random();

        // État initial
        this.casesTouchees = 0;
        this.origine = null;
        this.direction = NORD;
        this.prochainTir = aleatoireNonExclue();
    }

    /**
     * Retourne une coordonnée aléatoire non exclue.
     * Évite les boucles infinies avec un nombre limite de tentatives.
     *
     * @return une coordonnée valide et non exclue
     */
    private Coordonnee aleatoireNonExclue() {
        int maxTentatives = 100;
        int tentatives = 0;

        while (tentatives < maxTentatives) {
            Coordonnee c = new Coordonnee(
                random.nextInt(tailleGrille),
                random.nextInt(tailleGrille)
            );
            if (!estExclue(c)) {
                return c;
            }
            tentatives++;
        }

        // Fallback : cherche systématiquement une case libre
        for (int i = 0; i < tailleGrille; i++) {
            for (int j = 0; j < tailleGrille; j++) {
                try {
                    Coordonnee c = new Coordonnee(i, j);
                    if (!estExclue(c)) {
                        return c;
                    }
                } catch (IllegalArgumentException e) {
                    // Coordonnée invalide, continuer
                }
            }
        }

        // Grille pleine (cas extrême)
        return new Coordonnee(0, 0);
    }

    /**
     * Vérifie si une coordonnée est exclue.
     *
     * @param c la coordonnée
     * @return true si exclue
     */
    private boolean estExclue(Coordonnee c) {
        return exclues.contains(c);
    }

    /**
     * Exclut une coordonnée (marquée comme tirée).
     *
     * @param c la coordonnée à exclure
     */
    private void exclure(Coordonnee c) {
        exclues.add(c);
    }

    /**
     * Change de direction (sens horaire).
     */
    private void tourne() {
        direction = (direction + 1) % 4;
    }

    /**
     * Inverse la direction (180°).
     */
    private void inverse() {
        tourne();
        tourne();
    }

    /**
     * Retourne la coordonnée voisine dans la direction actuelle.
     *
     * @param c la coordonnée de base
     * @return la coordonnée voisine, ou null si hors limites
     */
    private Coordonnee voisineSuivantDirection(Coordonnee c) {
        try {
            return switch (direction) {
                case NORD -> new Coordonnee(c.getLigne() - 1, c.getColonne());
                case EST -> new Coordonnee(c.getLigne(), c.getColonne() + 1);
                case SUD -> new Coordonnee(c.getLigne() + 1, c.getColonne());
                case OUEST -> new Coordonnee(c.getLigne(), c.getColonne() - 1);
                default -> null;
            };
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Vérifie si une coordonnée est valide et non exclue.
     *
     * @param c la coordonnée
     * @return true si valide
     */
    private boolean valideEtNonExclue(Coordonnee c) {
        if (c == null) return false;
        return c.getLigne() >= 0 && c.getLigne() < tailleGrille &&
               c.getColonne() >= 0 && c.getColonne() < tailleGrille &&
               !estExclue(c);
    }

    @Override
    public Coordonnee choixAttaque() {
        exclure(prochainTir);
        return prochainTir;
    }

    @Override
    protected void retourAttaque(Coordonnee c, int etat) {
        switch (etat) {
            case Joueur.TOUCHE:
                // Premier navire touché
                if (casesTouchees == 0) {
                    origine = c;
                    direction = NORD;
                }
                casesTouchees++;

                // Cherche la direction du navire
                prochainTir = voisineSuivantDirection(c);
                if (!valideEtNonExclue(prochainTir)) {
                    inverse();
                    prochainTir = voisineSuivantDirection(origine);
                }
                break;

            case Joueur.A_L_EAU:
                // Pas encore touché : tire aléatoirement
                if (casesTouchees == 0) {
                    prochainTir = aleatoireNonExclue();
                }
                // Première fois à l'eau après un toucher : change de direction
                else if (casesTouchees == 1) {
                    direction = NORD;
                    boolean trouve = false;
                    for (int tentative = 0; tentative < 4; tentative++) {
                        tourne();
                        prochainTir = voisineSuivantDirection(origine);
                        if (valideEtNonExclue(prochainTir)) {
                            trouve = true;
                            break;
                        }
                    }
                    if (!trouve) {
                        casesTouchees = 0;
                        origine = null;
                        prochainTir = aleatoireNonExclue();
                    }
                }
                // Navire trouvé dans une direction : inverse
                else {
                    inverse();
                    prochainTir = voisineSuivantDirection(origine);
                }
                break;

            case Joueur.COULE:
                // Navire coulé : réinitialise
                casesTouchees = 0;
                origine = null;
                direction = NORD;
                prochainTir = aleatoireNonExclue();
                break;
                
            case Joueur.GAMEOVER:
                casesTouchees = 0;
                origine = null;
                direction = NORD;
                prochainTir = null;
                break;
        }
    }

    @Override
    protected void retourDefense(Coordonnee c, int etat) {
        // Vide pour l'IA automatique
    }
}