package org.example;

public class GameOfLife {
    private static final int SIZE = 20;
    private static final char ALIVE = '*';
    private static char DEAD = '$';

    private char[][] grid;

    public GameOfLife() {
        grid = new char[SIZE][SIZE];
        initializeGrid();
    }

    private void initializeGrid() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = Math.random() < 0.5 ? ALIVE : DEAD;
            }
        }
    }

    private void printGrid() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    private int countNeighbors(int x, int y) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int neighborX = x + i;
                int neighborY = y + j;
                if (i == 0 && j == 0) continue; // skip self
                if (neighborX >= 0 && neighborX < SIZE && neighborY >= 0 && neighborY < SIZE) {
                    if (grid[neighborX][neighborY] == ALIVE) count++;
                }
            }
        }
        return count;
    }

    private void nextGeneration() {
        char[][] newGrid = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int neighbors = countNeighbors(i, j);
                if (grid[i][j] == ALIVE) {
                    if (neighbors < 2 || neighbors > 3) {
                        newGrid[i][j] = DEAD; // underpopulation or overpopulation
                    } else {
                        newGrid[i][j] = ALIVE; // survive
                    }
                } else {
                    if (neighbors == 3) {
                        newGrid[i][j] = ALIVE; // reproduction
                    } else {
                        newGrid[i][j] = DEAD; // stay dead
                    }
                }
            }
        }
        grid = newGrid;
    }

    public void run() {
        while (true) {
            printGrid();
            nextGeneration();
            try {
                Thread.sleep(500); // pause for 500ms
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        GameOfLife game = new GameOfLife();
        game.run();
    }
}