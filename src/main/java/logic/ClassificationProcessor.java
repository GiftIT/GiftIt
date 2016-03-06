
package logic;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.filter.normalize.NormalizeMidrange;
import net.sf.javaml.tools.data.FileHandler;

import java.io.File;
import java.io.IOException;

public class    ClassificationProcessor implements DataProcessor {

    private Classifier classifier;

    public void learn(File file, int columnNumber) throws IOException {
        Dataset data = FileHandler.loadDataset(file, columnNumber, ",");
        classifier = new KNearestNeighbors(4);
        classifier.buildClassifier(data);
    }

    @Override
    public Object classify(Instance instance) {
        return classifier.classify(instance);
    }
}

