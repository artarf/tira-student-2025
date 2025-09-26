package oy.interact.tira.student;

import java.util.Date;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import oy.interact.tira.factories.StackFactory;
import oy.interact.tira.util.StackInterface;

public class PersonXMLToMarkdownHandler extends DefaultHandler {

    // If print is true, then code must print out error information to System.err
    private boolean print = true;    
    // STUDENT TODO: define a member variable for a StackInterface of Strings using StackInterface and StackFactory.

    private int personNumber = 1;
    private StringBuilder docBuilder = null;

    // Table for converting XML element names to Markdown output
    private static final String [][] elements = {
        { "document", "# Persons Document\n" },
        { "heading", "## Document Information\n" },
        { "subject", "\n * Subject: " },
        { "description", "\n * Description: " },
        { "generated", "\n * Original Document Date: " },
        { "persons", "\n\n# List of Customers" },
        { "person", "\n\n## Customer " },
        { "firstname", "\n - First name: " },
        { "middlename", "\n - Middle name: " },
        { "lastname", "\n - Family name: " },
        { "age", "\n - Age: " },
    };

    public PersonXMLToMarkdownHandler(boolean doPrintOuts) {
        print = doPrintOuts;
    }

    public String getProcessedOutput() {
        if (docBuilder != null) {
            return docBuilder.toString();
        }
        return "No XML processed yet!";
    }

    @Override
    public void setDocumentLocator(Locator locator) {
        // Empty
    }

    @Override
    public void startDocument() throws SAXException {
        // STUDENT TODO: Reset the stack by calling clear() so that is
        // resetted to empty state before starting to handle a new XML document.

        docBuilder = new StringBuilder();
        personNumber = 0;
    }

    @Override
    public void endDocument() throws SAXException {
        docBuilder.append("\n\n# Summary of records:\n\n");
        docBuilder.append(String.format(" * Persons in document: %d%n%n---%n", personNumber));
        // STUDENT TODO: check that stack has nothing inside,
        // otherwise XML is not valid (think why!?), and
        // in this situation you should:
        // 1. Append an error string information to docBuilder
        // 2. If member variable print is true, print error information also, using System.err.println.
        // 3. Finally, throw SAXException
    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        // Empty
    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
        // Empty
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        // Convert XML element name to Markdown using getMarkdownTextForXMLElement.
        final String markdownOutput = getMarkdownTextForXMLElement(qName);

        docBuilder.append(markdownOutput);
        if (print) {
            System.out.format("%s", markdownOutput);
        }
        if (qName.equals("document")) {
            // If this is the start of the persons xml doc, then print out the date of the conversion.
            Date now = new Date();
            final String generated = String.format("\nGenerated at %s%n%n", now.toString());
            docBuilder.append(generated);
            if (print) {
                System.out.print(generated);
            }
        } else if (qName.equals("person")) {
            // We have a new person, increase person number so we get them numbered in Markdown output
            personNumber++;
            final String number = String.format(" %d%n", personNumber);
            docBuilder.append(number);
            if (print) {
                System.out.print(number);
            }
        }
        // STUDENT TODO: push the element name (qName parameter) to stack to validate the correctness of the XML.
        // - When an XML element starts, the next ending element must be an element with the same name.
        // - So, push the start element to stack, and then in endElement, check that these match.
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        // STUDENT TODO: we have a XML element ending, so stack must have the corresponsing start element at the top!
        // If stack is empty, then this is an extra closing XML element, then you must
        //  throw a SAXException, with an error message.
        // If XML start element (from the top of the stack) and end element name (qName parameter) do not match, throw a
        // SAXException.
        // 
        // Before throwing the exceptions, if print is true, print error information
        // also, using System.err.println. After that, throw.
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        final String contents = new String(ch);
        docBuilder.append(contents);
        if (print) System.out.format("%s", contents);
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        // Empty
    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException {
        // Empty
    }

    @Override
    public void skippedEntity(String name) throws SAXException {
        // Empty
    }
    
    private String getMarkdownTextForXMLElement(final String element) {
        for (String [] item : elements) {
            if (item[0].equals(element)) {
                return item[1];
            }
        }
        return "? UNKNOWN XML ELEMENT ?";
    }


}
