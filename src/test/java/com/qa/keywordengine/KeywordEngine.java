package com.qa.keywordengine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.testcases.BaseSteps;

public class KeywordEngine {

	public WebDriver driver;
	public Properties prop;
	FileInputStream fis;
	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;
	public final String SCENARIO_SHEET_PATH = ".\\src\\test\\resources\\TestSteps.xlsx";
	String locatorName;
	String locatorValue;
	String action;
	String value;
	BaseSteps base;
	WebElement element;

	public void startExecution(String sheetName) {
		try {
			fis = new FileInputStream(new File(SCENARIO_SHEET_PATH));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			workbook = new XSSFWorkbook(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}

		sheet = workbook.getSheet(sheetName);
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			String locator = sheet.getRow(i).getCell(1).getStringCellValue().trim();
			if (!locator.equalsIgnoreCase("NA")) {
				locatorName = locator.split("=")[0].trim();
				locatorValue = locator.split("=")[1].trim();
			}
			action = sheet.getRow(i).getCell(2).getStringCellValue().trim();
			try {
				value = sheet.getRow(i).getCell(3).getStringCellValue().trim();
			} catch (Exception e) {
				value = String.valueOf((long) sheet.getRow(i).getCell(3).getNumericCellValue());
			}

			switch (action) {
			case "Open Browser":
				System.out.println("Open Browser: " + value);
				base = new BaseSteps();
				prop = base.init_Properties();

				if (value.isEmpty() || value.equalsIgnoreCase("NA")) {
					driver = base.init_Driver(prop.getProperty("browser"), prop.getProperty("headless"));
				} else {
					driver = base.init_Driver(value, prop.getProperty("headless"));
				}
				break;
			case "Enter URL":
				System.out.println("Enter URL: " + value);
				if (value.isEmpty() || value.equalsIgnoreCase("NA")) {
					driver.get(prop.getProperty("url"));
				} else {
					driver.get(value);
				}
				break;
			case "Close Browser":
				System.out.println("Close Browser");
				driver.quit();
				break;
			case "Wait":
				System.out.println("Wait: " + value);
				try {
					Thread.sleep(Long.parseLong(value) * 1000);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}

			if (locatorName != null) {
				switch (locatorName) {
				case "id":
					System.out.println("id");
					element = driver.findElement(By.id(locatorValue));
					if (action.equalsIgnoreCase("Sendkeys")) {
						element.clear();
						element.sendKeys(value);
					} else if (action.equalsIgnoreCase("Click")) {
						element.click();
					}
					locatorName = null;
					break;
				case "name":
					System.out.println("name");
					element = driver.findElement(By.name(locatorValue));
					if (action.equalsIgnoreCase("Sendkeys")) {
						element.clear();
						element.sendKeys(value);
					} else if (action.equalsIgnoreCase("Click")) {
						element.click();
					}
					locatorName = null;
					break;
				case "xpath":
					System.out.println("xpath");
					element = driver.findElement(By.xpath(locatorValue));
					if (action.equalsIgnoreCase("Sendkeys")) {
						element.clear();
						element.sendKeys(value);
					} else if (action.equalsIgnoreCase("Click")) {
						element.click();
					}
					locatorName = null;
					break;
				case "linkText":
					System.out.println("linkText");
					element = driver.findElement(By.linkText(locatorValue));
					element.click();
					locatorName = null;
					break;
				default:
					locatorName = null;
					break;
				}
			}
		}

	}

}
