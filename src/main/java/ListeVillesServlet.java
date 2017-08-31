import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.VilleJpaDao;
import donnees.Ville;

public class ListeVillesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().println(HelloServer.enTete());
		VilleJpaDao villeJpaDao = new VilleJpaDao();
		ArrayList<Ville> listeVilles = (ArrayList<Ville>) villeJpaDao.toutesLesVilles();

		response.setStatus(HttpServletResponse.SC_OK);
		int itemEnCours = 0;
		int nbItems = 10;
		int itemDebut = 1;

		// On parcourt les paramètres pour prendre en compte l'item de début et nbItems
		for (Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();) {

			String name = (String) e.nextElement();
			String[] values = request.getParameterValues(name);

			for (int i = 0; i < values.length; i++) {
				response.getWriter().println(name + ":" + values[i] + "<br/>");
				if ("start".equals(name))
					itemDebut = Integer.valueOf(values[i]);
				if ("limit".equals(name))
					nbItems = Integer.valueOf(values[i]);
			}
		}
		
		response.getWriter().println("<h2>Liste des villes dans la base</h2>");
		for (Ville maVille : listeVilles) {
			itemEnCours++;
			if ((itemEnCours >= itemDebut) && (itemEnCours <= nbItems + itemDebut - 1))
				response.getWriter().println(maVille + "<br>");
		}
	}
}
