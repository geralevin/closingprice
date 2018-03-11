package com.gera.ice;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

/**
 * Tests
 */
public class ClosingPriceProcessorTest {

	/**
	 * Test processing of the file
	 */
	@Test
	public void processingTest() {
		Map<String, Double> cusipAndPrice = new HashMap<>();
		try {
			ClosingPriceProcessor
					.of(Paths.get(getClass().getClassLoader().getResource("cusip-price-input.txt").toURI()))
					.process((cusip, cusipPrice) -> cusipAndPrice.putIfAbsent(cusip, cusipPrice));

			Map<String, Double> expectedResults = expectedResults(
					Paths.get(getClass().getClassLoader().getResource("cusip-price-output.txt").toURI()));
			assertThat(cusipAndPrice.entrySet(), equalTo(expectedResults.entrySet()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets expected results from the test 'output' file
	 *
	 * @param path
	 *            - resource file path
	 * @return map of CUSIP/price
	 * @throws IOException
	 */
	private static Map<String, Double> expectedResults(Path path) throws IOException {
		Map<String, Double> expectedPrices = new ConcurrentHashMap<>();
		Files.lines(path).parallel().map(s -> s.split(" "))
				.forEach(parts -> expectedPrices.putIfAbsent(parts[0], Double.parseDouble(parts[1])));
		return expectedPrices;
	}
}
