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
		entityManager.getTransaction().begin();
		entityManager.remove(ville);
		entityManager.getTransaction().commit();
	}

	public Ville createVille(Ville ville) {
		List<Ville> listeVillesDoublons;
		listeVillesDoublons = findDoublon(ville.getNom(), ville.getLongitude(), ville.getLatitude());
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
		return (List<Ville>) entityManager.createNamedQuery("Ville.FindByName", Ville.class).setParameter("nom", nom)
				.getResultList();
	}

	public List<Ville> findDoublon(String nom, double latitude, double longitude) {
		return (List<Ville>) entityManager.createNamedQuery("Ville.FindDoublon", Ville.class).setParameter("nom", nom)
				.setParameter("lat", latitude).setParameter("long", longitude).getResultList();
	}

	public List<Ville> toutesLesVilles(){
		return (List<Ville>) entityManager.createNamedQuery("Ville.FindAll", Ville.class).getResultList();
	}
	
	public List<Ville> partieDeToutesLesVilles(int debut, int nbreItems){
		return (List<Ville>) entityManager.createNamedQuery("Ville.FindPartOfAll", Ville.class).setParameter("debut", debut)
				.setParameter("nbreItems", nbreItems).getResultList();
	}

	public Ville majVille(Ville ville) {
		entityManager.getTransaction().begin();
		entityManager.persist(ville);
		entityManager.getTransaction().commit();
		return ville;
	}
}