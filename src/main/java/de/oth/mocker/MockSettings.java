package de.oth.mocker;

/**
 * The settings used at mock creation. Currently only the
 * spy option is availible.
 * 
 * @author Michael Stadler
 *
 */
public class MockSettings
{
	private boolean isSpy = false;

	public boolean isSpy()
	{
		return isSpy;
	}

	public void setSpy(boolean isSpy)
	{
		this.isSpy = isSpy;
	}


}
