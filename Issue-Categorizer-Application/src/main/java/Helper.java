public class Helper {
    public static void main(String[] args) {
        System.out.println("--------------------------------------------ISSUE CATEGORIZER--------------------------------------------");
        System.out.println("BASIC INFORMATION:");
        System.out.println("\tCreated CSV and ARFF files are stored in directory 'data', all files to be used in application" +
                "\n\tneed to be stored in this directory");
        //task run
        System.out.println("TASK RUN");
        System.out.println("\tDownloads, Converts and Classifies(labels) issues based on other labeled issues");
        System.out.println("\tExample: './gradlew run --args='-R=atom/atom -S=all''");
        System.out.println("\t-R\trepresents user and repository on www.github.com to use issues from, mandatory");
        System.out.println("\t\texample: -R=atom/atom");
        System.out.println("\t-S\trepresents status of the issues to be categorized, default value: 'open'");
        System.out.println("\t\texample: -S=open");
        System.out.println("\t-L\trepresents labels of training issues, default value: 'enhancement,bug'");
        System.out.println("\t\texample: -L=enhancement,bug");
        System.out.println("\t-c\trepresents labels of testing issues, default value: ''\n\t\t - meaning issues with all the labels except training ones");
        System.out.println("\t\texample: -c=unlabeled");
        //task download
        System.out.println("TASK DOWNLOAD");
        System.out.println("\tDownloads issues and saves them into csv file");
        System.out.println("\tExample: './gradlew download --args='-R=atom/atom'");
        System.out.println("\t-R\trepresents user and repository on www.github.com to use issues from, mandatory");
        System.out.println("\t\texample: -R=atom/atom");
        System.out.println("\t-S\trepresents status of the issues to be categorized, default value: 'open'");
        System.out.println("\t\texample: -S=open");
        System.out.println("\t-L\trepresents labels of training issues, default value: 'enhancement,bug'");
        System.out.println("\t\texample: -L=enhancement,bug");
        //task convert
        System.out.println("TASK CONVERT");
        System.out.println("\tConverts issues stored in csv into arff files ready for classification");
        System.out.println("\tExample: './gradlew convert --args='-f=IssueCategorizer-atom-atom-issues-open-labels-enhancement-bug.csv'");
        System.out.println("\twhere file IssueCategorizer-atom-atom-issues-open-labels-enhancement-bug.csv is stored in data directory");
        System.out.println("\t-f\trepresents csv file to be converted, mandatory");
        System.out.println("\t\texample: -f=IssueCategorizer-atom-atom-issues-open-labels-enhancement-bug.csv");
        //task classify
        System.out.println("TASK CLASSIFY");
        System.out.println("\tClassifies issues stored in csv/arff files based on another labeled issues");
        System.out.println("\tExample: './gradlew classify --args='-train=train.arff -test=test.csv''");
        System.out.println("\t-train\trepresents file where training issues for creating the model are stored, mandatory");
        System.out.println("\t\texample: -train=IssueCategorizer-atom-atom-issues-open-labels-enhancement-bug.arff");
        System.out.println("\t-test\trepresents file where issues to be classified are stored, mandatory");
        System.out.println("\t\texample: -test=IssueCategorizer-atom-atom-issues-open-labels-unlabeled.csv");
        System.out.println("\t-sd\trepresents whether there should be used smartdata while converting training data(if stored in csv), default value: 'false'");
        System.out.println("\t\texample: -sd=true");
        System.out.println("--------------------------------------------ISSUE CATEGORIZER--------------------------------------------");
    }
}
