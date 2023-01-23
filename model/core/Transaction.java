package model.core;

import java.util.*;

public class Transaction implements Comparable<Transaction> {
  private static int GLOBAL_IDX = 0;  /** Global transaction index. Used to compare insert orders and equalty */
  private int idx;
  private float amount;
  private Date date;
  private String description;

  public Transaction() {
    this(0f, new Date(), "Mock description");
  }

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
      description.substring(0, 30)
    );
  }

  @Override public int compareTo(Transaction other) {
    final var dateComparison = other.date.compareTo(this.date);

    if (dateComparison != 0) {
      return dateComparison;
    }

    return other.idx - this.idx;
  }
}
