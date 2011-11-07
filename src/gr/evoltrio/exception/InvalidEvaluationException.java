/**
 * This file is part of EvolTrio.
 *
 * EvolTrio is licensed under the GPLv3.
 *
 * For licensing information please see the file license.txt included with EvolTrio
 * or have a look at the top of class gr.evoltrio.evomusic.Evolution which representatively
 * includes the EvolTrio license policy applicable for any file delivered with EvolTrio.
 */
package gr.evoltrio.exception;

/**
 * 
 * This exception is thrown when some of the fitness filters
 * does not behave as excepted.
 *
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public class InvalidEvaluationException extends Exception {


	/**
	 * Constructs a new InvalidEvaluationException instance with the
	 * given error message.
   	 * 
	 * @param message an error message describing the reason this exception
	 * is being thrown
	 */
	public InvalidEvaluationException(String message) {
		super(message);
	}


}
