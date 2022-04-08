import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class AlgorithmCalculator {
	static FileReader reader;
	static BufferedReader buffer;

	static ArrayList<Stop> stops = new ArrayList<Stop>();
	static ArrayList<Trip> trips = new ArrayList<Trip>();

	static float[][] pathMatrix;

	AlgorithmCalculator() {
		loadFiles();
		runAlgorithOnPathMatrix();
	}

// ---------------------------------------------------------------------------------//
// Functions that perform the 3 required tasks
// ---------------------------------------------------------------------------------//
	public void fastestRouteBetween(int stopID1, int stopID2) {
		float shortestPath = pathMatrix[stopID1][stopID2];

		System.out.println("Cost from stop" + stopID1 + " to " + stopID2 + "is" + shortestPath);
	}

	public boolean searchForStopByName(String name) {
		ArrayList<Stop> tripsMatchingSearch = new ArrayList<Stop>();

		// currently brute force version TODO make using ternary tree
		for (int i = 0; i < stops.size(); i++) {
			if (stops.get(i).stop_name.contains(name)) {
				tripsMatchingSearch.add(stops.get(i));
			}
		}

		for (int i = 0; i < tripsMatchingSearch.size(); i++) {
			Stop stop = tripsMatchingSearch.get(i);
			stop.printInfo();
		}

		if (tripsMatchingSearch.size() > 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean searchForTripByTime(String time) {
		ArrayList<Trip> tripsMatchingSearch = new ArrayList<Trip>();

		for (int i = 0; i < trips.size(); i++) {
			for (int j = 0; j < trips.get(i).nodes.size(); j++) {
				if (trips.get(i).nodes.get(j).arrivalTime == time) {
					tripsMatchingSearch.add(trips.get(i));
					break;
				}
			}
		}
		System.out.println("-------- Trips matching the given time --------");
		for (int i = 0; i < tripsMatchingSearch.size(); i++) {
			Trip trip = tripsMatchingSearch.get(i);
			System.out.println("Trip ID: " + trip.tripID);
			for (int j = 0; j < trip.nodes.size(); j++) {
				tripNode tripnode = trip.nodes.get(j);
				System.out.println(j + 1 + " Step in trip, " + "Arrival Time: " + tripnode.arrivalTime
						+ ", Departure Time: " + tripnode.departureTime);
			}
		}

		if (tripsMatchingSearch.size() > 0) {
			return true;
		} else {
			return false;
		}

	}

// ---------------------------------------------------------------------------------//
// Functions to analyse and load in data from data files.
// ---------------------------------------------------------------------------------//
	private static void loadFiles() {
		loadStopsFile();
		populatePathMatrix();
		loadTransfersFile();
		loadStopTimesFile();
	}

	// ---------------------------------------------------------------------------------//
	// loads data into the trips array list and the path matrix from the stop times
	// file.
	// ---------------------------------------------------------------------------------//
	private static void loadStopTimesFile() {
		try {
			reader = new FileReader("stop_times.txt");
			buffer = new BufferedReader(reader);
			String labels = buffer.readLine();
		} catch (Exception e) {
			System.out.println("ERROR FILE NAMED transfers.txt NOT FOUND");
		}

		while (true) {
			String currentLine;
			try {
				currentLine = buffer.readLine();
			} catch (Exception e) {
				break;
			}
			
			if (currentLine == "") {
				break;
			}

			String[] slitCurrentLine = currentLine.split(",");

			// makes the trips and nodes so they can be access much easier later.
			//
			if (trips.get(trips.size() - 1).tripID != Integer.parseInt(slitCurrentLine[0])) {
				trips.add(new Trip(Integer.parseInt(slitCurrentLine[0])));
			}
			String[] splitArrivalTime = slitCurrentLine[1].split(":");
			String[] splitDepartureTime = slitCurrentLine[1].split(":");
			if (Integer.parseInt(splitArrivalTime[0]) < 24 && Integer.parseInt(splitArrivalTime[0]) >= 0
					&& Integer.parseInt(splitArrivalTime[1]) <= 59 && Integer.parseInt(splitArrivalTime[1]) >= 0
					&& Integer.parseInt(splitArrivalTime[2]) <= 59 && Integer.parseInt(splitArrivalTime[2]) >= 0) {
				if (Integer.parseInt(splitDepartureTime[0]) < 24 && Integer.parseInt(splitDepartureTime[0]) >= 0
						&& Integer.parseInt(splitDepartureTime[1]) <= 59 && Integer.parseInt(splitDepartureTime[1]) >= 0
						&& Integer.parseInt(splitDepartureTime[2]) <= 59
						&& Integer.parseInt(splitDepartureTime[2]) >= 0) {
					trips.get(trips.size() - 1).nodes.add(new tripNode(slitCurrentLine[1], slitCurrentLine[2],
							Integer.parseInt(slitCurrentLine[3]), Integer.parseInt(slitCurrentLine[4]),
							Integer.parseInt(slitCurrentLine[5]), Integer.parseInt(slitCurrentLine[6]),
							Integer.parseInt(slitCurrentLine[7]), Float.parseFloat(slitCurrentLine[8])));
				}
			}

			// adds more data to the path matrix for finding the optimal path between stops
			//
			int previousStop = -1;
			int tripID = -1;

			if (tripID == Integer.parseInt(slitCurrentLine[0])) {
				pathMatrix[previousStop][Integer.parseInt(slitCurrentLine[3])] = 1;
			}

			previousStop = Integer.parseInt(slitCurrentLine[3]);
			tripID = Integer.parseInt(slitCurrentLine[0]);

		}
	}

	// ---------------------------------------------------------------------------------//
	// loads data into the stops array list from the stops file.
	// ---------------------------------------------------------------------------------//
	private static void loadStopsFile() {
		try {
			reader = new FileReader("stops.txt");
			buffer = new BufferedReader(reader);
			String labels = buffer.readLine();
		} catch (Exception e) {
			System.out.println("ERROR FILE NAMED stops.txt NOT FOUND");
		}

		while (true) {
			String currentLine;
			try {
				currentLine = buffer.readLine();
			} catch (Exception e) {
				break;
			}
			
			if (currentLine == "") {
				break;
			}

			String[] splitCurrentLine = { "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", };
			String[] splitCurrentLineValue = currentLine.split(",");
			
			for (int i = 0; i < splitCurrentLineValue.length; i++) {
				splitCurrentLine[i] = splitCurrentLineValue[i];
				try {
					if (i == 0 || i == 1) {
					Integer.parseInt(splitCurrentLine[i]);
					}
				} catch(Exception e) {
					splitCurrentLine[i] = "-1";
				}
			}

			System.out.println(splitCurrentLine[0] + "," + splitCurrentLine[1] + "," + splitCurrentLine[2] + ","
					+ splitCurrentLine[3] + "," + splitCurrentLine[4] + "," + splitCurrentLine[5] + ","
					+ splitCurrentLine[6] + "," + splitCurrentLine[7] + "," + splitCurrentLine[8] + ","
					+ splitCurrentLine[9]);

			stops.add(new Stop(Integer.parseInt(splitCurrentLine[0]), Integer.parseInt(splitCurrentLine[1]),
					splitCurrentLine[2], splitCurrentLine[3], Float.parseFloat(splitCurrentLine[4]),
					Float.parseFloat(splitCurrentLine[5]), splitCurrentLine[6], splitCurrentLine[7],
					splitCurrentLine[8], splitCurrentLine[9]));
			System.out.println("ID: " + Integer.parseInt(splitCurrentLine[0]) + " : Stop added to list");
		}

		System.out.println("All stops added");
	}

	// ---------------------------------------------------------------------------------//
	// loads data into the path matrix from the transfers file.
	// ---------------------------------------------------------------------------------//
	private static void loadTransfersFile() {
		try {
			reader = new FileReader("transfers.txt");
			buffer = new BufferedReader(reader);
			String labels = buffer.readLine();
		} catch (Exception e) {
			System.out.println("ERROR FILE NAMED transfers.txt NOT FOUND");
		}
		
		while (true) {
			String currentLine;
			try {
				currentLine = buffer.readLine();
			} catch (Exception e) {
				break;
			}
			
			if (currentLine == "") {
				break;
			}

			String[] splitCurrentLine = { "-1", "-1", "-1", "-1" };
			String[] splitCurrentLineValue = currentLine.split(",");
			for (int i = 0; i < splitCurrentLineValue.length; i++) {
				splitCurrentLine[i] = splitCurrentLineValue[i];
			}
			if (Integer.parseInt(splitCurrentLine[2]) == 0) {
				pathMatrix[Integer.parseInt(splitCurrentLine[0])][Integer.parseInt(splitCurrentLine[1])] = 2;
			} else if (Integer.parseInt(splitCurrentLine[0]) == 2) {
				pathMatrix[Integer.parseInt(splitCurrentLine[0])][Integer.parseInt(splitCurrentLine[1])] = Integer
						.parseInt(splitCurrentLine[3]) / 100;
			}

		}

	}

// --------------------------------------------------------------------------------------------------------------------------------//
// Functions to update the data loaded from the files so that it can be used in the required functions..
// --------------------------------------------------------------------------------------------------------------------------------//
	// ----------------------------------------------------------------------------------------------------------------------------//
	// runs an algorithm to find the fastest routes for every possible path and then
	// updates the path matrix with that info.
	// ----------------------------------------------------------------------------------------------------------------------------//
	private static void runAlgorithOnPathMatrix() {
		for (int i = 0; i < stops.size(); i++) {
			for (int j = 0; j < stops.size(); j++) {
				for (int k = 0; k < stops.size(); k++) {
					if (pathMatrix[i][k] + pathMatrix[k][j] < pathMatrix[i][j]) {
						pathMatrix[i][j] = pathMatrix[i][k] + pathMatrix[k][j];
					}
				}
			}
		}
	}

	private static void populatePathMatrix() {
		pathMatrix = new float[stops.size()][stops.size()];

		for (int i = 0; i < stops.size(); i++) {
			for (int j = 0; j < stops.size(); j++) {
				pathMatrix[i][j] = 999999999;
			}
		}
	}

}
