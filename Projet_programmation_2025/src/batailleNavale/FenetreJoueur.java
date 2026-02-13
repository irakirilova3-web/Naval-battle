package batailleNavale;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;

/**
 * Fenêtre graphique pour un joueur de Bataille Navale.
 * Affiche deux grilles : une pour les tirs et une pour la défense.
 */
public class FenetreJoueur extends JFrame {
    private JPanel contentPane;
    private GrilleGraphique grilleTirs;
    private GrilleNavaleGraphique grilleDefense;

    /**
     * Constructeur par défaut avec un joueur nommé et une grille 10x10.
     */
    public FenetreJoueur() {
        this("Nom du joueur", 10);
    }

    /**
     * Constructeur avec le nom du joueur et la taille de la grille.
     *
     * @param nom    le nom du joueur à afficher dans la fenêtre
     * @param taille la taille de la grille (ex: 10 pour 10x10)
     */
    public FenetreJoueur(String nom, int taille) {
        setTitle("Bataille Navale - " + nom);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(800, 400));

        // Créer le panneau principal avec 2 colonnes
        contentPane = new JPanel(new GridLayout(1, 2, 10, 10));
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        // Grille de tirs (à gauche)
        grilleTirs = new GrilleGraphique(taille);
        grilleTirs.setBorder(BorderFactory.createTitledBorder("Grille de tirs"));
        contentPane.add(grilleTirs);

        // Grille de défense (à droite)
        grilleDefense = new GrilleNavaleGraphique(taille);
        grilleDefense.getGrilleGraphique().setBorder(BorderFactory.createTitledBorder("Grille de défense"));
        contentPane.add(grilleDefense.getGrilleGraphique());

        // Placement automatique des navires sur la grille de défense
        int[] taillesNavires = {5, 4, 3, 3, 2};
        grilleDefense.placementAuto(taillesNavires);

        // Configurer la fenêtre et l'afficher
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Retourne la grille graphique pour les tirs.
     *
     * @return la grille de tirs
     */
    public GrilleGraphique getGrilleTirs() {
        return grilleTirs;
    }

    /**
     * Retourne la grille de défense.
     *
     * @return la grille de défense
     */
    public GrilleNavaleGraphique getGrilleDefense() {
        return grilleDefense;
    }
}