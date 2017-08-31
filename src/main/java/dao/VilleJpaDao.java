package dao;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import donnees.Ville;

import javax.persistence.EntityNotFoundException;

public class VilleJpaDao  implements VilleDao {
	EntityManager entityManager;

	public VilleJpaDao() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("h2-Simplon");
		entityManager = emf.createEntityManager();
	}

	public Ville getVilleById(long idRecherche) {
		Ville ville = entityManager.find(Ville.class, idRecherche);
		if (ville != null)
			return ville;
		else
			throw new EntityNotFoundException();
	}

	public void deleteVilleById(long idRecherche) {
		Ville ville = entityManager.find(Ville.class, idRecherche);
		entityManager.remove(ville);
	}

	public Ville createVille(Ville ville) {
		List<Ville> listeVillesDoublons;
		listeVillesDoublons = findDoublon(ville.getNom(),ville.getLatitude(), ville.getLongitude());
		if (listeVillesDoublons.size()==0){
			entityManager.getTransaction().begin();
			entityManager.persist(ville);
			entityManager.getTransaction().commit();
			return ville;
		}
		else
		{
			return listeVillesDoublons.get(0); 
		}
	}

	public List<Ville> findByNom(String nom) {
		return (List<Ville>) entityManager.createNamedQuery("Ville.FindByName").setParameter("nom", nom)
				.getResultList();
	}

	public List<Ville> findDoublon(String nom, double latitude, double longitude) {
		return (List<Ville>) entityManager.createNamedQuery("Ville.FindDoublon").setParameter("nom", nom)
				.setParameter("lat", latitude).setParameter("long", longitude).getResultList();
	}

	public List<Ville> toutesLesVilles(){
		return (List<Ville>) entityManager.createNamedQuery("Ville.FindAll").getResultList();
	}
	
	public List<Ville> partieDeToutesLesVilles(int debut, int nbreItems){
		return (List<Ville>) entityManager.createNamedQuery("Ville.FindPartOfAll").setParameter("debut", debut)
				.setParameter("nbreItems", nbreItems).getResultList();
	}
}