package balance;

import java.util.*;

public class Transaction {
  private float amount;
  private Date date;
  private String description;

  public Transaction() {
    this(0f, Calendar.getInstance().getTime(), "Mock description");
  }

  public Transaction(
    float amount,
    Date date,
    String description
  ) {
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
}
