package ui.components.containers;

import tunable.*;
import ui.core.*;
import ui.utils.*;
import ui.components.clickable.*;
import ui.components.text.*;
import ui.behaviour.*;

import java.awt.*;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.border.*;


import java.time.Period;
import java.util.*;
import java.util.stream.*;


public class SearchPanel extends JPanel implements IComponent {
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
      SearchPeriods.getStrings(),
      CommonColors.TEXTBOX.getColor(),
      CommonColors.TEXT.getColor(),
      CommonFonts.TEXT_NORMAL.getFont().deriveFont(25f),
      CommonDimensions.PERIOD_SELECTOR.getDimension(),
      25
    );

    
    startDateField = new RoundedTextField(
      "",
      CommonColors.TEXTBOX.getColor(),
      CommonColors.TEXTBOX_INVALID.getColor(),
      ColorOpaqueBuilder.build(CommonColors.TEXT.getColor(), 0.5f),
      CommonFonts.TEXT_NORMAL.getFont().deriveFont(25f),
      CommonDimensions.DATE_START_TEXT_FIELD.getDimension(),
      25
    ).setInputFilterMonadic(CommonValidators.DATE.getFilter());
  
    endDateField = new RoundedTextField(
      "",
      CommonColors.TEXTBOX.getColor(),
      CommonColors.TEXTBOX_INVALID.getColor(),
      ColorOpaqueBuilder.build(CommonColors.TEXT.getColor(), 0.5f),
      CommonFonts.TEXT_NORMAL.getFont().deriveFont(25f),
      CommonDimensions.DATE_END_TEXT_FIELD.getDimension(),
      25
    ).setMaxLengthMonadic(CommonValidators.DATE.getMaxLength())
    .setInputFilterMonadic(CommonValidators.DATE.getFilter())
    .setInputValidatorMonadic(CommonValidators.DATE.getValidator());
    
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

    setSearchPeriod(SearchPeriods.YEAR);
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
      new TunableText("From")
        .setColorMonadic(CommonColors.TEXT.getColor())
        .setOpacityMonadic(1f)
        .setFontMonadic(CommonFonts.TEXT_NORMAL.getFont().deriveFont(25f))
    );
    add(startDateField);

    add(
      new TunableText("To")
        .setColorMonadic(CommonColors.TEXT.getColor())
        .setOpacityMonadic(1f)
        .setFontMonadic(CommonFonts.TEXT_NORMAL.getFont().deriveFont(25f))
    );
    add(endDateField);
    add(searchButton);
  }

  public void setSearchPeriod(SearchPeriods period) {
    switch (period) {
      case DAY:
      case WEEK:
      case CUSTOM:
      
      startDateField
        .setInputValidatorMonadic(
          text -> text.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}") || text.equals("")
        )
        .setMaxLengthMonadic(10);

      endDateField
        .setInputValidatorMonadic(
          text -> text.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}") || text.equals("")
        )
        .setMaxLengthMonadic(10);

      break;

      case MONTH:

      startDateField
        .setInputValidatorMonadic(
          text -> text.matches("[0-9]{2}/[0-9]{4}") || text.equals("")
        )
        .setMaxLengthMonadic(8);

      endDateField
        .setInputValidatorMonadic(
          text -> text.matches("[0-9]{2}/[0-9]{4}") || text.equals("")
        )
        .setMaxLengthMonadic(8);

      break;

      case YEAR:

      startDateField
        .setInputValidatorMonadic(
          text -> text.matches("[0-9]{4}") || text.equals("")
        )
        .setMaxLengthMonadic(4);

      endDateField
        .setInputValidatorMonadic(
          text -> text.matches("[0-9]{4}") || text.equals("")
        )
        .setMaxLengthMonadic(4);
      break;
    }

    startDateField.setDefaultText(period.getMockStartText());
    endDateField.setDefaultText(period.getMockEndText());
    perdiodSelector.setSelectedItem(period.toString());
    
    if (period == SearchPeriods.CUSTOM) {
      startDateField.setEditable(true);
      endDateField.setEditable(true);
      return;
    }

    startDateField.setEditable(true);
    endDateField.setEditable(false);
  }
  
  @Override public void registerCallbacks() {
    perdiodSelector.addActionListener(
      event -> {
        setSearchPeriod(
          SearchPeriods.valueOf(
            perdiodSelector.getSelectedItem().toString().toUpperCase()
          )
        );
      }
    );
  }
}
