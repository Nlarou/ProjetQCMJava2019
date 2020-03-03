package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Utilisateur;
import singleton.Singleton;

public class UtilisateurDao {
	
	Connection conn = Singleton.getInstance();
	
	
		public void add(Utilisateur user)throws Exception{
			
		String req="INSERT INTO utilisateur(username,nom,prenom, password, type_id, question_securite, reponse_securite) VALUE(\""+user.getUsername()+"\",\""+
					user.getNom()+"\",\""+user.getPrenom()+"\",\""+
							user.getPassword()+"\","+user.getTypeID()+",\""+ user.getQuestion_securite()+"\",\""+user.getReponse_securite()+"\")";
			System.out.println(req);
			conn.createStatement().executeUpdate(req);
		}
		public void suprimmer(Utilisateur user)throws Exception{
			
			String query = "DELETE from utilisateur WHERE ID =" + user.getId();
			System.out.println(query +  user.getId());
			//execution de la commande dans un statement
			conn.createStatement().executeUpdate(query);
		}
		public void update(Utilisateur user)throws Exception{
			int id = user.getId();
			String query = "UPDATE utilisateur SET username = '"+user.getUsername()+"', nom = '"+user.getNom()+"', prenom = '"+user.getPrenom()+"', type_id= "+user.getTypeID()+", password = "+user.getPassword()+" Where ID=" + id;
			System.out.println(query);
			//execution de la commande dans un statement
			conn.createStatement().executeUpdate(query);
		}
		public Utilisateur getById(int id)throws Exception{
			// TODO Auto-generated method stub
			String query = "SELECT * from utilisateur WHERE ID = " + id;
			//execution de la commande dans un statement
			ResultSet rs = conn.createStatement().executeQuery(query);
			if(rs.next())
			{
				Utilisateur user = new Utilisateur();
				user.setId(rs.getInt("ID"));
				user.setUsername(rs.getString("username"));
				user.setNom(rs.getString("nom"));
				user.setPrenom(rs.getString("prenom"));
				user.setPassword(rs.getString("password"));
				user.setTypeID(rs.getInt("type_id"));
				user.setQuestion_securite("question_securite");
				user.setReponse_securite("reponse_securite");
				
				return user;
			}
			return null;
		}
		public Utilisateur getByLogin(String username, String password)throws Exception{
			// TODO Auto-generated method stub
			String query = "SELECT * from utilisateur WHERE username= '" + username + "' AND password='" + password + "'";
			System.out.println(query);
			//execution de la commande dans un statement
			ResultSet rs = conn.createStatement().executeQuery(query);
			if(rs.next())
			{
				Utilisateur user = new Utilisateur();
				user.setId(rs.getInt("ID"));
				user.setUsername(rs.getString("username"));
				user.setNom(rs.getString("nom"));
				user.setPrenom(rs.getString("prenom"));
				user.setPassword(rs.getString("password"));
				user.setTypeID(rs.getInt("type_id"));
				user.setQuestion_securite("question_securite");
				user.setReponse_securite("reponse_securite");
				
				return user;
			}
			return null;
		}
		
		public List getAll()throws Exception{
		
			List<Utilisateur> myList = new ArrayList<Utilisateur>();
			String query;
			query = "SELECT * from utilisateur";
			System.out.println(query);
			ResultSet rs = conn.createStatement().executeQuery(query);
			while(rs.next()) 
			{
				Utilisateur user = new Utilisateur();
				user.setId(rs.getInt("ID"));
				user.setUsername(rs.getString("username"));
				user.setNom(rs.getString("nom"));
				user.setPrenom(rs.getString("prenom"));
				user.setPassword(rs.getString("password"));
				user.setTypeID(rs.getInt("type_id"));
				user.setQuestion_securite("question_securite");
				user.setReponse_securite("reponse_securite");
				myList.add(user);
				
			}
			return myList;
		}
		

		public boolean IdentifiantExist(String identifiant) throws Exception 
		{
			//commande cherchant les ID existant deja dans la base de donnees
			ResultSet IDResult = conn.createStatement().executeQuery("SELECT username from utilisateur");
			int compte=0;
				
			while(IDResult.next())
			{
				String result = IDResult.getString(1);
				if(result.equalsIgnoreCase(identifiant.trim()))
				{
						compte++;
				}
			}
				
			if(compte !=0) {
				return true;
			}

		return false;

		}
}
