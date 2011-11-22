/**
 * EvolTrio is a program which composes music phrases using evolutionary
 * algorithms. It's main purpose is to explore some possibilities of AI in
 * the extremely experimental field of computer generated music.
 * 
 * It's secondary objective is to have fun.
 *
 * Copyright (C) 2011  Konstantinos Georgiadis
 * { kongeor@it.teithe.gr, psykasso@gmail.com }
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License Version 3
 * along with this program.  If not, see http://www.gnu.org/licenses/gpl-3.0.txt
 */
package gr.evoltrio.core;

import gr.evoltrio.conf.CliParametersParser;
import gr.evoltrio.fitness.SoloFitnessEvol;
import gr.evoltrio.midi.MusicConfiguration;
import gr.evoltrio.midi.SongBuilder;

import java.io.File;
import java.util.List;

import org.apache.pivot.wtk.DesktopApplicationContext;
import org.apache.pivot.wtk.ScriptApplication;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;
import org.jgap.impl.IntegerGene;

/**
 * 
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public class Evolution {

    private File file = new File("song.mid");

    protected Genotype population;
    private Gene[] sampleGenes;
    protected EvolConfiguration evolConf;
    private MusicChromosome sampleChromosome;
    private SoloFitnessEvol soloFitness;

    private CliParametersParser parser;
    protected MusicConfiguration musicConf;

    protected MusicChromosome[] phrases;

    // public Evolution() {
    // // file = new File(filename);
    // String[] emptyStuff = { "", "" };
    // parser = new CliParametersParser(emptyStuff);
    // evolConf = parser.getEvolConfig();
    // soloFitness = parser.getSoloFitness();
    // parser.getMusicConfig();
    // musicConf = MusicConfiguration.getInstance();
    // setup();
    // }

    public Evolution(String args[]) {
        parser = new CliParametersParser(args);
        // Configuration.reset();
        evolConf = parser.getEvolConfig();
        soloFitness = parser.getSoloFitness();
        parser.getMusicConfig();
        musicConf = MusicConfiguration.getInstance();
    }

    public Evolution() {
        Configuration.reset();
        evolConf = new EvolConfiguration();
        Configuration.reset();
        evolConf.setTheConfiguration();
        soloFitness = new SoloFitnessEvol();
        soloFitness.addAllFilters();
        musicConf = MusicConfiguration.getInstance();
    }

    public void setup() {

        // every note has an interval jump and a duration jump
        sampleGenes = new Gene[musicConf.getPhraseNotes() * 2];

        try {
            for (int i = 0; i < musicConf.getPhraseNotes() * 2; i += 2) {
                sampleGenes[i] = new IntegerGene(evolConf,
                        musicConf.getMaxIntervalJump() * (-1),
                        musicConf.getMaxIntervalJump());
                sampleGenes[i + 1] = new IntegerGene(evolConf,
                        musicConf.getMaxDurationJump() * (-1),
                        musicConf.getMaxDurationJump());
            }
            sampleChromosome = new MusicChromosome(evolConf, sampleGenes);
            evolConf.setSampleChromosome(sampleChromosome);
            evolConf.setFitnessFunction(soloFitness);

        } catch (InvalidConfigurationException e) {
            System.out.println(e);
        }

        // set the phrase count
        phrases = new MusicChromosome[musicConf.getChordProgression().length];

        try {
            population = Genotype.randomInitialGenotype(evolConf);
        } catch (InvalidConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @return the evolConf
     */
    public EvolConfiguration getEvolConf() {
        return evolConf;
    }

    public void resetEvolConf() {
        EvolConfiguration.reset();
    }

    /**
     * @param evolConf
     *            the evolConf to set
     */
    public void setEvolConf(EvolConfiguration evolConf) {
        this.evolConf = evolConf;
    }

    public FitnessFunction getSoloFitness() {
        return soloFitness;
    }

    public void setSoloFitness(SoloFitnessEvol soloFitness) {
        this.soloFitness = soloFitness;
    }

    public void play() {
        // Pattern song = new Pattern();
        // IChromosome bestPhrase = null;
        // ChromoManipuplation chromoMan;
        //
        // bestPhrase = population.getFittestChromosome();
        // chromoMan = new ChromoManipuplation(bestPhrase);
        // song.add(chromoMan.getJFuguePattern());

        // Player player = new Player();

    }

    public void evolve() throws Exception {

        StringBuffer progress = new StringBuffer("|");
        int maxBars = 50;
        int bar;

        int iterations = ((EvolConfiguration) evolConf).getIterations();

        for (int i = 0; i < phrases.length; i++) {
            population = Genotype.randomInitialGenotype(evolConf);
            updateAbsolutePopulation(population);
            for (int j = 0; j <= iterations; j++) {
                // System.out.println("Phrase: " + (i+1) + ", iteration: " + j);

                progress = new StringBuffer("Fitness : "
                        + population.getFittestChromosome().getFitnessValue()
                        + "\tProgress: "
                        + (int) (((double) (i * iterations + j)
                                / (phrases.length * iterations) * 100)) + "% ");
                progress.append("|");
                bar = (maxBars * (i * iterations + j))
                        / (phrases.length * iterations);

                for (int k = 0; k < bar; k++)
                    progress.append("=");

                for (int k = 0; k < maxBars - bar; k++)
                    progress.append(" ");

                progress.append("| Phrase : " + (i + 1) + "/" + phrases.length);

                progress.append("\r");
                System.out.print(progress);
                population.evolve();
                updateAbsolutePopulation(population);
                // System.out.println(bestPhrase);
            }
            phrases[i] = (MusicChromosome) (population.getFittestChromosome());

        }
        System.out.println();
    }

    public double evolveOnce() {

        population.evolve();
        updateAbsolutePopulation(population);
        return population.getFittestChromosome().getFitnessValue();
    }

    public MusicChromosome getFittestChromosome() {
        return (MusicChromosome) population.getFittestChromosome();
    }

    public Genotype getPopulation() {
        return population;
    }

    /**
     * Synchronizes the absolute chromosome values for a given population.
     * 
     * @param pop
     *            The population to update
     */
    private void updateAbsolutePopulation(Genotype pop) {
        for (IChromosome mc : pop.getPopulation().getChromosomes()) {
            ((MusicChromosome) mc).updateAbsolute();
        }

    }

    public void buildAndPlay() {
        SongBuilder sb = new SongBuilder(file, phrases, musicConf);
        sb.buildSong();

        sb.playTheSong();
    }

    public void buildAndSave() {

        SongBuilder sb = new SongBuilder(file, phrases, musicConf);
        sb.buildSong();
        sb.saveTheSong();
    }

    //
    // public void buildAndSave(String filename) {
    // if (filename.equals(""))
    // buildAndSave();
    // else {
    // file = new File(filename);
    // SongBuilder sb = new SongBuilder(file, phrases, musicPattern,
    // phrasePlaybackTimes, drumPattern, bassPattern, tempo,
    // soloOrgan, keyNote, begginingDuration);
    // sb.buildSong();
    // sb.saveTheSong();
    // }
    // }

    public static void main(String args[]) throws Exception {

        // for(String arg : args)
        // System.out.println(arg);
        // Evolution evo = new Evolution(args);
        // CmdLineParser parser = new CmdLineParser(evo);

        // if(args.length == 0){
        // System.out.println("Usage: java -jar EvolTrio.jar [ OPTIONS ] FILE");
        // //System.out.println("\nParameters and Options:");
        // //parser.printUsage(System.out);
        // System.out.println("\nTo view all the available options, type : java -jar EvolTrio.jar --help");
        // return;
        // }
        // System.out.println("Evolving ...");
        // evo.setup();
        // evo.evolve();
        // evo.buildAndPlay();
        // evo.buildAndSave();
        // evo.writeParameters();
        // ScriptApplication sa = new ScriptApplication();
        DesktopApplicationContext.main(ScriptApplication.class,
                new String[] { "--src=/gr/evoltrio/ui/EvolTrioUI.bxml" });

    }
}
