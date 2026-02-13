package batailleNavale;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Bouton personnalisé contenant une coordonnée pour la grille interactive.
 */
class JButtonCoordonnee extends JButton {
    private Coordonnee c;

    /**
     * Constructeur avec coordonnée.
     *
     * @param c la coordonnée du bouton
     */
    public JButtonCoordonnee(Coordonnee c) {
        this.c = c;
        // IMPORTANT: Autoriser la coloration des boutons
        this.setOpaque(true);
        this.setContentAreaFilled(true);
        this.setFocusPainted(false);
        this.setBorderPainted(true);
    }

    /**
     * Retourne la coordonnée associée au bouton.
     *
     * @return la coordonnée
     */
    public Coordonnee getCoordonnee() {
        return c;
    }
}

/**
 * Grille graphique interactive pour la Bataille Navale.
 * Affiche une grille de boutons cliquables et gère les interactions utilisateur.
 */
public class GrilleGraphique extends JPanel implements ActionListener {
    private static final long serialVersionUID = 8857166149660579225L;

    private JButton[][] cases;
    private Coordonnee coordonneeSelectionnee;
    private boolean[] casesJouees;  // Suivi des cases cliquées

    /**
     * Constructeur de la grille graphique.
     *
     * @param taille la taille de la grille (ex: 10 pour 10x10)
     */
    public GrilleGraphique(int taille) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        this.setLayout(new GridLayout(taille + 1, taille + 1));

        // Ajouter la première case vide (coin supérieur gauche)
        this.add(new JLabel());

        // Ajouter les en-têtes de colonnes (A, B, C, ...)
        for (int i = 0; i < taille; i++) {
            JLabel lbl = new JLabel(String.valueOf((char) ('A' + i)));
            lbl.setHorizontalAlignment(JLabel.CENTER);
            this.add(lbl);
        }

        // Initialiser le tableau de boutons
        cases = new JButton[taille][taille];
        casesJouees = new boolean[taille * taille];

        // Ajouter les en-têtes de lignes (1, 2, 3, ...) et les boutons
        for (int i = 0; i < taille; i++) {
            JLabel lbl = new JLabel(String.valueOf(i + 1));
            lbl.setHorizontalAlignment(JLabel.CENTER);
            this.add(lbl);
            for (int j = 0; j < taille; j++) {
                cases[i][j] = new JButtonCoordonnee(new Coordonnee(i, j));
                this.add(cases[i][j]);
                cases[i][j].addActionListener(this);
            }
        }
        coordonneeSelectionnee = null;
    }

    /**
     * Colorie une seule case avec la couleur spécifiée.
     *
     * @param coord la coordonnée de la case à colorier
     * @param color la couleur à appliquer
     */
    public void colorie(Coordonnee coord, Color color) {
        int ligne = coord.getLigne();
        int colonne = coord.getColonne();
        
        if (ligne >= 0 && ligne < cases.length && colonne >= 0 && colonne < cases[0].length) {
            cases[ligne][colonne].setBackground(color);
            cases[ligne][colonne].setOpaque(true);
            cases[ligne][colonne].setContentAreaFilled(true);
            int index = ligne * cases[0].length + colonne;
            casesJouees[index] = true;
        }
    }

    /**
     * Colorie une zone rectangulaire de cases avec la couleur spécifiée.
     *
     * @param debut la coordonnée de début de la zone
     * @param fin   la coordonnée de fin de la zone
     * @param color la couleur à appliquer
     */
    public void colorie(Coordonnee debut, Coordonnee fin, Color color) {
        for (int i = debut.getLigne(); i <= fin.getLigne(); i++) {
            for (int j = debut.getColonne(); j <= fin.getColonne(); j++) {
                if (i >= 0 && i < cases.length && j >= 0 && j < cases[0].length) {
                    cases[i][j].setBackground(color);
                    cases[i][j].setOpaque(true);
                    cases[i][j].setContentAreaFilled(true);
                    int index = i * cases[0].length + j;
                    casesJouees[index] = true;
                }
            }
        }
    }

    /**
     * Vérifie si une case a déjà été colorée ou jouée.
     *
     * @param coord la coordonnée à vérifier
     * @return true si la case a déjà été jouée, false sinon
     */
    public boolean estDejaColoree(Coordonnee coord) {
        int ligne = coord.getLigne();
        int colonne = coord.getColonne();
        if (ligne >= 0 && ligne < cases.length && colonne >= 0 && colonne < cases[0].length) {
            int index = ligne * cases[0].length + colonne;
            return casesJouees[index];
        }
        return false;
    }

    /**
     * Retourne la taille préférée de la grille (carré).
     *
     * @return la dimension préférée
     */
    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        d.setSize(d.width, d.width);
        return d;
    }

    /**
     * Active ou désactive les clics sur les cases non jouées.
     *
     * @param active true pour activer les clics, false pour les désactiver
     */
    public void setClicActive(boolean active) {
        SwingUtilities.invokeLater(() -> {
            for (int i = 0; i < cases.length; i++) {
                for (int j = 0; j < cases[i].length; j++) {
                    int index = i * cases[0].length + j;
                    // Désactiver les cases déjà jouées
                    if (active && casesJouees[index]) {
                        cases[i][j].setEnabled(false);
                    } else {
                        cases[i][j].setEnabled(active);
                    }
                }
            }
        });
    }

    /**
     * Gère les clics sur les boutons.
     * Ignore les clics sur les cases déjà jouées.
     *
     * @param e l'événement de clic
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButtonCoordonnee btn = (JButtonCoordonnee) e.getSource();
        Coordonnee c = btn.getCoordonnee();
        int index = c.getLigne() * cases[0].length + c.getColonne();

        // Bloquer les doubles clics
        if (casesJouees[index]) {
            System.out.println("Case déjà jouée: " + c);
            return;
        }

        // Désactiver les clics et notifier les observateurs
        this.setClicActive(false);
        coordonneeSelectionnee = c;
        synchronized (this) {
            this.notifyAll();
        }
    }

    /**
     * Retourne la coordonnée sélectionnée par l'utilisateur.
     * Bloque jusqu'à ce que l'utilisateur clique sur une case.
     *
     * @return la coordonnée sélectionnée
     */
    public synchronized Coordonnee getCoordonneeSelectionnee() {
        this.setClicActive(true);
        try {
            this.wait();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        return coordonneeSelectionnee;
    }
}