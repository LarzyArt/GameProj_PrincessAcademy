package charmees.util;
public class MobNPC extends Entity {
    public int chapter;

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

    public int getSkillCount() {
        if (charClass.equals("Boss") 
                || charClass.equals("Mini-Boss")) 
            return 3; // Bosses and mini-bosses have 3 skills
        return 2;// Regular mobs have 2 skills
        
    }



}