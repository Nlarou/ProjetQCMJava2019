package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import beans.Examen;
import beans.Question;
import dao.ExamenAQuestionDao;
import dao.QuestionDao;

public class CreationQuestions extends JPanel {
	/**
	 * Create the panel.
	 */

	private ResourceBundle textBoutons;
	private ResourceBundle textLabels;
	ArrayList<Question> maList = new ArrayList<Question>();
	public CreationQuestions() {
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
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		//creation de mes tableaus de composants


		//ligne 1 
		JLabel lblTitreQuestionnaire = new JLabel(textLabels.getString("lblTitreQuestionnaire"));
		lblTitreQuestionnaire.setName("lblTitreQuestionnaire");
		lblTitreQuestionnaire.setFont(new Font("Papyrus", Font.BOLD, 48));
		GridBagConstraints gbc_lblQuestionnaire = new GridBagConstraints();
		gbc_lblQuestionnaire.gridwidth = 13;
		gbc_lblQuestionnaire.insets = new Insets(0, 0, 5, 0);
		gbc_lblQuestionnaire.gridx = 1;
		gbc_lblQuestionnaire.gridy = 0;
		add(lblTitreQuestionnaire, gbc_lblQuestionnaire);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 9;
		gbc_scrollPane.gridwidth = 14;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		add(scrollPane, gbc_scrollPane);

		JPanel sousPanel = new JPanel();
		scrollPane.setViewportView(sousPanel);
		GridBagConstraints gbc = new GridBagConstraints();
		GridBagLayout bagLayout = new GridBagLayout();
		bagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		bagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		bagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		bagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		sousPanel.setLayout(bagLayout);
		
		
		try {
			Examen ExamenEncours  = InterfacePrincipale.getExamenEncours();
			int NbreQuestion = InterfacePrincipale.getNombreQuestionMax();
			double NbrePOint = InterfacePrincipale.getExamenEncours().getPointMax();
			boolean modificationStatut = InterfacePrincipale.getModificationExamen();
			ArrayList<Question> questions = new ArrayList<Question>();
			ExamenAQuestionDao eqDao = new ExamenAQuestionDao(); 
			
			//recuperation des question de l examen en cour pour pouvoir les afficher si jamais le professeur est en mode modification
			if(modificationStatut) 
			{
				try {
					questions = (ArrayList<Question>) eqDao.getQuestions(ExamenEncours);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
			//nombre de question initiale sans la modification faite
			int NombreDeQuestionsInitial = questions.size();
			
			//declaration de mes tableaux de mes composants graphiques utilises pour les generer en fonction du nombre de questions entree dans examen
			JLabel labelQuestionTab[] = new JLabel[NbreQuestion];
			JFormattedTextField fieldTab[] = new JFormattedTextField[NbreQuestion];
			JLabel labelreponseTab[] = new JLabel[NbreQuestion];
			JCheckBox chckbxVraiTab[] = new JCheckBox[NbreQuestion];
			JCheckBox chckbxFauxTab[] = new JCheckBox[NbreQuestion];
			ButtonGroup radioButtonGroupe[] = new ButtonGroup[NbreQuestion];
			
			//generatio de mes composants en fonction du nombre de questions de lexamen en cours
			for( int j =0; j<NbreQuestion; j++) 
			{

				labelQuestionTab[j]= new JLabel(textLabels.getString("lblQuestionTitleTab") + (j+1) + ":");
				labelQuestionTab[j].setName("lblQuestionTitleTab");
				gbc.fill = GridBagConstraints.BOTH;
				gbc.gridwidth = 2;
				gbc.gridx = 0;
				gbc.gridy = 2*j+1;
				sousPanel.add(labelQuestionTab[j], gbc);

				fieldTab[j] = new JFormattedTextField();
				gbc.gridwidth = 6;
				gbc.fill = GridBagConstraints.BOTH;
				gbc.gridx = 2;
				gbc.gridy = 2*j+1;
				sousPanel.add(fieldTab[j], gbc);

				labelreponseTab[j]  = new JLabel(textLabels.getString("lblReponseTab")+ (j+1) + ":");
				labelreponseTab[j].setName("lblReponseTab");
				gbc.fill = GridBagConstraints.BOTH;
				gbc.gridwidth = 2;
				gbc.gridx = 2;
				gbc.gridy = 2*j+2;
				sousPanel.add(labelreponseTab[j], gbc);


				chckbxVraiTab[j]= new JCheckBox(textLabels.getString("chckbxVrai"));
				chckbxVraiTab[j].setName("chckbxVrai");
				gbc.fill = GridBagConstraints.BOTH;
				gbc.gridwidth = 1;
				gbc.gridx = 4;
				gbc.gridy = 2*j+2;
				sousPanel.add(chckbxVraiTab[j], gbc);

				chckbxFauxTab[j] = new JCheckBox(textLabels.getString("chckbxFaux"));
				chckbxFauxTab[j].setName("chckbxFaux");
				gbc.gridwidth = 1;
				gbc.fill = GridBagConstraints.BOTH;
				gbc.gridx = 6;
				gbc.gridy = 2*j+2;
				sousPanel.add( chckbxFauxTab[j] , gbc);
				radioButtonGroupe[j]=new ButtonGroup();
				radioButtonGroupe[j].add(chckbxVraiTab[j]);
				radioButtonGroupe[j].add(chckbxFauxTab[j]);
			}
			//si le professeur est en mode modification qlors on coche les reponses du prof enregistre dans la bse de donnees
			if(modificationStatut) 
			{
				int i=0;
				for(Question element : questions)
				{
					fieldTab[i].setText(element.getQuestion());
					if(element.getReponse() == true)
					{
						chckbxVraiTab[i].setSelected(true);
						chckbxFauxTab[i].setSelected(false);
					}
					else 
					{
						chckbxFauxTab[i].setSelected(true);
						chckbxVraiTab[i].setSelected(false);
					}

					i++;
				}

			}
			
			//remplir tf de questions avec les questions enregistrees en BD
			Question QuestionTab[] = new Question[NombreDeQuestionsInitial];
			for(int i=0; i< NombreDeQuestionsInitial;i++)
			{
				QuestionTab[i] = questions.get(i);
			}
			JButton btnPrecedent= new JButton(textBoutons.getString("btnPrecedent"));
			btnPrecedent.setName("btnPrecedent");
			GridBagConstraints gbc_precedant = new GridBagConstraints();
			gbc_precedant.fill = GridBagConstraints.BOTH;
			btnPrecedent.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) 
				{
					InterfacePrincipale.ChangePage(InterfacePrincipale.getClass.CreationExamen);
				}
			});
			gbc_precedant.gridwidth = 2;
			gbc_precedant.insets = new Insets(50, 0, 10, 100);
			gbc_precedant.gridx = 0;
			gbc_precedant.gridy =10;
			add(btnPrecedent, gbc_precedant);

			JButton btnSuivant = new JButton(textBoutons.getString("btnSuivant"));
			GridBagConstraints gbc_suivant = new GridBagConstraints();
			btnSuivant.setName("btnSuivant");
			gbc_suivant.fill = GridBagConstraints.BOTH;
			gbc_suivant.insets = new Insets(50, 150, 10, 0);
			gbc_suivant.gridwidth = 2;
			gbc_suivant.gridx = 12;
			gbc_suivant.gridy = 10;
			add(btnSuivant, gbc_suivant);
			btnSuivant.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) 
				{
					for(int j =0; j<NbreQuestion; j++) 
					{
						//passage en parametre de mes questions dans une variable temporaire
						try {
							QuestionDao qtDao = new QuestionDao();
							ExamenAQuestionDao eqDao = new ExamenAQuestionDao();
							Question question= new Question();
							//creation de mes questions		
							question.setPointMax(NbrePOint/NbreQuestion);
							question.setQuestion(fieldTab[j].getText());
							question.setReponse(chckbxVraiTab[j].isSelected()?true:false);
							
							//si le prof est en mode modification alrs il yaura un update des  questions existant deja dans la base de donnees
							//et l ajout des nouvelle si jamasi elle existe
							if(modificationStatut)
							{
								if(j<NombreDeQuestionsInitial)
								{
									qtDao.update(QuestionTab[j], question);
								}
								else
								{
									qtDao.add(question);
									eqDao.add(ExamenEncours, qtDao.getLast());
								}

							}
							// sinon on creera normaleemnt des questions pour un nouvel examen
							else
							{
								qtDao.add(question);
								eqDao.add(ExamenEncours, qtDao.getLast());

							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					//message de confirmation dependant si on est en mode modification ou pas
					if(modificationStatut)
					{
						JOptionPane.showMessageDialog(null, textLabels.getString("JPaneModificationCreer"));
					}
					else
					{
						JOptionPane.showMessageDialog(null, textLabels.getString("JPaneExamenCreer"));
					}

					InterfacePrincipale.ChangePage(InterfacePrincipale.getClass.ProfesseurHome);
				}
			});
		}
		catch(Exception j) {
			j.printStackTrace();
		}
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
