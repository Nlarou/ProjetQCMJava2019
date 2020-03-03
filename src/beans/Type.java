package beans;

public class Type {
	private int id;
	private int nom;
	Type(){
		
	}
	/**
	 * @param id
	 * @param nom
	 */
	public Type(int id, int nom) {
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
	public int getNom() {
		return nom;
	}
	/**
	 * @param nom the nom to set
	 */
	public void setNom(int nom) {
		this.nom = nom;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Type [id=" + id + ", nom=" + nom + "]";
	}
	
}
