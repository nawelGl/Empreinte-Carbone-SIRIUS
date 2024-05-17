package edu.ezip.ing1.pds.front;

import edu.ezip.ing1.pds.business.dto.Marque;
import edu.ezip.ing1.pds.client.UC1.SelectMarqueById;
import edu.ezip.ing1.pds.front.MethodesFront;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;

public class MethodesFrontTest {

    @Test
    public void testMalusOUbonusEmpreinteDurable() throws IOException, InterruptedException {
        // Given
        int idMarque = 1;
        double empreinte = 100.0;
        Marque expectedMarque = new Marque();
        expectedMarque.setRse("Durable");

        // Mocking the static method
        try (MockedStatic<SelectMarqueById> mockedStatic = Mockito.mockStatic(SelectMarqueById.class)) {
            mockedStatic.when(() -> SelectMarqueById.launchSelectMarqueById(String.valueOf(idMarque)))
                    .thenReturn(expectedMarque);

            // When
            double result = MethodesFront.malusOUbonusEmpreinte(empreinte, idMarque);

            // Then
            double expected = empreinte - 300.0;
            Assertions.assertEquals(expected, result, 0.001);

            mockedStatic.verify(() -> SelectMarqueById.launchSelectMarqueById(String.valueOf(idMarque)), Mockito.times(1));
        }
    }
}
