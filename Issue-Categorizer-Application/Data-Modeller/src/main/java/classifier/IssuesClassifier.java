package classifier;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.Serializable;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

public class IssuesClassifier /*implements Serializable*/ {
    private final static Logger LOGGER = Logger.getLogger(IssuesClassifier.class.getName());
    //private static final long serialVersionUID = -123455813150452885L;

    private Instances data;
    private StringToWordVector filter;
    private Classifier classifier;

    public IssuesClassifier(Instances data) {
        this.data = data;

        if (data.classIndex() == -1) {
            data.setClassIndex(data.numAttributes() - 1);
        }

        filter = new StringToWordVector();
        classifier = new J48();
    }

    public Instances classifyIssues(Instances testData) throws Exception {
        if (data.numInstances() == 0) {
            LOGGER.log(INFO, "Training instances are empty");
            return null;
        }

        filter.setInputFormat(data);
        Instances filteredData = Filter.useFilter(data, filter);
        classifier.buildClassifier(filteredData);

        Instances classifiedTestData = new Instances(data, 0);

        for (Instance instance : testData) {
            Instance adjustedInstance = makeInstance(instance, data);
            filter.input(adjustedInstance);
            Instance filteredInstance = filter.output();
            double predicted = classifier.classifyInstance(filteredInstance);
            adjustedInstance.setClassValue(predicted);
            classifiedTestData.add(adjustedInstance);
        }

        return classifiedTestData;
    }

    private Instance makeInstance(Instance testInstance, Instances data) {
        // Create instance of length two.
        Instance instance = new DenseInstance(data.numAttributes());

        // Set value for message attribute
        Attribute titleAtt = data.attribute(0);
        Attribute bodyAtt = data.attribute(1);
        instance.setValue(titleAtt, titleAtt.addStringValue(testInstance.stringValue(0)));
        instance.setValue(bodyAtt, bodyAtt.addStringValue(testInstance.stringValue(1)));

        // Give instance access to attribute information from the dataset.
        instance.setDataset(data);

        return instance;
    }
}
