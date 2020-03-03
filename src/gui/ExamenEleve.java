package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import beans.Examen;
import beans.ExamenAQuestion;
import beans.Question;
import beans.Utilisateur;
import dao.ExamenAQuestionDao;
import dao.QuestionDao;
import dao.UtilisateurAExamenDao;

public class ExamenEleve extends JPanel {
	private List<ExamenAQuestion> listeExamenQuestion = new ArrayList();
	private ResourceBundle textBoutons;
	private ResourceBundle textLabels;
	private int NombreQuestion = 0;
	private boolean startTimer = false;
	//declaration de mon timer.
	public Timer timer = new Timer();
	private JLabel lblTimer;
	private JButton btnSoumettre;
	private List<Question> listeQuestions = new ArrayList();
	//declaration de mes donnees de temps

	private int secondes = InterfacePrincipale.getExamenEncours().getTemps().getSeconds();
	private int minutes= InterfacePrincipale.getExamenEncours().getTemps().getMinutes();
	private int heures=InterfacePrincipale.getExamenEncours().getTemps().getHours();

	/**
	 * Create the panel.
	 */
	public ExamenEleve() {
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



		JLabel lblQuestionnaire = new JLabel(textLabels.getString("lblQuestionnaire"));
		lblQuestionnaire.setName("lblQuestionnaire");
		GridBagConstraints gbc_lblQuestionnaire = new GridBagConstraints();
		gbc_lblQuestionnaire.gridwidth = 13;
		gbc_lblQuestionnaire.insets = new Insets(0, 0, 5, 0);
		gbc_lblQuestionnaire.gridx = 1;
		gbc_lblQuestionnaire.gridy = 0;
		add(lblQuestionnaire, gbc_lblQuestionnaire);

		JLabel lblTempsRestant = new JLabel(textLabels.getString("lblTempsRestant"));
		lblTempsRestant.setName("lblTempsRestant");
		GridBagConstraints gbc_lblTempsRestant = new GridBagConstraints();
		gbc_lblTempsRestant.insets = new Insets(0, 0, 5, 5);
		gbc_lblTempsRestant.gridx = 2;
		gbc_lblTempsRestant.gridy = 1;
		add(lblTempsRestant, gbc_lblTempsRestant);

		lblTimer = new JLabel("");
		GridBagConstraints gbc_lblTimer = new GridBagConstraints();
		gbc_lblTimer.gridwidth = 2;
		gbc_lblTimer.anchor = GridBagConstraints.WEST;
		gbc_lblTimer.insets = new Insets(0, 0, 5, 5);
		gbc_lblTimer.gridx = 3;
		gbc_lblTimer.gridy = 1;
		add(lblTimer, gbc_lblTimer);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 14;
		gbc_scrollPane.gridheight = 7;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
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
			//Va chercher l'examen en question ainsi que les questions relier
			ExamenAQuestionDao eAQDao = new ExamenAQuestionDao();
			Examen examen = InterfacePrincipale.getExamenEncours();
			lblTimer.setText(examen.getTemps().toString());
			QuestionDao questionDao = new QuestionDao();
			NombreQuestion = eAQDao.getNombreQuestion(examen);
			listeExamenQuestion=eAQDao.getAll();

			JLabel lblQuestionTitleTab[] = new JLabel[NombreQuestion];
			JLabel lblQuestionAskTab[] = new JLabel[NombreQuestion];
			JLabel lblReponseTab[] = new JLabel[NombreQuestion];
			JCheckBox chckbxVraiTab[] = new JCheckBox[NombreQuestion];
			JCheckBox chckbxFauxTab[] = new JCheckBox[NombreQuestion];
			ButtonGroup radioButtonGroupe[] = new ButtonGroup[NombreQuestion];
			
			//Genere dynamiquement les questions et les remplits avec les informations sur la base de donnees
			List<Question>questionPresente = new ArrayList(NombreQuestion);
			for( int j =0; j<NombreQuestion; j++) 
			{
				//Fait le mapping des questions afin de ne retomber dessus plustard
				Question question = null;
				for(ExamenAQuestion examenQuestion : listeExamenQuestion) {
					if(examenQuestion.getIdExamen()==examen.getId()) {
						question = questionDao.getById(examenQuestion.getIdQuestion());
						questionPresente.add(question);
					}
				}

				lblQuestionTitleTab[j]= new JLabel(textLabels.getString("lblQuestionTitleTab") + (j+1) + ":");
				lblQuestionTitleTab[j].setName("lblQuestionTitleTab");
				gbc.fill = GridBagConstraints.BOTH;
				gbc.gridwidth = 2;
				gbc.gridx = 0;
				gbc.gridy = 2*j+1;
				sousPanel.add(lblQuestionTitleTab[j], gbc);

				lblQuestionAskTab[j] = new JLabel(questionPresente.get(j).getQuestion());
				gbc.gridwidth = 6;
				gbc.fill = GridBagConstraints.BOTH;
				gbc.gridx = 2;
				gbc.gridy = 2*j+1;
				sousPanel.add(lblQuestionAskTab[j], gbc);

				lblReponseTab[j]  = new JLabel(textLabels.getString("lblReponseTab")+ (j+1) + ":");
				lblReponseTab[j].setName("lblReponseTab");
				gbc.fill = GridBagConstraints.BOTH;
				gbc.gridwidth = 2;
				gbc.gridx = 2;
				gbc.gridy = 2*j+2;
				sousPanel.add(lblReponseTab[j], gbc);

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
			questionPresente.clear();
			scrollPane.setViewportView(sousPanel);
			scrollPane.revalidate();
			/*
			 * Recupere les valeurs de tout les radiosbuttons et compare avec la base donnes afin de donner la note a l'utilisateur
			 */
			btnSoumettre = new JButton(textBoutons.getString("btnSoumettre"));
			btnSoumettre.setName("btnSoumettre");
			btnSoumettre.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Examen examen = InterfacePrincipale.getExamenEncours();
					ExamenAQuestionDao examenAQuestionDao = new ExamenAQuestionDao();
					UtilisateurAExamenDao utiliAExamDao = new UtilisateurAExamenDao();
					Utilisateur user = new Utilisateur();
					try {
						//Recupere tout les questions
						listeQuestions = examenAQuestionDao.getQuestions(examen);
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
					for( int j =0; j<NombreQuestion; j++) {
						try {
							//Fait la comparaison et ajoute les informations des questions sur la base de donnee
							boolean reponse_eleve = (chckbxVraiTab[j].isSelected())?true:false;
							System.out.println("Question" + j+" reponse_eleve" + reponse_eleve);
							boolean reponse_prof = listeQuestions.get(j).getReponse();
							double point_eleve= (reponse_eleve==reponse_prof)?listeQuestions.get(j).getPointMax():0;
							user.setId(InterfacePrincipale.getUtilisateurId());
							examenAQuestionDao.setPoint(examen, listeQuestions.get(j),point_eleve);
							examenAQuestionDao.setReponseEleve(examen, listeQuestions.get(j), reponse_eleve);
						}
						catch(Exception b) {
							b.printStackTrace();
						}

					}

					try {
						//Attribut la note final a l'utilisateur
						double note = utiliAExamDao.getNote(examen);
						utiliAExamDao.add(user, examen);
						utiliAExamDao.setNote(note, user, examen);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					//Redirige sur la page de Correction d'examen
					InterfacePrincipale.ChangePage(InterfacePrincipale.getClass.CorrectionEleve);
				}
			});

			GridBagConstraints gbc_btnSoumettre = new GridBagConstraints();
			gbc_btnSoumettre.fill = GridBagConstraints.BOTH;
			gbc_btnSoumettre.insets = new Insets(0, 0, 0, 5);
			gbc_btnSoumettre.gridx = 7;
			gbc_btnSoumettre.gridy = 9;
			add(btnSoumettre, gbc_btnSoumettre);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		//Demarre le timer
		startTimer=true;
		start();
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
	//declaration de ma tache qui fera marcher  le timer

	public  TimerTask task = new TimerTask() 
	{
		public void run() 
		{
			//si le get timer est false alors on commence a decrementer mon timer
			if(startTimer)
			{
				if(secondes!=0)
				{
					secondes--;
				}
			}
			if(secondes <=60) 
				lblTimer.setText(heures+":" + minutes +":" + secondes);

			//decrementer les secondes lorsque

			if((minutes == 0&&secondes==0)&&heures!=0)
			{
				heures--;
				minutes = 59;
				secondes = 59;
			}
			if(secondes == 0 && minutes!=0){
				minutes--;
				secondes = 59;
			}
			//pour arreter mon timer
			if(heures==0 && minutes==0 && secondes ==0) 
			{
				timer.cancel();
				JOptionPane.showMessageDialog(new JFrame(),textLabels.getObject("JPaneTempsEcoule"));
				btnSoumettre.doClick();
			}
		}         
	};
	//Fonction qui va demarrer la tache
	public void start() {timer.scheduleAtFixedRate(task, 1000, 1000);}
}
