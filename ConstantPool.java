import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

public final class ConstantPool 
{
	public static final int CONSTANT_Class = 7;
	public static final int CONSTANT_Fieldref = 9;
	public static final int CONSTANT_Methodref = 10;
	public static final int CONSTANT_InterfaceMethodref = 11;
	public static final int CONSTANT_String = 8;
	public static final int CONSTANT_Integer = 3;
	public static final int CONSTANT_Float = 4;
	public static final int CONSTANT_Long = 5;
	public static final int CONSTANT_Double = 6;
	public static final int CONSTANT_NameAndType = 12;
	public static final int CONSTANT_Utf8 = 1;
	public static final int CONSTANT_MethodHandle = 15;
	public static final int CONSTANT_MethodType = 16;
	public static final int CONSTANT_InvokeDynamic = 18;
	private ConstantPoolEntry[] entries;
	
	private ConstantPool(DataInputStream stream) throws IOException
	{
		int numOfEntries = stream.readUnsignedShort();
		entries = new ConstantPoolEntry[numOfEntries];
		
		for(int i = 1; i < numOfEntries; i++)
		{
			switch(stream.readUnsignedByte())
			{
			case CONSTANT_Class:
				entries[i] = new ConstantPoolEntry(CONSTANT_Class, new Object[]{stream.readUnsignedShort()});
				break;
			case CONSTANT_Fieldref:
				entries[i] = new ConstantPoolEntry(CONSTANT_Fieldref, new Object[]{stream.readUnsignedShort(), stream.readUnsignedShort()});
				break;
			case CONSTANT_Methodref:
				entries[i] = new ConstantPoolEntry(CONSTANT_Methodref, new Object[]{stream.readUnsignedShort(), stream.readUnsignedShort()});
				break;
			case CONSTANT_InterfaceMethodref:
				entries[i] = new ConstantPoolEntry(CONSTANT_InterfaceMethodref, new Object[]{stream.readUnsignedShort(), stream.readUnsignedShort()});
				break;
			case CONSTANT_String:
				entries[i] = new ConstantPoolEntry(CONSTANT_String, new Object[]{stream.readUnsignedShort()});
				break;
			case CONSTANT_Integer:
				entries[i] = new ConstantPoolEntry(CONSTANT_Integer, new Object[]{stream.readInt()});
				break;
			case CONSTANT_Float:
				entries[i] = new ConstantPoolEntry(CONSTANT_Float, new Object[]{stream.readInt()});
				break;
			case CONSTANT_Long:
				entries[i] = new ConstantPoolEntry(CONSTANT_Float, new Object[]{stream.readInt(), stream.readInt()});
				i++;
				break;
			case CONSTANT_Double:
				entries[i] = new ConstantPoolEntry(CONSTANT_Double, new Object[]{stream.readInt(), stream.readInt()});
				i++;
				break;
			case CONSTANT_NameAndType:
				entries[i] = new ConstantPoolEntry(CONSTANT_NameAndType, new Object[]{stream.readUnsignedShort(), stream.readUnsignedShort()});
				break;
			case CONSTANT_Utf8:
				entries[i] = new ConstantPoolEntry(CONSTANT_Utf8, new Object[]{stream.readUTF()});
				break;
			case CONSTANT_MethodHandle:
				entries[i] = new ConstantPoolEntry(CONSTANT_MethodHandle, new Object[]{stream.readUnsignedByte(), stream.readUnsignedShort()});
				break;
			case CONSTANT_MethodType:
				entries[i] = new ConstantPoolEntry(CONSTANT_MethodType, new Object[]{stream.readUnsignedShort()});
				break;
			case CONSTANT_InvokeDynamic:
				entries[i] = new ConstantPoolEntry(CONSTANT_InvokeDynamic, new Object[]{stream.readUnsignedShort(), stream.readUnsignedShort()});
				break;
			}
		}
	}
	
	public ConstantPoolEntry getEntry(int index)
	{
		return entries[index];
	}
	
	public static ConstantPool parse(DataInputStream stream) throws IOException
	{
		return new ConstantPool(stream);
	}
}