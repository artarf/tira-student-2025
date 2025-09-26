package oy.interact.tira.util;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.DefaultHandler;

public class CustomXMLParser {
    
    private enum State {
        START_DOCUMENT,
        HANDLE_ELEMENT_STARTS,      // <THIS_AREA>
        HANDLE_ELEMENT_CONTENT,     // <element>THIS AREA HERE</element>
        HANDLE_ELEMENT_ENDS,        // </THIS_AREA>
    }
    private State state = State.START_DOCUMENT;

    public void parse(final String xmlString, final DefaultHandler handler) throws SAXException {
        
        StringBuilder builder = new StringBuilder();
        
        state = State.START_DOCUMENT;
        handler.startDocument();

        for (int index = 0; index < xmlString.length(); index++) {
            final char character = xmlString.charAt(index);
            switch (character) {
                case '<':
                    if (state == State.HANDLE_ELEMENT_CONTENT) {
                        final String name = builder.toString().trim();
                        if (name.length() > 0) {
                            handler.characters(name.toCharArray(), 0, name.length());
                        }
                    }
                    state = State.HANDLE_ELEMENT_STARTS;
                    builder.setLength(0);
                    break;
                case '/':
                    if (state == State.HANDLE_ELEMENT_STARTS) {
                        state = State.HANDLE_ELEMENT_ENDS;
                    }
                    break;
                case '>':
                    switch (state) {
                        case HANDLE_ELEMENT_STARTS: {
                            final String name = builder.toString();
                            handler.startElement("", "", name, new AttributesImpl());
                            state = State.HANDLE_ELEMENT_CONTENT;                            
                            builder.setLength(0);
                            break;
                        }
                        case HANDLE_ELEMENT_ENDS: {
                            final String name = builder.toString();
                            handler.endElement("", "", name);
                            builder.setLength(0);
                            state = State.HANDLE_ELEMENT_CONTENT;
                            break;
                        }
                        default:
                            break;
                        
                    }
                    break;

                default: // processing chars in element name or bw element tags
                    if (state == State.HANDLE_ELEMENT_STARTS || 
                        state == State.HANDLE_ELEMENT_CONTENT ||
                        state == State.HANDLE_ELEMENT_ENDS
                    ) {
                        builder.append(character);
                    }
            }

        }
        handler.endDocument();
    }
}
