# Problem 1 (ICE) CUSIP Closing Price. #

**Task**  
You are given a file formatted like this:

CUSIP  
Price  
Price  
Price  
…  
CUSIP  
Price  
Price  
CUSIP  
Price  
Price  
Price  
…  
Price  
CUSIP  
Price  
…  

Think of it as a file of price ticks for a set of bonds identified by their CUSIPs.  
You can assume a CUSIP is just an 8-character alphanumeric string.
Each CUSIP may have any number of prices (e.g., 95.752, 101.255) following it in sequence, one per line.
The prices can be considered to be ordered by time in ascending order, earliest to latest.

Write me a Java program that will print the closing (or latest) price for each CUSIP in the file.
DO NOT assume the entire file can fit in memory!

**Example:**  
java -jar target\closingprice-1.0-SNAPSHOT-jar-with-dependencies.jar 
E:\Home\closingprice\src\test\resources\cusip-price-input.txt