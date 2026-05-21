package charmees.Entity.players;
import charmees.util.Character;
import charmees.util.MobNPC;

public class Abby extends Character {

    public Abby(String name, String charClass, String type, String weapon, int healthPoints, int manaPoints) {
        super(name, charClass, type, weapon, healthPoints, manaPoints);
    }

    @Override
    public void useSkill(int skill, MobNPC target, Character ally, Character[] party) {
        abbySkills(skill, target);
    }

    // ----------------- Abby's Skills -----------------
    private void abbySkills(int skill, MobNPC target) {
        int damage = 0;
        switch (skill) {
            case 1: // Wrist Grab Dash
                if (manaPoints >= 2) {
                    manaPoints -= 2;
                    damage = (int)(Math.random() * 9) + 6;
                    System.out.println(name + " used Wrist Grab Dash! Grabs the enemy dramatically and pulls them into a wall — because personal space is a season 2 concept. Deals " + damage + " damage.");
                    target.takedamage(damage);
                } else System.out.println(name + " doesn't have enough mana! The FL just walked away and took all motivation with her.");
                break;
            case 2: // Second Lead Syndrome
                if (manaPoints >= 5) {
                    manaPoints -= 5;
                    int hits = (int)(Math.random() * 3) + 1;
                    damage = 0;
                    for (int i = 0; i < hits; i++) {
                        damage += (int)(Math.random() * 10) + 7;
                    }
                    System.out.println(name + " used Second Lead Syndrome! Hits " + hits + " time(s) for " + damage + " damage. Did everything right, still lost to the male lead with better hair.");
                    target.takedamage(damage);
                } else System.out.println(name + " doesn't have enough mana! Too busy staring longingly out of a cafe window.");
                break;
            case 3: // Makjang Finale
                if (manaPoints >= 12) {
                    manaPoints -= 12;
                    damage = (int)(Math.random() * 31) + 90;
                    System.out.println(name + " used Ultimate, Makjang Finale! Reveals a long-lost twin, fakes amnesia, and delivers a speech in the pouring rain — " + damage + " damage. The OST is playing. You're already crying.");
                    target.takedamage(damage);
                } else System.out.println(name + " doesn't have enough mana! The drama got cut from 16 episodes to 12. Budget issues.");
                break;
        }
    }

    @Override
    public String[] getSkillList() {
        return new String[]{
                "[1] Wrist Grab Dash      - Cost: 2 MP  | Dramatic grab that skips consent and goes straight to damage (6-14 dmg)",
                "[2] Second Lead Syndrome - Cost: 5 MP  | Multi-hit from a man who deserved better (7-16 per hit)",
                "[3] Makjang Finale       - Cost: 12 MP | Rain-soaked emotional nuke with a killer OST (90-120 dmg)"
        };
    }

    @Override
    public String getSkillTargetType(int skill) {
        return "ENEMY";
    }
}