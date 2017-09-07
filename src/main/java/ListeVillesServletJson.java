import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.VilleJpaDao;
import donnees.Ville;

public class ListeVillesServletJson extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		VilleJpaDao villeJpaDao = new VilleJpaDao();
		ArrayList<Ville> listeVilles = (ArrayList<Ville>) villeJpaDao.toutesLesVilles();

		String mode = "json";
		
		// On renvoie html ou json selon le type de requete
		if ("text/html".equals(request.getHeader("Content-Type")))
			mode="html";
		
		if ("application/json".equals(request.getHeader("Content-Type")))
			mode = "json";
		
		if ("json".equals(mode) ){
		response.setStatus(HttpServletResponse.SC_OK);
		response.setHeader("Content-Type", "application/json");
		}
		
		if ("html".equals(mode)  ){
			response.setStatus(HttpServletResponse.SC_OK);
			response.setHeader("Content-Type","text/html");
			}
			
		int nbItems = 10;
		int itemDebut = 1;

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
		if ("html".equals(mode))
			ecritJsonAlaMain(request, response, listeVilles, itemDebut, nbItems);
		else
			ecritJsonAvecGson(request, response, listeVilles, itemDebut, nbItems);
	}

	private void ecritJsonAlaMain(HttpServletRequest request, HttpServletResponse response,
			ArrayList<Ville> listeVilles, int itemDebut, int nbItems) throws IOException {

		int itemEnCours = 0;

		// On ouvre le Json
		response.getWriter().print("[");

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

	private void ecritJsonAvecGson(HttpServletRequest request, HttpServletResponse response,
			ArrayList<Ville> listeVilles, int itemDebut, int nbItems) throws IOException {

		int itemEnCours = 0;
		ArrayList<Ville> listeVillesChoisies = new ArrayList<Ville>();

		for (Ville maVille : listeVilles) {
			itemEnCours++;
			if ((itemEnCours >= itemDebut) && (itemEnCours <= nbItems + itemDebut - 1)) {
				listeVillesChoisies.add(maVille);
			}
		}
		ObjectMapper mapper = new ObjectMapper();

		// Object to JSON in file
		// mapper.writeValue(response.getOutputStream(), listeVillesChoisies);

		// Object to JSON in String
		String jsonInString = mapper.writeValueAsString(listeVillesChoisies);
		response.getWriter().print(jsonInString);
	}
}
