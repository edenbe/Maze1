import java.util.ArrayList;
import java.util.List;

public class Node {
    private List<Node> neighbors;
    private int row;
    private int column;
    private boolean visited;

    public Node(int row, int column )  {
        this.row = row;
        this.column = column;
        this.visited = false;
        this.neighbors = new ArrayList<>();
    }

    public void addNeighbors(Node neighbor){
        this.neighbors.add(neighbor);

    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isVisited(){
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }


    public List<Node> getNeighbors() {
        return neighbors;
    }



}