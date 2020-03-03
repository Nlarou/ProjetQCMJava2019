package beans;

public class ExamenAQuestion {
	private int idExamen;
	private int idQuestion;
	boolean reponse_eleve;
	private double point;
	
	public ExamenAQuestion() {
		
	}

	/**
	 * @param idExamen
	 * @param idQuestion
	 * @param point
	 */
	public ExamenAQuestion(int idExamen, int idQuestion, double point, boolean reponse_eleve ) {
		super();
		this.idExamen = idExamen;
		this.idQuestion = idQuestion;
		this.point = point;
		this.reponse_eleve = reponse_eleve;
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
	 * @return the idQuestion
	 */
	public int getIdQuestion() {
		return idQuestion;
	}

	/**
	 * @param idQuestion the idQuestion to set
	 */
	public void setIdQuestion(int idQuestion) {
		this.idQuestion = idQuestion;
	}

	/**
	 * @return the point
	 */
	public double getPoint() {
		return point;
	}

	/**
	 * @param point the point to set
	 */
	public void setPoint(double point) {
		this.point = point;
	}


	public boolean getReponse_eleve() {
		return reponse_eleve;
	}

	public void setReponse_eleve(boolean reponse_eleve) {
		this.reponse_eleve = reponse_eleve;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return "ExamenAQuestion [idExamen=" + idExamen + ", idQuestion=" + idQuestion + ", point=" + point + ", reponse_eleve=" + reponse_eleve +"]";
	}
	
}
