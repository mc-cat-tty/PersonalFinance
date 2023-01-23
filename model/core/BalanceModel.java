package model.core;

import java.util.*;

/**
 * Balance model keeps a transaction set ordered by date (in case of equal date, the creation order is used).
 */
public class BalanceModel {
  private final SortedSet<Transaction> transactions;

  protected BalanceModel() {
    transactions = Collections.synchronizedSortedSet(new TreeSet<>());
  }

  public Collection<Transaction> getTransactions() {
    return transactions;
  }

  public void addTransaction(Transaction transaction) throws ModelEditFailedException{
    synchronized (transactions) {
      if (!transactions.add(transaction)) {
        throw new ModelEditFailedException("Failed add of " + transaction);
      }
    }
  }

  public void deleteTransaction(Transaction transaction) throws ModelEditFailedException {
    synchronized (transactions) {
      if (!transactions.remove(transaction)) {
        throw new ModelEditFailedException("Failed remove of " + transaction);
      }
    }
  }

  public void editTransaction(
    Transaction transaction,
    float newAmount,
    Date newDate,
    String newDescription
  ) throws ModelEditFailedException {
    synchronized (transactions) {
      final var searchResult = searchTransaction(transaction);
      
      if (!searchResult.isPresent()) {
        throw new ModelEditFailedException("Not found transaction " + transaction);
      }
  
      final var newTransaction = searchResult.get();
      newTransaction.setAmount(newAmount);
      newTransaction.setDate(newDate);
      newTransaction.setDescription(newDescription);

      deleteTransaction(transaction);
      addTransaction(newTransaction);
    }
  }

  public Optional<Transaction> searchTransaction(Transaction transaction) {
    return transactions
      .stream()
      .filter(t -> t.equals(transaction))
      .findFirst();
  }

  public Collection<Transaction> searchTransactionByDate(Date startDate, Date endDate) {
    return transactions
      .stream()
      .filter(t -> t.getDate().after(startDate) && t.getDate().before(endDate))
      .collect(TreeSet::new, TreeSet::add, (x, y) -> x.addAll(y));
  }

  public Collection<Transaction> searchTransactionByDescription(String description) {
    return transactions
      .stream()
      .filter(t -> t.getDescription().equals(description))
      .collect(TreeSet::new, TreeSet::add, (x, y) -> x.addAll(y));
  }
}
