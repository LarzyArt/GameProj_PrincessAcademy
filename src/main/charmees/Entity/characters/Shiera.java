package charmees.Entity.characters;
import charmees.util.Character;
import charmees.util.MobNPC;

public class Shiera extends Character {

    public Shiera(String name, String charClass, String type, String weapon, int healthPoints, int manaPoints) {
        super(name, charClass, type, weapon, healthPoints, manaPoints);
    }

    @Override
    public void useSkill(int skill, MobNPC target, Character ally, Character[] party) {
        shieraSkills(skill, target, ally);
    }

    // ----------------- Shiera's Skills -----------------
    private void shieraSkills(int skill, MobNPC target, Character ally) {
        int damage = 0;
        switch (skill) {
            case 1: // Stone Spikes
                if (manaPoints >= 4) {
                    manaPoints -= 4;
                    int hits = (int)(Math.random() * 4) + 1;
                    for (int i = 0; i < hits; i++) {
                        damage += (int)(Math.random() * 10) + 1;
                    }
                    System.out.println(name + " used Stone Spikes! Hits " + hits + " times for " + damage + " damage.");
                    target.takedamage(damage);
                } else System.out.println(name + " doesn't have enough mana!");
                break;
            case 2: // Earth Wall
                if (manaPoints >= 6) {
                    manaPoints -= 6;
                    int hits = (int)(Math.random() * 4) + 1;
                    damage = 0;
                    for (int i = 0; i < hits; i++) {
                        damage += (int)(Math.random() * 10) + 1;
                    }
                    System.out.println(name + " used Earth Wall! Hits " + hits + " times for " + damage + " damage.");
                    target.takedamage(damage);
                } else System.out.println(name + " doesn't have enough mana!");
                break;
            case 3: // Iron Maiden
                if (manaPoints >= 10) {
                    manaPoints -= 10;
                    damage = 50;
                    System.out.println(name + " used Ultimate, Iron Maiden! Deals " + damage + " damage.");
                    target.takedamage(damage);
                } else System.out.println(name + " doesn't have enough mana!");
                break;
        }
    }

    @Override
    public String[] getSkillList() {
        return new String[]{"[1] Stone Spikes - Cost: 4 MP",
        "[2] Earth Wall - Cost: 6 MP",
        "[3] Iron Maiden - Cost: 10 MP"};
    }

    @Override
    public String getSkillTargetType(int skill) {
        switch (skill) {
            case 2: return "SELF"; // Earth Wall
            default: return "ENEMY";
        }
    }
}