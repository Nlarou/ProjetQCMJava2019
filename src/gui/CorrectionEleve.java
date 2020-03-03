package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import beans.Examen;
import beans.ExamenAQuestion;
import beans.Question;
import beans.Utilisateur;
import dao.ExamenAQuestionDao;
import dao.UtilisateurAExamenDao;
import dao.UtilisateurDao;

public class CorrectionEleve extends JPanel {
	private JTable table;
	private ResourceBundle textBoutons;
	private ResourceBundle textLabels;
	Examen examenEnCours = new Examen();
	Utilisateur userOnline = new Utilisateur();
	private TableModele modele = new TableModele();
	UtilisateurAExamenDao ue = new UtilisateurAExamenDao();
	
	/**
	 * Create the panel.
	 */
	public CorrectionEleve() throws Exception 
	{
		//Lors d'un changement de border au niveau du JPanel, il va récuperer la langue de l'interface principale
		addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("border")) {
					getLanguage();
				}
						
			}
		});
			
		getLanguage();
		//recuperation de lexamen en cours par l intermediare de mon fonction defini dans l interface Pincipale
		try {
			UtilisateurDao userDao = new UtilisateurDao();
			examenEnCours = InterfacePrincipale.getExamenEncours();
			userOnline = userDao.getById(InterfacePrincipale.getUtilisateurId());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		Double noteFinale = (ue.getNote(examenEnCours)*100)/(examenEnCours.getPointMax());
		JLabel lblVotreNote = new JLabel(textLabels.getString("lblVotreNote") + String.valueOf(noteFinale)+ "%");
		lblVotreNote.setName("lblVotreNote");
		GridBagConstraints gbc_lblVotreNote = new GridBagConstraints();
		gbc_lblVotreNote.insets = new Insets(0, 0, 5, 5);
		gbc_lblVotreNote.gridx = 1;
		gbc_lblVotreNote.gridy = 1;
		add(lblVotreNote, gbc_lblVotreNote);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 3;
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		add(scrollPane, gbc_scrollPane);

		table = new JTable();
		table.setName("TableCorrection");
		scrollPane.setViewportView(table);

		JButton btnTerminer = new JButton(textBoutons.getString("btnTerminer"));
		btnTerminer.setName("btnTerminer");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 5;
		add(btnTerminer, gbc_btnNewButton);
		btnTerminer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InterfacePrincipale.ChangePage(InterfacePrincipale.getClass.EtudiantHome);
		}
		}); 
		
		//declaration de ExamenAQuestionDao  pour pouvoir recuperer le nombre de questions 
		ExamenAQuestionDao eq = new ExamenAQuestionDao();
		int nombreDeQuestions = eq.getNombreQuestion(examenEnCours);
		//recuperer les questions pour examen en cours
		List<Question> maListQuestion = eq.getQuestions(examenEnCours);
		List<ExamenAQuestion>  maListEq = eq.getExamenAQuestions(examenEnCours);
		//creation de mon tableau a 2 dimensions qui contiendra les donnees a afficher : l enonce de la question,  la reponse fournie par le professeur,
		//la reponse de leleve et les points obtenues sur chaque question
		Object ContenuTab[][] = new Object[nombreDeQuestions][5];
		//
			for(int j=0; j<nombreDeQuestions; j++)
			{
				//remplissement de la colonne 1 par le numero de la question adequat
				ContenuTab[j][0] = j+1;
				//remplissemnt de la colonne 2 par l ennonce de la question adequat
				ContenuTab[j][1]= maListQuestion.get(j).getQuestion();
				//remplissemnt de la colonne 3 par la reponse attendu du prof de la question adequat
				ContenuTab[j][2] = maListQuestion.get(j).getReponse();
				//remplissemnt de la colonne 4 par la reponse de leleve 
				ContenuTab[j][3] =  maListEq.get(j).getReponse_eleve();
				//remplissemnt de la colonne 5 par le nombre de point obtenu par le prof
				ContenuTab[j][4] =  maListEq.get(j).getPoint() + "/" + maListQuestion.get(j).getPointMax();
			}
			
		//definition de ma table modele
		modele.setTableModele(ContenuTab, new String[]{textLabels.getString("jtbNumeroQuestion"),textLabels.getString("jtbEnoncer"),
				textLabels.getString("jtbBonneReponse"),textLabels.getString("jtbVosReponse"),textLabels.getString("jtbPointObtenus")});
		//defintion de modele de mon Jtable qui prendra en parametre ma table definie precedemment
		table.setModel(modele);
		table.revalidate();
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
