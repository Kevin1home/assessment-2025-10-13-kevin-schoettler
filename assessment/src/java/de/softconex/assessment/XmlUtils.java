package de.softconex.assessment;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.custommonkey.xmlunit.Diff;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;

/**
 * Tools for handling XML documents.
 */
public class XmlUtils {
	private final static Log LOG = LogFactory.getLog(XmlUtils.class);

	/**
	 * Parse the contents of a given resource into a document object. The resource
	 * will be located using XmlUtils' class loader (see {@link XmlUtils#getClass()}
	 * and
	 * 
	 * @link {@link Class#getResourceAsStream(String)}).
	 * 
	 * @param resourceName
	 * @return
	 */
	public final static Document parseResource(final String resourceName) {
		try {
			final SAXReader reader = new SAXReader();
			final Document document = reader.read(XmlUtils.class.getResourceAsStream(resourceName));
			return document;
		} catch (final Exception ex) {
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException("Cannot parse [" + resourceName + "]: " + ex.getMessage(), ex);
		}
	}

	/**
	 * Returns whether the documents are either identical or at least similar
	 * (ignoring whitespace).
	 * 
	 * @param doc1
	 * @param doc2
	 * @return
	 */
	public final static boolean similarDocuments(final Document doc1, final Document doc2) {
		String xml1 = null;
		String xml2 = null;

		try {
			xml1 = toCompactXml(doc1);
			xml2 = toCompactXml(doc2);

			if (LOG.isInfoEnabled()) {
				LOG.info("xml1: " + xml1);
				LOG.info("xml2: " + xml2);
			}

			if (xml1.equals(xml2)) {
				LOG.info("xml1.equals(xml2) == true");
				return true;
			}

			final Diff diff = new Diff(xml1, xml2);

			final boolean similar = diff.similar();
			LOG.info("similar: " + similar);

			return similar;
		} catch (final Exception ex) {
			LOG.error(ex.getMessage(), ex);
			LOG.error("xml1: " + xml1);
			LOG.error("xml2: " + xml2);
			throw new RuntimeException("Error comparing XML documents: " + ex.getMessage(), ex);
		}
	}

	/**
	 * Convert document to "pretty" XML (one node per line etc.).
	 * 
	 * @param doc
	 * @return
	 */
	public final static String toPrettyXml(final Document doc) {
		try (StringWriter sw = new StringWriter()) {
			final OutputFormat outputFormat = OutputFormat.createPrettyPrint();

			try (MyXMLWriter xmlWriter = new MyXMLWriter(sw, outputFormat)) {
				xmlWriter.get().write(doc);
			}

			return sw.toString();
		} catch (final IOException ex) {
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException("Error converting document to pretty xml: " + ex.getMessage(), ex);
		}
	}

	/**
	 * Convert document to "compact" XML (if possible, only one line).
	 * 
	 * @param doc
	 * @return
	 */
	public final static String toCompactXml(final Document doc) {
		try (StringWriter sw = new StringWriter()) {
			final OutputFormat outputFormat = OutputFormat.createCompactFormat();

			try (MyXMLWriter xmlWriter = new MyXMLWriter(sw, outputFormat)) {
				xmlWriter.get().write(doc);
			}

			return sw.toString();
		} catch (final IOException ex) {
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException("Error converting document to compact xml: " + ex.getMessage(), ex);

		}
	}

	/**
	 * Validate an XML document against an XSD file.
	 * 
	 * @param doc
	 *            XML document to be validated
	 * @param xsdFileResourceName
	 *            resource name (in class path) to XSD schema document.
	 */
	public final static void validate(final Document doc, final String xsdFileResourceName) {
		try (InputStream xsdInput = XmlUtils.class.getResourceAsStream(xsdFileResourceName)) {
			// Copy XSD from resource to (temporary) file
			try (MyTempFile xsdFile = new MyTempFile("XmlUtils", ".xsd")) {
				try (OutputStream xsdOutput = new FileOutputStream(xsdFile.get())) {
					IOUtils.copy(xsdInput, xsdOutput);

					// Create XSD Schema
					final SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

					final Source schemaFile = new StreamSource(xsdFile.get());
					final Schema schema = factory.newSchema(schemaFile);

					// Validate XML
					final Validator validator = schema.newValidator();

					try (MyTempFile xmlFile = new MyTempFile("XmlUtils", ".xml")) {
						FileUtils.writeStringToFile(xmlFile.get(), doc.asXML());

						validator.validate(new StreamSource(new FileInputStream(xmlFile.get())));
					}
				}
			}
		} catch (final Exception ex) {
			LOG.error(ex.getMessage(), ex);
			throw new RuntimeException(
					"Cannot validate XML against XSD file [" + xsdFileResourceName + "]: " + ex.getMessage(), ex);
		}
	}
}
