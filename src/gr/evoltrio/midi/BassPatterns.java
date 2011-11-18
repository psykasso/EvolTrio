/**
 * This file is part of EvolTrio.
 *
 * EvolTrio is licensed under the GPLv3.
 *
 * For licensing information please see the file license.txt included with EvolTrio
 * or have a look at the top of class gr.evoltrio.evomusic.Evolution which representatively
 * includes the EvolTrio license policy applicable for any file delivered with EvolTrio.
 */
package gr.evoltrio.midi;

import gr.evoltrio.core.EvolConfiguration;

import org.jfugue.Pattern;
import org.jgap.Chromosome;
import org.jgap.Gene;
import org.jgap.impl.IntegerGene;

/**
 * 
 * @author psykasso
 */
public class BassPatterns {

	private MusicConfiguration musicConf;
	private String pattern;
	private double[] phrasesDuration;

	public BassPatterns(MusicConfiguration musicConf, double[] phrasesDuration) {

		this.musicConf = musicConf;
		this.phrasesDuration = phrasesDuration;
		pattern = new String("V1 I[35] ");

	}

	public Pattern getBassPattern() {
		
		for (int i = 0; i < phrasesDuration.length; i++) {

			for (int j = (int) (phrasesDuration[i] / 4); j > 0; j--)
				pattern = pattern
						.concat(MusicFactory.getJFugueNote(MusicConfiguration.getInstance().getKeyNote()
								+ MusicConfiguration.MAJOR_INTERVALS[MusicConfiguration.CHORDS
										.get(musicConf.getChordProgression()[i])])
								+ "3w ");
		}

		return new Pattern(pattern);
	}

	public static void main(String args[]) throws Exception {
		EvolConfiguration evolConf = new EvolConfiguration();
		int maxJump = 5; // for testing purposes it will be used for both
							// interval and duration

		Gene[] sampleGenes1 = new Gene[12];

		for (int i = 0; i < sampleGenes1.length; i++)
			sampleGenes1[i] = new IntegerGene(evolConf, -maxJump, maxJump);
		/*
		 * sampleGenes1[0].setAllele(new Integer(2));
		 * sampleGenes1[1].setAllele(new Integer(1));
		 * sampleGenes1[2].setAllele(new Integer(0));
		 * sampleGenes1[3].setAllele(new Integer(0));
		 * sampleGenes1[4].setAllele(new Integer(-2));
		 * sampleGenes1[5].setAllele(new Integer(-1));
		 * sampleGenes1[6].setAllele(new Integer(2));
		 * sampleGenes1[7].setAllele(new Integer(1));
		 * sampleGenes1[8].setAllele(new Integer(0));
		 * sampleGenes1[9].setAllele(new Integer(0));
		 * 
		 * sampleGenes1[10].setAllele(new Integer(0));
		 * sampleGenes1[11].setAllele(new Integer(2));
		 */
		for (int i = 0; i < sampleGenes1.length; i++) {
			sampleGenes1[i].setAllele(new Integer(2));
		}

		Chromosome chromos[] = new Chromosome[1];
		chromos[0] = new Chromosome(evolConf, sampleGenes1);

		// BassPatterns bp = new BassPatterns("default", "I-IV-V-I", chromos,1,
		// 72, 3);

		// System.out.println(bp.getBassPattern());
	}
}
