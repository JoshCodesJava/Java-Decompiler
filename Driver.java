import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Driver 
{
	private static final int ACC_PUBLIC = 0x0001;
	private static final int ACC_FINAL = 0x0010;
	private static final int ACC_INTERFACE = 0x0200;
	private static final int ACC_ABSTRACT = 0x0400;
	private static final int ACC_ANNOTATION = 0x2000;//not implemented yet
	private static final int ACC_ENUM = 0x4000;
	private static final int ACC_PRIVATE = 0x0002;
	private static final int ACC_PROTECTED = 0x0004;
	private static final int ACC_STATIC = 0x0008;
	private static final int ACC_VOLATILE = 0x0040;
	private static final int ACC_TRANSIENT = 0x0080;
	
	public static void main(String[] args) throws IOException
	{
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("Java Class Files", "class");
	    chooser.setFileFilter(filter);
	    chooser.showOpenDialog(null);
		DataInputStream in = new DataInputStream(new FileInputStream(chooser.getSelectedFile()));
		
		if(in.readInt() != 0xCAFEBABE)
		{
			in.close();
			throw new RuntimeException("Invalid Magic");
		}
		
		in.readInt();
		ConstantPool pool = ConstantPool.parse(in);
		
		int modifiers = in.readUnsignedShort();
		
		if((modifiers & ACC_PUBLIC) != 0)
			System.out.print("public ");
		if((modifiers & ACC_FINAL) != 0)
			System.out.print("final ");
		if((modifiers & ACC_ABSTRACT) != 0)
			System.out.print("abstract ");
		if((modifiers & ACC_INTERFACE) != 0)
			System.out.print("interface ");
		else if((modifiers & ACC_ENUM) != 0)
			System.out.print("enum ");
		else if((modifiers & ACC_ANNOTATION) != 0)
			System.out.println("annotation");
		else
			System.out.print("class ");
		
		int thisClass = in.readUnsignedShort();
		String name = (String) pool.getEntry((int) pool.getEntry(thisClass).getArgs()[0]).getArgs()[0];
		int superClass = in.readUnsignedShort();
		String superName = (String) pool.getEntry((int) pool.getEntry(superClass).getArgs()[0]).getArgs()[0];
		System.out.print(name + " extends " + parseName(superName));
		int interfaces = in.readUnsignedShort();
		
		if(interfaces > 0)
		{
			System.out.print(" implements ");
			
			for(int i = 0; i < interfaces; i++)
			{
				int interfaceNum = in.readUnsignedShort();
				System.out.print(parseName((String) pool.getEntry((int) pool.getEntry(interfaceNum).getArgs()[0]).getArgs()[0]) + ((i == interfaces-1) ? "" : ", "));
			}
		}
		
		System.out.println();
		System.out.println("{");
		parseFields(in, pool);
		System.out.println("}");
		in.close();
	}

	private static void parseFields(DataInputStream in, ConstantPool pool) throws IOException 
	{
		int numOfFields = in.readUnsignedShort();
		
		for(int i = 0; i < numOfFields; i++)
		{
			int modifiers = in.readUnsignedShort();
			String identifier = "\t";
			
			if((modifiers & ACC_PUBLIC) != 0)
				identifier+="public ";
			if((modifiers & ACC_PRIVATE) != 0)
				identifier+="private ";
			if((modifiers & ACC_PROTECTED) != 0) 
				identifier+="protected ";
			if((modifiers & ACC_STATIC) != 0)
				identifier+="static ";
			if((modifiers & ACC_FINAL) != 0)
				identifier+="final ";
			if((modifiers & ACC_VOLATILE) != 0)
				identifier+="volatile ";
			if((modifiers & ACC_TRANSIENT) != 0)
				identifier+="transient ";

			String idName = (String) pool.getEntry(in.readUnsignedShort()).getArgs()[0];
			String descriptor = (String) pool.getEntry(in.readUnsignedShort()).getArgs()[0];
			System.out.print(identifier + parseName(Utility.parseFieldName(descriptor)) + " " + idName);
			int attributes = in.readUnsignedShort();
			
			for(int j = 0; j < attributes; j++)
			{
				Attribute att = Attribute.parseAttribute(in, pool);
				
				switch(att.getName())
				{
				case "ConstantValue":
					int index = att.getData()[0];
					index<<=8;
					index+=att.getData()[1];
					ConstantPoolEntry entry = pool.getEntry(index);

					switch(entry.getType())
					{
					case ConstantPool.CONSTANT_Long:
						System.out.print(" = "+Utility.buildLong(entry.getArgs()));
						break;
					case ConstantPool.CONSTANT_Float:
						System.out.print(" = "+Utility.buildFloat(entry.getArgs()));
						break;
					case ConstantPool.CONSTANT_Double:
						System.out.print(" = "+Utility.buildDouble(entry.getArgs()));
						break;
					case ConstantPool.CONSTANT_Integer:
						int val = Utility.buildInteger(entry.getArgs());
						System.out.print(" = " + (descriptor.equals("Z") ? (val == 0 ? "false" : "true") : val));
						break;
					case ConstantPool.CONSTANT_String:
						System.out.print(" = \"" + pool.getEntry((int) entry.getArgs()[0]).getArgs()[0] + "\"");
						break;
					}
				
					break;
				}
			}
			
			System.out.println(";");
		}
	}

	private static String parseName(String superName) 
	{
		return superName.replace("/", ".");
	}
}