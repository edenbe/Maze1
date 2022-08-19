import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;


public class Maze extends JFrame {

    private int[][] values;
    private boolean[][] visited;
    private int startRow;
    private int startColumn;
    private ArrayList<JButton> buttonList;
    private int rows;
    private int columns;
    private boolean backtracking;
    private int algorithm;

    public Maze( int size, int startRow, int startColumn) {
       // this.algorithm = algorithm;
        Random random = new Random();
        this.values = new int[size][];
        for (int i = 0; i < values.length; i++) {
            int[] row = new int[size];
            for (int j = 0; j < row.length; j++) {
                if (i > 1 || j > 1) {
                    row[j] = random.nextInt(8) % 7 == 0 ? Definitions.OBSTACLE : Definitions.EMPTY;
                } else {
                    row[j] = Definitions.EMPTY;
                }
            }
            values[i] = row;
        }
        values[0][0] = Definitions.EMPTY;
        values[size - 1][size - 1] = Definitions.EMPTY;
        this.visited = new boolean[this.values.length][this.values.length];
        this.startRow = startRow;
        this.startColumn = startColumn;
        this.buttonList = new ArrayList<>();
        this.rows = values.length;
        this.columns = values.length;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setLocationRelativeTo(null);
        GridLayout gridLayout = new GridLayout(rows, columns);
        this.setLayout(gridLayout);
        for (int i = 0; i < rows * columns; i++) {
            int value = values[i / rows][i % columns];
            JButton jButton = new JButton(String.valueOf(i));
            if (value == Definitions.OBSTACLE) {
                jButton.setBackground(Color.BLACK);
            } else {
                jButton.setBackground(Color.WHITE);
            }
            this.buttonList.add(jButton);
            this.add(jButton);
        }
        this.setVisible(true);
        this.setSize(Definitions.WINDOW_WIDTH, Definitions.WINDOW_HEIGHT);
        this.setResizable(false);
    }

    public void checkWayOut() {
        new Thread(() -> {
            boolean result = false;
                    Node[][] nodesList = initNodesMatrix();
                    Stack<Node> stack = new Stack<>();
                    stack.add(nodesList[0][0]);
                    addNeighbors(nodesList,values.length);
                    while (!stack.empty() && !result) {
                        Node currentNode = stack.pop();
                        if (!currentNode.isVisited()) {
                            currentNode.setVisited(true);
                            setSquareAsVisited(currentNode.getRow(), currentNode.getColumn(), currentNode.isVisited());
                            if (isResult(currentNode)){
                                result=true;
                            }
                            for (Node neighbor :  currentNode.getNeighbors()) {
                                if (!neighbor.isVisited()&& isEmpty(neighbor))
                                    stack.add(neighbor);

                            }

                        }

                    }

            JOptionPane.showMessageDialog(null, result ? "FOUND SOLUTION" : "NO SOLUTION FOR THIS MAZE");

        }).start();
    }



    public void setSquareAsVisited(int x, int y, boolean visited) {
        try {
            if (visited) {
                if (this.backtracking) {
                    Thread.sleep(Definitions.PAUSE_BEFORE_NEXT_SQUARE * 5);
                    this.backtracking = false;
                }
                this.visited[x][y] = true;
                for (int i = 0; i < this.visited.length; i++) {
                    for (int j = 0; j < this.visited[i].length; j++) {
                        if (this.visited[i][j]) {
                            if (i == x && y == j) {
                                this.buttonList.get(i * this.rows + j).setBackground(Color.RED);
                            } else {
                                this.buttonList.get(i * this.rows + j).setBackground(Color.BLUE);
                            }
                        }
                    }
                }
            } else {
                this.visited[x][y] = false;
                this.buttonList.get(x * this.columns + y).setBackground(Color.WHITE);
                Thread.sleep(Definitions.PAUSE_BEFORE_BACKTRACK);
                this.backtracking = true;
            }
            if (!visited) {
                Thread.sleep(Definitions.PAUSE_BEFORE_NEXT_SQUARE / 4);
            } else {
                Thread.sleep(Definitions.PAUSE_BEFORE_NEXT_SQUARE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isResult (Node nodeToCheck) {
        boolean isSolved = false;
        if (nodeToCheck.getRow()==rows-1 && nodeToCheck.getColumn()==columns-1) {
            isSolved=true;
        }
        return isSolved;
    }

    public Node[][] initNodesMatrix() {
        Node[][] nodes = new Node[values.length][values.length];
        for (int i = 0; i < values.length; i++) {
            nodes[i] = new Node[values.length];
            for (int j = 0; j < values.length; j++) {
                nodes[i][j] = new Node(i, j);
            }
        }

        return nodes;
    }

    private void addNeighbors(Node[][] nodes, int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Node currentNode=nodes[i][j];
                if(isValidIndex(i-1)!=-1){
                    currentNode.addNeighbors(nodes[i - 1] [j]);
                }
                if(isValidIndex(j - 1)!=-1){
                    currentNode.addNeighbors(nodes[i] [j - 1]);
                }
                if (isValidIndex(i+1)!=-1){
                    nodes[i][j].addNeighbors(nodes[i + 1] [j]);
                }
                if (isValidIndex(j+1)!=-1){
                    currentNode.addNeighbors( nodes[i][ j + 1]);
                }

            }
        }


    }
    private int isValidIndex(int x){
        int valid=-1;
        if (x<rows  && x>=0 ){
            valid=x;
        }
        return valid;
    }


    private boolean isEmpty (Node node) {
        boolean isWhite= values[node.getRow()][node.getColumn()] == Definitions.EMPTY;

        return isWhite;
    }



}