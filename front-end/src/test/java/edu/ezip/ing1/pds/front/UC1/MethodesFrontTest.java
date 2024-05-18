package edu.ezip.ing1.pds.front.UC1;

import edu.ezip.ing1.pds.business.dto.Marque;
import edu.ezip.ing1.pds.front.MethodesFront;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MethodesFrontTest {

    @Test
    public void testCarbonFootPrintCalcul() {
        // Given
        double coordLat1 = 40.4168;  // Latitude de Madrid
        double coordLong1 = -3.7038; // Longitude de Madrid
        double coordLat2 = 48.8566;  // Latitude de Paris
        double coordLong2 = 2.3522;  // Longitude de Paris
        double coeff = 1605;         // Coefficient de conversion
        double poids = 200;          // Poids en grammes

        // When
        double actualCarbonFootPrint = MethodesFront.carbonFootPrintCalcul(coordLat1, coordLat2, coordLong1, coordLong2, coeff, poids);

        // Then
        // Calcul manuel pour vérifier le résultat attendu
        double distance = MethodesFront.getDistanceBetweenPointsNew(coordLat1, coordLong1, coordLat2, coordLong2);
        double expectedCarbonFootPrint = distance * coeff * (poids / 1000) / 1000;
        BigDecimal bd = new BigDecimal(expectedCarbonFootPrint).setScale(1, RoundingMode.HALF_UP);
        double expectedValue = bd.doubleValue();

        Assertions.assertEquals(expectedValue, actualCarbonFootPrint, 0.1);
    }




}
