package ma.salma.bankaccount_management_backend.services;

public class CurrentAccountStrategy implements AccountStrategy {
    @Override
    public double calculateInterest(double balance) {
        return 0; // No interest for current accounts
    }
}
