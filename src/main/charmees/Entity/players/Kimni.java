package charmees.Entity.players;
import charmees.util.Character;
import charmees.util.MobNPC;

public class Kimni extends Character {

    public Kimni(String name, String charClass, String type, String weapon, int healthPoints, int manaPoints) {
        super(name, charClass, type, weapon, healthPoints, manaPoints);
    }

    @Override
    public void useSkill(int skill, MobNPC target, Character ally, Character[] party) {
        kimniSkills(skill, target);
    }

    // ----------------- Kimni's Skills -----------------
    private void kimniSkills(int skill, MobNPC target) {
        int damage = 0;
        switch (skill) {
            case 1: // Speed Lines
                if (manaPoints >= 2) {
                    manaPoints -= 2;
                    damage = (int)(Math.random() * 11) + 10;
                    target.takedamage(damage);
                    System.out.println(name + " used Speed Lines! Dashes across the panel so fast the background can't keep up! Deals " + damage + " damage.");
                } else System.out.println(name + " doesn't have enough mana! Even the author won't give you a power-up arc.");
                break;
            case 2: // Plot Armor Piercer
                if (manaPoints >= 4) {
                    manaPoints -= 4;
                    int hits = (int)(Math.random() * 3) + 1;
                    damage = 0;
                    for (int i = 0; i < hits; i++) {
                        damage += (int)(Math.random() * 8) + 5;
                    }
                    target.takedamage(damage);
                    System.out.println(name + " used Plot Armor Piercer! Hits " + hits + " time(s) for " + damage + " damage. Sorry, your plot armor only covers the protagonist.");
                } else System.out.println(name + " doesn't have enough mana! The chapter got delayed. Come back next week.");
                break;
            case 3: // Final Chapter Drop
                if (manaPoints >= 9) {
                    manaPoints -= 9;
                    damage = (int)(Math.random() * 21) + 90;
                    target.takedamage(damage);
                    System.out.println(name + " used Ultimate, Final Chapter Drop! Slams a 500-chapter manga omnibus directly onto the enemy — " + damage + " damage. The series is complete. You are not.");
                } else System.out.println(name + " doesn't have enough mana! The manga went on hiatus. Classic.");
                break;
        }
    }

    @Override
    public String[] getSkillList() {
        return new String[]{
                "[1] Speed Lines        - Cost: 2 MP  | Blur across the panel at panel-breaking speed (10-20 dmg)",
                "[2] Plot Armor Piercer - Cost: 4 MP  | Multi-hit that bypasses any narrative convenience (5-13 per hit)",
                "[3] Final Chapter Drop - Cost: 9 MP  | End the fight like a 500-chapter series finale (90-110 dmg)"
        };
    }

    @Override
    public String getSkillTargetType(int skill) {
        return "ENEMY";
    }
}