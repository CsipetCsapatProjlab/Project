package terkep;

public class Main  {

	public static void main(String[] args) {
		
		Maze maze = new Maze(10,100);

		System.out.println(maze.szigetekSzama);

        maze.print();
	}

}
