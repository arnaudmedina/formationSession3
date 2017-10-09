package co.simplon;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dao.VilleJpaDao;
import donnees.Ville;

@Path ( "/villes")
public class MonEssaiJaxRs {

	private static Logger logger = (Logger) LoggerFactory.getLogger(MonEssaiJaxRs.class);

	public MonEssaiJaxRs(){
		logger.info("Création de l'objet MonEssaiJaxRs !");
	}

	//Application integration
	@GET
	@Path("{monId}")
	//	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Ville getVille(@PathParam("monId") Long monId) throws JsonProcessingException {
		//		public String getVille() {
		logger.info("Passage dans la méthode getVille ! avec monId = " + monId);
		if (monId==null)
		{
			logger.debug("Méthode MonEssaiJaxRs.getVille, paramètre monId fourni = null");
			Ville ville = new Ville("Paris",1L,2L);
			return (ville);
		}
		VilleJpaDao villeJpaDao = new VilleJpaDao();
		Ville ville = villeJpaDao.getVilleById (monId);
		if(ville==null)
			throw new RuntimeException("Get: Ville with " + monId +  " not found");
		return (ville);
	}

	//	    // for the browser
	//	    @GET
	//	    @Produces(MediaType.TEXT_XML)
	//	    public Todo getTodoHTML() {
	//	        Todo todo = TodoDao.instance.getModel().get(id);
	//	        if(todo==null)
	//	            throw new RuntimeException("Get: Todo with " + id +  " not found");
	//	        return todo;
	//	    }
	//
	//	    @PUT
	//	    @Consumes(MediaType.APPLICATION_XML)
	//	    public Response putTodo(JAXBElement<Todo> todo) {
	//	        Todo c = todo.getValue();
	//	        return putAndGetResponse(c);
	//	    }
	//
	//	    @DELETE
	//	    public void deleteTodo() {
	//	        Todo c = TodoDao.instance.getModel().remove(id);
	//	        if(c==null)
	//	            throw new RuntimeException("Delete: Todo with " + id +  " not found");
	//	    }
	//
	//	    private Response putAndGetResponse(Todo todo) {
	//	        Response res;
	//	        if(TodoDao.instance.getModel().containsKey(todo.getId())) {
	//	            res = Response.noContent().build();
	//	        } else {
	//	            res = Response.created(uriInfo.getAbsolutePath()).build();
	//	        }
	//	        TodoDao.instance.getModel().put(todo.getId(), todo);
	//	        return res;
	//	    }
}
