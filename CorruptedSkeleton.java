
public class CorruptedSkeleton extends MobNPC {

    public CorruptedSkeleton(String name, String charClass, String type, String weapon, int healthPoints, int chapter) {
        super(name, charClass, type, weapon, healthPoints, chapter);
    }

    @Override
    public void useSkill(int skill, Character target) {
        corruptedSkeletonSkills(skill, target);
    }

    // ----------------- Corrupted Skeleton's Skills -----------------
    private void corruptedSkeletonSkills(int skill, Character target) {
        int damage = 0;
        switch (skill) {
            case 1: // Bone Strike
                damage = (int)(Math.random() * 6) + 5;
                target.takedamage(damage);
                System.out.println(name + " used Bone Strike! Deals " + damage + " damage.");
                break;
            case 2: // Skull Bash
                damage = (int)(Math.random() * 11) + 5;
                target.takedamage(damage);
                System.out.println(name + " used Skull Bash! Deals " + damage + " damage.");
                break;
        }
    }
}