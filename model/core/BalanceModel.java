package model.core;

import java.util.*;


/**
 * Balance model keeps a transaction set ordered by date (in case of equal date, the creation order is used).
 */
public class BalanceModel implements IReadableModel, IWritableModel {
  private final SortedSet<Transaction> transactions;

  protected BalanceModel() {
    transactions = Collections.synchronizedSortedSet(new TreeSet<>());
  }

  public Collection<Transaction> getTransactions() {
    synchronized (transactions) {
      return transactions;
    }
  }

  public void setTransactions(Collection<Transaction> transactions) {
    synchronized (transactions) {
      this.transactions.clear();
      this.transactions.addAll(transactions);
    }
  }

  /**
   * Add a transaction to the set
   * @param transaction input transaction
   * @throws ModelEditFailedException if add operation doesn't go well
   */
  public void addTransaction(Transaction transaction) throws ModelEditFailedException {
    synchronized (transactions) {
      if (!transactions.add(transaction)) {
        throw new ModelEditFailedException("Failed add of " + transaction);
      }
    }
  }

  /**
   * Removes a transaction from the set
   * @param transaction input transaction
   * @throws ModelEditFailedException if add operation doesn't go well
   */
  public void deleteTransaction(Transaction transaction) throws ModelEditFailedException {
    synchronized (transactions) {
      if (!transactions.remove(transaction)) {
        throw new ModelEditFailedException("Failed remove of " + transaction);
      }
    }
  }

  /**
   * Edit a transaction all fields at once.
   * @param transaction transaction to be edited
   * @param newAmount new amount value
   * @param newDate new date
   * @param newDescription new description
   * @return the edited transaction. The internal index is not edited.
   * @throws ModelEditFailedException
   */
  public Transaction editTransaction(
    Transaction transaction,
    float newAmount,
    Date newDate,
    String newDescription
  ) throws ModelEditFailedException {
    final var searchResult = searchTransaction(transaction);
   
    synchronized (transactions) {
      
      if (!searchResult.isPresent()) {
        throw new ModelEditFailedException("Not found transaction " + transaction);
      }
  
      final var newTransaction = (Transaction) searchResult.get().clone();
      newTransaction.setAmount(newAmount);
      newTransaction.setDate(newDate);
      newTransaction.setDescription(newDescription);

      deleteTransaction(transaction);
      addTransaction(newTransaction);
      
      return newTransaction;
    }
  }

  /**
   * Search a transaction in the set. Linear cost.
   * @param transaction input transaction
   * @return
   */
  public Optional<Transaction> searchTransaction(Transaction transaction) {
    synchronized (transactions) {
      return transactions
        .stream()
        .filter(t -> t.equals(transaction))
        .findFirst();
    }
  }

  /**
   * Apply a reduction function to the set and return the result of the reduction.
   * @param startDate start filter date
   * @param endDate end filter date
   * @return a transaction subset
   */
  public Collection<Transaction> filterTransactionsByDate(Date startDate, Date endDate) {
    synchronized (transactions) {
      return transactions
        .stream()
        .filter(t -> t.getDate().compareTo(startDate) >= 0 && t.getDate().compareTo(endDate) <= 0)
        .collect(TreeSet::new, TreeSet::add, (x, y) -> x.addAll(y));
    }
  }

  /**
   * Apply a reduction function to the set and return the result.
   * @param description input description.
   * @return a transaction subset
   */
  public Collection<Transaction> filterTransactionsByDescription(String description) {
    synchronized (transactions) {
      return transactions
        .stream()
        .filter(t -> t.getDescription().toLowerCase().contains(description.toLowerCase()))
        .collect(TreeSet::new, TreeSet::add, (x, y) -> x.addAll(y));
    }
  }

  /**
   * Combines filterTransactionsByDate and filterTransactionsByDescription
   * Logical and between conditions.
   */
  public Collection<Transaction> filterTransactions(String description, Date startDate, Date endDate) {
    synchronized (transactions) {
      return transactions
        .stream()
        .filter(
          t -> t.getDescription().equals(description)
          && t.getDate().after(startDate)
          && t.getDate().before(endDate)
        )
        .collect(TreeSet::new, TreeSet::add, (x, y) -> x.addAll(y));
    }
  }
}
