import static org.junit.Assert.*;
import org.junit.Test;
import donnees.Ville;

public class CommuneTest {
	@Test
	public void creeCommunes() {
		// GIVEN
		Ville maCommune = new Ville();
		
		// WHEN
		maCommune.setNom("Test");
		
		// THEN
		assertEquals("Test", maCommune.getNom());
		
//		if("Test"!=maCommune.getNom())
//			fail("Erreur de création d'une commune");
//		else
//			success("Création de commune ok");
	}
}
