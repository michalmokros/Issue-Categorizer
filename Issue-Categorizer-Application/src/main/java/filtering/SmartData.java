package filtering;

import weka.classifiers.bayes.NaiveBayesMultinomialText;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;

public class SmartData {
    private Instances instances;
    private final static int PARTITION_SPLIT_COUNT = 5;

    public SmartData(Instances instances) {
        this.instances = instances;
    }

    public Instances convertBigDataToSmartData() throws Exception {
        Instances output = new Instances(instances, 0);

        final int partitionSize = Math.floorDiv(instances.size(), PARTITION_SPLIT_COUNT);
        final int leftOverSize = instances.size() - (partitionSize * PARTITION_SPLIT_COUNT);
        List<Instances> splitInstances = new ArrayList<>();
        for (int i = 0; i < PARTITION_SPLIT_COUNT; i++) {
            if (i == PARTITION_SPLIT_COUNT - 1) {
                splitInstances.add(new Instances(instances, i * partitionSize, partitionSize + leftOverSize));
            } else {
                splitInstances.add(new Instances(instances, i * partitionSize, partitionSize));
            }
        }

        for (int i = 0; i < PARTITION_SPLIT_COUNT; i++) {
            Instances train = getGroupedInstances(splitInstances, i, false);
            Instances test = getGroupedInstances(splitInstances, i, true);
            train.setClassIndex(2);
            test.setClassIndex(2);
            NaiveBayesMultinomialText naiveBayesMultinomialText = new NaiveBayesMultinomialText();
            naiveBayesMultinomialText.buildClassifier(train);

            for (int j = 0; j < test.numInstances(); j++) {
                double pred = naiveBayesMultinomialText.classifyInstance(test.instance(j));
                System.out.print("actual: " + test.classAttribute().value((int) test.instance(j).classValue()));
                System.out.println(", predicted: " + test.classAttribute().value((int) pred));
                if (test.classAttribute().value((int) test.instance(j).classValue())
                        .equals(test.classAttribute().value((int) pred))) {
                    output.add(test.instance(j));
                }
            }
        }

        return output;
    }

    private Instances getGroupedInstances(List<Instances> allInstances, int testInstancesIndex, boolean isReturingTestInstances) {
        if (isReturingTestInstances) {
            return allInstances.get(testInstancesIndex);
        }

        return mergeListOfInstancesExceptTest(allInstances, testInstancesIndex);
    }

    private Instances mergeListOfInstancesExceptTest(List<Instances> listOfInstances, int testInstancesIndex) {
        Instances output = new Instances(instances, 0);

        for (int i = 1; i < listOfInstances.size(); i++) {
            if (i != testInstancesIndex) {
                output.addAll(listOfInstances.get(i));
            }
        }

        return output;
    }
}
