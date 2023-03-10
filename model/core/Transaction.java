package model.core;

import java.io.Serializable;
import java.util.*;


/**
 * Representation of incoming or outcoming transaction.
 */
public class Transaction implements Comparable<Transaction>, Cloneable, Serializable {
  private static int GLOBAL_IDX = 0;  /** Global transaction index. Used to compare insert orders and equalty */
  private int idx;
  private float amount;
  private Date date;
  private String description;

  public Transaction() {
    this(0f, new Date(), "Mock description");
  }

  /**
   * Constructor.
   * @param amount incoming/outcoming money amount
   * @param date transaction date
   * @param description short description
   */
  public Transaction(
    float amount,
    Date date,
    String description
  ) {
    this.idx = GLOBAL_IDX++;
    this.amount = amount;
    this.date = date;
    this.description = description;
  }

  public float getAmount() {
    return amount;
  }

  public Date getDate() {
    return date;
  }
 
  public String getDescription() {
    return description;
  }
 
  public void setAmount(float amount) {
    this.amount = amount;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override public String toString() {
    return String.format(
      "%1$d %2$f %3$td/%3$tm/%3$tY %4$s...",
      idx,
      amount,
      date,
      description.length() > 30 ? description.subSequence(0, 30) : description
    );
  }

  /**
   * Transactions a > b if a's date comes after b's.
   * If the two dates are equal, the unique idx is used.
   */
  @Override public int compareTo(Transaction other) {
    final var dateComparison = other.date.compareTo(this.date);

    if (dateComparison != 0) {
      return dateComparison;
    }

    return other.idx - this.idx;
  }

  @Override public boolean equals(Object obj) {
    if (this == obj)
      return true;

    if (obj == null)
      return false;
    
    if (getClass() != obj.getClass())
      return false;
    

    Transaction other = (Transaction) obj;

    if (this.idx == other.idx) {
      return true;
    }

    return false;
  }

  @Override public Object clone() {
    final var clone = new Transaction();
    clone.idx = this.idx;
    clone.setAmount(amount);
    clone.setDate(date);
    clone.setDescription(description);
    return clone;
  }
}
