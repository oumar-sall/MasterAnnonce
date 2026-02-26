package com.example.tp01dev;

import org.junit.jupiter.api.*;
import org.tribuo.*;
import org.tribuo.classification.*;
import org.tribuo.classification.evaluation.*;
import org.tribuo.classification.sgd.linear.LogisticRegressionTrainer;
import org.tribuo.data.csv.CSVDataSource;
import org.tribuo.dataset.TrainTestSplitter;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

// ATTENTION : On utilise le Label de classification, pas le Label du bytecode
import org.tribuo.classification.Label;

import static org.junit.jupiter.api.Assertions.*;



@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LogistiqueLMIATests {
    private static final Path input = Paths.get("src", "main", "resources", "livraison_retards_dataset.csv");
    private static final Path output = Paths.get("src", "main", "resources", "livraison_retards_dataset_converted.csv");
    private static final Path MODEL_PATH = Paths.get("src", "main", "resources", "livraison_regressor.ser");

    private static MutableDataset<Label> train;
    private static MutableDataset<Label> test;
    private static Model<Label> model;

    @Test @Order(1)
    void prepareDatasets() {
        HeureDepartPreprocessor.convertPreprocessor(input, output);
        assertTrue(output.toFile().exists());
    }

    @Test @Order(2)
    void loadAndSplit() throws IOException {
        var rowProcessor = LogistiqueModelService.createRowProcessor();
        var dataSource = new CSVDataSource<>(output, rowProcessor, true);
        var splitter = new TrainTestSplitter<>(dataSource, 0.8, 42L);

        train = new MutableDataset<>(splitter.getTrain());
        test = new MutableDataset<>(splitter.getTest());
        assertFalse(train.getData().isEmpty());
    }

    @Test @Order(3)
    void trainingAndEval() {
        var trainer = new LogisticRegressionTrainer();
        model = trainer.train(train);
        var evaluation = new LabelEvaluator().evaluate(model, test);
        System.out.println(evaluation.toString());
    }

    @Test @Order(4)
    void saveAndPredict() throws Exception {
        // Sauvegarde sérieuse
        try (var oos = new ObjectOutputStream(new FileOutputStream(MODEL_PATH.toFile()))) {
            oos.writeObject(model);
        }

        // Test de prédiction
        Example<Label> ex = new ArrayExample<>(new Label("non"));
        ex.add("distance_km", 120.0);
        ex.add("heure_decimal", 8.5); // 8h30

        Prediction<Label> pred = model.predict(ex);
        System.out.println("🎯 Résultat : " + pred.getOutput().getLabel());
        System.out.println("📊 Confiance : " + pred.getOutput().getScore());
    }
}