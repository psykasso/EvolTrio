/**
 * This file is part of EvolTrio.
 *
 * EvolTrio is licensed under the GPLv3.
 *
 * For licensing information please see the file license.txt included with EvolTrio
 * or have a look at the top of class gr.evoltrio.evomusic.Evolution which representatively
 * includes the EvolTrio license policy applicable for any file delivered with EvolTrio.
 */
package gr.evomusic.evoltrio.ui;

import org.apache.pivot.charts.LineChartView;
import org.apache.pivot.charts.content.Point;
import org.apache.pivot.charts.content.ValueSeries;
import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.List;
import org.jgap.Genotype;
import org.jgap.InvalidConfigurationException;

import gr.evomusic.evoltrio.core.EvolConfiguration;
import gr.evomusic.evoltrio.core.Evolution;
import gr.evomusic.evoltrio.core.MusicChromosome;
import gr.evomusic.evoltrio.midi.MusicConfiguration;

/**
 * 
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public class EvolutionRunner extends Evolution implements Runnable {

	private List<ValueSeries<Point>> data;

	private EvolTrioUI evolTrioUI;

	private boolean isEvolutionRunning = false;

	Point point;

	public EvolutionRunner(EvolTrioUI evolTrioUI, String[] args) {
		super(args);
		this.evolTrioUI = evolTrioUI;
		data = new ArrayList<ValueSeries<Point>>();
		// create the series according to the music pattern
		for (String pattern : musicConf.getChordProgression())
			data.add(new ValueSeries<Point>(pattern));

		for (ValueSeries<Point> serie : data)
			serie.ensureCapacity(((EvolConfiguration) evolConf).getIterations());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		int iterations = ((EvolConfiguration) evolConf).getIterations();
		isEvolutionRunning = true;
		// ChartUpdater chartUpdater = new ChartUpdater("ChartUpdater");

		for (int i = 0; i < phrases.length; i++) {
			// try to generate an initial population for every phrase
			try {
				population = Genotype.randomInitialGenotype(evolConf);
			} catch (InvalidConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// generate chromosome-patterns
			for (int j = 0; j <= iterations; j++) {
				population.evolve();

				point = new Point();
				point.setX(j);
				point.setY((float) population.getFittestChromosome()
						.getFitnessValue());
				data.get(i).insert(point, j);
				evolTrioUI.getEvolutionChart().setChartData(data);
				
				evolTrioUI.getEvolutionMeter().setPercentage((double)((i*iterations)+j)/(double)(iterations*phrases.length));

				System.out.println("phrase : " + i + " -- iteration : " + j
						+ " -- fittest : "
						+ population.getFittestChromosome().getFitnessValue());
			}

			phrases[i] = (MusicChromosome) population.getFittestChromosome();

		}
		System.out.println();
		isEvolutionRunning = false;
		System.out.println(data);
//		evolTrioUI.getEvolutionIndicator().setActive(false);
		evolTrioUI.getEvolutionChart().setVisible(true);
		evolTrioUI.getEvolutionChart().repaint();
		evolTrioUI.getEvolutionChart().setX(0);
		evolTrioUI.getEvolutionChart().setY(0);
		evolTrioUI.getEvolutionChart().setPreferredHeight(200);
		evolTrioUI.getEvolutionChart().setPreferredWidth(200);
		}

}
