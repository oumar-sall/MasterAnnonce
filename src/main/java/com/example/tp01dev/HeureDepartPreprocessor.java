package com.example.tp01dev;

import java.io.*;
import java.nio.file.Path;

public class HeureDepartPreprocessor {

    /**
     * Lit le fichier source et convertit la colonne 'heure_depart'
     * en format décimal dans le fichier de sortie.
     */
    public static void convertPreprocessor(Path input, Path output) {
        try (BufferedReader reader = new BufferedReader(new FileReader(input.toFile()));
             BufferedWriter writer = new BufferedWriter(new FileWriter(output.toFile()))) {

            String line = reader.readLine();
            if (line != null) {
                writer.write(line + "\n"); // Écrit le header
            }

            while ((line = reader.readLine()) != null) {
                // Ici, logique de conversion (ex: "08:30" -> 8.5)
                // Pour l'instant on copie la ligne, à adapter selon ton format CSV
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du prétraitement du CSV", e);
        }
    }
}