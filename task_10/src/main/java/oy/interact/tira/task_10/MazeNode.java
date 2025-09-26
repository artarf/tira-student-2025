package oy.interact.tira.task_10;

/**
 * MazeNode is the element held in the graph vertices and edges in the Maze app.
 * 
 * @author Antti Juustila
 * @version 1.0
 */
public class MazeNode implements Comparable<MazeNode> {
   
   private int xCoordinate;
   private int yCoordinate;

   /** In a maze, vertices are in a grid. Thus we can talk about vertex orientations to 
    * each other. If a vertex is on the right side, it is to EAST. If it is below, it
    * is in the SOUTH.
    */
   public enum Direction {
      NORTH,
      EAST,
      SOUTH,
      WEST,
      NONE
   }

   public MazeNode(int x, int y) {
      if (x < 0 || y < 0) {
         throw new RuntimeException("Maze coordinates must be >= 0");
      }
      if (x >= Short.MAX_VALUE || y >= Short.MAX_VALUE) {
         throw new RuntimeException("Too large maze coordinates");
      }
      xCoordinate = x;
      yCoordinate = y;
   }

   public int getX() {
      return xCoordinate;
   }

   public int getY() {
      return yCoordinate;
   }

   public Direction orientationTo(MazeNode another) {
      if (xCoordinate < another.xCoordinate) {
         return Direction.WEST;
      } else if (xCoordinate > another.xCoordinate) {
         return Direction.EAST;
      } else if (yCoordinate < another.yCoordinate) {
         return Direction.NORTH;
      } else if (yCoordinate > another.yCoordinate) {
         return Direction.SOUTH;
      }
      return Direction.NONE;
   }

   public MazeNode inDirectionOf(Direction direction) {
      switch (direction) {
         case NORTH:
            return new MazeNode(xCoordinate, yCoordinate - 1);
         case SOUTH:
            return new MazeNode(xCoordinate, yCoordinate + 1);
         case WEST:
            return new MazeNode(xCoordinate - 1, yCoordinate);
         case EAST:
            return new MazeNode(xCoordinate + 1, yCoordinate);
         default:
            break;
      }
      return null;
   }

   @Override
   public int compareTo(MazeNode o) {
      if (xCoordinate != o.xCoordinate) {
         return xCoordinate - o.xCoordinate;
      } else {
         if (yCoordinate != o.yCoordinate) {
            return yCoordinate - o.yCoordinate;
         }
      }
      return 0;
   }

   @Override
   public boolean equals(Object o) {
      if (o instanceof MazeNode) {
         MazeNode node = (MazeNode)o;
         return node.xCoordinate == xCoordinate && node.yCoordinate == yCoordinate;
      }
      return false;
   }

   @Override
   public int hashCode() {
      // STUDENT: TODO: implement an efficient algorithm for calculating 
      // hash from the coordinates.
      return 0;
   }

   @Override
   public String toString() {
      return String.format("%d,%d", xCoordinate, yCoordinate);
   }
   
}
