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
        double coordLat1 = 40.4168;
        double coordLong1 = -3.7038;
        double coordLat2 = 48.8566;
        double coordLong2 = 2.3522;
        double coeff = 1605;
        double poids = 200;

        // When
        double actualCarbonFootPrint = MethodesFront.carbonFootPrintCalcul(coordLat1, coordLat2, coordLong1, coordLong2, coeff, poids);

        // Then
       
        double distance = MethodesFront.getDistanceBetweenPointsNew(coordLat1, coordLong1, coordLat2, coordLong2);
        double expectedCarbonFootPrint = distance * coeff * (poids / 1000) / 1000;
        BigDecimal bd = new BigDecimal(expectedCarbonFootPrint).setScale(1, RoundingMode.HALF_UP);
        double expectedValue = bd.doubleValue();

        Assertions.assertEquals(expectedValue, actualCarbonFootPrint, 0.1);
    }




}
