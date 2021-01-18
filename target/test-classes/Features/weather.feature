Feature: Four-day weather forecast data read

Scenario: Reading 4-Day outlook weather details

  Given user is navigate to weather home page
  When user select two hour NowCast
  And User select four day forecast
  Then user validate day after tomorrow weather details Vs API Response

  