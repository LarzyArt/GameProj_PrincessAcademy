package charmees.util;
public abstract class Entity implements StatusEffectable {
    public String name;
    public String charClass;
    public String type;
    public String weapon;
    public int healthPoints;


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

}
