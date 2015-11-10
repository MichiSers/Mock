package de.oth.mocker;

/**
 * The times and never verification type.
 * Only getters and setters here.
 * 
 * @author Michael Stadler
 *
 */
public class Times implements VerificationType
{
	int times = 0;
	int atMost = 0;
	int atLeast = 0;
	
	public Times(int times){
		this.times = times;
	}

	@Override
	public int getTimes()
	{
		return times;
	}

	@Override
	public int getAtMost()
	{
		return atMost;
	}

	@Override
	public int getAtLeast()
	{
		return atLeast;
	}

	@Override
	public VerificationType getType()
	{
		return this;
	}
}
