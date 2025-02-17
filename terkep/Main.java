package terkep;

public class Main  {

	public static void main(String[] args) {
		Maze maze = new Maze(20,100);

		maze.generateMaze(10, 50);
        maze.print();
	}

}
