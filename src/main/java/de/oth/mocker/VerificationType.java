package de.oth.mocker;


/**
 * An interface to provide verification types
 * that can all be uses by the interceptor.
 * 
 * @author Michael Stadler
 *
 */
public interface VerificationType
{
	public int getTimes();
	public int getAtMost();
	public int getAtLeast();
	public VerificationType getType();
}
