package edu.ezip.ing1.pds.front.UC2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;

//public class ProductMappingTest{
//    /*
//     * Test : on teste les numéros d'étages dans le actionPerformed de la classe ProductMapping.java
//     *
//     */
//    @Test
//    public void testActionBoutonFlecheGauche() {
//        // Given
//        ProductMapping instance = new ProductMapping(); // Remplacez VotreClasse par le nom de votre classe
//        ActionEvent e = mock(ActionEvent.class); // Remplacez EvenementMock par le type de l'événement que vous utilisez
//        JPanel mapPanel = mock(JPanel.class); // Remplacez PanelMock par le type de votre panel
//
//        when(e.getSource()).thenReturn(leftArrow); // Simule un clic sur la flèche gauche
//        when(instance.getActualFloor()).thenReturn(2); // Fixe l'étage actuel pour le test
//
//        // When
//        instance.votreMethode(e); // Appel de la méthode à tester
//
//        // Then
//        // Vérifie si l'étage actuel a été décrémenté
//        assertEquals(1, instance.getActualFloor());
//
//        // Vérifie si changementfloor est vrai après le clic sur la flèche gauche
//        assertTrue(instance.isChangementFloor());
//
//        // Vérifie si afficherfloorTitle a été appelé
//        verify(instance).afficherfloorTitle();
//
//        // Vérifie si checkPath a été appelé après le clic sur la flèche gauche
//        verify(instance).checkPath();
//
//        // Vérifie si remove, repaint et revalidate ont été appelés sur mainPanel
//        verify(instance.getMainPanel()).remove(mapPanel);
//        verify(instance.getMainPanel()).repaint();
//        verify(instance.getMainPanel()).revalidate();
//    }
//}