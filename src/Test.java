import java.io.*;
import java.util.Scanner;

public class Test {
    public static String[][] arr;
    public static void main(String[] args) throws IOException {
        File file=new File("maze30_5.txt");
        Scanner input=new Scanner(file);
        arr=new String[numOfRows()][numOfColumns()];

        int index=0;
        while(input.hasNextLine()){
            arr[index]=input.nextLine().split("(?!^)");
            index++;
        }
        printArr();


    }
    public static int numOfRows() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("maze30_5.txt"));
        int lines = 0;
        while (reader.readLine() != null) lines++;
        reader.close();
        return lines;
    }
    public static int numOfColumns() throws FileNotFoundException {
        int numOfColumns = 0;
        File file=new File("maze30_5.txt");
        Scanner input=new Scanner(file);
        numOfColumns=input.nextLine().split("(?!^)").length;
        return numOfColumns;
    }
    public static void printArr(){
        for (String [] row:arr) {
            for (String column:row) {
                System.out.print(column);
            }
            System.out.println();
        }
    }
}
