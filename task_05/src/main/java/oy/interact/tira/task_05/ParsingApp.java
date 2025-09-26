package oy.interact.tira.task_05;

import org.xml.sax.SAXException;

import oy.interact.tira.student.PersonXMLToMarkdownHandler;
import oy.interact.tira.util.CustomXMLParser;

public class ParsingApp {

	public static void main(String[] args) {
		try {
			processCorrectXML();
			processInvalidXML();
		} catch (SAXException e) {
			System.err.format("ERROR: %s%n", e.getMessage());
		}
	}

	private static void processCorrectXML() throws SAXException {
		final String testXML = """
				<document>
					<heading>
						<subject>Oulu Customers</subject>
						<description>Customers from Oulu city area</description>
						<generated>2025-06-18 13.39 EET</generated>
					</heading>
					<persons>
						<person>
							<firstname>Antti</firstname>
							<middlename>Odysseu</middlename>
							<lastname>Juustila</lastname>
							<age>42</age>
						</person>
						<person>
							<firstname>Jouni</firstname>
							<middlename>Hector</middlename>
							<lastname>Lappalainen</lastname>
							<age>41</age>
						</person>
					</persons>
				</document>
				""";

		CustomXMLParser parser = new CustomXMLParser();
		PersonXMLToMarkdownHandler checker = new PersonXMLToMarkdownHandler(false);
		parser.parse(testXML, checker);
		System.out.println(checker.getProcessedOutput());
	}

	private static void processInvalidXML() throws SAXException {
		final String testXML = """
				<document>
					<heading>
						<subject>Oulu Customers</subject>
						<description>Customers from Oulu city area</description>
						<generated>2025-06-18 13.39 EET</generated>
					</heading>
					<persons>
							<firstname>Antti</firstname>
							<middlename>Odysseu</middlename>
							<lastname>Juustila</lastname>
							<age>42</age>
						</person>
						<person>
							<firstname>Jouni</firstname>
							<middlename>Hector</middlename>
							<lastname>Lappalainen</lastname>
							<age>41</age>
						</person>
					</persons>
				""";

		CustomXMLParser parser = new CustomXMLParser();
		PersonXMLToMarkdownHandler checker = new PersonXMLToMarkdownHandler(false);
		parser.parse(testXML, checker);
		System.out.println(checker.getProcessedOutput());
	}

}
