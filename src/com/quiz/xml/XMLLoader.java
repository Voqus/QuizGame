package com.quiz.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * Static class that contains XML file operations for the quiz.
 *
 * @author Voqus
 */
public final class XMLLoader
{
    private static File _xmlFile;
    private static DocumentBuilderFactory _dbFactory;
    private static DocumentBuilder _dBuilder;
    public static Document _doc;

    /**
     * Loads the xml <i>file</i>
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static Document loadFile(final File file) throws IOException
    {
        if (file == null)
            throw new IOException("File was null");

        _xmlFile = file;

        try
        {
            _dbFactory = DocumentBuilderFactory.newInstance();
            _dBuilder = _dbFactory.newDocumentBuilder();
            _doc = _dBuilder.parse(_xmlFile);
            _doc.getDocumentElement().normalize();
        } // try
        catch (Exception e) //  multicatch not supported before java7 so general type implemented here
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "We're sorry but there is no such quiz available yet.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        } // catch
        return _doc;
    } // loadFile

    /**
     * Returns the value of the node of the element.
     *
     * @param tag
     * @param element
     * @return
     */
    public static String getValue(final String tag, final Element element)
    {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);

        return node.getNodeValue();
    } // getValue

    /**
     * Returns a list with the contained text of the tag's node.
     * 
     * @param doc
     * @param tagName
     * @return
     * @throws Exception
     */
    public static List<String> getTextOf(final Document doc, final String tagName)
    {
        List<String> notes = new ArrayList<>();
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();

        try
        {
            NodeList xText = (NodeList) xPath.evaluate("//" + tagName + "/text()", doc, XPathConstants.NODESET);

            for (int i = 0; i < xText.getLength(); ++i)
            {
                Text textElt = (Text) xText.item(i);
                String noteTxt = textElt.getTextContent().trim();

                if (!noteTxt.isEmpty())
                {
                    notes.add(noteTxt.trim());
                } // if
            } // for
        } // try 
        catch (Exception e) //  multicatch not supported before java7 so general type implemented here
        {
            e.printStackTrace();
        } // catch
        return notes;
    } // getTextOf
}
