package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import beans.Examen;
import beans.Matiere;
import dao.ExamenDao;
import dao.MatiereDao;
import dao.UtilisateurAExamenDao;

public class CreationExamen extends JPanel {
	//declaration des objets odnt j aurais beoisn tout au long de ma classe
	private static List<Object> listInfo = new ArrayList();
	private JTextField tfTitre;
	private ResourceBundle textBoutons;
	private ResourceBundle textLabels;
	boolean modificationStatut = InterfacePrincipale.getModificationExamen();
	Examen examenEnCours = InterfacePrincipale.getExamenEncours();

	/**
	 * Create the panel.
	 */
	public CreationExamen() {
		
		//Lors d'un changement de border au niveau du JPanel, il va récuperer la langue de l'interface principale
		addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("border")) {
					getLanguage();
				}

			}
		});
		
		getLanguage();//recuperation de la langue
		
		//declaration de mon graphisme
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
				1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
				1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		//Conception de mon interface graphique
		JLabel lblTitreCreationExamen = new JLabel((modificationStatut)?textLabels.getString("LblModificationExamen"):textLabels.getString("LblCreationExamen"));
		lblTitreCreationExamen.setName((modificationStatut)?"LblModificationExamen":"LblCreationExamen");
		lblTitreCreationExamen.setFont(new Font("Papyrus", Font.BOLD, 29));
		GridBagConstraints gbc_lblTitre = new GridBagConstraints();
		gbc_lblTitre.gridwidth = 10;
		gbc_lblTitre.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitre.gridx = 2;
		gbc_lblTitre.gridy = 2;
		add(lblTitreCreationExamen, gbc_lblTitre);
		
		JLabel lblTitreExamen = new JLabel(textLabels.getString("lblTitreExamen"));
		lblTitreExamen.setName("lblTitreExamen");
		GridBagConstraints gbc_lblTitreExamen = new GridBagConstraints();
		gbc_lblTitreExamen.fill = GridBagConstraints.BOTH;
		gbc_lblTitreExamen.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitreExamen.gridx = 2;
		gbc_lblTitreExamen.gridy = 5;
		add(lblTitreExamen, gbc_lblTitreExamen);

		String TitreExam = (modificationStatut)?examenEnCours.getTitre(): "";
		tfTitre = new JTextField();
		tfTitre.setText(TitreExam);
		GridBagConstraints gbc_tfTitre = new GridBagConstraints();
		gbc_tfTitre.gridwidth = 8;
		gbc_tfTitre.insets = new Insets(0, 0, 5, 5);
		gbc_tfTitre.fill = GridBagConstraints.BOTH;
		gbc_tfTitre.gridx = 5;
		gbc_tfTitre.gridy = 5;
		add(tfTitre, gbc_tfTitre);
		tfTitre.setColumns(10);

		JLabel lblMatiereDispo = new JLabel(textLabels.getString("lblMatiereDispo"));
		lblMatiereDispo.setName("lblMatiereDispo");
		GridBagConstraints gbc_lblMatiereDispo = new GridBagConstraints();
		gbc_lblMatiereDispo.fill = GridBagConstraints.BOTH;
		gbc_lblMatiereDispo.insets = new Insets(0, 0, 5, 5);
		gbc_lblMatiereDispo.gridx = 2;
		gbc_lblMatiereDispo.gridy = 7;
		add(lblMatiereDispo, gbc_lblMatiereDispo);

		JComboBox cbMatiere = new JComboBox();
		GridBagConstraints gbc_cbMatiere = new GridBagConstraints();
		gbc_cbMatiere.gridwidth = 8;
		gbc_cbMatiere.insets = new Insets(0, 0, 5, 5);
		gbc_cbMatiere.fill = GridBagConstraints.BOTH;
		gbc_cbMatiere.gridx = 5;
		gbc_cbMatiere.gridy = 7;
		add(cbMatiere, gbc_cbMatiere);
		// TODO remplissage du comboBox examen par les exmaens enregistrees dans la base de donnees
		List<Matiere> listeMatiere = new ArrayList();
		try {

			MatiereDao dao = new MatiereDao();
			listeMatiere = dao.getAll();
			for (Matiere matiere : listeMatiere) {
				cbMatiere.addItem(matiere);
			}
			cbMatiere.addItem("Autre:");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//si le professeur est en mode modification d examen, la matiere de lexamen dont il modifie sera directement affiche grace a la recuperatio
		//de lexamen en cours gerer a partir de l interface principale
		if(modificationStatut)
		{
			for(Matiere x:listeMatiere) 
			{
				if(x.getId() == examenEnCours.getIdMatiere()) 
				{
					cbMatiere.setSelectedItem(x);
				}
			}

		}
		
		//interface graphique
		JLabel lblAddMatiere = new JLabel(textLabels.getString("lblAddMatiere"));
		lblAddMatiere.setName("lblAddMatiere");
		GridBagConstraints gbc_lblAddMatiere = new GridBagConstraints();
		gbc_lblAddMatiere.fill = GridBagConstraints.BOTH;
		gbc_lblAddMatiere.insets = new Insets(0, 0, 5, 5);
		gbc_lblAddMatiere.gridx = 2;
		gbc_lblAddMatiere.gridy = 9;
		add(lblAddMatiere, gbc_lblAddMatiere);

		JFormattedTextField tfMatiere = new JFormattedTextField();
		tfMatiere.setEditable(false);
		tfMatiere.setEnabled(false);
		GridBagConstraints gbc_tfMatiere = new GridBagConstraints();
		gbc_tfMatiere.gridwidth = 8;
		gbc_tfMatiere.insets = new Insets(0, 0, 5, 5);
		gbc_tfMatiere.fill = GridBagConstraints.BOTH;
		gbc_tfMatiere.gridx = 5;
		gbc_tfMatiere.gridy = 9;
		add(tfMatiere, gbc_tfMatiere);

		JLabel lblNbQuestion = new JLabel(textLabels.getString("lblNbQuestion"));
		lblNbQuestion.setName("lblNbQuestion");
		GridBagConstraints gbc_lblNbQuestion = new GridBagConstraints();
		gbc_lblNbQuestion.fill = GridBagConstraints.BOTH;
		gbc_lblNbQuestion.insets = new Insets(0, 0, 5, 5);
		gbc_lblNbQuestion.gridx = 2;
		gbc_lblNbQuestion.gridy = 11;
		add(lblNbQuestion, gbc_lblNbQuestion);

		JFormattedTextField tfNbQuestion = new JFormattedTextField();
		UtilisateurAExamenDao ue = new UtilisateurAExamenDao();
		//remplissage du textField tfNbQuestion par le nombre de questio nde lexamen en cours si le profesesur est en mode modification
		String question;
		try {
			question = (modificationStatut)?Integer.toString(ue.getNombreQuestion(examenEnCours)): "";
			tfNbQuestion.setText(question);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//interface graphqiue
		GridBagConstraints gbc_tfNbQuestion = new GridBagConstraints();
		gbc_tfNbQuestion.gridwidth = 8;
		gbc_tfNbQuestion.insets = new Insets(0, 0, 5, 5);
		gbc_tfNbQuestion.fill = GridBagConstraints.BOTH;
		gbc_tfNbQuestion.gridx = 5;
		gbc_tfNbQuestion.gridy = 11;
		add(tfNbQuestion, gbc_tfNbQuestion);
		
		JLabel lblNbPointTotal = new JLabel(textLabels.getString("lblNbPointTotal"));
		lblNbPointTotal.setName("lblNbPointTotal");
		GridBagConstraints gbc_lblNbPointTotal = new GridBagConstraints();
		gbc_lblNbPointTotal.fill = GridBagConstraints.BOTH;
		gbc_lblNbPointTotal.insets = new Insets(0, 0, 5, 5);
		gbc_lblNbPointTotal.gridx = 2;
		gbc_lblNbPointTotal.gridy = 13;
		add(lblNbPointTotal, gbc_lblNbPointTotal);

		JFormattedTextField tfNbPoint = new JFormattedTextField();
		//remplissage du textField tfNbPoint par le nombre de point de lexamen en cours si le profesesur est en mode modification
		String point = (modificationStatut)?Double.toString(examenEnCours.getPointMax()): "";
		tfNbPoint.setText(point);
		GridBagConstraints gbc_tfNbPoint = new GridBagConstraints();
		gbc_tfNbPoint.gridwidth = 8;
		gbc_tfNbPoint.insets = new Insets(0, 0, 5, 5);
		gbc_tfNbPoint.fill = GridBagConstraints.BOTH;
		gbc_tfNbPoint.gridx = 5;
		gbc_tfNbPoint.gridy = 13;
		add(tfNbPoint, gbc_tfNbPoint);
		
		JLabel lblTemps = new JLabel(textLabels.getString("lblTemps"));
		lblTemps.setName("lblTemps");
		GridBagConstraints gbc_lblTemps = new GridBagConstraints();
		gbc_lblTemps.fill = GridBagConstraints.BOTH;
		gbc_lblTemps.insets = new Insets(0, 0, 5, 5);
		gbc_lblTemps.gridx = 2;
		gbc_lblTemps.gridy = 15;
		add(lblTemps, gbc_lblTemps);

		JLabel lblHeure = new JLabel(textLabels.getString("lblHeure"));
		lblHeure.setName("lblHeure");
		GridBagConstraints gbc_lblMin = new GridBagConstraints();
		gbc_lblMin.anchor = GridBagConstraints.EAST;
		gbc_lblMin.insets = new Insets(0, 0, 5, 5);
		gbc_lblMin.gridx = 5;
		gbc_lblMin.gridy = 15;
		add(lblHeure, gbc_lblMin);
		//remplissage du textField tfHeure par le nombre d heure de lexamen en cours si le profeseur est en mode modification
		JFormattedTextField tfHeure = new JFormattedTextField();
		String heure = (modificationStatut)?Integer.toString(examenEnCours.getTemps().getHours()): "";
		tfHeure.setText(heure);
		GridBagConstraints gbc_tfHeure = new GridBagConstraints();
		gbc_tfHeure.insets = new Insets(0, 0, 5, 5);
		gbc_tfHeure.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfHeure.gridx = 6;
		gbc_tfHeure.gridy = 15;
		add(tfHeure, gbc_tfHeure);
		
		JLabel lblMin = new JLabel(textLabels.getString("lblMin"));
		lblMin.setName("lblMin");
		GridBagConstraints gbc_lblMin_1 = new GridBagConstraints();
		gbc_lblMin_1.anchor = GridBagConstraints.EAST;
		gbc_lblMin_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblMin_1.gridx = 8;
		gbc_lblMin_1.gridy = 15;
		add(lblMin, gbc_lblMin_1);
		
		//remplissage du textField tfMinute par le nombre de minute de lexamen en cours si le profesesur est en mode modification
		JFormattedTextField tfMinute = new JFormattedTextField();
		String minute = (modificationStatut)?Integer.toString(examenEnCours.getTemps().getMinutes()): "";
		tfMinute.setText(minute);
		GridBagConstraints gbc_tfMinute = new GridBagConstraints();
		gbc_tfMinute.insets = new Insets(0, 0, 5, 5);
		gbc_tfMinute.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfMinute.gridx = 9;
		gbc_tfMinute.gridy = 15;
		add(tfMinute, gbc_tfMinute);

		JLabel lblS = new JLabel(textLabels.getString("lblS"));
		lblS.setName("lblS");
		GridBagConstraints gbc_lblS = new GridBagConstraints();
		gbc_lblS.anchor = GridBagConstraints.EAST;
		gbc_lblS.insets = new Insets(0, 0, 5, 5);
		gbc_lblS.gridx = 11;
		gbc_lblS.gridy = 15;
		add(lblS, gbc_lblS);
		
		//remplissage du textField tfSeconde par le nombre de seconde de lexamen en cours si le profesesur est en mode modification
		JFormattedTextField tfSeconde = new JFormattedTextField();
		String seconde = (modificationStatut)?Integer.toString(examenEnCours.getTemps().getSeconds()): "";
		tfSeconde.setText(seconde);
		GridBagConstraints gbc_tfSeconde = new GridBagConstraints();
		gbc_tfSeconde.insets = new Insets(0, 0, 5, 5);
		gbc_tfSeconde.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfSeconde.gridx = 12;
		gbc_tfSeconde.gridy = 15;
		add(tfSeconde, gbc_tfSeconde);

		JButton btnAnnuler = new JButton(textBoutons.getString("btnAnnuler"));
        btnAnnuler.setName("btnAnnuler");
        btnAnnuler.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                InterfacePrincipale.ChangePage(InterfacePrincipale.getClass.ProfesseurHome);
                InterfacePrincipale.setModificationExamen(false);
            }
        });
		GridBagConstraints gbc_btnAnuuler = new GridBagConstraints();
		gbc_btnAnuuler.gridwidth = 4;
		gbc_btnAnuuler.fill = GridBagConstraints.BOTH;
		gbc_btnAnuuler.insets = new Insets(0, 0, 5, 5);
		gbc_btnAnuuler.gridx = 2;
		gbc_btnAnuuler.gridy = 18;
		add(btnAnnuler, gbc_btnAnuuler);

		JButton btnSuivant = new JButton(textBoutons.getString("btnSuivant"));
		btnSuivant.setName("btnSuivant");
		btnSuivant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ExamenDao examDao = new ExamenDao();
					MatiereDao matDao = new MatiereDao();
					Examen exam = new Examen();
					Matiere matiere = new Matiere();
					//passage de parametre de mon objet Examen temporaire que jai nomme exam
					exam.setTitre(tfTitre.getText());
					InterfacePrincipale.setNombreQuestionMax(Integer.parseInt(tfNbQuestion.getText()));
					exam.setPointMax(Double.parseDouble(tfNbPoint.getText()));
					Time time = new Time(Integer.parseInt(tfHeure.getText()),Integer.parseInt(tfMinute.getText()),Integer.parseInt(tfSeconde.getText()));;
					exam.setTemps(time);
					//si la matiere est desactivee, prendre la matiere contenu dans le textBox, puis la jouter dans la base de donnees comme nouvelle
					//matiere et mettre comme matiere de l examen, la derniere matiere cree
					if(tfMatiere.isEnabled()) 
					{ 
						matiere.setNom(tfMatiere.getText()); 
						matDao.add(matiere);
						matiere= matDao.getLast();
					} 
					
					//si le combobox matiere nest pas desactivee, mette comme matiere de lexamen le contenu selectionne de comboBox
					else{
						matiere= ((Matiere)cbMatiere.getSelectedItem()); 	
					}
					//passage en parametre de la matiere a l examen
					exam.setIdMatiere(matiere.getId());
					//si le professeur est en mode modification alors on fera un update sur l examen en cours  avec els parametres de notre examen temporaire cree plus haut
					if(modificationStatut)
					{
						examenEnCours.setIdMatiere(exam.getIdMatiere());
						examenEnCours.setPointMax(exam.getPointMax());
						examenEnCours.setTemps(exam.getTemps());
						examenEnCours.setTitre(exam.getTitre());
						examDao.update(examenEnCours);
					}
					//sinon on ajouter tout simplement l examen temporaire comme nouvel examen dans notre base de donnnee
					else
					{
						examDao.add(exam);
						InterfacePrincipale.setExamen(examDao.getLast());
					}

					//on affichera un JoptionPane pour verifier valider la premier partie de la creation examen et ensuite on sera tranferer dans la page 
					//des questions
					
					JOptionPane.showMessageDialog(null, textLabels.getString("JPanePartiUn"));
					InterfacePrincipale.ChangePage(InterfacePrincipale.getClass.CreationQuestions);

				} catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnSuivant = new GridBagConstraints();
		gbc_btnSuivant.fill = GridBagConstraints.BOTH;
		gbc_btnSuivant.gridwidth = 4;
		gbc_btnSuivant.insets = new Insets(0, 0, 5, 5);
		gbc_btnSuivant.gridx = 8;
		gbc_btnSuivant.gridy = 18;
		add(btnSuivant, gbc_btnSuivant);
		
		//fonction permettant enable le textfield matiere uniquement si l option autre est selectionne dans le comboBox
		cbMatiere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cbMatiere.getSelectedItem() == "Autre:") {
					tfMatiere.setEditable(true);
					tfMatiere.setEnabled(true);
				} else {
					tfMatiere.setEditable(false);
					tfMatiere.setEnabled(false);
				}
			}
		});
	}

	public static List getInformation() {
		return listInfo;
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
