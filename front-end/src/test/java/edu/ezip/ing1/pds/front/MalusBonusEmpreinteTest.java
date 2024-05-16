package edu.ezip.ing1.pds.front;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;

public class MalusBonusEmpreinteTest {

    @Disabled
    public void testMalusBonusEmpreinte() {
        // Définissez vos valeurs d'entrée pour le test
        double empreinte = 1000.0;
        int idMarque = 2; // Remplacez par l'ID de marque approprié

        // Appelez la méthode que vous souhaitez tester

        double resultat = MethodesFront.malusOUbonusEmpreinte(empreinte, idMarque);

        // Définissez le résultat attendu en fonction de vos conditions de votre méthode
        double resultatAttendu = 0.0; // Remplacez par le résultat attendu

        // Vérifiez si le résultat obtenu correspond au résultat attendu
        assertEquals(resultatAttendu, resultat, 0.01); // Tolérance de 0.01 pour les doubles
    }
}
