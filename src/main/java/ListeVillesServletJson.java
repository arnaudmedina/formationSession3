import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.VilleJpaDao;
import donnees.Ville;

public class ListeVillesServletJson extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		VilleJpaDao villeJpaDao = new VilleJpaDao();
		ArrayList<Ville> listeVilles = (ArrayList<Ville>) villeJpaDao.toutesLesVilles();

		response.setStatus(HttpServletResponse.SC_OK);
		int itemEnCours = 0;
		int nbItems = 10;
		int itemDebut = 1;

		response.setHeader("Content-Type","application/json");
		
		// On ouvre le Json
		response.getWriter().print("[");

		// On parcourt les paramètres pour prendre en compte l'item de début et
		// nbItems
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

		for (Ville maVille : listeVilles) {
			itemEnCours++;
			if ((itemEnCours >= itemDebut) && (itemEnCours <= nbItems + itemDebut - 1)) {
				if (itemEnCours > itemDebut)
					response.getWriter().print(",\n");
				response.getWriter().print("{");
				response.getWriter().print("\"id\":\"" + maVille.getId() + "\" ");
				response.getWriter().print(" , \"name\" : \"" + maVille.getNom() + "\" ");
				response.getWriter().print(" , \"latitude\" : \"" + maVille.getLatitude() + "\" ");
				response.getWriter().print(" , \"longitude\" : \"" + maVille.getLongitude() + "\" }");
			}
		}
		// On ferme le Json
		response.getWriter().print("]");
	}
}
