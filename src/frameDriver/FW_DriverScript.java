/**
 * 
 */
package frameDriver;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import keywordsLib.Aut_Keywords;

/** FW_DriverScript
 * ----------------------------------------------------------------------------------------------------
 * @author: Brijendra Singh
 * @Date  : May 03, 2016 
 * @Discription: "main" method of the framework, setup the framework dependability and trigger the 
 * 				 execution
 * -----------------------------------------------------------------------------------------------------
 */
public class FW_DriverScript {
	
	//Object declaration 
	static WebDriver driver;
	static BufferedWriter log_file;
	static Workbook RunManager;
	static Workbook TestData;
	static Properties config;
	static Aut_Keywords B_lib=null;
	static String TC_Name;
	static Sheet TD_Sheet;
	
	ExtentReports extent;
	ExtentTest test;
	static ExtentTest ChiledTest;
		
	/** init_FW
	 * ----------------------------------------------------------------------------------------------------
	 * @author: Brijendra Singh
	 * @Date  : May 03, 2016 
	 * @Discription: "main" method of the framework, setup the framework dependability and trigger the execution
	 * -----------------------------------------------------------------------------------------------------
	 */
	public void init_FW() throws IOException{

		String RunManagerName,TestDataName,MainSheetName;
		
		//configuration file operation
		config=FW_Logger.ConfiGFileSetup();
		System.out.println("Framework CONFIGURATION FILE loaded");
		
		//log file management
		log_file=FW_Logger.createLogFile();
		FW_Logger.logThis("Config & Log file", "Setup", "Completed");
		System.out.println("Framework LOG FILE loaded");
		
		//extent reporter
		FW_Reporter.StartReporter();
		
		FW_Logger.logThis("Config file setup", "", "Done");
		RunManagerName=config.getProperty("RunManagerFile");
		TestDataName=config.getProperty("TestDataFile");
		MainSheetName=config.getProperty("MainSheet");
		
		//RunManager and TestData file Objects		
		RunManager =  FW_XlSetup.connectExcelFiles(RunManagerName+".xlsx");
		TestData = FW_XlSetup.connectExcelFiles(TestDataName+".xlsx");
		
		//get Main sheet of run manager
		Sheet mainSheet = RunManager.getSheet(MainSheetName);
		FW_Logger.logThis("Excel Files", "", "Connected");
		
		//-----read modules to execute form main sheet
		int MS_usedrow;    
		MS_usedrow = mainSheet.getLastRowNum()-mainSheet.getFirstRowNum();
		for (int MS_rc=1;MS_rc<MS_usedrow;MS_rc++){
			Row MS_row = mainSheet.getRow(MS_rc);
			if (MS_row.getCell(1).toString().equalsIgnoreCase("true")){
				Sheet module = RunManager.getSheet(MS_row.getCell(0).toString());
				TD_Sheet=TestData.getSheet(MS_row.getCell(0).toString());
				FW_Logger.logThis("Connected Module", module.getSheetName(), "Execution [IN]");
				System.out.println("----- " + module.getSheetName()+ " -----");
				
				//------read test cases to execute from modules sheet
				int mod_usedrow;
				mod_usedrow=module.getLastRowNum()-module.getFirstRowNum();
				for (int mod_rc=1;mod_rc<mod_usedrow;mod_rc++){
					Row mod_row = module.getRow(mod_rc);
					if (mod_row.getCell(1).toString().equalsIgnoreCase("true")){
						FW_Logger.logThis("Connected TestCase", mod_row.getCell(0).toString(), "Execution [IN]");
						System.out.println("TC@ - "+mod_row.getCell(0).toString());
						TC_Name=mod_row.getCell(0).toString();
						FW_SupportUtil.setTestDataSheet(TD_Sheet,TC_Name);
						int row= TD_Sheet.getLastRowNum()-TD_Sheet.getFirstRowNum();
						
						//----start reporter test and log TC details
						//@ Start Reporter
						FW_Reporter.StartReporterTest(mod_row.getCell(0).toString(),mod_row.getCell(2).toString());
						FW_Reporter.setMODULE(module.getSheetName());
						
						//----read keywords to execute
						int mod_usedcol = mod_row.getLastCellNum()-mod_row.getFirstCellNum();
						for (int mod_cc=3;mod_cc<mod_usedcol;mod_cc++){
							if (mod_row.getCell(mod_cc).toString().equalsIgnoreCase("")){
								break;
							}else{
								
								//----launch the code
								String keyword = mod_row.getCell(mod_cc).toString();
								FW_Logger.logThis("Keyword", keyword, "Execution [IN]");
								System.out.println(keyword + "Executing [IN]" );
								
								//@@Child Test-Keyword
								ChiledTest=FW_Reporter.StartChiled_ReporterTest(keyword);
								
								if (keyword.equalsIgnoreCase("LaunchBrowser")){
									driver=FW_BrowserSetup.LaunchBrowser();
									B_lib =  new Aut_Keywords(driver);
								}else{
									Method method;
									try {
										//launch keyword
										method = Aut_Keywords.class.getDeclaredMethod(keyword,null);
										method.invoke(B_lib, null);	
										if (FW_Logger.config.getProperty("TerminateTC").equalsIgnoreCase("yes")){
											FW_Reporter.logFATAL("Terminating This TC Execution", "FATAL ERROR");
											FW_Logger.config.setProperty("TerminateTC", "NO");
											driver.close();
											driver.quit();
											break;
										}
									} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
										System.out.println("Problem execution/Invoking the Keyword " + keyword + ", Casue - " + e.getCause()+ " , Message: " + e.getStackTrace());
										FW_Reporter.logUNKNOWN("Problem execution/Invoking the Keyword " + keyword , ", Casue - " + e.getCause() + " | Message- "+ e.getMessage());
										FW_Logger.logThis("Problem execution/Invoking the Keyword", keyword, e.getMessage());
									}
								}
								FW_Reporter.Append_ChildTest(ChiledTest);
								FW_Logger.logThis("Keyword", keyword, "Execution [OUT]");
							}
						}
						FW_Logger.logThis("Connected TestCase", mod_row.getCell(0).toString(), "Execution [OUT]");
						FW_Reporter.endTest();
					}
				}
			}
		}		
		FW_Reporter.flushReporter();
		FW_Logger.logThis("Final Result", "TestReport", "OPEN UP");
		FW_Reporter.OpenResult();
		log_file.close();
		
	}
}
