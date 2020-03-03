/**
 * 
 */
package beans;

/**
 * @author Nathaniel Larouche
 * @author Sonia Wang
 */
public class UtilisateurAExamen {
	private int idUtilisateur;
	private int idExamen;
	private double note;
	
	public UtilisateurAExamen() {
		
	}

	/**
	 * @param idUtilisteur
	 * @param idExamen
	 * @param note
	 */
	public UtilisateurAExamen(int idUtilisteur, int idExamen, double note) {
		super();
		this.idUtilisateur = idUtilisteur;
		this.idExamen = idExamen;
		this.note = note;
	}

	/**
	 * @return the idUtilisteur
	 */
	public int getIdUtilisateur() {
		return idUtilisateur;
	}

	/**
	 * @param idUtilisteur the idUtilisteur to set
	 */
	public void setIdUtilisateur(int idUtilisteur) {
		this.idUtilisateur = idUtilisteur;
	}

	/**
	 * @return the idExamen
	 */
	public int getIdExamen() {
		return idExamen;
	}

	/**
	 * @param idExamen the idExamen to set
	 */
	public void setIdExamen(int idExamen) {
		this.idExamen = idExamen;
	}

	/**
	 * @return the note
	 */
	public double getNote() {
		return note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(double note) {
		this.note = note;
	}
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UtilisateurAExamen [idUtilisteur=" + idUtilisateur + ", idExamen=" + idExamen + ", note=" + note + "]";
	}
	
}
