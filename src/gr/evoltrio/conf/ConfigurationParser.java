/**
 * This file is part of EvolTrio.
 *
 * EvolTrio is licensed under the GPLv3.
 *
 * For licensing information please see the file license.txt included with EvolTrio
 * or have a look at the top of class gr.evoltrio.evomusic.Evolution which representatively
 * includes the EvolTrio license policy applicable for any file delivered with EvolTrio.
 */
package gr.evoltrio.conf;

import gr.evoltrio.core.EvolConfiguration;
import gr.evoltrio.fitness.FiltersFactory;
import gr.evoltrio.fitness.SoloFitnessEvol;
import gr.evoltrio.midi.MusicConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

/**
 * 
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public class ConfigurationParser {
 
    public static void parseConfigurationFile(
            File configFile, EvolConfiguration evolConf,
            SoloFitnessEvol fitness) throws IOException {
        
        Properties config = new Properties();
        InputStream ins = new FileInputStream(configFile);
        config.load(ins);
        
        //parse music parameters
        MusicConfiguration.getInstance().setRestOffset(Integer.parseInt(config.getProperty("restOffset")));
        MusicConfiguration.getInstance().setBeginningDuration(Integer.parseInt(config.getProperty("begDur")));
        MusicConfiguration.getInstance().setPhraseNotes(Integer.parseInt(config.getProperty("phraseNotes")));
        MusicConfiguration.getInstance().setMaxIntervalJump(Integer.parseInt(config.getProperty("intJump")));
        MusicConfiguration.getInstance().setMaxDurationJump(Integer.parseInt(config.getProperty("durJump")));
        MusicConfiguration.getInstance().setRootNote(config.getProperty("keyNote"));
        MusicConfiguration.getInstance().setOctave(Integer.parseInt(config.getProperty("octave")));
        MusicConfiguration.getInstance().setMusicPattern(config.getProperty("pattern"));
        MusicConfiguration.getInstance().setDrumPattern(config.getProperty("drums"));
        MusicConfiguration.getInstance().setBassPattern(config.getProperty("bass"));
        MusicConfiguration.getInstance().setTempo(config.getProperty("tempo"));
        MusicConfiguration.getInstance().setSoloOrgan(Integer.parseInt(config.getProperty("organ")));
        
        //parse evolutionary parameters
        evolConf.setRandomGen(config.getProperty("randomGen"));
        evolConf.setNaturalSel(config.getProperty("natualSel"));
        evolConf.setExecuteNaturalBefore(Boolean.parseBoolean(config.getProperty("execBefore")));
        evolConf.setNaturalSel(config.getProperty("minPop"));
        evolConf.setSelectFromPrevGen(Double.parseDouble(config.getProperty("previousGen")));
        evolConf.setKeepPopSizeConstant(Boolean.parseBoolean(config.getProperty("popConstant")));
        evolConf.setCrossoverRate(Double.parseDouble(config.getProperty("crossover")));
        evolConf.setMutationRate(Integer.parseInt(config.getProperty("mutation")));
        //TODO user the method from the Configuration class
        evolConf.setPopSize(Integer.parseInt(config.getProperty("population")));
        evolConf.setIterations(Integer.parseInt(config.getProperty("iterations")));
        
        //parse Solo Fitness Parameters
        fitness.addFilter((Boolean.parseBoolean(config.getProperty("pitch"))) ? FiltersFactory.Filter.SIMPLEPITCH : null);
        fitness.addFilter((Boolean.parseBoolean(config.getProperty("duration"))) ? FiltersFactory.Filter.SIMPLEDURATION : null);
        fitness.addFilter((Boolean.parseBoolean(config.getProperty("scale"))) ? FiltersFactory.Filter.OUTOFSCALE : null);
        fitness.addFilter((Boolean.parseBoolean(config.getProperty("time"))) ? FiltersFactory.Filter.TIME : null);
        fitness.addFilter((Boolean.parseBoolean(config.getProperty("dull"))) ? FiltersFactory.Filter.DULL : null);
        fitness.addFilter((Boolean.parseBoolean(config.getProperty("high"))) ? FiltersFactory.Filter.HIGHANDLOW : null);
        fitness.addFilter((Boolean.parseBoolean(config.getProperty("repetition1"))) ? FiltersFactory.Filter.ROOTNOTE : null);
        fitness.addFilter((Boolean.parseBoolean(config.getProperty("repetition2"))) ? FiltersFactory.Filter.THREENOTE : null);
        fitness.addFilter((Boolean.parseBoolean(config.getProperty("ascending"))) ? FiltersFactory.Filter.ASCENDING : null);
        fitness.addFilter((Boolean.parseBoolean(config.getProperty("descending"))) ? FiltersFactory.Filter.DESCENDING : null);
        
        //add all filters
        if( Boolean.parseBoolean(config.getProperty("filtAll")) ) {
            fitness.addFilter(FiltersFactory.Filter.SIMPLEPITCH);
            fitness.addFilter(FiltersFactory.Filter.SIMPLEDURATION);
            fitness.addFilter(FiltersFactory.Filter.OUTOFSCALE);
            fitness.addFilter(FiltersFactory.Filter.TIME);
            fitness.addFilter(FiltersFactory.Filter.DULL);
            fitness.addFilter(FiltersFactory.Filter.HIGHANDLOW);
            fitness.addFilter(FiltersFactory.Filter.ROOTNOTE);
            fitness.addFilter(FiltersFactory.Filter.THREENOTE);
            fitness.addFilter(FiltersFactory.Filter.ASCENDING);
            fitness.addFilter(FiltersFactory.Filter.DESCENDING);
        }
        
        System.out.println(MusicConfiguration.getInstance());
        System.out.println(evolConf);
        System.out.println(fitness);
    }

    public static void main(String[] args) throws IOException {
        File conf = new File("conf/default.conf");
        ConfigurationParser.parseConfigurationFile(conf,
                new EvolConfiguration(), new SoloFitnessEvol());

    }
}
