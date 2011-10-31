/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.evomusic.evoltrio.midi;

import gr.evomusic.evoltrio.core.*;

import java.util.ArrayList;
import org.jgap.Gene;
import org.jgap.impl.IntegerGene;

/**
 *
 * @author psykasso
 */
public class ReverseChromoManipulation {

    EvolConfiguration conf;
    private ArrayList<Gene> genes;

    private String song;

    private int keyNote;
    private int begginingDuration;

    public ReverseChromoManipulation(String song, EvolConfiguration conf){
        this.song = song;
        this.conf = conf;
        
        genes = new ArrayList<Gene>();
    }

    public Gene[] getGenes(){
        Gene[] g = new Gene[genes.size()];
        genes.toArray(g);
        return g;
    }

    public void reverse() throws Exception{
        int index;

        if(song.substring(1, 2).equalsIgnoreCase("#")){
            index = 5;
            keyNote = getIntegerNote(song.substring(0, 2)) + getIntegerOctave(song.substring(2, 3));
            begginingDuration = getIntegerDuration(song.substring(3, 4));
        }
        else{
            index = 4;
            keyNote = getIntegerNote(song.substring(0, 1)) + getIntegerOctave(song.substring(1, 2));
            begginingDuration = getIntegerDuration(song.substring(2, 3));
        }

        int lastNote = keyNote;
        int lastDuration = begginingDuration;

        int currentNote = 0;
        int currentDuration = 0;

        
        IntegerGene tempGene;

        int diff = 0;
        //a value for the parsing of # string, in the case which the delta = 1, otherwise = 0 due to one extra character in the former case.
        int delta;

        while(index<song.length()){
            if(song.substring(index+1, index+2).equalsIgnoreCase("#")){
                delta = 1;
            }
            else{
                delta = 0;
            }
                currentNote = getIntegerNote(song.substring(index, index+1+delta)) + getIntegerOctave(song.substring(index+1+delta,index+2+delta));
                diff = currentNote - lastNote;
                lastNote = currentNote;
                
                tempGene = new IntegerGene(conf);
                tempGene.setAllele(new Integer(diff));

                genes.add(tempGene);

                currentDuration = getIntegerDuration(song.substring(index+2+delta, index+3+delta));
                diff = currentDuration - lastDuration;
                lastDuration = currentDuration;

                tempGene = new IntegerGene(conf);
                tempGene.setAllele(new Integer(diff));

                genes.add(tempGene);

                index += 4 + delta;
              
        }
    }


    private int getIntegerNote(String note){
        if(note.equalsIgnoreCase("C"))
            return 0;
        else if(note.equalsIgnoreCase("C#"))
            return 1;
        else if(note.equalsIgnoreCase("D"))
            return 2;
        else if(note.equalsIgnoreCase("D#"))
            return 3;
        else if(note.equalsIgnoreCase("E"))
            return 4;
        else if(note.equalsIgnoreCase("F"))
            return 5;
        else if(note.equalsIgnoreCase("F#"))
            return 6;
        else if(note.equalsIgnoreCase("G"))
            return 7;
        else if(note.equalsIgnoreCase("G#"))
            return 8;
        else if(note.equalsIgnoreCase("A"))
            return 9;
        else if(note.equalsIgnoreCase("A#"))
            return 10;
        else if(note.equalsIgnoreCase("B"))
            return 11;
        else
            return -1;
    }

    private int getIntegerOctave(String octave){
        return 12*Integer.parseInt(octave);
    }

    private int getIntegerDuration(String duration){
        if(duration.equalsIgnoreCase("w"))
            return 1;
        else if(duration.equalsIgnoreCase("h"))
            return 2;
        else if(duration.equalsIgnoreCase("q"))
            return 3;
        else if(duration.equalsIgnoreCase("i"))
            return 4;
        else if(duration.equalsIgnoreCase("s"))
            return 5;
        else if(duration.equalsIgnoreCase("t"))
            return 6;
        else
            return -1;
    }

    public static void main(String args[]) throws Exception{
        ReverseChromoManipulation rcm = new ReverseChromoManipulation("E5s A5s C6s B5s E5s B5s D6s C6i E6i G#5i E6i A5s E5s A5s C6s B5s E5s B5s D6s C6i A5i", new EvolConfiguration());

        rcm.reverse();


        Gene[] genes  = rcm.getGenes();

        for(int i=0; i<genes.length; i++)
            System.out.print((Integer)genes[i].getAllele() + " ");
        //System.out.println("skata".substring(0, 1));
    }

}
