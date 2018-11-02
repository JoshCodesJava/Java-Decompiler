public class Utility
{
	public static String parseFieldName(String descriptor)
	{
		switch(descriptor)
		{
		case "B":
			return "byte";
		case "C":
			return "char";
		case "D":
			return "double";
		case "F":
			return "float";
		case "I":
			return "int";
		case "J":
			return "long";
		case "S":
			return "short";
		case "Z":
			return "boolean";
		}
		
		if(descriptor.startsWith("L"))
		{
			return descriptor.replace("L", "").replace(";", "");
		}
		else if(descriptor.startsWith("["))
		{
			return parseFieldName(descriptor.substring(1))+"[]";
		}
		
		throw new RuntimeException("Unknown Type " + descriptor);
	}

	public static long buildLong(Object[] args) 
	{
		long build = (int) args[0];
		build<<=32;
		build+= (int) args[1];
		return build;
	}

	public static float buildFloat(Object[] args) 
	{
		return Float.intBitsToFloat(buildInteger(args));
	}

	public static double buildDouble(Object[] args) 
	{
		return Double.longBitsToDouble(buildLong(args));
	}

	public static int buildInteger(Object[] args) 
	{
		int build = (int) args[0];
		return build;
	}
}