package edu.ezip.ing1.pds.front.UC3;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class StatScoreTest {

    @Test
    public void testExportData() {

        String outputDir = "/Users/sophia";

        // instancie statscore
        StatScore statScore = new StatScore();

        // on appelle la méthode de exportData()
        statScore.exportData();

        File file = new File(outputDir +"/score.xls");
        assertTrue(file.exists()); //on vérifie si le fichier existe d
    }
}
