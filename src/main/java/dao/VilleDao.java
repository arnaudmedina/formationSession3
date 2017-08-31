package dao;

import donnees.Ville;

public interface VilleDao {
	Ville getVilleById(long id);
	void deleteVilleById(long id);
	Ville createVille(Ville ville);
}
