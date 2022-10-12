package org.dekuan.airpassport.lib.exceptions;

import org.springframework.core.NestedRuntimeException;

//
//	Checked Exceptions
//	--------------------
//	In general, checked exceptions represent errors outside the control of the program.
//	For example, the constructor of FileInputStream throws FileNotFoundException if
//	the input file does not exist.
//
//	Java verifies checked exceptions at compile-time.
//	Therefore, we should use the throws keyword to declare a checked exception
//
//	Unchecked Exceptions
//	--------------------
//	Java does not verify unchecked exceptions at compile-time.
//	Furthermore, we don't have to declare unchecked exceptions in a method with the throws keyword.
//	And although the above code does not have any errors during compile-time,
//	it will throw ArithmeticException at runtime.
//
//	Some common unchecked exceptions in Java are NullPointerException,
//	ArrayIndexOutOfBoundsException and IllegalArgumentException.
//	The RuntimeException class is the superclass of all unchecked exceptions,
//	so we can create a custom unchecked exception by extending RuntimeException:
//
//
//	When to Use Checked Exceptions and Unchecked Exceptions
//	------------------------------------------------------------
//	If a client can reasonably be expected to recover from an exception,
//	make it a checked exception. If a client cannot do anything to recover from the exception,
//	make it an unchecked exception.
//
//	For example, before we open a file, we can first validate the input file name.
//	If the user input file name is invalid, we can throw a custom checked exception:
//	| if ( ! isCorrectFileName( fileName ) ) {
//	|	throw new IncorrectFileNameException("Incorrect filename : " + fileName );
//	| }
//
//	In this way, we can recover the system by accepting another user input file name.
//	However, if the input file name is a null pointer or it is an empty string,
//	it means that we have some errors in the code. In this case, we should throw an unchecked exception:
//	| if ( fileName == null || fileName.isEmpty() )  {
//	| 	throw new NullOrEmptyException( "The filename is null or empty." );
//	| }
//


public class AirExceptions
{
	//
	public static class NotFound extends NestedRuntimeException
	{
		public NotFound( String sMessage )
		{
			super( sMessage );
		}
	}

	public static class InvalidParameter extends NestedRuntimeException
	{
		public InvalidParameter( String sMessage )
		{
			super( sMessage );
		}
	}
	public static class InvalidData extends NestedRuntimeException
	{
		public InvalidData( String sMessage )
		{
			super( sMessage );
		}
	}
	public static class DuplicateData extends NestedRuntimeException
	{
		public DuplicateData( String sMessage )
		{
			super( sMessage );
		}
	}

	public static class Timeout extends NestedRuntimeException
	{
		public Timeout( String sMessage )
		{
			super( sMessage );
		}
	}
	public static class Execute extends NestedRuntimeException
	{
		public Execute( String sMessage )
		{
			super( sMessage );
		}
	}
	public static class Creation extends NestedRuntimeException
	{
		public Creation( String sMessage )
		{
			super( sMessage );
		}
	}


	public static class ReadData extends NestedRuntimeException
	{
		public ReadData( String sMessage )
		{
			super( sMessage );
		}
	}
	public static class SaveData extends NestedRuntimeException
	{
		public SaveData( String sMessage )
		{
			super( sMessage );
		}
	}



	public static class ParseJson extends NestedRuntimeException
	{
		public ParseJson( String sMessage )
		{
			super( sMessage );
		}
	}
	public static class BuildJson extends NestedRuntimeException
	{
		public BuildJson( String sMessage )
		{
			super( sMessage );
		}
	}

	public static class ParseJwt extends NestedRuntimeException
	{
		public ParseJwt( String sMessage )
		{
			super( sMessage );
		}
	}

	public static class ExpiredObject extends NestedRuntimeException
	{
		public ExpiredObject( String sMessage )
		{
			super( sMessage );
		}
	}

	public static class NullObject extends NestedRuntimeException
	{
		public NullObject( String sMessage )
		{
			super( sMessage );
		}
	}

	public static class EmptyObject extends NestedRuntimeException
	{
		public EmptyObject( String sMessage )
		{
			super( sMessage );
		}
	}


	public static class Unsupported extends NestedRuntimeException
	{
		public Unsupported( String sMessage )
		{
			super( sMessage );
		}
	}


	public static class Conflict extends NestedRuntimeException
	{
		public Conflict( String sMessage )
		{
			super( sMessage );
		}
	}

	public static class SendMessage extends NestedRuntimeException
	{
		public SendMessage( String sMessage )
		{
			super( sMessage );
		}
	}

	public static class CheckLogin extends NestedRuntimeException
	{
		public CheckLogin( String sMessage )
		{
			super( sMessage );
		}
	}


	public static class Validation extends NestedRuntimeException
	{
		public Validation( String sMessage )
		{
			super( sMessage );
		}
	}

}
