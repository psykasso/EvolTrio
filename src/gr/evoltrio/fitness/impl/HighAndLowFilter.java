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
import gr.evoltrio.midi.MusicConfiguration;

/**
 *
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public class HighAndLowFilter implements IFitnessFilter {

	/* (non-Javadoc)
	 * @see gr.evomusic.evoltrio.fitness.IFitnessFilter#evaluate(org.jgap.IChromosome, int[], int, int)
	 */
	@Override
	public double evaluate(MusicChromosome chromo) throws InvalidEvaluationException {
		int penalty = 0;
		
		int[] intChromo = chromo.getAbsGenes();
		
		int keyNote = MusicConfiguration.getInstance().getKeyNote();
		
		for(int i=0; i<intChromo.length; i+=2)
			if(intChromo[i] > keyNote + MusicConfiguration.NOTES.length*3 || intChromo[i] < keyNote - MusicConfiguration.NOTES.length*3)
				penalty -= 130;
			else if(intChromo[i] > keyNote + MusicConfiguration.NOTES.length*2 || intChromo[i] < keyNote - MusicConfiguration.NOTES.length*2)
				penalty -= 30;
			else if(intChromo[i] > keyNote + MusicConfiguration.NOTES.length || intChromo[i] < keyNote - MusicConfiguration.NOTES.length)
				penalty -= 5;
		
		return penalty;
	}

}
