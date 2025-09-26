package oy.interact.tira.task_05;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import org.xml.sax.SAXException;

import oy.interact.tira.factories.StackFactory;
import oy.interact.tira.student.PersonXMLToMarkdownHandler;
import oy.interact.tira.util.CustomXMLParser;

@DisplayName("Tests XML parsing correctness with StackImplementation")
@EnabledIf("checkIfImplemented")
public class XMLParserTests {

	static boolean checkIfImplemented() {
		return StackFactory.createStringStack() != null;
	}

	@Test
	void testCorrectXMLFile() {
		// Nothing is wrong in input, so do not print out details and get the processed
		// output in the end.
		// If code fails, test fails and nothing should be printed out.
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
		assertDoesNotThrow(() -> parser.parse(testXML, checker), "Handling correct XML must not throw");
		System.out.println(checker.getProcessedOutput());
	}

	@Test
	void testInorrectXMLFileEndElementMissing() {
		// The </document> is missing at the end
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
			""";

		CustomXMLParser parser = new CustomXMLParser();
		PersonXMLToMarkdownHandler checker = new PersonXMLToMarkdownHandler(true);
		assertThrows(SAXException.class, () -> parser.parse(testXML, checker), "Handling incorrect XML must throw");
	}

	@Test
	void testInorrectXMLFileStartElementMissing() {
		// The <document> is missing from the beginning
		final String testXML = """
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
		PersonXMLToMarkdownHandler checker = new PersonXMLToMarkdownHandler(true);
		assertThrows(SAXException.class, () -> parser.parse(testXML, checker), "Handling incorrect XML must throw");
	}

	@Test
	void testInorrectXMLFileWrongEndElement() {
		// Error is on fourth line where element starts with <description> but ends with
		// </subject>.
		final String testXML = """
			<document>
				<heading>
					<subject>Oulu Customers</subject>
					<description>Customers from Oulu city area</subject>
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
		PersonXMLToMarkdownHandler checker = new PersonXMLToMarkdownHandler(true);
		assertThrows(SAXException.class, () -> parser.parse(testXML, checker), "Handling incorrect XML must throw");
	}

	@Test
	void testInorrectXMLFileEndElementMissingFromMiddle() {
		// first <person> is missing the </person> at the end of properties
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
		PersonXMLToMarkdownHandler checker = new PersonXMLToMarkdownHandler(true);
		assertThrows(SAXException.class, () -> parser.parse(testXML, checker), "Handling incorrect XML must throw");
	}

	@Test
	void testDuplicateFirstElementStart() {
		// document starts with two document elements and ending with only one
		final String testXML = """
				<document>
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
		PersonXMLToMarkdownHandler checker = new PersonXMLToMarkdownHandler(true);
		assertThrows(SAXException.class, () -> parser.parse(testXML, checker), "Handling incorrect XML must throw");
	}

	@Test
	void testDuplicateMisspelledEndElement() {
		// Document starts with one document element but has tocument element at the end.
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
				</tocument>
				""";

		CustomXMLParser parser = new CustomXMLParser();
		PersonXMLToMarkdownHandler checker = new PersonXMLToMarkdownHandler(true);
		assertThrows(SAXException.class, () -> parser.parse(testXML, checker), "Handling incorrect XML must throw");
	}

	@Test
	void testFirstPersonStartElementMissing() {
		// first person's start element is missing
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
						<firstname>Jouni</firstname>
						<middlename>Hector</middlename>
						<lastname>Lappalainen</lastname>
						<age>41</age>
					</person>
				</persons>
			</document>
			""";

		CustomXMLParser parser = new CustomXMLParser();
		PersonXMLToMarkdownHandler checker = new PersonXMLToMarkdownHandler(true);
		assertThrows(SAXException.class, () -> parser.parse(testXML, checker), "Handling incorrect XML must throw");
	}
}
