/**
 * This file is part of EvolTrio.
 *
 * EvolTrio is licensed under the GPLv3.
 *
 * For licensing information please see the file license.txt included with EvolTrio
 * or have a look at the top of class gr.evoltrio.evomusic.Evolution which representatively
 * includes the EvolTrio license policy applicable for any file delivered with EvolTrio.
 */
package gr.evoltrio.core;

import gr.evoltrio.midi.MusicConfiguration;
import gr.evoltrio.tools.MusicFactory;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map.Entry;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.IChromosomePool;
import org.jgap.ICloneHandler;
import org.jgap.IJGAPFactory;
import org.jgap.InvalidConfigurationException;
import org.jgap.RandomGenerator;

/**
 * 
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public class MusicChromosome extends Chromosome {

	/**
	 * Default serial version uid.
	 */
	// private static final long serialVersionUID

	/**
	 * 
	 */
	private static final long serialVersionUID = -832453268482016286L;

	/**
	 * A representation of the genes in absolute values according to the key
	 * note and the beginning duration.
	 */
	private int[] absGenes;

	/**
	 * The duration of the current chromosome in the means of the JFugue.
	 */
	private double duration;

	/**
	 * A checker for the update process.
	 */
	private boolean isAbsUpdates;

	/**
	 * A jfugue pattern representation of the chromsome.
	 */
	private String jFuguePattern;

	/**
	 * A rounded to 4/4 pattern
	 */
	private String jFugueRoundedPattern;
	
	  /**
	   * Holds multiobjective values.
	   *
	   * @since 2.6
	   * @todo move to new subclass of Chromosome (and introduce new interface
	   * IMultiObjective with that)
	   */
	  private List m_multiObjective;

	/**
	 * @param conf
	 * @param genes
	 * @throws InvalidConfigurationException
	 */
	public MusicChromosome(Configuration conf, Gene[] genes)
			throws InvalidConfigurationException {
		super(conf, genes);
	}

	/**
	 * Updates the absGenes array that is needed for the fitness filters
	 * evaluation process and calculates the duration.
	 */
	public void updateAbsolute() {
		// if is not updated

			// initialize absolute genes array to the size of the chromosome
			absGenes = new int[size()];

			int currentPitch = MusicConfiguration.getInstance().getKeyNote();
			int currentDuration = MusicConfiguration.getInstance().getBeginningDuration();

			for (int i = 0; i < size(); i += 2) {

				// set the pitch
				currentPitch = currentPitch
						+ ((Integer) (getGene(i).getAllele())).intValue();
				// ensure that the pitch is not above 127 or less than 0
				if (currentPitch < 0) {
					// move one octave up
					currentPitch += MusicConfiguration.getInstance().getScaleValuesCnt();
				} else if (currentPitch > 127) {
					// move one octave down
					currentPitch -= MusicConfiguration.getInstance().getScaleValuesCnt();
				}

				absGenes[i] = currentPitch;

				// set the duration
				absGenes[i + 1] = calcDurationAtIndex((i + 1), currentDuration);
				currentDuration = absGenes[i + 1];

				// calculate the duration of the chromsome
				duration += (Double) MusicConfiguration.DURATION_VALUES
						.values().toArray()[absGenes[i + 1]];

			}

	}

	/**
	 * Calculates the current running duration according to where are we and
	 * what is the change.
	 * 
	 * @param currentDur
	 *            the current duration
	 * @param runningDur
	 *            the int representation of the duration change.
	 * @return the current duration.
	 */
	private int calcDurationAtIndex(int index, int currentDur) {
		int runningDur = ((Integer) (getGene(index).getAllele())).intValue();
		int dur = currentDur + runningDur;

		// the total available durations
		int durationSum = MusicConfiguration.DURATION_VALUES.size();

		if (dur >= durationSum)
			dur = dur - durationSum;
		else if (dur < 0)
			dur = durationSum + dur;

		// TODO find out what is the problem with the 3 parameters
		// return dur >=3 ? 2 : dur;
		return dur;
	}

	private void updateJFuguePattern() {

		String pattern = "";

		for (int i = 0; i < absGenes.length; i += 2) {
			pattern += MusicFactory.convertToJFugueNote(absGenes[i]);
			pattern += MusicFactory.convertToJFugueDuration(absGenes[i + 1]);
		}

		jFuguePattern = pattern;
	}

	public String getJFuguePattern() {
		//slightly more overhead but to be sure
		updateJFuguePattern();
		return jFuguePattern;
	}

	public void updateJFugueRoundedPattern() {
		updateJFuguePattern();
		if (duration % 4 == 0) {
			jFugueRoundedPattern = jFuguePattern;
		} else {
			jFugueRoundedPattern = jFuguePattern;
			double desiredDuration = (((int) duration / 4) + 1) * 4;
			// System.out.println(desiredDuration);
			while (Math.abs(desiredDuration - duration) > MusicConfiguration.getSmallestDuration()) {

				System.out.println("desired : " + desiredDuration);
				System.out.println("pattern : " + duration);
				for (Entry<String, Double> dur : MusicConfiguration.DURATION_VALUES
						.entrySet())
					if (desiredDuration >= duration + dur.getValue()) {
						duration += dur.getValue();
						jFugueRoundedPattern = jFugueRoundedPattern.concat("R"
								+ dur.getKey() + " ");
						break; // restart the for loop
					}

			}
		}
	}

	public String getJFugueRoundedPattern() {
		//same stuff
		updateJFugueRoundedPattern();
		return jFugueRoundedPattern;
	}
	
	public int[] getAbsGenes(){
		return absGenes;
	}
	
	@Override
	public Object perform(Object a_obj, Class a_class, Object a_params)
			throws Exception {
		return randomInitialMusicChromosome(getConfiguration());
	}

	public static IChromosome randomInitialMusicChromosome(
			Configuration a_configuration) throws InvalidConfigurationException {
		// Sanity check: make sure the given configuration isn't null.
		// -----------------------------------------------------------
		if (a_configuration == null) {
			throw new IllegalArgumentException(
					"Configuration instance must not be null");
		}
		// Lock the configuration settings so that they can't be changed
		// from now on.
		// -------------------------------------------------------------
		a_configuration.lockSettings();
		// First see if we can get a Chromosome instance from the pool.
		// If we can, we'll randomize its gene values (alleles) and then
		// return it.
		// -------------------------------------------------------------
		IChromosomePool pool = a_configuration.getChromosomePool();
		if (pool != null) {
			IChromosome randomChromosome = pool.acquireChromosome();
			if (randomChromosome != null) {
				Gene[] genes = randomChromosome.getGenes();
				RandomGenerator generator = a_configuration
						.getRandomGenerator();
				for (int i = 0; i < genes.length; i++) {
					genes[i].setToRandomValue(generator);
					/** @todo what about Gene's energy? */
				}
				randomChromosome
						.setFitnessValueDirectly(FitnessFunction.NO_FITNESS_VALUE);
				return randomChromosome;
			}
		}
		// We weren't able to get a Chromosome from the pool, so we have to
		// construct a new instance and build it from scratch.
		// ------------------------------------------------------------------
		IChromosome sampleChromosome = a_configuration.getSampleChromosome();
		sampleChromosome.setFitnessValue(FitnessFunction.NO_FITNESS_VALUE);
		Gene[] sampleGenes = sampleChromosome.getGenes();
		Gene[] newGenes = new Gene[sampleGenes.length];
		RandomGenerator generator = a_configuration.getRandomGenerator();
		for (int i = 0; i < newGenes.length; i++) {
			// We use the newGene() method on each of the genes in the
			// sample Chromosome to generate our new Gene instances for
			// the Chromosome we're returning. This guarantees that the
			// new Genes are setup with all of the correct internal state
			// for the respective gene position they're going to inhabit.
			// -----------------------------------------------------------
			newGenes[i] = sampleGenes[i].newGene();
			// If application data is set, try to clone it as well.
			// ----------------------------------------------------
			Object appData = sampleGenes[i].getApplicationData();
			if (appData != null) {
				try {
					cloneObject(a_configuration, appData, sampleChromosome);
				} catch (Exception ex) {
					throw new InvalidConfigurationException(
							"Application data of "
									+ "sample chromsome is not cloneable", ex);
				}
			}
			// Set the gene's value (allele) to a random value.
			// ------------------------------------------------
			newGenes[i].setToRandomValue(generator);
			/** @todo what about Gene's energy? */
		}
		// Finally, construct the new chromosome with the new random
		// genes values and return it.
		// ---------------------------------------------------------
		return new MusicChromosome(a_configuration, newGenes);
	}
	
	  /**
	   * Returns a copy of this Chromosopublic static IChromosome randomInitialMusicChromosomme. The returned instance can evolve
	   * independently of this instance. Note that, if possible, this method
	   * will first attempt to acquire a Chromosome instance from the active
	   * ChromosomePool (if any) and set its value appropriately before
	   * returning it. If that is not possible, then a new Chromosome instance
	   * will be constructed and its value set appropriately before returning.
	   *
	   * @return copy of this Chromosome
	   * @throws IllegalStateException instead of CloneNotSupportedException
	   *
	   * @author Neil Rotstan
	   * @author Klaus Meffert
	   * @since 1.0
	   */
	  public synchronized Object clone() {
	    // Before doing anything, make sure that a Configuration object
	    // has been set on this Chromosome. If not, then throw an
	    // IllegalStateException.
	    // ------------------------------------------------------------
	    if (getConfiguration() == null) {
	      throw new IllegalStateException(
	          "The active Configuration object must be set on this " +
	          "Chromosome prior to invocation of the clone() method.");
	    }
	    IChromosome copy = null;
	    // Now, first see if we can pull a Chromosome from the pool and just
	    // set its gene values (alleles) appropriately.
	    // ------------------------------------------------------------
	    IChromosomePool pool = getConfiguration().getChromosomePool();
	    if (pool != null) {
	      copy = pool.acquireChromosome();
	      if (copy != null) {
	        Gene[] genes = copy.getGenes();
	        for (int i = 0; i < size(); i++) {
	          genes[i].setAllele(getGene(i).getAllele());
	        }
	      }
	    }
	    try {
	      if (copy == null) {
	        // We couldn't fetch a Chromosome from the pool, so we need to create
	        // a new one. First we make a copy of each of the Genes. We explicity
	        // use the Gene at each respective gene location (locus) to create the
	        // new Gene that is to occupy that same locus in the new Chromosome.
	        // -------------------------------------------------------------------
	        int size = size();
	        if (size > 0) {
	          Gene[] copyOfGenes = new Gene[size];
	          for (int i = 0; i < size; i++) {
	            copyOfGenes[i] = getGene(i).newGene();
	            Object allele = getGene(i).getAllele();
	            if (allele != null) {
	              IJGAPFactory factory = getConfiguration().getJGAPFactory();
	              if (factory != null) {
	                ICloneHandler cloner = factory.
	                    getCloneHandlerFor(allele, allele.getClass());
	                if (cloner != null) {
	                  try {
	                    allele = cloner.perform(allele, null, this);
	                  } catch (Exception ex) {
	                    throw new RuntimeException(ex);
	                  }
	                }
	              }
	            }
	            copyOfGenes[i].setAllele(allele);
	          }
	          // Now construct a new Chromosome with the copies of the genes and
	          // return it. Also clone the IApplicationData object later on.
	          // ---------------------------------------------------------------
	          if (getClass() == MusicChromosome.class) {
	            copy = new MusicChromosome(getConfiguration(), copyOfGenes);
	          }
	          else {
	            try {
	              // Try dynamic call of constructor. Attention: This may not
	              // work for inner classes!
	              // --------------------------------------------------------
	              Constructor[] constr = getClass().getDeclaredConstructors();
	              if (constr != null) {
	                for (int i = 0; i < constr.length; i++) {
	                  Class[] params = constr[i].getParameterTypes();
	                  if (params != null && params.length == 1) {
	                    if (params[0] == Configuration.class) {
	                      copy = (IChromosome) constr[i].newInstance(new Object[] {
	                          getConfiguration()});
	                      copy.setGenes(copyOfGenes);
	                    }
	                  }
	                }
	              }
	              if (copy == null) {
	                // Enforce alternative cloning to get at least something.
	                // ------------------------------------------------------
	                throw new Exception(
	                    "No appropriate constructor for cloning found.");
	              }
	            } catch (Exception ex) {
	              ex.printStackTrace();
	              // Do it the old way with the danger of getting a stack overflow.
	              // --------------------------------------------------------------
	              copy = (IChromosome) getConfiguration().getSampleChromosome().clone();
	              copy.setGenes(copyOfGenes);
	            }
	          }
	        }
	        else {
	          if (getClass() == MusicChromosome.class) {
	            copy = new Chromosome(getConfiguration());
	          }
	          else {
	            copy = (IChromosome) getConfiguration().getSampleChromosome().clone();
	          }
	        }
	      }
	      copy.setFitnessValue(m_fitnessValue);
	      // Clone constraint checker.
	      // -------------------------
	      copy.setConstraintChecker(getConstraintChecker());
	    } catch (InvalidConfigurationException iex) {
	      throw new IllegalStateException(iex.getMessage());
	    }
	    // Also clone the IApplicationData object.
	    // ---------------------------------------
	    try {
	      copy.setApplicationData(cloneObject(getApplicationData()));
	    } catch (Exception ex) {
	      throw new IllegalStateException(ex.getMessage());
	    }
	    // Clone multi-objective object if necessary and possible.
	    // -------------------------------------------------------
	    if (m_multiObjective != null) {
	      if (getClass() == MusicChromosome.class) {
	        try {
	          ( (MusicChromosome) copy).setMultiObjectives( (List) cloneObject(
	              m_multiObjective));
	        } catch (Exception ex) {
	          throw new IllegalStateException(ex.getMessage());
	        }
	      }
	    }
	    return copy;
	  }

}
