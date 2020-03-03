package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import beans.Examen;
import beans.Matiere;
import beans.Utilisateur;
import dao.ExamenDao;
import dao.MatiereDao;
import dao.UtilisateurAExamenDao;
import dao.UtilisateurDao;

public class ChoixExamen extends JPanel {
	private JTable JtblExamen;
	private TableModele modele;
	private List<Examen> listExamen = new ArrayList();
	private List<Matiere> listMatiere = new ArrayList();
	UtilisateurDao userDao = new UtilisateurDao();
	Utilisateur userOnline;
	private ResourceBundle textBoutons;
	private ResourceBundle textLabels;
	/**
	 * Create the panel.
	 */
	public ChoixExamen() {
		//Va get quel type de user est present sur la classe
		try {
			userOnline = userDao.getById((InterfacePrincipale.getUtilisateurId()));
		} catch (Exception e1) {
			e1.printStackTrace();
		}


		//Lors d'un changement de border au niveau du JPanel, il va récuperer la langue de l'interface principale
		addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("border")) {
					getLanguage();
				}

			}
		});

		getLanguage();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JLabel lblExamenDisponible = new JLabel(textLabels.getString("lblExamenDisponible"));
		lblExamenDisponible.setName("lblExamenDisponible");
		GridBagConstraints gbc_lblExamenDisponible = new GridBagConstraints();
		gbc_lblExamenDisponible.insets = new Insets(0, 0, 5, 5);
		gbc_lblExamenDisponible.gridwidth = 9;
		gbc_lblExamenDisponible.gridx = 0;
		gbc_lblExamenDisponible.gridy = 0;
		add(lblExamenDisponible, gbc_lblExamenDisponible);

		JScrollPane ScrollPaneChoixExamen = new JScrollPane();
		GridBagConstraints gbc_ScrollPaneChoixExamen = new GridBagConstraints();
		gbc_ScrollPaneChoixExamen.insets = new Insets(0, 0, 5, 0);
		gbc_ScrollPaneChoixExamen.gridheight = 6;
		gbc_ScrollPaneChoixExamen.gridwidth = 9;
		gbc_ScrollPaneChoixExamen.fill = GridBagConstraints.BOTH;
		gbc_ScrollPaneChoixExamen.gridx = 0;
		gbc_ScrollPaneChoixExamen.gridy = 1;
		add(ScrollPaneChoixExamen, gbc_ScrollPaneChoixExamen);
		//Definie le model des JTables
		modele = new TableModele();
		JtblExamen = new JTable();
		//Permet la selection d'un seul composant du tableau
		JtblExamen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JtblExamen.setName("JtblExamen");

		ScrollPaneChoixExamen.setViewportView(JtblExamen);
		/* Dependament du type user, la classe va afficher differament
		 * Si c'est le prof, il a va aller dans creation examen en mode modification
		 * Si c'est un eleve il va entrer en mode examen donc pouvoir passer l'examen
		 */
		JButton btnCommencer = new JButton((userOnline.getTypeID() == 1)?textBoutons.getString("btnModifier"):textBoutons.getString("btnCommencer"));
		btnCommencer.setName((userOnline.getTypeID() == 1)?"btnModifier":"btnCommencer");
		btnCommencer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(userOnline.getTypeID() == 1) {
					try {
						//Prend la row selectionner et invique creation examen
						InterfacePrincipale.setExamen(listExamen.get(JtblExamen.getSelectionModel().getMaxSelectionIndex()));
						InterfacePrincipale.ChangePage(InterfacePrincipale.getClass.CreationExamen);
					}
					catch(Exception b) {
						b.printStackTrace();
						JOptionPane.showMessageDialog(new JFrame(),textLabels.getString("JPaneExamenSelection"));
					}
				}
				else 
				{
					try {
						//Si l'etudiant a deja passer l'examen il ne pourra pas donc le repasser
						UtilisateurAExamenDao utilisateurAExamenDao = new UtilisateurAExamenDao();
						Examen examen = new Examen();
						examen = listExamen.get(JtblExamen.getSelectionModel().getMaxSelectionIndex());
						Boolean aDejaPasserExamen = (utilisateurAExamenDao.getNote(examen)>1?true:false);
						if(aDejaPasserExamen) {
							JOptionPane.showMessageDialog(new JFrame(), textLabels.getString("JPaneDejaPasserExamen"));
						}
						else {
							//Prend la row selection et invoque la page exameneleve
							InterfacePrincipale.setExamen(listExamen.get(JtblExamen.getSelectionModel().getMaxSelectionIndex()));
							InterfacePrincipale.ChangePage(InterfacePrincipale.getClass.ExamenEleve);
						}

					}
					catch(Exception b) {
						JOptionPane.showMessageDialog(new JFrame(),textLabels.getString("JPaneExamenSelection"));
					}
				}
			}
		});
		//Dependament du user visiant la classe, il va retourner dans le bon portail correspondant
		JButton btnRetour = new JButton(textBoutons.getString("btnRetour"));
		btnRetour.setName("btnRetour");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(userOnline.getTypeID() == 1) {
					InterfacePrincipale.ChangePage(InterfacePrincipale.getClass.ProfesseurHome);
				}
				else {
					InterfacePrincipale.ChangePage(InterfacePrincipale.getClass.EtudiantHome);
				}
			}
		});

		GridBagConstraints gbc_btnRetour = new GridBagConstraints();
		gbc_btnRetour.fill = GridBagConstraints.BOTH;
		gbc_btnRetour.insets = new Insets(0, 0, 5, 5);
		gbc_btnRetour.gridx = 3;
		gbc_btnRetour.gridy = 7;
		add(btnRetour, gbc_btnRetour);
		GridBagConstraints gbc_btnCommencer = new GridBagConstraints();
		gbc_btnCommencer.fill = GridBagConstraints.BOTH;
		gbc_btnCommencer.insets = new Insets(0, 0, 5, 5);
		gbc_btnCommencer.gridx = 4;
		gbc_btnCommencer.gridy = 7;
		add(btnCommencer, gbc_btnCommencer);

		try {
			//Fait l'afichage des elements du tableau
			ExamenDao dao = new ExamenDao();
			MatiereDao mDao = new MatiereDao();
			listExamen = dao.getAll();
			listMatiere = mDao.getAll();
			modele.setTableModele(convert(listExamen),new String[]{textLabels.getString("jtbTitre"),textLabels.getString("jtbMatiere"),
					textLabels.getString("jtbTemps"),textLabels.getString("jtbPoint")});
			JtblExamen.setModel(modele);
			JtblExamen.revalidate();
			ScrollPaneChoixExamen.revalidate();
			updateUI();

		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Permet de convertir une liste en un tableau d'objet afin d'etre utilisé par un JTable. 
	 * @param liste
	 * @return Un tableau d'objet
	 */
	private Object[][] convert(List<Examen> liste ) {
		Object[][] tab =new Object[liste.size()][4];

		for(int i = 0; i<liste.size(); i++) {
			tab[i][0] = liste.get(i).getTitre();
			for(Matiere matiere:listMatiere) {
				if(matiere.getId()==liste.get(i).getIdMatiere()) {
					tab[i][1]=matiere.getNom();
				}
			}
			tab[i][2] = liste.get(i).getTemps();
			tab[i][3] = liste.get(i).getPointMax();
		}

		return tab;
	}
	// Fonction qui va chercher la langue utilisé dans la classe de l'InterfacePrincipale
	public void getLanguage() {
		textLabels = InterfacePrincipale.getResourceBundle(InterfacePrincipale.RESOURCE_LABEL);
		textBoutons = InterfacePrincipale.getResourceBundle(InterfacePrincipale.RESOURCE_BOUTON);
		if(textLabels==null) {
			textLabels=ResourceBundle.getBundle("properties.Labels", Locale.getDefault());
		}
		if(textBoutons==null) {
			textBoutons=ResourceBundle.getBundle("properties.Boutons", Locale.getDefault());
		}
	}
}
