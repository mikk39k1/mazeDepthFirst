package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Maze {

    private static char[][] maze;

    private static final int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    public Maze(String filepath) throws IOException {
        maze = loadMazeFromFile(filepath);
    }

    private char[][] loadMazeFromFile(String filepath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        List<char[]> lines = new ArrayList<>();
        String line;

        while ((line = reader.readLine()) != null) {
            lines.add(line.toCharArray());
        }

        char [][] maze = new char[lines.size()][];

        for (int i = 0; i < lines.size(); i++) {
            maze[i] = lines.get(i);
        }

        reader.close();
        return maze;
    }

    private void printMaze() {
        for (char[] row : maze) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public boolean findRoute() {
        for (int y = 0; y < maze.length; y++) {
            for (int x = 0; x < maze[y].length; x++) {
                if (maze[y][x] == 'S') {
                    return dfs(x, y);
                }
            }
        }
        return false;
    }

    private boolean dfs(int x, int y) {

        if (x < 0 || y < 0 || y >= maze.length || x >= maze[y].length || maze[y][x] == '#' || maze[y][x] == '*' || maze[y][x] == 'x') {
            return false;
        }


        if (maze[y][x] == 'G') {
            return true;
        }


        maze[y][x] = 'x';


        for (int[] direction : directions) {
            if (dfs(x + direction[0], y + direction[1])) {
                return true;
            }
        }


        return false;
    }

    private void printCoordinates(int x, int y) {
        if (y >= 0 && y < maze.length && x >= 0 && x < maze[y].length) {
            System.out.println(maze[y][x]);
        } else {
            System.out.println("Invalid coordinates");
        }
    }


    public static void main(String[] args) throws IOException {
        Maze maze = new Maze("src/main/resources/maze.txt");
        maze.printMaze();
        if (maze.findRoute()) {
            System.out.println("Route found!");
        } else {
            System.out.println("No route found.");
        }
        maze.printMaze();
    }

}