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
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ProfesseurHome extends JPanel {
	private ResourceBundle textBoutons;
	private ResourceBundle textLabels;
	/**
	 * Create the panel.
	 */
	public ProfesseurHome() {
		//Lors d'un changement de border au niveau du JPanel, il va récuperer la langue de l'interface principale
		addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("border")) {
					getLanguage();
				}

			}
		});
		
		//mise en place de linterace graphique 
		getLanguage();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JLabel lblHomeProfesseur = new JLabel(textLabels.getString("lblHomeProfesseur"));
		lblHomeProfesseur.setName("lblHomeProfesseur");
		lblHomeProfesseur.setFont(new Font("Papyrus", Font.BOLD, 23));
		GridBagConstraints gbc_lblHomeProfesseur = new GridBagConstraints();
		gbc_lblHomeProfesseur.gridwidth = 10;
		gbc_lblHomeProfesseur.insets = new Insets(0, 0, 5, 0);
		gbc_lblHomeProfesseur.gridx = 0;
		gbc_lblHomeProfesseur.gridy = 1;
		add(lblHomeProfesseur, gbc_lblHomeProfesseur);

		JButton btnCreeExamen = new JButton(textBoutons.getString("btnCreeExamen"));
		btnCreeExamen.setName("btnCreeExamen");
		btnCreeExamen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InterfacePrincipale.setModificationExamen(false);
				InterfacePrincipale.ChangePage(InterfacePrincipale.getClass.CreationExamen);
			}
		});
		GridBagConstraints gbc_btnCreeExamen = new GridBagConstraints();
		gbc_btnCreeExamen.insets = new Insets(0, 0, 5, 5);
		gbc_btnCreeExamen.fill = GridBagConstraints.BOTH;
		gbc_btnCreeExamen.gridx = 3;
		gbc_btnCreeExamen.gridy = 6;
		add(btnCreeExamen, gbc_btnCreeExamen);

		JButton btnModifier = new JButton(textBoutons.getString("btnModifier"));
		btnModifier.setName("btnModifier");
		//losrquon appuis sur modifier on activera le mode modification et on sera ainsi envoye dans le page creation examen
		btnModifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				InterfacePrincipale.setModificationExamen(true);
				InterfacePrincipale.ChangePage(InterfacePrincipale.getClass.ChoixExamen);
			}
		});
		GridBagConstraints gbc_btnModifier = new GridBagConstraints();
		gbc_btnModifier.fill = GridBagConstraints.BOTH;
		gbc_btnModifier.insets = new Insets(0, 0, 5, 5);
		gbc_btnModifier.gridx = 6;
		gbc_btnModifier.gridy = 6;
		add(btnModifier, gbc_btnModifier);
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
