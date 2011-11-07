/**
 * This file is part of EvolTrio.
 *
 * EvolTrio is licensed under the GPLv3.
 *
 * For licensing information please see the file license.txt included with EvolTrio
 * or have a look at the top of class gr.evoltrio.evomusic.Evolution which representatively
 * includes the EvolTrio license policy applicable for any file delivered with EvolTrio.
 */
package gr.evoltrio.fitness;

import gr.evoltrio.core.MusicChromosome;
import gr.evoltrio.exception.InvalidEvaluationException;

import java.util.HashMap;
import java.util.Map;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

/**
 * 
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public class SoloFitnessEvol extends FitnessFunction {

	/**
	 * TODO What is this ?
	 */
	private static final long serialVersionUID = -2021750101153709817L;
	private int keyNote;
	private int begginingDuration;

	private Map<FiltersFactory.Filter, IFitnessFilter> filters;

	public SoloFitnessEvol() {
		filters = new HashMap<FiltersFactory.Filter, IFitnessFilter>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgap.FitnessFunction#evaluate(org.jgap.IChromosome)
	 */
	@Override
	protected double evaluate(IChromosome chromo) {

		// TODO change to a different base
		double evaluation = 10000;

		// update abs genes for each chromosome
		((MusicChromosome) chromo).updateAbsolute();

		for (IFitnessFilter filter : filters.values())
			try {

				// TODO asign negative values to penalties
				evaluation += filter.evaluate((MusicChromosome) chromo);
			} catch (InvalidEvaluationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return evaluation;
	}

	public void addFilter(FiltersFactory.Filter filter) {
		filters.put(filter, FiltersFactory.getFilter(filter));
	}

}
