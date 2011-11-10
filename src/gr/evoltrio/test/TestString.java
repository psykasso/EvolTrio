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

import java.awt.print.Printable;

/**
 * 
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public class TestString {
	public static void main(String[] args) {
		String a = "a";
		String b = a;
		b += "x";
		
//		System.out.println(a);
//		System.out.println(b);
		
		double x = -1000;
		//double z = Math.pow((1d + ( 1d / x)),x);
		double z = Math.pow(Math.E,(1d + (x/(1-x))));
		System.out.println(z);
	}
}
