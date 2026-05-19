package charmees.Entity.players;
import charmees.util.Character;
import charmees.util.MobNPC;

public class Cenicen extends Character {

    public Cenicen(String name, String charClass, String type, String weapon, int healthPoints, int manaPoints) {
        super(name, charClass, type, weapon, healthPoints, manaPoints);
    }

    @Override
    public void useSkill(int skill, MobNPC target, Character ally, Character[] party) {
        cenicenSkills(skill, target);
    }

    // ----------------- Cenicen's Skills -----------------
    private void cenicenSkills(int skill, MobNPC target) {
        int damage = 0;
        switch (skill) {
            case 1: // Stan Attacc
                if (manaPoints >= 3) {
                    manaPoints -= 3;
                    damage = (int)(Math.random() * 21) + 20;
                    target.takedamage(damage);
                    System.out.println(name + " used Stan Attacc! Throws lightsticks with unwavering stan energy! Deals " + damage + " damage.");
                } else System.out.println(name + " doesn't have enough mana! (Literally can't even right now)");
                break;
            case 2: // Bias Wrecker
                if (manaPoints >= 7) {
                    manaPoints -= 7;
                    int hits = (int)(Math.random() * 3) + 1;
                    damage = 0;
                    for (int i = 0; i < hits; i++) {
                        damage += (int)(Math.random() * 13) + 7;
                    }
                    target.takedamage(damage);
                    System.out.println(name + " used Bias Wrecker! Hits " + hits + " time(s) for " + damage + " damage. Your bias is shaking.");
                } else System.out.println(name + " doesn't have enough mana! Not even a comeback stage can save this.");
                break;
            case 3: // World Tour Finale
                if (manaPoints >= 15) {
                    manaPoints -= 15;
                    damage = 175;
                    target.takedamage(damage);
                    System.out.println(name + " used Ultimate, World Tour Finale! Closes the show with a devastating encore — " + damage + " fixed damage. No refunds.");
                } else System.out.println(name + " doesn't have enough mana! The world tour got cancelled.");
                break;
        }
    }

    @Override
    public String[] getSkillList() {
        return new String[]{
                "[1] Stan Attacc       - Cost: 3 MP  | Hurl lightstick energy at the enemy",
                "[2] Bias Wrecker      - Cost: 7 MP  | Multi-hit attack that ruins your enemy's bias",
                "[3] World Tour Finale - Cost: 15 MP | Ultimate drop that ends the whole concert"
        };
    }

    @Override
    public String getSkillTargetType(int skill) {
        return "ENEMY";
    }
}