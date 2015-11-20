package test.citrix.service;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class WebinarTestService implements WebDriverTestService {

     //Login to go to webinar
    @Override
    public void login(WebDriver driver, String userName, String password) {
        // Login procedure
        driver.findElement(By.id("emailAddress")).sendKeys(userName);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("submit")).click();

        boolean myWebinars = driver.findElement(By.xpath("//h1[contains(text(),'My Webinars')]")).isDisplayed();
        boolean myWebinarsButtonPresent = driver.findElement(By.xpath(".//*[@id='scheduleWebinar']/div")).isDisplayed();
        assertTrue(myWebinars);// Validate my webinar header is displayed
        assertTrue(myWebinarsButtonPresent);// Validate schedule a webinar button is displayed
    }

    //It selects the webinar start date, start time and end time for webinar
    public void selectWebinarDateTime(WebDriver driver, int daysFromNow, String startTime, String startAmPm, String endTime, String endAmPm) {
        //Select start date
        String formattedDate = getFormattedStartDate(daysFromNow);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('webinarTimesForm.dateTimes_0.baseDate').setAttribute('value','" + formattedDate + "')");

        //Start Time
        js.executeScript("document.getElementById('webinarTimesForm.dateTimes_0.startTime').setAttribute('value', '" + startTime + "')");
        driver.findElement(By.id("webinarTimesForm_dateTimes_0_startAmPm_trig")).click();
        driver.findElement(By.xpath("//li[contains(text(),'AM')]")).click();

        //End Time
        js.executeScript("document.getElementById('webinarTimesForm.dateTimes_0.endTime').setAttribute('value', '" + endTime + "')");
        if (endAmPm != null && (endAmPm.equalsIgnoreCase("am") || endAmPm.equalsIgnoreCase("pm"))) {
            driver.findElement(By.id("webinarTimesForm_dateTimes_0_endAmPm_trig")).click();
            if (driver.findElement(By.xpath("//li[contains(text(),'PM')]")).isDisplayed()) {
                driver.findElement(By.xpath("//li[contains(text(),'AM')]")).click();
            }
        }
    }

    //Create random name for webinar title
    @Override
    public String getRandomTitle(String prefix) {
        return prefix + " " + new Date().getTime();
    }

    //Convert date and time to EEE, MMM d, yyyy format
    private String getFormattedStartDate(int daysFromNow) {

        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, daysFromNow);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
        return dateFormat.format(c.getTime());
    }

    //Get date with year to validate on my webinar page
    public String getDateWithoutYear(String formatted) throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
        Date dt = dateFormat.parse(formatted);

        SimpleDateFormat newDateFormat = new SimpleDateFormat("EEE, MMM d");
        String formattedDate = newDateFormat.format(dt.getTime());

        System.out.println(formattedDate);

        return formattedDate;
    }

    //Delete all webinar on my webinar screen
    public void deleteAllWebinars(WebDriver driver) {

        while (true) {

            try {
                WebElement element = driver.findElement(
                        By.xpath("//div[@id='upcomingWebinar']//div[contains(@class, 'table-data-row openWebinar')][1]"));

                String elementId = element.getAttribute("id");
                String editWebinarLinkXPath = "//div[@id='" + elementId + "']/ul[1]/li[3]/a/span";
                System.out.println("Webinar Link XPath" + editWebinarLinkXPath);

                driver.findElement(By.xpath(editWebinarLinkXPath)).click();
                driver.findElement(By.xpath("//a[contains(text(),'Cancel Webinar')]")).click();
                driver.findElement(By.xpath("//span[contains(text(),'Yes, Cancel Webinar')]")).click();

            } catch (NoSuchElementException ex) {
                break;
            }
        }
    }

    //Validate webinar title, date and time on my webinar page
    public void verifyWebinarCreationOnMyWebinarsPage(WebDriver driver, String webinarTitle, String dateAndTime) throws ParseException {
        String myWebinarDate = driver.findElement(By.className("myWebinarDate")).getText();
        String scheduleWebinar_dateAndTimeWithoutYear = getDateWithoutYear(dateAndTime);
        assertEquals(scheduleWebinar_dateAndTimeWithoutYear, myWebinarDate);

        //Validate webinar title on my webinars screen
        String myWebinarScreen_Title = driver.findElement(By.xpath("//div/ul[1]/li[3]/a/span")).getText();
        assertEquals(myWebinarScreen_Title, webinarTitle);

        // Validate webinar times.
        String myWebinarScreen_Time = driver.findElement(By.xpath("//div/ul[2]/div[1]/li[2]/span")).getText();
        assertEquals(myWebinarScreen_Time, "10:00 AM - 11:00 AM PST");
    }

    //Validate inputs on manage webinar page
    public void verifySuccessfulWebinarCreation(WebDriver driver, String webinarTitle, String webinarDescription, String webinarDateAndTime, String webinarLanguage) {
        //Validate scheduled webinar title and manage webinar screen title are same
        String setUpYourWebinar_Title = driver.findElement(By.id("trainingName")).getText();
        assertEquals(setUpYourWebinar_Title, webinarTitle);

        //Validate scheduled webinar description and manage webinar screen description are same.
        String setUpYourWebinar_Description = driver.findElement(By.id("trainingDesc")).getText();
        assertEquals(setUpYourWebinar_Description, webinarDescription);

        //Validate scheduled webinar date & time and manage webinar screen date & time are same.
        String setUpYourWebinar_DateAndTime = driver.findElement(By.xpath(".//*[@id='dateTime']/p")).getText();
        assertEquals(setUpYourWebinar_DateAndTime, webinarDateAndTime);

        //Validate add to calender text is present.
        String setUpYourWebinar_AddToCalender = driver.findElement(By.xpath(".//*[@id='calendarUrl']/li/a")).getText();
        assertEquals(setUpYourWebinar_AddToCalender, "Add to Calendar");

        //Validate add to calender link is present
        String setUpYourWebinar_AddToCalenderLink = driver.findElement(By.xpath(".//*[@id='calendarUrl']/li/a")).getAttribute("href");
        assertTrue(setUpYourWebinar_AddToCalenderLink.contains("/icsCalendar.tmpl"));

        //Validate scheduled webinar language and manage webinar screen language are same.
        String setUpYourWebinar_Language = driver.findElement(By.id("locale")).getText();
        assertEquals(setUpYourWebinar_Language, webinarLanguage);

        //Validate audio section is present.
        String setUpYourWebinar_Audio = driver.findElement(By.id("audioInstructions")).getText();
        assertEquals(setUpYourWebinar_Audio, "Participants can use their computer's microphone and speakers (VoIP) or telephone.");
    }

    //Go to my webinar page
    public void goToMyWebinarPage(WebDriver driver) {
        driver.findElement(By.xpath("//a[contains(text(),'My Webinars')]")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[contains(text(),'Upcoming Webinars')]"))));
    }
}
