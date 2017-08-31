import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// le code html du formulaire est dans un fichier formLogin.html
		String fichier ="src/main/forms/formLogin.html";
		response.getWriter().println(HelloServer.enTete());
		response.getWriter().println("<h2>Formulaire de login</h2>");
		
		//lecture du fichier texte	
		try{
			BufferedReader br=new BufferedReader(new FileReader(fichier)); 
			String ligne;
			while ((ligne=br.readLine())!=null){
				response.getWriter().println(ligne);
			}
			br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
		
//		response.getWriter().println("<h1>Mon Formulaire</h1><form action='./dynamic' method='post'> <div> <label for='nom'>Nom :</label>  <input type='text' id='name' name='name' /> </div> <div> Password: <input type='password' name='password'/> <br/><div class='button'> <button type='submit'>Envoyer votre message</button> </div>");
		}
}
