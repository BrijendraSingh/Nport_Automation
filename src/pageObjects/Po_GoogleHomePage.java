/**
 * 
 */
package pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import frameDriver.FW_Reporter;
import frameDriver.FW_Logger;

/**
 * @author Brijendra Singh
 *
 */
public class Po_GoogleHomePage {
	WebDriver driver;
	
	public Po_GoogleHomePage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
	}
	
	@FindBy(id="gb_70")
	WebElement gSignIn;
	
	@FindBy(xpath="//*[@id='gbwa']/div[1]/a")
	WebElement gApp;
	
	@FindBy(linkText="News")
	WebElement gNews;
	
	@FindBy(linkText="Play")
	WebElement gPlay;
	
	@FindBy(xpath="//*[@id='gbwa']/div[2]/ul[1]/li")
	List<WebElement> gAppIcons;
	
	public void SelectClick_GoogApp(String AppName){
		int RetVAL=0;
		if (gAppIcons.size()>0){
			for (int i=0;i<gAppIcons.size();i++){
				if (gAppIcons.get(i).getText().equalsIgnoreCase(AppName)){
					gAppIcons.get(i).click();
					RetVAL=1;
					FW_Reporter.logPASS("Google Application Selected", AppName);
					break;
				}
			}
		}
		if ( RetVAL==0){
			FW_Reporter.logFAIL("Google Application not Selected", AppName);
		}
	}
	
	
	public void click_gSignIn(){
		gSignIn.click();
	}
	
	public void click_gApp(){
		try {
			gApp.click();
			FW_Reporter.logPASS("Google app Icon Click","Done");
		}catch(Throwable e) {
			FW_Reporter.logFATAL("Google App Icon Click, NOT DONE", e.getMessage());
		}
		
	}
	
	public void click_gNews(){
		try {
			gNews.click();
			FW_Reporter.logPASS("Google News click","Done");
		}catch(Throwable e) {
			FW_Reporter.logFATAL("Google News Click Error", e.getMessage());
			FW_Logger.config.setProperty("TerminateTC", "YES");
		}
	}	
	
	public void click_gPlay(){
		try {
			gPlay.click();
			FW_Reporter.logPASS("Google Play click","Done");
		}catch(Throwable e) {
			FW_Reporter.logFATAL("Google Play Click Error", e.getMessage());
		}
	}
}
