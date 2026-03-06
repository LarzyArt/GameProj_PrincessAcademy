
public class Audry extends Character {

    public Audry(String name, String charClass, String type, String weapon, int healthPoints, int manaPoints) {
        super(name, charClass, type, weapon, healthPoints, manaPoints);
    }

    @Override
    public void useSkill(int skill, MobNPC target, Character ally, Character[] party) {
        // if this character is stunned, they cannot use skills
        if (this.isStunned()) {
            System.out.println(this.getName() + " is stunned and cannot use skills.");
            return;
        }
        audrySkills(skill, target);
    }

    // ----------------- Audry's Skills -----------------
    private void audrySkills(int skill, MobNPC target) {
        int damage = 0;
        switch (skill) {
            case 1: // Slime Bounce
                if (manaPoints >= 3) {
                    manaPoints -= 3;
                    damage = (int)(Math.random() * 21) + 20;
                    target.takedamage(damage);
                    System.out.println(name + " used Slime Bounce! Deals " + damage + " damage.");
                } else System.out.println(name + " doesn't have enough mana!");
                break;
            case 2: // Acid Shot
                if (manaPoints >= 7) {
                    manaPoints -= 7;
                    int hits = (int)(Math.random() * 3) + 1;
                    damage = 0;
                    for (int i = 0; i < hits; i++) {
                        damage += (int)(Math.random() * 11) + 5;
                    }
                    target.takedamage(damage);
                    System.out.println(name + " used Acid Shot! Hits " + hits + " times for " + damage + " damage.");
                } else System.out.println(name + " doesn't have enough mana!");
                break;
            case 3: // Beyond the Abyss
                if (manaPoints >= 15) {
                    manaPoints -= 15;
                    damage = 150;
                    target.takedamage(damage);
                    System.out.println(name + " used Ultimate, Beyond the Abyss! Deals " + damage + " fixed damage.");
                } else System.out.println(name + " doesn't have enough mana!");
                break;
        }
    }

    @Override
    public String[] getSkillList() {
        return new String[]{"1) Slime Bounce - Cost: 3 MP","2) Acid Shot - Cost: 7 MP","3) Beyond the Abyss - Cost: 15 MP"};
    }

    @Override
    public String getSkillTargetType(int skill) {
        return "ENEMY";
    }
}