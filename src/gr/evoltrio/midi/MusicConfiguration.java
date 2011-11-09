/**
 * This file is part of EvolTrio.
 *
 * EvolTrio is licensed under the GPLv3.
 *
 * For licensing information please see the file license.txt included with EvolTrio
 * or have a look at the top of class gr.evoltrio.evomusic.Evolution which representatively
 * includes the EvolTrio license policy applicable for any file delivered with EvolTrio.
 */
package gr.evoltrio.midi;

import gr.evoltrio.exception.InvalidConfigurationException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public class MusicConfiguration {

    private static final MusicConfiguration INSTANCE = new MusicConfiguration();

    public static final String[] NOTES = { "C", "C#", "D", "D#", "E", "F",
            "F#", "G", "G#", "A", "A#", "B" };

    public static final String[] TEMPOS = { "Grave", "Largo", "Larghetto",
            "Lento", "Adagio", "Adagietto", "Andante", "Andantino", "Moderato",
            "Allegretto", "Allegro", "Vivace", "Presto", "Pretissimo" };

    public static final Map<String, Integer> CHORDS;
    public static final Map<String, Double> DURATION_VALUES;

    static {
        Map<String, Integer> chordsMap = new HashMap<String, Integer>();
        chordsMap.put("I", 0);
        chordsMap.put("II", 1);
        chordsMap.put("III", 2);
        chordsMap.put("IV", 3);
        chordsMap.put("V", 4);
        chordsMap.put("VI", 5);
        chordsMap.put("VII", 6);
        chordsMap.put("VIII", 7);
        chordsMap.put("IX", 8);
        chordsMap.put("X", 9);
        CHORDS = Collections.unmodifiableMap(chordsMap);

        Map<String, Double> durationMap = new HashMap<String, Double>();
        durationMap.put("h. ", 3d);
        durationMap.put("h ", 2d);
        durationMap.put("h* ", 1.33332);

        durationMap.put("q. ", 1.5);
        durationMap.put("q ", 1d);
        durationMap.put("q* ", 0.66666);

        durationMap.put("i. ", 0.75);
        durationMap.put("i ", 0.5);
        durationMap.put("i* ", 0.333333334);

        durationMap.put("s. ", 0.375);
        durationMap.put("s ", 0.25);
        durationMap.put("s* ", 0.166666667);

        DURATION_VALUES = Collections.unmodifiableMap(durationMap);
    }

    /**
     * The major intervals for a major scale.
     */
    public static final int[] MAJOR_INTERVALS = { 0, 2, 4, 5, 7, 9, 11 };

    /**
     * The rest offset for each octave.
     */
    private int restOffset = 0;

    /**
     * The keyNote is the root note for a given chromosome. It's been calculated
     * as the index of the NOTE plus the octave times NOTES.length.
     */
    private int keyNote = 72; // C6

    /**
     * The root note for the given chromosome.
     */
    private String rootNote = "C";

    /**
     * The octave for the given chromosome.
     */
    private int octave = 5;

    /**
     * An array holding the musicPattern split up by -
     */
    private String[] chordProgression;

    /**
     * The music pattern in terms of music. beginning
     */
    private String musicPattern = "I-IV-V-I";

    /**
     * The index of the beginning duration in the DURATION_MAP.
     */
    public int beginningDuration = 7; // eight

    /**
     * The number of notes in a phrase (chromosome).
     */
    private int phraseNotes = 8;

    /**
     * The maximum possible jump for a gene in the interval portion. This value
     * is handled by jgap.
     */
    private int maxIntervalJump = 5;

    /**
     * The maximum possible jump for a gene in the duration portion. This value
     * is handled by jgap.
     */
    private int maxDurationJump = 1;

    /**
     * The bass pattern.
     */
    private String bassPattern;

    /**
     * The drum pattern.
     */
    private String drumPattern;

    /**
     * The tempo (is handled by jfugue).
     */
    private String tempo = "Allegro";

    /**
     * The index of the solo organ. Refer to jfuguekeyNote manual for the organ
     * indexing.
     */
    private int soloOrgan = 72;

    /**
     * Applying the singleton pattern.
     */
    private MusicConfiguration() {
        // The defineChordProgression function is called to set the
        // chordProgression array.
        defineChordProgression();
    }

    /**
     * 
     * @return The instance of the MusicConfiguration class.
     */
    public static MusicConfiguration getInstance() {
        return INSTANCE;
    }

    /**
     * 
     * @return a integer representing the different possible values in a scale
     */
    public int getScaleValuesCnt() {
        return NOTES.length + restOffset;
    }

    /**
     * Updates the the keyNote when rootNote, restOffset or octave is changed.
     */
    private void updateKeyNote() {
        keyNote = getNoteIndex(rootNote)
                + ((NOTES.length + restOffset) * (octave + 1));
    }

    /**
     * Gives the index of the given note as it appears in the NOTES array.
     * 
     * @param theNote
     *            a given note
     * @return the index of the note in the NOTES array.
     */
    private int getNoteIndex(String theNote) {
        for (int i = 0; i < NOTES.length; i++)
            if (theNote.equalsIgnoreCase(NOTES[i]))
                return i;

        // this should never happen.
        throw new InvalidConfigurationException("The given note : " + theNote
                + " is invalid, this should never happen");
    }

    /**
     * This method splits the musicPattern and sets the chordProgression array.
     * TODO handle errors
     */
    private void defineChordProgression() {

        String chrds[] = musicPattern.split("-");

        chordProgression = new String[chrds.length];

        for (int i = 0; i < chrds.length; i++)
            chordProgression[i] = chrds[i];

    }

    /**
     * @return the chordProgression
     */
    public String[] getChordProgression() {
        return chordProgression;
    }

    /**
     * @return the restOffset
     */
    public int getRestOffset() {
        return restOffset;
    }

    /**
     * Rest offset values are between [0,12].
     * 
     * @param restOffset
     *            the restOffset to set
     */
    public void setRestOffset(int restOffset) {
        // if restOffset is not in the bounds [0,12]
        // set it to default value
        if (restOffset < 0 || restOffset > 12) {
            System.out
                    .println("MusicConfiguration: restOffset invalid value : "
                            + restOffset + ". Setting to default (0).");
            restOffset = 0;
        }

        this.restOffset = restOffset;
        // update the keyNote
        updateKeyNote();
    }

    /**
     * @return the keyNote
     */
    public int getKeyNote() {
        return keyNote;
    }

    /**
     * @return the rootNote
     */
    public String getRootNote() {
        return rootNote;
    }

    /**
     * @param rootNote
     *            the rootNote to set
     */
    public void setRootNote(String rootNote) {

        boolean noteFound = false;

        for (String n : NOTES)
            if (rootNote.equalsIgnoreCase(n))
                noteFound = true;

        if (!noteFound) {
            System.out.println("MusicConfiguration: Invalid rootNote value :"
                    + rootNote + ". Setting to default (C).");
            rootNote = "C";
        }
        this.rootNote = rootNote;
        // update the keyNote
        updateKeyNote();
    }

    /**
     * @return the octave
     */
    public int getOctave() {
        return octave;
    }

    /**
     * @param octave
     *            the octave to set
     */
    public void setOctave(int octave) {
        if (octave < 2 || octave > 7) {
            System.out.println("MusicConfiguration: Invalid octave value :"
                    + octave + ". Setting to default (5).");
            octave = 5;
        }
        this.octave = octave;
        // update the keyNote
        updateKeyNote();
    }

    /**
     * @return the musicPattern
     */
    public String getMusicPattern() {
        return musicPattern;
    }

    /**
     * @param musicPattern
     *            the musicPattern to set
     */
    public void setMusicPattern(String musicPattern) {
        this.musicPattern = musicPattern;
        // define chord progression
        defineChordProgression();
    }

    /**
     * @return the beginningDuration
     */
    public int getBeginningDuration() {
        return beginningDuration;
    }

    /**
     * @param beginningDuration
     *            the beginningDuration to set
     */
    public void setBeginningDuration(int beginningDuration) {
        if (beginningDuration < 1 || beginningDuration > 6) {
            System.out
                    .println("MusicConfiguration: Invalid beginning duration value :"
                            + beginningDuration + ". Setting to default (7).");
            beginningDuration = 7;
        }
        this.beginningDuration = beginningDuration;
    }

    /**
     * @return the phraseNotes
     */
    public int getPhraseNotes() {
        return phraseNotes;
    }

    /**
     * @param phraseNotes
     *            the phraseNotes to set
     */
    public void setPhraseNotes(int phraseNotes) {
        if (phraseNotes < 4 || phraseNotes > 32) {
            System.out
                    .println("MusicConfiguration: Invalid phrase notes value :"
                            + phraseNotes + ". Setting to default (12).");
            phraseNotes = 12;
        }
        this.phraseNotes = phraseNotes;
    }

    /**
     * @return the maxIntervalJump
     */
    public int getMaxIntervalJump() {
        return maxIntervalJump;
    }

    /**
     * @param maxIntervalJump
     *            the maxIntervalJump to set TODO check weather changing this
     *            according to the restOffset is needed
     */
    public void setMaxIntervalJump(int maxIntervalJump) {
        if (maxIntervalJump < 1 || maxIntervalJump > 12) {
            System.out
                    .println("MusicConfiguration: Invalid maxIntevalJump value :"
                            + maxIntervalJump + ". Setting to default (5).");
            maxIntervalJump = 5;
        }
        this.maxIntervalJump = maxIntervalJump;
    }

    /**
     * @return the maxDurationJump
     */
    public int getMaxDurationJump() {
        return maxDurationJump;
    }

    /**
     * @param maxDurationJump
     *            the maxDurationJump to set
     */
    public void setMaxDurationJump(int maxDurationJump) {
        if (maxDurationJump < 1 || maxDurationJump > 6) {
            System.out
                    .println("MusicConfiguration: Invalid maxDurationJump value :"
                            + maxDurationJump + ". Setting to default (1).");
            maxDurationJump = 1;
        }
        this.maxDurationJump = maxDurationJump;
    }

    /**
     * @return the bassPattern
     */
    public String getBassPattern() {
        return bassPattern;
    }

    /**
     * @param bassPattern
     *            the bassPattern to set
     */
    public void setBassPattern(String bassPattern) {
        this.bassPattern = bassPattern;
    }

    /**
     * @return the drumPattern
     */
    public String getDrumPattern() {
        return drumPattern;
    }

    /**
     * @param drumPattern
     *            the drumPattern to set
     */
    public void setDrumPattern(String drumPattern) {
        this.drumPattern = drumPattern;
    }

    /**
     * @return the tempo
     */
    public String getTempo() {
        return tempo;
    }

    /**
     * @param tempo
     *            the tempo to set
     */
    public void setTempo(String tempo) {

        boolean tempoFound = false;

        for (String a_tempo : TEMPOS)
            if (tempo.equalsIgnoreCase(a_tempo))
                tempoFound = true;

        if (!tempoFound) {
            System.out.println("MusicConfiguration: Invalid tempo value :"
                    + tempo + ". Setting to default (Allegro).");
            tempo = "Allegro";
        }
        this.tempo = tempo;
    }

    /**
     * @return the soloOrgan
     */
    public int getSoloOrgan() {
        return soloOrgan;
    }

    /**
     * @param soloOrgan
     *            the soloOrgan to set
     */
    public void setSoloOrgan(int soloOrgan) {
        if (soloOrgan < 0 || soloOrgan > 127) {
            System.out
                    .println("MusicConfiguration: Invalid solo organ value :"
                            + maxDurationJump
                            + ". Setting to default (75). \n"
                            + " . Refer to the jfugue manual for the organ-value relation.\n"
                            + "http://www.jfugue.org/jfugue-chapter2.pdf , p.12");
            soloOrgan = 75;
        }
        this.soloOrgan = soloOrgan;
    }

    public static void main(String[] args) {
        MusicConfiguration mc = new MusicConfiguration();
        mc.setMusicPattern("I-I-I-I-V-V-V-VI-VI-I-I");
        for (String s : mc.chordProgression)
            System.out.println(MAJOR_INTERVALS[CHORDS.get(s)]);
        System.out.println();
    }

    public String toString() {
	    String str = "---------------------------------------------------\n" +
	                 "Music Configuration                                \n" +
	                 "---------------------------------------------------\n\n" +
	                 "Rest Offset : " + restOffset + "\n" +
	                 "Key Note : " + keyNote + "\n" + 
	                 "Root Note : " + rootNote + " \n" +
	                 "Octave : " + octave + "\n" +
	                 "Music Pattern : " + musicPattern + "\n" +
	                 "Beggining Duration : " + beginningDuration + "\n" +
	                 "Phrase Notes : " + phraseNotes + "\n" + 
	                 "Max Interval Jump : " + maxIntervalJump + "\n" + 
	                 "Max Duration Jump : " + maxDurationJump + "\n" +
	                 "Bass Pattern : " + bassPattern + "\n" +
	                 "Drum Pattern : " + drumPattern + "\n" +
	                 "Tempo : " + tempo + "\n" + 
	                 "Solo Organ : " + soloOrgan + "\n\n";
	                 
	   return str;              
	}
}
