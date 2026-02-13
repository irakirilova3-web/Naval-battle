package batailleNavale;

/**
 * Représente un navire sur la grille de bataille navale.
 * Gère la position, l'orientation et les impacts de tirs.
 */
public class Navire {
    private static final int LONGUEUR_MIN = 1;
    private static final int LONGUEUR_MAX = 5;

    private Coordonnee debut;
    private Coordonnee fin;
    private Coordonnee[] partiesTouchees;  // Coordonnées des parties TOUCHÉES
    private int nbTouchees;                 // Nombre de parties touchées

    /**
     * Constructeur du navire.
     *
     * @param debut       la coordonnée de départ
     * @param longueur    la longueur du navire (1-5)
     * @param estVertical true si le navire est vertical, false si horizontal
     * @throws IllegalArgumentException si les paramètres sont invalides
     */
    public Navire(Coordonnee debut, int longueur, boolean estVertical) {
        if (debut == null || longueur < LONGUEUR_MIN || longueur > LONGUEUR_MAX) {
            throw new IllegalArgumentException("Paramètres invalides");
        }

        this.debut = debut;
        this.nbTouchees = 0;

        // Calcule la coordonnée de fin
        if (estVertical) {
            this.fin = new Coordonnee(debut.getLigne() + longueur - 1, debut.getColonne());
        } else {
            this.fin = new Coordonnee(debut.getLigne(), debut.getColonne() + longueur - 1);
        }

        // Initialise le tableau pour stocker les touches (taille = longueur du navire)
        this.partiesTouchees = new Coordonnee[longueur];
    }

    public Coordonnee getDebut() {
        return debut;
    }

    public Coordonnee getFin() {
        return fin;
    }

    /**
     * Retourne une représentation textuelle du navire.
     *
     * @return chaîne de caractères descriptive
     */
    @Override
    public String toString() {
        int longueur = partiesTouchees.length;
        String orientation = (debut.getLigne() == fin.getLigne()) ? "horizontal" : "vertical";
        return "Navire(" + debut + ", " + longueur + ", " + orientation + ")";
    }

    /**
     * Vérifie si une coordonnée fait partie du navire.
     *
     * @param c la coordonnée à vérifier
     * @return true si la coordonnée est dans le navire
     */
    public boolean contient(Coordonnee c) {
        if (c == null) {
            return false;
        }

        // Horizontal : même ligne, colonne entre debut et fin
        if (debut.getLigne() == fin.getLigne()) {
            return c.getLigne() == debut.getLigne()
                    && c.getColonne() >= debut.getColonne()
                    && c.getColonne() <= fin.getColonne();
        }

        // Vertical : même colonne, ligne entre debut et fin
        return c.getColonne() == debut.getColonne()
                && c.getLigne() >= debut.getLigne()
                && c.getLigne() <= fin.getLigne();
    }

    /**
     * Vérifie si ce navire se chevauche avec un autre.
     *
     * @param n l'autre navire
     * @return true s'il y a chevauchement
     */
    public boolean chevauche(Navire n) {
        if (n == null) {
            return false;
        }

        // Vérifier si une coordonnée de ce navire est dans l'autre navire
        for (int i = debut.getLigne(); i <= fin.getLigne(); i++) {
            for (int j = debut.getColonne(); j <= fin.getColonne(); j++) {
                try {
                    Coordonnee c = new Coordonnee(i, j);
                    if (n.contient(c)) {
                        return true;
                    }
                } catch (IllegalArgumentException e) {
                    // Coordonnée invalide, continuer
                }
            }
        }
        return false;
    }

    /**
     * Vérifie si ce navire touche un autre (sans chevauchement).
     * L'adjacence diagonale ne compte pas.
     *
     * @param n l'autre navire
     * @return true si les navires sont adjacents horizontalement ou verticalement
     */
    public boolean touche(Navire n) {
        if (n == null || this.chevauche(n)) {
            return false;
        }

        // Vérifie l'adjacence orthogonale uniquement (pas diagonale)
        for (int i = debut.getLigne(); i <= fin.getLigne(); i++) {
            for (int j = debut.getColonne(); j <= fin.getColonne(); j++) {
                try {
                    Coordonnee c = new Coordonnee(i, j);
                    // Vérifier les 4 voisins orthogonaux
                    int[][] directions = {{-1,0},{1,0},{0,-1},{0,1}};
                    for (int[] d : directions) {
                        try {
                            Coordonnee voisine = new Coordonnee(i + d[0], j + d[1]);
                            if (!contient(voisine) && n.contient(voisine)) {
                                return true;
                            }
                        } catch (IllegalArgumentException e2) {
                            // Hors limites, ignorer
                        }
                    }
                } catch (IllegalArgumentException e) {
                    // Coordonnée invalide
                }
            }
        }
        return false;
    }

    /**
     * Enregistre un tir sur une coordonnée du navire.
     * La coordonnée est ajoutée aux parties touchées si nécessaire.
     *
     * @param c la coordonnée tirée
     * @return true si la coordonnée appartient au navire, false sinon
     */
    public boolean recoitTir(Coordonnee c) {
        if (!contient(c)) {
            return false;
        }

        // Vérifier si cette coordonnée a déjà été touchée
        if (estTouche(c)) {
            return true; // Déjà touché, mais le tir a réussi
        }

        // Ajouter la coordonnée aux parties touchées
        partiesTouchees[nbTouchees] = c;
        nbTouchees++;
        return true;
    }

    /**
     * Vérifie si une coordonnée spécifique du navire a été touchée.
     *
     * @param c la coordonnée à vérifier
     * @return true si cette partie du navire a été touchée
     */
    public boolean estTouche(Coordonnee c) {
        if (!contient(c)) {
            return false;
        }

        // Parcourt les parties touchées pour voir si c y est
        for (int i = 0; i < nbTouchees; i++) {
            if (partiesTouchees[i].equals(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vérifie si le navire a au moins une partie touchée.
     *
     * @return true si au moins une partie est touchée
     */
    public boolean estTouche() {
        return nbTouchees > 0;
    }

    /**
     * Vérifie si le navire est complètement coulé.
     *
     * @return true si toutes les parties sont touchées
     */
    public boolean estCoule() {
        return nbTouchees == partiesTouchees.length;
    }
}