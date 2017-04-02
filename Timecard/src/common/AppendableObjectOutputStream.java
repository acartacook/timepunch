package common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
//taken from stack overflow to fix issue
public class AppendableObjectOutputStream extends ObjectOutputStream {

	public AppendableObjectOutputStream(FileOutputStream inputStream) throws IOException, SecurityException {
		super();
	}


	@Override
	 protected void writeStreamHeader() throws IOException {
	   // do not write a header, but reset the handle list
	   reset();
	 }
}
