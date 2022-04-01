import java.util.ArrayList;
import java.util.PriorityQueue;

public class AStar {
    //    costs for diagonal and vertical / horizontal moves
    public static final int DIAGONAL_COST = 14;
    public static final int V_H_COST = 10;
    //    Cell of our grid
    private Cell[][] grid;
    //    We define a priority queue for open cells
//    Open Cells : the set of nodes to be evaluated
//    we put cells with the lowest cost in first
    private PriorityQueue<Cell> openCells;
    //    Closed Cells : the set of nodes already evaluated
    private boolean[][] closedCells;
    //    Start Cell
    private int startI, startJ;
    //    End Cell
    private int endI, endJ;

    public AStar(int width, int height, int si, int sj, int ei, int ej, int[][] blocks) {
        grid = new Cell[width][height];
        closedCells = new boolean[width][height];
        openCells = new PriorityQueue<>((Cell c1, Cell c2) -> {
            return c1.finalCost < c2.finalCost ? -1 : c1.finalCost > c2.finalCost ? 1 : 0;
        });

        startCell(si, sj);
        endCell(ei, ej);

//        init heuristic
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Cell(i, j);
                grid[i][j].heuristicCost = Math.abs(i - endI) + Math.abs(j - endJ);
                grid[i][j].solution = false;
            }
        }

        grid[startI][startJ].finalCost = 0;

//        we put the blocks on the grid
        for (int i = 0; i < blocks.length; i++) {
            addBlockOnCell(blocks[i][0], blocks[i][1]);
        }
    }

    public void addBlockOnCell(int i, int j) {
        grid[i][j] = null;
    }

    public void startCell(int i, int j) {
        startI = i;
        startJ = j;
    }

    public void endCell(int i, int j) {
        endI = i;
        endJ = j;
    }

    public void updateCostIfNeeded(Cell current, Cell t, int cost) {
        if (t == null || closedCells[t.i][t.j])
            return;

        int tFinalCost = t.heuristicCost + cost;
        boolean isOpen = openCells.contains(t);

        if (!isOpen || tFinalCost < t.finalCost) {
            t.finalCost = tFinalCost;
            t.parent = current;

            if (!isOpen) {
                openCells.add(t);
            }
        }
    }

    public void process() {
        openCells.add(grid[startI][startJ]);
        Cell current;

        while (true) {
            current = openCells.poll();

            if (current == null) {
                break;
            }

            closedCells[current.i][current.j] = true;

            if (current.equals(grid[endI][endJ])) {
                return;
            }

            Cell t;

            if (current.i - numUpSlider(current) >= 0) {
                t = grid[current.i - numUpSlider(current)][current.j];
                updateCostIfNeeded(current, t, current.finalCost + (V_H_COST* numUpSlider(current)));
            }

            if (current.j - numLeftSlider(current) >= 0) {
                t = grid[current.i][current.j - numLeftSlider(current)];
                updateCostIfNeeded(current, t, current.finalCost + V_H_COST* numLeftSlider(current));
            }

            if (current.j + numRightSlider(current) < grid[0].length) {
                t = grid[current.i][current.j + numRightSlider(current)];
                updateCostIfNeeded(current, t, current.finalCost + V_H_COST* numRightSlider(current));
            }

            if (current.i + numDownSlider(current) < grid.length) {
                t = grid[current.i + numDownSlider(current)][current.j];
                updateCostIfNeeded(current, t, current.finalCost + V_H_COST* numDownSlider(current));
            }
        }
    }
    public int numUpSlider(Cell current){
        int ci=current.i;
        int cj=current.j;
        int count=0;
        while(ci-1>=0 && grid[ci-1][cj]!=null){
            count++;
            ci--;
        }

        return count;
    }
    public int numLeftSlider(Cell current){
        int ci=current.i;
        int cj=current.j;
        int count=0;
        while(cj-1>=0 && grid[ci][cj-1]!=null){
            count++;
            cj--;
        }

        return count;
    }
    public int numDownSlider(Cell current){
        int ci=current.i;
        int cj=current.j;
        int count=0;
        while(ci+1<grid[0].length && grid[ci+1][cj]!=null){
            count++;
            ci++;
        }

        return count;
    }
    public int numRightSlider(Cell current){
        int ci=current.i;
        int cj=current.j;
        int count=0;
        while(cj+1<grid[0].length && grid[ci][cj+1]!=null){
            count++;
            cj++;
        }

        return count;
    }
    public void displaySolutionCorrect() {
        ArrayList<Cell> solutionList = new ArrayList<>();//cells in right order
        if (closedCells[endI][endJ]) {
//            we track back the path
            System.out.println("Path :");
            Cell current = grid[endI][endJ];
            solutionList.add(0,current);
            grid[current.i][current.j].solution = true;

            while (current.parent != null) {
                solutionList.add(0,current.parent);
                grid[current.parent.i][current.parent.j].solution = true;
                current = current.parent;
            }
            Cell temp=null;
            for (Cell cell:solutionList) {
                if(temp == null) {//first node
                    System.out.println("Start at      ("+(cell.j+1)+","+(cell.i+1)+")");//change i and j while printing to more visualisation
                }else{
                    if(temp.i>cell.i){
                        System.out.println("Move up to    ("+(cell.j+1)+","+(cell.i+1)+")");
                    }else if(temp.i<cell.i){
                        System.out.println("Move down to  ("+(cell.j+1)+","+(cell.i+1)+")");
                    }else if(temp.j>cell.j){
                        System.out.println("Move left to  ("+(cell.j+1)+","+(cell.i+1)+")");
                    }else if(temp.j<cell.j){
                        System.out.println("Move right to ("+(cell.j+1)+","+(cell.i+1)+")");
                    }
                }
                temp=cell;

            }
            System.out.println("Done!\n");

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (i == startI && j == startJ) {
                        System.out.print("S"); //source cell
                    } else if (i == endI && j == endJ) {
                        System.out.print("F"); //destination cell
                    } else if (grid[i][j] != null) {
                        System.out.printf("%-1s", grid[i][j].solution ? "X" : ".");//cham- print x if solution true otherwise 0
                    } else {
                        System.out.print("0"); //block cell
                    }
                }
                System.out.println();
            }
            System.out.println();
        } else {
            System.out.println("No possible path");
        }
    }


    public void display() {
        System.out.println("Grid :");

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (i == startI && j == startJ) {
                    System.out.print("S   "); //source cell
                } else if (i == endI && j == endJ) {
                    System.out.print("F   "); //destination cell
                } else if (grid[i][j] != null) {
                    System.out.printf("%-3s ", ".");
                } else {
                    System.out.print("0   "); //block cell
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void displayScores() {
        System.out.println("\nScores for cells :");

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != null) {
                    System.out.printf("%-3d ", grid[i][j].finalCost);
                } else {
                    System.out.print("BL  ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void displaySolution() {
        if (closedCells[endI][endJ]) {
//            we track back the path
            System.out.println("Path :");
            Cell current = grid[endI][endJ];
            System.out.println(current);
            grid[current.i][current.j].solution = true;

            while (current.parent != null) {
                System.out.println(" -> " + current.parent);
                grid[current.parent.i][current.parent.j].solution = true;
                current = current.parent;
            }
            System.out.println();

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (i == startI && j == startJ) {
                        System.out.print("SO  "); //source cell
                    } else if (i == endI && j == endJ) {
                        System.out.print("DE  "); //destination cell
                    } else if (grid[i][j] != null) {
                        System.out.printf("%-3s ", grid[i][j].solution ? "X" : ".");//cham- print x if solution true otherwise 0
                    } else {
                        System.out.print("BL  "); //block cell
                    }
                }
                System.out.println();
            }
            System.out.println();
        } else {
            System.out.println("No possible path");
        }
    }

    public static void main(String[] args) {//  cham0   -i -down j -up
        AStar aStar = new AStar(5,5, 2, 4, 0, 0,
                new int[][]{
                        {0,4}, {2,2}, {3,1}, {3,3}, {2,1}, {2,3}
                });

        aStar.display();
        aStar.process();
        aStar.displayScores();
        aStar.displaySolution();
    }
}