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

/**
 *
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public class TestDoubleComparison {

	public static void main(String[] args) {
		double x = 4.056;
		System.out.println( x < 4 ? x%4 : x/4);
	}
}
