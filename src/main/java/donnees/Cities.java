package donnees;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Cities {
	// Lit la liste des villes en BDD et renvoie une liste d'objets ville
	public static List<Ville> lireVilles(Connection conn) {
		String query = "SELECT * FROM CITIES";
		ArrayList<Ville> listeVilles = new ArrayList<Ville>();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery(query);
			Ville maVille;
			while (res.next()) {
				maVille = new Ville(res.getString("NAME"), res.getInt("LATITUDE"), res.getInt("LATITUDE"));
				System.out.println(maVille);
				listeVilles.add(maVille);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			try {
				if (stmt != null) {

				}
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return (listeVilles);
	}

	// Renvoie la liste des objets ville dont le nom est �gal a NOM
	public static List<Ville> findByName(Connection conn, String name) throws SQLException {
		// String query = "SELECT * FROM CITIES WHERE NAME='" +
		// Name.replace("'", "''") + "';";
		String query = "SELECT * FROM CITIES WHERE NAME=?;";
		System.out.println("Requ�te : " + query);
		ArrayList<Ville> listeVilles = new ArrayList<Ville>();
		PreparedStatement maRequete = null;
		maRequete = conn.prepareStatement(query);
		try {
			maRequete.setString(1, name);
			System.out.println("Requ�te : " + maRequete);
			ResultSet res = maRequete.executeQuery();
			Ville maVille;
			while (res.next()) {
				maVille = new Ville(res.getString("NAME"), res.getInt("LATITUDE"), res.getInt("LATITUDE"));
				System.out.println(maVille);
				listeVilles.add(maVille);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (maRequete != null) {
					maRequete.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return (listeVilles);
	}
	

	// Renvoie la liste des objets ville se situant � moins de DIST km de
	// LATITUDE
	// LONGITUDE via la connexion CONN
	public static List<Ville> villesProches(Connection conn, double latitude, double longitude, double dist) {
		String query = "SELECT ID, NAME, LONGITUDE, LATITUDE, ( 6371 * acos( cos( radians('" + latitude
				+ "') ) * cos( radians( LATITUDE ) ) * cos( radians( LONGITUDE ) - radians('" + longitude
				+ "') ) + sin( radians('" + latitude
				+ "') ) * sin( radians( LATITUDE ) ) ) ) AS DISTANCE FROM CITIES GROUP BY ID HAVING DISTANCE < " + dist
				+ " ORDER BY DISTANCE";
		System.out.println("Requ�te : " + query);
		ArrayList<Ville> listeVilles = new ArrayList<Ville>();

		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery(query);
			Ville maVille;
			while (res.next()) {
				maVille = new Ville(res.getString("NAME"), res.getInt("LATITUDE"), res.getInt("LONGITUDE"));
				System.out.println(maVille + " \t dist : " + Integer.toString(res.getInt("DISTANCE")));
				listeVilles.add(maVille);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return (listeVilles);
	}


}
