package com.example.runner;

import java.net.URL;
import java.util.Collections;
import org.testng.TestNG;

import com.example.listener.ResultCollectorListener;

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
            // testng.setDefaultSuiteName("SauceDemoSuite");
            testng.setDefaultTestName("SimpleTest");
            testng.addListener(new ResultCollectorListener());
            testng.setVerbose(2);
            testng.run();

            System.out.println("\n=== ✅ Test Result Summary ===");

            System.out.println("✅ Passed Tests:");
            ResultCollectorListener.passedTests.forEach(name -> System.out.println("  - " + name));

            System.out.println("❌ Failed Tests:");
            ResultCollectorListener.failedTests.forEach(name -> System.out.println("  - " + name));

            System.out.println("⚠️ Skipped Tests:");
            ResultCollectorListener.skippedTests.forEach(name -> System.out.println("  - " + name));


            return !testng.hasFailure() && !testng.hasSkip();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
