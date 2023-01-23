package ui.components.containers;

import tunable.*;
import ui.core.*;
import ui.utils.*;
import ui.components.clickable.*;
import ui.components.text.*;
import ui.behaviour.*;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.border.*;

import model.core.BalanceModel;
import model.core.BalanceModelManager;
import model.core.Transaction;
import model.events.EventsBroker;

import java.time.Period;
import java.util.*;
import java.util.stream.*;


public class SearchPanel extends JPanel implements IComponent {
  private final RoundedTextField searchField;
  private final RoundedComboBox perdiodSelector;
  private final RoundedTextField startDateField;
  private final RoundedTextField endDateField;
  private final RoundedButton searchButton;
  private Date startDate;
  private Date endDate;
  
  public SearchPanel() {
    super(
      new FlowLayout(
        FlowLayout.CENTER,
        CommonPaddings.SEARCH_PANEL_HORIZONTAL_PADDING.getPadding(),
        0
      )
    );

    startDate = endDate = new Date();

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

  private void setSearchPeriod(SearchPeriods period) {
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

  private SearchPeriods getSearchPeriod() {
    return SearchPeriods.valueOf(
      perdiodSelector.getSelectedItem().toString().toUpperCase()
    );
  }

  private Date parseStringDate(String dateStr, SearchPeriods searchPeriod) throws ParseException {
    switch (searchPeriod) {
      case DAY:
      case WEEK:
      case CUSTOM:
        return CommonDateFormats
          .EU_DATE_FORMAT_LONG
          .getFormatter()
          .parse(dateStr);
      
      case MONTH:
        return CommonDateFormats
          .MONTH_FORMAT_LONG
          .getFormatter()
          .parse(dateStr);
      
      case YEAR:
        return CommonDateFormats
          .YEAR_FORMAT_LONG
          .getFormatter()
          .parse(dateStr);
      
      default:
        return null;
    }
  }
  
  @Override public void registerCallbacks() {
    perdiodSelector.addActionListener(
      event -> {
        setSearchPeriod(getSearchPeriod());
      }
    );

    searchButton.addActionListener(
      event -> {
        try {
          startDate = parseStringDate(startDateField.getText(), getSearchPeriod());
          endDate = parseStringDate(endDateField.getText(), getSearchPeriod());
        }
        catch (ParseException exception) {
          startDate = endDate = null;
          EventsBroker
          .getInstance()
          .getFilterEvent()
          .notifyAllObservers(new ArrayList<>());
          return;
        }

        final boolean isStartDateDefault =
          startDateField.isDefaultText()
          || startDateField.isEmptyText();

        final boolean isEndDateDefault =
          endDateField.isDefaultText()
          || endDateField.isEmptyText();

        final boolean isSearchDescriptionDefault =
          searchField.isDefaultText()
          || searchField.isEmptyText();
        

        // If every search field is at the default value, show all transactions
        EventsBroker
          .getInstance()
          .getFilterEvent()
          .notifyAllObservers(
            BalanceModelManager
              .getInstance()
              .filterTransactionsByDate(
                startDate,
                endDate
              )
          );
      }
    );

    EventsBroker.getInstance().getAddEvent().attachObserver(
      transactions -> {
        if (startDate == null || endDate == null) {
          return;
        }

        EventsBroker
          .getInstance()
          .getFilterEvent()
          .notifyAllObservers(
            BalanceModelManager
              .getInstance()
              .filterTransactionsByDate(
                startDate,
                endDate
              )
          );
      }
    );
  }
}
