import java.util.Scanner;

public class Chopsticks {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Chopsticks game!");

        System.out.println("Enter the number of players: ");
        int numPlayers = scanner.nextInt();

        int[] hands = new int[numPlayers * 2];
        boolean[] isHandLive = new boolean[numPlayers * 2];
        boolean[] isPlayerEliminated = new boolean[numPlayers];

        // Initialize the game
        for (int i = 0; i < numPlayers * 2; i++) {
            hands[i] = 1;
            isHandLive[i] = true;
        }

        boolean continueGame = true;
        int currentPlayer = 0;

        while (continueGame) {
            System.out.println("Player " + (currentPlayer + 1) + ", it's your turn.");
            System.out.println("Enter 'A' to attack, 'S' to split, or 'E' to end your turn: ");
            String choice = scanner.next();

            if (choice.equalsIgnoreCase("A")) {
                System.out.println("Enter the hand index to attack (0-" + (numPlayers * 2 - 1) + "): ");
                int handIndex = scanner.nextInt();

                if (isHandLive[handIndex] && handIndex != currentPlayer && !isPlayerEliminated[currentPlayer]) {
                    hands[handIndex] += hands[currentPlayer];
                    hands[handIndex] %= 5;

                    if (hands[handIndex] == 0) {
                        isHandLive[handIndex] = false;
                        if (handIndex % 2 == 0) {
                            isPlayerEliminated[handIndex / 2] = true;
                        }
                    }
                }
            } else if (choice.equalsIgnoreCase("S")) {
                System.out.println("Enter the hand index to split (0-" + (numPlayers * 2 - 1) + "): ");
                int handIndex = scanner.nextInt();

                if (isHandLive[handIndex] && handIndex == currentPlayer && !isPlayerEliminated[currentPlayer]) {
                    int otherHandIndex = (handIndex + 1) % (numPlayers * 2);

                    if (hands[handIndex] != 0 && hands[otherHandIndex] != 0) {
                        System.out.println("Enter the number of fingers to transfer: ");
                        int fingersToTransfer = scanner.nextInt();

                        if (fingersToTransfer <= hands[otherHandIndex] && (hands[handIndex] + fingersToTransfer <= 4)) {
                            hands[handIndex] += fingersToTransfer;
                            hands[handIndex] %= 5;
                            hands[otherHandIndex] -= fingersToTransfer;
                            hands[otherHandIndex] %= 5;
                        }
                    }
                }
            } else if (choice.equalsIgnoreCase("E")) {
                continueGame = false;
            }

            currentPlayer = (currentPlayer + 1) % numPlayers;
        }

        int remainingPlayers = 0;
        int winnerIndex = -1;
        for (int i = 0; i < numPlayers; i++) {
            if (!isPlayerEliminated[i]) {
                remainingPlayers++;
                winnerIndex = i;
            }
        }

        System.out.println();
        System.out.println("Game over!");

        if (remainingPlayers == 1) {
            System.out.println("Player " + (winnerIndex + 1) + " wins!");
        } else {
            System.out.println("No winner. The game ended with multiple players remaining.");
        }

        scanner.close();
    }
}
