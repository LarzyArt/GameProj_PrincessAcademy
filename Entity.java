public abstract class Entity implements StatusEffectable {
    String name;
    String charClass;
    String type;
    String weapon;
    int healthPoints;

    // status effect counters
    int tauntTurns = 0;
    int damageReductionTurns = 0;
    double damageReductionPercent = 0.0;
    int stunnedTurns = 0;

    public Entity(String name, String charClass, String type, String weapon, int healthPoints) {
        this.name = name;
        this.charClass = charClass;
        this.type = type;
        this.weapon = weapon;
        this.healthPoints = healthPoints;
    }

    public String getName() {
        return name;
    }

    public void takedamage(int damage){
        // apply damage reduction if active
        if (damageReductionTurns > 0 && damageReductionPercent > 0.0) {
            int reduced = (int) Math.round(damage * (1.0 - damageReductionPercent));
            damage = Math.max(0, reduced);
        }
        healthPoints -= damage;
        if (healthPoints <= 0) {
            healthPoints = 0;
            System.out.println(name + " took " + damage + " damage! Remaining HP: " + healthPoints);
            // character vs mob messages may be handled by subclasses
        } else {
            System.out.println(name + " took " + damage + " damage! Remaining HP: " + healthPoints);
        }
    }

    public boolean isAlive(){
        return healthPoints > 0;
    }

    // generic stats display
    public void showStats() {
        System.out.println("---- " + name + " ----");
        System.out.println("Class: " + charClass);
        System.out.println("Type: " + type);
        System.out.println("Weapon: " + weapon);
        System.out.println("HP: " + healthPoints);
        System.out.println("------------------");
        System.out.println();
    }

    // StatusEffectable implementations
    public boolean isStunned() { return stunnedTurns > 0; }
    public void applyStun(int turns) { stunnedTurns = Math.max(stunnedTurns, turns); System.out.println(name + " is stunned for " + stunnedTurns + " turn(s)."); }
    public void applyTaunt(int turns) { tauntTurns = Math.max(tauntTurns, turns); System.out.println(name + " is taunted for " + tauntTurns + " turn(s)."); }
    public void applyDamageReduction(double percent, int turns) { damageReductionPercent = percent; damageReductionTurns = Math.max(damageReductionTurns, turns); System.out.println(name + " has damage reduction for " + damageReductionTurns + " turn(s)."); }

    public void tickStatus() {
        if (tauntTurns > 0) tauntTurns--;
        if (stunnedTurns > 0) stunnedTurns--;
        if (damageReductionTurns > 0) {
            damageReductionTurns--;
            if (damageReductionTurns == 0) damageReductionPercent = 0.0;
        }
    }
}
