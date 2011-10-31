/**
 * This file is part of EvolTrio.
 *
 * EvolTrio is licensed under the GPLv3.
 *
 * For licensing information please see the file license.txt included with EvolTrio
 * or have a look at the top of class gr.evoltrio.evomusic.Evolution which representatively
 * includes the EvolTrio license policy applicable for any file delivered with EvolTrio.
 */
package gr.evomusic.evoltrio.test;

import gr.evomusic.evoltrio.fitness.IFitnessFilter;
import gr.evomusic.evoltrio.fitness.impl.SimpleDurationFilter;
import gr.evomusic.evoltrio.fitness.impl.SimplePitchFilter;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public class TestMap {

	public static void main(String[] args) {
//		Map<String, IFitnessFilter> filters = new HashMap<String, IFitnessFilter>();
//
//		filters.put("simplePitch", new SimplePitchFilter());
//		filters.put("simplePitch", new SimplePitchFilter());
//		filters.put("simpleDuration", new SimpleDurationFilter());
//		
//		for(IFitnessFilter filter : filters.values())
//			System.out.println(filter.);
		
		//System.out.println(filters);
		
		int a = 4;
		int b = 2;
		
		int x = new TestMap().test(a, b);
		System.exit(1);
		System.out.println(x);
		
		
	}
	/**
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public int test(int a,int b){
		try {
			return a/b;
		} catch (Error e) {
			return Integer.MAX_VALUE;
		}
	}
	
	
}
