# PersonalFinance
## UI Design
![HomeView](HomeView.svg)

## End Result
![EndResult](EndResult.png)

## Usage
### 4Users

 1. Download the latest release at [https://github.com/mc-cat-tty/PersonalFinance/releases/latest](https://github.com/mc-cat-tty/PersonalFinance/releases/latest)
 2. Run **PersonalFinance.jar** through the gui (usually by double clicking it) or through the cli (`java -jar PersonalFinance.jar`)

### 4Devs
Run this before building the project for the first time:
```bash
make init
```

Build and run:
```bash
make build && make run
```

Try jar executable:
```bash
make build && make jar && java -jar PersonalFinance.jar
```

## Assets
 - Inter Font: [https://fonts.google.com/specimen/Inter](https://fonts.google.com/specimen/Inter)
 - Material Icons: [https://fonts.google.com/icons](https://fonts.google.com/icons)
 - Material Colors: [https://mui.com/material-ui/customization/color/](https://mui.com/material-ui/customization/color/)

## Documentation
[docs/html/index.html](docs/html/index.html)

## Bugs
Feel free to report any bug or issue by opening a issue marked as _bug_ or _enhancement_