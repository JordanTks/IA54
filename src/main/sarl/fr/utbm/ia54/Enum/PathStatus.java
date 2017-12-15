package fr.utbm.ia54.Enum;

public enum PathStatus {

	// Good path, does not bother any happy tile
	GOOD,
	
	// Bad path, does bother some happy tiles on the way
	BAD,
	
	// Terrible path, does bother happy tiles which had the token in the past
	TERRIBLE;
}
