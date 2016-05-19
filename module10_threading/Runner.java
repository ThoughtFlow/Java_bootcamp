package module10_threading;

public class Runner implements Runnable {
	private final int maxCount;
	private final Counter counter;

	public Runner(int maxCount, Counter counter) {
		this.maxCount = maxCount;
		this.counter = counter;
	}

	public void run() {
		for (int i = 0; i < this.maxCount; i++) {
			int currentCount;
			synchronized (this.counter) {
				currentCount = this.counter.getCount();
				currentCount++;
				this.counter.setCount(currentCount);
			}
			System.out.println(Thread.currentThread().getName() + " at count " + currentCount);
		}
	}
	
	private static class Counter {
		private int count = 0;

		public void setCount(int count) {
			this.count = count;
		}

		public int getCount() {
			return this.count;
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Counter counter = new Counter();
		Thread t1 = new Thread(new Runner(10, counter));
		Thread t2 = new Thread(new Runner(10, counter));
		Thread t3 = new Thread(new Runner(10, counter));
		t1.start();
		t2.start();
		t3.start();
		t1.join();
		t2.join();
		t3.join();
	}
}
