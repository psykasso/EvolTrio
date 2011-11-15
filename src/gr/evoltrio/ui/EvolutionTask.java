package gr.evoltrio.ui;

import java.util.Comparator;

import gr.evoltrio.core.Evolution;
import gr.evoltrio.midi.MusicConfiguration;

import org.apache.pivot.charts.LineChartView;
import org.apache.pivot.charts.content.Point;
import org.apache.pivot.charts.content.ValueSeries;
import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.List;
import org.apache.pivot.collections.ListListener;
import org.apache.pivot.collections.Sequence;
import org.apache.pivot.collections.concurrent.SynchronizedList;
import org.apache.pivot.util.concurrent.Task;
import org.apache.pivot.util.concurrent.TaskExecutionException;
import org.apache.pivot.wtk.Label;

public class EvolutionTask extends Task<String> {

    public static final int PAUSED = 0;
    public static final int RUNNING = 1;
    public static final int RESET = 2;

    private int state;

    private List<ValueSeries<Point>> data;
    private EvolTrioUI evolTrioUI;
    private Evolution evolution;
    private LineChartView evolutionChart;
    private Point point;
    private int iteration = 0;

    public EvolutionTask(EvolTrioUI evolTrioUI, LineChartView evolutionChart,
            String[] args) {
        evolution = new Evolution();
        this.evolutionChart = evolutionChart;

        this.evolTrioUI = evolTrioUI;

        List nonSyncList = new ArrayList<ValueSeries<Point>>();
        data = new SynchronizedList<ValueSeries<Point>>(nonSyncList);
        
        data.add(new ValueSeries<Point>("cool"));
        
        data.getListListeners().add(new ListListener.Adapter<ValueSeries<Point>>() {

            @Override
            public void itemInserted(List<ValueSeries<Point>> list, int index) {
                System.out.println("cool!!");
            }
            
        });

        state = EvolutionTask.RESET;
    }
    
    public void setupEvolutionTask() {
          evolution.setup();

        System.out.println(evolution.getEvolConf());
        System.out.println(MusicConfiguration.getInstance());
        System.out.println(evolution.getSoloFitness());
    }

    @Override
    public String execute() throws TaskExecutionException {
        

        while (state == EvolutionTask.RUNNING) {
            
            
                point = new Point();
                point.setX(iteration);
                double fitness = evolution.evolveOnce();
                point.setY((float) fitness);
                
//                if(iteration % 50 == 0) {
//                data.add(new ValueSeries<Point>("iteration :" + iteration));
                data.get(0).insert(point, iteration);
                evolutionChart.setChartData(data);
//                }
                evolTrioUI.getIterationLabel().setText("" + iteration);
                evolTrioUI.getFitnessLabel().setText("" + fitness);
    
                iteration++;

//                System.out.println(evolution.getPopulation().getFittestChromosome().getFitnessValue());
        }
        
        if(state == EvolutionTask.RESET)
            evolTrioUI.getIterationLabel().setText("" + 0);
        
        return null;
    }

    public Evolution getEvolution() {
        return evolution;
    }
    
    public synchronized void setState(int state) {
        this.state = state;
    }
    
    public synchronized int getState() {
        return state;
    }

    public void pause() {
        state = EvolutionTask.PAUSED;
    }

    public void start() {
        state = EvolutionTask.RUNNING;
    }

    public void reset() {
        state = EvolutionTask.RESET;
    }

}
