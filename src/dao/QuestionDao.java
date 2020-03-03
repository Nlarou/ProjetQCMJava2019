package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Question;
import singleton.Singleton;

public class QuestionDao {

	Connection conn = Singleton.getInstance();

	public void add(Question question)throws Exception{

		String req="INSERT INTO question(question,reponse,point_max) VALUE(\""+question.getQuestion()+"\","+
				question.getReponse()+","+question.getPointMax()+")";
			System.out.println(req);
			conn.createStatement().executeUpdate(req);
	}
	public void suprimmer(Question question)throws Exception{
		String query = "DELETE from question WHERE ID =" + question.getId();
		System.out.println(query +  question.getId());
		//execution de la commande dans un statement
		conn.createStatement().executeUpdate(query);
	}
	public void update(Question initial, Question finale)throws Exception{
		int id = initial.getId();
		String query = "UPDATE question SET question = '"+finale.getQuestion()+"', reponse = "+finale.getReponse()+", point_max = "+finale.getPointMax()+" Where ID=" + id;
		System.out.println(query);
		//execution de la commande dans un statement
		conn.createStatement().executeUpdate(query);;
	}
	public Question getById(int id)throws Exception{
		String query = "SELECT * from Question WHERE ID = " + id;
		//execution de la commande dans un statement
		ResultSet rs = conn.createStatement().executeQuery(query);
		if(rs.next())
		{
			Question question = new Question();
			question.setId(rs.getInt("ID"));
			question.setQuestion(rs.getString("question"));
			question.setReponse(rs.getBoolean("reponse"));
			question.setPointMax(rs.getDouble("point_max"));
			
			return question;
		}
		return null;
	}
	public List getAll()throws Exception{
	
		
		List<Question> myList = new ArrayList<Question>();
		String query;
		query = "SELECT * from question";
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
	
	public Question getLast()throws Exception{
		String query = "SELECT * from Question ORDER BY ID DESC LIMIT 1";
		//execution de la commande dans un statement
		ResultSet rs = conn.createStatement().executeQuery(query);
		if(rs.next())
		{
			Question question = new Question();
			question.setId(rs.getInt("ID"));
			question.setQuestion(rs.getString("question"));
			question.setReponse(rs.getBoolean("reponse"));
			question.setPointMax(rs.getDouble("point_max"));
			
			return question;
		}
		return null;
	}
}
