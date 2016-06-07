/**
 * 
 */
package keywordsLib;

import java.util.List;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.PageFactory;

import frameDriver.FW_BrowserSetup;
import frameDriver.FW_SupportUtil;
import frameDriver.FW_Reporter;
import frameDriver.FW_Logger;
import pageObjects.Po_GoogleHomePage;
import pageObjects.Po_GoogleNewsPage;
import pageObjects.Po_GooglePlayPage;



/**
 * @author Brijendra Singh
 *
 */
public class Aut_Keywords  {
	
	static WebDriver driver;	
	//Page objects- declare here
	static Po_GoogleHomePage gLanPage;
	static Po_GoogleNewsPage googNewsPage;
	static Po_GooglePlayPage googPlayPage;
	
	/**1. pageSetup
	 * ----------------------------------------------------------------------------------------------------
	 * @author: Brijendra Singh
	 * @Date  : May 03, 2016 
	 * @Discription: pageSetup, initialize all the available page object classes in here
	 * -----------------------------------------------------------------------------------------------------
	 */
	public Aut_Keywords(WebDriver ldriver) {
		//Page object initialization- initialize here
		 driver =ldriver;
		 gLanPage = PageFactory.initElements(driver, Po_GoogleHomePage.class);
		 googNewsPage = PageFactory.initElements(driver, Po_GoogleNewsPage.class);
		 googPlayPage = PageFactory.initElements(driver, Po_GooglePlayPage.class);
	}
	
	/**2. getdriver
	 * ----------------------------------------------------------------------------------------------------
	 * @author: Brijendra Singh
	 * @Date  : May 03, 2016 
	 * @Discription: getdriver, returm the webdriver
	 * -----------------------------------------------------------------------------------------------------
	 */
	public static WebDriver getdriver(){		
		return driver;
	}
	
	/**3. appNavigateBack
	 * ----------------------------------------------------------------------------------------------------
	 * @author: Brijendra Singh
	 * @Date  : May 03, 2016 
	 * @Discription: appNavigateBack, print news headers from specific class to logFile
	 * -----------------------------------------------------------------------------------------------------
	 */
	public void appNavigateBack(){
		driver.navigate().back();
		FW_Reporter.logPASS("Open Previous Page", "Done");
	}
	
	/**4. QuitApp
	 * ----------------------------------------------------------------------------------------------------
	 * @author: Brijendra Singh
	 * @Date  : May 03, 2016 
	 * @Discription: QuitApp, close the browser
	 * -----------------------------------------------------------------------------------------------------
	 */
	public void QuitApp(){
		try{
			FW_BrowserSetup.getMainDriver().close();
			FW_BrowserSetup.getMainDriver().quit();
			FW_Reporter.logINFO("Close Browser" , "Done");
			driver = null;
		}catch (Throwable e){
			FW_Reporter.logERROR("Close Browser error", e.getMessage());
		}
	}
	
	public static void putWAIT(int wait){
		try {
			Thread.sleep(wait);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//*****************************************************************************************************
	/**----------------------------------------------------------------------------------------------------
	 *					User Define Methods- Keywords
	 -----------------------------------------------------------------------------------------------------*/
	//*****************************************************************************************************
	/** OpenGnewsPage
	 * ----------------------------------------------------------------------------------------------------
	 * @author: Brijendra Singh
	 * @Date  : May 03, 2016 
	 * @Discription: OpenGnewsPage, open up the google news page
	 * -----------------------------------------------------------------------------------------------------
	 */
	public void OpenGnewsPage(){					
		gLanPage.click_gApp();
		gLanPage.click_gNews();
		if (driver.getTitle().equalsIgnoreCase("Google News")){
			FW_Reporter.logPASS("Google News Page Open", "Done");
		}else{
			FW_Reporter.logFATAL("Google News Page Open", "NOT OPEN");
			FW_Logger.config.setProperty("TerminateTC", "YES");
		}
	}
	
	/** SelectGapp
	 * ----------------------------------------------------------------------------------------------------
	 * @author: Brijendra Singh
	 * @Date  : May 03, 2016 
	 * @Discription: SelectGapp, select and click google app type from the icons sections
	 * -----------------------------------------------------------------------------------------------------
	 */
	public void SelectGapp(){
		String[] AppName = FW_SupportUtil.get_TestData("AppName").split(",");
		int index = Integer.parseInt(FW_SupportUtil.get_TestData("AppNameIndex"));
		putWAIT(5000);
		gLanPage.click_gApp();
		gLanPage.SelectClick_GoogApp(AppName[index]);
		index=index+1;
		FW_SupportUtil.set_TestData("AppNameIndex", ""+index);
	}
	
	/** print_news
	 * ----------------------------------------------------------------------------------------------------
	 * @author: Brijendra Singh
	 * @Date  : May 03, 2016 
	 * @Discription: print_news, print news headers from specific class to logFile
	 * -----------------------------------------------------------------------------------------------------
	 */
	public void print_news(){
		List<WebElement> gntitle =googNewsPage.newsTitles();
		if (gntitle.size()>0){
			int i=1; 
			for(WebElement ele: gntitle){		
				if (ele.getText()!=""){
					FW_Reporter.logINFO("News Item is - ", ele.getText());
				}
				i=i+1;
			}	
			FW_Reporter.logPASS("Google News Page", "News Printed");
		}else{
			FW_Reporter.logFAIL("Google news page", "News Items are not found");
		}
		
	}
		
	/** print_news1
	 * ----------------------------------------------------------------------------------------------------
	 * @author: Brijendra Singh
	 * @Date  : May 03, 2016 
	 * @Discription: print_news1, print news headers to Extent report
	 * -----------------------------------------------------------------------------------------------------
	 */
	public void print_news1(){
		List<WebElement> gntitle1 =googNewsPage.newsTitle1();
		if (gntitle1.size()>0){
			int i=1; 
			for(WebElement ele: gntitle1){
				FW_Reporter.logINFO("News " + i + "is - ", ele.getText());
				i=i+1;
			}
			FW_Reporter.logPASS("Google news page", "News Printed");
		}else{
			FW_Reporter.logFAIL("Google news page", "News Items are not found");
		}
		
	}
	
	/** LaunchApp
	 * ----------------------------------------------------------------------------------------------------
	 * @author: Brijendra Singh
	 * @Date  : May 03, 2016 
	 * @Discription: LaunchApp, open the application url in browser
	 * -----------------------------------------------------------------------------------------------------
	 */
	public void LaunchApp(){
		String appUrl = FW_SupportUtil.get_TestData("App_URL");
		//set_testdata validation
		//FW_SupportUtil.set_TestData("testData", "mozila");
		
		driver.navigate().to(appUrl);
		if (driver.getTitle().equalsIgnoreCase("Google")){
			FW_Reporter.logPASS("Google Home Page", "Title Validated");
		}else{
			FW_Reporter.logFAIL("Google Home Page", "Title is not Validated");
		}						
	}
		
	/** LaunchApp
	 * ----------------------------------------------------------------------------------------------------
	 * @author: Brijendra Singh
	 * @Date  : May 03, 2016 
	 * @Discription: LaunchApp, open the application url in browser
	 * -----------------------------------------------------------------------------------------------------
	 */
	public void Select_Click_gPlaySection(){		
		String section=FW_SupportUtil.get_TestData("gApp_section");		
		if (googPlayPage.click_gAppSection(section)==1){
			FW_Reporter.logPASS(section + " is clicked","Google Play Page");
		}else{
			FW_Reporter.logFATAL("Google Play Page", section + " is not Clicked");
		}		
		googPlayPage.click_gPlayAccountSection(FW_SupportUtil.get_TestData("gPlayAccountSection"));
		googPlayPage.click_CancelButton();
	}
	
	/** click_Gplay
	 * ----------------------------------------------------------------------------------------------------
	 * @author: Brijendra Singh
	 * @Date  : May 03, 2016 
	 * @Discription: click_Gplay, click google play option from app icons
	 * -----------------------------------------------------------------------------------------------------
	 */
	public void click_Gplay(){	
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gLanPage.click_gApp();
		gLanPage.click_gPlay();			
	}
	
	/** Validate
	 * ----------------------------------------------------------------------------------------------------
	 * @author: Brijendra Singh
	 * @Date  : May 03, 2016 
	 * @Discription: Validate, this method is under development and need to be evolve
	 * -----------------------------------------------------------------------------------------------------
	 */
	public void Validate(String actual, String compareWith){
		if (actual.equalsIgnoreCase(compareWith)){
			FW_Reporter.logPASS(actual + " is compared with :", compareWith);
		}else{		
			FW_Reporter.logFAIL( actual + " is compared with :" ,compareWith );
		}
	}
	
}
