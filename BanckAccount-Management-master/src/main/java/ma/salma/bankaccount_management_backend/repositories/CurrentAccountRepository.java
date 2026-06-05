package ma.salma.bankaccount_management_backend.repositories;

import org.springframework.data.repository.Repository;

import ma.salma.bankaccount_management_backend.entities.CurrentAccount;

interface CurrentAccountRepository extends Repository<CurrentAccount, String> {
}
