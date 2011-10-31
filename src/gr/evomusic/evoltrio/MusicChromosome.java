/**
 * This file is part of EvolTrio.
 *
 * EvolTrio is licensed under the GPLv3.
 *
 * For licensing information please see the file license.txt included with EvolTrio
 * or have a look at the top of class gr.evoltrio.evomusic.Evolution which representatively
 * includes the EvolTrio license policy applicable for any file delivered with EvolTrio.
 */
package gr.evomusic.evoltrio;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;



/**
 *
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public class MusicChromosome extends Chromosome {

	/**
	 * Auto generated ...
	 */
	private static final long serialVersionUID = 6600135456863635173L;
	
	/**
	 * A representation of the chromosome using with absolute values
	 */
	private byte[] absoluteGenes;

	/**
	 * @param a_configuration
	 * @param a_initialGenes
	 * @throws InvalidConfigurationException
	 */
	public MusicChromosome(Configuration a_configuration, Gene[] a_initialGenes)
			throws InvalidConfigurationException {
		super(a_configuration, a_initialGenes);
		// TODO Auto-generated constructor stub
	}

}
