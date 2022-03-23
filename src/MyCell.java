public class MyCell {
    int x,y;
    MyCell parent;
    boolean visited=false;

    public MyCell(int x, int y,MyCell parent) {
        this.x = x;
        this.y = y;
        this.parent=parent;
    }
}
