package gr.evoltrio.ui;

import gr.evoltrio.core.Evolution;
import gr.evoltrio.midi.MusicConfiguration;

import org.apache.pivot.charts.LineChartView;
import org.apache.pivot.charts.content.Point;
import org.apache.pivot.charts.content.ValueSeries;
import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.List;
import org.apache.pivot.util.concurrent.Task;
import org.apache.pivot.util.concurrent.TaskExecutionException;

public class EvolutionTask extends Task<String> {

    public static final int PAUSED = 0;
    public static final int RUNNING = 1;
    public static final int RESET = 2;

    private int state;

    private List<ValueSeries<Point>> data;
    private EvolTrioUI evolTrioUI;
    private Evolution evolution;
    private LineChartView evolutionChart;

    //TODO change this
    public int iteration;

    public EvolutionTask(EvolTrioUI evolTrioUI, LineChartView evolutionChart,
            String[] args) {
        evolution = new Evolution();
        this.evolutionChart = evolutionChart;

        this.evolTrioUI = evolTrioUI;

        data = new ArrayList<ValueSeries<Point>>();

        data.add(new ValueSeries<Point>("Best Chromosome"));
        evolutionChart.setChartData(data);

        iteration = 0;
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
            Point point = new Point();
            point.setX(iteration);
            double fitness = evolution.evolveOnce();
            point.setY((float) fitness);

            //data.get(0).insert(point, iteration);
            ((ValueSeries<Point>)(evolutionChart.getChartData().get(0))).add(point);
            //evolutionChart.setChartData(data);
//            if(iteration % 10 == 0){
//                evolutionChart.invalidate();
//                evolutionChart.getParent().repaint(0,0,evolutionChart.getParent().getWidth(),evolutionChart.getParent().getWidth());
//            }

            evolTrioUI.getIterationLabel().setText("" + iteration);
            evolTrioUI.getFitnessLabel().setText("" + fitness);

            iteration++;
        }

        if (state == EvolutionTask.RESET)
            evolTrioUI.getIterationLabel().setText("" + 0);

        return null;
    }

    public Evolution getEvolution() {
        return evolution;
    }
    
    public int getState() {
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
    
    public int getIteration() {
        return iteration;
    }

}
