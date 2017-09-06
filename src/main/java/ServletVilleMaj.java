import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.VilleJpaDao;
import donnees.Ville;

public class ServletVilleMaj extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Prend en entrée un tableau de chaînes de caractères et renvoie une chaîne
	// en les séparant par des retours à la ligne
	private static String arrayToString(String[] strs) {
		String res = "";
		for (String str : strs) {
			res += str + "\n";
		}
		return res;
	}

	// Renvoie une chaîne de caractères contenant les paramètres de l'objet
	// requête Http séparés par des retours à la ligne
	private static String getParams(HttpServletRequest request) {
		StringBuilder res = new StringBuilder();
		for (Map.Entry<String, String[]> kv : request.getParameterMap().entrySet()) {
			res.append(kv.getKey() + ":" + arrayToString(kv.getValue()));
		}
		return res.toString();
	}

	// Cherche un paramètre dans la chaîne et renvoie sa valeur
	private static String getParam(String chaineParams, String nomParam) {
		String valeurRetour = "";

		// Transformer les chaines séparées par des \n en tableau
		String[] params = chaineParams.split("\n");
		String[] myCurrParam = null;

		// parcourir toutes les chaines
		for (int i = 0; i < params.length; i++) {
			// récupérer les items séparés par ":"
			myCurrParam = params[i].split(":");
			if (myCurrParam != null && myCurrParam.length >= 2) {
				// si valeur de gauche = nomParam, valeurRetour = valeur de
				// droite
				if (myCurrParam[0].equalsIgnoreCase(nomParam))
					valeurRetour = myCurrParam[1];
			} else
				System.err.println("Paramètre incomplet dans la chaine : " + myCurrParam[0] + "ignored.");
		}
		return valeurRetour;
	}

	// Exemple de traitement d'une requête GET
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println("Hello from HelloServlet !\n" + "got:\n" + request);
	}

	// Exemple de traitement d'une requête POST
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println(HelloServer.enTete());
		response.getWriter().println("On traite le POST pour la ville !\n" + "requête reçue : \n" + request +
		// "\n body:\n"+getBody(request.getReader())+
				"\n parameters:\n" + getParams(request));

		// On va récupérer les infos sur la ville à mettre à jour passées en
		// paramètre
		String params = getParams(request);
		long id = Long.parseLong(getParam(params, "idVille"));
		String nom = getParam(params, "nom");
		String mode = getParam(params, "mode");
		double latitude = Double.parseDouble(getParam(params, "latitude"));
		double longitude = Double.parseDouble(getParam(params, "longitude"));

		// On charge les données de la ville passée en paramètre
		VilleJpaDao villeJpaDao = new VilleJpaDao();
		Ville maVille;

		if ((id == 0) || ("CREA".equals(mode))) {
			maVille = new Ville(id, nom, latitude, longitude);
			villeJpaDao.createVille(maVille);
		} else {
			maVille = villeJpaDao.getVilleById(id);
			maVille.setNom(nom);
			maVille.setLatitude(latitude);
			maVille.setLongitude(longitude);
			villeJpaDao.createVille(maVille);
		}
	}

	// Exemple de traitement de requête DELETE
	@Override
	public void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println("Delete from HelloServlet !\n" + "got:\n" + request +
		// "\n body:\n"+getBody(request.getReader())+
				"\n parameters:\n" + getParams(request));

		String id = "";
		for (Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();) {
			String name = (String) e.nextElement();
			String[] values = request.getParameterValues(name);

			for (int i = 0; i < values.length; i++) {
				response.getWriter().println(name + ":" + values[i] + "<br/>");
				if ("idVille".equals(name))
					id = values[i];
			}
		}
		// Suppression de l'identifiant passé en paramètre
		VilleJpaDao villeJpaDao = new VilleJpaDao();
		System.out.println("Id = " + id);
		villeJpaDao.deleteVilleById(Long.parseLong(id));
	}
}
