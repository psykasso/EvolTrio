/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.evomusic.evoltrio.midi;


import org.jfugue.Pattern;
import org.jfugue.Rhythm;

/**
 *
 * @author psykasso
 */
public class DrumPatterns {


    public static Pattern getDrumPattern(String drumPattern, double[] phrasesDuration){
        //drums get the 2rd voice ..................... default V9
        Pattern pattern = new Pattern();
        //the measuresCount has the sum of all the phrases that are going to be played and they
        //are multiplied with the repetition count.
        
        int patternCount = 0;
        
        for (int i = 0; i < phrasesDuration.length; i++)
			for (int j = (int) (phrasesDuration[i] / 4); j > 0; j--)
				patternCount++;
        
        pattern.add(getDefaultRhythm().getPattern(),patternCount);
        return pattern;
    }

    private static Rhythm getDefaultRhythm(){
        Rhythm rhythm = new Rhythm();
        /*
        rhythm.setLayer(1, "O.......O.......");
        rhythm.setLayer(2, "....*.......*...");
        rhythm.setLayer(3, "^^^^^^^^^^^^^^^^");
        rhythm.setLayer(4, "!...............");
        */
        
        rhythm.setLayer(1, "O.......");
        rhythm.setLayer(2, "....*...");
        rhythm.setLayer(3, "^^^^^^^^");
        rhythm.setLayer(4, "........");
        /*
        rhythm.setLayer(1, "O.0.0.0.");
        rhythm.setLayer(2, ".*.*.*.*");
        rhythm.setLayer(3, "^^^^^^^^");
        rhythm.setLayer(4, "!.......");
*/
        rhythm.addSubstitution('O', "[BASS_DRUM]i");
        rhythm.addSubstitution('o', "Rs [BASS_DRUM]i");
        rhythm.addSubstitution('*', "[ACOUSTIC_SNARE]i");
        rhythm.addSubstitution('^', "[PEDAL_HI_HAT]i");
        rhythm.addSubstitution('!', "[CRASH_CYMBAL_1]i");
        rhythm.addSubstitution('.', "Ri");

        return rhythm;
    }

}
