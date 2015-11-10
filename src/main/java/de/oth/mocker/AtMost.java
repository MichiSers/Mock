package de.oth.mocker;

/**
 * The atMost verification type.
 * Only getters and setters here.
 * 
 * @author Michael Stadler
 *
 */
public class AtMost implements VerificationType
{
	int times = 0;
	int atMost = 0;
	int atLeast = 0;
	
	public AtMost(int atMost){
		this.atMost = atMost;
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
