package com.qa.testcases;

import org.testng.annotations.Test;

import com.qa.keywordengine.KeywordEngine;

public class CreateNewAccountTest {
	public KeywordEngine keywordEngine;

	@Test
	public void createNewAccountTest() {
		keywordEngine = new KeywordEngine();
		keywordEngine.startExecution("TC002");
	}
}
