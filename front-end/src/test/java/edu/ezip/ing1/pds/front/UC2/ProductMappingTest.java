package edu.ezip.ing1.pds.front.UC2;

import static org.mockito.Mockito.*;
import org.junit.*;
import javax.swing.*;
import java.awt.*;
import org.junit.Before;
import org.junit.Test;

public class ProductMappingTest{

    private ProductMapping productMapping;
    private JLabel floorTitleMock;
    private JPanel mainPanelMock;
    private int textSize = 12;

    @Before
    public void setUp() {
        floorTitleMock = mock(JLabel.class);
        mainPanelMock = mock(JPanel.class);
        productMapping = new ProductMapping();
        productMapping.setFloorTitle(floorTitleMock);
        productMapping.setMainPanel(mainPanelMock);
        productMapping.setTextSize(textSize);
    }

    @Test
    public void testAfficherfloorTitle_FirstFloor() {
        // Given
        productMapping.setActualFloor(1);

        // When
        productMapping.afficherfloorTitle();

        // Then
        verify(floorTitleMock).setText("Plan du 1er étage :");
        verify(floorTitleMock).setFont(new Font("Avenir", Font.BOLD, textSize + 2));
        verify(floorTitleMock).setBounds(600, 10, 300, 50);
        verify(mainPanelMock).add(floorTitleMock);
    }

    @Test
    public void testAfficherfloorTitle_OtherFloor() {
        // Given
        productMapping.setActualFloor(2);

        // When
        productMapping.afficherfloorTitle();

        // Then
        verify(floorTitleMock).setText("Plan du 2ème étage :");
        verify(floorTitleMock).setFont(new Font("Avenir", Font.BOLD, textSize + 2));
        verify(floorTitleMock).setBounds(600, 10, 300, 50);
        verify(mainPanelMock).add(floorTitleMock);
    }
}