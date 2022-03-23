import java.util.LinkedList;

public class PokemonIceCave {
    public static void main(String[] args) {
        int[][] iceCave1 = {
                {0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1},
                {0, 1, 1, 0, 0},
                {0, 1, 0, 0, 1},
                {0, 0, 0, 1, 0}
        };
        System.out.println(solve(iceCave1, 0, 0, 2, 4));
        System.out.println();

        int[][] iceCave2 = {
                {0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1},
                {0, 1, 1, 0, 0},
                {0, 1, 0, 0, 1},
                {0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0}
        };
        System.out.println(solve(iceCave2, 0, 0, 2, 5));
    }

    public static int solve(int[][] iceCave, int startX, int startY, int endX, int endY) {
        Point startPoint = new Point(startX, startY);

        LinkedList<Point> queue = new LinkedList<>();
        Point[][] iceCaveColors = new Point[iceCave.length][iceCave[0].length];

        queue.addLast(new Point(0, 0));
        iceCaveColors[startY][startX] = startPoint;

        while (queue.size() != 0) {
            Point currPos = queue.pollFirst();
            System.out.println(currPos);
            // traverse adjacent nodes while sliding on the ice
            for (Direction dir : Direction.values()) {
                Point nextPos = move(iceCave, iceCaveColors, currPos, dir);
                System.out.println("\t" + nextPos);
                if (nextPos != null) {
                    queue.addLast(nextPos);
                    iceCaveColors[nextPos.getY()][nextPos.getX()] = new Point(currPos.getX(), currPos.getY());
                    if (nextPos.getY() == endY && nextPos.getX() == endX) {
                        // we found the end point
                        Point tmp = currPos;  // if we start from nextPos we will count one too many edges
                        int count = 0;
                        while (tmp != startPoint) {
                            count++;
                            tmp = iceCaveColors[tmp.getY()][tmp.getX()];
                        }
                        return count;
                    }
                }
            }
            System.out.println();
        }
        return -1;
    }

    public static Point move(int[][] iceCave, Point[][] iceCaveColors, Point currPos, Direction dir) {
        int x = currPos.getX();
        int y = currPos.getY();

        int diffX = (dir == Direction.LEFT ? -1 : (dir == Direction.RIGHT ? 1 : 0));
        int diffY = (dir == Direction.UP ? -1 : (dir == Direction.DOWN ? 1 : 0));

        int i = 1;
        while (x + i * diffX >= 0
                && x + i * diffX < iceCave[0].length
                && y + i * diffY >= 0
                && y + i * diffY < iceCave.length
                && iceCave[y + i * diffY][x + i * diffX] != 1) {
            i++;
        }

        i--;  // reverse the last step

        if (iceCaveColors[y + i * diffY][x + i * diffX] != null) {
            // we've already seen this point
            return null;
        }

        return new Point(x + i * diffX, y + i * diffY);
    }

    public static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    public enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
}