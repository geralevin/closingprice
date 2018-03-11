package com.gera.ice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

/**
 * Closing price processor. Reads file of all the prices and applies consumer
 */
public class ClosingPriceProcessor {

	private final Path path;

	/**
	 * @param path
	 *            to the cusip price file
	 */
	private ClosingPriceProcessor(Path path) {
		this.path = path;
	}

	/**
	 * @param path
	 *            to the CUSIP price file
	 * @return closing price processor
	 */
	public static ClosingPriceProcessor of(Path path) {
		return new ClosingPriceProcessor(path);
	}

	/**
	 * Processes the file
	 *
	 * @param cusipPriceConsumer
	 *            - CUSIP/price consumer
	 * @throws IOException
	 *             - exception if the is IO problem for the file
	 */
	public void process(BiConsumer<String, Double> cusipPriceConsumer) throws IOException {
		// do not load all of the file in the memory
		try (Stream<String> stream = Files.lines(path)) {

			String cusip = null;
			String price = null;
			for (String line : (Iterable<String>) stream::iterator) {
				if (line.length() == 8 && !StringUtils.isNumeric(line)) {
					if (price != null) {
						cusipPriceConsumer.accept(cusip, Double.parseDouble(price));
					}
					cusip = line;
				} else {
					price = line;
				}
			}

			// consume the last cusip
			if (cusip != null && price != null) {
				cusipPriceConsumer.accept(cusip, Double.parseDouble(price));
			}
		}
	}
}
