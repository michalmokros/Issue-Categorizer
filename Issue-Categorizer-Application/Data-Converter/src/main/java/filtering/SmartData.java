package filtering;

import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

/**
 * Class for SmartData Clearing, using ML for better pre-processing of training data.
 *
 * @author xmokros
 */
public class SmartData {
    private final static Logger LOGGER = Logger.getLogger(SmartData.class.getName());

    private Instances instances;
    private final static int PARTITION_SPLIT_COUNT = 5;
    private final static int LABEL_INDEX = 2;

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
            train.setClassIndex(LABEL_INDEX);
            test.setClassIndex(LABEL_INDEX);
            FilteredClassifier filteredClassifier = new FilteredClassifier();
            StringToWordVector stringToWordVector = new StringToWordVector();
            RandomForest randomForest = new RandomForest();
            stringToWordVector.setInputFormat(train);
            filteredClassifier.setFilter(stringToWordVector);
            filteredClassifier.setClassifier(randomForest);
            filteredClassifier.buildClassifier(train);

            for (int j = 0; j < test.numInstances(); j++) {
                double pred = filteredClassifier.classifyInstance(test.instance(j));

                LOGGER.log(INFO, "Classified instance number " + (i*test.numInstances()) + j
                        + ", actual: " + test.classAttribute().value((int) test.instance(j).classValue())
                        + ", predicted: " + test.classAttribute().value((int) pred));

                if (test.classAttribute().value((int) test.instance(j).classValue())
                        .equals(test.classAttribute().value((int) pred))) {
                    output.add(test.instance(j));
                }
            }
        }

        return output;
    }

    private Instances getGroupedInstances(List<Instances> allInstances, int testInstancesIndex, boolean isReturningTestInstances) {
        if (isReturningTestInstances) {
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
