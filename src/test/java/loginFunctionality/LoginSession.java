package loginFunctionality;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class LoginSession {

	public static void main(String[] args) throws IOException, InterruptedException, AWTException {

		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\adj_2\\Downloads\\chromedriver-win64\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
		// driver.get("https://www.servicenow.com/my-account/sign-up.html");
		driver.get("https://demo.guru99.com/V4/index.php");
		Thread.sleep(3000);
		Robot boot= new Robot();

		FileInputStream file = new FileInputStream("C:\\Users\\adj_2\\OneDrive\\Documents\\login.xlsx");
		XSSFWorkbook book = new XSSFWorkbook(file);
		XSSFSheet sheet = book.getSheet("logindata");
		int rowCount = sheet.getLastRowNum();
		System.out.println("Row count is ::" + rowCount);
		int cellCount = sheet.getRow(0).getLastCellNum();
		System.out.println("Cell count is ::" + cellCount);

		for (int i = 1; i <= rowCount; i++) {
			System.out.println("Inside for loop");

			XSSFRow row = sheet.getRow(i);
			String userName = row.getCell(0).getStringCellValue();
			String password = row.getCell(1).getStringCellValue();
			String status = row.getCell(2).getStringCellValue();

			driver.findElement(By.xpath("//input[@name='uid']")).sendKeys(userName);
			driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
			driver.findElement(By.xpath("//input[@name='btnLogin']")).click();
			Thread.sleep(2000);
			//driver.switchTo().alert().accept();
			
			boot.keyPress(KeyEvent.VK_SPACE);
			String actualTitle = driver.getTitle();
			System.out.println("actual title is :" + actualTitle);
			String expectedTitle = "Guru99 Bank Manager HomePage";

			if (status.equals("Valid"))
			{
				if (expectedTitle.equals(actualTitle)) 
				{
					
					WebElement logout = driver.findElement(By.xpath("//ul[@class='menusubnav']/li[15]"));
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("arguments[0].scrollIntoView(true);", logout);
					logout.click();
					Thread.sleep(2000);
					boot.keyPress(KeyEvent.VK_SPACE);
					System.out.println("Test passed");
				} else {
					System.out.println("Test is not passed");
				}

			} else if (status.equals("Invalid")) 
			{
				System.out.println("Inside else if loop");
				if (expectedTitle.equals(actualTitle))
				{
					System.out.println("Inside if condition");
					
					WebElement logout = driver.findElement(By.xpath("//ul[@class='menusubnav']/li[15]"));
					System.out.println("Logout text :"+logout.getText());
					JavascriptExecutor js1 = (JavascriptExecutor) driver;
					js1.executeScript("arguments[0].scrollIntoView(true);", logout);
					logout.click();
					Thread.sleep(2000);
					boot.keyPress(KeyEvent.VK_SPACE);
					System.out.println("Test is not passed");
				} else {
					System.out.println("Inside else condition");
					System.out.println("Test passed");
				}
			}

		}

	}

}
