package ui.utils;

import java.awt.*;
import javax.swing.*;

import tunable.CommonColors;
import tunable.CommonFonts;
import ui.components.containers.RoundedCornerBorder;


public class FlatConfirmDialogUtiliy {
  public enum Choices {CLOSED_CHOICE, OK_CHOICE, CANCEL_CHOICE};
  private final JOptionPane optionPane;
  private final JDialog dialog;

  public FlatConfirmDialogUtiliy(
    Component owner,
    String title,
    String message
  ) {
    UIManager.put("OptionPane.messageFont", CommonFonts.TEXT_NORMAL.getFont().deriveFont(22f));
    UIManager.put("OptionPane.messageForeground", CommonColors.TEXT.getColor());
    UIManager.put("OptionPane.background", CommonColors.BACKGROUND.getColor());
    UIManager.put("Panel.background", CommonColors.BACKGROUND.getColor());

    UIManager.put("Button.border", new RoundedCornerBorder(20, 0));
    UIManager.put("Button.font", CommonFonts.TEXT_NORMAL.getFont().deriveFont(22f));
    UIManager.put("Button.background", CommonColors.BUTTON_SECONDARY.getColor());
    UIManager.put("Button.foreground", CommonColors.TEXT.getColor());
    UIManager.put("Button.focus", CommonColors.BUTTON_SECONDARY.getColor());
    UIManager.put("Button.select", CommonColors.BUTTON_SECONDARY.getColor().darker());
    
    
    optionPane = new JOptionPane(
      message,
      JOptionPane.PLAIN_MESSAGE,
      JOptionPane.YES_NO_OPTION,
      null,
      null,
      JOptionPane.YES_OPTION
    );

    dialog = optionPane.createDialog(owner, title);
  }

  public JDialog getDialog() {
    return dialog;
  }

  public JOptionPane getOptionPane() {
    return optionPane;
  }

  public Choices getChoice() {
    final var selectedObj = getOptionPane().getValue();
    
    if (selectedObj == null || !(selectedObj instanceof Integer)) {
      return Choices.CLOSED_CHOICE;
    }

    final var selectedVal = ((Integer) selectedObj).intValue();

    return switch(selectedVal) {
      case 0 -> Choices.OK_CHOICE;
      case 1 -> Choices.CANCEL_CHOICE;
      default -> Choices.CLOSED_CHOICE;
    };
  }
}
