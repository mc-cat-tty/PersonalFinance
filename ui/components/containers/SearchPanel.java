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
    DAY ("01/01/2022", "01/01/2022"),
    WEEK ("01/01/2022", "07/01/2022"),
    MONTH ("01/2022", "01/2022"),
    YEAR ("2022", "2022"),
    CUSTOM ("00/01/2022", "00/02/2023");

    private String mockStartText;
    private String mockEndText;

    private SearchPeriod(String mockStartText, String mockEndText) {
      this.mockStartText = mockStartText;
      this.mockEndText = mockEndText;
    }

    public String getMockStartText() {
      return mockStartText;
    }

    public String getMockEndText() {
      return mockEndText;
    }

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
  private SearchPeriod searchPeriod;
  
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

    setSearchPeriod(SearchPeriod.DAY);
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
  }

  public void setSearchPeriod(SearchPeriod period) {
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

    startDateField.setInnerText(period.getMockStartText());
    endDateField.setInnerText(period.getMockEndText());
    
    if (period == SearchPeriod.CUSTOM) {
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
          SearchPeriod.valueOf(
            perdiodSelector.getSelectedItem().toString().toUpperCase()
          )
        );
      }
    );
  }
}
