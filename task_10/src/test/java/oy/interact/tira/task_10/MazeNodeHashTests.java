package oy.interact.tira.task_10;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;

@DisplayName("Testing the MazeNode.hashCode")
@EnabledIf("checkIfImplemented")
public class MazeNodeHashTests {

	private static final short MAX_COORDINATE_VALUE = 3_000;

	static boolean checkIfImplemented() {
		final MazeNode node = new MazeNode(42, 42);
		return node.hashCode() != 0;
	}

	@Test
	void testHashTimeEfficiencyWithMap() {

		Map<Integer, MazeNode> map = new HashMap<>();

		try {
			System.out.format(
				"Starting to add %,d MazeNodes to HashMap using hashing%n", 
				(int)Math.pow(MAX_COORDINATE_VALUE, 2)
			);
			long start = System.currentTimeMillis();
			int count = 0;
			for (short xCoordinate = 0; xCoordinate < MAX_COORDINATE_VALUE; xCoordinate++) {
				for (short yCoordinate = 0; yCoordinate < MAX_COORDINATE_VALUE; yCoordinate++) {
					final MazeNode node = new MazeNode(xCoordinate, yCoordinate);
					map.put(node.hashCode(), node);
					count++;
					assertEquals(count, map.size(), "MazeNode was not added to map due to duplicate hashes");
				}
			}
			System.out.format(
				"Duration of adding %,d MazeNodes to Map: %,d ms%n", 
				count,
				System.currentTimeMillis() - start
			);
			
		} catch (OutOfMemoryError e) {
			fail("Test failed because OutOfMemoryError. Make MAX_COORDINATE_VALUE smaller");
		} catch (Exception e) {
			fail("Test failed because " + e.getMessage());
		}
	}
}
