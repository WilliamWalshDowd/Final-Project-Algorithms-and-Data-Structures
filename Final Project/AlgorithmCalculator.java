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
		System.out.println("Loading files...");
		loadFiles();
		System.out.println("All files loaded!");
	}

// ---------------------------------------------------------------------------------//
// Functions that perform the 3 required tasks
// ---------------------------------------------------------------------------------//
	public boolean fastestRouteBetween(int stopID1, int stopID2) {
		int pathMatrixStopIDByIndex1 = pathMatrixFromStopID(stopID1);
		int pathMatrixStopIDByIndex2 = pathMatrixFromStopID(stopID2);
		
		runAlgorithOnPathMatrix(pathMatrixStopIDByIndex1);
		
		float shortestPath;

		if (pathMatrixStopIDByIndex1 == -1 || pathMatrixStopIDByIndex2 == -1) {
			System.out.println(
					"There was a problem finding the fastest route, the IDs given dont have an associated stop.");
			return false;
		}

		shortestPath = pathMatrix[pathMatrixStopIDByIndex1][pathMatrixStopIDByIndex2];

		if (shortestPath >= 999999999) {
			System.out
					.println("There was a problem finding the fastest route, there isn't a route between the 2 stops.");
			return false;
		} else {
			System.out.println("Cost from stop " + stopID1 + " to " + stopID2 + " is " + shortestPath);
			return true;
		}
	}

	public boolean searchForStopByName(String name) {
		ArrayList<Stop> stopsMatchingSearch = new ArrayList<Stop>();

		// currently brute force version TODO make using ternary tree
		for (int i = 0; i < stops.size(); i++) {
			if (stops.get(i).stop_name.toLowerCase().contains(name.toLowerCase())) {
				stopsMatchingSearch.add(stops.get(i));
			}
		}

		for (int i = 0; i < stopsMatchingSearch.size(); i++) {
			Stop stop = stopsMatchingSearch.get(i);
			stop.printInfo();
		}

		if (stopsMatchingSearch.size() > 0) {
			return true;
		} else {
			return false;
		}

	}
	
	private boolean timeEquals(String arrivalTime, String searchTime) {
		String paddedArrivalTime = arrivalTime;
		String paddedSearchTime = searchTime;
		
		if(paddedArrivalTime.charAt(0) ==  ' ' || paddedArrivalTime.charAt(0) ==  '0') {
			paddedArrivalTime = paddedArrivalTime.substring(1);
		}
				
		if(paddedSearchTime.charAt(0) == ' ' || paddedSearchTime.charAt(0) == '0' ) {
			paddedSearchTime = paddedSearchTime.substring(1);
		}

		return paddedArrivalTime.equals(paddedSearchTime);
	}

	public boolean searchForTripByTime(String time) {
		ArrayList<Trip> tripsMatchingSearch = new ArrayList<Trip>();

		
		System.out.println("Search trips " + trips.size() + " trips for " + time);
		
		for (int i = 0; i < trips.size(); i++) {
			for (int j = 0; j < trips.get(i).nodes.size(); j++) {
				if ( timeEquals(trips.get(i).nodes.get(j).arrivalTime, time) ) {
					tripsMatchingSearch.add(trips.get(i));
					break;
				}
			}
		}
		System.out.println("-------- Trips matching the given time --------");
		for (int i = 0; i < tripsMatchingSearch.size(); i++) {
			Trip trip = tripsMatchingSearch.get(i);
			System.out.println("------------------------------------------------------------------");
			System.out.println("-------------------Trip ID: " + trip.tripID + "-------------------");
			for (int j = 0; j < trip.nodes.size(); j++) {
				TripNode tripnode = trip.nodes.get(j);
				System.out.print("		step in trip = " + (j + 1));
				tripnode.printInfo();
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
		
		int lines = 0;
		int previousStop = -1;
		int tripID = -1;

		while (true) {	
			lines ++;
			if(lines % 100000 == 0) System.out.print(".");
			
			String currentLine;
			try {
				currentLine = buffer.readLine();
			} catch (Exception e) {
				break;
			}

			if (currentLine == null) {
				break;
			}

			String[] splitCurrentLine = { "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1" };
			String[] splitCurrentLineValue = currentLine.split(",");

			for (int i = 0; i < splitCurrentLineValue.length; i++) {
				splitCurrentLine[i] = splitCurrentLineValue[i];
				try {
					if (i == 3 || i == 4 || i == 5 || i == 6 || i == 7) {
						Integer.parseInt(splitCurrentLine[i]);
					} else if (i == 8) {
						Float.parseFloat(splitCurrentLine[i]);
					}
				} catch (Exception e) {
					splitCurrentLine[i] = "-1";
				}
			}

			// makes the trips and nodes so they can be access much easier later.
			//
			int currentTripID = Integer.parseInt(splitCurrentLine[0]);
			if (trips.size() == 0) {
				trips.add(new Trip(currentTripID));
			} else if (trips.get(trips.size() - 1).tripID != currentTripID) {
				trips.add(new Trip(currentTripID));
			}

			String[] splitArrivalTime = splitCurrentLine[1].split(":");
			String[] splitDepartureTime = splitCurrentLine[2].split(":");
			if (splitArrivalTime[0].charAt(0) == ' ') {
				splitArrivalTime[0] = splitArrivalTime[0].substring(1);				
			}
			
			if (splitDepartureTime[0].charAt(0) == ' ') {
				splitDepartureTime[0] = splitDepartureTime[0].substring(1);
			}
						
			if (Integer.parseInt(splitArrivalTime[0]) < 24 && Integer.parseInt(splitArrivalTime[0]) >= 0
					&& Integer.parseInt(splitArrivalTime[1]) <= 59 && Integer.parseInt(splitArrivalTime[1]) >= 0
					&& Integer.parseInt(splitArrivalTime[2]) <= 59 && Integer.parseInt(splitArrivalTime[2]) >= 0) {
				if (Integer.parseInt(splitDepartureTime[0]) < 24 && Integer.parseInt(splitDepartureTime[0]) >= 0
						&& Integer.parseInt(splitDepartureTime[1]) <= 59 && Integer.parseInt(splitDepartureTime[1]) >= 0
						&& Integer.parseInt(splitDepartureTime[2]) <= 59 && Integer.parseInt(splitDepartureTime[2]) >= 0) {
					trips.get(trips.size() - 1).nodes
							.add(new TripNode(splitCurrentLine[1], splitCurrentLine[2],
									Integer.parseInt(splitCurrentLine[3]), Integer.parseInt(splitCurrentLine[4]),
									Integer.parseInt(splitCurrentLine[5]), Integer.parseInt(splitCurrentLine[6]),
									Integer.parseInt(splitCurrentLine[7]), Float.parseFloat(splitCurrentLine[8])));
				} else {
					// System.out.println("Departure failed splitArrival " + splitArrivalTime[0] + "  splitDepartureTime " + splitDepartureTime[0]);
				}
			} else {
				// System.out.println("Arrival failed splitArrival " + splitArrivalTime[0] + "  splitDepartureTime " + splitDepartureTime[0]);
			}

			// adds more data to the path matrix for finding the optimal path between stops
			//

			if (tripID == currentTripID) {
				pathMatrix[pathMatrixFromStopID(previousStop)][pathMatrixFromStopID(
						Integer.parseInt(splitCurrentLine[3]))] = 1;
			}

			previousStop = Integer.parseInt(splitCurrentLine[3]);
			tripID = currentTripID;

		}
		
		System.out.println("!");
		System.out.println("Completed parsing " + lines + " rows, with " + trips.size() +  " valid trips found.");
		System.out.println("All stop files added (3/3)");
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

			if (currentLine == null) {
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
				} catch (Exception e) {
					splitCurrentLine[i] = "-1";
				}
			}

			stops.add(new Stop(Integer.parseInt(splitCurrentLine[0]), Integer.parseInt(splitCurrentLine[1]),
					splitCurrentLine[2], splitCurrentLine[3], Float.parseFloat(splitCurrentLine[4]),
					Float.parseFloat(splitCurrentLine[5]), splitCurrentLine[6], splitCurrentLine[7],
					splitCurrentLine[8], splitCurrentLine[9], stops.size()));
		}

		System.out.println("All stops added (1/3)");
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

			if (currentLine == null) {
				break;
			}

			String[] splitCurrentLine = { "-1", "-1", "-1", "-1" };
			String[] splitCurrentLineValue = currentLine.split(",");
			for (int i = 0; i < splitCurrentLineValue.length; i++) {
				splitCurrentLine[i] = splitCurrentLineValue[i];
			}
			if (Integer.parseInt(splitCurrentLine[2]) == 0) {
				pathMatrix[pathMatrixFromStopID(Integer.parseInt(splitCurrentLine[0]))][pathMatrixFromStopID(
						Integer.parseInt(splitCurrentLine[1]))] = 2;
			} else if (Integer.parseInt(splitCurrentLine[2]) == 2) {
				pathMatrix[pathMatrixFromStopID(Integer.parseInt(splitCurrentLine[0]))][pathMatrixFromStopID(
						Integer.parseInt(splitCurrentLine[1]))] = Integer.parseInt(splitCurrentLine[3]) / 100;

			}

		}
		System.out.println("All transfers added (2/3)");
	}

	private static int pathMatrixFromStopID(int stopID) {
		for (int i = 0; i < stops.size(); i++) {
			if (stops.get(i).stop_id == stopID) {
				return stops.get(i).pathMatrixIndex;
			}
		}
		return -1;
	}

// --------------------------------------------------------------------------------------------------------------------------------//
// Functions to update the data loaded from the files so that it can be used in the required functions..
// --------------------------------------------------------------------------------------------------------------------------------//
	// ----------------------------------------------------------------------------------------------------------------------------//
	// runs an algorithm to find the fastest routes for every possible path and then
	// updates the path matrix with that info.
	// ----------------------------------------------------------------------------------------------------------------------------//
	private static void runAlgorithOnPathMatrix(int startNode) {
		dijkstra(startNode);
	}

	static float minDistance(float distanceMatrix[], Boolean sptSet[]) {
		float min = 999999999, minIndex = -1;
		for (int v = 0; v < pathMatrix.length; v++)
			if (sptSet[v] == false && distanceMatrix[v] <= min) {
				min = distanceMatrix[v];
				minIndex = v;
			}
		return minIndex;
	}

	static void dijkstra(int src) {
		float dist[] = new float[pathMatrix.length];

		Boolean sptSet[] = new Boolean[pathMatrix.length];

		for (int i = 0; i < pathMatrix.length; i++) {
			dist[i] = 999999999;
			sptSet[i] = false;
		}

		dist[src] = 0;

		for (int count = 0; count < pathMatrix.length - 1; count++) {
			int u = (int) (minDistance(dist, sptSet));
			sptSet[u] = true;
			for (int v = 0; v < pathMatrix.length; v++)
				if (!sptSet[v] && pathMatrix[u][v] != 0 && dist[u] != 999999999 && dist[u] + pathMatrix[u][v] < dist[v])
					dist[v] = dist[u] + pathMatrix[u][v];
		}

		pathMatrix[src] = dist;
	}

	private static void populatePathMatrix() {
		pathMatrix = new float[stops.size()][stops.size()];

		for (int i = 0; i < stops.size(); i++) {
			for (int j = 0; j < stops.size(); j++) {
				if (i == j) {
					pathMatrix[i][j] = 0;
				} else {
					pathMatrix[i][j] = 999999999;
				}
			}
		}
	}

}
