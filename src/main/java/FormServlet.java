import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Traitement du Get sur le formulaire de login

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// le code html du formulaire est dans un fichier .html
		String fichier = "src/main/forms/formVille.html";
		
		// lecture du fichier html de formulaire
		String formulaire = lireFichier(fichier);

		// lecture des paramètres mode et id
		
		
		// On va renseigner les champs du formulaire
		
		// Cas CREA
		formulaire.replaceFirst("value='valMode'", "value='CREA'");
		formulaire.replaceFirst("value='valIdVille'", "value=''");
		formulaire.replaceFirst("value='valNomVille'", "value='nom de la ville'");
		formulaire.replaceFirst("value='valLongitude'", "value='longitude'");
		formulaire.replaceFirst("value='valLatitude'", "value='latitude'");
		
		// Cas Modification
		
		// On envoie un en-tête
		response.getWriter().println(HelloServer.enTete());
		
		// On envoie un titre
		response.getWriter().println("<h2>Formulaire de création, modification, suppression de Ville</h2>");
		
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
