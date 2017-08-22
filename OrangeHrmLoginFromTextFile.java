package tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
public class OrangeHrmLoginFromTextFile {
	int rc = 0;
	String username = "", password = "";
	static WebDriver driver;
	

	@DataProvider(name="TestData")
	public Object[][] getDataFromTxtFile() throws FileNotFoundException {
		int p = 0, q = 0;
		System.out.println("Data Provider is working on Test Data Preparation ...");
		Scanner fileIn = new Scanner(new File("E:\\Eclipse\\workspace\\OrangeHrmTests\\UID.txt"));
		username = fileIn.next();
		password = fileIn.next();
		
		while (fileIn.hasNextLine()){
			rc++;
			fileIn.next();			
		}
		rc = rc / 2;
		//System.out.println(rc);
		fileIn.reset();
		Scanner fileIn1 = new Scanner(new File("E:\\Eclipse\\workspace\\OrangeHrmTests\\UID.txt"));
		username = fileIn1.next();
		password = fileIn1.next();
		
		Object[][] data = new Object[rc][2];
			
		for(p=0 ;p<rc ;p++)
			for(q=0; q<1 ; q++){
			username = fileIn1.next();
			password = fileIn1.next();
			//System.out.println(username);
			//System.out.println(password);
			//System.out.println(p + "" + q);
			System.out.println("...");
			data[p][q++] = username;
			data[p][q] = password;
			}
		System.out.println("Data Provider has completed Test Data Preparation ...");
		return data;
	}
	
	@Test(priority=2, enabled=true, dataProvider = "TestData", groups="Functional")
	public void userLoginTest(String username, String password) throws InterruptedException, IOException {
		System.out.println("User login ...");
		By userName = By.id("txtUsername");
		By passwd   = By.id("txtPassword");
		By loginBtn = By.id("btnLogin");
		By adminMenu = By.id("menu_admin_viewAdminModule");
		
		//By adminDropDown = By.xpath("//*[@id='welcome']"); 
				//By.className("panelContainer"); 
				//By.id("welcome-menu");
		String actualTitle = "", expectedTitle = "OrangeHRM", expectedAdminMenuTxt = "Administración", actualAdminMenuTxt = ""; 
		
		System.setProperty("webdriver.firefox.marionette","D:\\Softwares\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.get("http://opensource.demo.orangehrmlive.com/");
		
		driver.findElement(userName).sendKeys(username);
		driver.findElement(passwd).sendKeys(password);
		driver.findElement(loginBtn).click();
		
		actualTitle = driver.getTitle();
		//System.out.println(actualTitle);
		actualAdminMenuTxt = driver.findElement(adminMenu).getText();
		
		//Assert.assertEquals(actualTitle, expectedTitle);
		Assert.assertEquals(actualAdminMenuTxt, expectedAdminMenuTxt);
		//System.out.println(actualAdminMenuTxt);
		//Select oSelect = new Select(driver.findElement(adminDropDown));
		//oSelect.selectByIndex(1);
		//oSelect.selectByVisibleText("Logout");
	}
	
}