package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.testng.TestNG;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

@Controller
public class TestController {
 
    private final Map<String, String> testResults = new ConcurrentHashMap<>();

    @GetMapping("/test-ui")
    public String showTestPage(Model model) {
        return "test-ui";
    }

    @PostMapping("/start-test")
    public String startTest(Model model) {
        String testId = UUID.randomUUID().toString();
        testResults.put(testId, "PENDING");

        new Thread(() -> {
            try {
                TestNG testng = new TestNG();
                testng.setTestClasses(new Class[]{com.example.demo.test.SauceDemoTest.class});
                testng.run();

                boolean passed = !testng.hasFailure() && !testng.hasSkip();
                testResults.put(testId, passed ? "PASS" : "FAIL");
            } catch (Exception e) {
                testResults.put(testId, "FAIL");
            }
        }).start();

        model.addAttribute("testId", testId);
        return "test-ui";

        // Simulate async test run
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                Thread.sleep(5000); // Simulate test duration
                boolean result = SauceDemoTest.runLoginTest();
                testResults.put(testId, result ? "PASS" : "FAIL");
            } catch (Exception e) {
                testResults.put(testId, "FAIL");
            }
        });

        model.addAttribute("testId", testId); // double quote for JS string
        return "status";
    }

    @GetMapping("/test-result")
    @ResponseBody
    public Map<String, String> getTestResult(@RequestParam String id) {
        String result = testResults.getOrDefault(id, "UNKNOWN");

        Map<String, String> response = new HashMap<>();
        response.put("status", result);
        return response; // returned as JSON
    }
}
