
public abstract class Character extends Entity implements Healable {
    int manaPoints;

    public Character(String name, String charClass, String type, String weapon, int healthPoints, int manaPoints) {
        super(name, charClass, type, weapon, healthPoints);
        this.manaPoints = manaPoints;
    }
    
    // heal and MP methods are still required by Healable
    @Override
    public void healAlly(int heal){
        healthPoints += heal;
        System.out.println(name + " healed for " + heal + " HP. Current HP: " + healthPoints);
    }

    @Override
    public void restoreMP(int mp){
        manaPoints += mp;
        System.out.println(name + " restored " + mp + " MP. Current MP: " + manaPoints);
    }

    // ================== Skill System ==================
    //To use skills
    public abstract void useSkill(int skill, MobNPC target, Character ally, Character[] party);

    // ----------------- Show Stats -----------------
    public void showStats() {
        System.out.println("---- " + name + " ----");
        System.out.println("Class: " + charClass);
        System.out.println("Type: " + type);
        System.out.println("Weapon: " + weapon);
        System.out.println("HP: " + healthPoints + ", MP: " + manaPoints);
        System.out.println("------------------");
        System.out.println();
    }

    // Return the skill list for this character as an array of strings
    // Each entry should describe the skill name and any MP cost or requirement
    public abstract String[] getSkillList();

    // Return a string indicating the target type for a given skill index.
    // Possible return values: "ENEMY", "ALLY", "ALL", "SELF".
    public abstract String getSkillTargetType(int skill);

    // Decrement status durations each turn (delegated to Entity)
    @Override
    public void tickStatus() {
        super.tickStatus();
    }
}
