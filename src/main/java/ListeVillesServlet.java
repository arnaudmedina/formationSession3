import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import donnees.Cities;
import donnees.Ville;

public class ListeVillesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().println(HelloServer.enTete());
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection conn = null;
		ArrayList<Ville> listeVilles = new ArrayList<Ville>();
		try {
			conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/h2/h2-Simplon", "sa", "");

			listeVilles = (ArrayList<Ville>) Cities.lireVilles(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println("<h2>Liste des villes dans la base</h2>");
		for (Ville maVille : listeVilles) {
			response.getWriter().println(maVille + "<br>");
		}

	}

	private static String arrayToString(String[] strs) {
		String res = "";
		for (String str : strs) {
			res += str + "\n";
		}
		return res;
	}

	private static String getParams(HttpServletRequest request) {
		StringBuilder res = new StringBuilder();
		for (Map.Entry<String, String[]> kv : request.getParameterMap().entrySet()) {
			res.append(kv.getKey() + ":" + arrayToString(kv.getValue()));
		}
		return res.toString();
	}

}
