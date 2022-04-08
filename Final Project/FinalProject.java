import java.util.Scanner;

public class FinalProject {

	private static AlgorithmCalculator calculator;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		calculator = new AlgorithmCalculator();
		boolean exit = false;
		String userInput;

		while (!exit) {
			printOutOptionList();
			userInput = scanner.next();

			while (userInput != "1" && userInput != "2" && userInput != "3") {
				System.out.println(
						"You typed an input that wasn't recognised, please type 1 2 or 3 based on the above list.");
				userInput = scanner.next();
			}

			asnwerInput(userInput, scanner);

		}

	}

	private static void printOutOptionList() {
		System.out.println("----------------------- What would you like to do? -----------------------");
		System.out.println("|                                                                        |");
		System.out.println("|      Type 1 if you want to find the shorest path between 2 stops       |");
		System.out.println("|                                                                        |");
		System.out.println("|             Type 2 if you want to find a stop by its name              |");
		System.out.println("|                                                                        |");
		System.out.println("|          Type 3 if you want to find a trip by its arrival time         |");
		System.out.println("|                                                                        |");
		System.out.println("--------------------------------------------------------------------------");
		System.out.println("> ");
	}

	private static void asnwerInput(String input, Scanner scanner) {
		if (input == "1") {
			runShortestPathInput(scanner);
		} else if (input == "2") {
			runStopByNameInput(scanner);
		} else {
			runTripByArrivalTimeInput(scanner);
		}
	}

	private static void runStopByNameInput(Scanner scanner) {
		String userInput;

		System.out.println("You have chosen, find a stop by its name.");
		System.out.println("Please type the name of the stop or parts of the stop's name");
		System.out.println("> ");

		userInput = scanner.next();
		while (userInput == null || userInput == "INVALID" || userInput == ""
				|| !calculator.searchForStopByName(userInput)) {
			System.out.println("Invalid input or no stops found, please type a name or part of a name.");
			System.out.println("> ");
			userInput = scanner.next();
		}

	}

	private static void runShortestPathInput(Scanner scanner) {
		String userInput;

		System.out.println("You have chosen, find the shorest path between 2 stops.");
		System.out.println("Please type your desired Starting stop and Ending stop in the form: ");
		System.out.println("               start stop ID/end stop ID");
		System.out.println("> ");

		userInput = scanner.next();
		int[] stops = new int[2];
		try {
			String[] userInputSplit = userInput.split("/");
			stops[0] = Integer.parseInt(userInputSplit[0]);
			stops[0] = Integer.parseInt(userInputSplit[0]);
		} catch (Exception e) {
			userInput = "INVALID";
		}
		while (userInput == null || userInput == "INVALID") {
			System.out.println("Invalid input, please follow the format: ");
			System.out.println("           start stop/end stop");
			System.out.println("> ");
			userInput = scanner.next();
			try {
				String[] userInputSplit = userInput.split("/");
				stops[0] = Integer.parseInt(userInputSplit[0]);
				stops[0] = Integer.parseInt(userInputSplit[0]);
			} catch (Exception e) {
				userInput = "INVALID";
			}
		}

		calculator.fastestRouteBetween(stops[0], stops[1]);
	}

	private static void runTripByArrivalTimeInput(Scanner scanner) {
		String userInput;

		System.out.println("You have chosen, find a trip by its arrival time.");
		System.out.println("Please type the arrival time in the form:");
		System.out.println("                hh:DD:MM");
		System.out.println("> ");

		userInput = scanner.next();
		while (userInput == null || userInput == "INVALID" || userInput == ""
				|| !calculator.searchForStopByName(userInput)) {
			System.out.println("Invalid input or no stops found, please type a name or part of a name.");
			System.out.println("> ");
			userInput = scanner.next();
		}
	}

}
