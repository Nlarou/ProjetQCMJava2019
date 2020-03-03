package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.MenuElement;

import beans.Examen;
import beans.Utilisateur;

/**
 * @author natha
 *
 */
public class InterfacePrincipale {

	private static JFrame frame;
	private static ResourceBundle textMenus;
	private static ResourceBundle textBoutons;
	private static ResourceBundle textLabels;
	//Declaration des int afin de savoir quel type de user dans examenEleve
	public final static int PROFESSEUR_TYPE_ID =1;
	public final static int ETUDIANT_TYPE_ID =2;
	//Declaration des int pour indentifer quel ressourceBundle utiliser
	public final static int RESOURCE_MENU = 1;
	public final static int RESOURCE_BOUTON = 2;
	public final static int RESOURCE_LABEL = 3;
	
	private Locale currentLocale = Locale.getDefault(); 
	private final Locale LANGUE_FRANCAISE =  new Locale("fr","CA");
	private final Locale LANGUE_ANGLAISE = new Locale("en","CA");
	
	private static Utilisateur utilisateur;
	private static Examen examen;
	private static boolean modificationExamen = false;
	public static boolean userEstConnecter;
	private static int nombreQuestionMax;
	//Liste de page sauvegarder permet de sauvegarder les instances de JPanel voulus
	private static List<JPanel> listePageSauvegarder;
	//WhiteList des classes a mettre dans la liste de sauvegarde
	private static List<String>whiteList;
	private static JMenuItem mitemLogin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfacePrincipale window = new InterfacePrincipale();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InterfacePrincipale() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//initialisation des ressourcesBundle
		textMenus =ResourceBundle.getBundle("properties.Menus", currentLocale);
		textBoutons = ResourceBundle.getBundle("properties.Boutons", currentLocale);
		textLabels = ResourceBundle.getBundle("properties.Labels", currentLocale);
		listePageSauvegarder=new ArrayList();
		whiteList= new ArrayList();
		//Ajout de la classe CreationExamen et CreationQuestion dans la whitelist afin de sauvegarder leurs instances
		whiteList.add("gui.CreationExamen");
		whiteList.add("gui.CreationQuestions");
		userEstConnecter=false;

		frame = new JFrame();
		frame.pack();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));


		frame.getContentPane().add(new Login());

		JMenuBar menuBar = new JMenuBar();
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);

		JMenu mnNavigation = new JMenu(textMenus.getString("mnNavigation"));
		mnNavigation.setName("mnNavigation");
		menuBar.add(mnNavigation);
		//Permet de tout reset lors de la deconnection
		mitemLogin = new JMenuItem((userEstConnecter)?textMenus.getString("menuLogout"):textMenus.getString("menuLogin"));
		mitemLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listePageSauvegarder.clear();
				utilisateur=null;
				nombreQuestionMax=0;
				modificationExamen=false;
				examen=null;
				userEstConnecter=false;
				mitemLogin.setText(textMenus.getString("menuLogin"));
				InterfacePrincipale.ChangePage(InterfacePrincipale.getClass.Login);
			}
		});
		mitemLogin.setName((userEstConnecter)?"menuLogin":"menuLogout");
		mnNavigation.add(mitemLogin);

		JMenu mnLangue = new JMenu(textMenus.getString("mnLangue"));
		mnLangue.setName("mnLangue");
		menuBar.add(mnLangue);
		//Fait la traduction francaise
		JMenuItem mItemFrancais = new JMenuItem(textMenus.getString("mItemFrancais"));
		mItemFrancais.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentLocale = LANGUE_FRANCAISE;
				textMenus = ResourceBundle.getBundle("properties.Menus", currentLocale);
				textBoutons = ResourceBundle.getBundle("properties.Boutons", currentLocale);
				textLabels = ResourceBundle.getBundle("properties.Labels", currentLocale);
				updateLangage();

			}
		});
		mItemFrancais.setName("mItemFrancais");
		mnLangue.add(mItemFrancais);
		//Fait la Traduction Anglaise
		JMenuItem mItemAnglais = new JMenuItem(textMenus.getString("mItemAnglais"));
		mItemAnglais.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentLocale = LANGUE_ANGLAISE;
				textMenus = ResourceBundle.getBundle("properties.Menus", currentLocale);
				textBoutons = ResourceBundle.getBundle("properties.Boutons", currentLocale);
				textLabels = ResourceBundle.getBundle("properties.Labels", currentLocale);
				updateLangage();
			}
		});
		mItemAnglais.setName("mItemAnglais");
		mnLangue.add(mItemAnglais);

	}
	/**
	 * Fonction qui permet de de retourner quel ressourceBundle est présentement utilisé pour les
	 * labels,boutons et menus
	 * @param i afin de selectionner un ressourceBundle
	 * @return ResourceBundle qui correspond au i
	 */
	public static ResourceBundle getResourceBundle(int i) {
		if(i==RESOURCE_MENU) {
			return textMenus;
		}
		if(i==RESOURCE_BOUTON) {
			return textBoutons;
		}
		if(i==RESOURCE_LABEL) {
			return textLabels;
		}
		return null;
	}


	/**
	 * Fonction qui permet dynamiquement de changer le language de chaque composant disposant de texte
	 * dans l'interface graphique en fonction des ressourceBundle utilisé a chaqu'un.
	 */
	private void updateLangage() {
		//Iteration pour chaqu'un des composants du frame
		try {
			for(Component composantPrincipal: frame.getContentPane().getComponents()) {
				//Iteration pour chaqu'un des composants soit d'un JPanel ou d'un JMenuBar pour la grande partit de l'application
				for(Component sousComposant : getAllChild(composantPrincipal)) {
					if(sousComposant instanceof JLabel) {
						((JLabel)sousComposant).setText(textLabels.getString(((JLabel)sousComposant).getName()));
					}
					if(sousComposant instanceof JButton) {
						((JButton)sousComposant).setText(textBoutons.getString(((JButton)sousComposant).getName()));
					}
					if(sousComposant instanceof JMenu) {
						((JMenu)sousComposant).setText(textMenus.getString(((JMenu)sousComposant).getName()));
						for(MenuElement popMenu: ((JMenu) sousComposant).getSubElements()) {
							for(MenuElement mItem: ((JPopupMenu) popMenu).getSubElements()) {
								if(mItem instanceof JMenuItem) {
									((JMenuItem)mItem).setText(textMenus.getString(((JMenuItem)mItem).getName()));
								}
							}
						}
					}

					if(sousComposant instanceof JScrollPane) {
						//Va faire l'iteration des composants du JScrollPane qui se compose majoritairement de ViewPort dont
						// l'un ce trouvent le JTable que l'on cherche
						for(Component sousComposantJScrollPane : getAllChild(sousComposant)) {
							for(Component composantJTabel : getAllChild(sousComposantJScrollPane)) {
								//Fait la mise a jour de la langue du JTable
								for(Component panelComposant : getAllChild(composantJTabel)) {
									if(panelComposant instanceof JLabel) {
										System.out.println(sousComposant.getName());
										((JLabel)panelComposant).setText(textLabels.getString(((JLabel)sousComposant).getName()));
									}
									if(panelComposant instanceof JButton) {
										((JButton)panelComposant).setText(textBoutons.getString(((JButton)sousComposant).getName()));
									}
								}
								if(composantJTabel instanceof JTable) {
									if(composantJTabel.getName()=="jtbResultatExamen") {
										TableModele modele;
										if(((JTable)composantJTabel).getModel() instanceof TableModele ) {
											modele = new TableModele((TableModele) ((JTable)composantJTabel).getModel());
											modele.setHeaders(new String[]{textLabels.getString("jtbTitre"),textLabels.getString("jtbMatiere"),textLabels.getString("jtbNote")});
											((JTable)composantJTabel).setModel(modele);
											((JTable)composantJTabel).revalidate();

										}
									}
									if(composantJTabel.getName()=="JtblExamen") {
										TableModele modele;
										if(((JTable)composantJTabel).getModel() instanceof TableModele ) {
											modele = new TableModele((TableModele) ((JTable)composantJTabel).getModel());
											modele.setHeaders(new String[]{textLabels.getString("jtbTitre"),textLabels.getString("jtbMatiere"),
													textLabels.getString("jtbTemps"),textLabels.getString("jtbPoint")});
											System.out.println(modele.getHeaders());
											((JTable)composantJTabel).setModel(modele);
											((JTable)composantJTabel).revalidate();

										}
									}
									if(composantJTabel.getName()=="TableCorrection") {
										TableModele modele;
										if(((JTable)composantJTabel).getModel() instanceof TableModele ) {
											modele = new TableModele((TableModele) ((JTable)composantJTabel).getModel());
											modele.setHeaders(new String[]{textLabels.getString("jtbNumeroQuestion"),textLabels.getString("jtbEnoncer"),
													textLabels.getString("jtbBonneReponse"),textLabels.getString("jtbVosReponse"),textLabels.getString("jtbPointObtenus")});
											System.out.println(modele.getHeaders());
											((JTable)composantJTabel).setModel(modele);
											((JTable)composantJTabel).revalidate();

										}
									}
								}
							}
						}
						((JScrollPane)sousComposant).revalidate();
					}
				}
				if(composantPrincipal instanceof JPanel ) {
					((JPanel)composantPrincipal).updateUI();
				}
			}
		}
		catch(NullPointerException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Fonction qui retourne un liste de child d'un composant.
	 * @param c
	 * @return Liste de composant fille
	 */
	private static Component[] getAllChild(Component c) {
		Component[] components = ((Container) c).getComponents();
		return components;
	}

	/**Fonction qui recupere un enum du nom d'une classe JPanel  afin de le retirer du frame le
	 * JPanel actuel et d'instancier ce nouveau JPanel.
	 * @param p nom de la classe voulu
	 */
	public static void ChangePage(getClass p) {
		if(userEstConnecter) {
			mitemLogin.setText(textMenus.getString("menuLogout"));
		}
		boolean restauration = false;
		try {
			Class<JPanel> panel =(Class<JPanel>) Class.forName("gui."+p);
			Constructor<JPanel> construt = panel.getConstructor();	
			for(Component c : frame.getContentPane().getComponents() ) {
				if(c instanceof JPanel) {
					if((whiteList.contains(c.getClass().getName()) && !modificationExamen)) {
						listePageSauvegarder.add((JPanel) c);
					}
					frame.remove(c);

				}
			}
			for(JPanel jp:listePageSauvegarder) {
				if((jp.getClass().getName()==panel.getName())&&!modificationExamen) {
					frame.getContentPane().add(jp,BorderLayout.CENTER);
					restauration=true;
					frame.validate();
					frame.repaint();
				}
			}
			if(!restauration) { 
				frame.getContentPane().add((JPanel)construt.newInstance(),BorderLayout.CENTER);
				frame.repaint();
				frame.validate();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Enum des noms de classe des differentes interfaces du projet
	 *
	 */
	public static enum getClass{
		Login,Inscription,ProfesseurHome,CreationExamen,CreationQuestions,EtudiantHome,ExamenEleve,CorrectionEleve,ChoixExamen,EtudiantNote;
	}
	public static int getUtilisateurId() {
		return utilisateur.getId();
	}
	public static void setUtilisateur(Utilisateur user) {
		utilisateur=user;
	}
	public static Examen getExamenEncours() {

		return examen;
	}
	public static void setExamen(Examen exam) {
		examen=exam;
	}
	public static int getNombreQuestionMax() 
	{
		return nombreQuestionMax;
	}
	public static void setNombreQuestionMax(int max) 
	{
		nombreQuestionMax = max;
	}

	public static boolean getModificationExamen() 
	{
		return modificationExamen;
	}
	public static void setModificationExamen(boolean statut) 
	{
		modificationExamen = statut;
	}
}
