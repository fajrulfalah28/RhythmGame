package project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Menu extends Game {
    private static int mainchoice = 0;
    public static String username;

    public static void mainmenu() {
        mainchoice = 0;
        System.out.println("+--------------------------+");
        System.out.println("|       RHYTHM GAME        |");
        System.out.println("+--------------------------+");
        System.out.println("| 1. Select Difficulty     |");
        System.out.println("| 2. Instructions          |");
        System.out.println("| 3. History               |");
        System.out.println("| 4. Leaderboard           |");
        System.out.println("| 5. Reset Score           |");
        System.out.println("| 6. Exit                  |");
        System.out.println("+--------------------------+");
        System.out.print("Enter your choice: ");
        Scanner input = new Scanner(System.in);
        mainchoice = input.nextInt();
    }

    public static void nameEnter() {
        System.out.println("+--------------------------+");
        System.out.println("|   Enter your username:   |");
        System.out.println("+--------------------------+");
        Scanner input = new Scanner(System.in);
        username = input.nextLine();
        diffmenu();

    }

    public static void diffmenu() {
        clrscr();
        System.out.println("+--------------------------+");
        System.out.println("|        DIFFICULTY        |");
        System.out.println("+--------------------------+");
        System.out.println("| 1. Easy                  |");
        System.out.println("| 2. Medium                |");
        System.out.println("| 3. Hard                  |");
        System.out.println("+--------------------------+");
        System.out.print("Enter your choice: ");
        mainchoice = 0;
        Scanner input = new Scanner(System.in);
        mainchoice = input.nextInt();

    }

    public static int returnValue() {
        return mainchoice;
    }

    public static void instruction() {
        System.out.println("+--------------------------+");
        System.out.println("|       INSTRUCTIONS       |");
        System.out.println("+--------------------------+");
        System.out.println("Instructions 101");
        System.out.println("\nIn order to play this game,");
        System.out.println("You must press the falling");
        System.out.println("Note above the drop point.");
        System.out.println("\nIf you failed, the point will");
        System.out.println("Decrease, try to finish the");
        System.out.println("Level and win the game!");
        System.out.println("+--------------------------+");
        System.out.println("\nDo you understand? (Yes/No)");
        Scanner scanner = new Scanner(System.in);
        String Answer = scanner.nextLine();

        if (Answer.equalsIgnoreCase("yes")) {
            mainchoice = 0;
        } else {
            mainchoice = 2;
        }

    }

    public static void History() {
        try {
            readScore();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        clrscr();
        System.out.println("+--------------------------+");
        System.out.println("|          HISTORY         |");
        System.out.println("+--------------------------+");
        System.out.println(scoreList);
        Scanner scan = new Scanner(System.in);
        String placeholder = scan.nextLine();
    }

    public static void Leaderboard() {
        try {
            List<String> leaderboard = readLeaderboard();
            clrscr();
            System.out.println("+--------------------------+");
            System.out.println("|        Leaderboard       |");
            System.out.println("+--------------------------+");
            System.out.printf("%-10s %-10s %-10s\n", "Name", "Score", "Combo");
            for (String playerInfo : leaderboard) {
                String[] info = playerInfo.split(",");
                System.out.printf("%-10s %-10s %-10s\n", info[0], info[1], info[2]);
            }
            Scanner scan = new Scanner(System.in);
            String placeholder = scan.nextLine();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static void resetScore(){
        clrscr();
        System.out.println("+--------------------------+");
        System.out.println("|        Reset Score       |");
        System.out.println("+--------------------------+");
        System.out.println("| Are you sure you want to |");
        System.out.println("| reset your score?        |");
        System.out.println("|(This choice is permanent)|");
        System.out.println("|           (Y/N)          |");
        System.out.println("+--------------------------+");
        Scanner scan = new Scanner(System.in);
        String scoreReset = scan.nextLine();
        if( scoreReset.equalsIgnoreCase("Y") ){
            try {
                clearScore();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else{

        }
            
        
    }

}
