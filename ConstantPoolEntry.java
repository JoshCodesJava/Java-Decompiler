public class ConstantPoolEntry 
{
	private int type;
	private Object[] args;

	public ConstantPoolEntry(int type, Object[] args)
	{
		this.type = type;
		this.args = args;
	}

	public int getType() 
	{
		return type;
	}

	public Object[] getArgs() 
	{
		return args;
	}
}