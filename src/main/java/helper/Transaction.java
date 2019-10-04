package helper;

import java.time.LocalDate;

public class Transaction {
	public final double amount;

	private LocalDate transactionDate;

	public Transaction(double amount) {
		this.amount = amount;
		this.transactionDate = LocalDate.now();
	}

	public LocalDate getTranscationDate() {
		return transactionDate;
	}

}
