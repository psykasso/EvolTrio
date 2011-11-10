/**
 * This file is part of EvolTrio.
 *
 * EvolTrio is licensed under the GPLv3.
 *
 * For licensing information please see the file license.txt included with EvolTrio
 * or have a look at the top of class gr.evoltrio.evomusic.Evolution which representatively
 * includes the EvolTrio license policy applicable for any file delivered with EvolTrio.
 */
package gr.evoltrio.ui;

import gr.evoltrio.core.EvolConfiguration;
import gr.evoltrio.core.Evolution;
import gr.evoltrio.core.MusicChromosome;
import gr.evoltrio.midi.MusicConfiguration;

import org.apache.pivot.charts.LineChartView;
import org.apache.pivot.charts.content.Point;
import org.apache.pivot.charts.content.ValueSeries;
import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.List;
import org.jgap.Genotype;
import org.jgap.InvalidConfigurationException;

/**
 * 
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public class EvolutionRunner implements Runnable {

    private List<ValueSeries<Point>> data;

    private EvolTrioUI evolTrioUI;

    private Evolution evolution;
    
    private LineChartView evolutionChart;

    private boolean isEvolutionRunning = false;

    Point point;

    public EvolutionRunner(EvolTrioUI evolTrioUI, LineChartView evolutionChart, String[] args) {
        evolution = new Evolution(args);
        this.evolutionChart = evolutionChart;
        evolution.setupOnce();
        this.evolTrioUI = evolTrioUI;
        data = new ArrayList<ValueSeries<Point>>();
        data.add(new ValueSeries<Point>("cool"));

        // for (ValueSeries<Point> serie : data)
        // serie.ensureCapacity(((EvolConfiguration) evolConf).getIterations());
        data.get(0).ensureCapacity(1000);

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        // int iterations = ((EvolConfiguration) evolConf).getIterations();
        int iteration = 0;
        isEvolutionRunning = true;
        // ChartUpdater chartUpdater = new ChartUpdater("ChartUpdater");

        // generate chromosome-patterns
        while ( true && isEvolutionRunning ) {
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            point = new Point();
            point.setX(iteration);
            point.setY((float) evolution.evolveOnce());
            System.out.println(point.getY());
            data.get(0).insert(point, iteration);
            evolutionChart.setChartData(data);
            //evolutionChart.repaint();
            evolutionChart.setPreferredHeight(200);
            //evolutionChart.setSize(200,200);
            evolutionChart.setPreferredWidth(200);
            
            
            iteration++;
            // evolTrioUI.getEvolutionMeter().setPercentage((double)((i*iterations)+j)/(double)(iterations*phrases.length));

            // System.out.println("phrase : " + i + " -- iteration : " + j
            // + " -- fittest : "
            // + population.getFittestChromosome().getFitnessValue());
        }

        // phrases[i] = (MusicChromosome) population.getFittestChromosome();

        // System.out.println();
        // isEvolutionRunning = false;
        // System.out.println(data);
        // evolTrioUI.getEvolutionIndicator().setActive(false);
        // evolTrioUI.getEvolutionChart().setVisible(true);
        // evolTrioUI.getEvolutionChart().repaint();
        // evolTrioUI.getEvolutionChart().setX(0);
        // evolTrioUI.getEvolutionChart().setY(0);
        // evolTrioUI.getEvolutionChart().setPreferredHeight(200);
        // evolTrioUI.getEvolutionChart().setPreferredWidth(200);
    }

    public boolean isEvolutionRunning() {
        return isEvolutionRunning;
    }

    public void setEvolutionRunning(boolean isEvolutionRunning) {
        this.isEvolutionRunning = isEvolutionRunning;
    }
    
    

}
