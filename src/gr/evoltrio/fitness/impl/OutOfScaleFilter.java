/**
 * This file is part of EvolTrio.
 *
 * EvolTrio is licensed under the GPLv3.
 *
 * For licensing information please see the file license.txt included with EvolTrio
 * or have a look at the top of class gr.evoltrio.core.Evolution which representatively
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
public class OutOfScaleFilter implements IFitnessFilter {

	/* (non-Javadoc)
	 * @see gr.evomusic.evoltrio.fitness.IFitnessFilter#evaluate(org.jgap.IChromosome, int[], int, int)
	 */
	@Override
	public double evaluate(MusicChromosome chromo) throws InvalidEvaluationException {
		int curNote;
		int penalties = 0;
		
		int[] intChromo = chromo.getAbsGenes();
		int scaleValues = MusicConfiguration.getInstance().getScaleValuesCnt();

		// offset is needed for the cross-scale check
		int offset = MusicConfiguration.getInstance().getKeyNote() % scaleValues;

		for (int i = 0; i < intChromo.length; i += 2) {
			curNote = intChromo[i] % scaleValues;

			// TODO 1+offset modules 12 ?????
			if (curNote == ((1 + offset) % scaleValues)
					|| curNote == ((3 + offset) % scaleValues)
					|| curNote == ((6 + offset) % scaleValues)
					|| curNote == ((8 + offset) % scaleValues)
					|| curNote == ((10 + offset) % scaleValues))
				penalties -= 20;
		}

		return penalties;
	}

}
