/**
 * This file is part of EvolTrio.
 *
 * EvolTrio is licensed under the GPLv3.
 *
 * For licensing information please see the file license.txt included with EvolTrio
 * or have a look at the top of class gr.evoltrio.evomusic.Evolution which representatively
 * includes the EvolTrio license policy applicable for any file delivered with EvolTrio.
 */
package gr.evomusic.evoltrio.fitness;

import gr.evomusic.evoltrio.core.MusicChromosome;
import gr.evomusic.evoltrio.exception.InvalidEvaluationException;

import org.jgap.IChromosome;

/**
 * The base interface for any Fitness Filter.
 * 
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public interface IFitnessFilter {

	
	/**
	 * Evaluate this given chromosome.
	 * 
	 * @param chromo the chromosome to be evaluated
	 * @param intChromo a integer representation of the previous chromosome
	 * @param keyNote the pitch of the first note of the given chromosome 
	 * @param begginingDuration the duration of the given chromosome
	 * @return a value which represents the evaluation corresponding to the current class
	 * . This value can be both positive and negative.
	 * @throws InvalidEvaluationException is thrown when something is not working correctly
	 */
	public double evaluate(MusicChromosome chromo) throws InvalidEvaluationException;
}
