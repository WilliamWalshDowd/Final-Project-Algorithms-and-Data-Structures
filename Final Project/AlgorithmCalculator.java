import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class AlgorithmCalculator {
	static FileReader reader;
	static BufferedReader buffer;
	
	static ArrayList<Stop> stops = new ArrayList<Stop>();
	
	AlgorithmCalculator() {
		loadFiles();
	}
	
	// Functions that perform the 3 required tasks
	//
	public static String fastestRouteBetween(String stop1, String stop2) {
		//TODO write function
	}
	
	public static String searchForStopByName(String name) {
		//TODO write function
	}
	
	public static String searchForStopByTime(String time) {
		//TODO write function
	}
	
	
	// Functions to analyse and load in data from .txt files
	//
	private static void loadFiles() {
		loadStopsFile();
	}
	
	private static void loadStopTimesFile() {
		
	}
	
	private static void loadStopsFile() {
		try {
			reader = new FileReader("stops.txt");
			buffer = new BufferedReader(reader);
		} catch(Exception e) {
			System.out.println("ERROR FILE NAMED stops.txt NOT FOUND");
		}
		
		while(true) {
			String currentLine;
			try {
				currentLine = buffer.readLine();
			} catch(Exception e) {
				break;
			}
			
			String[] slitCurrentLine = currentLine.split(",");
			stops.add(new Stop(Integer.parseInt(slitCurrentLine[0]), Integer.parseInt(slitCurrentLine[1]), slitCurrentLine[2], slitCurrentLine[3], Float.parseFloat(slitCurrentLine[4]), Float.parseFloat(slitCurrentLine[5]), slitCurrentLine[6], slitCurrentLine[7], slitCurrentLine[8], slitCurrentLine[9]));
			System.out.println("ID: " + Integer.parseInt(slitCurrentLine[0]) + " : Stop added to list");
		}
		
		System.out.println("All stops added");
		
	}

	private static void loadTransfersFile() {
	
	}

}
