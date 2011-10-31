/**
 * This file is part of EvolTrio.
 *
 * EvolTrio is licensed under the GPLv3.
 *
 * For licensing information please see the file license.txt included with EvolTrio
 * or have a look at the top of class gr.evoltrio.evomusic.Evolution which representatively
 * includes the EvolTrio license policy applicable for any file delivered with EvolTrio.
 */
package gr.evomusic.evoltrio.fitness.impl;

import gr.evomusic.evoltrio.core.MusicChromosome;
import gr.evomusic.evoltrio.exception.InvalidEvaluationException;
import gr.evomusic.evoltrio.fitness.IFitnessFilter;

/**
 *
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public class SimpleDurationFilter implements IFitnessFilter {

	/* (non-Javadoc)
	 * @see gr.evomusic.evoltrio.fitness.IFitnessFilter#evaluate(org.jgap.IChromosome, int[], int, int)
	 */
	@Override
	public double evaluate(MusicChromosome chromo) throws InvalidEvaluationException {
		
//		int durationPenalties = 0;
//		
//		for(int i=0; i<chromo.size(); i+=2)
//			durationPenalties -= ((Integer)(chromo.getGene(i+1).getAllele())).intValue();
//		
//		return Math.abs(durationPenalties)*3;
		
		double avg = 0;
		
		for(int i=1; i<chromo.size(); i+=2)
			avg += ((Integer)(chromo.getGene(i).getAllele())).intValue();
		
		return (-1)*Math.abs(avg);
	}

}
