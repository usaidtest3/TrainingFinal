package com.test.pages;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.test.controller.ActionMethods;
import com.test.controller.FlowMethods;
import com.test.controller.Report;
import com.test.excelAPI.ExcelOperation;
import com.test.testData.ProjectTestData;
import com.test.utils.Constant;

public class StrategiesPage extends FlowMethods {
	Logger log = LogManager.getLogger(ActionMethods.class);
	ActionMethods actionMethods = new ActionMethods();
	ExcelOperation excelOperation = new ExcelOperation();

	public void navigteToStrategisScreen() {
		try {
			actionMethods.waitFor();
			String locator = objectRepo.getProperty("Strategies.Link");
			actionMethods.click(locator);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),
					Constant.statusFlag + "User navigate to Strategic screen : ", driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),
					Constant.statusFlag, driver);
			throw e;
		}
	}

	public void CreateStrategyAndCancel() {
		String locator = "";
		try {
			System.out.println("entered into create strategy");
			actionMethods.waitFor();
			locator = objectRepo.getProperty("strategies.createNewStrategy");
			actionMethods.click(locator);
			
			System.out.println("executed before steup");
			actionMethods.waitFor();
			String cdsName = "CdsName"+actionMethods.genarateRandomNumber(6);
          System.out.println(cdsName);
			locator = objectRepo.getProperty("strategies.cdcsName");
		    actionMethods.enterInputMandatoryFiled(locator, cdsName);
		    actionMethods.waitFor();
			
		
			 Thread.sleep(5000);
			driver.findElement(By.xpath("(//*[@class=\"fa fa-calendar\"])[2]")).click();
			Thread.sleep(5000);
			
		
			
			try {
				actionMethods.scrollToElement("//a[contains(@class,'ui-state-highlight ng-star-inserted')]~Xpath");
				Thread.sleep(3000);
				driver.findElement(By.xpath("//a[contains(@class,'ui-state-highlight ng-star-inserted')]")).click();
			} catch (Exception e) {
				System.out.println("Error in selecting date");
			}
			
			Thread.sleep(3000);
			System.out.println("After date selectino");
			driver.findElement(By.xpath("//*[@aria-label=\"Strategy Name\"]")).click();
			locator = objectRepo.getProperty("strategies.cdcsName");
			actionMethods.click(locator);
			Thread.sleep(5000);
			locator = objectRepo.getProperty("strategis.NewCancel");
			actionMethods.click(locator);
actionMethods.waitFor();
			locator = objectRepo.getProperty("Strategies.Cancel2");
			actionMethods.click(locator);
		//	driver.findElement(By.xpath("//*[text()=' SAVE ']")).click();

		    System.out.println("after clicking on cancel");
		    Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),
					Constant.statusFlag + " Success" , driver);
		}
		  catch(Exception e ) {
			  System.out.println(e.getMessage());
				Constant.statusFlag = "Failed";
			  Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),
						Constant.statusFlag, driver);
		  }
		    
		}
		

	public void validateTheGoalStatementFields() {
		String locator = "", msg = "";
		try {
			locator = objectRepo.getProperty("Strategis.GoalStatement");
			if (actionMethods.isElementPresent(locator)) {
				locator = objectRepo.getProperty("Strategis.StartDate");
				if (actionMethods.isAlertPresent(locator)) {
					locator = objectRepo.getProperty("Strategis.EndDate");
					if (actionMethods.isAlertPresent(locator)) {
						msg = "Goal settings, start date & end date displayed successfully!";
					}
				}
			}
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),
					Constant.statusFlag + msg, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),
					Constant.statusFlag, driver);
			throw e;
		}
	}

	public void editGoalStatement(String goalStmt) {
		String locator = "";
		try {
			locator = objectRepo.getProperty("Strategis.GoalStatement");
			actionMethods.enterInputMandatoryFiled(locator, goalStmt);
			locator = objectRepo.getProperty("Strategis.UpdateButton");
			actionMethods.click(locator);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),
					Constant.statusFlag + "Edited Goal stmt : " + goalStmt, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),
					Constant.statusFlag, driver);
			throw e;
		}
	}

	public void discardGoalStatementChanges(String goalStmt, String action) throws Exception {
		String locator = "";
		try {
			locator = objectRepo.getProperty("Strategies.EditGoal");

			actionMethods.waitFor();
			if (actionMethods.isElementPresentOptional(locator))
				actionMethods.click(locator);
			locator = objectRepo.getProperty("Strategis.GoalStatement");
			actionMethods.enterInputMandatoryFiled(locator, goalStmt);
			actionMethods.waitFor();
			locator = objectRepo.getProperty("Strategis.Cancel");
			actionMethods.click(locator);

			locator = objectRepo.getProperty("Strategis.WarningMsg");
			System.out.println("Before Yes or no");
			if (actionMethods.isElementPresent(locator)) {
				System.out.println("verified the warning window");
				if (action.contains("Yes")) {
					locator = objectRepo.getProperty("Strategis.YesBtn");
					actionMethods.click(locator);
					actionMethods.waitFor();
					afterCancel_YES_NavigateToStrategisScreen();
				} else {
					System.out.println("User has selected NO option");
					locator = objectRepo.getProperty("Strategis.NoBtn");
					actionMethods.click(locator);
					actionMethods.waitFor();
					validateTheGoalStatementFields();
					locator = objectRepo.getProperty("Strategis.UpdateButton");
					actionMethods.click(locator);
				}
			} else {
				System.out.println("Warning message not displayed");
			}

			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),
					Constant.statusFlag + "Edited Goal stmt : " + goalStmt + "User selected : " + action, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),
					Constant.statusFlag + "User selected : " + action, driver);
			throw e;
		}
	}

	public void afterCancel_YES_NavigateToStrategisScreen() throws Exception {
		String locator = "";
		try {
			Thread.sleep(3000);
			locator = objectRepo.getProperty("Strategies.Header");
			actionMethods.isElementPresent(locator);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),
					Constant.statusFlag + "After select 'Yes' user navigated to 'Strategis' screen", driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),
					Constant.statusFlag, driver);
			throw e;
		}
	}

	public void CreateStrategy() throws Exception {
		String locator = "";
		try {
			System.out.println("entered into create strategy");
			actionMethods.waitFor();
			locator = objectRepo.getProperty("strategies.createNewStrategy");
			actionMethods.click(locator);

			System.out.println("executed before steup");
			actionMethods.waitFor();
			String cdsName = "CdsName" + actionMethods.genarateRandomNumber(6);
			System.out.println(cdsName);
			locator = objectRepo.getProperty("strategies.cdcsName");
			actionMethods.enterInputMandatoryFiled(locator, cdsName);
			actionMethods.waitFor();

			Thread.sleep(5000);
			driver.findElement(By.xpath("(//*[@class=\"fa fa-calendar\"])[2]")).click();
			Thread.sleep(5000);

			try {
				actionMethods.scrollToElement("//a[contains(@class,'ui-state-highlight ng-star-inserted')]~Xpath");
				Thread.sleep(3000);
				driver.findElement(By.xpath("//a[contains(@class,'ui-state-highlight ng-star-inserted')]")).click();
			} catch (Exception e) {
				System.out.println("Error in selecting date");
			}

			Thread.sleep(3000);
			System.out.println("After date selectino");
			driver.findElement(By.xpath("//*[@aria-label=\"Strategy Name\"]")).click();
			locator = objectRepo.getProperty("strategies.cdcsName");
			actionMethods.click(locator);
//			WebElement ele=ActionMethods.driver.switchTo().activeElement();
//			ele.sendKeys(By..ESCAPE);
			// String enddate="actionMethods.enterInputMandatoryFiled(locator, startdate);";

			// actionMethods.enterInputMandatoryFiled(locator, enddate);
			// actionMethods.waitFor();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//*[text()=' SAVE ']")).click();
//			locator = objectRepo.getProperty("strategis.NewSave");
//		    actionMethods.click(locator);
			System.out.println("after clicking on save");
//			

			// String path = System.getProperty(("user.dir"));
			// String userName = System.getProperty("user.name");
			// actionMethods.uploadFile("C:\\Users\\"+userName+"\\Desktop\\24-06-2019-cab.pdf");

			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),
					Constant.statusFlag + "FileUploaded : ", driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";
			// Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),
			// Constant.statusFlag, driver);
			throw e;
		}
	}

	public String getCurrentDate(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = new Date();
		return sdf.format(date);
	}

	public String selectCalanderDate(String selectDate) {
		String locator = "";
		int dateIndex;
		String date = "";
		try {
			// locator = objectRepo.getProperty("");
			// actionMethods.click(locator);
			dateIndex = Integer.parseInt(selectDate);
			if (dateIndex == 0) {
				date = getCurrentDate("dd");
				locator = objectRepo.getProperty("Project.selectDate");
				actionMethods.click(locator.replace("Dummy", date));

			} else {
				date = getCurrentDate("dd");
				locator = objectRepo.getProperty("Project.selectDate");
				int i = Integer.parseInt(date) + dateIndex;
				actionMethods.click(locator.replace("Dummy", String.valueOf(i)));
			}

		} catch (Exception e) {

		}
		return date;
	}

	public void autoIt(String fileName) throws IOException {
		String fileLocation = System.getProperty("user.dir") + "\\src\\test\\java\\uploadDocuments\\" + fileName;
		fileLocation = fileLocation.replace("\\", "\\\\");
		String fileToUpload;

		fileToUpload = fileLocation.replaceAll("//", Matcher.quoteReplacement("\\"));
		StringSelection filePath = new StringSelection(fileToUpload);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(filePath, null);
		if (Constant.browser.equalsIgnoreCase("Chrome")) {
			Runtime.getRuntime().exec(System.getProperty("user.dir")
					+ "\\src\\test\\java\\uploadDocuments\\fileUpload.exe " + fileToUpload);
		}

	}

}
