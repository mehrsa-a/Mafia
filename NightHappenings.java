public class NightHappenings {
    //night time methods
    public static void nightVoting(String act){
        String voted=MainGame.scanner.next();
        //recognize alive players
        String tempVoter=null;
        String tempVoted=null;
        for(int i = 0; i< MainGame.sizeOfDead; i++){
            if(MainGame.dead[i].name.equals(act)){
                tempVoter= MainGame.dead[i].name;
            }
            if(MainGame.dead[i].name.equals(voted)){
                tempVoted= MainGame.dead[i].name;
            }
        }
        if(tempVoter!=null){
            System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"user is dead"+ColorPrint.ANSI_RESET);
        }
        else if(tempVoted!=null){
            System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"voted already dead"+ColorPrint.ANSI_RESET);
        }
        else{
            //recognize players
            for(int i=0; i<MainGame.size; i++){
                if(MainGame.Player[i].name.equals(act)){
                    tempVoter= MainGame.Player[i].name;
                }
                if(MainGame.Player[i].name.equals(voted)){
                    tempVoted= MainGame.Player[i].name;
                }
            }
            if(tempVoter==null){
                System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"user not joined"+ColorPrint.ANSI_RESET);
            }
            else if(tempVoted==null){
                System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"user not joined"+ColorPrint.ANSI_RESET);
            }
            else{
                //some players can't wake at night
                boolean keepGoing=false;
                for(int i=0; i<MainGame.size; i++){
                    if(MainGame.Player[i].name.equals(act)){
                        if(MainGame.Player[i].role!=Roles.mafia&& MainGame.Player[i].role!=Roles.godfather&& MainGame.Player[i].role!=Roles.silencer&& MainGame.Player[i].role!=Roles.bomb&& MainGame.Player[i].role!=Roles.detective&& MainGame.Player[i].role!=Roles.doctor&& MainGame.Player[i].role!=Roles.freemason){
                            System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"user can not wake up during night"+ColorPrint.ANSI_RESET);
                            keepGoing=true;
                        }
                    }
                }
                if(!keepGoing){
                    for(int i=0; i<MainGame.size; i++){
                        if(MainGame.Player[i].name.equals(act)){
                            //mafia wake up
                            if(MainGame.Player[i].role==Roles.godfather|| MainGame.Player[i].role==Roles.mafia|| MainGame.Player[i].role==Roles.bomb){
                                if(!MainGame.Player[i].called){
                                    for(int j=0; j<MainGame.size; j++){
                                        if(MainGame.Player[j].name.equals(voted)){
                                            MainGame.Player[j].NightVoted++;
                                            if(MainGame.Player[i].role==Roles.mafia){
                                                MainGame.votedByMafia= MainGame.Player[j];
                                            }
                                            else{
                                                MainGame.votedByGodfather= MainGame.Player[j];
                                            }
                                            MainGame.tryToKill[MainGame.sizeOfTry]= MainGame.Player[i];
                                            MainGame.sizeOfTry++;
                                        }
                                    }
                                    MainGame.Player[i].called=true;
                                }
                                //mafias can change their vote
                                else{
                                    if(MainGame.Player[i].role==Roles.mafia){
                                        for(int j=0; j<MainGame.size; j++){
                                            if(MainGame.Player[j]== MainGame.votedByMafia){
                                                MainGame.Player[j].NightVoted--;
                                            }
                                        }
                                        for(int j=0; j<MainGame.size; j++){
                                            if(MainGame.Player[j].name.equals(voted)){
                                                MainGame.Player[j].NightVoted++;
                                                for(int k = 0; k< MainGame.sizeOfTry; k++){
                                                    if(MainGame.tryToKill[k]== MainGame.votedByMafia){
                                                        MainGame.tryToKill[k]= MainGame.Player[j];
                                                    }
                                                }
                                                MainGame.votedByMafia= MainGame.Player[j];
                                            }
                                        }
                                    }
                                    else{
                                        for(int j=0; j<MainGame.size; j++){
                                            if(MainGame.Player[j]== MainGame.votedByGodfather){
                                                MainGame.Player[j].NightVoted--;
                                            }
                                        }
                                        for(int j=0; j<MainGame.size; j++){
                                            if(MainGame.Player[j].name.equals(voted)){
                                                MainGame.Player[j].NightVoted++;
                                                for(int k = 0; k< MainGame.sizeOfTry; k++){
                                                    if(MainGame.tryToKill[k]== MainGame.votedByGodfather){
                                                        MainGame.tryToKill[k]= MainGame.Player[j];
                                                    }
                                                }
                                                MainGame.votedByGodfather= MainGame.Player[j];
                                            }
                                        }
                                    }
                                }
                            }
                            //silencer wake up
                            if(MainGame.Player[i].role==Roles.silencer){
                                //silencing someone as silencer
                                if(!MainGame.Player[i].called){
                                    for(int j=0; j<MainGame.size; j++){
                                        if(MainGame.Player[j].name.equals(voted)){
                                            MainGame.Player[j].silenced=true;
                                        }
                                    }
                                    MainGame.Player[i].called=true;
                                }
                                //kill someone as mafia
                                else{
                                    if(MainGame.votedBySilencer.role==Roles.unknown){
                                        for(int j=0; j<MainGame.size; j++){
                                            if(MainGame.Player[j].name.equals(voted)){
                                                MainGame.Player[j].NightVoted++;
                                                MainGame.votedBySilencer= MainGame.Player[j];
                                                MainGame.tryToKill[MainGame.sizeOfTry]= MainGame.Player[i];
                                                MainGame.sizeOfTry++;
                                            }
                                        }
                                    }
                                    //change vote
                                    else{
                                        for(int j=0; j<MainGame.size; j++){
                                            if(MainGame.Player[j]== MainGame.votedBySilencer){
                                                MainGame.Player[j].NightVoted--;
                                            }
                                        }
                                        for(int j=0; j<MainGame.size; j++){
                                            if(MainGame.Player[j].name.equals(voted)){
                                                MainGame.Player[j].NightVoted++;
                                                for(int k = 0; k< MainGame.sizeOfTry; k++){
                                                    if(MainGame.tryToKill[k]== MainGame.votedBySilencer){
                                                        MainGame.tryToKill[k]= MainGame.Player[j];
                                                    }
                                                }
                                                MainGame.votedBySilencer= MainGame.Player[j];
                                            }
                                        }
                                    }
                                }
                            }
                            //doctor wake up
                            if(MainGame.Player[i].role==Roles.doctor){
                                Doctor temp=(Doctor) MainGame.Player[i];
                                temp.action(voted);
                                MainGame.saved=temp.choose(voted);
                            }
                            //detective wake up
                            if(MainGame.Player[i].role==Roles.detective){
                                if(!MainGame.ask){
                                    MainGame.ask=true;
                                    ((Detective) MainGame.Player[i]).action(voted);
                                }
                                //detective can't ask twice or more at one night
                                else{
                                    System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"detective has already asked"+ColorPrint.ANSI_RESET);
                                }
                            }
                            //freemason wakeup
                            if(MainGame.Player[i].role==Roles.freemason){
                                for(int j=0; j<MainGame.size; j++){
                                    if(MainGame.Player[j].name.equals(voted)){
                                        if(MainGame.Player[j].role==Roles.godfather|| MainGame.Player[j].role==Roles.mafia|| MainGame.Player[j].role==Roles.bomb|| MainGame.Player[j].role==Roles.silencer){
                                            MainGame.freemasonKilled= MainGame.Player[i];
                                            MainGame.kill(MainGame.Player[i]);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    //end night methods
    public static void dayGuide(){
        MainGame.line();
        System.out.println("at start of the day players should vote to kill someone");
        System.out.println("for this action enter name of voter player and after that the voted one with one space");
        System.out.println("after voting type "+ColorPrint.ANSI_BLUE+"end_vote "+ColorPrint.ANSI_RESET+"and night will start");
        System.out.println("you can get number of alive players with "+ColorPrint.ANSI_BLUE+"get_game_state"+ColorPrint.ANSI_RESET);
        System.out.println("you can get name of alive players with "+ColorPrint.ANSI_BLUE+"list_of_alive_players"+ColorPrint.ANSI_RESET);
        MainGame.line();
        System.out.println("enter your action:");
    }
    //if max vote is belongs to bulletproof
    public static void bulletproofDead(Players max){
        if(max.dead){
            MainGame.kill(max);
            System.out.println(ColorPrint.ANSI_YELLOW+"mafia tried to kill "+max.name+ColorPrint.ANSI_RESET);
            System.out.println(ColorPrint.ANSI_YELLOW+max.name+" was killed"+ColorPrint.ANSI_RESET);
            System.out.println(ColorPrint.ANSI_YELLOW+max.name+" was "+max.role+ColorPrint.ANSI_RESET);
        }
        else{
            for(int j=0; j<MainGame.size; j++){
                if(MainGame.Player[j]==max){
                    MainGame.Player[j].dead=true;
                    break;
                }
            }
            System.out.println(ColorPrint.ANSI_YELLOW+"mafia tried to kill "+max.name+ColorPrint.ANSI_RESET);
            System.out.println(ColorPrint.ANSI_YELLOW+"nobody died"+ColorPrint.ANSI_RESET);
        }
    }
    public static void swap(){
        while(MainGame.array==null){
            System.out.println("swap two character with "+ColorPrint.ANSI_BLUE+"swap_character"+ColorPrint.ANSI_RESET);
            MainGame.scanner.next();
            String p1=MainGame.scanner.next();
            String p2=MainGame.scanner.next();
            //maybe we swap two character that one of them killed last night
            if(MainGame.freemasonKilled!=null){
                if(p1.equals(MainGame.freemasonKilled.name) || p2.equals(MainGame.freemasonKilled.name)){
                    System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"user was killed last night"+ColorPrint.ANSI_RESET);
                }
            }
            else if(p1.equals(findKilled().name)||p2.equals(findKilled().name)){
                System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"user was killed last night"+ColorPrint.ANSI_RESET);
            }
            else{
                //recognize alive players
                String tempP1=null;
                String tempP2=null;
                for(int i = 0; i< MainGame.sizeOfDead; i++){
                    if(MainGame.dead[i].name.equals(p1)){
                        tempP1= MainGame.dead[i].name;
                    }
                    if(MainGame.dead[i].name.equals(p2)){
                        tempP2= MainGame.dead[i].name;
                    }
                }
                if(tempP1!=null||tempP2!=null){
                    System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"user is dead"+ColorPrint.ANSI_RESET);
                }
                else{
                    //swap
                    Outer: for(int i=0; i<MainGame.size; i++){
                        if(MainGame.Player[i].name.equals(p1)){
                            String temp= MainGame.Player[i].name;
                            for(int j=0; j<MainGame.size; j++){
                                if(MainGame.Player[j].name.equals(p2)){
                                    MainGame.Player[i].name= MainGame.Player[j].name;
                                    MainGame.Player[j].name=temp;
                                    break Outer;
                                }
                            }
                        }
                    }
                    MainGame.array= new String[]{p1, p2};
                }
            }
        }
    }
    //finding the player who killed at night for swap method
    public static Players findKilled(){
        Players max=MainGame.Player[0];
        for(int i=0; i<MainGame.size; i++){
            if(MainGame.Player[i].NightVoted>=max.NightVoted){
                max= MainGame.Player[i];
            }
        }
        //find number of players that have max night voted
        int numberOfMax=0;
        for(int i=0; i<MainGame.size; i++){
            if(MainGame.Player[i].NightVoted==max.NightVoted){
                numberOfMax++;
            }
        }
        //killed
        if(numberOfMax==1){
            if(max.role==Roles.bulletproof){
                bulletproofDead(max);
            }
            else if(MainGame.saved!=max){
                return max;
            }
            else{
                return new Players(" ", Roles.unknown);
            }
        }
        else if(numberOfMax==2) {
            Players[] maxes = new Players[2];
            int n = 0;
            for (int i = 0; i < MainGame.size; i++) {
                if (MainGame.Player[i].NightVoted == max.NightVoted) {
                    maxes[n] = MainGame.Player[i];
                    n++;
                }
            }
            if (maxes[0] == MainGame.saved) {
                if (maxes[1].role != Roles.bulletproof) {
                    return maxes[1];
                }
            } else if (maxes[1] == MainGame.saved) {
                if (maxes[0].role != Roles.bulletproof) {
                    return maxes[0];
                }
            }
        }
        else if(MainGame.freemasonKilled!=null){
            return MainGame.freemasonKilled;
        }
        return new Players(" ", Roles.unknown);
    }
    public static void endNight(){
        swap();
        MainGame.counter++;
        MainGame.night++;
        System.out.println(ColorPrint.ANSI_RED+"Day "+MainGame.day+ColorPrint.ANSI_RESET);
        //check freemason situation
        if(MainGame.freemasonKilled!=null){
            System.out.println(ColorPrint.ANSI_YELLOW+ MainGame.freemasonKilled.name+" as freemason was wake a mafia up"+ColorPrint.ANSI_RESET);
            System.out.println(ColorPrint.ANSI_YELLOW+ MainGame.freemasonKilled.name+" was killed"+ColorPrint.ANSI_RESET);
            deleteKill(MainGame.freemasonKilled);
        }
        //find max night voted player
        Players max= MainGame.Player[0];
        for(int i=0; i<MainGame.size; i++){
            if(MainGame.Player[i].NightVoted>=max.NightVoted){
                max= MainGame.Player[i];
            }
        }
        //find number of players that have max night voted
        int numberOfMax=0;
        for(int i=0; i<MainGame.size; i++){
            if(MainGame.Player[i].NightVoted==max.NightVoted){
                numberOfMax++;
            }
        }
        //killed
        if(numberOfMax==1){
            if(max.role==Roles.bulletproof){
                bulletproofDead(max);
            }
            else if(MainGame.saved!=max){
                MainGame.kill(max);
                deleteKill(max);
                System.out.println(ColorPrint.ANSI_YELLOW+"mafia tried to kill "+max.name+ColorPrint.ANSI_RESET);
                System.out.println(ColorPrint.ANSI_YELLOW+max.name+" was killed"+ColorPrint.ANSI_RESET);
                System.out.println(ColorPrint.ANSI_YELLOW+max.name+" was "+max.role+ColorPrint.ANSI_RESET);
                if(max.role==Roles.informer){
                    Informer temp=(Informer)max;
                    System.out.println(ColorPrint.ANSI_YELLOW+temp.action()+ColorPrint.ANSI_RESET);
                }
            }
            else{
                System.out.println(ColorPrint.ANSI_YELLOW+"mafia tried to kill "+max.name+ColorPrint.ANSI_RESET);
                if(MainGame.freemasonKilled==null){
                    System.out.println(ColorPrint.ANSI_YELLOW+"nobody died"+ColorPrint.ANSI_RESET);
                }
            }
        }
        else if(numberOfMax==2){
            Players[] maxes=new Players[2];
            int n=0;
            for(int i=0; i<MainGame.size; i++){
                if(MainGame.Player[i].NightVoted==max.NightVoted){
                    maxes[n]= MainGame.Player[i];
                    n++;
                }
            }
            if(maxes[0]==MainGame.saved){
                if(maxes[1].role==Roles.bulletproof){
                    bulletproofDead(maxes[1]);
                }
                else{
                    MainGame.kill(maxes[1]);
                    deleteKill(maxes[1]);
                    System.out.println(ColorPrint.ANSI_YELLOW+"mafia tried to kill "+maxes[1].name+ColorPrint.ANSI_RESET);
                    System.out.println(ColorPrint.ANSI_YELLOW+maxes[1].name+" was killed"+ColorPrint.ANSI_RESET);
                    System.out.println(ColorPrint.ANSI_YELLOW+maxes[1].name+" was "+maxes[1].role+ColorPrint.ANSI_RESET);
                    if(maxes[1].role==Roles.informer){
                        Informer temp=(Informer)maxes[1];
                        System.out.println(ColorPrint.ANSI_YELLOW+temp.action()+ColorPrint.ANSI_RESET);
                    }
                }
            }
            else if(maxes[1]==MainGame.saved){
                if(maxes[0].role==Roles.bulletproof){
                    bulletproofDead(maxes[0]);
                }
                else{
                    MainGame.kill(maxes[0]);
                    deleteKill(maxes[0]);
                    System.out.println(ColorPrint.ANSI_YELLOW+"mafia tried to kill "+maxes[0].name+ColorPrint.ANSI_RESET);
                    System.out.println(ColorPrint.ANSI_YELLOW+maxes[0].name+" was killed"+ColorPrint.ANSI_RESET);
                    System.out.println(ColorPrint.ANSI_YELLOW+maxes[0].name+" was "+maxes[0].role+ColorPrint.ANSI_RESET);
                    if(maxes[0].role==Roles.informer){
                        Informer temp=(Informer)maxes[0];
                        System.out.println(ColorPrint.ANSI_YELLOW+temp.action()+ColorPrint.ANSI_RESET);
                    }
                }
            }
            else{
                if(MainGame.freemasonKilled==null){
                    System.out.println(ColorPrint.ANSI_YELLOW+"nobody died"+ColorPrint.ANSI_RESET);
                }
            }
        }
        else{
            if(MainGame.freemasonKilled==null){
                System.out.println(ColorPrint.ANSI_YELLOW+"nobody died"+ColorPrint.ANSI_RESET);
            }
        }
        //silenced
        for(int i=0; i<MainGame.size; i++){
            if(MainGame.Player[i].silenced){
                System.out.println(ColorPrint.ANSI_YELLOW+"silenced "+ MainGame.Player[i].name+ColorPrint.ANSI_RESET);
            }
        }
        System.out.println(ColorPrint.ANSI_YELLOW+MainGame.array[0]+" swapped characters with "+MainGame.array[1]+ColorPrint.ANSI_RESET);
        nightGameFinish();
    }
    //check finish of the game
    public static void nightGameFinish(){
        if(Villagers.numberOfVillagers<=Mafias.numberOfMafias){
            System.out.println(ColorPrint.ANSI_PURPLE+"Mafia won!"+ColorPrint.ANSI_RESET);
            MainGame.ans="Mafia won!";
        }
        else if(Mafias.numberOfMafias==0){
            System.out.println(ColorPrint.ANSI_PURPLE+"Villagers won!"+ColorPrint.ANSI_RESET);
            MainGame.ans="Villagers won!";
        }
        else{
            dayGuide();
        }
        nightReset();
    }
    //reset all player features
    public static void nightReset(){
        for(int i=0; i<MainGame.size; i++){
            MainGame.Player[i].NightVoted=0;
            MainGame.Player[i].called=false;
            MainGame.saved=new Players(" ", Roles.mafia);
            MainGame.ask=false;
            MainGame.freemasonKilled=null;
            MainGame.votedByMafia= new Players(" ", Roles.unknown);
            MainGame.votedByGodfather= new Players(" ", Roles.unknown);
            MainGame.votedBySilencer= new Players(" ", Roles.unknown);
        }
    }
    //if mafia kill someone that they want to kill at past nights but he didn't voted enough, we use this method(it's because of informer)
    public static void deleteKill(Players input){
        for(int i = 0; i< MainGame.sizeOfTry; i++){
            if(input==MainGame.tryToKill[i]){
                MainGame.tryToKill[i]=null;
                for(int j = i; j< MainGame.sizeOfTry; j++){
                    MainGame.tryToKill[j]= MainGame.tryToKill[j+1];
                }
                MainGame.sizeOfTry--;
                MainGame.tryToKill[MainGame.sizeOfTry]=null;
            }
        }
    }
}