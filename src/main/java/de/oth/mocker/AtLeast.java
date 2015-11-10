package de.oth.mocker;


/**
 * The atLeast verification type.
 * Only getters and setters here.
 * 
 * @author Michael Stadler
 *
 */
public class AtLeast implements VerificationType
{
	int times = 0;
	int atMost = 0;
	int atLeast = 0;
	
	public AtLeast(int atLeast){
		this.atLeast = atLeast;
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
