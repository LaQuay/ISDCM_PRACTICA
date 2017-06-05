package balanaauth;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.wso2.balana.Indenter;
import org.wso2.balana.ctx.xacml3.Result;

/**
 *
 * @author marc.vila.gomez
 */
public class Utils {   
    
    public static void printResult(OutputStream output, Indenter indenter, Set results) {
        // Make a PrintStream for a nicer printing interface
        PrintStream out = new PrintStream(output);

        // Prepare the indentation string
        String indent = indenter.makeString();

        // Now write the XML...
        out.println(indent + "<Response>");

        // Go through all results
        Iterator it = results.iterator();
        indenter.in();
        while (it.hasNext()) {
            Result result = (Result)(it.next());
            out.append(result.encode());
        }
        indenter.out();

        // Finish the XML for a response
        out.println(indent + "</Response>");
    }
    
    public static String getXMLFromFilePath(String path) throws TransformerException, Exception {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(getDocument(path)), new StreamResult(writer));
        
        return writer.getBuffer().toString().replaceAll("\n|\r", "");
    }
    
    private static Document getDocument(String xmlFile) throws Exception {
        /* Get the instance of BuilderFactory class. */
        DocumentBuilderFactory builder = DocumentBuilderFactory.newInstance();

        /* Instantiate DocumentBuilder object. */
        DocumentBuilder docBuilder = builder.newDocumentBuilder();

        /* Get the Document object */
        Document document = docBuilder.parse(xmlFile);
        return document;
    }
    
    /**
     * Creates DOM representation of the XACML request
     *
     * @param response  XACML request as a String object
     * @return XACML request as a DOM element
     */
    public static Element getXacmlResponse(String response) {
        ByteArrayInputStream inputStream;
        DocumentBuilderFactory dbf;
        Document doc;

        inputStream = new ByteArrayInputStream(response.getBytes());
        dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);

        try {
            doc = dbf.newDocumentBuilder().parse(inputStream);
        } catch (Exception e) {
            System.err.println("DOM of request element can not be created from String");
            return null;
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
               System.err.println("Error in closing input stream of XACML response");
            }
        }
        return doc.getDocumentElement();
    }    
}
