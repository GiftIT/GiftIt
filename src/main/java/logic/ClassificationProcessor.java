package logic;

import java.io.File;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.data.FileHandler;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.Map;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.classification.evaluation.EvaluateDataset;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.FileHandler;
import net.sf.javaml.tools.data.StreamHandler;

public class ClassificationProcessor implements DataProcessor {

    private Classifier classifier;

    public void learn(File file, int columnNumber) throws IOException {
        Dataset data = FileHandler.loadDataset(file, columnNumber, ",");
        classifier = new KNearestNeighbors(5);
        classifier.buildClassifier(data);
    }

    @Override
    public Object classify(Instance instance) {
        return classifier.classify(instance);
    }
}