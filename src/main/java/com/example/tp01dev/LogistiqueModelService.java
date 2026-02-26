package com.example.tp01dev;

import org.tribuo.classification.Label;
import org.tribuo.classification.LabelFactory;
import org.tribuo.data.columnar.FieldProcessor;
import org.tribuo.data.columnar.RowProcessor;
import org.tribuo.data.columnar.processors.field.DoubleFieldProcessor;
import org.tribuo.data.columnar.processors.field.IdentityProcessor;
import org.tribuo.data.columnar.processors.response.FieldResponseProcessor;
import java.util.LinkedHashMap;

public class LogistiqueModelService {
    public static RowProcessor<Label> createRowProcessor() {
        var labelFactory = new LabelFactory();
        var fieldProcessors = new LinkedHashMap<String, FieldProcessor>();

        fieldProcessors.put("heure_decimal", new DoubleFieldProcessor("heure_decimal"));
        fieldProcessors.put("distance_km", new DoubleFieldProcessor("distance_km"));
        fieldProcessors.put("pluie", new IdentityProcessor("pluie"));
        fieldProcessors.put("jour_semaine", new IdentityProcessor("jour_semaine"));
        fieldProcessors.put("vehicule_type", new IdentityProcessor("vehicule_type"));

        var responseProcessor = new FieldResponseProcessor<>("retard", "non", labelFactory);
        return new RowProcessor<>(responseProcessor, fieldProcessors);
    }
}