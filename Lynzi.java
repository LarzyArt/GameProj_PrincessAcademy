
public class Lynzi extends Character {

    public Lynzi(String name, String charClass, String type, String weapon, int healthPoints, int manaPoints) {
        super(name, charClass, type, weapon, healthPoints, manaPoints);
    }

    @Override
    public void useSkill(int skill, MobNPC target, Character ally, Character[] party) {
        // if this character is stunned, they cannot use skills
        if (this.isStunned()) {
            System.out.println(this.getName() + " is stunned and cannot use skills.");
            return;
        }
        lynziSkills(skill, target);
    }

    // ----------------- Lynzi's Skills -----------------
    private void lynziSkills(int skill, MobNPC target) {
        int damage = 0;
        
        switch (skill) {
            case 1: // Majestic Kick
                damage = (int)(Math.random() * 21) + 20;
                target.takedamage(damage);
                System.out.println(name + " used Majestic Kick! Deals " + damage + " damage.");
                break;
            case 2: // Galactic Fist
                if (manaPoints >= 2) {
                    manaPoints -= 2;
                    damage = (int)(Math.random() * 36) + 20;
                    target.takedamage(damage);
                    System.out.println(name + " used Galactic Fist! Deals " + damage + " damage.");
                } else System.out.println(name + " doesn't have enough mana!");
                break;
            case 3: // Meteoric Smash
                if (manaPoints >= 10) {
                    manaPoints -= 10;
                    damage = (int)(Math.random() * 151) + 100;
                    target.takedamage(damage);
                    // Lynzi becomes unable to act for 2 turns after using Meteoric Smash
                    this.stunnedTurns = Math.max(this.stunnedTurns, 2);
                    System.out.println(name + " used Ultimate, Meteoric Smash! Deals " + damage + " damage and is exhausted for 2 turns.");
                } else System.out.println(name + " doesn't have enough mana!");
                break;
        }
    }

    @Override
    public String[] getSkillList() {
        return new String[]{"1) Majestic Kick - Cost: 0 MP","2) Galactic Fist - Cost: 2 MP","3) Meteoric Smash - Cost: 10 MP"};
    }

    @Override
    public String getSkillTargetType(int skill) {
        return "ENEMY";
    }
}