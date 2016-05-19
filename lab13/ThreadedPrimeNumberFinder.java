package lab13;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ThreadedPrimeNumberFinder {

	private static final int K_SLICES = 5;

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		List<PrimeFinder> primeFinders = new ArrayList<>(K_SLICES);
		List<Thread> threads = new ArrayList<>(K_SLICES);

		for (int index = 0; index < K_SLICES; ++index) {
			primeFinders.add(new PrimeFinder(index * 1000, index * 1000 + 999));
		}

		for (Runnable nextRunnable : primeFinders) {
			Thread nextThread = new Thread(nextRunnable);
			threads.add(nextThread);
			nextThread.start();
		}

		for (Thread nextThread : threads) {
			nextThread.join();			
		}
		
		for (PrimeFinder nextPrimeFinder : primeFinders) {
			System.out.println("Primes found: " + nextPrimeFinder.getResult());			
		}
	}

	private static class PrimeFinder implements Runnable {

		private final int startRange;
		private final int endRange;

		private int primesFound = 0;
		private String result;

		public PrimeFinder(int startRange, int endRange) {
			this.startRange = startRange;
			this.endRange = endRange;
		}

		@Override
		public void run() {
			for (int primeCandidate = startRange; primeCandidate <= endRange; ++primeCandidate) {
				primesFound += isPrime(primeCandidate) ? 1 : 0;
			} 

			result = Thread.currentThread().getName() +": Range " + startRange + " to " + endRange + ": " + primesFound;
		}
		
		public String getResult() {
			return result;
		}

		private boolean isPrime(int primeCandidate) {
			boolean isPrimeFound = true;

			if (primeCandidate > 2) {
				for (int testValue = 2; testValue <= Math.sqrt(primeCandidate); ++testValue) {
					if (primeCandidate % testValue == 0) {
						isPrimeFound = false;
						break;
					}
				}
			}
			else if (primeCandidate != 2) {
				isPrimeFound = false;
			}

			return isPrimeFound;
		}
	}
}
