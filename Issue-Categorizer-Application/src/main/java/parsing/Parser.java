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

public final class Parser {

    public static List<DataHolder> parseCsvFile(String csvFile) {
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

        return arffList;
    }

    public static void createArffFile(String arffFile, List<DataHolder> arffList) throws Exception {
        FastVector attributes = new FastVector();
        FastVector attributesRel = new FastVector();
        attributes.addElement(new Attribute(Column.TITLE.toString(), (FastVector) null));
        attributes.addElement(new Attribute(Column.BODY.toString(), (FastVector) null));
        attributesRel.addElement("bug");
        attributesRel.addElement("enhancement");
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

        //System.out.println(data);

        ArffSaver saver = new ArffSaver();
        SmartData smartData = new SmartData(data);
        saver.setInstances(smartData.convertBigDataToSmartData());

        try {
            saver.setFile(new File(arffFile));
            saver.writeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}