package gr.evoltrio.ui;

import gr.evoltrio.core.MusicChromosome;
import gr.evoltrio.midi.MusicConfiguration;
import gr.evoltrio.midi.SongBuilder;

import org.apache.pivot.util.concurrent.Task;
import org.apache.pivot.util.concurrent.TaskExecutionException;
import org.apache.pivot.wtk.Button;

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
