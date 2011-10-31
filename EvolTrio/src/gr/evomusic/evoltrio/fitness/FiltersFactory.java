/**
 * This file is part of EvolTrio.
 *
 * EvolTrio is licensed under the GPLv3.
 *
 * For licensing information please see the file license.txt included with EvolTrio
 * or have a look at the top of class gr.evoltrio.evomusic.Evolution which representatively
 * includes the EvolTrio license policy applicable for any file delivered with EvolTrio.
 */
package gr.evomusic.evoltrio.fitness;

import gr.evomusic.evoltrio.fitness.impl.AscendingFilter;
import gr.evomusic.evoltrio.fitness.impl.DescendingFilter;
import gr.evomusic.evoltrio.fitness.impl.DullFilter;
import gr.evomusic.evoltrio.fitness.impl.HighAndLowFilter;
import gr.evomusic.evoltrio.fitness.impl.OutOfScaleFilter;
import gr.evomusic.evoltrio.fitness.impl.RootNoteFilter;
import gr.evomusic.evoltrio.fitness.impl.SimpleDurationFilter;
import gr.evomusic.evoltrio.fitness.impl.SimplePitchFilter;
import gr.evomusic.evoltrio.fitness.impl.ThreeNoteFilter;
import gr.evomusic.evoltrio.fitness.impl.TimeFilter;

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
			return null;		// should never happen
		}
	}
	//TODO remove this
	public static void main(String[] args) {
		for(Filter f :Filter.values())
			System.out.println(f);
	}

}
