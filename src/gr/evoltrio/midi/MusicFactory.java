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

import gr.evoltrio.midi.MusicConfiguration;

import org.jgap.IChromosome;

/**
 * 
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public class MusicFactory {

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
	public static int calcDurationAtIndex(IChromosome chromo, int index,
			int currentDur) {
		int runningDur = ((Integer) (chromo.getGene(index).getAllele()))
				.intValue();
		int dur = currentDur + runningDur;

		//the total available durations
		int durationSum = MusicConfiguration.DURATION_VALUES.size();
		
		if (dur >= durationSum)
			dur = dur - durationSum;
		else if (dur < 0)
			dur = durationSum + dur;

		//TODO find out what is the problem with the 3 parameters
		//return dur >=3 ? 2 : dur;
		return dur;
	}
	
	static public double convertToDoubleDuration(int value) {
		return (Double) MusicConfiguration.DURATION_VALUES.values().toArray()[value];
	}

	public static String convertToJFugueNote(int value) {
		
		int octave = value / MusicConfiguration.getInstance().getScaleValuesCnt();
		//if(value / MusicConfiguration.SCALE_NOTE_COUNT)
		return (value % MusicConfiguration.getInstance().getScaleValuesCnt())>MusicConfiguration.NOTES.length ?
				(getJFugueNote(value)) : (getJFugueNote(value) + octave);
	}
	/**
	 * Does not return an octave
	 * @param value
	 * @return
	 */
    public static String getJFugueNote(int value){
		String jnote = "";
		int note = value % MusicConfiguration.getInstance().getScaleValuesCnt();

		switch(note){
			case 0: jnote = "C"; break;
			case 1: jnote = "C#"; break;
			case 2: jnote = "D"; break;
			case 3: jnote = "D#"; break;
			case 4: jnote = "E"; break;
			case 5: jnote = "F"; break;
			case 6: jnote = "F#"; break;
			case 7: jnote = "G"; break;
			case 8: jnote = "G#"; break;
			case 9: jnote = "A"; break;
			case 10: jnote = "A#"; break;
			case 11: jnote = "B"; break;
			default: jnote =  "R";
		}

		return jnote;

	}

	public static String convertToJFugueDuration(int value) {
		return (String) MusicConfiguration.DURATION_VALUES.keySet().toArray()[value];
	}

}
