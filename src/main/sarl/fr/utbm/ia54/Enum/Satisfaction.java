package fr.utbm.ia54.Enum;

/*
 *  This is used in order to choose the most pertinent target to attack.
 *  It is preferable to attack UNSATISFIED > RANDOMLY_SATISFIED > TOKEN_SATISFIED
 */

public enum Satisfaction {

	// Not satisified
	UNSATISFIED,
	
	// Satisfied because matched properly by luck
	RANDOMLY_SATISFIED,
	
	// Satisfied because used the token to get matched properly
	TOKEN_SATISFIED;
	
}
