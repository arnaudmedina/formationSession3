
import java.io.BufferedReader;
import java.io.FileReader;

import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.*;

public class HelloServer {

	public static String enTete(){
		String retour;
		
		retour = integreHtml("src/main/menu.html");
		return retour;
		
	}
	
	public static String integreHtml(String nomFichier)
	{
				String retour = "";
				
				//lecture du fichier texte	
				try{
					BufferedReader br=new BufferedReader(new FileReader(nomFichier)); 
					String ligne;
					while ((ligne=br.readLine())!=null){
						retour += ligne;
					}
					br.close(); 
				}		
				catch (Exception e){
					System.out.println(e.toString());
				}
				return retour;
	}
	
	
	public static void main(String args[]) throws Exception{
	 Server server = new Server(9093);
	 ServletHandler handler = new ServletHandler();
	 server.setHandler(handler);
	 handler.addServletWithMapping(HelloGenericServlet.class, "/hello");
	 handler.addServletWithMapping(HelloGenericServlet.class, "/");
	 handler.addServletWithMapping(StatefulServlet.class, "/stateful");
	 handler.addServletWithMapping(FormServlet.class, "/form");
	 handler.addServletWithMapping(DynamicServlet.class, "/dynamic");
	 handler.addServletWithMapping(ListeVillesServlet.class, "/listeVilles");
	 
	 
	 
	 server.start();
	 server.join();
	 }
}