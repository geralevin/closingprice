package com.gera.ice;

import java.nio.file.Paths;

/**
 * CUSIP/closing price utility.
 */
public class Application {
	public static void main(String... args) {
		if (args.length != 1) {
			System.out.println("CUSIP/Price input file is not given.");
			return;
		}

		try {
			ClosingPriceProcessor.of(Paths.get(args[0]))
					.process((cusip, price) -> System.out.println(cusip + " " + price));
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}
