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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import beans.Examen;
import beans.Matiere;
import beans.UtilisateurAExamen;
import dao.ExamenDao;
import dao.MatiereDao;
import dao.UtilisateurAExamenDao;

public class EtudiantNote extends JPanel {
	private JTable jtbResultatExamen;
	private TableModele modele;
	private List<UtilisateurAExamen> listeUtilisateurAExamen = new ArrayList();
	private List<Matiere> listeMatiere = new ArrayList();
	private List<Examen> listeExamen = new ArrayList();
	private List<Double> notes = new ArrayList();
	private ResourceBundle textBoutons;
	private ResourceBundle textLabels;

	/**
	 * Create the panel.
	 */
	public EtudiantNote() {
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
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JLabel lblVosNotes = new JLabel(textLabels.getString("lblVosNotes"));
		lblVosNotes.setName("lblVosNotes");
		GridBagConstraints gbc_lblVosNotes = new GridBagConstraints();
		gbc_lblVosNotes.gridwidth = 9;
		gbc_lblVosNotes.insets = new Insets(0, 0, 5, 5);
		gbc_lblVosNotes.gridx = 0;
		gbc_lblVosNotes.gridy = 0;
		add(lblVosNotes, gbc_lblVosNotes);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridheight = 7;
		gbc_scrollPane.gridwidth = 9;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		add(scrollPane, gbc_scrollPane);
		//Initialise le model
		modele = new TableModele();
		jtbResultatExamen = new JTable();
		jtbResultatExamen.setName("jtbResultatExamen");
		scrollPane.setViewportView(jtbResultatExamen);
		//Redirige vers le portail etudiant
		JButton btnPortail = new JButton(textBoutons.getString("btnPortail"));
		btnPortail.setName("btnPortail");
		btnPortail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InterfacePrincipale.ChangePage(InterfacePrincipale.getClass.EtudiantHome);
			}
		});
		GridBagConstraints gbc_btnPortail = new GridBagConstraints();
		gbc_btnPortail.fill = GridBagConstraints.BOTH;
		gbc_btnPortail.gridwidth = 2;
		gbc_btnPortail.insets = new Insets(0, 0, 0, 5);
		gbc_btnPortail.gridx = 4;
		gbc_btnPortail.gridy = 8;
		add(btnPortail, gbc_btnPortail);
		try {
			//Genere le tableau a partir des donnees de l'utilisateur
			UtilisateurAExamenDao uAEDao = new UtilisateurAExamenDao();
			ExamenDao examenDao = new ExamenDao();
			MatiereDao matiereDao = new MatiereDao();
			listeUtilisateurAExamen = uAEDao.getByUserId(InterfacePrincipale.getUtilisateurId());
			listeExamen=examenDao.getAll();
			listeMatiere = matiereDao.getAll();

			modele.setTableModele(convert(listeExamen,listeMatiere,listeUtilisateurAExamen,uAEDao),new String[]{textLabels.getString("jtbTitre"),textLabels.getString("jtbMatiere"),textLabels.getString("jtbNote")});

			jtbResultatExamen.setModel(modele);
			jtbResultatExamen.revalidate();
			scrollPane.revalidate();
			updateUI();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**Permet de convertir une liste en un tableau d'objet afin d'etre utilisé par un JTable. 
	 * @param listeExamen
	 * @param listedeMatiere
	 * @param listeUAE
	 * @param dao
	 * @return Tableau d'object
	 * @throws Exception
	 */
	private Object[][] convert(List<Examen> listeExamen,List<Matiere> listedeMatiere,List<UtilisateurAExamen> listeUAE,UtilisateurAExamenDao dao ) throws Exception {
		Object[][] tab =new Object[listeUAE.size()][3];
		//Parcour la listeUtilisateurAExamen afin de recuperer les informations de l'examen ainsi que sa matiere
		for(int i = 0; i<listeUAE.size(); i++) {
			for(Examen exam:listeExamen) {
				if(exam.getId()==listeUAE.get(i).getIdExamen()) {
					tab[i][0] = exam.getTitre();
					tab[i][2] = dao.getNote(exam)+"/"+exam.getPointMax();
					for(Matiere matiere:listedeMatiere) {
						if(matiere.getId()==exam.getIdMatiere()) {
							tab[i][1]=matiere.getNom();
						}
					}
				}
			}
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
