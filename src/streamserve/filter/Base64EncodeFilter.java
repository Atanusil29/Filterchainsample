package streamserve.filter;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Base64;

import streamserve.context.Context;
import streamserve.context.ContextFactory;
import streamserve.context.LogService;

public class Base64EncodeFilter extends Base64Filter implements Execute {

	@Override
	public void invoke(InputStream decoded, OutputStream encoded) {
		
		Context context = ContextFactory.acquireContext( ContextFactory.ServiceContextType );
		LogService log = context.getInterface( LogService.class );
		String filter = "Base64EncodeFilter";

		byte[] inputBytes = this.readAll(decoded, log, filter);
		byte[] outputBytes = Base64.encodeBase64(inputBytes);
		this.writeAll(encoded, outputBytes, log, filter);
	}

}
