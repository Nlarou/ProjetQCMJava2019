package beans;

public class Utilisateur {
	private int id;
	private String username;
	private String nom;
	private String prenom;
	private String password;
	private int typeID;
	private String question_securite;
	private String reponse_securite;

	public Utilisateur() {
		super();
	}
	public Utilisateur(int id, String username, String nom, String prenom,String password, int typeID, String question_securite, String reponse_securite ) {
		super();
		this.id = id;
		this.username = username;
		this.nom = nom;
		this.prenom = prenom;
		this.password = password;
		this.typeID= typeID;
		this.question_securite = question_securite;
		this.reponse_securite = reponse_securite;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public int getTypeID() {
		return typeID;
	}
	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}
	
	public String getQuestion_securite() {
		return question_securite;
	}
	public void setQuestion_securite(String question_securite) {
		this.question_securite = question_securite;
	}
	public String getReponse_securite() {
		return reponse_securite;
	}
	public void setReponse_securite(String reponse_securite) {
		this.reponse_securite = reponse_securite;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String type = (typeID == 1)?"professeur":"etudiant";
		return "Utilisateur [id=" + id + ", username=" + username + ", nom=" + nom + ", prenom=" + prenom + ", password=" + password+ ", type=" + type+ ", question:"+question_securite+ ", reponse_securite"+ reponse_securite+"]";
	}
	
}
