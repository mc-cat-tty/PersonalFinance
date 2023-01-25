package print;

import java.awt.*;
import java.awt.print.*;

import model.core.BalanceModelManager;
import tunable.CommonDateFormats;
import tunable.CommonFonts;

public class PrintableBalance implements Printable {
  private final static int PADDING = 5;
  private int slice;
  private int firstColX;
  private int secondColX;
  private int thirdColX;
  private int currentY;
  private float balance;

  public int print(Graphics graphics, PageFormat pageFormat, int page) throws PrinterException {
    if (page > 0) {
      return NO_SUCH_PAGE;
    }
    
    final var g2d = (Graphics2D) graphics;
    slice = (int) (pageFormat.getImageableWidth()/10f);
    firstColX = 0;
    secondColX = slice*3;
    thirdColX = secondColX + slice*2;
    currentY = 20;
    balance = 0;

    g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
    g2d.setColor(Color.BLACK);

    drawGrid(g2d, (int) pageFormat.getImageableWidth(), (int) pageFormat.getImageableHeight());

    drawHeader(g2d);

    g2d.setFont(CommonFonts.TEXT_NORMAL.getFont().deriveFont(13f));
    for (final var transaction : BalanceModelManager.getInstance().getTransactions()){
      g2d.drawString(String.format("%06.02f ", transaction.getAmount()), firstColX + PADDING, currentY);
      
      g2d.drawString(CommonDateFormats.EU_DATE_FORMAT_SHORT.getFormatter().format(
        transaction.getDate()
      ), secondColX + PADDING, currentY);
      
      g2d.drawString(transaction.getDescription(), thirdColX + PADDING, currentY);
      
      balance += transaction.getAmount();
      currentY += 20;
    }

    drawFooter(g2d, (int) pageFormat.getImageableWidth(), (int) pageFormat.getImageableHeight());

    return PAGE_EXISTS;
  }
  

  private void drawGrid(Graphics2D g2d, int w, int h) {
    g2d.drawRect(0, 0, w, h);
    g2d.drawLine(secondColX, 0, secondColX, h);
    g2d.drawLine(thirdColX, 0, thirdColX, h);
  }

  private void drawHeader(Graphics g2d) {
    g2d.setFont(CommonFonts.TEXT_MEDIUM_WEIGHT.getFont().deriveFont(20f));
    g2d.drawString("AMOUNT", firstColX + PADDING, currentY);
    g2d.drawString("DATE", secondColX + PADDING, currentY);
    g2d.drawString("DESCRIPTION", thirdColX + PADDING, currentY);
    currentY += 27;
  }

  private void drawFooter(Graphics2D g2d, int w, int h) {
    g2d.drawLine(0, currentY, w, currentY);
    g2d.setFont(CommonFonts.TEXT_MEDIUM_WEIGHT.getFont().deriveFont(13f));
    g2d.drawString(String.format("%06.02f ", balance), firstColX + PADDING, currentY + 20);
  }
}
