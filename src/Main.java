import javax.swing.*;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        String sizeAsString = JOptionPane.showInputDialog("Welcome to solving DFS maze , please choose maze size: ");
        int size = Integer.parseInt(sizeAsString);
        Maze maze = new Maze(size, 0, 0);

        maze.checkWayOut();
    }

}