import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Selector {
    private final static HashMap<Integer, String> names = new HashMap<>();
    private final static Random random = new Random();
    private final static Scanner in = new Scanner(System.in);
    private final static boolean repeatNames = false;
    private final static ArrayList<String> ignoreName = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        //read names from file or from console
        String name;
        int counter = 0;
        //reads name from file
        {
            File names = new File("src/NameList");
            BufferedReader br = new BufferedReader(new FileReader(names));
            while ((name = br.readLine()) != null) {
                Selector.names.put(counter, name);
                counter++;
            }
        }
        //reads names from console
        {
            System.out.println("Input Names, 0 to stop");
            name = in.next();
            while(!in.next().equals("0")){
                Selector.names.put(counter, name);
                counter++;
                name = in.next();
            }
        }



        //Inputs data needed for training week
        System.out.println("Input number of names needed");
        int namesNeeded = in.nextInt();

        System.out.println("Print names to ignore, 0 to stop");
        String input = in.next();
        while (!input.equals("0")) {
            ignoreName.add(input);
            input = in.next();
        }
        System.out.println("Working...");

        String[] nameList = getNames(RNG(namesNeeded));

        System.out.println("List of names: ");
        for (String s : nameList) {
            System.out.println(s);
        }
    }


    private static int[] RNG(int namesNeeded) {
        int[] values = new int[namesNeeded];
        ArrayList<Integer> ignoreValues = getIgnoredNameKeys();
        Collections.sort(ignoreValues);
        for (int i = 0; i < values.length; i++) {
            int rngValue = random.nextInt(names.size());
            //checks to see if value should be ignored
            if (searchValue(rngValue, ignoreValues)) {
                i--;
                System.out.println("Skipping");

            } else {
                values[i] = rngValue;
                System.out.println("Working...");
                ignoreValues.add(rngValue);
            }
        }
        return values;
    }
    //Get keys for ignoredNames
    private static ArrayList<Integer> getIgnoredNameKeys() {
        ArrayList<Integer> ignoredKeys = new ArrayList<>();
        for (String ignoredInstructor : ignoreName) {
            for (int j = 0; j < names.size(); j++) {
                if (names.get(j).equals(ignoredInstructor)) {
                    if(!repeatNames) {
                        ignoredKeys.add(j);
                        break;
                    }
                }
            }
        }
        return ignoredKeys;
    }
    //Get names from Hashmap
    private static String[] getNames(int[] RNGValues) {
        String[] names = new String[RNGValues.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = Selector.names.get(RNGValues[i]);
        }
        return names;
    }
    //wrote because search doesn't work for some reason
    private static boolean searchValue(int value, ArrayList<Integer> valueList) {
        boolean found = false;
        for (Integer integer : valueList) {
            if (value == integer) {
                found = true;
                break;
            }
        }
        return found;
    }
}
