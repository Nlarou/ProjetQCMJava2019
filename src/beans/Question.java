package beans;

public class Question {
	private int id;
	private String question;
	private boolean reponse;
	private double pointMax;
	public Question() {
		
	}
	/**
	 * @param id
	 * @param question
	 * @param reponse
	 * @param pointMax
	 */
	public Question(int id, String question, boolean reponse, double pointMax) {
		super();
		this.id = id;
		this.question = question;
		this.reponse = reponse;
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
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}
	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}
	/**
	 * @return the reponse
	 */
	public boolean getReponse() {
		return reponse;
	}
	/**
	 * @param reponse the reponse to set
	 */
	public void setReponse(boolean reponse) {
		this.reponse = reponse;
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
		return "question [id=" + id + ", question=" + question + ", reponse=" + reponse + ", pointMax=" + pointMax
				+ "]";
	}
	
}
