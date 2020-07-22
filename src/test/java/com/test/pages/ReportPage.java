package com.test.pages;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.test.controller.ActionMethods;
import com.test.controller.FlowMethods;
import com.test.controller.Report;
import com.test.excelAPI.ExcelOperation;
import com.test.utils.Constant;

public class ReportPage extends FlowMethods {

	Logger log = LogManager.getLogger(ActionMethods.class);
	ActionMethods actionMethods = new ActionMethods();
	ExcelOperation excelOperation = new ExcelOperation();

	public void selectPreferableReport(String reportName) {
		String locator = "";
		try {

			locator = objectRepo.getProperty("Reports.ReportLink");
			actionMethods.waitFor();
			actionMethods.click(locator);

			driver.findElement(By.xpath("//input[@aria-label=\"Search Reports and Dashboards\"]")).click();
			actionMethods.waitFor();
			driver.findElement(By.xpath("//input[@aria-label=\"Search Reports and Dashboards\"]")).sendKeys(reportName);

			/*
			 * actionMethods.scrollToElement(locator);
			 * actionMethods.isAlertPresent("accept"); locator =
			 * objectRepo.getProperty("Reports.selectReport");
			 * actionMethods.click(locator.replace("Dummy", reportName));
			 */
			actionMethods.waitFor();
			driver.findElement(By.xpath(
					"//span[contains(text(),' " + reportName + " ')]//..//..//*[@class=\"fa fa-ellipsis-v fa-lg\"]"))
					.click();
			actionMethods.waitFor();
			driver.findElement(By.xpath("//*[text()=\"Run\"]")).click();
			/*
			 * locator = objectRepo.getProperty("Reports.RunInReport");
			 * actionMethods.click(locator); actionMethods.waitFor();
			 */
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),
					Constant.statusFlag + "ReportName : " + reportName, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),
					Constant.statusFlag, driver);
		}
	}

	public void selectFiscalYear(String fiscalYear) {
		String locator = "";
		try {
			locator = objectRepo.getProperty("Report.selectFiscalYear");
			actionMethods.click(locator);
			locator = objectRepo.getProperty("Report.FiscalYearDropdown");
			actionMethods.selectDropDownData(locator, fiscalYear);
			actionMethods.driver.findElement(By.xpath("//span[text()=' 2017 ']")).click();

			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),
					Constant.statusFlag + "FiscalYear : " + fiscalYear, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),
					Constant.statusFlag, driver);
		}
	}

	public void selectFiscalYearReport() {
		String locator = "";
		try {
			locator = objectRepo.getProperty("Report.CurrentYearReport");
			List<WebElement> list = actionMethods.getListOfdata(locator);
			if (list.size() >= 1) {
				Constant.statusFlag = "Passed";
			} else
				System.out.println("Current year report not presented");
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),
					Constant.statusFlag + "Total FiscalYear Reports : " + list.size(), driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),
					Constant.statusFlag, driver);
		}
	}

	public void lockAndUnlockPPRReport(String text, String status) {
		String locator = "", update = "";
		try {
			locator = objectRepo.getProperty("Report.Unlocked");
			if (actionMethods.isElementPresentOptional(locator))
				actionMethods.click(locator);
			locator = objectRepo.getProperty("Report.locked");
			actionMethods.click(locator);
			if (status.contains("Yes")) {
				locator = objectRepo.getProperty("Report.EnterReason");
				actionMethods.click(locator);
				actionMethods.enterInputMandatoryFiled(locator, text + actionMethods.genarateRandomNumber(4));
				locator = objectRepo.getProperty("ManageOu.AddCustomSaveBtn");
				actionMethods.click(locator);
				update = "UnLockedsuccessfully!";
			} else {
				locator = objectRepo.getProperty("Report.disabledSaveBtn");
				if (!driver.findElement(By.xpath(locator.split("~")[0])).isEnabled()) {
					update = "Cleint not provided reasons because Save button disabled.";
				}
				locator = objectRepo.getProperty("Indicator.CancelBtn");
				if (actionMethods.isElementPresentOptional(locator))
					actionMethods.click(locator);
			}
			Report.getInstance().generateReport(
					Thread.currentThread().getStackTrace()[1].getMethodName() + "getScreenShot",
					Constant.statusFlag + update, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),
					Constant.statusFlag + e.getMessage(), driver);
		}
	}

	public void selectPMPOffices(String officeName) {
		String locator = "";
		try {
			locator = objectRepo.getProperty("Report.SelectPMPOffice");
			actionMethods.click(locator);
			for (int i = 0; i < officeName.split(",").length; i++) {
				locator = objectRepo.getProperty("Report.SelectPMPOfficeDropdown");
				actionMethods.selectDropDownData(locator, officeName.split(",")[i]);
			}
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),
					Constant.statusFlag + "Selected Office Names : " + officeName, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),
					Constant.statusFlag, driver);
			throw e;
		}
	}

	public void selectPMPProjects(String projectNames) {
		String locator = "";
		try {
			locator = objectRepo.getProperty("Report.SelectPMPProejct");
			actionMethods.click(locator);
			for (int i = 0; i < projectNames.split(",").length; i++) {
				locator = objectRepo.getProperty("Report.SelectPMPProjectDropdown");
				actionMethods.selectDropDownData(locator, projectNames.split(",")[i]);
			}
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),
					Constant.statusFlag + "Selected Project Names : " + projectNames, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),
					Constant.statusFlag, driver);
			throw e;
		}
	}

	public void runReport() {
		String locator = "";
		try {
			locator = objectRepo.getProperty("Report.RunPMPReport");
			actionMethods.click(locator);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),
					Constant.statusFlag, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),
					Constant.statusFlag, driver);
			throw e;
		}
	}

	public void report_ExcelToExport() {
		String locator = "";
		try {
			locator = objectRepo.getProperty("Report.ReportToExcel");
			actionMethods.click(locator);
			actionMethods.waitFor();
			Report.getInstance().generateReport(
					Thread.currentThread().getStackTrace()[1].getMethodName() + "getScreenShot", Constant.statusFlag,
					driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),
					Constant.statusFlag, driver);
			throw e;
		}
	}

	public void selectOperationUnit(String opUnit) {
		String locator = "";
		try {
			locator = objectRepo.getProperty("Reports.OperatingUnit");
			actionMethods.click(locator);
			locator = objectRepo.getProperty("Reports.OperatingunitDropdown");
			actionMethods.selectDropDownData(locator, opUnit);
			WebElement element = driver.switchTo().activeElement();
			element.sendKeys(Keys.ESCAPE);
			Report.getInstance().generateReport(
					Thread.currentThread().getStackTrace()[1].getMethodName() + "user selected: " + opUnit,
					Constant.statusFlag, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),
					Constant.statusFlag, driver);
			throw e;
		}
	}

	public void selectOfficeName(String offName) throws Exception {
		String locator = "";
		try {
			locator = objectRepo.getProperty("Reports.OfficeName");
			actionMethods.click(locator);
//			//locator = objectRepo.getProperty("Reports.OfficeNameDRopdown");
//			if ("All".equalsIgnoreCase(offName)) {
//				locator = objectRepo.getProperty("Reports.OffAll");
//				actionMethods.click(locator);
			
//				} else {
//				actionMethods.selectDropDownData(locator, offName);
			System.out.println("|"+offName.trim()+"|");
			Thread.sleep(5000);
			driver.findElement(By.xpath("//*[text()=' "+offName.trim()+" ']")).click();
			
			//driver.findElement(By.xpath("//*[text()=' " + offName + " ' ]")).click();
			;

			System.out.println(offName);
			WebElement element = driver.switchTo().activeElement();
			element.sendKeys(Keys.ESCAPE);

			Report.getInstance().generateReport(
					Thread.currentThread().getStackTrace()[1].getMethodName() + "user selected: " + offName,
					Constant.statusFlag, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),
					Constant.statusFlag, driver);
			throw e;
		}
	}

	public void selectPartnerName(String partName) throws Exception {
		String locator = "";
		try {
			locator = objectRepo.getProperty("Reports.PartnerName");
			actionMethods.click(locator);
			Thread.sleep(5000);
			driver.findElement(By.xpath("//*[text()=' "+partName.trim()+" ']")).click();
			//locator = objectRepo.getProperty("Reports.PartnerNameDropdown");
			//locator = objectRepo.getProperty("Reports.OfficeNameDRopdown");
//			if ("All".equalsIgnoreCase(partName)) {
//				locator = objectRepo.getProperty("Reports.PaAll");
//				actionMethods.click(locator);
//
//			} else
//				actionMethods.selectDropDownData(locator, partName);
			WebElement element = driver.switchTo().activeElement();
			element.sendKeys(Keys.ESCAPE);
			Report.getInstance().generateReport(
					Thread.currentThread().getStackTrace()[1].getMethodName() + "user selected: " + partName,
					Constant.statusFlag, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),
					Constant.statusFlag, driver);
			throw e;
		}
	}

	public void activityStatus(String partName) throws Exception {
		String locator = "";
		try {
			locator = objectRepo.getProperty("Reports.ActivityStatus");
			actionMethods.click(locator);
			Thread.sleep(5000);
			driver.findElement(By.xpath("//span[text()=' "+partName.trim()+" ']")).click();
//			locator = objectRepo.getProperty("Reports.ActivityStatusDropdown");
//			actionMethods.selectDropDownData(locator, partName);
			WebElement element = driver.switchTo().activeElement();
			element.sendKeys(Keys.ESCAPE);
			Report.getInstance().generateReport(
					Thread.currentThread().getStackTrace()[1].getMethodName() + "user selected: " + partName,
					Constant.statusFlag, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),
					Constant.statusFlag, driver);
			throw e;
		}
	}

	public void runPortfioliReport() {
		String locator = "";
		try {
			locator = objectRepo.getProperty("Reports.RunReport");
			actionMethods.scrollToElement(locator);

			Report.getInstance().generateReport(
					Thread.currentThread().getStackTrace()[1].getMethodName() + "user selected runReport ",
					Constant.statusFlag, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),
					Constant.statusFlag, driver);
			throw e;
		}
	}

}
