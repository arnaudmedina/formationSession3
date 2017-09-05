import java.io.IOException;
import java.util.Enumeration;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.VilleJpaDao;
import donnees.Ville;

public class FormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Traitement du Get sur le formulaire de login

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// le code html du formulaire est dans un fichier .html
		String fichier = "src/main/forms/formVille.html";

		// On envoie un en-tête
		response.getWriter().println(HelloServer.enTete());

		// On envoie un titre
		response.getWriter().println("<h2>Formulaire de création, modification, suppression de Ville</h2>");

		
		// lecture du fichier html de formulaire
		String formulaire = lireFichier(fichier);

		// lecture des paramètres mode et id

		String mode = "";
		String id = "";

		// On parcourt les paramètres
		response.getWriter().println("Paramètres : <br> \n");
		
		for (Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();) {
			String name = (String) e.nextElement();
			String[] values = request.getParameterValues(name);
			for (int i = 0; i < values.length; i++) {
				response.getWriter().println(name + ":" + values[i] + "<br/>");
				if ("mode".equals(name))
					mode = values[i];
				if ("id".equals(name))
					id = values[i];
			}
		}

		// On va renseigner les champs du formulaire

		// Cas CREA
		if ("CREA".equals(mode)) {
			formulaire = formulaire.replaceFirst("valMode", "CREA");
			formulaire = formulaire.replaceFirst("valIdVille", "");
			formulaire = formulaire.replaceFirst("value='valNomVille'", "value='nom de la ville'");
			formulaire = formulaire.replaceFirst("value='valLongitude'", "value='longitude'");
			formulaire = formulaire.replaceFirst("value='valLatitude'", "value='latitude'");
		}

		// Cas Modification
		if ("MOD".equals(mode)) {
			formulaire = formulaire.replaceFirst("valMode", "MOD");
			formulaire = formulaire.replaceFirst("valIdVille", id);
			
			// On charge les données de la ville passée en paramètre
			VilleJpaDao villeJpaDao = new VilleJpaDao();
			
			Ville maVille = villeJpaDao.getVilleById(Long.parseUnsignedLong(id));
			
			formulaire = formulaire.replaceFirst("valNomVille", maVille.getNom());
			formulaire = formulaire.replaceFirst("valLongitude", Double.toString(maVille.getLongitude()));
			formulaire = formulaire.replaceFirst("valLatitude", Double.toString(maVille.getLatitude()));
		}
		
		// On envoie le résultat dans l'objet reponse
		response.getWriter().println(formulaire);
	}

	// Lit le fichier passé en paramètre et renvoie la chaîne
	public String lireFichier(String fichier) {
		String formulaire = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(fichier));
			String ligne;
			while ((ligne = br.readLine()) != null) {
				formulaire += ligne + "\n";
			}
			br.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return formulaire;
	}
}
