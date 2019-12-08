package classifier;

import weka.classifiers.Classifier;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

public class IssuesClassifier /*implements Serializable*/ {
    private final static Logger LOGGER = Logger.getLogger(IssuesClassifier.class.getName());
    //private static final long serialVersionUID = -123455813150452885L;

    private Instances data;
    private StringToWordVector filter;
    private FilteredClassifier filteredClassifier;
    private Classifier classifier;

    public IssuesClassifier(Instances data) {
        this.data = data;

        if (data.classIndex() == -1) {
            data.setClassIndex(data.numAttributes() - 1);
        }

        filter = new StringToWordVector();
        filteredClassifier = new FilteredClassifier();
        classifier = new RandomForest();
    }

    public Instances classifyIssues(Instances testData) throws Exception {
        if (data.numInstances() == 0) {
            LOGGER.log(INFO, "Training instances are empty");
            return null;
        }

        if (testData.classIndex() == -1) {
            testData.setClassIndex(testData.numAttributes() - 1);
        }

        filter.setInputFormat(data);
        filteredClassifier.setFilter(filter);
        filteredClassifier.setClassifier(classifier);
        filteredClassifier.buildClassifier(data);

        Instances classifiedTestData = new Instances(data, 0);

        for (Instance instance : testData) {
            Instance predictedInstance = instance;
            double predicted = filteredClassifier.classifyInstance(instance);
            predictedInstance.setClassValue(predicted);
            classifiedTestData.add(predictedInstance);
        }

        return classifiedTestData;
    }
}
