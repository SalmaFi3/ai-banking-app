package ma.salma.bankaccount_management_backend.services;

public class SavingAccountStrategy implements AccountStrategy {
    private double interestRate;

    public SavingAccountStrategy(double interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public double calculateInterest(double balance) {
        return balance * interestRate / 100;
    }
}
