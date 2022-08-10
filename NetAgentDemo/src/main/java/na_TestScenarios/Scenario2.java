package na_TestScenarios;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class Scenario2 extends BaseInit {

	@Test
	public void getTotalJobs() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 50);

		logger.info("=======TestScenario 2 Test Start=======");
		msg.append("=======TestScenario 2 Test Start=======" + "\n\n");

		// --Go to TaskLog
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idOperations")));
		isElementPresent("Operations_id").click();
		logger.info("Clicked on Operations");

		isElementPresent("TaskLog_linkText").click();
		logger.info("Clicked on TaskLog");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("panel-body")));

		getScreenshot(Driver, "TaskLog_Operations");

		// --Check the Record
		WebElement NoData = isElementPresent("NoData_className");
		if (NoData.isDisplayed()) {
			// --Get Total Jobs
			String TotalJobs = isElementPresent("TotalJob_xpath").getText();
			logger.info("Total Jobs=" + TotalJobs);
			System.out.println("Data is not present related search parameter=FAIL");
			logger.info("Data is not present related search parameter=FAIL");
			msg.append("Data is not present related search parameter=FAIL" + "\n");

		} else {
			System.out.println("Data is present related search parameter=PASS");
			logger.info("Data is present related search parameter=PASS");
			msg.append("Data is present related search parameter=PASS" + "\n");

			// --Get Total Jobs
			String TotalJobs = isElementPresent("TotalJob_xpath").getText().trim();
			int TotalJob = Integer.parseInt(TotalJobs);
			logger.info("Total Jobs=" + TotalJobs);
			msg.append("Total Jobs=" + TotalJobs + "\n\n");

			if (TotalJob == 3) {
				System.out.println("Total jobs are matched with assigned job count=PASS");
				logger.info("Total jobs are matched with assigned job count=PASS");
				msg.append("Total jobs are matched with assigned job count=PASS" + "\n");

			} else {
				System.out.println("Total jobs are not matched with assigned job count=FAIL");
				logger.info("Total jobs are not matched with assigned job count=FAIL");
				msg.append("Total jobs are not matched with assigned job count=FAIL" + "\n");

			}

		}

		// --Refresh the App
		refreshApp();

		logger.info("=======TestScenario 2 Test End=======");
		msg.append("=======TestScenario 2 Test End=======" + "\n\n");
	}

}
