package logic;


import net.sf.javaml.core.DenseInstance;

import net.sf.javaml.core.Instance;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class StatisticsAnalyzer {
    private StatisticsUpdater statisticsUpdater;
    private ClassificationProcessor classificationProcessor;
    private String src;

    public StatisticsAnalyzer(StatisticsUpdater statisticsUpdater, ClassificationProcessor classificationProcessor, String src) {
        this.statisticsUpdater = statisticsUpdater;
        this.classificationProcessor = classificationProcessor;
        this.src = src;
    }

    public void learn(String... categories) throws IOException {

        String[] userData = statisticsUpdater.getData(categories);
        int length = userData.length;
        File file = new File(src);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (int i = 0; i < length - 1; i++) {
            writer.write(userData[i]);
            writer.flush();
        }
        String data = userData[userData.length - 1];
        writer.write(data.substring(0, data.length() - 1));
        writer.close();
        int columnNumber = userData[0].length() - userData[0].replaceAll(",", "").length() - 1;
        classificationProcessor.learn(file, columnNumber);
    }

    public Object classify(Instance instance) {
        return classificationProcessor.classify(instance);
    }

    public String[] getCategories(double[] data, int amount) throws IOException {
//        statisticsUpdater.run();
        String[] result = new String[amount];
        Instance instance = new DenseInstance(data);
        for (int i = 0; i < amount; i++) {
            learn(result);
            String cat = (String) classify(instance);
            result[i] = cat;
        }
        return result;
    }

    public static void main(String[] args) throws IOException {

        StatisticsAnalyzer analyzer = new StatisticsAnalyzer(new StatisticsUpdater(), new ClassificationProcessor(), "/home/vladislav/data.data");

//        Dataset data = FileHandler.loadDataset(new File("C:\\Users\\Администратор\\Desktop\\iris.data"), 4, ",");


        String[] prediction = analyzer.getCategories(new double[]{100, 300, 200}, 8);
        for(String s : prediction){

            System.out.println(s);
        }
//        for(int i = 0; i <= 1; i++){
//            for(int j = 1; j <= 50; j++){
//                for(int k = 1; k < 3; k++) {
//                    Object o = analyzer.classify(new DenseInstance(new double[]{i*100, j, k*100}));
//                    System.out.println(i + ", " + j + ", " + k + " = " + o);
//                }
//            }
//        }
    }
}

