
public class MagmaSkeleton extends MobNPC {

    public MagmaSkeleton(String name, String charClass, String type, String weapon, int healthPoints, int chapter) {
        super(name, charClass, type, weapon, healthPoints, chapter);
    }

    @Override
    public void useSkill(int skill, Character target) {
        magmaSkeletonSkills(skill, target);
    }

    // ----------------- Magma Skeleton's Skills -----------------
    private void magmaSkeletonSkills(int skill, Character target) {
        int damage = 0;
        switch (skill) {
            case 1: // Fire Bone
                damage = (int)(Math.random() * 6) + 5;
                target.takedamage(damage);
                System.out.println(name + " used Fire Bone! Deals " + damage + " damage.");
                break;
            case 2: // Lava Strike
                damage = (int)(Math.random() * 11) + 5;
                target.takedamage(damage);
                System.out.println(name + " used Lava Strike! Deals " + damage + " damage.");
                break;
        }
    }
}