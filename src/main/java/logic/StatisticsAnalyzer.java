package logic;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.classification.evaluation.EvaluateDataset;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.data.FileHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class StatisticsAnalyzer {
    private StatisticsUpdater statisticsUpdater;
    private ClassificationProcessor classificationProcessor;
    private String src;

    public StatisticsAnalyzer(StatisticsUpdater statisticsUpdater, ClassificationProcessor classificationProcessor, String src) {
        this.statisticsUpdater = statisticsUpdater;
        this.classificationProcessor = classificationProcessor;
        this.src = src;
    }

    public void learn() throws IOException {
//        statisticsUpdater.run();
        String[] userData = statisticsUpdater.getData();
        int length = userData.length;
        File file = new File(src);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (int i = 0; i < length; i++) {
            writer.write(userData[i]);

            System.out.println(userData[i]);

        }
        int columnNumber = userData[0].length() - userData[0].replaceAll(",", "").length() - 1;
        System.out.println(columnNumber);
        classificationProcessor.learn(file, columnNumber);
    }

    public Object classify(Instance instance) {
        return classificationProcessor.classify(instance);
    }


}
