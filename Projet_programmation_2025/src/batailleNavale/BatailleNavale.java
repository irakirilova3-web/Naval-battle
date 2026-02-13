package batailleNavale;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class BatailleNavale {

    private JFrame frame;
    private JTextField tfTaille;
    private JLabel lblTaille;

    private JPanel panelJoueur1;
    private JLabel lblNom1;
    private JTextField tfNomJoueur1;
    private JRadioButton j1Graphique, j1Texte, j1Auto;

    private JPanel panelJoueur2;
    private JLabel lblNom2;
    private JTextField tfNomJoueur2;
    private JRadioButton j2Graphique, j2Texte, j2Auto;

    private JButton btnLancer;

    private Joueur joueur1, joueur2;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                BatailleNavale window = new BatailleNavale();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public BatailleNavale() {
        initialize();
    }

    /**
     * Initialise tous les composants de l'interface graphique.
     */
    private void initialize() {
        frame = new JFrame("Bataille Navale");
        frame.setBounds(100, 100, 520, 460);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setLayout(new GridBagLayout());

        // ---------- TAILLE ----------
        lblTaille = new JLabel("Taille de grille :");
        GridBagConstraints gbc_lblTaille = new GridBagConstraints();
        gbc_lblTaille.insets = new Insets(8, 8, 5, 5);
        gbc_lblTaille.anchor = GridBagConstraints.WEST;
        gbc_lblTaille.gridx = 0;
        gbc_lblTaille.gridy = 0;
        frame.getContentPane().add(lblTaille, gbc_lblTaille);

        tfTaille = new JTextField();
        tfTaille.setText("10");
        GridBagConstraints gbc_tfTaille = new GridBagConstraints();
        gbc_tfTaille.insets = new Insets(8, 0, 5, 8);
        gbc_tfTaille.fill = GridBagConstraints.HORIZONTAL;
        gbc_tfTaille.gridx = 1;
        gbc_tfTaille.gridy = 0;
        frame.getContentPane().add(tfTaille, gbc_tfTaille);
        tfTaille.setColumns(10);

        // ---------- JOUEUR 1 ----------
        panelJoueur1 = new JPanel();
        panelJoueur1.setBorder(new TitledBorder(null, "Joueur 1", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        GridBagConstraints gbc_panelJoueur1 = new GridBagConstraints();
        gbc_panelJoueur1.insets = new Insets(0, 8, 5, 8);
        gbc_panelJoueur1.fill = GridBagConstraints.BOTH;
        gbc_panelJoueur1.gridwidth = 2;
        gbc_panelJoueur1.gridx = 0;
        gbc_panelJoueur1.gridy = 1;
        frame.getContentPane().add(panelJoueur1, gbc_panelJoueur1);

        GridBagLayout gbl_panelJoueur1 = new GridBagLayout();
        gbl_panelJoueur1.columnWidths = new int[]{0, 0, 0};
        gbl_panelJoueur1.rowHeights = new int[]{0, 0, 0, 0};
        gbl_panelJoueur1.columnWeights = new double[]{0.0, 1.0};
        gbl_panelJoueur1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
        panelJoueur1.setLayout(gbl_panelJoueur1);

        lblNom1 = new JLabel("Nom :");
        GridBagConstraints gbc_lblNom1 = new GridBagConstraints();
        gbc_lblNom1.insets = new Insets(0, 0, 5, 5);
        gbc_lblNom1.anchor = GridBagConstraints.EAST;
        gbc_lblNom1.gridx = 0;
        gbc_lblNom1.gridy = 0;
        panelJoueur1.add(lblNom1, gbc_lblNom1);

        tfNomJoueur1 = new JTextField("Joueur 1");
        GridBagConstraints gbc_tfNom1 = new GridBagConstraints();
        gbc_tfNom1.insets = new Insets(0, 0, 5, 0);
        gbc_tfNom1.fill = GridBagConstraints.HORIZONTAL;
        gbc_tfNom1.gridx = 1;
        gbc_tfNom1.gridy = 0;
        panelJoueur1.add(tfNomJoueur1, gbc_tfNom1);
        tfNomJoueur1.setColumns(10);

        // Création des boutons radio pour le choix du type de joueur
        j1Graphique = new JRadioButton("Joueur Graphique");
        j1Texte = new JRadioButton("Joueur Texte");
        j1Auto = new JRadioButton("Joueur Auto");
        j1Graphique.setSelected(true);

        // Grouper les boutons radio pour en sélectionner qu'un
        ButtonGroup group1 = new ButtonGroup();
        group1.add(j1Graphique);
        group1.add(j1Texte);
        group1.add(j1Auto);

        // Ajouter les boutons radio au panneau
        GridBagConstraints gbc_j1Graphique = new GridBagConstraints();
        gbc_j1Graphique.gridx = 0;
        gbc_j1Graphique.gridy = 1;
        gbc_j1Graphique.gridwidth = 2;
        gbc_j1Graphique.anchor = GridBagConstraints.WEST;
        panelJoueur1.add(j1Graphique, gbc_j1Graphique);

        GridBagConstraints gbc_j1Texte = new GridBagConstraints();
        gbc_j1Texte.gridx = 0;
        gbc_j1Texte.gridy = 2;
        gbc_j1Texte.gridwidth = 2;
        gbc_j1Texte.anchor = GridBagConstraints.WEST;
        panelJoueur1.add(j1Texte, gbc_j1Texte);

        GridBagConstraints gbc_j1Auto = new GridBagConstraints();
        gbc_j1Auto.gridx = 0;
        gbc_j1Auto.gridy = 3;
        gbc_j1Auto.gridwidth = 2;
        gbc_j1Auto.anchor = GridBagConstraints.WEST;
        panelJoueur1.add(j1Auto, gbc_j1Auto);

        // ---------- JOUEUR 2 ----------
        panelJoueur2 = new JPanel();
        panelJoueur2.setBorder(new TitledBorder(null, "Joueur 2", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        GridBagConstraints gbc_panelJoueur2 = new GridBagConstraints();
        gbc_panelJoueur2.insets = new Insets(0, 8, 5, 8);
        gbc_panelJoueur2.fill = GridBagConstraints.BOTH;
        gbc_panelJoueur2.gridwidth = 2;
        gbc_panelJoueur2.gridx = 0;
        gbc_panelJoueur2.gridy = 2;
        frame.getContentPane().add(panelJoueur2, gbc_panelJoueur2);

        GridBagLayout gbl_panelJoueur2 = new GridBagLayout();
        gbl_panelJoueur2.columnWidths = new int[]{0, 0, 0};
        gbl_panelJoueur2.rowHeights = new int[]{0, 0, 0, 0};
        gbl_panelJoueur2.columnWeights = new double[]{0.0, 1.0};
        gbl_panelJoueur2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
        panelJoueur2.setLayout(gbl_panelJoueur2);

        lblNom2 = new JLabel("Nom :");
        GridBagConstraints gbc_lblNom2 = new GridBagConstraints();
        gbc_lblNom2.insets = new Insets(0, 0, 5, 5);
        gbc_lblNom2.anchor = GridBagConstraints.EAST;
        gbc_lblNom2.gridx = 0;
        gbc_lblNom2.gridy = 0;
        panelJoueur2.add(lblNom2, gbc_lblNom2);

        tfNomJoueur2 = new JTextField("Joueur 2");
        GridBagConstraints gbc_tfNom2 = new GridBagConstraints();
        gbc_tfNom2.insets = new Insets(0, 0, 5, 0);
        gbc_tfNom2.fill = GridBagConstraints.HORIZONTAL;
        gbc_tfNom2.gridx = 1;
        gbc_tfNom2.gridy = 0;
        panelJoueur2.add(tfNomJoueur2, gbc_tfNom2);
        tfNomJoueur2.setColumns(10);

        // Création des boutons radio pour le choix du type de joueur
        j2Graphique = new JRadioButton("Joueur Graphique");
        j2Texte = new JRadioButton("Joueur Texte");
        j2Auto = new JRadioButton("Joueur Auto");
        j2Graphique.setSelected(true);

        // Grouper les boutons radio pour en sélectionner qu'un
        ButtonGroup group2 = new ButtonGroup();
        group2.add(j2Graphique);
        group2.add(j2Texte);
        group2.add(j2Auto);

        // Ajouter les boutons radio au panneau
        GridBagConstraints gbc_j2Graphique = new GridBagConstraints();
        gbc_j2Graphique.gridx = 0;
        gbc_j2Graphique.gridy = 1;
        gbc_j2Graphique.gridwidth = 2;
        gbc_j2Graphique.anchor = GridBagConstraints.WEST;
        panelJoueur2.add(j2Graphique, gbc_j2Graphique);

        GridBagConstraints gbc_j2Texte = new GridBagConstraints();
        gbc_j2Texte.gridx = 0;
        gbc_j2Texte.gridy = 2;
        gbc_j2Texte.gridwidth = 2;
        gbc_j2Texte.anchor = GridBagConstraints.WEST;
        panelJoueur2.add(j2Texte, gbc_j2Texte);

        GridBagConstraints gbc_j2Auto = new GridBagConstraints();
        gbc_j2Auto.gridx = 0;
        gbc_j2Auto.gridy = 3;
        gbc_j2Auto.gridwidth = 2;
        gbc_j2Auto.anchor = GridBagConstraints.WEST;
        panelJoueur2.add(j2Auto, gbc_j2Auto);

        // ---------- BOUTON ----------
        btnLancer = new JButton("Lancer la partie");
        GridBagConstraints gbc_btnLancer = new GridBagConstraints();
        gbc_btnLancer.insets = new Insets(0, 0, 10, 0);
        gbc_btnLancer.gridwidth = 2;
        gbc_btnLancer.gridx = 0;
        gbc_btnLancer.gridy = 3;
        frame.getContentPane().add(btnLancer, gbc_btnLancer);

        // Ajouter un écouteur pour lancer la partie
        btnLancer.addActionListener(e -> demarrerPartie());
    }

    /**
     * Démarre la partie avec les paramètres configurés.
     * Lance les joueurs dans un thread séparé.
     */
    private void demarrerPartie() {
        try {
            int taille = Integer.parseInt(tfTaille.getText());
            joueur1 = creerJoueur(tfNomJoueur1.getText(), taille, j1Graphique.isSelected(), j1Texte.isSelected());
            joueur2 = creerJoueur(tfNomJoueur2.getText(), taille, j2Graphique.isSelected(), j2Texte.isSelected());

            // Lancer le jeu dans un thread séparé pour ne pas bloquer l'interface
            new Thread(() -> joueur1.jouerAvec(joueur2)).start();

        } catch (NumberFormatException e) {
            System.err.println("Erreur: La taille doit être un nombre.");
        }
    }

    /**
     * Crée un joueur en fonction des paramètres fournis.
     *
     * @param nom       le nom du joueur
     * @param taille    la taille de la grille
     * @param graphique true si le joueur est graphique
     * @param texte     true si le joueur est en mode texte
     * @return un objet Joueur approprié
     */
    private Joueur creerJoueur(String nom, int taille, boolean graphique, boolean texte) {
        // Flotte standard identique pour tous les joueurs
        int[] taillesNavires = {5, 4, 3, 3, 2};

        if (graphique) {
            // Créer une fenêtre graphique pour le joueur
            FenetreJoueur fen = new FenetreJoueur(nom, taille);
            fen.setVisible(true);
            return new JoueurGraphique(fen.getGrilleDefense(), fen.getGrilleTirs(), nom);
        }

        // Créer les grilles de défense et de tir
        // Créer les grilles de défense et de tir
        GrilleNavale grilleDefense = new GrilleNavale(taille, taillesNavires);
        grilleDefense.placementAuto(taillesNavires);  // <-- AJOUT CRUCIAL

        if (texte) {
            return new JoueurTexte(grilleDefense, nom);
        }
        // Mode automatique par défaut
        return new JoueurAuto(grilleDefense, nom);
    }
}