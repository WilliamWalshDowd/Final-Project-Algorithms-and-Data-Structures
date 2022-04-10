import java.util.Scanner;

public class FinalProject {

	private static AlgorithmCalculator calculator;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		calculator = new AlgorithmCalculator();
		boolean exit = false;
		String userInput;
		
		while (exit == false) {
			printOutOptionList();
			userInput = scanner.next();

			while (!userInput.contains("1") && !userInput.contains("2") && !userInput.contains("3")) {
				System.out.println(
						"You typed an input that wasn't recognised, please type 1 2 or 3 based on the above list.");
				System.out.print("> ");
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
		System.out.print("> ");
	}

	private static void asnwerInput(String input, Scanner scanner) {
		if (input.contains("1")) {
			runShortestPathInput(scanner);
		} else if (input.contains("2")) {
			runStopByNameInput(scanner);
		} else {
			runTripByArrivalTimeInput(scanner);
		}
	}

	private static void runStopByNameInput(Scanner scanner) {
		String userInput;

		System.out.println("You have chosen, find a stop by its name.");
		System.out.println("Please type the name of the stop or parts of the stop's name");
		System.out.print("> ");

		userInput = scanner.next();
		userInput += scanner.nextLine();

		while (userInput == null || userInput == "INVALID" || userInput == ""
				|| !calculator.searchForStopByName(userInput)) {
			System.out.println("Invalid input or no stops found, please type a name or part of a name.");
			System.out.print("> ");
			userInput = scanner.next();
			userInput += scanner.nextLine();
		}

	}

	private static void runShortestPathInput(Scanner scanner) {
		String userInput;

		System.out.println("You have chosen, find the shorest path between 2 stops.");
		System.out.println("Please type your desired Starting stop and Ending stop in the form: ");
		System.out.println("               start stop ID/end stop ID");
		System.out.print("> ");

		userInput = scanner.next();
		// userInput += scanner.nextLine();
		int[] stops = new int[2];
		try {
			String[] userInputSplit = userInput.split("/");
			stops[0] = Integer.parseInt(userInputSplit[0]);
			stops[1] = Integer.parseInt(userInputSplit[1]);
		} catch (Exception e) {
			userInput = "INVALID";
		}
		while (userInput.equals("INVALID") || !calculator.fastestRouteBetween(stops[0], stops[1])) {
			if (userInput.equals("INVALID")) {
				System.out.println("Invalid input, please follow the format: ");
				System.out.println("           start stop/end stop");
			} else {
				System.out.println("try again and follow the format: ");
				System.out.println("       start stop/end stop");
			}
			System.out.print("> ");
			userInput = scanner.next();
			userInput += scanner.nextLine();
			try {
				String[] userInputSplit = userInput.split("/");
				stops[0] = Integer.parseInt(userInputSplit[0]);
				stops[1] = Integer.parseInt(userInputSplit[1]);
			} catch (Exception e) {
				userInput = "INVALID";
			}
		}
	}

	private static void runTripByArrivalTimeInput(Scanner scanner) {
		String userInput;

		System.out.println("You have chosen, find a trip by its arrival time.");
		System.out.println("Please type the arrival time in the form:");
		System.out.println("                hh:mm:ss");
		System.out.print("> ");

		userInput = scanner.next();
		userInput += scanner.nextLine();
		while (userInput == null || userInput == "INVALID" || userInput == ""
				|| !calculator.searchForTripByTime(userInput)) {
			System.out.println("Invalid input or no trips found, please type a time in the format:");
			System.out.println("                          hh:DD:MM");
			System.out.print("> ");
			userInput = scanner.next();
			userInput += scanner.nextLine();
		}
	}

}
