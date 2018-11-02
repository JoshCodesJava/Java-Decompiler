import java.awt.Color;
import java.awt.color.ColorSpace;

public final class Test extends Color implements Runnable, Comparable<Test>
{
	public int a = 4;
	private final static int c = 6;
	double b = 3.4244;
	int[][] k;
	final String s = "Hello World";
	Object o = new Object();
	String q = "SDS";
	final double PI = 3.1415926535897;
	final boolean TEST = true;	

	public Test(ColorSpace cspace, float[] components, float alpha)
	{
		super(cspace, components, alpha);
	}

	@Override
	public int compareTo(Test o) 
	{
		return 0;
	}

	@Override
	public void run() 
	{
		
	}
}