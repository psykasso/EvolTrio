/**
 * This file is part of EvolTrio.
 *
 * EvolTrio is licensed under the GPLv3.
 *
 * For licensing information please see the file license.txt included with EvolTrio
 * or have a look at the top of class gr.evoltrio.core.Evolution which representatively
 * includes the EvolTrio license policy applicable for any file delivered with EvolTrio.
 */
package gr.evoltrio.core;

import org.jgap.Configuration;
import org.jgap.DefaultFitnessEvaluator;
import org.jgap.InvalidConfigurationException;
import org.jgap.event.EventManager;
import org.jgap.impl.BestChromosomesSelector;
import org.jgap.impl.CauchyRandomGenerator;
import org.jgap.impl.ChromosomePool;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.GABreeder;
import org.jgap.impl.GaussianRandomGenerator;
import org.jgap.impl.MutationOperator;
import org.jgap.impl.StockRandomGenerator;
import org.jgap.impl.ThresholdSelector;
import org.jgap.impl.TournamentSelector;
import org.jgap.impl.WeightedRouletteSelector;

/**
 * 
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public class EvolConfiguration extends Configuration {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public static final int DEFAULT_POP_SIZE = 100;

	public static final String[] NATURALSELECTORS = { "best", "threshold",
			"tournament", "weighted" };

	public static final String[] RANDOMGENERATORS = { "stock", "cauchy",
			"gaussian" };

	private String randomGen = "stock";
	private String naturalSel = "best";
	// TODO change this with allow doublette
	private boolean executeNaturalBefore = false;
	private int minPopSizePercent = 0;
	private double selectFromPrevGen = 1.0d;
	private boolean keepPopSizeConstant = false;
	private double crossoverRate = 0.55d;
	private int mutationRate = 32;

	// TODO iterations are part of EvolConfig ? Move getters and setters down
	private int iterations = 100;


	/**
	 * TODO this wrapper handles errors
	 * @param popSize the popSize to set
	 */
	public void setPopSize(int popSize) {
		try {
            setPopulationSize(popSize);
        } catch (InvalidConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

	/**
	 * @return the iterations
	 */
	public int getIterations() {
		return iterations;
	}

	/**
	 * @param iterations the iterations to set
	 */
	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	// TODO remove this constructor as it's not going to be used
	public EvolConfiguration() {
		this("stock", "Best", false, 0, 1.0d, false, 35.0, 12, 1000);
	}

	public EvolConfiguration(String randomGen, String naturalSel,
			boolean executeNaturalBefore, int minPopSizePercent,
			double selectFromPrevGen, boolean keepPopSizeConstant,
			double crossoverRate, int mutationRate, int iterations) {

		// calling the default constructor
		super("", "");
		// setting the fields
		this.randomGen = randomGen;
		this.naturalSel = naturalSel;
		// TODO correct the typo
		this.executeNaturalBefore = executeNaturalBefore;
		this.minPopSizePercent = minPopSizePercent;
		this.selectFromPrevGen = selectFromPrevGen;
		this.keepPopSizeConstant = keepPopSizeConstant;
		this.crossoverRate = crossoverRate;
		this.mutationRate = mutationRate;

		this.iterations = iterations;

		setTheConfiguration();
	}

	/**
	 * @return the randomGen
	 */
	public String getRandomGen() {
		return randomGen;
	}

	/**
	 * @param randomGen the randomGen to set
	 */
	public void setRandomGen(String randomGen) {
		this.randomGen = randomGen;
	}

	/**
	 * @return the naturalSel
	 */
	public String getNaturalSel() {
		return naturalSel;
	}

	/**
	 * @param naturalSel the naturalSel to set
	 */
	public void setNaturalSel(String naturalSel) {
		this.naturalSel = naturalSel;
	}

	/**
	 * @return the executeNaturalBefore
	 */
	public boolean isExecuteNaturalBefore() {
		return executeNaturalBefore;
	}

	/**
	 * @param executeNaturalBefore the executeNaturalBefore to set
	 */
	public void setExecuteNaturalBefore(boolean executeNaturalBefore) {
		this.executeNaturalBefore = executeNaturalBefore;
	}

	/**
	 * @return the minPopSizePercent
	 */
	public int getMinPopSizePercent() {
		return minPopSizePercent;
	}

	/**
	 * @param minPopSizePercent the minPopSizePercent to set
	 */
	public void setMinPopSizePercent(int minPopSizePercent) {
		this.minPopSizePercent = minPopSizePercent;
	}

	/**
	 * @return the selectFromPrevGen
	 */
	public double getSelectFromPrevGen() {
		return selectFromPrevGen;
	}

	/**
	 * @param selectFromPrevGen the selectFromPrevGen to set
	 */
	public void setSelectFromPrevGen(double selectFromPrevGen) {
		this.selectFromPrevGen = selectFromPrevGen;
	}

	/**
	 * @return the keepPopSizeConstant
	 */
	public boolean isKeepPopSizeConstant() {
		return keepPopSizeConstant;
	}

	/**
	 * @param keepPopSizeConstant the keepPopSizeConstant to set
	 */
	public void setKeepPopSizeConstant(boolean keepPopSizeConstant) {
		this.keepPopSizeConstant = keepPopSizeConstant;
	}

	/**
	 * @return the crossoverRate
	 */
	public double getCrossoverRate() {
		return crossoverRate;
	}

	/**
	 * @param crossoverRate the crossoverRate to set
	 */
	public void setCrossoverRate(double crossoverRate) {
		this.crossoverRate = crossoverRate;
	}

	/**
	 * @return the mutationRate
	 */
	public int getMutationRate() {
		return mutationRate;
	}

	/**
	 * @param mutationRate the mutationRate to set
	 */
	public void setMutationRate(int mutationRate) {
		this.mutationRate = mutationRate;
	}

	public void setTheConfiguration() {
		try {
			// set the ones according to DefaultConfiguration in jgap
			setBreeder(new GABreeder());
			setEventManager(new EventManager());
			setFitnessEvaluator(new DefaultFitnessEvaluator());
			setChromosomePool(new ChromosomePool());

			// set the Random Generator
			if (randomGen.equalsIgnoreCase("Stock")) {
				setRandomGenerator(new StockRandomGenerator());
			} else if (randomGen.equalsIgnoreCase("Cauchy")) {
				// setting a Cauchy Random Gen, with default values TODO, add
				// parameters
				setRandomGenerator(new CauchyRandomGenerator());
			} else if (randomGen.equalsIgnoreCase("Gaussian")) {
				// the same as before, TODO ... the same as before
				setRandomGenerator(new GaussianRandomGenerator());

			}

			// set the Natural Selector
			if (naturalSel.equalsIgnoreCase("Best")) {
				// TODO ... defaults defaults ...
				addNaturalSelector(new BestChromosomesSelector(this),
						executeNaturalBefore);
			} else if (naturalSel.equalsIgnoreCase("Threshold")) {
				// TODO let's say ... why 0.5d and not 0.7d ..... ? Well,
				// because there are no defaults ..
				addNaturalSelector(new ThresholdSelector(this, 0.5d),
						executeNaturalBefore);
			} else if (naturalSel.equalsIgnoreCase("Tournament")) {
				// TODO .... guess what ? Hm let's say 30 and 0.5 are the
				// optimal values. Just a guess
				addNaturalSelector(new TournamentSelector(this, 30, 0.5d),
						executeNaturalBefore);
			} else if (naturalSel.equalsIgnoreCase("Weighted")) {
				// TODO yeah, you guessed right. Although this one is ok
				addNaturalSelector(new WeightedRouletteSelector(this),
						executeNaturalBefore);
			}
			// set the rest
			setMinimumPopSizePercent(minPopSizePercent);
			setSelectFromPrevGen(selectFromPrevGen);
			setKeepPopulationSizeConstant(keepPopSizeConstant);
			setPopulationSize(DEFAULT_POP_SIZE);

			if (crossoverRate != 0)
				addGeneticOperator(new CrossoverOperator(this, crossoverRate));

			if (mutationRate != 0)
				addGeneticOperator(new MutationOperator(this, mutationRate));

		} catch (InvalidConfigurationException e) {
			throw new RuntimeException(
					"Fatal error: DefaultConfiguration class could not use its "
							+ "own stock configuration values. This should never happen. "
							+ "Please report this as a bug to the JGAP team.");
		}
	}
	
	public String toString() {
	    String str = "---------------------------------------------------\n" +
                     "Evolutionary Settings\n" + 
                     "---------------------------------------------------\n\n" +
                     "Random Generator : " + getRandomGenerator() + "\n" + 
                     "Natural Selector : " + naturalSel + "\n" +
                     "Execute Natural Selector before GO : " + executeNaturalBefore + "\n" +
                     "Min Population Size Percent : " + minPopSizePercent + "\n" +
                     "Select From previous Generation " + selectFromPrevGen + "\n" +
                     "Keep Population Size Constant : " + keepPopSizeConstant + "\n" +
                     "Crossover Rate : " + crossoverRate + "\n" +
                     "Mutation Rate : " +  mutationRate + "\n" +
                     "Iterations : " + iterations + "\n" +
                     "Population Size : " + getPopulationSize() + "\n";
                     
	    return str;
	}

}
