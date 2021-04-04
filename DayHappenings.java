public class DayHappenings {
    //day time methods
    public static void dayVoting(String act){
        String voted=MainGame.scanner.next();
        //recognize alive players
        String tempVoter=null;
        String tempVoted=null;
        for(int i=0; i<MainGame.sizeOfDead; i++){
            if(MainGame.dead[i].name.equals(act)){
                tempVoter=MainGame.dead[i].name;
            }
            if(MainGame.dead[i].name.equals(voted)){
                tempVoted= MainGame.dead[i].name;
            }
        }
        if(tempVoter!=null){
            System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"voter already dead"+ColorPrint.ANSI_RESET);
        }
        else if(tempVoted!=null){
            System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"voted already dead"+ColorPrint.ANSI_RESET);
        }
        else{
            //recognize players
            for(int i=0;i<MainGame.size; i++){
                if(MainGame.Player[i].name.equals(act)){
                    tempVoter= MainGame.Player[i].name;
                }
                if(MainGame.Player[i].name.equals(voted)){
                    tempVoted= MainGame.Player[i].name;
                }
            }
            if(tempVoter==null){
                System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"user not found"+ColorPrint.ANSI_RESET);
            }
            else if(tempVoted==null){
                System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"user not found"+ColorPrint.ANSI_RESET);
            }
            else{
                //silence player can't vote
                for(int i=0; i<MainGame.size; i++){
                    if(MainGame.Player[i].name.equals(act)){
                        if(MainGame.Player[i].silenced){
                            System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"voter is silenced"+ColorPrint.ANSI_RESET);
                        }
                        else{
                            //voting
                            for(int j=0; j<MainGame.size; j++){
                                if(MainGame.Player[j].name.equals(voted)){
                                    MainGame.Player[j].DayVoted++;
                                    break;
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }
    }
    //end day methods
    public static void endDay(){
        MainGame.counter++;
        MainGame.day++;
        System.out.println(ColorPrint.ANSI_RED+"Night "+ MainGame.night+ColorPrint.ANSI_RESET);
        //find player who has max vote
        int max= MainGame.Player[0].DayVoted;
        for(int i=0; i<MainGame.size; i++){
            for(int j=0; j<MainGame.size; j++){
                if(MainGame.Player[j].DayVoted> MainGame.Player[i].DayVoted){
                    max= MainGame.Player[j].DayVoted;
                }
            }
        }
        int c=0;
        for(int i=0; i<MainGame.size; i++){
            if(MainGame.Player[i].DayVoted==max){
                c++;
            }
        }
        //maybe there is more than one players with max vote
        if(c>1){
            System.out.println(ColorPrint.ANSI_YELLOW+"nobody died"+ColorPrint.ANSI_RESET);
            nightGuide();
        }
        else{
            for(int i=0; i<MainGame.size; i++){
                if(max== MainGame.Player[i].DayVoted){
                    //joker death can expire the game
                    if(MainGame.Player[i].role==Roles.Joker){
                        System.out.println(ColorPrint.ANSI_PURPLE+"Joker won!"+ColorPrint.ANSI_RESET);
                        MainGame.ans="Joker won!";
                    }
                    else{
                        System.out.println(ColorPrint.ANSI_YELLOW+ MainGame.Player[i].name+" died"+ColorPrint.ANSI_RESET);
                        System.out.println(ColorPrint.ANSI_YELLOW+ MainGame.Player[i].name+" was "+ MainGame.Player[i].role+ColorPrint.ANSI_RESET);
                        //bomb can kill someone with himself
                        if(MainGame.Player[i].role==Roles.bomb){
                            System.out.println("enter the player bomb wants to kill:");
                            String kill=MainGame.scanner.next();
                            for(int j=0; j<MainGame.size; j++){
                                if(MainGame.Player[j].name.equals(kill)){
                                    System.out.println(ColorPrint.ANSI_YELLOW+"bomb killed "+ MainGame.Player[j].name+ColorPrint.ANSI_RESET);
                                    System.out.println(ColorPrint.ANSI_YELLOW+ MainGame.Player[j].name+" died"+ColorPrint.ANSI_RESET);
                                    ((Bomb) MainGame.Player[i]).action(kill);
                                    break;
                                }
                            }
                        }
                        MainGame.kill(MainGame.Player[i]);
                        //end game check
                        if(Villagers.numberOfVillagers<=Mafias.numberOfMafias){
                            System.out.println(ColorPrint.ANSI_PURPLE+"Mafia won!"+ColorPrint.ANSI_RESET);
                            MainGame.ans="Mafia won!";
                        }
                        else if(Mafias.numberOfMafias==0){
                            System.out.println(ColorPrint.ANSI_PURPLE+"Villagers won!"+ColorPrint.ANSI_RESET);
                            MainGame.ans="Villagers won!";
                        }
                        else{
                            nightGuide();
                        }
                    }
                }
            }
        }
        dayReset();
    }
    //reset all player features
    public static void dayReset(){
        for(int i=0; i<MainGame.size; i++){
            MainGame.Player[i].DayVoted=0;
            MainGame.Player[i].silenced=false;
        }
    }
    //name of players that should wake up at night
    public static void nightWake(){
        MainGame.playerTable();
        for(int i=0; i<MainGame.size; i++){
            if(MainGame.Player[i].role==Roles.mafia||MainGame.Player[i].role==Roles.godfather|| MainGame.Player[i].role==Roles.silencer||MainGame.Player[i].role==Roles.bomb||MainGame.Player[i].role==Roles.detective|| MainGame.Player[i].role==Roles.doctor|| MainGame.Player[i].role==Roles.freemason){
                int backTemp, frontTemp;
                int nameLength=MainGame.Player[i].name.length();
                int roleLength= MainGame.Player[i].role.toString().length();
                System.out.print("| ");
                if(nameLength%2==0){
                    backTemp=(15-nameLength)/2;
                    frontTemp=(15-nameLength)/2+1;
                }
                else{
                    backTemp=(15-nameLength)/2;
                    frontTemp=(15-nameLength)/2;
                }
                for(int j=0; j<backTemp; j++){
                    System.out.print(" ");
                }
                System.out.print(ColorPrint.ANSI_BLUE+MainGame.Player[i].name+ColorPrint.ANSI_RESET);
                for(int j=0; j<frontTemp; j++){
                    System.out.print(" ");
                }
                System.out.print(" | ");
                if(roleLength%2==0){
                    backTemp=(11-roleLength)/2;
                    frontTemp=(11-roleLength)/2+1;
                }
                else{
                    backTemp=(11-roleLength)/2;
                    frontTemp=(11-roleLength)/2;
                }
                for(int j=0; j<backTemp; j++){
                    System.out.print(" ");
                }
                System.out.print(ColorPrint.ANSI_BLUE+ MainGame.Player[i].role.toString()+ColorPrint.ANSI_RESET);
                for(int j=0; j<frontTemp; j++){
                    System.out.print(" ");
                }
                System.out.println(" |");
                MainGame.playerTable();
            }
        }
    }
    public static void nightGuide(){
        MainGame.line();
        System.out.println("at start of the night mafias should vote to kill someone");
        System.out.println("silencer should silence someone");
        System.out.println("doctor should save someone");
        System.out.println("detective should ask for someone role");
        System.out.println("=> for this actions enter name of voter player and after that the voted one with one space");
        System.out.println("after voting type "+ColorPrint.ANSI_BLUE+"end_night "+ColorPrint.ANSI_RESET+"and night will start");
        System.out.println("you can get number of alive players with "+ColorPrint.ANSI_BLUE+"get_game_state"+ColorPrint.ANSI_RESET);
        System.out.println("you can get name of alive players with "+ColorPrint.ANSI_BLUE+"list_of_alive_players"+ColorPrint.ANSI_RESET);
        System.out.println("here is the list of players that should wake at night:");
        //list of players that should wake up at night
        nightWake();
        System.out.println("enter your action:");
    }
}