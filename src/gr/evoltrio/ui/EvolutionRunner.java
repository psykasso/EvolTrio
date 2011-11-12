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

import java.util.Collections;

import gr.evoltrio.core.EvolConfiguration;
import gr.evoltrio.core.Evolution;
import gr.evoltrio.core.MusicChromosome;
import gr.evoltrio.midi.MusicConfiguration;

import org.apache.pivot.charts.LineChartView;
import org.apache.pivot.charts.content.Point;
import org.apache.pivot.charts.content.ValueSeries;
import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.concurrent.SynchronizedList;
import org.apache.pivot.collections.List;
import org.apache.pivot.util.ListenerList;
import org.apache.pivot.wtk.ComponentListener;
import org.apache.pivot.wtk.ComponentMouseListener;
import org.jgap.Genotype;
import org.jgap.InvalidConfigurationException;

/**
 * 
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public class EvolutionRunner implements Runnable {
    
    public static final int PAUSED = 0;
    public static final int RUNNING = 1;
    public static final int RESET = 2;

    private List<ValueSeries<Point>> data;

    private EvolTrioUI evolTrioUI;

    private Evolution evolution;
    
    private LineChartView evolutionChart;

    private boolean isEvolutionRunning = false;

    private Point point;
    
    private int iteration = 0;

    public EvolutionRunner(EvolTrioUI evolTrioUI, LineChartView evolutionChart, String[] args) {
        evolution = new Evolution(args);
        this.evolutionChart = evolutionChart;
        evolution.setup();
        this.evolTrioUI = evolTrioUI;
        
        List nonSyncList = new ArrayList<ValueSeries<Point>>();
        data = new SynchronizedList<ValueSeries<Point>>(nonSyncList);
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
        
        isEvolutionRunning = true;
        // ChartUpdater chartUpdater = new ChartUpdater("ChartUpdater");

        // generate chromosome-patterns
        while ( true && isEvolutionRunning ) {
            
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }

            point = new Point();
            point.setX(iteration);
            double fitness = evolution.evolveOnce();
            point.setY((float) fitness);
            //System.out.println(point.getY());
            data.get(0).insert(point, iteration);
            //evolutionChart.setHeightLimits(10000, 10020);
            
            evolutionChart.setChartData(data);
            
            //evolutionChart.get
            
            //evolutionChart.repaint();
            //evolutionChart.setPreferredHeight(200);
            //evolutionChart.setSize(200,200);
            //evolutionChart.setPreferredWidth(200);
            
            
//            evolTrioUI.getIterationLabel().setText("" + iteration);
//            evolTrioUI.getIterationLabel().requestFocus();
//            evolTrioUI.getFitnessLabel().setText("" + fitness);
//            
//            evolTrioUI.getIterationLabel().setPreferredHeight(100);
//            
//            ListenerList<ComponentMouseListener> listeners = evolTrioUI.getFitnessLabel().getComponentMouseListeners();
//            
//            for(ComponentMouseListener list: listeners){
//                System.out.println(list);
//                list.mouseMove(evolTrioUI.getIterationLabel(), 0, 0);
//            }
                
            
            
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

    public synchronized int getIteration() {
        return iteration;
    }
    
    
    

}
