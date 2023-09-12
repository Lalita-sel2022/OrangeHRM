package orangeHRM;

import java.awt.Robot;
import java.awt.event.KeyEvent;
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

	public static void main(String[] args) throws InterruptedException, IOException
	{

		System.setProperty("webdriver.chrome.driver","C:\\Users\\adj_2\\Downloads\\chromedriver-win64\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
		// driver.get("https://www.servicenow.com/my-account/sign-up.html");
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
	    
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

			driver.findElement(By.xpath("//input[@name='username']")).sendKeys(userName);
			driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
			driver.findElement(By.xpath("//button[@class='oxd-button oxd-button--medium oxd-button--main orangehrm-login-button']")).click();
			Thread.sleep(2000);
			String actualURL = driver.getCurrentUrl();
			System.out.println("actual URL is :" + actualURL);
			String expectedURL = "https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index";
			
			if (status.equals("Valid"))
			{
				System.out.println("Inside if loop");
				if (actualURL.equals(expectedURL)) 
				{
					driver.findElement(By.xpath("//i[@class='oxd-icon bi-caret-down-fill oxd-userdropdown-icon']")).click();
					driver.findElement(By.linkText("Logout")).click();
					Thread.sleep(2000);
				    System.out.println("Test passed");
				} 
				else {
					System.out.println("Test is not passed");
				}

			} else if(status.equals("Invalid")) 
			{
				System.out.println("Inside else if loop");
				if (actualURL.equals(expectedURL))
				{
					driver.findElement(By.xpath("//i[@class='oxd-icon bi-caret-down-fill oxd-userdropdown-icon']")).click();
					driver.findElement(By.linkText("Logout")).click();
					Thread.sleep(2000);
					System.out.println("Test is not passed");
				} 
				else {
				
					System.out.println("Test passed");
				}
			}

	}
		
		System.out.println("Login testing finished");
	}

}
