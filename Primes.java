import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.math.BigInteger;

/*
 *  Desc: This class generates primes, twin primes, and hexagon crosses using BigInteger data types.
 */
public class Primes {

	// Pair class implementation.
//	private class Pair<T> implements Comparable<Pair<T>>;

	private class Pair<T> implements Comparable<Pair<T>>{
	
		T X; 
		T Y; 
		/**
		 *  Set method 
		 * @param x
		 * @param y
		 */
		public T getxVal() {
			return X;
		}
		public T getyVal() {
			return Y;
		}
		
		public Pair(T x, T y){
			this.X = x; 
			this.Y = y;
		}
		public String toString() { 
			return "(" + this.X.toString() + "," + this.Y.toString() + ")";
		}
		/**
		 *  Only works for type ints 
		 */
		@Override
		public int compareTo(Pair<T> Y) {
		    return ((BigInteger) X).compareTo((BigInteger) Y.getxVal());
		}

	}

	// Member variables for containing out lists of integers, twin primes, hexagon
	// crosses, and the pairs of twin primes that make up the hex crosses.
	private List<BigInteger> Primes = new ArrayList<BigInteger>();
	private Set<BigInteger> PrimeSet = new HashSet<BigInteger>();
	private List<Pair<BigInteger>> TwinPrimes = new ArrayList<Pair<BigInteger>>();
	private Set<Pair<BigInteger>> TwinPrimeSet = new HashSet<Pair<BigInteger>>();
	private List<Pair<BigInteger>> HexagonCross = new ArrayList<Pair<BigInteger>>();
	private Set<Pair<BigInteger>> HexagonCrossSet = new HashSet<Pair<BigInteger>>();

//	private List<BigInteger> HexagonCrosses = new ArrayList<BigInteger>();
//	private int numberOfPrimes = 0;

	// Add a prime to the prime list if and only if it is not already in the list.
	// (ignore duplicates)
	public void addPrime(BigInteger x) {
		PrimeSet.add(x);
		if(PrimeSet.contains(x))
			return;
		Primes.add(x);
	}

	// Output the prime list. Each prime should be on a separate line and the total
	// number of primes should be on the following line.
	/**
	 *  Prints the prime numbers in sorted order.
	 */
	public void printPrimes() {
		// Sorting the primes. Unnecessary but I feel like the output should be in sorted order.
		for (BigInteger i : Primes) {
			System.out.println(i);
		}
	
		System.out.println("Total Number of primes is " + Primes.size());
	}

	// Output the twin prime list. Each twin prime should be on a separate line with
	// a comma separating them, and the total number of twin primes should be on the
	// following line.
	public void printTwins() {
		// Sorting the primes. Unnecessary but I feel like the output should be in sorted order.
		for (Pair<BigInteger> i : TwinPrimes) {
			System.out.println(i.toString());
		}
		System.out.println("Total Number of twin primes is " + TwinPrimes.size());
	}

	// Output the hexagon cross list. Each should be on a separate line listing the
	// two twin primes and the corresponding hexagon cross, with the total number on
	// the following line.
	public void printHexes() {
		for( Pair<BigInteger> i: HexagonCross) {
			System.out.println(i.toString());
		}
	}

	// Generate and store a list of primes.
	/**
	 * Generates primes from 1 to variable count 
	 * @param count
	 */
	public void generatePrimes(int count) {
		// Generate primes using the SieveOfEratosthenes algorithm
		int n = count; 
		boolean arr[] = new boolean[n+1]; 
		Arrays.fill(arr, Boolean.TRUE);
		
		for(int i = 2; i < Math.ceil(Math.sqrt(n+1)); i++) {
			if(arr[i] == true) {
				for(int j = i*i; j <= n; j += i ) {
					arr[j] = false; 
				}
			}
		}
		// Finds the prime ones and adds them to the primes set. 
		for(int k = 2; k < n; k++) {
			if(arr[k] == true) {
				PrimeSet.add(BigInteger.valueOf(k));
			}
		}
        ArrayList<BigInteger> sortedList = new ArrayList<BigInteger>(PrimeSet); 
        Collections.sort(sortedList); 
        this.Primes = sortedList;
	}

	// Generate and store a list of twin primes.
	public void generateTwinPrimes() {
		if(Primes.size() < 2) {
			return; 
		}
		BigInteger a = Primes.get(0);
		BigInteger b = Primes.get(1);
		BigInteger two = new BigInteger("2");
		for(int i = 0; i < Primes.size()-1; i++) {
			a = Primes.get(i);
			b = Primes.get(i+1);
			if(b.equals(a.add(two))){
				Pair<BigInteger> temp = new Pair<BigInteger>(a,b);
				TwinPrimeSet.add(temp);
			}

		}
		// Update generatedTwinPrimes with new list 
		ArrayList<Pair<BigInteger>> sortedList = new ArrayList<Pair<BigInteger>>(TwinPrimeSet); 
        Collections.sort(sortedList); 
        this.TwinPrimes = sortedList;
	}

	// Generate and store the hexagon crosses, along with the two twin primes that
	// generate the hexagon cross.
	public void generateHexPrimes() {
		BigInteger one = new BigInteger("1");
		BigInteger two = new BigInteger("2");
		
		Set <BigInteger> candidates = new HashSet<BigInteger>();
		// Generate hex primes candidates 
		for(Pair<BigInteger> i : TwinPrimes) {
			candidates.add(i.getxVal().add(one));
		}
		for(BigInteger j : candidates) {
			BigInteger k = j.multiply(two);
 			if(candidates.contains(k)) {
 				HexagonCrossSet.add(new Pair<BigInteger>(j,k));
 			}
		}
        ArrayList<Pair<BigInteger>> sortedList = new ArrayList<Pair<BigInteger>>(HexagonCrossSet); 
        Collections.sort(sortedList); 
        HexagonCross = sortedList;
	}
}
