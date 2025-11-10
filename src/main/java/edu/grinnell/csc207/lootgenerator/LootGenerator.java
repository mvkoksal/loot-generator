package edu.grinnell.csc207.lootgenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class LootGenerator {
    /** The path to the dataset (either the small or large set). */
    private static final String DATA_SET_SMALL = "data/small";
    private static final String DATA_SET_LARGE = "data/large";
    private static final int TREASURE_OPTIONS = 3;
    

    public Monster pickMonster(String filename) throws IOException {
        Scanner monsterFile;
        monsterFile = new Scanner(new File(filename));

        ArrayList<Monster> monsterList = new ArrayList<>();
    
        while(monsterFile.hasNextLine()) {
            String newLine = monsterFile.nextLine();
            String[] parts = newLine.split("\t");

            String name = parts[0];
            String type = parts[1];
            String level = parts[2];
            String treasureClass = parts[3];

            Monster monster = new Monster(name, type, level, treasureClass);
            monsterList.add(monster);
            
        }

        Random randomNumGenerator = new Random();
        int randomNum = randomNumGenerator.nextInt(monsterList.size());
        Monster monster = monsterList.get(randomNum);

        monsterFile.close();
        return monster;
        // System.out.println("name :" + monster.name);
        // System.out.println("type :" + monster.type);
        // System.out.println("level :" + monster.level);
        // System.out.println("treasureClass :" + monster.treasureClass);
    }

    public String fetchTreasureClass(Monster monster) {
        return monster.treasureClass;
    }
    
    public String generateBaseItem(String filename, String treasureClassName) throws IOException {
        Scanner treasureFile;
        treasureFile = new Scanner(new File(filename));

        HashMap<String, TreasureClass> treasureList = new HashMap<>();
    
        while(treasureFile.hasNextLine()) {
            String newLine = treasureFile.nextLine();
            String[] parts = newLine.split("\t");

            String name = parts[0];
            String item1 = parts[1];
            String item2 = parts[2];
            String item3 = parts[3];

            TreasureClass treasure = new TreasureClass(name, item1, item2, item3);
            treasureList.put(treasure.name, treasure);
        }
        String curTreasure = treasureClassName;
        while(true) {
            if (treasureList.containsKey(curTreasure)) {
                Random randomNumGenerator = new Random();
                int randomNum = randomNumGenerator.nextInt(TREASURE_OPTIONS);
                if (randomNum == 0) {
                    curTreasure = treasureList.get(curTreasure).item1;
                    //System.out.println("1:" + curTreasure);
                    if (!treasureList.containsKey(curTreasure)) {
                        break;
                    }
                } else if (randomNum == 1) {
                    curTreasure = treasureList.get(curTreasure).item2;
                    //System.out.println("2:" + curTreasure);
                    if (!treasureList.containsKey(curTreasure)) {
                        break;
                    }
                } else if (randomNum == 2) {
                    curTreasure = treasureList.get(curTreasure).item3;
                    //System.out.println("3:" + curTreasure);
                    if (!treasureList.containsKey(curTreasure)) {
                        break;
                    }
                }   
            } else {
                System.out.println("The given treasureClass is not in data");
                break;
            }
        }
        treasureFile.close();
        return curTreasure;
    }
        
    public int generateBaseStats(String filename, String armorName) throws IOException {
        Scanner armorFile;
        armorFile = new Scanner(new File(filename));

        HashMap<String, Armor> armorList = new HashMap<>();
    
        while(armorFile.hasNextLine()) {
            String newLine = armorFile.nextLine();
            String[] parts = newLine.split("\t");

            String name = parts[0];
            String minacS = parts[1];
            String maxacS = parts[2];

            int minac = Integer.parseInt(minacS);
            int maxac = Integer.parseInt(maxacS);

            
            Armor armor = new Armor(name, minac, maxac);
            armorList.put(armor.name, armor);
        }

        int minac = 0;
        int maxac = 0;
        if (armorList.containsKey(armorName)) {
            minac = armorList.get(armorName).minac;
            maxac = armorList.get(armorName).maxac;
        } else {
            System.out.println("Error. The given armorName is not in data.");
        }

        Random random = new Random();
        int randomNum = random.nextInt(minac, maxac+1);

        armorFile.close();
        return randomNum;
    }


    // generateBaseStats(...)
    // generateAffix(...)


    public static void main(String[] args) throws IOException {
        System.out.println("This program kills monsters and generates loot!");
        LootGenerator loots = new LootGenerator();
        Monster monster = loots.pickMonster(DATA_SET_SMALL + "/monstats.txt");
        String treasureClass = loots.fetchTreasureClass(monster);
        String armor = loots.generateBaseItem(DATA_SET_SMALL + "/TreasureClassEx.txt", treasureClass);
        System.out.println(armor);

        int defenseNum = loots.generateBaseStats(DATA_SET_SMALL + "/armor.txt", armor);
        System.out.println(defenseNum);

    }
}
