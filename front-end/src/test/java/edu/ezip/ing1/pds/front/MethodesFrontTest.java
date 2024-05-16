package edu.ezip.ing1.pds.front;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import edu.ezip.ing1.pds.business.dto.Marque;
import edu.ezip.ing1.pds.client.UC1.SelectMarqueById;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.io.IOException;

public class MethodesFrontTest {

    @Disabled
    public void testMalusOUbonusEmpreinte() throws IOException, InterruptedException {
        // Creating a Mock object for SelectMarqueById class
        SelectMarqueById selectMarqueByIdMock = mock(SelectMarqueById.class);        // Configuring the Mock behavior to return brands with different RSE values

        Marque durableBrand = new Marque();
        durableBrand.setRse("Durable");
        when(selectMarqueByIdMock.launchSelectMarqueById("1")).thenReturn(durableBrand);

        Marque ethicalBrand = new Marque();
        ethicalBrand.setRse("Ethique");
        when(selectMarqueByIdMock.launchSelectMarqueById("2")).thenReturn(ethicalBrand);

        Marque responsibleBrand = new Marque();
        responsibleBrand.setRse("Responsable");
        when(selectMarqueByIdMock.launchSelectMarqueById("3")).thenReturn(responsibleBrand);

        Marque transparentBrand = new Marque();
        transparentBrand.setRse("Transparente");
        when(selectMarqueByIdMock.launchSelectMarqueById("4")).thenReturn(transparentBrand);

        Marque pollutingBrand = new Marque();
        pollutingBrand.setRse("Polluante");
        when(selectMarqueByIdMock.launchSelectMarqueById("5")).thenReturn(pollutingBrand);


        // Test case for "Durable" RSE
        double resultDurable = MethodesFront.malusOUbonusEmpreinte(100.0, 1);
        Assertions.assertEquals(100.0 - 300.0, resultDurable, 0.001);

        // Test case for "Ethique" RSE
        double resultEthical = MethodesFront.malusOUbonusEmpreinte(100.0, 2);
        Assertions.assertEquals(100.0 - 200.0, resultEthical, 0.001);

        // Test case for "Responsable" RSE
        double resultResponsible = MethodesFront.malusOUbonusEmpreinte(100.0, 3);
        Assertions.assertEquals(100.0 - 100.0, resultResponsible, 0.001);

        // Test case for "Transparente" RSE
        double resultTransparent = MethodesFront.malusOUbonusEmpreinte(100.0, 4);
        Assertions.assertEquals(100.0 + 100.0, resultTransparent, 0.001);

        // Test case for "Polluante" RSE
        double resultPolluting = MethodesFront.malusOUbonusEmpreinte(100.0, 5);
        Assertions.assertEquals(100.0 + 200.0, resultPolluting, 0.001);
    }
}
