package com.github.bogdanlivadariu.reporting.testng.xml.models;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.testng.reporters.XMLReporterConfig;

@XmlRootElement(name = "suite")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClassModel {
    @XmlAttribute
    private String name;

    @XmlElement(name = "test-method")
    private List<TestMethodModel> testMethods;

    private String overallStatus = XMLReporterConfig.TEST_PASSED;

    private int totalPassed = 0;

    private int totalFailed = 0;

    private int totalSkipped = 0;

    private int totalTests = 0;

    private long totalDuration = 0;

    public void postProcess() {
        for (TestMethodModel tm : getTestMethods()) {
            String status = tm.getStatus();
            if (status.equalsIgnoreCase(XMLReporterConfig.TEST_FAILED)
                || status.equalsIgnoreCase(XMLReporterConfig.TEST_SKIPPED)) {
                overallStatus = XMLReporterConfig.TEST_FAILED;
                break;
            }
        }
        for (TestMethodModel tm : getTestMethods()) {
            // if the test is a setup / teardown do not count it
            totalDuration += tm.getDurationMs();
            if (tm.getIsConfig()) {
                continue;
            }
            totalTests++;

            switch (tm.getStatus().toUpperCase()) {
                case XMLReporterConfig.TEST_PASSED:
                    totalPassed++;
                    break;
                case XMLReporterConfig.TEST_FAILED:
                    totalFailed++;
                    break;
                case XMLReporterConfig.TEST_SKIPPED:
                    totalSkipped++;
                    break;
            }
        }
    }

    public String getName() {
        return name;
    }

    public List<TestMethodModel> getTestMethods() {
        return testMethods;
    }

    public String getOverallStatus() {
        return overallStatus;
    }

    public int getTotalPassed() {
        return totalPassed;
    }

    public int getTotalFailed() {
        return totalFailed;
    }

    public int getTotalSkipped() {
        return totalSkipped;
    }

    public int getTotalTests() {
        return totalTests;
    }

    public long getTotalDuration() {
        return totalDuration;
    }
}
