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
public class RootNoteFilter implements IFitnessFilter {

	/* (non-Javadoc)
	 * @see gr.evomusic.evoltrio.fitness.IFitnessFilter#evaluate(org.jgap.IChromosome, int[], int, int)
	 */
	@Override
	public double evaluate(MusicChromosome chromo) throws InvalidEvaluationException {
			
		int[] intChromo = chromo.getAbsGenes();
		int curNote = -1;
		int rootNote = intChromo[0] % MusicConfiguration.getInstance().getScaleValuesCnt();
		int bonus = 0;
		
		for(int i=2; i<intChromo.length; i+=2){
			curNote = intChromo[i] % MusicConfiguration.getInstance().getScaleValuesCnt();
			
			if(curNote == rootNote)
				bonus += 4;
		}
		return bonus;
	}

}
