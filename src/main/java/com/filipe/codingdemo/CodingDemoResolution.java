package com.filipe.codingdemo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

/**
 * CodingDemoResolution
 * 
 * Name: filipe reis
 * Date: 2021-05-19
 */
@Service
public class CodingDemoResolution {

	/**
	 * File data pattern.
	 */
	private static final String FILE_PATTER = "(^.*?),.*\\|(.*)";
	/**
	 * Regex to get only numeric characters.
	 */
	private static final String NUMERIC_ONLY_PATTER = "[0-9.].*";
	/**
	 * Message format.
	 */
	private static final String PRINT_PATTER = "{0}: Average: {1} Max: {2} Min: {3}";
	
	/**
	 * 
	 */
	public void start() {
		String fileName = "files/Coding Demo Data.txt";
		ClassLoader classLoader = getClass().getClassLoader();
		List<Host> hosts = new ArrayList<>();
		
		try (InputStream inputStream = classLoader.getResourceAsStream(fileName);
				Scanner scanner = new Scanner(inputStream);) {
			while (scanner.hasNext()) {
				Host host = getHosts(scanner.next());
				resolve(host);
				hosts.add(host);
			}
			
			prepareResult(hosts);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets hosts and values.
	 * 
	 * @param data
	 * @return
	 */
	private Host getHosts(String data) {
		Host host = null;
		Pattern pattern = Pattern.compile(FILE_PATTER);
		Matcher matcher = pattern.matcher(data);
		Pattern numericPattern = Pattern.compile(NUMERIC_ONLY_PATTER);

		if (matcher.matches()) {
			String hostName = matcher.group(1);
			String[] hostValues = matcher.group(2).split(",");
			// Streams through array and gets only numeric values
			List<Double> hostDouble = Arrays.asList(hostValues).stream().map(numericPattern::matcher)
					.filter(Matcher::find).map(m -> Double.valueOf(m.group())).collect(Collectors.toList());
			
			host = new Host(hostName, hostDouble);
		}

		return host;
	}
	
	/**
	 * Solve the problem, get min, max and average value.
	 * 
	 * @param host
	 */
	private void resolve(Host host) {
		List<Double> values = host.getValues();
		values.sort((d1, d2) -> Double.compare(d1, d2));
		host.setMin(values.get(0));
		host.setMax(values.get(values.size() -1));
		host.setAverage(values.stream().mapToDouble(Number::doubleValue).average().getAsDouble());
	}
	
	/**
	 * Sort and print the result.
	 * 
	 * @param hosts
	 */
	private void prepareResult(List<Host> hosts) {
		System.out.println("\n##################################################");
		System.out.println("########### CODING DEMO RESOLUTION ###############");
		System.out.println("##################################################");
		hosts.stream().sorted((h1, h2) -> h2.average.compareTo(h1.average))
				.forEach(h -> printResults(h.name, h.average, h.max, h.min));
		
	}
	
	/**
	 * Print message. 
	 * 
	 * @param host
	 * @param average
	 * @param max
	 * @param min
	 */
	private void printResults(String host, Double average, Double max, Double min) {
		MessageFormat message = new MessageFormat(PRINT_PATTER, Locale.US);
		System.out.println(message.format(
				new String[] { host, 
						BigDecimal.valueOf(average).setScale(1, RoundingMode.HALF_UP).toString(),
						max.toString(), min.toString() }));
	}
	
	/**
	 * Class Host
	 *
	 */
	private class Host {
		private String name;
		private List<Double> values;
		private Double average;
		private Double max;
		private Double min;
		
		public Host(String name, List<Double> values) {
			this.name = name;
			this.values = values;
		}
		
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @return the values
		 */
		public List<Double> getValues() {
			return values;
		}

		/**
		 * @return the average
		 */
		public double getAverage() {
			return average;
		}

		/**
		 * @param average the average to set
		 */
		public void setAverage(Double average) {
			this.average = average;
		}

		/**
		 * @return the max
		 */
		public Double getMax() {
			return max;
		}

		/**
		 * @param max the max to set
		 */
		public void setMax(Double max) {
			this.max = max;
		}

		/**
		 * @return the min
		 */
		public Double getMin() {
			return min;
		}

		/**
		 * @param min the min to set
		 */
		public void setMin(Double min) {
			this.min = min;
		}
		
	}

}
