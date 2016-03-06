package logic;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.classification.evaluation.EvaluateDataset;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.filter.normalize.NormalizeMidrange;
import net.sf.javaml.tools.data.FileHandler;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ClassificationProcessor implements DataProcessor {

    private Classifier classifier;

    public void learn(File file, int columnNumber) throws IOException {
        Dataset data = FileHandler.loadDataset(file, columnNumber, ",");
        classifier = new KNearestNeighbors(4);
        NormalizeMidrange nmr = new NormalizeMidrange(0.5, 1);
        nmr.build(data);
        classifier.buildClassifier(data);
        Dataset dataForClassification = FileHandler.loadDataset(file, columnNumber, ",");
        Map<Object, PerformanceMeasure> pm = EvaluateDataset.testDataset(classifier, dataForClassification);
        for (Object o : pm.keySet())
            System.out.println(o + ": " + pm.get(o).getAccuracy());
    }

    @Override
    public Object classify(Instance instance) {
        return classifier.classify(instance);
    }
}