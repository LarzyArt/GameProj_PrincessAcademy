
public class EchoImp extends MobNPC {

    public EchoImp(String name, String charClass, String type, String weapon, int healthPoints, int chapter) {
        super(name, charClass, type, weapon, healthPoints, chapter);
    }

    @Override
    public void useSkill(int skill, Character target) {
        echoImpSkills(skill, target);
    }

    // ----------------- Echo Imp's Skills -----------------
    private void echoImpSkills(int skill, Character target) {
        int damage = 0;
        switch (skill) {
            case 1: // Echo Scream
                damage = (int)(Math.random() * 6) + 5;
                target.takedamage(damage);
                System.out.println(name + " used Echo Scream! Deals " + damage + " damage.");
                break;
            case 2: // Sonic Wave
                damage = (int)(Math.random() * 11) + 5;
                target.takedamage(damage);
                System.out.println(name + " used Sonic Wave! Deals " + damage + " damage.");
                break;
        }
    }
}