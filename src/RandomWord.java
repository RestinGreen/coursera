import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {

	public static void main(String[] args) {

		String champion = null;
		int i = 0;
		String string = null;
		while (!StdIn.isEmpty()) {
			string = StdIn.readString();
			i++;
			if (string.isEmpty()) {
				break;
			}
			if (StdRandom.bernoulli(1.0 / i)) {
				champion = string;
			}
		}

		StdOut.println(champion);

	}

}
