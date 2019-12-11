package util;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayesMultinomialText;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.util.Random;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

public class TesterUtil {
    private final static Logger LOGGER = Logger.getLogger(TesterUtil.class.getName());
    private static final int FOLDS = 10;
    private static final int SEED = 1;

    public static String testData(String fileName, boolean usingNB, boolean usingJ48, boolean usingRF) throws Exception {
        Instances instances = convertArffFileIntoInstances(fileName);
        String summary = runTesting(instances, usingNB, usingJ48, usingRF);
        return summary;
    }

    private static String runTesting(Instances instances, boolean usingNB, boolean usingJ48, boolean usingRF) throws Exception {
        if (usingNB) {
            NaiveBayesMultinomialText nbMultiText = new NaiveBayesMultinomialText();
            Evaluation evaluation = new Evaluation(instances);
            evaluation.crossValidateModel(nbMultiText, instances, FOLDS, new Random(SEED));
            LOGGER.log(INFO, "Estimated Accuracy Using NaiveBayesMultinomialText Classifier : " + evaluation.pctCorrect());
            return getInfo(evaluation);
        }

        if (usingJ48) {
            StringToWordVector stringToWordVector = new StringToWordVector();
            J48 j48 = new J48();
            stringToWordVector.setInputFormat(instances);
            Instances filteredInstances = Filter.useFilter(instances, stringToWordVector);
            Evaluation evaluation = new Evaluation(filteredInstances);
            evaluation.crossValidateModel(j48, filteredInstances, FOLDS, new Random(SEED));
            LOGGER.log(INFO, "Estimated Accuracy Using StringToWordVector filter and J48 tree Classifier : " + evaluation.pctCorrect());
            return getInfo(evaluation);
        }

        if (usingRF) {
            StringToWordVector stringToWordVector = new StringToWordVector();
            RandomForest randomForest = new RandomForest();
            stringToWordVector.setInputFormat(instances);
            Instances filteredInstances = Filter.useFilter(instances, stringToWordVector);
            Evaluation evaluation = new Evaluation(filteredInstances);
            evaluation.crossValidateModel(randomForest, filteredInstances, FOLDS, new Random(SEED));
            LOGGER.log(INFO, "Estimated Accuracy Using StringToWordVector filter and Random Forest tree Classifier : " + evaluation.pctCorrect());
            return getInfo(evaluation);
        }

        throw new Exception("No classifier was chosen.");
    }

    private static Instances convertArffFileIntoInstances(String filePath) {
        try {
            DataSource dataSource = new DataSource(filePath);
            Instances instances = dataSource.getDataSet();
            instances.setClassIndex(instances.numAttributes() - 1);
            Remove remove = new Remove();
            remove.setAttributeIndices("1");
            remove.setInvertSelection(false);
            remove.setInputFormat(instances);
            return Filter.useFilter(instances, remove);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String getInfo(Evaluation evaluation) throws Exception {
        return evaluation.toSummaryString() + "\n"
                + evaluation.toClassDetailsString() + "\n"
                + evaluation.toMatrixString();
    }
}