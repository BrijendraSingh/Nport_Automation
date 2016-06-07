/**
 * 
 */
package frameDriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;

/** FW_BrowserSetup
 * ----------------------------------------------------------------------------------------------------
 * @author: Brijendra Singh
 * @Date  : May 03, 2016 
 * @Discription: "FW_BrowserSetup" It Invoke and initiate the browser with selected type
 * -----------------------------------------------------------------------------------------------------
 */
public class FW_BrowserSetup {
	
	static WebDriver driver;
	
	public static WebDriver LaunchBrowser(){
		String typeBrowser= FW_SupportUtil.get_TestData("Browser_Name");
		String FF_Profile = FW_Logger.config.getProperty("Firfox_Profile");
		System.out.println("Browser name is: " + typeBrowser);
		if(typeBrowser.equalsIgnoreCase("chrome")){
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"Drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		}else if (typeBrowser.equalsIgnoreCase("mozila")){
			ProfilesIni profile = new ProfilesIni();
			FirefoxProfile ffprofile = profile.getProfile(FF_Profile);
			driver = new FirefoxDriver(ffprofile);
		}else if (typeBrowser.equalsIgnoreCase("ie")){
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"Drivers\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}else{
			ProfilesIni profile = new ProfilesIni();
			FirefoxProfile ffprofile = profile.getProfile(FF_Profile);
			driver = new FirefoxDriver(ffprofile);
			
		}
			
		//driver.manage().window().maximize();
		driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		FW_Logger.logThis(typeBrowser + " Browser launched", "", "");
		FW_Reporter.logINFO(typeBrowser , " Launched successfully");
		
		return driver;
	}
	
	public static WebDriver getMainDriver(){
		return driver;
	}

}
