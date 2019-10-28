package classifier;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayesMultinomialText;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

public class IssuesClassifier /*implements Serializable*/ {
    private final static Logger LOGGER = Logger.getLogger(IssuesClassifier.class.getName());
    //private static final long serialVersionUID = -123455813150452885L;

    private Instances data;
    private StringToWordVector filter;
    private FilteredClassifier filteredClassifier;
    private NaiveBayesMultinomialText naiveBayesMultinomialText;

    public IssuesClassifier(Instances data) {
        this.data = data;

        if (data.classIndex() == -1) {
            data.setClassIndex(data.numAttributes() - 1);
        }

        filter = new StringToWordVector();
        filteredClassifier = new FilteredClassifier();
        naiveBayesMultinomialText = new NaiveBayesMultinomialText();
    }

    public Instances classifyIssues(Instances testData) throws Exception {
        if (data.numInstances() == 0) {
            LOGGER.log(INFO, "Training instances are empty");
            return null;
        }

        Remove rm = new Remove();
        rm.setAttributeIndices("1");
        filteredClassifier.setFilter(rm);
        filteredClassifier.setClassifier(naiveBayesMultinomialText);
        filter.setInputFormat(data);
        Instances filteredData = Filter.useFilter(data, filter);
        filteredClassifier.buildClassifier(data);

        Instances classifiedTestData = new Instances(data, 0);

        for (Instance instance : testData) {
            Instance adjustedInstance = makeInstance(instance, data);
            filter.input(adjustedInstance);
            Instance filteredInstance = filter.output();
            double predicted = filteredClassifier.classifyInstance(adjustedInstance);
            adjustedInstance.setClassValue(predicted);
            classifiedTestData.add(adjustedInstance);
        }

        return classifiedTestData;
    }

    private Instance makeInstance(Instance testInstance, Instances data) {
        // Create instance of length two.
        Instance instance = new DenseInstance(data.numAttributes());

        // Set value for message attribute
        Attribute idAtt = data.attribute(0);
        Attribute titleAtt = data.attribute(1);
        Attribute bodyAtt = data.attribute(2);
        instance.setValue(idAtt, idAtt.addStringValue(testInstance.stringValue(0)));
        instance.setValue(titleAtt, titleAtt.addStringValue(testInstance.stringValue(1)));
        instance.setValue(bodyAtt, bodyAtt.addStringValue(testInstance.stringValue(2)));

        // Give instance access to attribute information from the dataset.
        instance.setDataset(data);

        return instance;
    }
}
