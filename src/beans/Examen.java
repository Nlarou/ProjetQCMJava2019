package beans;

import java.sql.Time;

public class Examen {
	private int id;
	private int idMatiere;
	private String titre;
	private Time temps;
	private double pointMax;
	
	public Examen() {
	}

	
	/**
	 * @param id
	 * @param idMatiere
	 * @param titre
	 * @param temps
	 * @param pointMax
	 */
	public Examen(int id, int idMatiere, String titre, Time temps, double pointMax) {
		super();
		this.id = id;
		this.idMatiere = idMatiere;
		this.titre = titre;
		this.temps = temps;
		this.pointMax = pointMax;
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
	 * @return the idMatiere
	 */
	public int getIdMatiere() {
		return idMatiere;
	}


	/**
	 * @param idMatiere the idMatiere to set
	 */
	public void setIdMatiere(int idMatiere) {
		this.idMatiere = idMatiere;
	}


	/**
	 * @return the titre
	 */
	public String getTitre() {
		return titre;
	}


	/**
	 * @param titre the titre to set
	 */
	public void setTitre(String titre) {
		this.titre = titre;
	}


	/**
	 * @return the temps
	 */
	public Time getTemps() {
		return temps;
	}


	/**
	 * @param temps the temps to set
	 */
	public void setTemps(Time temps) {
		this.temps = temps;
	}


	/**
	 * @return the pointMax
	 */
	public double getPointMax() {
		return pointMax;
	}


	/**
	 * @param pointMax the pointMax to set
	 */
	public void setPointMax(double pointMax) {
		this.pointMax = pointMax;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Examen [id=" + id + ", titre=" + titre + ", pointMax=" + pointMax + "]";
	}
	
}
