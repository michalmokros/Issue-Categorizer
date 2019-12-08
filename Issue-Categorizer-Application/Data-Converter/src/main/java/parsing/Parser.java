package parsing;

import entities.DataHolder;
import enums.Column;
import filtering.SmartData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import weka.core.*;
import weka.core.converters.ArffSaver;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

/**
 * Class for parsing of different types of files (csv, arff)
 *
 * @author xmokros
 */
public final class Parser {
    private final static Logger LOGGER = Logger.getLogger(Parser.class.getName());

    public static List<DataHolder> parseCsvFile(String csvFile) {
        LOGGER.log(INFO, "Beginning of parsing csv file --> " + csvFile + " <--");

        List<DataHolder> arffList = new ArrayList<>();
        try (Reader in = new FileReader(csvFile)) {
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
            for (CSVRecord record : records) {
                String title = record.get("Title");
                String body = record.get("Body");
                String label = record.get("Label");

                arffList.add(new DataHolder(title, body, label));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        LOGGER.log(INFO, "Finished parsing the file.");
        return arffList;
    }

    public static void createArffFile(String arffFile, List<DataHolder> arffList, boolean useSmartData) throws Exception {
        LOGGER.log(INFO, "Beginning of creation of arff file --> " + arffFile + " <--");

        FastVector attributes = new FastVector();
        FastVector attributesRel = new FastVector();
        attributes.addElement(new Attribute(Column.TITLE.toString(), (FastVector) null));
        attributes.addElement(new Attribute(Column.BODY.toString(), (FastVector) null));

        for (String label : getLabels(arffList)) {
            attributesRel.addElement(label);
        }

        attributes.addElement(new Attribute(Column.LABEL.toString(), attributesRel));
        Instances data = new Instances("Issues", attributes, 0);

        for (DataHolder dataHolder : arffList) {
            double[] vals = new double[data.numAttributes()];
            vals[0] = data.attribute(0).addStringValue(dataHolder.getTitle());
            vals[1] = data.attribute(1).addStringValue(dataHolder.getBody());
            vals[2] = attributesRel.indexOf(dataHolder.getLabel());
            Instance instance = new DenseInstance(1.0, vals);
            data.add(instance);
        }

        ArffSaver saver = new ArffSaver();

        if (useSmartData) {
            SmartData smartData = new SmartData(data);
            saver.setInstances(smartData.convertBigDataToSmartData());
        } else {
            saver.setInstances(data);
        }

        try {
            saver.setFile(new File(arffFile));
            saver.writeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }

        LOGGER.log(INFO, "Finished creating and filling arff file.");
    }

    private static List<String> getLabels(List<DataHolder> entries) {
        List<String> output = new ArrayList<>();

        for (DataHolder entry : entries) {
            if (!output.contains(entry.getLabel())) {
                output.add(entry.getLabel());
            }
        }

        return output;
    }
}