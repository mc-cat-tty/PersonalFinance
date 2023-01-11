package ui.components.containers;

import ui.components.buttons.*;
import ui.components.text.*;
import ui.core.*;
import tunable.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.time.Period;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class SearchPanel extends JPanel implements IComponent {
  public enum SearchPeriod {
    DAY,
    WEEK,
    YEAR,
    CUSTOM;

    public String toString() {
      final var name = name();
      return
        name.substring(0, 1).toUpperCase() +
        name.substring(1).toLowerCase();
    }

    public static String[] getStrings() {
      return
        Stream.of(SearchPeriod.values())
        .map(SearchPeriod::toString)
        .toArray(String[]::new);
    }
  }

  private final RoundedTextField searchField;
  private final RoundedComboBox perdiodSelector;
  private final RoundedTextField startDateField;
  private final RoundedTextField endDateField;
  private final RoundedButton searchButton;
  
  public SearchPanel() {
    super(
      new FlowLayout(
        FlowLayout.CENTER,
        CommonPaddings.SEARCH_PANEL_HORIZONTAL_PADDING.getPadding(),
        0
      )
    );

    searchField = new RoundedTextField(
      "Search description",
      CommonColors.TEXTBOX.getColor(),
      CommonColors.TEXTBOX_INVALID.getColor(),
      ColorOpaqueBuilder.build(CommonColors.TEXT.getColor(), 0.5f),
      CommonFonts.TEXT_NORMAL.getFont().deriveFont(25f),
      CommonDimensions.SEARCH_TEXT_FIELD.getDimension(),
      25
    );

    perdiodSelector = new RoundedComboBox(
      SearchPeriod.getStrings(),
      CommonColors.TEXTBOX.getColor(),
      CommonColors.TEXT.getColor(),
      CommonFonts.TEXT_NORMAL.getFont().deriveFont(25f),
      CommonDimensions.PERIOD_SELECTOR.getDimension(),
      25
    );

    startDateField = new RoundedTextField(
      "00/01/2022",
      CommonColors.TEXTBOX.getColor(),
      CommonColors.TEXTBOX_INVALID.getColor(),
      ColorOpaqueBuilder.build(CommonColors.TEXT.getColor(), 0.5f),
      CommonFonts.TEXT_NORMAL.getFont().deriveFont(25f),
      CommonDimensions.DATE_START_TEXT_FIELD.getDimension(),
      25
    ).setMaxLengthMonadic(10)
    .setInputFilterMonadic(c -> Character.isDigit(c) || c == '/')
    .setInputValidatorMonadic(text -> text.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}") || text.equals(""));
  
    endDateField = new RoundedTextField(
      "00/01/2023",
      CommonColors.TEXTBOX.getColor(),
      CommonColors.TEXTBOX_INVALID.getColor(),
      ColorOpaqueBuilder.build(CommonColors.TEXT.getColor(), 0.5f),
      CommonFonts.TEXT_NORMAL.getFont().deriveFont(25f),
      CommonDimensions.DATE_END_TEXT_FIELD.getDimension(),
      25
    ).setMaxLengthMonadic(10)
    .setInputFilterMonadic(c -> Character.isDigit(c) || c == '/')
    .setInputValidatorMonadic(text -> text.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}") || text.equals(""));

    searchButton = new RoundedButton(
      "Search",
      CommonColors.BUTTON_PRIMARY.getColor(),
      CommonColors.TEXT.getColor(),
      CommonFonts.TEXT_NORMAL
        .getFont()
        .deriveFont(25f),
      CommonDimensions.ADD_BUTTON.getDimension(),
      63
    );

    composeView();
    registerCallbacks();
  }

  public void composeView() {
    setBackground(CommonColors.BACKGROUND.getColor());
    setBorder(
      new EmptyBorder(
        0,
        0,
        CommonPaddings.SEARCH_PANEL_BOTTOM_PADDING.getPadding(),
        0
      )
    );

    add(searchField);
    
    add(perdiodSelector);

    add(
      new FlatText("From")
        .setColorMonadic(CommonColors.TEXT.getColor())
        .setOpacityMonadic(1f)
        .setFontMonadic(CommonFonts.TEXT_NORMAL.getFont().deriveFont(25f))
    );
    add(startDateField);

    add(
      new FlatText("To")
        .setColorMonadic(CommonColors.TEXT.getColor())
        .setOpacityMonadic(1f)
        .setFontMonadic(CommonFonts.TEXT_NORMAL.getFont().deriveFont(25f))
    );
    add(endDateField);
    add(searchButton);

    perdiodSelector.setSelectedIndex(0);
    startDateField.setEditable(false);
    endDateField.setEditable(false);
  }

  @Override public void registerCallbacks() {
    perdiodSelector.addActionListener(
      event -> {
        if (!perdiodSelector.getSelectedItem().equals("Custom")) {
          startDateField.setEditable(false);
          endDateField.setEditable(false);
        }
        else {
          startDateField.setEditable(true);
          endDateField.setEditable(true);
        }
      }
    );
  }


}
