package test.citrix.service;

import org.openqa.selenium.WebDriver;

import java.text.ParseException;

/**
 * WebinarDriverTestService interface for the methods required for webdriver tests
 */
public interface WebDriverTestService {

    public void login(WebDriver driver, String userName, String password);

    public void selectWebinarDateTime(WebDriver driver, int daysFromNow, String startTime,
                                      String startAmPm, String endTime, String endAmPm);

    public String getRandomTitle(String prefix);

    public String getDateWithoutYear(String formatted) throws ParseException;

    public void deleteAllWebinars(WebDriver driver);

    public void verifyWebinarCreationOnMyWebinarsPage(WebDriver driver,
                                                      String webinarTitle, String dateAndTime) throws ParseException;

    public void verifySuccessfulWebinarCreation(WebDriver driver, String webinarTitle,
                                                String webinarDescription, String webinarLanguage);

    public void goToMyWebinarPage(WebDriver driver);
}
