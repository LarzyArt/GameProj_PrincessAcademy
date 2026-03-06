
public class PrincessPuppet extends MobNPC {

    public PrincessPuppet(String name, String charClass, String type, String weapon, int healthPoints, int chapter) {
        super(name, charClass, type, weapon, healthPoints, chapter);
    }

    @Override
    public void useSkill(int skill, Character target) {
        princessPuppetSkills(skill, target);
    }

    // ----------------- Princess Puppet's Skills -----------------
    private void princessPuppetSkills(int skill, Character target) {
        int damage = 0;
        switch (skill) {
            case 1: // Puppet Slap
                damage = (int)(Math.random() * 6) + 5;
                target.takedamage(damage);
                System.out.println(name + " used Puppet Slap! Deals " + damage + " damage.");
                break;
            case 2: // Royal Lash
                damage = (int)(Math.random() * 11) + 5;
                target.takedamage(damage);
                System.out.println(name + " used Royal Lash! Deals " + damage + " damage.");
                break;
        }
    }
}