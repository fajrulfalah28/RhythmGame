package project;

import java.util.Scanner;

public class Setup {
	static String[][] notes = {
			{ "       ", "       ", "       ", "       " },
			{ "       ", "       ", "       ", "       " },
			{ "       ", "       ", "       ", "       " },
			{ "       ", "       ", "       ", "       " },
			{ "       ", "       ", "       ", "       " },
			{ "       ", "       ", "       ", "       " },
			{ "       ", "       ", "       ", "       " },
			{ "       ", "       ", "       ", "       " },
			{ "       ", "       ", "       ", "       " },
			{ "       ", "       ", "       ", "       " },
			{ "       ", "       ", "       ", "       " },
			{ "       ", "       ", "       ", "       " },
			{ "       ", "       ", "       ", "       " }
	};

	public static int Score = 0;
	public static int Combo = 0;
	private static int buttonPressed = 0;
	private static Scanner scanner = new Scanner(System.in);

	public static void resetButtonPressed(int value) {
		buttonPressed = value;
	}

	public static void gameSystem() {
		Game.clrscr();

		for (int i = notes.length - 1; i > 0; i--) {
			notes[i] = notes[i - 1];
		}

		notes[0] = new String[] { "       ", "       ", "       ", "       " };
		int randomNoteIndex = (int) Math.floor(Math.random() * 4);
		notes[0][randomNoteIndex] = "   -   ";

		if (buttonPressed == 1) {
			Combo = 0;
			Score -= 5;

		} else if (buttonPressed == 2) {
			Combo++;
			Score += 5;
			buttonPressed = 1;
		}

		System.out.println("+--------------------------+");
		System.out.println("|       RHYTHM GAME        |");
		System.out.println("+--------------------------+");
		System.out.println("|       Rick Astley        |");
		System.out.println("| Never Gonna Give You Up~ |");
		System.out.println("+--------------------------+");
		System.out.println(
				"Score: " + Score + "   Combo: " + Combo
						+ "\n+--------------------------+");
		try {
			for (String[] noteRow : notes) {
				for (String note : noteRow) {
					System.out.print(note);
				}
				System.out.println("");
			}
		} catch (Exception e) {

		}
		System.out.println("| [w]    [a]    [s]    [d] |");
		System.out.println("+--------------------------+");

	}

	public static int[] returnScore() {
		int returnscore[] = { Score, Combo };
		return returnscore;
	}

	public static void keyInput() {
		while (Game.running == true) {
			String input = scanner.nextLine();
			for (int i = 0; i < notes[notes.length - 1].length; i++) {
				if (input.equals("w") && notes[notes.length - 1][0].contains("   -   ")
						|| input.equals("a") && notes[notes.length - 1][1].contains("   -   ")
						|| input.equals("s") && notes[notes.length - 1][2].contains("   -   ")
						|| input.equals("d") && notes[notes.length - 1][3].contains("   -   ")) {
					buttonPressed = 2;
					break;
				}

			}
		}
	}

}
