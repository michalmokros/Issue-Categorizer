package classifier;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayesMultinomialText;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
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
    private Instances classifiedData;
    private StringToWordVector filter;
    private FilteredClassifier filteredClassifier;
    private Classifier classifier;

    public IssuesClassifier(Instances data, String classifier) throws Exception {
        if (data.classIndex() == -1) {
            data.setClassIndex(data.numAttributes() - 1);
        }

        Remove remove = new Remove();
        remove.setAttributeIndices("1");
        remove.setInvertSelection(false);
        remove.setInputFormat(data);
        this.data = Filter.useFilter(data, remove);
        this.classifiedData = new Instances(data, 0);

        this.filteredClassifier = new FilteredClassifier();
        if (classifier.equals("nb")) {
            this.filter = null;
            this.classifier = new NaiveBayesMultinomialText();
        } else if (classifier.equals("j48")) {
            filter = new StringToWordVector();
            this.classifier = new J48();
        } else if (classifier.equals("rf")) {
            filter = new StringToWordVector();
            this.classifier = new RandomForest();
        } else {
            throw new Exception("Invalid value for classifier.");
        }
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
        if (filter != null) {
            filteredClassifier.setFilter(filter);
        }
        filteredClassifier.setClassifier(classifier);
        filteredClassifier.buildClassifier(data);

        for (Instance instance : testData) {
            Instance classifiedInstance = instance;
            double predicted = filteredClassifier.classifyInstance(removeId(testData, classifiedInstance));
            classifiedInstance.setClassValue(predicted);
            classifiedData.add(classifiedInstance);
        }

        return classifiedData;
    }

    private Instance removeId(Instances test, Instance instance) throws Exception {
        Instances instances = new Instances(test, 0);
        instances.add(instance);
        Remove remove = new Remove();
        remove.setAttributeIndices("1");
        remove.setInvertSelection(false);
        remove.setInputFormat(instances);
        Instances cleanInstances = Filter.useFilter(instances, remove);
        return cleanInstances.get(0);
    }
}
