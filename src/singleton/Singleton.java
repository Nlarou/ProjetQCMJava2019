package singleton;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

/**
 * Classe Singleton dont la fonction est de nous servir de pont de connection unique entre notre application et la base de donnée
 * @author Sonia Wang
 * @author Nathaniel Larouche
 */
public class Singleton {
	private static Connection conn = null;
	private static ResourceBundle config;
	private Singleton() {
		try {
			//Les informations sont stocker dans le fichier config.
			config = ResourceBundle.getBundle("properties.Configuration");
			Class.forName(config.getString("driver"));
			conn= DriverManager.getConnection(config.getString("url"),config.getString("login"),config.getString("password"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	static public Connection getInstance() {
		if(conn == null)
			new Singleton();
		
		return conn;
	}
}
