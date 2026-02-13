package batailleNavale;

import java.awt.Color;

/**
 * Grille navale avec représentation graphique.
 * Étend GrilleNavale en ajoutant une visualisation interactive.
 */
public class GrilleNavaleGraphique extends GrilleNavale {
    private static final int NB_NAVIRES_MAX = 12;
    private static final Color COULEUR_NAVIRE = Color.GREEN;
    private static final Color COULEUR_TOUCHE = Color.RED;
    private static final Color COULEUR_EAU = Color.BLUE;

    private GrilleGraphique grille;

    /**
     * Constructeur pour une grille navale graphique.
     *
     * @param taille la dimension de la grille (carrée)
     */
    public GrilleNavaleGraphique(int taille) {
        super(taille, NB_NAVIRES_MAX);
        this.grille = new GrilleGraphique(taille);
    }

    /**
     * Retourne le composant graphique de la grille.
     *
     * @return l'instance de GrilleGraphique
     */
    public GrilleGraphique getGrilleGraphique() {
        return grille;
    }

    /**
     * Ajoute un navire et le colorie en vert sur l'interface.
     *
     * @param n le navire à ajouter
     * @return true si l'ajout réussit, false sinon
     */
    @Override
    public boolean ajouteNavire(Navire n) {
        boolean ok = super.ajouteNavire(n);

        if (ok) {
            System.out.println("Navire ajouté en " + n.getDebut() + " - " + n.getFin());
            grille.colorie(n.getDebut(), n.getFin(), COULEUR_NAVIRE);
        }
        return ok;
    }

    /**
     * Enregistre un tir et met à jour l'interface graphique.
     * Rouge si touché, bleu si à l'eau.
     *
     * @param c la coordonnée visée
     * @return true si un navire est touché, false sinon
     */
    @Override
    public boolean recoitTir(Coordonnee c) {
        boolean touche = super.recoitTir(c);

        System.out.println("Tir reçu en " + c + " - Touché: " + touche);

        if (touche) {
            grille.colorie(c, COULEUR_TOUCHE);
        } else if (estALEau(c)) {
            grille.colorie(c, COULEUR_EAU);
        }

        return touche;
    }
}