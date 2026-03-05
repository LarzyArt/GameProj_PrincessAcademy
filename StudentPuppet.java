
public class StudentPuppet extends MobNPC {

    public StudentPuppet(String name, String charClass, String type, String weapon, int healthPoints, int chapter) {
        super(name, charClass, type, weapon, healthPoints, chapter);
    }

    @Override
    public void useSkill(int skill, Character target) {
        studentPuppetSkills(skill, target);
    }

    // ----------------- Student Puppet's Skills -----------------
    private void studentPuppetSkills(int skill, Character target) {
        int damage = 0;
        switch (skill) {
            case 1: // Puppet Punch
                damage = (int)(Math.random() * 6) + 5;
                target.takedamage(damage);
                System.out.println(name + " used Puppet Punch! Deals " + damage + " damage.");
                break;
            case 2: // String Lash
                damage = (int)(Math.random() * 11) + 5;
                target.takedamage(damage);
                System.out.println(name + " used String Lash! Deals " + damage + " damage.");
                break;
        }
    }
}