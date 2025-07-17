package com.example.runner;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;

import org.testng.TestNG;

import com.example.listener.ResultCollectorListener;

public class SauceDemoTestRunner {
    public static boolean run() {
        try {
            InputStream is = SauceDemoTestRunner.class
                .getClassLoader()
                .getResourceAsStream("testng.xml");
            if (is == null) {
                throw new RuntimeException("❌ testng.xml not found");
            }

            Path tempFile = Files.createTempFile("testng", ".xml");
            Files.copy(is, tempFile, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("is = " + is);
            System.out.println("tempFile = " + tempFile);

            TestNG testng = new TestNG();
            testng.setTestSuites(List.of(tempFile.toAbsolutePath().toString()));
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
