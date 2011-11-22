/**
 * This file is part of EvolTrio.
 *
 * EvolTrio is licensed under the GPLv3.
 *
 * For licensing information please see the file license.txt included with EvolTrio
 * or have a look at the top of class gr.evoltrio.core.Evolution which representatively
 * includes the EvolTrio license policy applicable for any file delivered with EvolTrio.
 */
package gr.evoltrio.conf;

import gr.evoltrio.core.EvolConfiguration;
import gr.evoltrio.fitness.FiltersFactory;
import gr.evoltrio.fitness.SoloFitnessEvol;
import gr.evoltrio.midi.MusicConfiguration;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * 
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public class CliParametersParser {

	// TODO put tha somewhere else
	private CommandLineParser parser;
	private Options options;
	CommandLine line;

	private String[] args;

	public CliParametersParser(String[] args) {

		this.args = args;

		parser = new GnuParser();
		options = new Options();

		// add options first
		addOptions();
		
		// parse the command line arguments
		try {
			line = parser.parse(options, args);
			parseParameters();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	private void addOptions() {
		OptionBuilder
				.withLongOpt("begDur")
				.withDescription(
						"Sets the duration of the first note in a phrase")
				.hasArg().withArgName("[1,6]");
		// music parameters
		options.addOption(OptionBuilder.create());

		options.addOption(OptionBuilder
				.withLongOpt("phraseNotes")
				.withDescription(
						"Sets the number of notes a phrase will contain")
				.hasArg().withArgName("[4,32]").create());

		options.addOption(OptionBuilder
				.withLongOpt("intJump")
				.withDescription(
						"Sets the max interval jump between two adjacent notes")
				.hasArg().withArgName("[1,12]").create());

		options.addOption(OptionBuilder
				.withLongOpt("durJump")
				.withDescription(
						"Sets the max duration jump between two adjacent notes")
				.hasArg().withArgName("[1,6]").create());

		options.addOption(OptionBuilder.withLongOpt("note")
				.withDescription("The pitch of the key note").hasArg()
				.withArgName("[ C | C# | .. | B ]").create());

		options.addOption(OptionBuilder.withLongOpt("octave")
				.withDescription("The octave of the key note").hasArg()
				.withArgName("[2,7]").create());

		options.addOption(OptionBuilder.withLongOpt("pattern")
				.withDescription("The music pattern to use").hasArg()
				.withArgName("[ I-IV-V-I ]").create());

		options.addOption(OptionBuilder.withLongOpt("drums")
				.withDescription("The drum pattern to use").hasArg()
				.withArgName("[ default ]").create());

		options.addOption(OptionBuilder.withLongOpt("bass")
				.withDescription("The bass pattern to use").hasArg()
				.withArgName("[ default ]").create());

		options.addOption(OptionBuilder.withLongOpt("tempo")
				.withDescription("Defines the music tempo").hasArg()
				.withArgName("[ ADAGIO ]").create());

		options.addOption(OptionBuilder
				.withLongOpt("organ")
				.withDescription(
						"The midi instrument that the solist is going to use")
				.hasArg().withArgName("[0,127]").create());

		// parameters for the evolution
		options.addOption(OptionBuilder.withLongOpt("random")
				.withDescription("The random generator to use").hasArg()
				.withArgName("[ STOCK | CAUCHY | GAUSSIAN ]").create());

		options.addOption(OptionBuilder.withLongOpt("natural")
				.withDescription("The natural selector to use").hasArg()
				.withArgName("[ BEST | THRESHOLD | TOURNAMENT | WEIGHTED ]")
				.create());

		options.addOption("u", "execBefore", false,
				"Execute the natural selector before the genetic operators");

		options.addOption(OptionBuilder
				.withLongOpt("minpop")
				.withDescription(
						"Minimum percent size guaranteed for population")
				.hasArg().withArgName("[0,100]").create());

		options.addOption(OptionBuilder
				.withLongOpt("previousGen")
				.withDescription(
						"Defines the percentage of the chromosomes that are going to enter the new population")
				.hasArg().withArgName("[0,1]").create());

		options.addOption("f", "constant", false,
				"Keep constant the population size");

		options.addOption(OptionBuilder.withLongOpt("crossover")
				.withDescription("Defines the crossover rate").hasArg()
				.withArgName("[0,1]").create());

		options.addOption(OptionBuilder.withLongOpt("mutation")
				.withDescription("Defines the mutation rate").hasArg()
				.withArgName("[0,100]").create());

		options.addOption(OptionBuilder.withLongOpt("pop")
				.withDescription("Defines the population size").hasArg()
				.withArgName("[0,1000]").create());

		options.addOption(OptionBuilder
				.withLongOpt("iterations")
				.withDescription(
						"Sets the number of iterations that each phrase will evolve")
				.hasArg().withArgName("[1,10000]").create());

		// fitness parameters
		options.addOption(
				"1",
				"filtall",
				false,
				"Enables all the fitness filters. This option will override any other made that involves filters");

		options.addOption("P", "pitch", false,
				"Enables simple pitch fitness filter");
		options.addOption("D", "duration", false,
				"Enables simple duration fitness filter");
		options.addOption("S", "scale", false,
				"Enables scale notes fitness filter. USE THIS!");
		options.addOption("T", "time", false, "Enables time fitness filter");
		options.addOption("B", "dull", false,
				"Enables dull (boring music) fitness filter");
		options.addOption("H", "high", false,
				"Enables high diversity of notes fitness filter");

		options.addOption("R", "repetition1", false,
				"Enables repetition of notes fitness filter");
		options.addOption("E", "repetition2", false,
				"Enables a different repetition of notes fitness filter");
		options.addOption("A", "ascending", false,
				"Enables adjacent ascending notes fitness filter");
		options.addOption("Z", "descending", false,
				"Enables adjacent descending notes fitness filter");

		// options.addOption(OptionBuilder.withLongOpt("file").isRequired()
		// .withDescription("The file to store the produced midi")
		// .hasArg().withArgName("FILE").create());

		options.addOption("v", "version", false, "Show version");

		options.addOption("h", "help", false, "Print this screen and quit");

	}

	/**
	 * TODO correct this to smt more generic
	 */
	private void parseParameters() {
		try {

			// validate that block-size has been set
			if (line.hasOption("help")) {
				// print the value of block-size
				System.out.println(line.getOptionValue("help"));
				HelpFormatter formatter = new HelpFormatter();
				formatter.setWidth(120);
				formatter.printHelp("java -jar EvolTrio.jar [ OPTIONS ] FILE",
						options);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void getMusicConfig() {

			// validate the beggining duration
			if (line.hasOption("begDur")) {
				// parse the value
				int value = Integer.parseInt(line.getOptionValue("begDur"));
				MusicConfiguration.getInstance().setBeginningDuration(value);
			}
			// validate the phrase notes
			if (line.hasOption("phraseNotes")) {

				int value = Integer
						.parseInt(line.getOptionValue("phraseNotes"));

				MusicConfiguration.getInstance().setPhraseNotes(value);
			}

			// validate the maximum interval jump
			if (line.hasOption("intJump")) {

				int value = Integer.parseInt(line.getOptionValue("intJump"));
				MusicConfiguration.getInstance().setMaxIntervalJump(value);
			}
			// validate the maximum duration jump
			if (line.hasOption("durJump")) {

				int value = Integer.parseInt(line.getOptionValue("durJump"));
				MusicConfiguration.getInstance().setMaxDurationJump(value);

			}
			// validate the octave -- it comes first
			// afterwards the note is going to set the keyNote
			if (line.hasOption("octave")) {

				int value = Integer.parseInt(line.getOptionValue("octave"));
				MusicConfiguration.getInstance().setOctave(value);
			}
			// validate for the note
			if (line.hasOption("note")) {

				String value = line.getOptionValue("note");
				MusicConfiguration.getInstance().setRootNote(value);
			}

			if (line.hasOption("pattern")) {

				String value = line.getOptionValue("pattern");
				MusicConfiguration.getInstance().setMusicPattern(value);
			}

			if (line.hasOption("drums")) {

				String value = line.getOptionValue("drums");
				MusicConfiguration.getInstance().setDrumPattern(value);
			}

			if (line.hasOption("bass")) {

				String value = line.getOptionValue("bass");
				MusicConfiguration.getInstance().setBassPattern(value);

			}

			if (line.hasOption("organ")) {

				int value = Integer.parseInt(line.getOptionValue("organ"));
				MusicConfiguration.getInstance().setSoloOrgan(value);
			}

			if (line.hasOption("tempo")) {

				String value = line.getOptionValue("tempo");
				MusicConfiguration.getInstance().setTempo(value);
			}
		

	}

	public EvolConfiguration getEvolConfig() {
		String randomGen = "stock";
		String naturalSel = "best";
		boolean executeNaturalBefore = false;
		double selectFromPrevGen = 1.0d;
		int minPopSizePercent = 0;
		boolean keepPopSizeConstant = false;
		double crossoverRate = 0.55d;
		int mutationRate = 32;
		int popSize = 100;
		int iterations = 100;

		try {
			if (line.hasOption("random")) {
				// parse the value
				String value = line.getOptionValue("random");

				boolean genFound = false;

				for (String rndG : EvolConfiguration.RANDOMGENERATORS)
					if (value.equalsIgnoreCase(rndG))
						genFound = true;

				if (!genFound) {
					throw new ParseException(
							"Invalid value for random generator : "
									+ value
									+ ". Possible values are stock,cauchy or gaussian");
				}

				randomGen = value;
			}

			if (line.hasOption("natural")) {
				// parse the value
				String value = line.getOptionValue("natural");

				boolean naturFound = false;

				for (String natF : EvolConfiguration.NATURALSELECTORS)
					if (value.equalsIgnoreCase(natF))
						naturFound = true;

				if (!naturFound) {
					throw new ParseException(
							"Invalid value for natural generator : "
									+ value
									+ ". Possible values are best,threshold,tournament or weighted");

				}
				naturalSel = value;
			}
			// TODO change order
			if (line.hasOption("minpop")) {
				// parse the value
				int value = Integer.parseInt(line.getOptionValue("minpop"));

				if (value < 0 || value > 100) {
					throw new ParseException(
							"Invalid value for minimum size percent : " + value
									+ " . Possible values are [0,100].");

				}
				minPopSizePercent = value;
			}
			// TODO change corresponding values to double
			if (line.hasOption("previousGen")) {
				// parse the value
				int value = Integer
						.parseInt(line.getOptionValue("previousGen"));
				if (value < 0 || value > 1) {
					throw new ParseException(
							"Invalid value for select from previous generation : "
									+ value + " . Possible values are [0,1].");
				}
				selectFromPrevGen = value;
			}

			if (line.hasOption("crossover")) {
				// parse the value
				double value = Double.parseDouble(line
						.getOptionValue("crossover"));
				if (value < 0 || value > 1) {
					throw new ParseException(
							"Invalid value for crossover rate : " + value
									+ " . Possible values are [0,1].");
				}
				crossoverRate = value;
			}

			if (line.hasOption("mutation")) {
				// parse the value
				int value = Integer.parseInt(line.getOptionValue("mutation"));
				if (value < 0 || value > 100) {
					throw new ParseException(
							"Invalid value for mutation rate : " + value
									+ " . Possible values are [0,100].");
				}
				mutationRate = value;
			}

			if (line.hasOption("pop")) {
				// parse the value
				int value = Integer.parseInt(line.getOptionValue("pop"));
				if (value < 1 || value > 1000) {
					throw new ParseException(
							"Invalid value for population size : " + value
									+ " . Possible values are [0,1000].");
				}
				iterations = value;
			}

			if (line.hasOption("iterations")) {
				// parse the value
				int value = Integer.parseInt(line.getOptionValue("iterations"));
				if (value < 1 || value > 10000) {
					throw new ParseException(
							"Invalid value for the number of iterations : "
									+ value
									+ " . Possible values are 0,10000].");

				}
				iterations = value;
			}

		} catch (ParseException exp) {
			System.out.println("Unexpected exception:" + exp.getMessage());
		}
		// TODO figure out smt for the invalid parameters
		return new EvolConfiguration(randomGen, naturalSel,
				executeNaturalBefore, minPopSizePercent, selectFromPrevGen,
				keepPopSizeConstant, crossoverRate, mutationRate, iterations);

	}

	public SoloFitnessEvol getSoloFitness() {

		SoloFitnessEvol soloFitnessEvol = new SoloFitnessEvol();

		if (line.hasOption("P") || line.hasOption("pitch")) {
			soloFitnessEvol.addFilter(FiltersFactory.Filter.SIMPLEPITCH);
		}
		if (line.hasOption("D") || line.hasOption("duration")) {
			soloFitnessEvol.addFilter(FiltersFactory.Filter.SIMPLEDURATION);
		}
		if (line.hasOption("S") || line.hasOption("scale")) {
			soloFitnessEvol.addFilter(FiltersFactory.Filter.OUTOFSCALE);
		}
		if (line.hasOption("T") || line.hasOption("time")) {
			soloFitnessEvol.addFilter(FiltersFactory.Filter.TIME);
		}
		if (line.hasOption("B") || line.hasOption("dull")) {
			soloFitnessEvol.addFilter(FiltersFactory.Filter.DULL);
		}
		if (line.hasOption("H") || line.hasOption("high")) {
			soloFitnessEvol.addFilter(FiltersFactory.Filter.HIGHANDLOW);
		}
		if (line.hasOption("R") || line.hasOption("repetition1")) {
			soloFitnessEvol.addFilter(FiltersFactory.Filter.ROOTNOTE);
		}
		if (line.hasOption("E") || line.hasOption("repetition2")) {
			soloFitnessEvol.addFilter(FiltersFactory.Filter.THREENOTE);
		}
		if (line.hasOption("A") || line.hasOption("ascending")) {
			soloFitnessEvol.addFilter(FiltersFactory.Filter.ASCENDING);
		}
		if (line.hasOption("Z") || line.hasOption("descending")) {
			soloFitnessEvol.addFilter(FiltersFactory.Filter.DESCENDING);
		}
		if (line.hasOption("1") || line.hasOption("filtall")) {
			soloFitnessEvol.addFilter(FiltersFactory.Filter.SIMPLEPITCH);
			soloFitnessEvol.addFilter(FiltersFactory.Filter.SIMPLEDURATION);
			soloFitnessEvol.addFilter(FiltersFactory.Filter.OUTOFSCALE);
			soloFitnessEvol.addFilter(FiltersFactory.Filter.TIME);
			soloFitnessEvol.addFilter(FiltersFactory.Filter.DULL);
			soloFitnessEvol.addFilter(FiltersFactory.Filter.HIGHANDLOW);
			soloFitnessEvol.addFilter(FiltersFactory.Filter.ROOTNOTE);
			soloFitnessEvol.addFilter(FiltersFactory.Filter.THREENOTE);
			soloFitnessEvol.addFilter(FiltersFactory.Filter.ASCENDING);
			soloFitnessEvol.addFilter(FiltersFactory.Filter.DESCENDING);
		}

		return soloFitnessEvol;
	}

	public static void main(String[] args) {
		new CliParametersParser(new String[] { "--h", "--phraseNotes=32" })
				.getSoloFitness();
	}

}
