import java.io.DataInputStream;
import java.io.IOException;

public class Attribute
{
	private String name;
	private byte[] data;

	public Attribute(String attributeName, byte[] attData) 
	{
		this.name = attributeName;
		this.data = attData;
	}

	public static Attribute parseAttribute(DataInputStream in, ConstantPool pool) throws IOException 
	{
		String attributeName = (String) pool.getEntry(in.readUnsignedShort()).getArgs()[0];
		byte[] attData = new byte[in.readInt()];
		in.readFully(attData);
		return new Attribute(attributeName, attData);
	}
	
	public String getName()
	{
		return name;
	}
	
	public byte[] getData()
	{
		return data;
	}
}