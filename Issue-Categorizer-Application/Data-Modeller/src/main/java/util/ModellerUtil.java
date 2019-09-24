package util;

import classifier.IssuesClassifier;
import com.opencsv.CSVWriter;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    public static String processData(String trainingFile, String testFile) throws Exception {
        Instances instances = convertArffFileIntoInstances(trainingFile);
        IssuesClassifier issuesClassifier = new IssuesClassifier(instances);
        Instances classifiedInstances = issuesClassifier.classifyIssues(convertArffFileIntoInstances(testFile));
        String csvFileName = testFile.substring(0, testFile.length() - 4).concat("csv");
        createCsvFile(csvFileName, classifiedInstances);
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


    public static void createCsvFile(String csvFileName, Instances instances) throws IOException {
        LOGGER.log(INFO, "Beginning of creation of csv file --> " + csvFileName + " <--");

        try (CSVWriter writer = new CSVWriter(new FileWriter(new File(csvFileName)))) {
            String[] header = { "Title", "Body" , "Label" };
            writer.writeNext(header);

            for (Instance instance : instances) {
                String[] entry = { instance.stringValue(0), instance.stringValue(1), instance.stringValue(2) };
                writer.writeNext(entry);
            }
        }
    }
}
