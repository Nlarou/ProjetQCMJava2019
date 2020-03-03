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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import beans.Utilisateur;
import dao.UtilisateurDao;

public class Login extends JPanel {
	private JTextField tfUsername;
	private JTextField tfPassword;
	private ResourceBundle textBoutons;
	private ResourceBundle textLabels;

	/**
	 * Create the panel.
	 */
	public Login() {

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
		gridBagLayout.columnWidths = new int[]{0, 86, 42, 56, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JLabel lblTitreLogin = new JLabel(textLabels.getString("lblTitreLogin"));
		lblTitreLogin.setName("lblTitreLogin");
		lblTitreLogin.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 16));
		GridBagConstraints gbc_lblTitreLogin = new GridBagConstraints();
		gbc_lblTitreLogin.gridwidth = 6;
		gbc_lblTitreLogin.insets = new Insets(0, 0, 5, 0);
		gbc_lblTitreLogin.gridx = 0;
		gbc_lblTitreLogin.gridy = 1;
		add(lblTitreLogin, gbc_lblTitreLogin);

		JLabel lblUsername = new JLabel(textLabels.getString("lblUsername"));
		lblUsername.setName("lblUsername");
		lblUsername.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 1;
		gbc_lblUsername.gridy = 4;
		add(lblUsername, gbc_lblUsername);

		tfUsername = new JTextField();
		GridBagConstraints gbc_tfUsername = new GridBagConstraints();
		gbc_tfUsername.insets = new Insets(0, 0, 5, 5);
		gbc_tfUsername.fill = GridBagConstraints.BOTH;
		gbc_tfUsername.gridx = 3;
		gbc_tfUsername.gridy = 4;
		add(tfUsername, gbc_tfUsername);
		tfUsername.setColumns(10);

		JLabel lblPassword = new JLabel(textLabels.getString("lblPassword"));
		lblPassword.setName("lblPassword");
		lblPassword.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 1;
		gbc_lblPassword.gridy = 7;
		add(lblPassword, gbc_lblPassword);

		tfPassword = new JPasswordField();
		GridBagConstraints gbc_tfPassword = new GridBagConstraints();
		gbc_tfPassword.insets = new Insets(0, 0, 5, 5);
		gbc_tfPassword.fill = GridBagConstraints.BOTH;
		gbc_tfPassword.gridx = 3;
		gbc_tfPassword.gridy = 7;
		add(tfPassword, gbc_tfPassword);
		tfPassword.setColumns(10);
		
		/*
		 * Verifie si l'utilisateur est dans la base de donnee.
		 * Dependament de son type il va etre rediriger vers soit le portail prof ou etudiant
		 */
		JButton btnLogin = new JButton(textBoutons.getString("btnLogin"));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tfUsername.getText()!=null && tfPassword.getText()!=null) {
					try {
						UtilisateurDao dao = new UtilisateurDao();
						Utilisateur user = new Utilisateur();
						user = dao.getByLogin(tfUsername.getText(), tfPassword.getText());
						InterfacePrincipale.setUtilisateur(user);
						if(user.getTypeID()==InterfacePrincipale.PROFESSEUR_TYPE_ID) {
							InterfacePrincipale.userEstConnecter=true;
							InterfacePrincipale.ChangePage(InterfacePrincipale.getClass.ProfesseurHome);
						}
						else if(user.getTypeID()==InterfacePrincipale.ETUDIANT_TYPE_ID) {
							InterfacePrincipale.userEstConnecter=true;
							InterfacePrincipale.ChangePage(InterfacePrincipale.getClass.EtudiantHome);
						}
						else {
							throw new Exception("Une erreur est survenue...");
						}
					}
					catch(Exception exc) {
						JOptionPane.showMessageDialog(new JFrame(),textLabels.getString("JPaneUtilisateurExistePas") );
						exc.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(new JFrame(),textLabels.getString("JPaneRemplirChamps") );
				}

			}
		});
		btnLogin.setName("btnLogin");
		btnLogin.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 11));
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.fill = GridBagConstraints.BOTH;
		gbc_btnLogin.insets = new Insets(0, 0, 5, 5);
		gbc_btnLogin.gridx = 1;
		gbc_btnLogin.gridy = 9;
		add(btnLogin, gbc_btnLogin);

		JButton btnInscrire = new JButton(textBoutons.getString("btnInscrire"));
		btnInscrire.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InterfacePrincipale.ChangePage(InterfacePrincipale.getClass.Inscription);
			}
		});
		btnInscrire.setName("btnInscrire");
		btnInscrire.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 11));
		GridBagConstraints gbc_btnInscrire = new GridBagConstraints();
		gbc_btnInscrire.fill = GridBagConstraints.BOTH;
		gbc_btnInscrire.insets = new Insets(0, 0, 5, 5);
		gbc_btnInscrire.gridx = 3;
		gbc_btnInscrire.gridy = 9;
		add(btnInscrire, gbc_btnInscrire);

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
