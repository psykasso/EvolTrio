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
import gr.evomusic.evoltrio.midi.MusicConfiguration;

/**
 *
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public class TimeFilter implements IFitnessFilter {

	/* (non-Javadoc)
	 * @see gr.evomusic.evoltrio.fitness.IFitnessFilter#evaluate(org.jgap.IChromosome, int[], int, int)
	 */
	@Override
	public double evaluate(MusicChromosome chromo) throws InvalidEvaluationException {
		double time = 0;
		
		int[] intChromo = chromo.getAbsGenes();
		
		for(int i=1; i<intChromo.length; i+=2){
			time += (Double) MusicConfiguration.DURATION_VALUES.values().toArray()[intChromo[i]];
//			switch(intChromo[i]){
//			case 1: time += 4; break;
//			case 2: time += 2; break;
//			case 3: time += 1; break;
//			case 4: time += 0.5; break;
//			case 5: time += 0.25; break;
//			case 6: time += 0.125; break;
//			}
		
		}
		
		if(time%4 != 0 )
			return -100;
		else 
			return 0;
	}

}
