package de.oth.mocker;

public interface VerificationType
{
	public int getTimes();
	public int getAtMost();
	public int getAtLeast();
	public VerificationType getType();
}
