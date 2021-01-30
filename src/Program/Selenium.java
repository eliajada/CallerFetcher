package Program;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class Selenium {

	static WebDriver driver;

	public static void startIncognitoSession() {
//
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");
//		options.addArguments("--headless");
//		options.addArguments("--disable-gpu");
		options.addArguments("--window-size=1400,800");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		System.setProperty("webdriver.chrome.driver",
				"Driver\\chromedriver.exe");

		driver = new ChromeDriver(capabilities);

	}

	public static void click(String element) {
		try {

			driver.findElement(By.xpath(element)).click();

		} catch (Exception e) {

			System.out.println("click Failed");

		}
	}

	public static void clear(String element) {
		try {

			driver.findElement(By.xpath(element)).clear();

		} catch (Exception e) {

			System.out.println("clear Failed");

		}
	}

	public static void sendKeys(String element, String text) {
		try {

			driver.findElement(By.xpath(element)).sendKeys(text);

		} catch (Exception e) {

			System.out.println("sendKeys Failed");

		}
	}

	public static boolean isDisplayed(String element) {
		if (driver.findElement(By.xpath(element)).isDisplayed()) {
			
			return true;
			
		} else {
			
			return false;
			
		}
	}

	public static String getText(String element) {
		String returnedText = "";

		try {

			returnedText = driver.findElement(By.xpath(element)).getText();

		} catch (Exception e) {

			returnedText = "No Results";
			System.out.println("getText Failed.");

		}
		return returnedText;

	}

	public static void goTo(String website) {
		try {

			driver.get(website);

		} catch (Exception e) {

			System.out.println("goTo Failed.");

		}

	}

	public static void endSession() {

		driver.quit();

	}

}
