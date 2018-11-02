import java.awt.Color;
import java.awt.color.ColorSpace;
import java.util.ArrayList;
import java.util.HashMap;

public final class Test<T extends Number> extends Color implements Runnable, Comparable<Test>
{
	public int a = 4;
	private final static int c = 6;
	double b = 3.4244;
	int[][] k;
	@Deprecated
	final String s = "Hello World";
	@TestAttribute
	Object o = new Object();
	String q = "SDS";
	final double PI = 3.1415926535897;
	final boolean TEST = true;
	volatile transient boolean r;
	ArrayList<? super Number> h = new ArrayList();
	HashMap<Integer, String> pop = new HashMap();


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
		System.out.println("Hello World");
	}
}