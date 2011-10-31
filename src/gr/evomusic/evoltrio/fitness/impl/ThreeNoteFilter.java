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
public class ThreeNoteFilter implements IFitnessFilter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gr.evomusic.evoltrio.fitness.IFitnessFilter#evaluate(org.jgap.IChromosome
	 * , int[], int, int)
	 */
	@Override
	public double evaluate(MusicChromosome chromo)
			throws InvalidEvaluationException {
		// applicable only if our melody has 6 notes or a multiplier of 6
		// TODO correct this. Because it can be used for e.g 8 notes
		int[] intChromo = chromo.getAbsGenes();
		if (intChromo.length % 12 != 0 || intChromo.length < 12)
			return 0;

		int bonus = 0;
		int curFirst, curSecond, curThird;
		int firstNote = intChromo[0];
		int secondNote = intChromo[2];
		int thirdNote = intChromo[4];

		if (firstNote == secondNote)
			bonus -= 40;
		if (firstNote == thirdNote)
			bonus -= 40;

		for (int i = 6; i < intChromo.length; i += 6) {

			curFirst = intChromo[i] % 12;
			if (curFirst == firstNote)
				bonus += 15;

			curSecond = intChromo[i + 2] % 12;
			if (curSecond == secondNote)
				bonus += 15;

			curThird = intChromo[i + 4] % 12;
			if (curThird == thirdNote)
				bonus += 15;
		}

		int firstDur = intChromo[1];
		int secondDur = intChromo[3];
		int thirdDur = intChromo[5];

		for (int i = 7; i < intChromo.length; i += 6) {

			curFirst = intChromo[i];
			if (curFirst == firstDur)
				bonus += 5;

			curSecond = intChromo[i + 2];
			if (curSecond == secondDur)
				bonus += 5;

			curThird = intChromo[i + 4];
			if (curThird == thirdDur)
				bonus += 5;
		}

		return bonus;
	}

}
