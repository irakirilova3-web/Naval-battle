package batailleNavale;

import java.awt.Color;
import javax.swing.JOptionPane;

/**
 * Joueur humain avec interface graphique.
 * Gère l'interaction utilisateur et l'affichage des tirs et défenses.
 */
public class JoueurGraphique extends JoueurAvecGrille {
    private GrilleGraphique grilleTirs;

    /**
     * Constructeur avec grille de défense, grille de tirs et nom.
     *
     * @param grilleDefense la grille contenant les navires du joueur
     * @param grilleTirs    la grille permettant de viser l'adversaire
     * @param nom           le nom du joueur
     */
    public JoueurGraphique(GrilleNavaleGraphique grilleDefense, GrilleGraphique grilleTirs, String nom) {
        super(grilleDefense, nom);
        this.grilleTirs = grilleTirs;
    }

    /**
     * Constructeur avec grille de défense et grille de tirs (nom par défaut).
     *
     * @param grilleDefense la grille contenant les navires du joueur
     * @param grilleTirs    la grille permettant de viser l'adversaire
     */
    public JoueurGraphique(GrilleNavaleGraphique grilleDefense, GrilleGraphique grilleTirs) {
        this(grilleDefense, grilleTirs, "Joueur");
    }

    /**
     * Récupère la coordonnée sélectionnée par l'utilisateur sur la grille de tirs.
     *
     * @return la coordonnée choisie
     */
    @Override
    public Coordonnee choixAttaque() {
        return grilleTirs.getCoordonneeSelectionnee();
    }

    /**
     * Affiche un message quand l'adversaire tire sur les navires du joueur.
     * La grille de défense se met à jour automatiquement via GrilleNavaleGraphique.
     *
     * @param c    la coordonnée visée
     * @param etat le résultat du tir (TOUCHE, COULE, GAMEOVER)
     */
    @Override
    protected void retourDefense(Coordonnee c, int etat) {
        switch (etat) {
            case TOUCHE:
                JOptionPane.showMessageDialog(grilleTirs, "Votre navire a été touché en " + c);
                break;
            case COULE:
                JOptionPane.showMessageDialog(grilleTirs, "Votre navire a été coulé en " + c);
                break;
            case GAMEOVER:
                JOptionPane.showMessageDialog(grilleTirs, "Vous avez perdu !");
                break;
        }
    }

    /**
     * Met à jour la grille de tirs après un tir et affiche le résultat.
     * Rouge pour les touches, bleu pour les tirs à l'eau.
     *
     * @param c    la coordonnée visée
     * @param etat le résultat (A_L_EAU, TOUCHE, COULE, GAMEOVER)
     */
    @Override
    protected void retourAttaque(Coordonnee c, int etat) {
        // Colorier immédiatement la grille de tirs
        if (etat == A_L_EAU) {
            System.out.println("Tir à l'eau en " + c);
            grilleTirs.colorie(c, Color.BLUE);
        } else if (etat == TOUCHE || etat == COULE || etat == GAMEOVER) {
            System.out.println("Touché en " + c);
            grilleTirs.colorie(c, Color.RED);
        }

        // Afficher les messages
        switch (etat) {
            case TOUCHE:
                JOptionPane.showMessageDialog(grilleTirs, "Vous avez touché un navire en " + c);
                break;
            case COULE:
                JOptionPane.showMessageDialog(grilleTirs, "Vous avez coulé un navire en " + c + " !");
                break;
            case A_L_EAU:
                // Optional: show water miss message
                break;
            case GAMEOVER:
                JOptionPane.showMessageDialog(grilleTirs, "Vous avez gagné !");
                break;
        }
    }
}