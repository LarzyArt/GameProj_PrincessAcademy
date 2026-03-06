public class MobNPC extends Entity {
    int chapter;

    public MobNPC(String name, String charClass, String type, String weapon, int healthPoints, int chapter) {
        super(name, charClass, type, weapon, healthPoints);
        this.chapter = chapter;
    }


    //=================== Skill System ==================
    //To use skills 

    public void useSkill(int skill, Character target) {
        // Default: no skills for generic mobs
        System.out.println(name + " has no skills defined!");
    }

    //----------------- Show Stats -----------------



}