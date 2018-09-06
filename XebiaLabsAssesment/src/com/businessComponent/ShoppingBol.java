package com.businessComponent;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

@Listeners(com.businessComponent.Listeners.class)

public class ShoppingBol {
	
	public static WebDriver driver;
	public static String systemPath="";
	
	@Parameters({"Path","Browser"})
	@BeforeTest
	public static void Initialization(String path,String browser)
	{
		  //Here we decide which browser should be selected for execution
		  systemPath=path;
		  if(browser.equals("chrome")){
			  	System.setProperty("webdriver.chrome.driver", systemPath+"\\chromedriver.exe");
				driver=new ChromeDriver();
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
			}
			else{
				driver= new FirefoxDriver();
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
			}
		  
	}
	
	@Parameters({"URL","EmailId","Password"})
	@Test(priority = 1)
	public void Login(String URL,String emailId,String password)
	{
				
		//Navigate to shopping site using credentials
		driver.get(URL);
		driver.manage().window().maximize();
		driver.findElement(By.xpath("//a[@href='/nl/rnwy/account/overzicht']")).click();
		driver.findElement(By.xpath("//input[@id='login_email']")).sendKeys(emailId);
		driver.findElement(By.xpath("//input[@id='login_password']")).sendKeys(password);
		driver.findElement(By.xpath("//input[@value='Inloggen']")).click();
	}
	
		
	@Test(priority = 2,dependsOnMethods={"Login"})
	public void EmptytheCart()
	{		
		//Navigate to my orders
		driver.findElement(By.xpath("//div[contains(@class,'fluid-grid--rev')]/div[2]/ul/li[8]/a")).click();
		//Empty the cart before proceeding further
		driver.findElement(By.xpath("//a[@id='basket']")).click();
		System.out.println(driver.findElements(By.xpath("//fieldset[contains(@class,'fluid-grid--auto')]")).size());
		int count=driver.findElements(By.xpath("//fieldset[contains(@class,'fluid-grid--auto')]")).size();
		//count =0 means your cart is already empty
		if (count!=0) 
		{
			if(driver.findElement(By.xpath("//a[@id='tst_remove_from_basket']")).isEnabled())
			{
				//delete the item present in cart
				driver.findElement(By.xpath("//a[@id='tst_remove_from_basket']")).click();
					if(driver.findElement(By.xpath("//div[@class='fluid-grid__item']")).isDisplayed())
					{
					System.out.println("Cart is empty");				
					}		
		     }
	     }
	  }
	
	@Parameters({"Product"})	
	@Test(priority = 3)
	public void	SearchProduct(String Product)
	{
		//Click on continue shopping
		driver.findElement(By.xpath("//div[@class='fluid-grid__item']")).click();
		//Click on to the shop
		driver.findElement(By.xpath("//div[contains(@class,'h-boxedright--xs')]")).click();
		Actions a=new Actions(driver);
		driver.findElement(By.xpath("//input[@id='searchfor']")).sendKeys(Product);
		driver.findElement(By.xpath("//input[@data-test='search-button']")).click();		
		a.moveToElement(driver.findElement(By.xpath("//a[text()='Google Chromecast - Media Streamer']"))).click().build().perform();
	}
	
	@Test(priority = 4)
	public void	AddtoCart()
	{
		//Adding product to the cart
		driver.findElement(By.xpath("//a[@data-test='add-to-basket']")).click();		
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS) ;
		driver.findElement(By.xpath("//a[@data-test='btn-continue-shopping']")).click();
	}
	
	@Test(priority = 4)
	public void CheckPrice()
	{
		String Total_price=driver.findElement(By.xpath("//td[@id='tst_total_price']")).getText().substring(1);
		String tempArr[]=Total_price.split(",");
		int resultPrice = Integer.parseInt(tempArr[0].trim());
		System.out.println(resultPrice);
		//Continue to order
		driver.findElement(By.xpath("//input[@class='js_basket_bottom_button']")).click();
		//return resultPrice;
		OfferValidation(resultPrice);
	}
	
	public void OfferValidation(int resultPrice)
	{
		System.out.println(resultPrice);
		String Shipping_Value = driver.findElement(By.xpath("//td[@data-content-section='shipping_value']")).getText();
		if (resultPrice>20) {
			
			if (Shipping_Value.equalsIgnoreCase("Gratis"))
				System.out.println("Functionality is working fine:Shipping is free");
			else
				System.out.println("Issue in Functionality");
		}
		else {
			System.out.println("Shipping is not free");
		}
			
	}
	
	public void Screenshot()
	{
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		// Code to take screenshot
		try {
			FileUtils.copyFile(scrFile, new File(systemPath+"\\testfailure.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@AfterTest
	public void Quit()
	{
		driver.close();
	}

}
