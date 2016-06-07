/**
 * 
 */
package pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import frameDriver.FW_Reporter;

/**
 * @author Brijendra Singh
 *
 */
public class Po_GooglePlayPage {
	WebDriver driver;
	public Po_GooglePlayPage(WebDriver rdriver) {
		this.driver=rdriver;
	}
	
	@FindBy(xpath="//*[@id='wrapper']/div[1]/div/ul/li")
	List<WebElement> gAppSections;
	
	@FindBy(xpath="//*[@id='wrapper']/div[1]/div/div/ul/li")
	List<WebElement> gPlayAccountSection;
	
	@FindBy(xpath="//*[@id='cancel-sign-in']")
	WebElement CancelButton;
	
	public void click_CancelButton(){
		try{
			CancelButton.click();
			FW_Reporter.logPASS("Google Play-Cancel button", "Clicked");
		}catch(Throwable e){
			FW_Reporter.logFATAL("Google Play-Cancel button", "Not found, Error - " + e.getMessage());
		}
	}
	
	public int click_gAppSection(String section){
		int RetVAL = 0;
		int total_sections = gAppSections.size();
		if (total_sections>0){
			for(int i=0; i<total_sections;i++){
				if (gAppSections.get(i).getText().toString().equalsIgnoreCase(section)){
					gAppSections.get(i).click();
					RetVAL=1;
					break;
				}
			}
		}	
		return RetVAL;
	}
	
	public void click_gPlayAccountSection(String section){
		int total_sections = gPlayAccountSection.size();
		if (total_sections>0){
			for(int i=0; i<total_sections;i++){
				if (gPlayAccountSection.get(i).getText().toString().equalsIgnoreCase(section)){
					gPlayAccountSection.get(i).click();
					FW_Reporter.logPASS("gPlayAccountSection Click", section + " is Clicked");
					break;
				}
			}
		}else{
			FW_Reporter.logFAIL("gPlayAccountSection Click", section + " is not Clicked");
		}
	}
}
