import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.math.BigInteger;

/*
 *  Desc: This class generates primes, twin primes, and hexagon crosses using BigInteger data types.
 */
public class Primes {

	// Pair class implementation.
	private class Pair<T> implements Comparable<Pair<T>>{
	
		T X; 
		T Y; 
		/**
		 * 
		 * @return the value of X 
		 */
		T getxVal() {
			return X;
		}
		/**
		 * 
		 * @return the value of y 
		 */
		public T getyVal() {
			return Y;
		}
		
		/**
		 * Pair Constructor. X and Y must be of same type. 
		 * @param x
		 * @param y
		 */
		public Pair(T x, T y){
			this.X = x; 
			this.Y = y;
		}
		/**
		 * 
		 * @return x as string 
		 */
		public String xString() {
			return this.X.toString();
		}
		/**
		 * 
		 * @return y as string 
		 */
		public String yString() {
			return this.Y.toString();
		}
		/** 
		 * return x and y as strings in format (X,Y) 
		 */
		public String toString() { 
			return "(" + this.X.toString() + "," + this.Y.toString() + ")";
		}
		/**
		 *  Only works if values have a compareTo method  
		 */
		@Override
		public int compareTo(Pair<T> Y) {
		    return ((BigInteger) X).compareTo((BigInteger) Y.getxVal());
		}

	}

	// Member variables for containing out lists of integers, twin primes, hexagon
	// crosses, and the pairs of twin primes that make up the hex crosses.
	// List of primes, twin primes, hexagon crosses have an associated set. These are
	// used to add items to a list without duplicates. 
	private List<BigInteger> Primes = new ArrayList<BigInteger>();
	private Set<BigInteger> PrimeSet = new HashSet<BigInteger>();
	private List<Pair<BigInteger>> TwinPrimes = new ArrayList<Pair<BigInteger>>();
	private Set<Pair<BigInteger>> TwinPrimeSet = new HashSet<Pair<BigInteger>>();
	private List<Pair<BigInteger>> HexagonCross = new ArrayList<Pair<BigInteger>>();
	private Set<Pair<BigInteger>> HexagonCrossSet = new HashSet<Pair<BigInteger>>();
	private HashMap<BigInteger,Pair<BigInteger>> HexMap = new HashMap<>();



	// Add a prime to the prime list if and only if it is not already in the set. 
	// (ignore duplicates)
	/**
	 * addPrime does not update the list. This method should only be called if the 
	 * list is updated from the set  
	 * @param x [PrimeNumber]
	 */
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
		for (BigInteger i : Primes) {
			System.out.println(i);
		}
	
		System.out.println("Total Number of primes is " + Primes.size());
	}

	// Output the twin prime list. Each twin prime should be on a separate line with
	// a comma separating them, and the total number of twin primes should be on the
	// following line.
	public void printTwins() {
		for (Pair<BigInteger> i : TwinPrimes) {
			System.out.println(i.toString());
		}
		System.out.println("Total Number of twin primes is " + TwinPrimes.size());
	}

	// Output the hexagon cross list. Each should be on a separate line listing the
	// two twin primes and the corresponding hexagon cross, with the total number on
	// the following line.
	public void printHexes() {
		// 	private HashMap<Pair<BigInteger>,Pair<Pair<BigInteger>>> HexMap = new HashMap<>();
		for( Pair<BigInteger> i: HexagonCross) {
			Pair<BigInteger> pair1 = HexMap.get(i.getxVal());
			Pair<BigInteger> pair2 = HexMap.get(i.getyVal());
			System.out.println("Prime Pairs: " + pair1.toString() + "," + pair2.toString() + " separated by " + i.xString() + "," + i.yString());
		}
		System.out.println("Total Number of hex primes is " + HexagonCrossSet.size());

	}

	BigInteger four = new BigInteger("4");
	BigInteger three = new BigInteger("3");
	BigInteger six = new BigInteger("6");
	/**
	 * 
	 * @param candidate: Biginteger whose primality we are testing 
	 * @return a boolean representing if the number is prime or not 
	 */
	private boolean isPrime(BigInteger candidate) {
		if(candidate.compareTo(four) == -1) {
			if(candidate.compareTo(BigInteger.ONE) == 1) {
				return true;
			}
			else {
				return false;
			}
		}
		else if(candidate.mod(BigInteger.TWO).equals(BigInteger.ZERO) || candidate.mod(three).equals(BigInteger.ZERO) ){
			return false; 
		}
		BigInteger i = new BigInteger("5");
		while(i.multiply(i).compareTo(candidate) <= 0) {
			if(candidate.mod(i).equals(BigInteger.ZERO) || candidate.mod(i.add(BigInteger.TWO)).equals(BigInteger.ZERO)) {
				return false;
			}
			i = i.add(six);
		}
		return true;
	}
	// Generate and store a list of primes.
	/**
	 * Generates primes from 1 to variable count 
	 * @param count
	 */
	public void generatePrimes(int count) {
		// Finds the prime ones and adds them to the primes set. 
		int i = 0; 
		BigInteger one = new BigInteger("1");
		BigInteger k = new BigInteger("1"); 
		while(i < count) {
			if(isPrime(k)) {  
				PrimeSet.add(k);
				i++; 
			}
			k = k.add(one);
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
	/**
	 * Values Changed: HexagonCross, HexMap 
	 * This function overwrites the primes currently generated. 
	 * Must call generateTwinPrimes() before calling this method for it to work properly. 
	 */
	public void generateHexPrimes() {
		BigInteger one = new BigInteger("1");
		BigInteger two = new BigInteger("2");
		
		Set <BigInteger> candidates = new HashSet<BigInteger>();
		HashMap<BigInteger,Pair<BigInteger>> map = new HashMap<>();
		// Generate hex primes candidates: all values between the twin primes 
		for(Pair<BigInteger> i : TwinPrimes) {
			candidates.add(i.getxVal().add(one));
			map.put((i.getxVal().add(one)),i);
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
        HexMap = map;
        
	}
}
