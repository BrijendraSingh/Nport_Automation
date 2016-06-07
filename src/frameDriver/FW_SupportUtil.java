/**
 * 
 */
package frameDriver;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/** FW_SupportUtil
 * ----------------------------------------------------------------------------------------------------
 * @author: Brijendra Singh
 * @Date  : May 03, 2016 
 * @Discription: FW_SupportUtil, manages the excel operations such as retrieving the test
 * 				 cases its keywords and getTest data and setTestData
 * -----------------------------------------------------------------------------------------------------
 */
public class FW_SupportUtil {
	static String TC_Name;
	static Sheet TD_Sheet;
	
	/** setTestDataSheet
	 * ----------------------------------------------------------------------------------------------------
	 * @author: Brijendra Singh
	 * @Date  : May 18, 2016 
	 * @Discription: setTestDataSheet, set the row for currently executed test case row of the RunManager and TestData
	 * 				 Sheet 
	 * -----------------------------------------------------------------------------------------------------
	 */
	public static void setTestDataSheet(Sheet TDSheet, String TcName) {
		TD_Sheet = TDSheet;
		TC_Name=TcName;
	}
	
	/** set_TestData, String TestData
	 * ----------------------------------------------------------------------------------------------------
	 * @author: Brijendra Singh
	 * @Date  : May 03, 2016 
	 * @Discription: set_TestData, Take the runtime testData from the script and set it to the TestData sheet
	 * 				 under provided testData Parameter as a future step verification
	 * -----------------------------------------------------------------------------------------------------
	 */
	public static void set_TestData(String key , String testData){
		int used_col = TD_Sheet.getRow(0).getLastCellNum()-TD_Sheet.getRow(0).getFirstCellNum();
		int fc_col=1;
		for ( int fr_cc=2; fr_cc<used_col;fr_cc++){
			if (TD_Sheet.getRow(0).getCell(fr_cc).toString().equalsIgnoreCase(key) ){
				//td_row.createCell(fr_cc).setCellValue(testData.toString());
				//FW_Reporter.logINFO("Set_TestData: DataParameter- ["+key+"]", " is set to- ["+testData + "]");
				//System.out.println("Set row value for Data Parameter - ("+key+ ") is set to: " + td_row.getCell(fr_cc));
				fc_col=fr_cc;
				break;
			}
		}
		
		int used_row = TD_Sheet.getLastRowNum()-TD_Sheet.getFirstRowNum();
		for (int fc_row=0; fc_row<used_row;fc_row++){
			if (TD_Sheet.getRow(fc_row).getCell(0).toString().equals(TC_Name)){
				TD_Sheet.getRow(fc_row).createCell(fc_col).setCellValue(testData.toString());
				FW_Reporter.logINFO("Set_TestData: DataParameter- ["+key+"]", " is set to- ["+testData + "]");
				System.out.println("Set row value for Data Parameter - ("+key+ ") is set to: " + TD_Sheet.getRow(fc_row).getCell(fc_col));
				break;
			}
		}
	}
	
	
	/** get_TestData
	 * ----------------------------------------------------------------------------------------------------
	 * @author: Brijendra Singh
	 * @Date  : May 03, 2016 
	 * @Discription: get_TestData, Take the testData from the testData sheet for corresponding parameter
	 * -----------------------------------------------------------------------------------------------------
	 */
	public static String get_TestData(String key){
		String keyVal="";
		String commonDataIdentifier, CommonDataSheetName;
		int row_num=1;
		
		int used_row = TD_Sheet.getLastRowNum()-TD_Sheet.getFirstRowNum();
		for (int fc_row=1; fc_row<used_row;fc_row++){
			if (TD_Sheet.getRow(fc_row).getCell(0).toString().equals(TC_Name)){
				row_num=fc_row;
				//TD_Sheet.getRow(fc_row).createCell(fc_col).setCellValue(testData.toString());
				//FW_Reporter.logINFO("Set_TestData: DataParameter- ["+key+"]", " is set to- ["+testData + "]");
				//System.out.println("Set row value for Data Parameter - ("+key+ ") is set to: " + TD_Sheet.getRow(fc_row).getCell(fc_col));
				break;
			}
		}
		
		int used_col = TD_Sheet.getRow(0).getLastCellNum()-TD_Sheet.getRow(0).getFirstCellNum();
		for ( int fr_cc=2; fr_cc<used_col;fr_cc++){
			if (TD_Sheet.getRow(0).getCell(fr_cc).toString().equalsIgnoreCase(key) ){
				keyVal=TD_Sheet.getRow(row_num).getCell(fr_cc).getStringCellValue();
				break;
			}
		}	
		
		commonDataIdentifier=FW_Logger.config.getProperty("CommonDataIdentifier");
		CommonDataSheetName=FW_Logger.config.getProperty("CommonnDataSheet_Name");
		if (keyVal.indexOf(commonDataIdentifier)==0){
			Sheet CDSheet = FW_DriverScript.TestData.getSheet(CommonDataSheetName);
			String CD_name = keyVal.substring(1).toString();
			
			int CD_usedRow, CD_usedCol;
			CD_usedRow=CDSheet.getLastRowNum()-CDSheet.getFirstRowNum();
			CD_usedCol=CDSheet.getRow(0).getLastCellNum()-CDSheet.getRow(0).getFirstCellNum();
			for(int loopR=1;loopR<CD_usedRow;loopR++){
				Row CD_row = CDSheet.getRow(loopR);
				if (CD_row.getCell(0).toString().equalsIgnoreCase(CD_name)){
					//find out the coulumn
					for (int loopC=1;loopC<CD_usedCol;loopC++){
						if (CDSheet.getRow(0).getCell(loopC).toString().equalsIgnoreCase(key)){
							keyVal=CDSheet.getRow(loopR).getCell(loopC).toString();
							System.out.println(key + " is(CommonData) " + keyVal);
							break;
						}
					}
				}
			}
		}
		
		return keyVal;		
	}
	
}
