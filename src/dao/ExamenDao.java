package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import beans.Examen;
import beans.Question;
import singleton.Singleton;

public class ExamenDao {
	private Connection con= Singleton.getInstance();
	public void add(Examen exam)throws Exception{
		String req="INSERT INTO examen(id_matiere,titre,temps,point_max) VALUE("+exam.getIdMatiere()+",\""+exam.getTitre()+"\",\""+
				exam.getTemps()+"\","+exam.getPointMax()+")";
		System.out.println(req);
		
		con.createStatement().executeUpdate(req);
	}
	
	public void suprimmer(Examen exam)throws Exception{
		String req="DELETE examen FROM examen WHERE id="+exam.getId();
		System.out.println(req);
		con.createStatement().executeUpdate(req);
	}
	public void update(Examen exam)throws Exception{
		String req="UPDATE examen SET id_matiere="+exam.getIdMatiere()+",titre=\""+exam.getTitre()+"\",temps='"+exam.getTemps()
		+"',point_max="+exam.getPointMax()+" WHERE id="+exam.getId()+"";
		System.out.println(req);
		con.createStatement().executeUpdate(req);
	}
	public Examen getById(int id)throws Exception{
		String req="SELECT * FROM examen WHERE id="+id;
		System.out.println(req);
		ResultSet rs=con.createStatement().executeQuery(req);
		if(rs.next()) {
			Examen examen=new Examen();
			examen.setId(id);
			examen.setTitre(rs.getString("titre"));
			examen.setIdMatiere(rs.getInt("id_matiere"));
			examen.setTemps(Time.valueOf(rs.getString("temps")));
			examen.setPointMax(rs.getDouble("point_max"));
			return examen;
		}
		return null;
	}
	public List getAll()throws Exception{
		String req="SELECT * FROM examen";
		System.out.println(req);
		ResultSet rs=con.createStatement().executeQuery(req);
		List<Examen> liste=new ArrayList<Examen>();
		while(rs.next()) {
			Examen examen=new Examen();
			examen.setId(rs.getInt("id"));
			examen.setTitre(rs.getString("titre"));
			examen.setIdMatiere(rs.getInt("id_matiere"));
			examen.setTemps(Time.valueOf(rs.getString("temps")));
			examen.setPointMax(rs.getDouble("point_max"));
			liste.add(examen);
		}
		return liste;
	}
	
	public Examen getLast()throws Exception
	{
		String req="SELECT * from examen ORDER BY ID DESC LIMIT 1";
		System.out.println(req);
		ResultSet rs=con.createStatement().executeQuery(req);
		if(rs.next()) 
		{
			Examen examen=new Examen();
			examen.setId(rs.getInt("id"));
			examen.setTitre(rs.getString("titre"));
			examen.setIdMatiere(rs.getInt("id_matiere"));
			examen.setTemps(Time.valueOf(rs.getString("temps")));
			examen.setPointMax(rs.getDouble("point_max"));
			return examen;
		}
		return null;
	}
}
