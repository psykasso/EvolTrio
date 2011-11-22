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

/**
 * 
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public class DullFilter implements IFitnessFilter {

	/* (non-Javadoc)
	 * @see gr.evomusic.evoltrio.fitness.IFitnessFilter#evaluate(org.jgap.IChromosome, int[], int, int)
	 */
	@Override
	public double evaluate(MusicChromosome chromo) throws InvalidEvaluationException {
		// TODO find the correct value
		double[] notes = new double[1024];
		
		int[] intChromo = chromo.getAbsGenes();

		// initialize to 1; deended for the exponentional penalty

		for (int i = 0; i < notes.length; i++)
			notes[i] = 1;

		for (int i = 0; i < intChromo.length; i += 2) {
			// a try block for index array ...
			try {
				notes[intChromo[i]] *= 1.7;
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("intChromo[i]value is : " + intChromo[i]
						+ "\nThat should never happen");
			}
		}

		int penalty = 0;

		for (int i = 0; i < notes.length; i++)
			if (notes[i] > 2)
				penalty -= notes[i];

		return penalty;
	}

}
