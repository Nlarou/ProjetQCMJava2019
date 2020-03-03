package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Examen;
import beans.ExamenAQuestion;
import beans.Question;
import beans.Utilisateur;
import singleton.Singleton;

public class ExamenAQuestionDao {
	
	Connection conn = Singleton.getInstance();
	public void add( Examen examenChoisi, Question Questionconserne)throws Exception
	{
		String req="INSERT INTO examenaquestion(id_examen,id_question) VALUE("+examenChoisi.getId()+","+
				Questionconserne.getId()+")";
		System.out.println(req);
		conn.createStatement().executeUpdate(req);
	}
	public void setReponseEleve(Examen examenChoisi, Question Questionconserne,Boolean reponse_eleve) 
	{

		try {
			String query = "UPDATE examenaquestion SET reponse_eleve="+ reponse_eleve +" Where id_question=" + Questionconserne.getId()+ "  AND id_examen = " + examenChoisi.getId();
			System.out.println(query);
			//execution de la commande dans un statement
			conn.createStatement().executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void supprimer(Examen exam)throws Exception{
		
		String query = "DELETE from examenaquestion WHERE id_examen =" + exam.getId();
		System.out.println(query +  exam.getId());
		//execution de la commande dans un statement
		conn.createStatement().executeUpdate(query);
	}
	
	public ExamenAQuestion getById(Examen examenChoisi, Question Questionconserne)throws Exception{
		
		String query = "SELECT * from examenaquestion WHERE id_examen=" + examenChoisi.getId() + " AND id_question=" +  Questionconserne.getId();
		System.out.println(query);
		//execution de la commande dans un statement
		ResultSet rs = conn.createStatement().executeQuery(query);
		if(rs.next())
		{
			ExamenAQuestion eq = new ExamenAQuestion();
			eq.setIdExamen(rs.getInt("id_examen"));
			eq.setIdQuestion(rs.getInt("id_question"));
			eq.setReponse_eleve(rs.getBoolean("reponse_eleve"));
			eq.setPoint(rs.getDouble("point_eleve"));
			return eq;
		}
		return null;
	}
	
	public void setPoint(Examen examenChoisi, Question Questionconserne, double points) throws SQLException 
	{
		String query = "UPDATE examenaquestion SET point_eleve="+ points +" Where id_question=" + Questionconserne.getId()+ "  AND id_examen = " + examenChoisi.getId();
		System.out.println(query);
		//execution de la commande dans un statement
		conn.createStatement().executeUpdate(query);
		
	}
	
	public List getAll()throws Exception{
	
		List<ExamenAQuestion> myList = new ArrayList<ExamenAQuestion>();
		String query;
		query = "SELECT * from examenaquestion";
		System.out.println(query);
		ResultSet rs = conn.createStatement().executeQuery(query);
		while(rs.next()) 
		{
			ExamenAQuestion eq = new ExamenAQuestion();
			eq.setIdExamen(rs.getInt("id_examen"));
			eq.setIdQuestion(rs.getInt("id_question"));
			eq.setReponse_eleve(rs.getBoolean("reponse_eleve"));
			eq.setPoint(rs.getDouble("point_eleve"));
			myList.add(eq);
			
		}
		return myList;
	
	}

	public List<Question> getQuestions(Examen examen) throws SQLException
	{
		List<Question> myList = new ArrayList<Question>();
		String query = "SELECT * FROM question \r\n" + 
				"JOIN examenaquestion ON question.id = examenaquestion.id_question\r\n" + 
				"where examenaquestion.id_examen =" + examen.getId();
		//execution de la commande dans un statement
		System.out.println(query);
		ResultSet rs = conn.createStatement().executeQuery(query);
		while(rs.next()) 
		{
			Question question = new Question();
			question.setId(rs.getInt("ID"));
			question.setQuestion(rs.getString("question"));
			question.setReponse(rs.getBoolean("reponse"));
			question.setPointMax(rs.getDouble("point_max"));
			myList.add(question);
		}
		
		return myList;
	}
	public int getNombreQuestion(Examen examen)throws Exception{
        int nombreQuestion =0;
        String query = "SELECT COUNT(examenaquestion.id_question) FROM examenaquestion WHERE id_examen=" + examen.getId();
        System.out.println(query);
        //execution de la commande dans un statement
        ResultSet rs = conn.createStatement().executeQuery(query);
        while(rs.next()) 
        {
            nombreQuestion = rs.getInt(1);    
        }
        return nombreQuestion;

    }
	
	public List<ExamenAQuestion> getExamenAQuestions(Examen examen) throws SQLException
    {
        List<ExamenAQuestion> myList = new ArrayList<ExamenAQuestion>();
        String query = "SELECT * FROM examenaquestion where examenaquestion.id_examen=" + examen.getId();
        //execution de la commande dans un statement
        System.out.println(query);
        ResultSet rs = conn.createStatement().executeQuery(query);
        while(rs.next()) 
        {
            ExamenAQuestion eq = new ExamenAQuestion();
            eq.setIdExamen(rs.getInt("id_examen"));
            eq.setIdQuestion(rs.getInt("id_question"));
            eq.setReponse_eleve(rs.getBoolean("reponse_eleve"));
            eq.setPoint(rs.getDouble("point_eleve"));
            myList.add(eq);
        }
        
        return myList;
    }

}
