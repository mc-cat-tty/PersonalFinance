package ui.components.containers;

import tunable.*;
import ui.core.*;
import ui.utils.*;
import ui.components.clickable.*;
import ui.components.text.*;
import ui.behaviour.*;

import java.awt.*;
import java.text.ParseException;

import javax.swing.*;
import javax.swing.border.*;

import model.core.BalanceModelManager;
import model.core.Transaction;
import model.events.EventsBroker;

import java.util.*;


public class SearchPanel extends JPanel implements IComponent {
  private final RoundedTextField searchField;
  private final RoundedComboBox perdiodSelector;
  private final RoundedTextField startDateField;
  private final RoundedTextField endDateField;
  private final RoundedButton searchButton;
  private Date startDate;
  private Date endDate;
  private Iterator<Transaction> searchIterator;
  private String currentSearchString;
  
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
      ColorOpaqueBuilder.build(CommonColors.TEXT.getColor(), 0.9f),
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
      ColorOpaqueBuilder.build(CommonColors.TEXT.getColor(), 0.9f),
      ColorOpaqueBuilder.build(CommonColors.TEXT.getColor(), 0.9f),
      CommonFonts.TEXT_NORMAL.getFont().deriveFont(25f),
      CommonDimensions.DATE_START_TEXT_FIELD.getDimension(),
      25
    ).withInputFilter(CommonValidators.DATE.getFilter())
    .withDefaultText(false);
  
    endDateField = new RoundedTextField(
      "",
      CommonColors.TEXTBOX.getColor(),
      CommonColors.TEXTBOX_INVALID.getColor(),
      ColorOpaqueBuilder.build(CommonColors.TEXT.getColor(), 0.9f),
      ColorOpaqueBuilder.build(CommonColors.TEXT.getColor(), 0.9f),
      CommonFonts.TEXT_NORMAL.getFont().deriveFont(25f),
      CommonDimensions.DATE_END_TEXT_FIELD.getDimension(),
      25
    ).withMaxLength(CommonValidators.DATE.getMaxLength())
    .withInputFilter(CommonValidators.DATE.getFilter())
    .withInputValidator(CommonValidators.DATE.getValidator())
    .withDefaultText(false);
    
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
        .withColor(CommonColors.TEXT.getColor())
        .withOpacity(1f)
        .withFont(CommonFonts.TEXT_NORMAL.getFont().deriveFont(25f))
    );
    add(startDateField);

    add(
      new TunableText("To")
        .withColor(CommonColors.TEXT.getColor())
        .withOpacity(1f)
        .withFont(CommonFonts.TEXT_NORMAL.getFont().deriveFont(25f))
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
        .withInputValidator(
          text -> text.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}") || text.equals("")
        )
        .withMaxLength(10);

      endDateField
        .withInputValidator(
          text -> text.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}") || text.equals("")
        )
        .withMaxLength(10);

      break;

      case MONTH:

      startDateField
        .withInputValidator(
          text -> text.matches("[0-9]{2}/[0-9]{4}") || text.equals("")
        )
        .withMaxLength(8);

      endDateField
        .withInputValidator(
          text -> text.matches("[0-9]{2}/[0-9]{4}") || text.equals("")
        )
        .withMaxLength(8);

      break;

      case YEAR:

      startDateField
        .withInputValidator(
          text -> text.matches("[0-9]{4}") || text.equals("")
        )
        .withMaxLength(4);

      endDateField
        .withInputValidator(
          text -> text.matches("[0-9]{4}") || text.equals("")
        )
        .withMaxLength(4);
      break;
    }

    startDateField.setDefaultText(period.getMockStartText());
    endDateField.setDefaultText(period.getMockEndText());
    perdiodSelector.setSelectedItem(period.toString());
    
    try {
      updateDates();
    }
    catch (ParseException e) { }

    EventsBroker
      .getInstance()
      .getFilterDateEvent()
      .notifyAllObservers(startDate, endDate);
    
    EventsBroker
      .getInstance()
      .getFilterEvent()
      .notifyAllObservers(
        BalanceModelManager
          .getInstance()
          .filterTransactionsByDate(startDate, endDate)
      );
    
    if (period == SearchPeriods.CUSTOM) {
      startDateField.setEditable(true);
      endDateField.setEditable(true);
      return;
    }

    startDateField.setEditable(false);
    endDateField.setEditable(true);
  }

  private SearchPeriods getSearchPeriod() {
    return SearchPeriods.valueOf(
      perdiodSelector.getSelectedItem().toString().toUpperCase()
    );
  }

  private void updateDates() throws ParseException {
    final var baseDate = getSearchPeriod().getFormatter().parse(endDateField.getText());
          
    endDate = getSearchPeriod() == SearchPeriods.CUSTOM ?
      baseDate : getSearchPeriod().toEndDate(baseDate);

    startDate = getSearchPeriod() == SearchPeriods.CUSTOM ?
      getSearchPeriod().getFormatter().parse(startDateField.getText()) :
      getSearchPeriod().toStartDate(baseDate);
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
          updateDates();
        }
        catch (ParseException exception) {
          startDate = endDate = null;

          EventsBroker
            .getInstance()
            .getFilterEvent()
            .notifyAllObservers(new ArrayList<>());
          
          return;
        }

        startDateField.setText(getSearchPeriod().getFormatter().format(startDate));

        final boolean isSearchDescriptionDefault =
          searchField.isDefaultText()
          || searchField.isEmptyText();
        
        // Notify a filter event to all subscribers if the parse doesn't fail
        EventsBroker
          .getInstance()
          .getFilterEvent()
          .notifyAllObservers(
            BalanceModelManager
              .getInstance()
              .filterTransactionsByDate(startDate, endDate)
          );
        
        EventsBroker
          .getInstance()
          .getFilterDateEvent()
          .notifyAllObservers(startDate, endDate);

        // Mark iterator as null if not searching
        if (isSearchDescriptionDefault) {
          searchIterator = null;
          return;
        }

        // If it's a new search or the end of the previous one, query the model
        if (
          searchIterator == null
          || !searchIterator.hasNext()
          || !currentSearchString.equals(searchField.getText())
          ) {
          currentSearchString = searchField.getText();

          searchIterator = BalanceModelManager
            .getInstance()
            .filterTransactionsByDescription(currentSearchString)
            .iterator();
        }

        // Select the next element
        if (searchIterator.hasNext()) {
          EventsBroker
            .getInstance()
            .getSelectionEvent()
            .notifyAllObservers(
              searchIterator.next()
            );
        }
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
