/**
 * This file is part of EvolTrio.
 *
 * EvolTrio is licensed under the GPLv3.
 *
 * For licensing information please see the file license.txt included with EvolTrio
 * or have a look at the top of class gr.evoltrio.evomusic.Evolution which representatively
 * includes the EvolTrio license policy applicable for any file delivered with EvolTrio.
 */
package gr.evoltrio.fitness;

import gr.evoltrio.fitness.impl.AscendingFilter;
import gr.evoltrio.fitness.impl.DescendingFilter;
import gr.evoltrio.fitness.impl.DullFilter;
import gr.evoltrio.fitness.impl.HighAndLowFilter;
import gr.evoltrio.fitness.impl.OutOfScaleFilter;
import gr.evoltrio.fitness.impl.RootNoteFilter;
import gr.evoltrio.fitness.impl.SimpleDurationFilter;
import gr.evoltrio.fitness.impl.SimplePitchFilter;
import gr.evoltrio.fitness.impl.ThreeNoteFilter;
import gr.evoltrio.fitness.impl.TimeFilter;

/**
 * 
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public class FiltersFactory {

    public static enum Filter {
        SIMPLEPITCH, SIMPLEDURATION, OUTOFSCALE, TIME, DULL, HIGHANDLOW, ROOTNOTE, ASCENDING, DESCENDING, THREENOTE
    }

    public static IFitnessFilter getFilter(Filter filter) {
        switch (filter) {
        case SIMPLEPITCH:
            return new SimplePitchFilter();
        case SIMPLEDURATION:
            return new SimpleDurationFilter();
        case OUTOFSCALE:
            return new OutOfScaleFilter();
        case TIME:
            return new TimeFilter();
        case DULL:
            return new DullFilter();
        case HIGHANDLOW:
            return new HighAndLowFilter();
        case ROOTNOTE:
            return new RootNoteFilter();
        case ASCENDING:
            return new AscendingFilter();
        case DESCENDING:
            return new DescendingFilter();
        case THREENOTE:
            return new ThreeNoteFilter();

        default:
            return null; // should never happen
        }
    }

    // TODO remove this
    public static void main(String[] args) {
        for (Filter f : Filter.values())
            System.out.println(f);
    }

}
