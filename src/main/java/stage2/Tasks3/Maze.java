package stage2.Tasks3;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class Maze {
    public Cell[][] maze;
    private final int sparsity;
    private int startPoint;
    private int endPoint;
    private final int size;
    int counter = 0;

    public int getSize(){
        return size;
    }
    public Maze(int size, int sparsity) {
        this.sparsity = sparsity;
        this.size = size;
        maze = new Cell[size][size];
        fillCells();
    }


    public void fillCells() {
        for (int i = 0; i < maze[0].length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                maze[i][j] = new Cell(i, j, " ");
            }
        }
    }

    public void fillArray(ArrayList<Integer> arr, int size) {
        for (int i = 0; i < size; i++) {
            arr.add(i);
        }
    }

    public void generateMaze() {
        Random rnd = new Random();
        startPoint = rnd.nextInt(0, size - 1);
        maze[0][startPoint].setValue("S");
        endPoint = rnd.nextInt(0, size - 1);
        maze[size - 1][endPoint].setValue("E");
        for (int i = 0; i < maze[0].length; i++) {

            ArrayList<Integer> arr = new ArrayList<>();
            fillArray(arr, size);
            if (i == 0)
                arr.remove(startPoint);
            if (i == maze[0].length - 1)
                arr.remove(endPoint);
            for (int j = 0; j < sparsity; j++) {
                int block = rnd.nextInt(0, arr.size());
                maze[i][arr.get(block)].setValue("▓");
                arr.remove(block);
            }
        }
    }

    public void printMaze() {
        for (int i = 0; i < maze[0].length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                System.out.print(maze[i][j].getValue() + " ");
            }
            System.out.println();

        }
    }

    public void counterStep() {
        counter = counter + 1 > 3 ? 0 : counter + 1;
    }

    public void BFS(Connection conn, int idMaze) throws SQLException {
        int lastDx = 1;
        int lastDy = 0;
        boolean hasPath;
        State[] xy = {new State(1, 0), new State(0, 1), new State(-1, 0), new State(0, -1)};
        Stack<Cell> states = new Stack<>();
        Set<Cell> visitedPoints = new HashSet<>();
        maze[0][startPoint].startOrEnd = true;
        maze[0][startPoint].setValue("S");
        maze[size - 1][endPoint].setValue("E");
        maze[size - 1][endPoint].startOrEnd = true;
        states.push(maze[0][startPoint]);
        visitedPoints.add(maze[0][startPoint]);
        int steps = 0;
        while (!Objects.equals(states.peek().getValue(), "E")) {
            Cell currentCell = states.peek();
            int currentX = currentCell.getX();
            int currentY = currentCell.getY();
            hasPath = false;
            while (true) {
                if (steps == 4) {
                    break;
                }
                if (inBounds(currentX + lastDx, currentY + lastDy)) {
                    if (!visitedPoints.contains(maze[currentX + lastDx][currentY + lastDy])) {
                        if (!Objects.equals(maze[currentX + lastDx][currentY + lastDy].getValue(), "▓")) {
                            states.push(maze[currentX + lastDx][currentY + lastDy]);
                            visitedPoints.add(maze[currentX + lastDx][currentY + lastDy]);
                            if (!maze[currentX + lastDx][currentY + lastDy].startOrEnd) {
                                maze[currentX + lastDx][currentY + lastDy].setValue("*");
                                new Inquire().updateCellValue(conn, idMaze, currentX + lastDx, currentY + lastDy, "*");
                            }
                            hasPath = true;
                            break;
                        }
                    }
                }
                counterStep();
                lastDx = xy[counter].x;
                lastDy = xy[counter].y;
                steps++;
            }
            if (!hasPath) {
                Cell cell = states.peek();
                if (!maze[cell.getX()][cell.getY()].startOrEnd) {
                    maze[cell.getX()][cell.getY()].setValue(" ");
                    new Inquire().updateCellValue(conn, idMaze, cell.getX(), cell.getY(), " ");
                }
                states.pop();
                if (states.empty()) {
                    System.out.println("НЕТ ПУТИ");
                    break;
                }
            }
            steps = 0;
        }

    }

    public boolean inBounds(int x, int y) {
        return x >= 0 && y >= 0 && y < maze.length && x < maze.length;
    }


}
