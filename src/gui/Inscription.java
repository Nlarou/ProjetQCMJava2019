package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;

import beans.Utilisateur;
import dao.UtilisateurDao;

public class Inscription extends JPanel {
	private JPasswordField passwordField;
	private ResourceBundle textBoutons;
	private ResourceBundle textLabels;

	/**
	 * Create the panel.
	 */
	public Inscription() {
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
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 22, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		//mise en place de mon interface graphique
		JLabel lblTitrepage = new JLabel(textLabels.getString("lblInscription"));
		lblTitrepage.setName("lblInscription");
		lblTitrepage.setFont(new Font("Papyrus", Font.BOLD, 35));
		GridBagConstraints gbc_lblTitrepage = new GridBagConstraints();
		gbc_lblTitrepage.gridwidth = 11;
		gbc_lblTitrepage.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitrepage.gridx = 2;
		gbc_lblTitrepage.gridy = 1;
		add(lblTitrepage, gbc_lblTitrepage);

		JLabel lbIdentifiant = new JLabel(textLabels.getString("lbIdentifiant"));
		lbIdentifiant.setName("lbIdentifiant");
		GridBagConstraints gbc_lbIdentifiant = new GridBagConstraints();
		gbc_lbIdentifiant.fill = GridBagConstraints.BOTH;
		gbc_lbIdentifiant.insets = new Insets(0, 0, 5, 5);
		gbc_lbIdentifiant.gridx = 1;
		gbc_lbIdentifiant.gridy = 5;
		add(lbIdentifiant, gbc_lbIdentifiant);

		JFormattedTextField IdentifiantField = new JFormattedTextField();
		GridBagConstraints gbc_IdentifiantField = new GridBagConstraints();
		gbc_IdentifiantField.gridwidth = 11;
		gbc_IdentifiantField.insets = new Insets(0, 0, 5, 5);
		gbc_IdentifiantField.fill = GridBagConstraints.BOTH;
		gbc_IdentifiantField.gridx = 3;
		gbc_IdentifiantField.gridy = 5;
		add(IdentifiantField, gbc_IdentifiantField);

		JLabel lblNom = new JLabel(textLabels.getString("lblNom"));
		lblNom.setName("lblNom");
		GridBagConstraints gbc_lblNom = new GridBagConstraints();
		gbc_lblNom.fill = GridBagConstraints.BOTH;
		gbc_lblNom.insets = new Insets(0, 0, 5, 5);
		gbc_lblNom.gridx = 1;
		gbc_lblNom.gridy = 7;
		add(lblNom, gbc_lblNom);

		JFormattedTextField nomField = new JFormattedTextField();
		GridBagConstraints gbc_nomField = new GridBagConstraints();
		gbc_nomField.gridwidth = 11;
		gbc_nomField.insets = new Insets(0, 0, 5, 5);
		gbc_nomField.fill = GridBagConstraints.BOTH;
		gbc_nomField.gridx = 3;
		gbc_nomField.gridy = 7;
		add(nomField, gbc_nomField);

		JLabel lblPrenom = new JLabel(textLabels.getString("lblPrenom"));
		lblPrenom.setName("lblPrenom");
		GridBagConstraints gbc_lblPrenom = new GridBagConstraints();
		gbc_lblPrenom.fill = GridBagConstraints.BOTH;
		gbc_lblPrenom.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrenom.gridx = 1;
		gbc_lblPrenom.gridy = 9;
		add(lblPrenom, gbc_lblPrenom);

		JFormattedTextField prenomField = new JFormattedTextField();
		GridBagConstraints gbc_prenomField = new GridBagConstraints();
		gbc_prenomField.gridwidth = 11;
		gbc_prenomField.insets = new Insets(0, 0, 5, 5);
		gbc_prenomField.fill = GridBagConstraints.BOTH;
		gbc_prenomField.gridx = 3;
		gbc_prenomField.gridy = 9;
		add(prenomField, gbc_prenomField);

		JLabel lblMotDePasse = new JLabel(textLabels.getString("lblMotDePasse"));
		lblMotDePasse.setName("lblMotDePasse");
		GridBagConstraints gbc_lblMotDePasse = new GridBagConstraints();
		gbc_lblMotDePasse.fill = GridBagConstraints.BOTH;
		gbc_lblMotDePasse.insets = new Insets(0, 0, 5, 5);
		gbc_lblMotDePasse.gridx = 1;
		gbc_lblMotDePasse.gridy = 11;
		add(lblMotDePasse, gbc_lblMotDePasse);

		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.gridwidth = 11;
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.BOTH;
		gbc_passwordField.gridx = 3;
		gbc_passwordField.gridy = 11;
		add(passwordField, gbc_passwordField);

		JLabel lblProfil = new JLabel(textLabels.getString("lblProfil"));
		lblProfil.setName("lblProfil");
		GridBagConstraints gbc_lblProfil = new GridBagConstraints();
		gbc_lblProfil.fill = GridBagConstraints.BOTH;
		gbc_lblProfil.insets = new Insets(0, 0, 5, 5);
		gbc_lblProfil.gridx = 1;
		gbc_lblProfil.gridy = 13;
		add(lblProfil, gbc_lblProfil);


		JRadioButton rdbtnProfesseur = new JRadioButton(textLabels.getString("rdbtnProfesseur"));
		rdbtnProfesseur.setName("rdbtnProfesseur");
		rdbtnProfesseur.setFont(new Font("Tahoma", Font.BOLD, 15));
		GridBagConstraints gbc_rdbtnProfesseur = new GridBagConstraints();
		gbc_rdbtnProfesseur.fill = GridBagConstraints.BOTH;
		gbc_rdbtnProfesseur.gridwidth = 5;
		gbc_rdbtnProfesseur.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnProfesseur.gridx = 3;
		gbc_rdbtnProfesseur.gridy = 13;
		add(rdbtnProfesseur, gbc_rdbtnProfesseur);

		JRadioButton rdbtnEleve = new JRadioButton(textLabels.getString("rdbtnEleve"));
		rdbtnEleve.setName("rdbtnEleve");
		rdbtnEleve.setFont(new Font("Tahoma", Font.BOLD, 15));
		GridBagConstraints gbc_rdbtnEleve = new GridBagConstraints();
		gbc_rdbtnEleve.fill = GridBagConstraints.BOTH;
		gbc_rdbtnEleve.gridwidth = 5;
		gbc_rdbtnEleve.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnEleve.gridx = 10;
		gbc_rdbtnEleve.gridy = 13;
		add(rdbtnEleve, gbc_rdbtnEleve);
		rdbtnEleve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rdbtnProfesseur.setSelected(false);
			}
		});

		rdbtnProfesseur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rdbtnEleve.setSelected(false);
			}
		});

		JButton btnAnnuler = new JButton(textBoutons.getString("btnAnnuler"));
		btnAnnuler.setName("btnAnnuler");
		btnAnnuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				InterfacePrincipale.ChangePage(InterfacePrincipale.getClass.Login);
			}
		});

		JLabel lblQuestionsecurite = new JLabel(textLabels.getString("lblQuestionsecurite"));
		lblQuestionsecurite.setName("lblQuestionsecurite");
		GridBagConstraints gbc_lblQuestionsecurite = new GridBagConstraints();
		gbc_lblQuestionsecurite.insets = new Insets(0, 0, 5, 5);
		gbc_lblQuestionsecurite.gridx = 1;
		gbc_lblQuestionsecurite.gridy = 15;
		add(lblQuestionsecurite, gbc_lblQuestionsecurite);

		JComboBox cbQuestion = new JComboBox();
		GridBagConstraints gbc_cbQuestion = new GridBagConstraints();
		gbc_cbQuestion.gridwidth = 11;
		gbc_cbQuestion.insets = new Insets(0, 0, 5, 5);
		gbc_cbQuestion.fill = GridBagConstraints.BOTH;
		gbc_cbQuestion.gridx = 3;
		gbc_cbQuestion.gridy = 15;
		add(cbQuestion, gbc_cbQuestion);
		cbQuestion.addItem("Quel est votre ville natale ?");
		cbQuestion.addItem("Quel est votre Sport prefere ?");
		cbQuestion.addItem("Quel est votre couleur prefere ?");
		cbQuestion.addItem("Quel est votre celebrite prefere ?");
		cbQuestion.addItem("Quel est le prenom de votre mere ?");


		JLabel lblReponsesecurite = new JLabel(textLabels.getString("lblReponsesecurite"));
		lblReponsesecurite.setName("lblReponsesecurite");
		GridBagConstraints gbc_lblReponsesecurite = new GridBagConstraints();
		gbc_lblReponsesecurite.insets = new Insets(0, 0, 5, 5);
		gbc_lblReponsesecurite.gridx = 1;
		gbc_lblReponsesecurite.gridy = 17;
		add(lblReponsesecurite, gbc_lblReponsesecurite);

		JFormattedTextField ReponseField = new JFormattedTextField();
		GridBagConstraints gbc_ReponseField = new GridBagConstraints();
		gbc_ReponseField.gridwidth = 11;
		gbc_ReponseField.insets = new Insets(0, 0, 5, 5);
		gbc_ReponseField.fill = GridBagConstraints.BOTH;
		gbc_ReponseField.gridx = 3;
		gbc_ReponseField.gridy = 17;
		add(ReponseField, gbc_ReponseField);

		GridBagConstraints gbc_btnAnnuler = new GridBagConstraints();
		gbc_btnAnnuler.gridwidth = 2;
		gbc_btnAnnuler.fill = GridBagConstraints.BOTH;
		gbc_btnAnnuler.insets = new Insets(0, 0, 5, 5);
		gbc_btnAnnuler.gridx = 3;
		gbc_btnAnnuler.gridy = 20;
		add(btnAnnuler, gbc_btnAnnuler);

		JButton btnConfirmer = new JButton(textBoutons.getString("btnConfirmer"));
		btnConfirmer.setName("btnConfirmer");
		btnConfirmer.addActionListener(new ActionListener() {
			//creation d un nouvel utilisateur lorsqu'on clique sur confirmer
			public void actionPerformed(ActionEvent arg0) {
				if(((!nomField.getText().isEmpty())&&!(prenomField.getText().isEmpty()&&passwordField.getText().isEmpty()&&IdentifiantField.getText().isEmpty()&&ReponseField.getText().isEmpty())&&(rdbtnEleve.isSelected()||rdbtnProfesseur.isSelected()))&&cbQuestion.isShowing()) {
					try
					{
						//creation de mon user temporaire et passage en parametre avec les contenu de mes textfields et enfin 
						//l ajouter dans la bbase de donnees
						UtilisateurDao userDao = new UtilisateurDao();
						//si l utilisateur existe pas encore alors on creera un nouveau
						if(!userDao.IdentifiantExist(IdentifiantField.getText())) 
						{
							Utilisateur user = new Utilisateur();
							user.setNom(nomField.getText());
							user.setPrenom(prenomField.getText());
							user.setPassword(passwordField.getText());
							user.setUsername(IdentifiantField.getText());
							user.setTypeID((rdbtnEleve.isSelected())?2:1);
							user.setQuestion_securite((String) cbQuestion.getSelectedItem());
							user.setReponse_securite(ReponseField.getText());
							userDao.add(user);
							JOptionPane.showMessageDialog(null, textLabels.getString("JPaneUserAjouter"));
							InterfacePrincipale.ChangePage(InterfacePrincipale.getClass.Login);
						}
						
						// sinon un message d erreur sera afficher pour gerer la situation dependemment si l utilisateur c etait deja inscrit auparavant oiu pas
						else 
						{
							int reponse = JOptionPane.showConfirmDialog(null, textLabels.getString("JPaneUserDejaExistant"));
							if (reponse == JOptionPane.YES_OPTION)
							{
								//envoyer dans le login
								JOptionPane.showMessageDialog(null,textLabels.getString("JPaneUserRedirectLogin"));
							}
							else if(reponse == JOptionPane.NO_OPTION) 
							{
								JOptionPane.showMessageDialog(null, textLabels.getString("JPaneUserChangeName"));
							}

						}
					}
					catch(Exception ex)
					{
						ex.getStackTrace();
					}
				}
				//message d erreur si tous les champs ne sont pas remplis
				else 
				{
					JOptionPane.showMessageDialog(null,textLabels.getString("JPaneRemplirChamps"));
				}

			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.gridwidth = 2;
		gbc_btnNewButton.gridx = 12;
		gbc_btnNewButton.gridy = 20;
		add(btnConfirmer, gbc_btnNewButton);


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
