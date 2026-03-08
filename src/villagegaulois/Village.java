package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtalMarche) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtalMarche);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}

	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();

		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");

		int etalLibre = marche.trouverEtalLibre();

		if (etalLibre != -1) {
			marche.utiliserEtal(etalLibre, vendeur, produit, nbProduit);
			chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°" + (etalLibre + 1) + ".\n");
		} else {
			chaine.append("Il n'y a pas d'étal libre pour " + vendeur.getNom() + ".\n");
		}


		return chaine.toString();
	}

	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();

		Etal[] etal = marche.trouverEtals(produit);

		if(etal.length == 0){
			chaine.append("Il n'y a pas de vendeur qui propose des "+produit+" au marché\n");
		}
		else if(etal.length == 1){
			chaine.append("Seul le vendeur "+etal[0].getVendeur().getNom()+" propose des "+produit+"au marché\n");
		}
		else {
			chaine.append("Les vendeurs qui proposent des " + produit + " sont :\n");
			for (int i = 0; i < etal.length; i++) {
				chaine.append("- " + etal[i].getVendeur().getNom() + "\n");
			}
		}

		return chaine.toString();
	}

	public Etal rechercherEtal(Gaulois vendeur){
		return marche.trouverVendeur(vendeur);
	}

	public String partirVendeur(Gaulois vendeur){
		StringBuilder chaine = new StringBuilder();
		Etal etal = marche.trouverVendeur(vendeur);
		if(etal != null){
			return etal.libererEtal();
		}
		return null;
	}

	public String afficherMarche(){
		return marche.afficherMarche();
	}



	public class Marche{
		private Etal[] etal;

		public Marche(int nbEtal) {
			etal = new Etal[nbEtal];
			for(int i = 0; i < nbEtal; i++) {
				etal[i] = new Etal();
			}
		}

		public void utiliserEtal(int indiceEtal, Gaulois vendeur,String produit, int nbProduit){
			etal[indiceEtal].occuperEtal(vendeur,produit,nbProduit);
		}

		public int trouverEtalLibre() {
			for(int i = 0; i < etal.length; i++) {
				if(etal[i].isEtalOccupe() == false) {
					return i;
				}
			}
			return -1;
		}

		public Etal[] trouverEtals(String produit) {
			int nbEtal = 0;
			for(int i = 0; i < etal.length; i++) {
				if(etal[i].contientProduit(produit) == true) {
					nbEtal++;
				}
			}

			Etal[] etal_return = new Etal[nbEtal];
			int indice = 0;

			for(int i = 0; i < etal.length; i++) {
				if(etal[i].contientProduit(produit)) {
					etal_return[indice] = etal[i];
					indice++;
				}

			}

			return etal_return;
		}

		public Etal trouverVendeur(Gaulois gaulois) {
			for(int i = 0; i < etal.length; i++) {
				if( etal[i].isEtalOccupe() && etal[i].getVendeur().equals(gaulois)){
					return etal[i];
				}
			}
			return null;
		}

		public String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int etalVide = 0;
			for(int i = 0; i < etal.length; i++) {
				if(etal[i].isEtalOccupe() == true) {
					chaine.append(etal[i].afficherEtal());
				}
				else {
					etalVide++;
				}
			}
			if(etalVide != 0) {
				chaine.append("Il reste " +etalVide+ " étals non utilisés dans le marché.\n");
			}
			return chaine.toString();
		}


	}


}