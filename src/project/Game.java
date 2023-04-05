package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.FloatControl;

public class Game {
	public static boolean running = false;
	private static final int reach = 30;
	static String scoreList = "";
	static String playerInfo = "";
	public static String name;
	static File score = new File("score.txt");
	static File leader = new File("leader.txt");
	static Clip clip;

	public static void main(String[] args) {

		if (score.exists() == false) {
			try {
				score.createNewFile();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		if (leader.exists() == false) {
			try {
				leader.createNewFile();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		while (running == false) {
			clrscr();
			Menu.mainmenu();
			switch (Menu.returnValue()) {
				case 1:
					clrscr();
					Menu.nameEnter();
					switch (Menu.returnValue()) {
						case 1:
							easy();
							Setup.keyInput();
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							clip.stop();
							break;

						case 2:
							medium();
							Setup.keyInput();
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							clip.stop();
							break;

						case 3:
							hard();
							Setup.keyInput();
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							clip.stop();
							break;

					}
					break;
				case 2:
					clrscr();
					Menu.instruction();
					break;
				case 3:
					Menu.History();
					break;
				case 4:
					Menu.Leaderboard();
					break;
				case 5:
					System.out.println("\nGoodbye!");
					System.exit(0);
					break;
				case 6:
					try {
						clearScore();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(scoreList);
					Scanner scan1 = new Scanner(System.in);
					String placeholder1 = scan1.nextLine();
					break;
				default:
					System.out.println("Invalid choice, please try again.");
					Scanner scan = new Scanner(System.in);
					String placeholder = scan.nextLine();
					break;
			}
		}

	}

	public static void clrscr() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static void gameResetter() {
		running = true;
		for (int i = 0; i < Setup.notes.length; i++) {
			for (int j = 0; j < Setup.notes[i].length; j++) {
				Setup.notes[i][j] = "       ";
			}
		}

		Setup.Score = 0;
		Setup.Combo = 0;

		Setup.resetButtonPressed(0);
	}

	public static void Music(float volume) {
		try {
			File musicFile = new File("Rick Astley - Never Gonna Give You Up (Official Music Video).wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
			clip = AudioSystem.getClip();
			clip.open(audioStream);

			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
			gainControl.setValue(dB);
			clip.start();

		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public static void readScore() throws FileNotFoundException {
		scoreList = "";
		try (Scanner fileScan = new Scanner(score)) {
			while (fileScan.hasNextLine()) {
				scoreList = scoreList.concat(fileScan.nextLine() + "\n");
			}
		}
	}
	// ini create score gue ganti sama createNewFile aja saving space sama leaderboard

	// public static void createScore() throws IOException {
	// 	FileWriter fw = new FileWriter("score.txt");
	// 	fw.close();
	// }

	public static void writeScore(String name, int Score, int Combo, String diff) throws IOException {
		FileWriter fw = new FileWriter("score.txt", true);
		name = Menu.username;
		LocalDateTime thismoment = LocalDateTime.now();
		DateTimeFormatter formatPattern = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String formattedDate = thismoment.format(formatPattern);
		String scorelist = name + "\n" + formattedDate + "\nScore : " + Setup.Score + " Combo : "
				+ Setup.Combo + "\n" + diff + " Difficulty\n\n";
		fw.write(scorelist);
		fw.close();
	}

	// public static void createLeaderboard() throws IOException {
	// 	FileWriter fw = new FileWriter("leader.txt");
	// 	fw.close();
	// }

	public static List<String> readLeaderboard() throws FileNotFoundException {
		List<String> leaderboard = new ArrayList<>();
		Scanner fileScan = new Scanner(new File("leader.txt"));
		while (fileScan.hasNextLine()) {
			leaderboard.add(fileScan.nextLine());
		}
		fileScan.close();
		return leaderboard;
	}

	public static void updateLeaderboard(String name, int score, int combo) throws IOException {
		name = Menu.username;
		List<String> leaderboard = readLeaderboard();

		// Buat ngeremove yang sebelumnya
		for (int i = 0; i < leaderboard.size(); i++) {
			String[] playerInfo = leaderboard.get(i).split(",");
			if (playerInfo[0].equals(name)) {
				leaderboard.remove(i);
				break;
			}
		}

		// Nambahin yang baru
		boolean updated = false;
		for (int i = 0; i < leaderboard.size(); i++) {
			String[] playerInfo = leaderboard.get(i).split(",");
			if (score > Integer.parseInt(playerInfo[1]) ||
					(score == Integer.parseInt(playerInfo[1]) && combo > Integer.parseInt(playerInfo[2]))) {
				leaderboard.add(i, name + "," + score + "," + combo);
				updated = true;
				break;
			}
		}

		// Kalo ga updated, make yang sebelumnya
		if (!updated) {
			leaderboard.add(name + "," + score + "," + combo);
		}

		FileWriter fw = new FileWriter("leader.txt");
		for (String playerInfo : leaderboard) {
			fw.write(playerInfo + "\n");
		}
		fw.close();
	}

	public static void update(String diff, String name) throws IOException {
		int[] scoreCombo = Setup.returnScore();
		int score = scoreCombo[0];
		int combo = scoreCombo[1];
		name = Menu.username;

		writeScore(name, score, combo, diff);
		updateLeaderboard(name, score, combo);
	}

	public static void clearScore() throws IOException{
		score.delete();
		leader.delete();
		score.createNewFile();
		leader.delete();
	}
	
	public static void easy() {
		gameResetter();
		final Timer timerEasy = new Timer();
		timerEasy.schedule(new TimerTask() {

			@Override
			public void run() {
				Setup.gameSystem();
				int scoreFinal[] = Setup.returnScore();
				if (scoreFinal[0] >= reach * 1) {
					timerEasy.cancel();
					running = false;
					System.out.println("\nCongratulations, you won!");
					System.out.println("\nFinal score: " + scoreFinal[0] + "   Final Combo: " + scoreFinal[1]);
					try {
						update("easy", name);
					} catch (IOException e) {
						e.printStackTrace();
					}
					System.out.println("Returning to main menu, please wait a second!");
				} else if (scoreFinal[0] <= -reach * 1) {
					timerEasy.cancel();
					running = false;
					System.out.println(timerEasy.purge());
					System.out.println("\nSorry, you lose...");

				}

			}
		}, 0, 1062);

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Music(0.05f);
	}

	public static void medium() {
		gameResetter();
		final Timer timerMedium = new Timer();
		timerMedium.schedule(new TimerTask() {
			@Override
			public void run() {
				Setup.gameSystem();
				int scoreFinal[] = Setup.returnScore();
				if (scoreFinal[0] >= reach * 3) {
					timerMedium.cancel();
					running = false;
					System.out.println("\nCongratulations, you won!");
					System.out.println("\nFinal score: " + scoreFinal[0] + "   Final Combo: " + scoreFinal[1]);
					try {
						update("Medium", name);
					} catch (IOException e) {
						e.printStackTrace();
					}
					System.out.println("Returning to main menu, please wait a second!");

				} else if (scoreFinal[0] <= -reach * 1) {
					timerMedium.cancel();
					running = false;
					System.out.println(timerMedium.purge());
					System.out.println("\nSorry, you lose...");

				}
			}
		}, 0, 796);

		try {
			Thread.sleep(6574);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Music(0.05f);
	}

	public static void hard() {
		gameResetter();
		final Timer timerHard = new Timer();
		timerHard.schedule(new TimerTask() {
			@Override
			public void run() {
				Setup.gameSystem();
				int scoreFinal[] = Setup.returnScore();
				if (scoreFinal[0] >= reach * 10) {
					timerHard.cancel();
					running = false;
					System.out.println("\nCongratulations, you won!");
					System.out.println("\nFinal score: " + scoreFinal[0] + "   Final Combo: " + scoreFinal[1]);
					try {
						update("Hard", name);
					} catch (IOException e) {
						e.printStackTrace();
					}
					System.out.println("Returning to main menu, please wait a second!");

				} else if (scoreFinal[0] <= -reach * 1) {
					timerHard.cancel();
					running = false;
					System.out.println("\nSorry, you lose...");
				}
			}
		}, 0, 531);

		try {
			Thread.sleep(4551);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Music(0.5f);
	}
}