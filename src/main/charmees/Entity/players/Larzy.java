package charmees.Entity.players;
import charmees.util.Character;
import charmees.util.MobNPC;

public class Larzy extends Character {

    public Larzy(String name, String charClass, String type, String weapon, int healthPoints, int manaPoints) {
        super(name, charClass, type, weapon, healthPoints, manaPoints);
    }

    @Override
    public void useSkill(int skill, MobNPC target, Character ally, Character[] party) {
        larzySkills(skill, target);
    }

    // ----------------- Larzy's Skills -----------------
    private void larzySkills(int skill, MobNPC target) {
        int damage = 0;
        switch (skill) {
            case 1: // Sketch Slap
                if (manaPoints >= 2) {
                    manaPoints -= 2;
                    damage = (int)(Math.random() * 9) + 8;
                    System.out.println(name + " used Sketch Slap! Smacks the enemy with a rough draft — unfinished, ugly, and surprisingly effective! Deals " + damage + " damage.");
                    target.takedamage(damage);
                } else System.out.println(name + " doesn't have enough mana! The sketchbook is empty, just like the artist's will to continue.");
                break;
            case 2: // Perspective Breakdown
                if (manaPoints >= 5) {
                    manaPoints -= 5;
                    int hits = (int)(Math.random() * 3) + 1;
                    damage = 0;
                    for (int i = 0; i < hits; i++) {
                        damage += (int)(Math.random() * 10) + 6;
                    }
                    System.out.println(name + " used Perspective Breakdown! Hits " + hits + " time(s) for " + damage + " damage. The vanishing point has vanished, and so has your HP.");
                    target.takedamage(damage);
                } else System.out.println(name + " doesn't have enough mana! Couldn't even draw a straight line today.");
                break;
            case 3: // Magnum Opus
                if (manaPoints >= 11) {
                    manaPoints -= 11;
                    damage = (int)(Math.random() * 26) + 85;
                    System.out.println(name + " used Ultimate, Magnum Opus! Unleashes a masterpiece so devastating it was rejected by every gallery — " + damage + " damage. Critics are speechless. So are you.");
                    target.takedamage(damage);
                } else System.out.println(name + " doesn't have enough mana! The commission got cancelled before the final piece.");
                break;
        }
    }

    @Override
    public String[] getSkillList() {
        return new String[]{
                "[1] Sketch Slap          - Cost: 2 MP  | A rough draft attack, unpolished but painful (8-16 dmg)",
                "[2] Perspective Breakdown - Cost: 5 MP  | Multi-hit that warps the enemy's entire worldview (6-15 per hit)",
                "[3] Magnum Opus          - Cost: 11 MP | The ultimate piece — gallery-rejected, enemy-destroying (85-110 dmg)"
        };
    }

    @Override
    public String getSkillTargetType(int skill) {
        return "ENEMY";
    }
}