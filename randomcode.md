// LAZULI HEAL — if Lazuli is in the party, player can choose to heal with her instead of attacking
    private void onLazuliHeal() {
            Character lazuli = characters[2];
            if (!lazuli.isAlive()) {
                BattleDisplay.log("Lazuli has fallen and cannot heal.");
                return;
            }
    
            BattleDisplay.showHealMenu(lazuli);
            int pick = Display.readInt(sc);
    
            String[] skills = lazuli.getSkillList();
            if (pick < 1 || pick > skills.length) {
                BattleDisplay.log("Cancelled.");
                return;
            }
    
            String tType = lazuli.getSkillTargetType(pick);
    
            if (tType.equals("ALLY")) {
                Character ally = pickAlly();
                if (ally == null) return;
                BattleDisplay.log("Lazuli heals " + ally.getName() + "!");
                lazuli.useSkill(pick, null, ally, characters);
            } else { // ALL
                BattleDisplay.log("Lazuli uses " + skills[pick - 1] + "!");
                lazuli.useSkill(pick, null, lazuli, characters);
            }
    
            // cap all HP at max — no overhealing
            for (int i = 0; i < characters.length; i++) {
                if (characters[i].healthPoints > charMaxHP[i])
                    characters[i].healthPoints = charMaxHP[i];
            }
    
            endPlayerTurn();
        }

        
    // attack logic
    private void onSkill() {
        Character actor = characters[activeIdx];
        
        // if the active character has no skills, show message and return
        BattleDisplay.showSkillMenu(actor);
        int pick = Display.readInt(sc);

        // if player picks invalid number, return to main menu
        if(pick < 1 || pick > Math.min(2, actor.getSkillList().length)){
            BattleDisplay.log("Cancelled.");
            return;
        }

        // execute the picked skill
        executeCharacterSkill(actor, pick);
    }

    // ultimate move logic
    private void onUlt(){
        Character actor = characters[activeIdx];
        
        // if the active character has no sig, show message and return
        String[] skills = actor.getSkillList();
        if(skills.length < 3){
            BattleDisplay.log(actor.getName() + "has no Ultimate Move available.");
            return;
        }
        
        BattleDisplay.showUltimateConfirm(actor);
        int confirm = Display.readInt(sc);

        if(confirm != 1){ BattleDisplay.log("Cancelled."); return; }
        
        // execute ultimate move (assumed to be skill 3)
        executeCharacterSkill(actor, 3);
    }