import java.io.IOException;
import java.util.ArrayList;
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
		getParams(request);
		
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
		int itemEnCours = 0;
		int nbItems = 10;
		int itemDebut = 1;

		if (request.getParameterMap().containsKey("start"))
			itemDebut = Integer.valueOf(request.getParameterMap().get("start").toString());

		if (request.getParameterMap().containsKey("limit"))
			nbItems = Integer.valueOf(request.getParameterMap().get("limit").toString());

		for (Map.Entry<String, String[]> kv : request.getParameterMap().entrySet()) {
			if ((itemEnCours > itemDebut) && (itemEnCours < nbItems + itemDebut))
				res.append(kv.getKey() + ":" + arrayToString(kv.getValue()));
		}
		return res.toString();
	}
}
