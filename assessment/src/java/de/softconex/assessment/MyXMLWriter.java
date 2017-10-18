package de.softconex.assessment;

import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class MyXMLWriter implements AutoCloseable {
	private final static Log LOG = LogFactory.getLog(MyXMLWriter.class);

	private final XMLWriter delegate;

	public MyXMLWriter(StringWriter sw, OutputFormat outputFormat) {
		delegate = new XMLWriter(sw, outputFormat);
	}

	@Override
	public void close() {
		try {
			delegate.close();
		} catch (final Exception ex) {
			LOG.error(ex);
		}
	}

	public XMLWriter get() {
		return delegate;
	}
}
