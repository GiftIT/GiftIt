package utilitiesfoserver;

import logic.StatisticsUpdater;

import java.util.Map;

/**
 * Created by Babenko on 16.12.2015.
 */
public class StatisticInit  {

    public static void main(String[] args) {
        Thread statistic= new Thread(new StatisticsUpdater());
        statistic.start();
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void runStatistiCollector(){
        Thread statistic= new Thread(new StatisticsUpdater());
        statistic.start();

        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
