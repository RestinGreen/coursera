public class Percolation {

	private int n;
	private int[][] parents;
	private int nrOpenSites;
	private int[][] sz;

	public Percolation(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		this.n = n;
		nrOpenSites = 0;
		parents = new int[n + 2][n + 2];
		sz = new int[n + 2][n + 2];
		parents[n + 1][1] = -convert(n + 1, 1); // virtual top
		parents[n + 1][2] = -convert(n + 1, 2); // virtual bot

		for (int i = 0; i <= n + 1; i++) {
			for (int j = 0; j <= n + 1; j++) {
				sz[i][j] = 1;
			}
		}

		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				parents[i][j] = (i - 1) * n + j;
//				System.out.printf("%-2d ", parents[i][j]);
			}
//			System.out.println();
		}
	}

//	public void print() {
//		System.out.printf("%9d\n", parents[n + 1][1]);
//		for (int i = 1; i <= n; i++) {
//			for (int j = 1; j <= n; j++) {
//				System.out.printf("%3d ", parents[i][j]);
//			}
//			System.out.print("\t");
//			for (int j = 1; j <= n; j++) {
//				System.out.printf("%3s", parents[i][j] < 0 ? "1" : "");
//			}
//			System.out.println();
//		}
//		System.out.printf("%9d\n", parents[n + 1][2]);
//	}

	public void open(int row, int col) {
		if (row < 1 || row > n || col < 1 || col > n) {
			throw new IllegalArgumentException();
		}
		parents[row][col] *= -1;
		nrOpenSites++;
		int i, j;

		if (row == 1) {
			i = root(row, col);
			j = root(n + 1, 1);
			union(i, j);
		}
		if (row == n) {
			i = root(row, col);
			j = root(n + 1, 2);
			union(i, j);
		}

		if (row - 1 >= 1 && isOpen(row - 1, col)) {
			i = root(row, col);
			j = root(row - 1, col);
			union(i, j);
		}
		if (row + 1 <= n && isOpen(row + 1, col)) {
			i = root(row, col);
			j = root(row + 1, col);
			union(i, j);
		}
		if (col - 1 >= 1 && isOpen(row, col - 1)) {
			i = root(row, col);
			j = root(row, col - 1);
			union(i, j);
		}
		if (col + 1 <= n && isOpen(row, col + 1)) {
			i = root(row, col);
			j = root(row, col + 1);
			union(i, j);
		}

	}

	private void union(int i, int j) {
		if (i == j) {
			return;
		}
		int cri = convertRow(i);
		int cci = convertCol(i);
		int crj = convertRow(j);
		int ccj = convertCol(j);
		if (sz[cri][cci] < sz[crj][ccj]) {
			parents[cri][cci] = j;
			sz[crj][ccj] += sz[cri][cci];
		} else {
			parents[crj][ccj] = i;
			sz[cri][cci] += sz[crj][ccj];
		}
	}

	public boolean isOpen(int row, int col) {
		if (row < 1 || row > n || col < 1 || col > n) {
			throw new IllegalArgumentException();
		}
		return parents[row][col] < 0;
	}

	public boolean isFull(int row, int col) {
		if (row < 1 || row > n || col < 1 || col > n) {
			throw new IllegalArgumentException();
		}
		return root(n+1, 1) == root(row, col);
	}

	public int numberOfOpenSites() {
		return nrOpenSites;
	}

	private int root(int row, int col) {
		int i = -convert(row, col);
		int convertRow = convertRow(i);
		int convertCol = convertCol(i);
		while (i != parents[convertRow][convertCol]) {

			parents[convertRow][convertCol] = parents[convertRow(parents[convertRow][convertCol])][convertCol(
					parents[convertRow][convertCol])];
			i = parents[convertRow][convertCol];
			convertRow = convertRow(i);
			convertCol = convertCol(i);
		}
		return i;
	}

	private int convert(int row, int col) {
		return (row - 1) * n + col;
	}

	private int convertRow(int i) {
		return (Math.abs(i) - 1) / n + 1;
	}

	private int convertCol(int i) {
		return (Math.abs(i) - 1) % n + 1;
	}

	public boolean percolates() {

		return root(n + 1, 1) == root(n + 1, 2);
	}

//	public static void main(String[] args) {
//
//		int n = 5;
//
//		Percolation p = new Percolation(n);
//
//		while (!p.percolates()) {
//			int randRow = StdRandom.uniformInt(n) + 1;
//			int randCol = StdRandom.uniformInt(n) + 1;
//			work(randRow, randCol, p);
//		}
//		System.out.println(p.nrOpenSites);
//
//	}
//
//	public static void work(int row, int col, Percolation p) {
//
//		System.out.println("------------------------");
//		System.out.printf("rRow: %-2d , rCol: %-2d\n", row, col);
//		if (!p.isOpen(row, col)) {
//			p.open(row, col);
//			p.print();
//		}
//	}

}
