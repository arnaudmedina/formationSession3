import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
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
		
		// On envoie un en-tête html
		response.setHeader("Content-Type","text/html");
		
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
		
		String urlImageMaj = "http://www.clker.com/cliparts/0/6/8/6/11971488431079343407barretr_Pencil.svg.hi.png";
		String urlImagePoubelle = "http://data-cache.abuledu.org/512/poubelle-menagere-52d7d953.jpg";
		response.getWriter().println("<h2>Liste des villes dans la base</h2>");
		response.getWriter().println(HelloServer.integreHtml("./views/barrePagination.html"));
		for (Ville maVille : listeVilles) {
			itemEnCours++;
			if ((itemEnCours >= itemDebut) && (itemEnCours <= nbItems + itemDebut - 1)){
				response.getWriter().println("<a href='./form?mode=MOD&id="+maVille.getId()+"'><img src='"+urlImageMaj+"' width='16'></A> ");
				response.getWriter().println(maVille);
				response.getWriter().println("<a href='./form?mode=MOD&id="+maVille.getId()+"'><img src='"+urlImagePoubelle+"' width='16'></a> <br> ");
			}
		}
	}
}
