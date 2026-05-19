package charmees.Display;

import charmees.Util.Character;
import charmees.Util.MobNPC;
import charmees.Util.Display;
import java.util.Scanner;

public class BattleDisplay {

    //TODO: implement battle display

    //MAINLY: show the battlefield, show the active character and mob, 
    // show the HP of all characters and mobs, show the turn count, show the chapter, 
    // show the actions available to the active character, 
    // show the actions available to the active mob 

    public static void showBattleField(
        Character[] characters,
        MobNPC[] mobs,
        int chapter,
        int activeIdx,
        int targetIdx,
        int[] charMaxHP,
        int[] mobMaxHP,
        int turncount)
        {
            // clear screen
            Display.gap();
            Display.header("CHAPTER " + chapter + " - TURN " + turncount);
            
            // enemy field section
            Display.gap();
            showEnemyField(mobs, chapter, targetIdx, mobMaxHP);

            // active character section
            Display.gap();
            showActiveChar(characters[activeIdx], charMaxHP[activeIdx]);
             
            // bench section
            Display.gap();
            showBench(characters, activeIdx, charMaxHP);
            
            // thin line separator
            Display.thin();
        }

        // enemy Section
        public static void showEnemyField(MobNPC[] mobs, int chapter, int targetIdx, int[] mobMaxHP){
                System.out.println("ENEMIES: ");
                for (int i = 0; i < mobs.length; i++){
                    MobNPC m = mobs[i];
                    if (m.chapter != chapter) continue;

                    Display.gap();

                    if(!m.isAlive()){
                        System.out.println(" " + m.getName() + " [" + m.charClass + "] - DEFEATED");
                        continue;
                    }

                    String target = (i == targetIdx) ? " <-- TARGET" : "";
                    System.out.println(" " + m.getName() + " [" + m.charClass + "]"
                    + " HP: " + m.healthPoints + "/" + mobMaxHP[i] + target);
                    System.out.println(" " + hpBar(m.healthPoints, mobMaxHP[i], 30)
                    + " " + percent(m.healthPoints,mobMaxHP[i]) + "%");
                }
            }

            // active character Section
            public static void showActiveChar(Character c, int maxHP){
                System.out.println("ACTIVE CHARACTER: ");
                Display.thin();
                System.out.println(" " + c.getName() + " " 
                + "   HP: " + c.healthPoints + "/" + maxHP
                + "   MP: " + c.manaPoints + "/" + c.manaPoints);

                System.out.println(" " + hpBar(c.healthPoints, maxHP, 30)
                + " " + percent(c.healthPoints, maxHP) + "%");
            }

        // Bench Section
        private static void showBench(Character[] characters, int activeIdx, int[] charMaxHP){
            System.out.print("   PARTY: ");
            for(int i = 0; i < characters.length; i++){
                Character c = characters[i];
                String label;

                if(!c.isAlive()){
                    label = c.getName() + " [DEFEATED]";
                } else if (i == activeIdx){
                    label = c.getName() + " [IN BATTLE]";
                } else if (i == 2) {
                    label = c.getName() + "[HEALER]";
                } else {
                    label = c.getName() + " [BENCH] [" + percent(c.healthPoints, charMaxHP[i]) + "%]";
                }

                System.out.print(label);
                if(i < characters.length - 1) System.out.print(" ");
            }
            System.out.println();
        }

        // ================================================
        // ACTION DISPLAY
        // ================================================
        public static void showActionDisplay(Character actor ){
            Display.gap();
            Display.thin();
            System.out.println("What will " + actor.getName() + "do?");
            Display.gap();

            System.out.println("  [1] SKILL"); // skill option(skill 1 or skill 2) will be handled in BattleLogic based on the character
            System.out.println("  [2] ULTIMATE"); // uses signature move (skill 3) will be handled in BattleLogic based on the character
            System.out.println("  [3] SWITCH"); // switch to another character in the bench
            System.out.println("  [4] LAZULI'S BLESSING"); // heals characters based on lazuli's skills, will be handled in BattleLogic based on the character 

            Display.thin();
            System.out.println("  > ");
        }

        // ================================================
        //SKILL MENU
        // ================================================
        
        // shows the skill menu for the active character,
        //  with options to select skill 1 or skill 2,
        //  or cancel and return to the action display
        public static void showSkillMenu(Character actor){
            String[] skills = actor.getSkillList();
            Display.gap();
            System.out.println(" " + actor.getName() + " -- Skills:");
            for (int i = 0; i < Math.min(2, skills.length); i++){
                System.out.println("  [" + (i + 1) + "] " + skills[i]);
            }
            System.out.println("  [3] Cancel");
            Display.thin();
            System.out.println("  > ");
        }

        // shows the ultimate confirmation menu for the active character,
        //  with the option to confirm using the ultimate, or cancel and return to the action
        public static void showUltimateConfirm(Character actor) {
            String[] skills = actor.getSkillList();
            Display.gap();
            System.out.println(" " + actor.getName() + " -- Ultimate:");
            Display.thin();
            System.out.println(" " + skills[2]);
            Display.thin();
            System.out.println(" Risk it all? [Y/N]");
            System.out.println(" [1] Use it!");
            System.out.println(" [0] Cancel");
            Display.thin();
            System.out.println(" > ");
        }

        // shows the switch menu to switch active character, with options to select any alive bench character,
        //  or cancel and return to the action display
        public static void showSwitchMenu(Character[] characters, int activeIdx, int[] charMaxHP){
                Display.gap();
                System.out.println(" SWITCH: Tag who's in?");
                Display.thin();

                int opt = 1; // option number for switching, starts at 1 
                for (int i = 0; i < characters.length; i++){
                    if (i == 2) continue; // skips lazuli
                    if (i == activeIdx) continue; // skip active character
                    if(!characters[i].isAlive()) continue; // skip defeated characters

                    System.out.println(" [" + opt + "] " + characters[i].getName()
                    + "   HP: " + characters[i].healthPoints + "/" + charMaxHP[i]
                    + "   MP: " + characters[i].manaPoints);
                    opt++;
                }

                System.out.println("   [0]Cancel");
                Display.thin();
                System.out.println("   > ");
        }

        // shows the heal menu for lazuli's blessing, with options to select any character to heal,
        //  or cancel and return to the action display
        public static void showHealMenu(Character lazuli){
            String[] skills = lazuli.getSkillList();
            Display.gap();
            System.out.println(" LAZULI BLESSING (MP: " + lazuli.manaPoints + ")");
            Display.thin();
            for (int i = 0; i < skills.length; i++){
                System.out.println("  [" + (i + 1) + "] " + skills[i]);
            }
            System.out.println("  [0] Cancel");
            Display.thin();
            System.out.println("  > ");
        }

        // shows the ally picker menu to select a target for healing, with options to select any alive character,
        //  or cancel and return to the action display 
        public static void showAllyPicker(Character[] characters, int[] charMaxHP){
            Display.gap();
            System.out.println("  Eeny.. Meeny.. Miney.. Mo:");
            Display.thin();

            int opt = 1;
            for (int i = 0; i < characters.length; i++) {
                if (!characters[i].isAlive()) continue;
                System.out.println("  [" + opt + "] " + characters[i].getName()
                + "   HP: " + characters[i].healthPoints + "/" + charMaxHP[i]
                + "   MP:"+ characters[i].manaPoints);
                opt++;
                
            }
            Display.thin();
            System.out.println("  > ");
        }

        // shows the enemy picker menu to select a target for an attack, with options to select any alive enemy,
        //  or cancel and return to the action display
        public static void showEnemyPicker(MobNPC[] mobs, int chapter, int[] mobMaxHP){
            Display.gap();
            System.out.println("  Choose a target:");
            Display.thin();

            int opt = 1;
            for (int i = 0; i < mobs.length; i++) {
                if (mobs[i].chapter != chapter || !mobs[i].isAlive()) continue;
                System.out.println("  [" + opt + "] " + mobs[i].getName()
                + " HP: " + mobs[i].healthPoints + "/" + mobMaxHP[i]
                + " [" +mobs[i].charClass + "]");
                opt++;
            }
            Display.thin();
            System.out.println("  > ");
        }

        //=================================================
        // RESULTS DISPLAYS
        //=================================================
        public static void showVictoryDisplay(int chapter){
            Display.gap();
            pause(500);
            Display.line();
            pause(300);
            Display.centered("L████: VICTORY!");
            pause(300);
            Display.centered("L████: Chapter " + chapter + " Cleared!");
            pause(500);
            if (chapter == 1){
                Display.centered("L█z̴̧̜͈̓̈́̃́̕██: Ohhh... are you new?");
            } else if (chapter == 2){
                Display.centered("L█z̴̧̜͈̓̈́̃́̕l̵̨̨̡̗͓̲͓͂͜ͅ█: This looks fun... I wanna play with you!");
            } else if (chapter == 3){
                Display.centered("Ļ̶͕̝̖͚̟͇̙͇̀e̴̥̖̠̭̻̣̰͒̓́̅̓͑́͆͠z̴̧̜͈̓̈́̃́̕l̵̨̨̡̗͓̲͓͂͜ͅì̵̧̧̦̫̮̦̻̈́̈̑͑̍̚͘: huh... y̷̨̛̼̟̞͍̘͈̟̬͇̝͊̐̔̕ọ̵̻̭͈̥̅̏̾̆̑̀̐̓͆̕͠u̸͇͛̾̃̄̾̆ ̵̶̡̨̫̠̺̬̜͕̖̰͙̻̆͌̎́̈́̇̍̚̚w̷̬̓̾͋̆̐͊̿̑͌͆̕ǫ̸̡̯̜͕̮̻̖̯͚̠̻̬̎̀͂͠ņ̸̜̝͓͕̥̼̈́́̑͒̒̆͐͆̃̀̋̃͐̚?");
            }
            Display.thin();
            Display.gap();
        }
        
        public static void showDefeatDisplay(){
            Display.gap();
            pause(500);
            Display.line();
            pause(300);
            Display.centered("L████: DEFEAT...");
            pause(500);
            int rng =(int)(Math.random() * 3);
                if(rng == 0){
                    Display.centered("L████: Not quite what I expected...");
                } else if (rng == 1){
                    Display.centered("L█z̴̧̜͈̓̈́̃́̕██: Ohh... I'm Disappointed :'<");
                }  else if (rng == 2){
                    Display.centered("L█z̴̧̜͈̓̈́̃́̕l̵̨̨̡̗͓̲͓͂͜ͅ█: REWIND TIME! b̴̨̛̮͚̜̝̠̭͐̄̊̊͋̿̋̚̕̕͜͝r̴̰̅r̶̹̩̝̣͇̖̰̎ŗ̵̼̫̠̈̌̿̔̉̅̈́̑͒̍̅r̸̨̥̠͉̠͓͛̚a̷̡̰̼͖̠̗̖͉̜͔͇͑̍̀̀̉̅͒͘͠͝͝͠b̷̨̢̲̟̻̣̠̫̞̫̗̟͑́̾̏̿̓̐̉̄͐͐e̶̙͈̪̣̫̺̲̓͛̇̂̕j̵̣̭͈̘̻̀͆͒́̀̓̆̊̈́̒̅d̵̢̡̧̺̟̻̠̜͋̿͒̂̓̎̀̈́͜͝q̶͙̩̞̝͙͔̫̹̳͋́͆͆̀̍̅͛́̚͜͠͝w̴̪͈̫̿̽̏́̒͛k̴̥͆̑́͑̏̽̕̕ͅē̶̼͙̈́̈́͛̌͠ͅq̵̧̪̭͕͚̮͍̜̞̜̇̿̃̽͐͋̕͜");
                }
            Display.thin();
            Display.gap();
        }
        public static void showEnemyPhaseHeader(){
            Display.gap();
            Display.thin();
            System.out.println("-- ENEMY PHASE --");
            Display.thin();
        }
        public static void showPlayerPhaseHeader(Character actor){
            Display.gap();
            Display.thin();
            System.out.println("  -- " + actor.getName() + "'s Turn --");
            Display.thin();
        }


        // =================================================
        // DIALOGUE -prints each line, waits for enter between them
        // =================================================
        // tentative not final

        // ================================================
        //HELPERS
        // ================================================
        public static String hpBar(int hp, int max, int width){
            if (max <= 0) return "░".repeat(width);
            int filled = (int)((double) hp / max * width); // calculate filled portion
            filled = Math.max(0, Math.min(width, filled)); // ensure within bounds
            return "█".repeat(filled) + "░".repeat(width - filled); // create bar string
        }

        public static void log(String msg){
            System.out.println(" " + msg);
        }

        private static int percent(int hp, int max){
            if (max <= 0) return 0;
            return (int)((double) hp / max * 100);
        }

        public static void pause(int milliseconds){
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

}