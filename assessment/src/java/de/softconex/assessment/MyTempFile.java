package de.softconex.assessment;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MyTempFile implements AutoCloseable {
	private final static Log LOG = LogFactory.getLog(MyTempFile.class);

	private final File file;

	public MyTempFile(final String prefix, final String suffix) {
		super();

		try {
			this.file = File.createTempFile(prefix, suffix);
		} catch (final Exception ex) {
			LOG.error(ex);
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void close() throws Exception {
		get().delete();
	}

	public File get() {
		return this.file;
	}
}
