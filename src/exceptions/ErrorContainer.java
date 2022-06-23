package exceptions;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ErrorContainer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Map<String, String> errors;
	private String resultat;

	/*
	 * initialise la map d'erreur
	 */
	public ErrorContainer() {
		this.errors = new HashMap<String, String>();
	}

	/*
	 * Ajoute un message correspondant au champ spécifié à la map des erreurs.
	 */
	public void addErreur(String champs, String valeur) {
		this.errors.put(champs, valeur);
	}
	
	public void clear() {
		this.errors.clear();
	}
	
	/*
	 * vérifie la présence d'erreur et renvoie un booléen
	 */
	public boolean isOk() {
		return this.errors.isEmpty();
	}

	/*
	 * permet de récupérer le résultat
	 */
	public String getResultat() {
		return resultat;
	}

	
	/*
	 *permet de mettre un résultat plus global 
	 */
	public void setResultat(String resultat) {
		this.resultat = resultat;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

}
