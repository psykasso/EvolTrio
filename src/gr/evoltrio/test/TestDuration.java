/**
 * This file is part of EvolTrio.
 *
 * EvolTrio is licensed under the GPLv3.
 *
 * For licensing information please see the file license.txt included with EvolTrio
 * or have a look at the top of class gr.evoltrio.evomusic.Evolution which representatively
 * includes the EvolTrio license policy applicable for any file delivered with EvolTrio.
 */
package gr.evoltrio.test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public class TestDuration {
	public static final Map<String, Integer> CHORDS;
	public static final Map<String, Double> DURATION_VALUES;
	static{
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
		durationMap.put("q.", 1.5);
		durationMap.put("q", 1d);
		durationMap.put("q*", 0.66);
		
		durationMap.put("i.", 0.75);
		durationMap.put("i", 0.5);
		durationMap.put("i*", 0.33);
		
		durationMap.put("s.", 0.375);
		durationMap.put("s", 0.25);
		durationMap.put("s*", 0.167);
		
		DURATION_VALUES = Collections.unmodifiableMap(durationMap);
	}
	
	public static void main(String[] args) {
		System.out.println(DURATION_VALUES.keySet().toArray()[0]);
	}
	

}
