package donnees;

public class Ville {

	private String nomVille;
	private double latitude;
	private double longitude;

	public Ville(String nomVille, double latitude, double longitude) {
		this.nomVille = nomVille;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * @return the nomVille
	 */
	public String getNomVille() {
		return nomVille;
	}

	/**
	 * @param nomVille
	 *            the nomVille to set
	 */
	public void setNomVille(String nomVille) {
		this.nomVille = nomVille;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String toString() {
		return this.nomVille + "\t - lat : " + this.getLatitude() + "\t - lon : " + this.getLongitude();
	}

}