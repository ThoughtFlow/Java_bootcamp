package lab04;

public class CheckingAccount {

	private static final String ACCOUNT_TYPE = "Checking";
	private static int nextNumber = 0;

	private final Customer customer;
	private final int number;
	private double balance = 0;

	public CheckingAccount(Customer customer, double initialBalance) {
		number = ++nextNumber;
		this.customer = customer;
		balance = initialBalance;
	}
	
	public CheckingAccount(Customer customer) {
		this(customer, 0);
	}
	
	public double credit(double amountToCredit) {
		balance += amountToCredit;
		return balance;
	}
	
	public double debit(double amountToDebit) {
		balance -= amountToDebit;
		return balance;
	}
	
	public void transferTo(CheckingAccount targetAccount, double amount) {
		debit(amount);
		targetAccount.credit(amount);
	}
	
	public int getNumber() {
		return number;
	}

	public double getBalance() {
		return balance;
	}
	
	public String getAccountType() {
		return ACCOUNT_TYPE;
	}
	
	public void printAccount() {
		customer.printAccount();
		System.out.println("Account: " + number);
		System.out.println("Type: " + getAccountType());
		System.out.println("Balance: " + balance);
	}
}
