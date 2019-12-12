/**
 * Main application class for executing the "info" task.
 *
 * @author xmokros 456442@mail.muni.cz
 */
public class Helper {
    public static void main(String[] args) {
        System.out.println("--------------------------------------------ISSUE CATEGORIZER--------------------------------------------");
        System.out.println("BASIC INFORMATION:");
        System.out.println("\tCreated CSV and ARFF files are stored in directory 'data', all files to be used in application" +
                "\n\tneed to be stored in this directory");
        //task run
        System.out.println("TASK RUN");
        System.out.println("\tDownloads, Converts, Analyzes and Classifies(labels) issues based on other labeled issues");
        System.out.println("\tExample: >./gradlew run --args='-r=atom/atom -s=all'");
        System.out.println("\t" + Main.REPOSITORY_ARGUMENT + "\trepresents user and repository on www.github.com to use issues from, mandatory");
        System.out.println("\t\texample: -r=atom/atom");
        System.out.println("\t" + Main.STATE_ARGUMENT + "\trepresents status of the issues to be categorized, default value: 'open'");
        System.out.println("\t\texample: -s=open");
        System.out.println("\t" + Main.TRAIN_LABELS_ARGUMENT + "\trepresents labels of training issues, default value: 'enhancement,bug'");
        System.out.println("\t\texample: -cl=enhancement,bug");
        System.out.println("\t" + Main.TEST_LABELS_ARGUMENT + "\trepresents labels of issues to be classified by the model created from labeled issues, default value: 'unlabeled'");
        System.out.println("\t\texample: -tl=unlabeled");
        //task download
        System.out.println("TASK DOWNLOAD");
        System.out.println("\tDownloads issues and saves them into csv file");
        System.out.println("\tExample: >./gradlew download --args='-r=atom/atom'");
        System.out.println("\t-" + Download.REPOSITORY_ARGUMENT + "\trepresents user and repository on www.github.com to use issues from, mandatory");
        System.out.println("\t\texample: -r=atom/atom");
        System.out.println("\t-" + Download.STATE_ARGUMENT + "\trepresents status of the issues to be categorized, default value: 'open'");
        System.out.println("\t\texample: -s=open");
        System.out.println("\t-" + Download.LABELS_ARGUMENT + "\trepresents labels of training issues, default value: 'all'");
        System.out.println("\t\texample: -l=enhancement,bug");
        System.out.println("\t-" + Download.EXCLUDE_LABELS_ARGUMENT + "\trepresents labels of issues to be excluded from downloading, default value none");
        System.out.println("\t\texample: -e=unlabeled");
        //task convert
        System.out.println("TASK CONVERT");
        System.out.println("\tConverts issues stored in csv into arff files ready for classification");
        System.out.println("\tExample: >./gradlew convert --args='-f=IssueCategorizer-atom-atom-issues-open-labels-enhancement-bug.csv'");
        System.out.println("\twhere file IssueCategorizer-atom-atom-issues-open-labels-enhancement-bug.csv is stored in data directory");
        System.out.println("\t-" + Convert.FILE_ARGUMENT + "\trepresents csv file to be converted, mandatory");
        System.out.println("\t\texample: -f=IssueCategorizer-atom-atom-issues-open-labels-enhancement-bug.csv");
        System.out.println("\t-" + Convert.SMART_DATA_ARGUMENT + "\trepresents whether to use Smart Data filter during conversion, default value: 'false'");
        System.out.println("\t\texample: -sd=true");
        //task diagnose
        System.out.println("TASK DIAGNOSE");
        System.out.println("\tRuns analysis on the labeled issues and outputs the summary of the classifier");
        System.out.println("\tExample: >./gradlew diagnose --args='-f=IssueCategorizer-atom-atom-issues-open-labels-enhancement-bug.csv -c=nb'");
        System.out.println("\tWarning: one of the tree classifiers must be set to true in the arguments");
        System.out.println("\t-" + Test.FILE_ARGUMENT + "\trepresents the name of the file where the issues are stored, mandatory");
        System.out.println("\t\texample: -f=file.arff");
        System.out.println("\t-" + Test.CLASSIFIER_ARGUMENT + "\trepresents which classifying algorithm is used, mandatory, possible values: 'nb,j48,rf'");
        System.out.println("\t\texample: -c=rf");
        //task classify
        System.out.println("TASK CLASSIFY");
        System.out.println("\tClassifies issues stored in csv/arff files based on another labeled issues");
        System.out.println("\tExample: >./gradlew classify --args='-u=train.arff -l=test.csv -c=rf'");
        System.out.println("\t-" + Model.LABELED_FILE_ARGUMENT + "\trepresents file where training issues for creating the model are stored, mandatory");
        System.out.println("\t\texample: -u=IssueCategorizer-atom-atom-issues-open-labels-enhancement-bug.arff");
        System.out.println("\t-" + Model.UNLABELED_FILE_ARGUMENT + "\trepresents file where issues to be classified are stored, mandatory");
        System.out.println("\t\texample: -l=IssueCategorizer-atom-atom-issues-open-labels-unlabeled.csv");
        System.out.println("\t-" + Model.CLASSIFIER_ARGUMENT + "\trepresents which of the classifiers to use, mandatory, possible values: 'nb,rf,j48'");
        System.out.println("\t\texample: -c=nb");
        System.out.println("\t-" + Model.SMART_DATA_ARGUMENT + "\trepresents whether to use Smart Data filter during conversion if the train file is in csv format, default value 'false'");
        System.out.println("\t\texample: -sd=true");
        System.out.println("--------------------------------------------ISSUE CATEGORIZER--------------------------------------------");
    }
}
