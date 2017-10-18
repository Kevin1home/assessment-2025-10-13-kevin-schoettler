package de.softconex.assessment;

import java.util.Iterator;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * Illustrate XML-Related tests.
 */
public class XmlUtilsTest extends TestCase {
	private final static Log LOG = LogFactory.getLog(XmlUtilsTest.class);

	/**
	 * Creates a new instance of a test document.
	 * 
	 * @return
	 */
	private final static Document createTestDocument() {
		final Document doc = DocumentHelper.createDocument();

		final Element root = doc.addElement("root");

		final Element node1 = root.addElement("node1");
		node1.addAttribute("attr1", "value1");
		node1.addAttribute("attr2", "value2");

		final Element node2 = root.addElement("node2");
		node2.addElement("node2_1").setText("node2_1");
		node2.addElement("node2_2").setText("node2_2");

		return doc;
	}

	/**
	 * Test creation of an XML document comparing the output to a reference from
	 * the classpath (=the eclipse workspace).
	 * 
	 */
	public final void testToXml() {
		final Document doc1 = createTestDocument();

		final String xml = doc1.asXML();
		LOG.info("xml1: " + xml);
		LOG.info("xml1: " + XmlUtils.toPrettyXml(doc1));

		// trivial cases - doc equals itself; doc equals document which has been
		// created the same way
		assertTrue(XmlUtils.similarDocuments(doc1, doc1));
		assertTrue(XmlUtils.similarDocuments(createTestDocument(), createTestDocument()));

		// parse document from ressource; please note that in the test document
		// white space
		// and attribute order is different
		final Document doc2 = XmlUtils.parseResource("/de/softconex/assessment/XmlUtilsTest.xml");
		LOG.info("xml2: " + XmlUtils.toPrettyXml(doc2));
		assertTrue(XmlUtils.similarDocuments(doc1, doc2));
	}

	/**
	 * Test the parsing of an XML document from the classpath.
	 * 
	 */
	public final void testParse() {
		final Document doc = XmlUtils.parseResource("/de/softconex/assessment/XmlUtilsTest.xml");

		assertEquals("root", doc.getRootElement().getName());

		boolean found = false;
		for (final Iterator<?> it = doc.getRootElement().elementIterator(); it.hasNext();) {
			final Element element = (Element) it.next();
			LOG.info("element: " + element.getName());
			if (element.getName().equals("node2")) {
				found = true;
			}
		}
		assertTrue(found);
	}

	/**
	 * Test the XSD Validation feature.
	 * 
	 */
	public final void testValidationSuccess() {
		final Document doc = XmlUtils.parseResource("/de/softconex/assessment/XmlUtilsTest.xml");

		XmlUtils.validate(doc, "/de/softconex/assessment/XmlUtilsTest.xsd");
	}

	/**
	 * Test the XSD Validation feature.
	 * 
	 */
	public final void testValidationFail() {
		final Document doc = createTestDocument();
		doc.getRootElement().addElement("bla").setText("lkadsjflsjdf");

		try {
			XmlUtils.validate(doc, "/de/softconex/assessment/XmlUtilsTest.xsd");
			fail();
		} catch (final Exception ex) {
			LOG.info("Caught expected " + ex.getMessage());
		}
	}
}
