import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.BinaryOperator;

public class Main {

    public static void main(String[] args)throws IOException {
        ArrayList<String> allInformations = getListOFTransactionsFromFile();
        int numberOfTransactions = Integer.parseInt(allInformations.get(0));
        int numberOfRequests = Integer.parseInt(allInformations.get(1));
        List<String> listOfTransactions = allInformations.subList(2, numberOfTransactions + 2);
        List<String> listOfRequests = allInformations.subList(numberOfTransactions + 2, numberOfTransactions + 2 + numberOfRequests);


        for (String idOFClient : listOfRequests){
            List<Long> listOFPhoneById = getListOfPhoneByIdClient(Long.parseLong(idOFClient), listOfTransactions);
            if (listOFPhoneById.isEmpty()){
                System.out.println("- -");
            }
            else {
                Long mostCommonPhoneNumber = getMostCommonPhoneNumber(listOFPhoneById);
                String prefixOfOperator =  getPrefixOfOperator(mostCommonPhoneNumber);
                System.out.println(mostCommonPhoneNumber + " " + prefixOfOperator);
            }

        }

    }

    public static ArrayList<String> getListOFTransactionsFromFile(){
        ArrayList<String> listOfTrans = new ArrayList<>();
        try {

            File f = new File("src/listOfTransactions.txt");

            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLine = "";


            while ((readLine = b.readLine()) != null) {
                listOfTrans.add(readLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return listOfTrans;
    }

    public static List<Long> getListOfPhoneByIdClient(Long IdClient, List<String> transactions){
        List<Long> listOfPhoneByID = new ArrayList<>();
        for (String line : transactions){
            String[] parts = line.split("\t");
            Long idClintInList = Long.parseLong(parts[1].trim());
            Long phoneNumber = Long.parseLong(parts[2].trim());
            if (IdClient.equals(idClintInList)){
                listOfPhoneByID.add(phoneNumber);
            }
        }
        return listOfPhoneByID;
    }

    public static Long getMostCommonPhoneNumber(List<Long> listOfPhoneNumbers){

        Long maxOccurredElement = listOfPhoneNumbers.stream()
                .reduce(BinaryOperator.maxBy((o1, o2) -> Collections.frequency(listOfPhoneNumbers, o1) -
                        Collections.frequency(listOfPhoneNumbers, o2))).orElse(null);
        return maxOccurredElement;
    }

    public static String getPrefixOfOperator(Long phoneNumber){
        String nameOfOperator;
        int firstTwoNumber = Integer.parseInt(String.valueOf(phoneNumber).substring(0,2));
        switch (firstTwoNumber) {
            case 99:
            case 95:
                nameOfOperator = "Uzmobile";
                break;
            case 93:
            case 94:
                nameOfOperator = "Ucell";
                break;
            case 90:
            case 91:
                nameOfOperator = "Beeline";
                break;
            case 97:
                nameOfOperator = "UMS";
                break;
            case 98:
                nameOfOperator = "Perfectum";
                break;
            default:
                nameOfOperator = "Invalid Prefix";
                break;
        }
        return nameOfOperator;

    }
}
