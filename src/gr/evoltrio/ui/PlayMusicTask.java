/**
 * This file is part of EvolTrio.
 *
 * EvolTrio is licensed under the GPLv3.
 *
 * For licensing information please see the file license.txt included with EvolTrio
 * or have a look at the top of class gr.evoltrio.core.Evolution which representatively
 * includes the EvolTrio license policy applicable for any file delivered with EvolTrio.
 */
package gr.evoltrio.ui;

import gr.evoltrio.core.MusicChromosome;
import gr.evoltrio.midi.MusicConfiguration;
import gr.evoltrio.midi.SongBuilder;

import org.apache.pivot.util.concurrent.Task;
import org.apache.pivot.util.concurrent.TaskExecutionException;
import org.apache.pivot.wtk.Button;

/**
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public class PlayMusicTask extends Task<String> {

    private Button playButton;
    private MusicChromosome chromosome;
    
    
    public PlayMusicTask(Button playButton, MusicChromosome chromosome) {
        this.playButton = playButton;
        this.chromosome = chromosome;
    }


    @Override
    public String execute() throws TaskExecutionException {
        playButton.setEnabled(false);
        
        MusicChromosome[] phrases = new MusicChromosome[1];
        phrases[0] = chromosome;
        // TODO change this!
        phrases[0].updateAbsolute();
        System.out.println(phrases[0]);
        SongBuilder sb = new SongBuilder(null,
                phrases, MusicConfiguration
                        .getInstance());
        sb.buildSong();
        sb.playTheSong();
        
        playButton.setEnabled(true);
        return "Playing song fnished!";
    }

}
