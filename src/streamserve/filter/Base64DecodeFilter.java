package streamserve.filter;

import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.codec.binary.Base64;
import streamserve.context.*;

public class Base64DecodeFilter extends Base64Filter implements Execute {

	@Override
	public void invoke(InputStream encoded, OutputStream decoded) {
		
		Context context = null;
		
		try {
			
			context = ContextFactory.acquireContext( ContextFactory.ServiceContextType );
			LogService log = context.getInterface( LogService.class );
			String filter = "Base64DecodeFilter";
	
			byte[] inputBytes = this.readAll(encoded, log, filter);
			byte[] outputBytes = Base64.decodeBase64(inputBytes);
			this.writeAll(decoded, outputBytes, log, filter);
		}
		finally {
			
			if ( context != null ) 
				ContextFactory.releaseContext( context );
		}
	}

}
