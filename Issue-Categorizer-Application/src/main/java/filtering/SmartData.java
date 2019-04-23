package filtering;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayesMultinomialText;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class SmartData {
    private Instances train;
    private Instances test;
    private Instances instances;
    private int sum = 46;

    public SmartData(Instances instances) {
        this.instances = instances;
    }

    public Instances convertBigDataToSmartData() throws Exception {
        Instances output = new Instances(instances, 0);
        for (int i = 1; i <= 5; i++) {
            //int start = (int) Math.round(instances.numInstances() * 0.8);
            //int stop = instances.numInstances() - start;
            int single = 46;
            int other = 46 * 4;

            if (i == 1) {
                train = new Instances(instances, 0, other);
                test = new Instances(instances, other, single);
            } else if (i == 2) {
                train = new Instances(instances, single, other);
                test = new Instances(instances, 0, single);
            } else if (i == 3) {
                train = new Instances(instances, 0, single);
                test = new Instances(instances, single, single);
                train.addAll(new Instances(instances, single * 2, single * 3));
            } else if (i == 4) {
                train = new Instances(instances, 0, single * 2);
                test = new Instances(instances, single * 2, single);
                train.addAll(new Instances(instances, single * 3, single * 2));
            } else if (i == 5) {
                train = new Instances(instances, 0, single * 3);
                test = new Instances(instances, single * 3, single);
                train.addAll(new Instances(instances, single * 4, single));
            }

            train.setClassIndex(2);
            test.setClassIndex(2);
            //StringToWordVector stringToWordVector = new StringToWordVector();
            NaiveBayesMultinomialText naiveBayesMultinomialText = new NaiveBayesMultinomialText();
            naiveBayesMultinomialText.buildClassifier(train);
            for (int j = 0; j < test.numInstances(); j++) {
                double pred = naiveBayesMultinomialText.classifyInstance(test.instance(j));
                System.out.print(", actual: " + test.classAttribute().value((int) test.instance(j).classValue()));
                System.out.println(", predicted: " + test.classAttribute().value((int) pred));
                if (test.classAttribute().value((int) test.instance(j).classValue())
                        .equals(test.classAttribute().value((int) pred))) {
                    output.add(test.instance(j));
                }
            }
        }
        return output;
    }
}
