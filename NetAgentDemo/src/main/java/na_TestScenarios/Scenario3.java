package na_TestScenarios;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class Scenario3 extends BaseInit {

	@Test
	public void searchByPUID() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		logger.info("=======TestScenario 3 Test Start=======");
		msg.append("=======TestScenario 3 Test Start=======" + "\n\n");

		// --Go to TaskLog
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idOperations")));
		isElementPresent("Operations_id").click();
		logger.info("Clicked on Operations");

		isElementPresent("TaskLog_linkText").click();
		logger.info("Clicked on TaskLog");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("panel-body")));

		getScreenshot(Driver, "TaskLog_Operations");

		// --Search with PickUPID
		String ServiceID = getData("OrderProcessing", 1, 0);
		logger.info("ServiceID is==" + ServiceID);
		msg.append("ServiceID==" + ServiceID + "\n");
		String PUID = getData("OrderProcessing", 1, 1);
		logger.info("PickUpID is==" + PUID);
		msg.append("PickUpID==" + PUID + "\n");

		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@id=\"operation\"][contains(@class,'active')]")));
			logger.info("Operation tab is already selected");

		} catch (Exception Operation) {
			logger.info("Operation tab is not selected");
			WebElement OpTab = isElementPresent("TLOperationTab_xpath");
			wait.until(ExpectedConditions.visibilityOf(OpTab));
			act.moveToElement(OpTab).build().perform();
			wait.until(ExpectedConditions.elementToBeClickable(OpTab));
			js.executeScript("arguments[0].click();", OpTab);
			logger.info("Click on Operation Tab");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		}

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
			isElementPresent("TLOSearch_id").clear();
			logger.info("Clear search input");
			isElementPresent("TLOSearch_id").sendKeys(PUID);
			logger.info("Enter PickUpID in Search input");
			WebElement OPSearch = isElementPresent("TLOSearchBTN_id");
			act.moveToElement(OPSearch).build().perform();
			js.executeScript("arguments[0].click();", OPSearch);
			logger.info("Click on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			WebElement PickuPBox = Driver.findElement(By.xpath("//*[contains(@class,'pickupbx')]"));
			if (PickuPBox.isDisplayed()) {
				logger.info("Searched Job is displayed in edit mode");
				getScreenshot(Driver, "LOCOrderEditor_" + PUID);

				// --Close the editor
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idclosetab")));
				WebElement Close = isElementPresent("TLCloseTab_id");
				act.moveToElement(Close).build().perform();
				wait.until(ExpectedConditions.elementToBeClickable(Close));
				js.executeScript("arguments[0].click();", Close);
				logger.info("Clicked on Close button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				// --Get Total Jobs
				String TotalJobs = isElementPresent("TotalJob_xpath").getText().trim();
				int TotalJob = Integer.parseInt(TotalJobs);
				logger.info("Total Jobs=" + TotalJobs);
				msg.append("Total Jobs=" + TotalJobs + "\n\n");

				if (TotalJob == 1) {
					System.out.println("Job is exist with searched pickupID=PASS");
					logger.info("Job is exist with searched pickupID=PASS");
					msg.append("Job is exist with searched pickupID=PASS" + "\n");

				} else if (TotalJob > 1) {
					System.out.println("More than 1 Jobs are exist with searched pickupID=FAIL");
					logger.info("More than 1 Jobs are exist with searched pickupID=FAIL");
					msg.append("More than 1 Jobs are exist with searched pickupID=FAIL" + "\n");

				} else {
					logger.info("Job is not exist with the search parameters");
					msg.append("Job is not exist with the search parameters" + "\n");

				}

				// --get the Job status
				List<WebElement> JobStatuses = Driver.findElements(
						By.xpath("//*[contains(@aria-label,'Column Task & Timing,')]//span[@class=\"pull-left\"]"));
				int ToatlJStatus = JobStatuses.size();

				for (int job = 0; job < ToatlJStatus; job++) {

					int JobCount = job + 1;
					String JobStatus = JobStatuses.get(job).getText();
					logger.info("Stage of " + JobCount + "st Job is=" + JobStatus);

					if (JobStatus.equalsIgnoreCase("PU DRV CONF")) {
						logger.info("Job is on PU DRV CONF stage=PASS");
						msg.append("Job is on PU DRV CONF stage=PASS" + "\n");

					} else {
						logger.info("Job is not on PU DRV CONF stage=FAIL");
						msg.append("Job is not on PU DRV CONF stage=FAIL" + "\n");

					}

				}

			}
		} catch (Exception NoData1) {
			logger.error(NoData1);
			getScreenshot(Driver, "NoData1_LOC_Error");

			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
				WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
				if (NoData.isDisplayed()) {
					logger.info("Job is not exist with the search parameters");
					msg.append("Job is not exist with the search parameters" + "\n");

				}
			} catch (Exception OnBoard) {
				logger.error(OnBoard);
				getScreenshot(Driver, "OnBoard_LOC_Error");

				String Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]")).getText();
				logger.info("Current stage of the order is=" + Orderstage);
				msg.append("Current stage of the order is=" + Orderstage + "\n");
				logger.info("Issue in Order stage==" + Orderstage);
				msg.append("Issue in Order stage==" + Orderstage + "\n");
				getScreenshot(Driver, "LOCStageIssue_" + Orderstage);

			}
		}

		// --Refresh the App
		refreshApp();

		logger.info("=======TestScenario 3 Test End=======");
		msg.append("=======TestScenario 3 Test End=======" + "\n\n");
	}

}
