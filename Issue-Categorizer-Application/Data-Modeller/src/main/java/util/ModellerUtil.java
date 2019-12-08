package util;

import classifier.IssuesClassifier;
import com.opencsv.CSVWriter;
import dto.Issue;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

/**
 * Util class for basic model creating and handling methods.
 *
 * @author xmokros
 */
public abstract class ModellerUtil {
    private final static Logger LOGGER = Logger.getLogger(ModellerUtil.class.getName());

    private ModellerUtil() {

    }

    public static String processData(String trainingFile, String testFile, String originalCsvFileName) throws Exception {
        Instances instances = convertArffFileIntoInstances(trainingFile);
        IssuesClassifier issuesClassifier = new IssuesClassifier(instances);
        Instances classifiedInstances = issuesClassifier.classifyIssues(convertArffFileIntoInstances(testFile));
        String csvFileName = testFile.substring(0, testFile.length() - 5).concat("-classified.csv");
        createCsvFile(csvFileName, originalCsvFileName, classifiedInstances);
        return csvFileName;
    }

    private static Instances convertArffFileIntoInstances(String filePath) {
        try {
            DataSource dataSource = new DataSource(filePath);
            return dataSource.getDataSet();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static void createCsvFile(String csvFileName, String originalCsvFileName, Instances instances) throws IOException {
        LOGGER.log(INFO, "Beginning of creation of csv file --> " + csvFileName + " <--");

        try (CSVWriter writer = new CSVWriter(new FileWriter(new File(csvFileName)))) {
            String[] header = { "Title", "Body" , "Label" };
            writer.writeNext(header);

            List<Issue> originalList = getOriginal(originalCsvFileName);
            for (int i = 1; i <= instances.numInstances(); i++) {
                Instance instance = instances.get(i - 1);
                if (originalList != null) {
                    Issue originalIssue = getIssueById(originalList, String.valueOf(i));

                    if (originalIssue != null) {
                        String[] entry = {originalIssue.getTitle(), originalIssue.getBody(), (originalIssue.getLabel().isEmpty() ? "" : originalIssue.getLabel() + ", ") + instance.stringValue(2)};
                        writer.writeNext(entry);
                        continue;
                    }
                }

                String[] entry = { instance.stringValue(0), instance.stringValue(1), instance.stringValue(2) };
                writer.writeNext(entry);
            }
        }
    }

    private static List<Issue> getOriginal(String originalCsvFileName) {
        if (originalCsvFileName == null) {
            return null;
        }

        List<Issue> output = new ArrayList<>();

        try (Reader in = new FileReader(originalCsvFileName)) {
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
            int id = 1;
            for (CSVRecord record : records) {
                String title = record.get("Title");
                String body = record.get("Body");
                String label = record.get("Label");

                output.add(new Issue(String.valueOf(id++), title, body, label));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return output;
    }

    private static Issue getIssueById(List<Issue> originalList, String id) {
        for (Issue issue : originalList) {
            if (issue.getId().equals(id)) {
                return issue;
            }
        }

        return null;
    }
}
