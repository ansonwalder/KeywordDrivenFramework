package com.qa.testcases;

import org.testng.annotations.Test;

import com.qa.keywordengine.KeywordEngine;

public class LoginTest {
	public KeywordEngine keywordEngine;

	@Test
	public void loginTest() {
		keywordEngine = new KeywordEngine();
		keywordEngine.startExecution("TC001");
	}
}