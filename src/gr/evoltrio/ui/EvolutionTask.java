package gr.evoltrio.ui;

import gr.evoltrio.core.Evolution;
import gr.evoltrio.midi.MusicConfiguration;

import org.apache.pivot.charts.LineChartView;
import org.apache.pivot.charts.content.Point;
import org.apache.pivot.charts.content.ValueSeries;
import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.List;
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
        evolution = new Evolution(args);
        this.evolutionChart = evolutionChart;

        this.evolTrioUI = evolTrioUI;

        List nonSyncList = new ArrayList<ValueSeries<Point>>();
        data = new SynchronizedList<ValueSeries<Point>>(nonSyncList);
        data.add(new ValueSeries<Point>("cool"));

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
                // data.get(0).insert(point, iteration);
                //
                // evolutionChart.setChartData(data);
         
                // evolutionChart.repaint(true);
    
                ValueSeries<Point> ser1 = data.get(0);
                ser1.add(point);
                data.update(0, ser1);
    
                evolutionChart.setChartData(data);
    
                evolTrioUI.getIterationLabel().setText("" + iteration);
                evolTrioUI.getFitnessLabel().setText("" + fitness);
    
                iteration++;
        }
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
