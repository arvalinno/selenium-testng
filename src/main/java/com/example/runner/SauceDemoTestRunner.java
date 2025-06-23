package com.example.runner;

import java.net.URL;
import java.util.Collections;
import org.testng.TestNG;

public class SauceDemoTestRunner {
    public static boolean run() {
        try {
            URL suiteXml = SauceDemoTestRunner.class
                .getClassLoader()
                .getResource("testng.xml");

            if (suiteXml == null) {
                throw new RuntimeException("❌ testng.xml not found in classpath");
            }

            TestNG testng = new TestNG();
            testng.setTestSuites(Collections.singletonList(suiteXml.toURI().toString()));
            testng.setDefaultSuiteName("SauceSuite");
            testng.setDefaultTestName("LoginTest");
            testng.setVerbose(2);
            testng.run();

            return !testng.hasFailure() && !testng.hasSkip();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
