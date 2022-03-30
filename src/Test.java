import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Test {
    public static String[][] arr;
    public static int si;
    public static int sj;
    public static int ei;
    public static int ej;
    public static int [][] blockList;
    public static String fileName="example_CW.txt";

    public static void main(String[] args) throws IOException {

        File file=new File(fileName);
        Scanner input=new Scanner(file);
        arr=new String[numOfRows()][numOfColumns()];

        int index=0;//initialize
        while(input.hasNextLine()){
            arr[index]=input.nextLine().split("(?!^)");
            index++;
        }
        printArr();

        ////////////
        startAndEnd();
        blocks();
        System.out.println(si);
        System.out.println(sj);
        System.out.println(ei);
        System.out.println(ej);
        AStar aStar = new AStar(numOfColumns(),numOfRows(), si, sj, ei, ej,
                blockList);

        aStar.display();
        aStar.process();
        aStar.displayScores();
        //aStar.displaySolution();
        aStar.displaySolutionCorrect();



        ///////////////


    }
    public static int numOfRows() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        int lines = 0;
        while (reader.readLine() != null) lines++;
        reader.close();
        return lines;
    }
    public static int numOfColumns() throws FileNotFoundException {
        int numOfColumns = 0;
        File file=new File(fileName);
        Scanner input=new Scanner(file);
        numOfColumns=input.nextLine().split("(?!^)").length;
        return numOfColumns;
    }
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
    public static void printArr(){
        for (String [] row:arr) {
            for (String column:row) {
                System.out.print(column);
            }
            System.out.println();
        }
    }
    public static void blocks(){
        ArrayList <int[]> blocks = new ArrayList<int[]>();
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
