
public class EclipseCore extends MobNPC {

    public EclipseCore(String name, String charClass, String type, String weapon, int healthPoints, int chapter) {
        super(name, charClass, type, weapon, healthPoints, chapter);
    }

    @Override
    public void useSkill(int skill, Character target) {
        eclipseCoreSkills(skill, target);
    }

    // ----------------- Eclipse Core's Skills -----------------
    private void eclipseCoreSkills(int skill, Character target) {
        int damage = 0;
        switch (skill) {
            case 1: // Shadow Bolt
                damage = (int)(Math.random() * 11) + 5;
                target.takedamage(damage);
                System.out.println(name + " used Shadow Bolt! Deals " + damage + " damage.");
                break;
            case 2: // Void Sphere
                damage = (int)(Math.random() * 21) + 10;
                target.takedamage(damage);
                System.out.println(name + " used Void Sphere! Deals " + damage + " damage.");
                break;
            case 3: // Eclipse Burst
                damage = (int)(Math.random() * 41) + 20;
                target.takedamage(damage);
                System.out.println(name + " used Eclipse Burst! Deals " + damage + " damage.");
                break;
        }
    }
}