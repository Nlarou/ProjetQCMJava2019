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

public class EtudiantHome extends JPanel {
	private ResourceBundle textBoutons;
	private ResourceBundle textLabels;
	/**
	 * Create the panel.
	 */
	public EtudiantHome() {
		//Lors d'un changement de border au niveau du JPanel, il va récuperer la langue de l'interface principale
		addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("border")) {
					getLanguage();
					System.out.println("Langage updated");
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

		JLabel lblHome = new JLabel(textLabels.getString("lblHome"));
		lblHome.setName("lblHome");
		lblHome.setFont(new Font("Papyrus", Font.BOLD, 19));
		GridBagConstraints gbc_lblHome = new GridBagConstraints();
		gbc_lblHome.gridwidth = 9;
		gbc_lblHome.insets = new Insets(0, 0, 5, 0);
		gbc_lblHome.gridx = 0;
		gbc_lblHome.gridy = 1;
		add(lblHome, gbc_lblHome);
		
		//Redirige dans choixExamen
		JButton btnPasserExamen = new JButton(textBoutons.getString("btnPasserExamen"));
		btnPasserExamen.setName("btnPasserExamen");
		btnPasserExamen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InterfacePrincipale.ChangePage(InterfacePrincipale.getClass.ChoixExamen);
			}
		});
		btnPasserExamen.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 11));
		GridBagConstraints gbc_btnPasserExamen = new GridBagConstraints();
		gbc_btnPasserExamen.fill = GridBagConstraints.BOTH;
		gbc_btnPasserExamen.insets = new Insets(0, 0, 5, 5);
		gbc_btnPasserExamen.gridx = 2;
		gbc_btnPasserExamen.gridy = 4;
		add(btnPasserExamen, gbc_btnPasserExamen);
		//Redirige vers la page de note
		JButton btnVoirNotes = new JButton(textBoutons.getString("btnVoirNotes"));
		btnVoirNotes.setName("btnVoirNotes");
		btnVoirNotes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InterfacePrincipale.ChangePage(InterfacePrincipale.getClass.EtudiantNote);
			}
		});
		btnVoirNotes.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 11));
		GridBagConstraints gbc_btnVoirNotes = new GridBagConstraints();
		gbc_btnVoirNotes.gridwidth = 2;
		gbc_btnVoirNotes.fill = GridBagConstraints.BOTH;
		gbc_btnVoirNotes.insets = new Insets(0, 0, 5, 5);
		gbc_btnVoirNotes.gridx = 5;
		gbc_btnVoirNotes.gridy = 4;
		add(btnVoirNotes, gbc_btnVoirNotes);

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
