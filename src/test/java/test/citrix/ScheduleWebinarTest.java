package test.citrix;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import test.citrix.service.WebDriverTestService;
import test.citrix.service.WebinarTestService;

import static junit.framework.Assert.assertEquals;

/**
 * Test cases for Schedule A Webinar functionality
 *
 * @author Hetal Pandya
 */
public class ScheduleWebinarTest extends BaseWebDriverTest {

    private static final String TITLE_PREFIX = "Webinar Test";

    // Instantiate WebDriverTestService
    public WebDriverTestService webinarTestService = new WebinarTestService();

    @Before
    public void setUp() throws Exception {
        super.setUp();

        driver.get(myWebinarsPageUrl);

        //Login to go to webinar
        webinarTestService.login(driver, config.getProperty("userName"),
                config.getProperty("password"));

        //Maximize the window
        driver.manage().window().maximize();

        //Clear all scheduled webinars from my webinar screen
        webinarTestService.deleteAllWebinars(driver);
    }

    /**
     * Test the schedule webinar that occur for only one session.
     * Validate inputs on my webinar and manage webinar page.
     */
    @Test
    public void scheduleWebinar_occursOneSession() throws Exception {

        //Click on schedule Webinar button
        driver.findElement(By.id("scheduleWebinar")).click();

        //Add random webinar title
        String webinarTitle = webinarTestService.getRandomTitle(TITLE_PREFIX);
        driver.findElement(By.id("name")).sendKeys(webinarTitle);

        //Add description
        driver.findElement(By.id("description")).sendKeys("This is a webinar test.");
        String webinarDescription = "This is a webinar test.";

        //Selecting occurs value
        driver.findElement(By.id("recurrenceForm_recurs_trig")).click();
        driver.findElement(By.xpath("//li[contains(text(),'One session')]")).click();

        //Selecting date and time for webinar
        webinarTestService.selectWebinarDateTime(driver, 3, "10:00", "AM", "11:00", "AM");
        String dateAndTime = driver.findElement(By.id("webinarTimesForm.dateTimes_0.baseDate")).getAttribute("value");
        String webinarDateAndTime = dateAndTime + " 10:00 AM" + " - " + "11:00 AM" + " PST";

        //Select webinar language
        driver.findElement(By.id("language_trig")).click();
        driver.findElement(By.xpath("//li[contains(text(),'English')]")).click();
        String webinarLanguage = "English";

        //Select submit button
        driver.findElement(By.id("schedule.submit.button")).click();

        //Validate webinar title, description,language on manage webinar page
        webinarTestService.verifySuccessfulWebinarCreation(driver, webinarTitle, webinarDescription, webinarLanguage);

        //Validate scheduled webinar date & time and manage webinar screen date & time are same.
        String setUpYourWebinar_DateAndTime = driver.findElement(By.xpath(".//*[@id='dateTime']/p")).getText();
        assertEquals(setUpYourWebinar_DateAndTime, webinarDateAndTime);

        //Go to my webinar page
        webinarTestService.goToMyWebinarPage(driver);

        //Validate webinar title, date and time on my webinar page
        webinarTestService.verifyWebinarCreationOnMyWebinarsPage(driver, webinarTitle, dateAndTime);
    }

    /**
     * Test the schedule webinar that occurs daily.
     * Validate inputs on my webinar page.
     */
    @Test
    public void scheduleWebinar_occursDaily() throws Exception {

        //Click on schedule Webinar button
        driver.findElement(By.id("scheduleWebinar")).click();

        //Add random webinar title
        String webinarTitle = webinarTestService.getRandomTitle(TITLE_PREFIX);
        driver.findElement(By.id("name")).sendKeys(webinarTitle);

        //Add description
        driver.findElement(By.id("description")).sendKeys("This is a webinar test.");
        String webinarDescription = "This is a webinar test.";

        //Selecting occurs value
        driver.findElement(By.id("recurrenceForm_recurs_trig")).click();
        driver.findElement(By.xpath("//li[contains(text(),'Daily')]")).click();

        //Selecting date and time for webinar
        webinarTestService.selectWebinarDateTime(driver, 3, "10:00", "AM", "11:00", "AM");
        String startDateAndTime = driver.findElement(By.id("webinarTimesForm.dateTimes_0.baseDate")).getAttribute("value");

        //Registration type
        driver.findElement(By.xpath(".//label[contains(text(),'Registration type')]")).isDisplayed();
        driver.findElement(By.xpath(".//*[@id='recurs-attendee-choice']/div[1]/input")).click();

        //Select webinar language
        driver.findElement(By.id("language_trig")).click();
        driver.findElement(By.xpath("//li[contains(text(),'English')]")).click();
        String webinarLanguage = "English";

        driver.findElement(By.id("schedule.submit.button")).click();

        //Validate webinar title, description,language on manage webinar page
        webinarTestService.verifySuccessfulWebinarCreation(driver, webinarTitle, webinarDescription, webinarLanguage);

        //Go to my webinar page
        webinarTestService.goToMyWebinarPage(driver);

        //Validate inputs on my webinar page
        webinarTestService.verifyWebinarCreationOnMyWebinarsPage(driver, webinarTitle, startDateAndTime);
    }

    /**
     * Test the schedule webinar that occurs monthly.
     * Validate inputs on my webinar page.
     */

    @Test
    public void scheduleWebinar_occursMonthly() throws Exception {

        //Click on schedule Webinar button
        driver.findElement(By.id("scheduleWebinar")).click();

        //Add random webinar title
        String webinarTitle = webinarTestService.getRandomTitle(TITLE_PREFIX);
        driver.findElement(By.id("name")).sendKeys(webinarTitle);

        //Add description
        driver.findElement(By.id("description")).sendKeys("This is a webinar test.");
        String webinarDescription = "This is a webinar test.";

        //Selecting occurs value
        driver.findElement(By.id("recurrenceForm_recurs_trig")).click();
        driver.findElement(By.xpath("//li[contains(text(),'Monthly')]")).click();

        //Selecting date and time for webinar
        webinarTestService.selectWebinarDateTime(driver, 3, "10:00", "AM", "11:00", "AM");
        String startDateAndTime = driver.findElement(By.id("webinarTimesForm.dateTimes_0.baseDate")).getAttribute("value");

        //Registration type
        driver.findElement(By.xpath(".//*[@id='recurs-attendee-choice']/div[1]/input")).click();

        //Select webinar language
        driver.findElement(By.id("language_trig")).click();
        driver.findElement(By.xpath("//li[contains(text(),'English')]")).click();
        String webinarLanguage = "English";

        driver.findElement(By.id("schedule.submit.button")).click();

        //Validate webinar title, description,language on manage webinar page
        webinarTestService.verifySuccessfulWebinarCreation(driver, webinarTitle, webinarDescription, webinarLanguage);

        //Go to my webinar page
        webinarTestService.goToMyWebinarPage(driver);

        // Validate start date and start time on my webinar page
        webinarTestService.verifyWebinarCreationOnMyWebinarsPage(driver, webinarTitle, startDateAndTime);
    }

    /**
     * Test the custom schedule of webinar
     * Validate inputs on my webinar page.
     */
    @Test
    public void scheduleWebinar_customSchedule() throws Exception {

        //Click on schedule Webinar button
        driver.findElement(By.id("scheduleWebinar")).click();

        //Add random webinar title
        String webinarTitle = webinarTestService.getRandomTitle(TITLE_PREFIX);
        driver.findElement(By.id("name")).sendKeys(webinarTitle);

        //Add description
        driver.findElement(By.id("description")).sendKeys("This is a webinar test.");
        String webinarDescription = "This is a webinar test.";

        //Selecting occurs value
        driver.findElement(By.id("recurrenceForm_recurs_trig")).click();
        driver.findElement(By.xpath("//li[contains(text(),'Custom schedule')]")).click();

        //Selecting date and time for webinar
        webinarTestService.selectWebinarDateTime(driver, 3, "10:00", "AM", "11:00", "AM");
        String startDateAndTime = driver.findElement(By.id("webinarTimesForm.dateTimes_0.baseDate")).getAttribute("value");

        //Add another session link
        driver.findElement(By.id("addAnother")).isDisplayed();
        driver.findElement(By.id("addAnother")).click();

        //Validate start date, start time and end time are present in add session link
        String anotherSession_StartDate = driver.findElement(By.id("webinarTimesForm.dateTimes_1.baseDateLabel")).getText();
        assertEquals(anotherSession_StartDate, "Start date");

        String anotherSession_StartTime = driver.findElement(By.id("webinarTimesForm.dateTimes_1.startTimeLabel")).getText();
        assertEquals(anotherSession_StartTime, "Start time");

        String anotherSession_EndTime = driver.findElement(By.id("webinarTimesForm.dateTimes_1.endTimeLabel")).getText();
        assertEquals(anotherSession_EndTime, "End time");

        //Registration type
        driver.findElement(By.xpath(".//*[@id='recurs-attendee-choice']/div[1]/input")).click();

        //Webinar Language
        driver.findElement(By.id("language_trig")).click();
        driver.findElement(By.xpath("//li[contains(text(),'English')]")).click();
        String webinarLanguage = "English";

        driver.findElement(By.id("schedule.submit.button")).click();

        //Validate webinar title, description,language on manage webinar page
        webinarTestService.verifySuccessfulWebinarCreation(driver, webinarTitle, webinarDescription, webinarLanguage);

        //Go to my webinar page
        webinarTestService.goToMyWebinarPage(driver);

        // Validate start date and start time my webinar page
        webinarTestService.verifyWebinarCreationOnMyWebinarsPage(driver, webinarTitle, startDateAndTime);
    }

    /**
     * Negative Test case
     * This test validates the title text field's error message.
     */
    @Test
    public void scheduleWebinar_titleValidation() throws Exception {

        //Click on Schedule Webinar Button
        driver.findElement(By.id("scheduleWebinar")).click();

        //Add random webinar title
        driver.findElement(By.id("name")).sendKeys("<script> alert('test') </script>");

        driver.findElement(By.id("schedule.submit.button")).click();

        //Validate title text field's error message
        String errorMessage = driver.findElement(By.xpath(".//*[@id='scheduleForm']/div[1]/div/div[2]/div[2]/p")).getText();
        assertEquals("Enter text that doesn't include <, >, &# or a backslash next to a number.", errorMessage);
    }

    /**
     * Negative test case
     * It is to test title field error message when submit blank form.
     */
    @Test
    public void scheduleWebinar_emptyFormSubmission() throws Exception {

        //Click on Schedule Webinar Button
        driver.findElement(By.id("scheduleWebinar")).click();

        driver.findElement(By.id("schedule.submit.button")).click();

        //Validate title text field's error message
        String titleErrorMessage = driver.findElement(By.xpath(".//*[@id='scheduleForm']/div[1]/div/div[2]/div[2]/p")).getText();
        assertEquals("Enter a title.", titleErrorMessage);
    }

}
