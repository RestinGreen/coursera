import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {

	private double sum = 0;
	private int trials;
	private double[] openSites;
	private int min;
	private int max;

	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0) {
			throw new IllegalArgumentException();
		}
		openSites = new double[trials];

		this.trials = trials;
		this.min = n * n;
		this.max = 0;
		int i = 0;
		while (i < trials) {

			Percolation p = new Percolation(n);
			while (!p.percolates()) {
				int randRow = StdRandom.uniformInt(n) + 1;
				int randCol = StdRandom.uniformInt(n) + 1;
//				System.out.println("------------------------");
//				System.out.printf("rRow: %-2d , rCol: %-2d\n", randRow, randCol);
				if (!p.isOpen(randRow, randCol)) {
					p.open(randRow, randCol);
//					p.print();
				}
			}
			sum += (double)p.numberOfOpenSites() / (n * n);
//		System.out.println(p.getNrOpenSites());
			openSites[i] = (double)p.numberOfOpenSites()/(n*n);
			i++;
		}
	}

	public double mean() {
		return sum / trials;
	}

	public double stddev() {
		double mean = mean();
		double s = 0;
		for (int i = 0; i < openSites.length; i++) {
			s += (openSites[i] - mean)*(openSites[i] - mean); 
		}
		return Math.sqrt(s/(trials-1));
	}

	public double confidenceLo() {
		return min;
	}

	public double confidenceHi() {
		return max;
	}

	public static void main(String[] args) {

		final double CONFIDENCE_95 = 1.96;
		
		int n = Integer.parseInt(args[0]);
		int t = Integer.parseInt(args[1]);
//		int n = 200;
//		int t = 100;

		PercolationStats ps = new PercolationStats(n, t);
		
		System.out.printf("mean\t\t\t = %f\n", ps.mean());
		System.out.printf("stddev\t\t\t = %f\n", ps.stddev());
		System.out.printf("95%% confidence interval\t = [%f, %f]", ps.mean()-((CONFIDENCE_95*ps.stddev())/Math.sqrt(t)), ps.mean()+((CONFIDENCE_95*ps.stddev())/Math.sqrt(t)));
	}

}
