import java.util.ArrayList;

public class MyAlgo {
    static int[][] iceCave1 = {
            {0, 0, 0, 1, 0},
            {0, 0, 0, 0, 1},
            {0, 1, 1, 0, 0},
            {0, 1, 0, 0, 1},
            {0, 0, 0, 1, 0}
    };
    public static void main(String[] args) {
        start(new MyCell(0,0,null),new MyCell(3,3,null));
    }
    static void start(MyCell s,MyCell e){
        ArrayList<MyCell> visitedList=new ArrayList<>();
        int cx=s.x;
        int cy=s.y;
        while(!(cx==e.x && cy==e.y)){
            checkNext(s,"left");
            checkNext(s,"right");
            checkNext(s,"up");
            checkNext(s,"down");

        }

    }
    static MyCell checkNext(MyCell current,String direction){
        int newX=current.x;
        int newY=current.y;
        if(direction.equals("left")){
            while(newX>0 &&newY>0 &&iceCave1[newX-1][newY]!=1 && newX!=iceCave1[0].length &&  newY!=iceCave1.length ){
                newX-=1;
            }
        }else if(direction.equals("right")){
            while(newX>0 &&newY>0 &&iceCave1[newX+1][newY]!=1 && newX!=iceCave1[0].length && newY!=iceCave1.length ){
                newX+=1;
            }
        }else if(direction.equals("up")){
            while(newX>0 &&newY>0 &&iceCave1[newX][newY-1]!=1 && newX!=iceCave1[0].length  && newY!=iceCave1.length ){
                newY-=1;
            }
        }else if(direction.equals("down")){
            while(newX>0 &&newY>0 &&iceCave1[newX][newY+1]!=1 && newX!=iceCave1[0].length && newY!=iceCave1.length){
                newY+=1;
            }
        }
        System.out.println(newX+" "+newY);
        return new MyCell(newX,newY,current);
    }
}

