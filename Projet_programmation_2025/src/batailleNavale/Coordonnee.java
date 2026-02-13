package batailleNavale;

/**
 * Représente une coordonnée (ligne, colonne) sur la grille de jeu.
 */
public class Coordonnee {

    private static final int MAX = 26;

    private int ligne;
    private int colonne;

    /**
     * Constructeur à partir d'indices numériques (0 à 25).
     *
     * @param ligne  l'indice de ligne (0-25)
     * @param colonne l'indice de colonne (0-25)
     * @throws IllegalArgumentException si les coordonnées sont hors grille
     */
    public Coordonnee(int ligne, int colonne) {
        if (ligne < 0 || ligne >= MAX || colonne < 0 || colonne >= MAX) {
            throw new IllegalArgumentException("Coordonnée hors grille (A-Z, 1-26)");
        }
        this.ligne = ligne;
        this.colonne = colonne;
    }

    /**
     * Constructeur à partir d'une chaîne (ex: "A1", "B10").
     *
     * @param s la chaîne de caractères au format "COLONNE+LIGNE" (ex: "A1")
     * @throws IllegalArgumentException si le format est invalide ou hors grille
     */
    public Coordonnee(String s) {
        if (s == null || s.length() < 2 || s.length() > 4) {
            throw new IllegalArgumentException("Format invalide");
        }

        char colChar = Character.toUpperCase(s.charAt(0));
        int col = colChar - 'A';
        int lig = Integer.parseInt(s.substring(1)) - 1;

        if (col < 0 || col >= MAX || lig < 0 || lig >= MAX) {
            throw new IllegalArgumentException("Coordonnée hors grille");
        }

        this.colonne = col;
        this.ligne = lig;
    }

    public int getLigne() {
        return ligne;
    }

    public int getColonne() {
        return colonne;
    }

    /**
     * Retourne la coordonnée au format lisible (ex: "A1").
     */
    @Override
    public String toString() {
        return "" + (char) (colonne + 'A') + (ligne + 1);
    }

    /**
     * Compare si deux objets Coordonnee ont les mêmes valeurs.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Coordonnee)) {
            return false;
        }
        Coordonnee other = (Coordonnee) obj;
        return ligne == other.ligne && colonne == other.colonne;
    }

    /**
     * Génère un code de hachage unique pour le stockage en collection.
     */
    @Override
    public int hashCode() {
        return 31 * ligne + colonne;
    }

    /**
     * Vérifie si la coordonnée donnée est adjacente (haut, bas, gauche, droite).
     *
     * @param c la coordonnée à vérifier
     * @return true si les coordonnées sont adjacentes
     */
    public boolean voisine(Coordonnee c) {
        return Math.abs(ligne - c.ligne) + Math.abs(colonne - c.colonne) == 1;
    }
}