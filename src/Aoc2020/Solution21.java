package Aoc2020;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution21 {

    private static final Map<String, Set<String>> possibleCausesMap = new HashMap<>();
    private static final ArrayList<AllergenIngredient> allergenIngredients = new ArrayList<>();

    public class AllergenIngredient implements Comparable<AllergenIngredient> {
        private final String ingredient;
        private final String allergen;
        public AllergenIngredient(final String ingredient, final String allergen) {
            this.ingredient=ingredient;
            this.allergen=allergen;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AllergenIngredient that = (AllergenIngredient) o;
            return Objects.equals(ingredient, that.ingredient) &&
                    Objects.equals(allergen, that.allergen);
        }

        @Override
        public int hashCode() {
            return Objects.hash(ingredient, allergen);
        }

        @Override
        public int compareTo(AllergenIngredient o) {
            return this.allergen.compareTo(o.allergen);
        }
    }

    public class Combination {
        Set<String> ingredients;
        Set<String> allergens;
        public Combination() {
            this.ingredients = new HashSet<>();
            this.allergens = new HashSet<>();
        }
    }

    public static void main(String[] args) throws IOException {
        Solution21 solution = new Solution21();
        solution.execute();
    }

    public void execute() throws FileNotFoundException {
        List<Combination> input = getInput();
        List<String> dangerousIngredients = new ArrayList<>();
        for(Combination combination : input) {
            for(String allergen : combination.allergens) {
                Set<String> newPossibleCauses = new HashSet<>();
                newPossibleCauses.addAll(combination.ingredients);
                if (!possibleCausesMap.containsKey(allergen)) {
                    possibleCausesMap.put(allergen, newPossibleCauses);
                } else {
                    Set<String> previousPossibleCauses = possibleCausesMap.get(allergen);
                    previousPossibleCauses.retainAll(newPossibleCauses);
                    possibleCausesMap.put(allergen, previousPossibleCauses);
                }
            }
        }
        while(!possibleCausesMap.isEmpty()) {
            String foundKey = null;
            for(String key : possibleCausesMap.keySet()) {
                Set<String> values = possibleCausesMap.get(key);
                if (values.size() == 1) {
                    foundKey = key;
                    break;
                }
            }
            String value = possibleCausesMap.get(foundKey).iterator().next();
            possibleCausesMap.remove(foundKey);
            dangerousIngredients.add(value);
            System.out.println("ingredient " + value + " contains: " + foundKey);
            allergenIngredients.add(new AllergenIngredient(value, foundKey));
            for(String key : possibleCausesMap.keySet()) {
                Set<String> values = possibleCausesMap.get(key);
                if (values.contains(value)) {
                    values.remove(value);
                    possibleCausesMap.put(key, values);
                }
            }
        }
        int count = 0;
        for(Combination combination : input) {
            Set<String> ingredients = combination.ingredients;
            for(String value : ingredients) {
                if (!dangerousIngredients.contains(value)) {
                    count++;
                }
            }
        }
        System.out.println("Result:" + count);

        AllergenIngredient[] array = allergenIngredients.toArray(new AllergenIngredient[0]);

        // wrong
        // spl,hsksz,fmpgn,tpnnkc,qzzzf,sp,hqgqj,bxjvzk,
        Arrays.sort(array);
        for(AllergenIngredient ai : array) {
            System.out.print(ai.ingredient);
            System.out.print(",");
        }

    }



    private static Pattern idRegex = Pattern.compile("([a-z ]+) \\(contains ([a-z ,]+)\\)");

    private List<Combination> getInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("/Users/marc/Desktop/input21.txt"));
        List<Combination> combinations = new ArrayList<>();

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher idMatcher = idRegex.matcher(line);
            idMatcher.find();
            String part1 = idMatcher.group(1);
            String part2 = idMatcher.group(2);
            String[] ingredients = part1.split(" ");
            String[] allergens = part2.split(", ");
            Combination combination = new Combination();
            combination.ingredients.addAll(Arrays.asList(ingredients));
            combination.allergens.addAll(Arrays.asList(allergens));
            combinations.add(combination);
        }
        scanner.close();
        return combinations;
    }


}