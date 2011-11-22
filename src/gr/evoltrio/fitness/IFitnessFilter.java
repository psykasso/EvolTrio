/**
 * This file is part of EvolTrio.
 *
 * EvolTrio is licensed under the GPLv3.
 *
 * For licensing information please see the file license.txt included with EvolTrio
 * or have a look at the top of class gr.evoltrio.core.Evolution which representatively
 * includes the EvolTrio license policy applicable for any file delivered with EvolTrio.
 */
package gr.evoltrio.fitness;

import gr.evoltrio.core.MusicChromosome;
import gr.evoltrio.exception.InvalidEvaluationException;

import org.jgap.IChromosome;

/**
 * The base interface for every Fitness Filter.
 * 
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public interface IFitnessFilter {

	
	/**
	 * Evaluate a music chromosome.
	 * 
	 * @param chromo The chromosome to evaluate
	 * @return The fitness value for the implemented fitness filter.
	 * @throws InvalidEvaluationException
	 */
	public double evaluate(MusicChromosome chromo) throws InvalidEvaluationException;
}
