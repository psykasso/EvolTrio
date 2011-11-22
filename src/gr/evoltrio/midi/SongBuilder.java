/**
 * This file is part of EvolTrio.
 *
 * EvolTrio is licensed under the GPLv3.
 *
 * For licensing information please see the file license.txt included with EvolTrio
 * or have a look at the top of class gr.evoltrio.core.Evolution which representatively
 * includes the EvolTrio license policy applicable for any file delivered with EvolTrio.
 */
package gr.evoltrio.midi;

import gr.evoltrio.core.MusicChromosome;

import java.io.File;
import java.io.IOException;
import org.jfugue.Pattern;
import org.jfugue.Player;
/**
 * This class needs major changes.
 * 
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public class SongBuilder {

    private MusicChromosome[] phrases;
	
    MusicConfiguration musicConf;

    private Pattern song;
    private Player player;

    private File file;

    public SongBuilder(File file,MusicChromosome[] phrases, MusicConfiguration musicConf) {
        this.file = file;
        this.phrases = phrases;
        
        this.musicConf = musicConf;

        song = new Pattern();
        player = new Player();

    }

    public void buildSong(){
        Pattern voice0 = new Pattern("V0 I[" + musicConf.getSoloOrgan() +"] T[" +  musicConf.getTempo() + "] ");
        double[] phraseDuration = new double[phrases.length];
        for(int i=0; i<phrases.length; i++){
            
//            int absChromo[] = ChromoManipFactory.chromosomeToAbsolute(phrases[i]);
//            phraseDuration[i] = ChromoManipFactory.absoluteToChromoDuration(absChromo);
//            String jfugueRoundedPat = ChromoManipFactory.getJFugueRoundedPattern(ChromoManipFactory.absoluteToFuguePattern(absChromo), phraseDuration[i]);
//            System.out.println(jfugueRoundedPat);
        	String jfugueRoundedPat = phrases[i].getJFugueRoundedPattern();
        	System.out.println(jfugueRoundedPat);
            voice0.add(jfugueRoundedPat);
            

        }

        //BassPatterns bp = new BassPatterns(bassPattern, musicPattern, phrases, 1, keyNote, begginingDuration);
        BassPatterns bp = new BassPatterns(musicConf,phraseDuration);
        Pattern voice1 = bp.getBassPattern();

        Pattern voice2 = new Pattern();
        //TODO change this
        //voice2.add(DrumPatterns.getDrumPattern("", phraseDuration));
        //voice2.add(DrumPatterns.getDrumPattern(drumPattern, (int)(dur/4),1));

        //System.out.println(voice0);
        //System.out.println(voice1);
        //System.out.println(voice2);


        song.add(voice0);
        song.add(voice1);
        song.add(voice2);

    }

    public void playTheSong(){
        // Play the song!
        
        player.play(song);
    }

    public void saveTheSong(){

        saveTheSong(file);
    }

    public void saveTheSong(File fileName){
        try {
			player.saveMidi(song, fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }



}
