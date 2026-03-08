package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {
    public static void main(String[] args) {
        Gaulois assurancetourix = new Gaulois("Assurancetourix", 2);

        // TEST 1 : Étaler non occupé
        System.out.println("=== Test 1: étal non occupé ===");
        Etal etalNonOccupe = new Etal();

        try {
            String resultat = etalNonOccupe.acheterProduit(3, assurancetourix);
            System.out.println("Résultat: " + resultat);
        } catch (IllegalStateException e) {  // ← Attrape la bonne exception
            System.err.println("Exception attrapée: " + e.getMessage());
            e.printStackTrace();
        }

        // TEST 2 : Étaler occupé (pour vérifier que ça marche)
        System.out.println("\n=== Test 2: étal occupé ===");
        Etal etalOccupe = new Etal();
        etalOccupe.occuperEtal(assurancetourix, "lyres", 5);

        try {
            String resultat = etalOccupe.acheterProduit(2, assurancetourix);
            System.out.println("Résultat: " + resultat);
        } catch (IllegalStateException e) {
            System.err.println("Exception attrapée: " + e.getMessage());
        }

        System.out.println("\nFin du test");
    }
}