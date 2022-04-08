import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Test {
    public static String[][] arr;//2d array containing whole elements in the puzzle with i and j - [i rows/down ,j- columns/right]
    public static int si;//start position i index
    public static int sj;//start position j index
    public static int ei;//finish position i index
    public static int ej;//finish position j index
    public static int [][] blockList;//indexes of block list - {{index}{i,j}}
    public static String fileName="maze10_1.txt";//puzzle file name

    public static void main(String[] args) throws IOException {

        File file=new File(fileName);
        Scanner input=new Scanner(file);
        arr=new String[numOfRows()][numOfColumns()];//initialize the size of the 2d array

        int index=0;//line index
        while(input.hasNextLine()){
            arr[index]=input.nextLine().split("(?!^)");
            index++;
        }

        startAndEnd();//initialize start and end
        initializeBlocks();//initialize the blocks [0] into 2d array
        AStar aStar = new AStar(numOfColumns(),numOfRows(), si, sj, ei, ej, blockList);

        aStar.display();//displaying the puzzle
        aStar.process();//doing the process
        aStar.displayScores();//displaying the scores
        aStar.displaySolutionCorrect();//displaying the solution

    }

    /*
     * @return number of rows in the puzzle
     * */
    public static int numOfRows() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        int lines = 0;
        while (reader.readLine() != null) lines++;
        reader.close();
        return lines;
    }

    /*
     * @return number of columns
     */
    public static int numOfColumns() throws FileNotFoundException {
        int numOfColumns;
        File file=new File(fileName);
        Scanner input=new Scanner(file);
        numOfColumns=input.nextLine().split("(?!^)").length;
        return numOfColumns;
    }

    /*
     * Initialize the start and end - i and j
     * */
    public static void startAndEnd(){
        for (int i=0;i<arr.length; i++) {
            for (int j=0;j<arr[i].length;j++) {
                if(arr[i][j].equals("S")){
                    si=i;
                    sj=j;
                }else if(arr[i][j].equals("F")){
                    ei=i;
                    ej=j;
                }
            }
        }
    }

    /*
     * Initialize the blocks indexes into 2d array
     * */
    public static void initializeBlocks(){
        ArrayList <int[]> blocks = new ArrayList<>();
        for (int i=0;i<arr.length; i++) {
            for (int j=0;j<arr[i].length;j++) {
                if(arr[i][j].equals("0")){
                    blocks.add(new int[]{i,j});
                }
            }
        }

        //initialize blockList array
        blockList=new int[blocks.size()][2];
        for(int i=0;i<blocks.size();i++){
            blockList[i]=blocks.get(i);
        }
    }
}
