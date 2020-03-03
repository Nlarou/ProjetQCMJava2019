package beans;

public class Matiere {
	private int id;
	private String nom;
	
	/**
	 * 
	 */
	public Matiere() {
		super();
	}
	/**
	 * @param id
	 * @param nom
	 */
	public Matiere(int id, String nom) {
		super();
		this.id = id;
		this.nom = nom;
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
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}
	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return nom;
	}
	
}
