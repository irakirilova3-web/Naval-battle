package batailleNavale;

/**
 * Représente la grille de jeu d'un joueur contenant ses navires et les tirs reçus.
 */
public class GrilleNavale {
    private static final int TAILLE_MAX = 26;
    private static final int MAX_TENTATIVES = 1000;

    private Navire[] navires;
    private int nbNavires;
    private int taille;
    private Coordonnee[] tirsRecus;
    private int nbTirsRecus;

    /**
     * Constructeur avec tableau de navires à placer manuellement.
     *
     * @param taille           dimension de la grille (1-26)
     * @param taillesNavires   tableau contenant la longueur de chaque navire
     * @throws IllegalArgumentException si la taille est invalide
     */
    public GrilleNavale(int taille, int[] taillesNavires) {
        if (taille < 1 || taille > TAILLE_MAX) {
            throw new IllegalArgumentException("Taille doit être entre 1 et " + TAILLE_MAX);
        }

        this.taille = taille;
        this.navires = new Navire[taillesNavires.length];
        this.nbNavires = 0;
        this.tirsRecus = new Coordonnee[taille * taille];
        this.nbTirsRecus = 0;
    }

    /**
     * Constructeur pour une grille vide.
     *
     * @param taille      dimension de la grille (1-26)
     * @param nbNavires   nombre maximal de navires à ajouter
     * @throws IllegalArgumentException si la taille est invalide
     */
    public GrilleNavale(int taille, int nbNavires) {
        if (taille < 1 || taille > TAILLE_MAX) {
            throw new IllegalArgumentException("Taille doit être entre 1 et " + TAILLE_MAX);
        }

        this.taille = taille;
        this.navires = new Navire[nbNavires];
        this.nbNavires = 0;
        this.tirsRecus = new Coordonnee[taille * taille];
        this.nbTirsRecus = 0;
    }

    /**
     * Affiche la grille en format texte.
     * Symboles : '.' = vide, '#' = navire, 'X' = touché, 'O' = tir à l'eau
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        // En-têtes des colonnes
        sb.append("   ");
        for (int j = 0; j < taille; j++) {
            sb.append(" ").append((char) ('A' + j));
        }
        sb.append("\n");

        // Lignes de la grille
        for (int i = 0; i < taille; i++) {
            sb.append(String.format("%2d ", i + 1));
            for (int j = 0; j < taille; j++) {
                Coordonnee c = new Coordonnee(i, j);
                sb.append(" ").append(getSymbole(c));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Détermine le symbole à afficher pour une coordonnée.
     *
     * @param c la coordonnée
     * @return le symbole correspondant
     */
    private char getSymbole(Coordonnee c) {
        // Vérifier les navires
        for (int i = 0; i < nbNavires; i++) {
            if (navires[i].estTouche(c)) {
                return 'X';
            } else if (navires[i].contient(c)) {
                return '#';
            }
        }

        // Vérifier les tirs à l'eau
        if (estDansTirsRecus(c)) {
            return 'O';
        }

        return '.';
    }

    public int getTaille() {
        return taille;
    }

    /**
     * Ajoute un navire à la grille.
     *
     * @param n le navire à ajouter
     * @return true si l'ajout réussit, false sinon
     */
    public boolean ajouteNavire(Navire n) {
        // Vérifier si le tableau est plein
        if (nbNavires >= navires.length) {
            return false;
        }

        // Vérifier si le navire est dans la grille
        if (!estDansGrille(n.getDebut()) || !estDansGrille(n.getFin())) {
            return false;
        }

        // Vérifier les collisions avec les navires existants
        for (int i = 0; i < nbNavires; i++) {
            if (navires[i].touche(n) || navires[i].chevauche(n)) {
                return false;
            }
        }

        navires[nbNavires] = n;
        nbNavires++;
        return true;
    }

    /**
     * Place aléatoirement les navires sur la grille.
     *
     * @param taillesNavires tableau des longueurs des navires à placer
     */
    public void placementAuto(int[] taillesNavires) {
        for (int taille : taillesNavires) {
            boolean place = false;
            int tentatives = 0;

            while (!place && tentatives < MAX_TENTATIVES) {
                tentatives++;
                boolean vertical = Math.random() < 0.5;

                int ligne, colonne;
                if (vertical) {
                    ligne = (int) (Math.random() * (this.taille - taille + 1));
                    colonne = (int) (Math.random() * this.taille);
                } else {
                    ligne = (int) (Math.random() * this.taille);
                    colonne = (int) (Math.random() * (this.taille - taille + 1));
                }

                Coordonnee debut = new Coordonnee(ligne, colonne);
                Navire n = new Navire(debut, taille, vertical);
                place = ajouteNavire(n);
            }
        }
    }

    /**
     * Vérifie si une coordonnée est dans la grille.
     *
     * @param c la coordonnée
     * @return true si la coordonnée est valide
     */
    private boolean estDansGrille(Coordonnee c) {
        return c.getLigne() >= 0 && c.getLigne() < taille &&
               c.getColonne() >= 0 && c.getColonne() < taille;
    }

    /**
     * Vérifie si une coordonnée a déjà été tirée.
     *
     * @param c la coordonnée
     * @return true si la coordonnée a été tirée
     */
    private boolean estDansTirsRecus(Coordonnee c) {
        for (int i = 0; i < nbTirsRecus; i++) {
            if (tirsRecus[i].equals(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Ajoute un tir à la liste des tirs reçus.
     *
     * @param c la coordonnée du tir
     * @return true si l'ajout réussit, false si le tir avait déjà été fait
     */
    private boolean ajouteDansTirsRecus(Coordonnee c) {
        if (estDansTirsRecus(c)) {
            return false;
        }
        tirsRecus[nbTirsRecus] = c;
        nbTirsRecus++;
        return true;
    }

    /**
     * Enregistre un tir reçu et vérifie s'il touche un navire.
     *
     * @param c la coordonnée du tir
     * @return true si un navire est touché, false sinon
     */
    public boolean recoitTir(Coordonnee c) {
        if (!ajouteDansTirsRecus(c)) {
            return false;
        }

        // Vérifier si un navire est touché
        for (int i = 0; i < nbNavires; i++) {
            if (navires[i].recoitTir(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vérifie si tous les navires sont coulés.
     *
     * @return true si la grille est perdue
     */
    public boolean perdu() {
        if (nbNavires == 0) {
            return false;
        }
        for (int i = 0; i < nbNavires; i++) {
            if (!navires[i].estCoule()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Vérifie si un navire contenant cette coordonnée est coulé.
     *
     * @param c la coordonnée
     * @return true si un navire coulé contient cette coordonnée
     */
    public boolean estCoule(Coordonnee c) {
        for (int i = 0; i < nbNavires; i++) {
            if (navires[i].contient(c) && navires[i].estCoule()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vérifie si une coordonnée touche un navire.
     *
     * @param c la coordonnée
     * @return true si un navire est touché à cette coordonnée
     */
    public boolean estTouche(Coordonnee c) {
        for (int i = 0; i < nbNavires; i++) {
            if (navires[i].estTouche(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vérifie si un tir est à l'eau (pas de navire touché).
     *
     * @param c la coordonnée
     * @return true si c'est un tir à l'eau
     */
    public boolean estALEau(Coordonnee c) {
        return estDansTirsRecus(c) && !estTouche(c);
    }
}