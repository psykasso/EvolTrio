/**
 * This file is part of EvolTrio.
 *
 * EvolTrio is licensed under the GPLv3.
 *
 * For licensing information please see the file license.txt included with EvolTrio
 * or have a look at the top of class gr.evoltrio.evomusic.Evolution which representatively
 * includes the EvolTrio license policy applicable for any file delivered with EvolTrio.
 */
package gr.evoltrio.fitness.impl;

import gr.evoltrio.core.MusicChromosome;
import gr.evoltrio.exception.InvalidEvaluationException;
import gr.evoltrio.fitness.IFitnessFilter;

/**
 *
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public class AscendingFilter implements IFitnessFilter {

	/* (non-Javadoc)
	 * @see gr.evomusic.evoltrio.fitness.IFitnessFilter#evaluate(org.jgap.IChromosome, int[], int, int)
	 */
	@Override
	public double evaluate(MusicChromosome chromo) throws InvalidEvaluationException {
		
		int bonus = 0;
		
		int[] intChromo = chromo.getAbsGenes();
		
		for(int i=2; i<intChromo.length-4; i+=4){
			if(intChromo[i-2]<intChromo[i] && intChromo[i]<intChromo[i+2] && intChromo[i+2]<intChromo[i+4] && intChromo[i+2]<intChromo[i+4])
				bonus += 10;
			else if(intChromo[i-2]<intChromo[i] && intChromo[i]<intChromo[i+2] && intChromo[i+2]<intChromo[i+4])
				bonus += 5;
			else if(intChromo[i-2]<intChromo[i] && intChromo[i]<intChromo[i+2])
				bonus += 2;
		}
		
		return bonus;
	}

}
