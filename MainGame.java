import java.util.Scanner;
public class MainGame{
    //public vars
    public static Players[] Player=new Players[100];
    public static int size=0;
    static String[] NameOfPlayers=new String[100];
    static int sizeOf=0;
    static Roles[] roles={ Roles.Joker, Roles.mafia, Roles.godfather, Roles.silencer, Roles.bomb, Roles.villager, Roles.detective, Roles.bulletproof, Roles.doctor, Roles.informer, Roles.freemason, Roles.unknown};
    //for inner while condition
    static String ans="";
    //the one who doctor save at night
    static Players saved=new Players(" ", Roles.mafia);
    static Players[] dead=new Players[100];
    static  int sizeOfDead=0;
    static Players[] tryToKill=new Players[100];
    static int sizeOfTry=0;
    static boolean gameCreating=false;
    static Players freemasonKilled=null;
    static boolean ask=false;
    static Players votedByMafia= new Players(" ", Roles.unknown);
    static Players votedByGodfather= new Players(" ", Roles.unknown);
    static Players votedBySilencer= new Players(" ", Roles.unknown);
    static String[] array;
    static int day=1, night=1, counter=0;
    static Scanner scanner=new Scanner(System.in);
    public static void main(String[] args){
        createGuide();
        do{
            String action= scanner.next();
            switch (action) {
                case "create_game":
                    createGame();
                    break;
                case "assign_role":
                    assignRole();
                    break;
                case "start_game":
                    if (!gameCreating) {
                        System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"no game created"+ColorPrint.ANSI_RESET);
                    } else if (size != sizeOf) {
                        System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"one or more player do not have a role"+ColorPrint.ANSI_RESET);
                    } else {
                        showPlayers();
                        System.out.println(ColorPrint.ANSI_RED + "\n" + "Ready?\nSet!\n\nGO!!!" + ColorPrint.ANSI_RESET);
                        System.out.println(ColorPrint.ANSI_RED + "Day " + day + ColorPrint.ANSI_RESET);
                        dayGuide();
                        do {
                            String act = scanner.next();
                            switch (act) {
                                case "start_game":
                                    System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"game has already started"+ColorPrint.ANSI_RESET);
                                    System.out.println("enter your action:");
                                    break;
                                case "end_vote":
                                    endDay();
                                    break;
                                case "end_night":
                                    endNight();
                                    break;
                                case "get_game_state":
                                    System.out.println("Mafia = " + Mafias.numberOfMafias + "\nVillager = " + Villagers.numberOfVillagers);
                                    line();
                                    System.out.println("enter your action:");
                                    break;
                                case "list_of_alive_players":
                                    showPlayers();
                                    line();
                                    System.out.println("enter your action:");
                                    break;
                                case "swap_character":
                                    scanner.next();
                                    scanner.next();
                                    if (counter % 2 == 0) {
                                        System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"voting in progress"+ColorPrint.ANSI_RESET);
                                    } else {
                                        System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"can’t swap before end of night"+ColorPrint.ANSI_RESET);
                                    }
                                    System.out.println("enter your action:");
                                    break;
                                default:
                                    if (counter % 2 == 0) {
                                        dayVoting(act);
                                    } else {
                                        nightVoting(act);
                                    }
                                    System.out.println("enter your action:");
                                    break;
                            }
                        } while (!ans.equals("Joker won!") && !ans.equals("Mafia won!") && !ans.equals("Villagers won!"));
                    }
                    break;
            }
        }while (scanner.hasNext());
    }
    public static void line(){
        for(int i=0; i<149; i++){
            System.out.print("-");
        }
        System.out.println("-");
    }
    public static void createGuide(){
        System.out.println(ColorPrint.ANSI_RED+"Hey =)\nthis is a MAFIA version that helps god of game to handle the game\n"+ColorPrint.ANSI_RESET);
        System.out.println("to build a new game please type "+ColorPrint.ANSI_BLUE+"create_game "+ColorPrint.ANSI_RESET+"and type players name after that");
        line();
        System.out.println("enter your action:");
    }
    public static void table(){
        for(int i=0; i<110; i++){
            System.out.print("-");
        }
        System.out.print("\n");
    }
    public static void playerTable(){
        for(int i=0; i<33; i++){
            System.out.print("-");
        }
        System.out.print("\n");
    }
    public static String[] cut(String input){
        String[] commands;
        commands = input.split(" ");
        return commands;
    }
    public static void introduceRoles(){
        System.out.println("here is available roles:");
        table();
        System.out.println("|"+ColorPrint.ANSI_BLUE+" godfather   "+ColorPrint.ANSI_RESET+"| it's from mafias and if detective ask for his role, he stay unknown                          |");
        table();
        System.out.println("|"+ColorPrint.ANSI_BLUE+" silencer    "+ColorPrint.ANSI_RESET+"| it's from mafias and at night time, it can silence one player for next day                   |");
        table();
        System.out.println("|"+ColorPrint.ANSI_BLUE+" mafia       "+ColorPrint.ANSI_RESET+"| it's just a simple player from mafias                                                        |");
        table();
        System.out.println("|"+ColorPrint.ANSI_BLUE+" bomb        "+ColorPrint.ANSI_RESET+"| it's from mafias and if it kills at day time, it can kill one other player too               |");
        table();
        System.out.println("|"+ColorPrint.ANSI_BLUE+" detective   "+ColorPrint.ANSI_RESET+"| it's from villagers and at night time he can ask for one player's role                       |");
        table();
        System.out.println("|"+ColorPrint.ANSI_BLUE+" doctor      "+ColorPrint.ANSI_RESET+"| it's from villagers and at night time he can cure one player                                 |");
        table();
        System.out.println("|"+ColorPrint.ANSI_BLUE+" bulletproof "+ColorPrint.ANSI_RESET+"| it's from villagers and if mafia kill him at night, he doesn't die at first time             |");
        table();
        System.out.println("|"+ColorPrint.ANSI_BLUE+" informer    "+ColorPrint.ANSI_RESET+"| it's from villagers and if mafia kill it at night, it inform a random information            |");
        table();
        System.out.println("|"+ColorPrint.ANSI_BLUE+" freemason   "+ColorPrint.ANSI_RESET+"| it's from villagers and he wake someone up at night. if he was from mafias, freemason killed |");
        table();
        System.out.println("|"+ColorPrint.ANSI_BLUE+" villager    "+ColorPrint.ANSI_RESET+"| it's just a simple player from villagers                                                     |");
        table();
        System.out.println("|"+ColorPrint.ANSI_BLUE+" Joker       "+ColorPrint.ANSI_RESET+"| it's alone at game and if players kill it at day time, he won the game                       |");
        table();
        System.out.println(ColorPrint.ANSI_RED+"          !!!PAY ATTENTION!!!"+ColorPrint.ANSI_RESET+"\nyou can have"+ColorPrint.ANSI_RED+" JUST ONE Joker"+ColorPrint.ANSI_RESET+" in whole game");
    }
    public static void createGame(){
        String players= scanner.nextLine();
        NameOfPlayers=cut(players);
        for(int i=0; i<players.lastIndexOf(" "); i++){
            if(players.charAt(i)==' '){
                sizeOf++;
            }
        }
        sizeOf++;
        gameCreating=true;
        System.out.println("now type "+ColorPrint.ANSI_BLUE+"assign_role "+ColorPrint.ANSI_RESET+"and type mentioned player's name and assign a role to it");
        System.out.println("when assigning role to all players done, type "+ColorPrint.ANSI_BLUE+"start_game "+ColorPrint.ANSI_RESET+"to start the game");
        line();
        introduceRoles();
        System.out.println("enter your action:");
    }
    public static void assignRole(){
        String name=scanner.next();
        String role=scanner.next();
        if(!gameCreating){
            System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"no game created"+ColorPrint.ANSI_RESET);
        }
        else{
            String tempName=null;
            for(int i=0; i<=sizeOf; i++){
                if(NameOfPlayers[i].equals(name)){
                    tempName=name;
                    break;
                }
            }
            if(tempName==null){
                System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"user not found"+ColorPrint.ANSI_RESET);
            }
            else{
                Roles tempRole=null;
                for(int i=0; i<12; i++){
                    if(role.equals(roles[i].toString())){
                        tempRole=roles[i];
                        break;
                    }
                }
                if(tempRole==null){
                    System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"role not found"+ColorPrint.ANSI_RESET);
                }
                else{
                    Roles temp=Roles.unknown;
                    for(int j=0; j<12; j++){
                        if(role.equals(roles[j].toString())){
                            temp=roles[j];
                            break;
                        }
                    }
                    if(temp==Roles.silencer){
                        Player[size]=new Silencer(name, temp);
                        size++;
                    }
                    else if(temp==Roles.bomb){
                        Player[size]=new Bomb(name, temp);
                        size++;
                    }
                    else if(temp==Roles.mafia|| temp==Roles.godfather){
                        Player[size]=new Mafias(name, temp);
                        size++;
                    }
                    else if(temp==Roles.Joker){
                        Player[size]=Joker.getInstance();
                        Player[size].name=name;
                        size++;
                    }
                    else if(temp==Roles.detective){
                        Player[size]=new Detective(name, temp);
                        size++;
                    }
                    else if(temp==Roles.doctor){
                        Player[size]=new Doctor(name, temp);
                        size++;
                    }
                    else if(temp==Roles.freemason){
                        Player[size]=Freemason.getInstance();
                        Player[size].name=name;
                        size++;
                    }
                    else if(temp==Roles.informer){
                        Player[size]=new Informer(name, temp);
                        size++;
                    }
                    else{
                        Player[size]=new Villagers(name, temp);
                        size++;
                    }
                }
            }
        }
        System.out.println("enter your action:");
    }
    public static void showPlayers(){
        playerTable();
        for(int i=0; i<size; i++){
            int backTemp, frontTemp;
            int nameLength=Player[i].name.length();
            int roleLength=Player[i].role.toString().length();
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
            System.out.print(ColorPrint.ANSI_BLUE+Player[i].name+ColorPrint.ANSI_RESET);
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
            System.out.print(ColorPrint.ANSI_BLUE+Player[i].role.toString()+ColorPrint.ANSI_RESET);
            for(int j=0; j<frontTemp; j++){
                System.out.print(" ");
            }
            System.out.println(" |");
            playerTable();
        }
    }
    public static void dayGuide(){
        line();
        System.out.println("at start of the day players should vote to kill someone");
        System.out.println("for this action enter name of voter player and after that the voted one with one space");
        System.out.println("after voting type "+ColorPrint.ANSI_BLUE+"end_vote "+ColorPrint.ANSI_RESET+"and night will start");
        System.out.println("you can get number of alive players with "+ColorPrint.ANSI_BLUE+"get_game_state"+ColorPrint.ANSI_RESET);
        System.out.println("you can get name of alive players with "+ColorPrint.ANSI_BLUE+"list_of_alive_players"+ColorPrint.ANSI_RESET);
        line();
        System.out.println("enter your action:");
    }
    public static void dayVoting(String act){
        String voted=scanner.next();
        String tempVoter=null;
        String tempVoted=null;
        for(int i=0; i<sizeOfDead; i++){
            if(dead[i].name.equals(act)){
                tempVoter=dead[i].name;
            }
            if(dead[i].name.equals(voted)){
                tempVoted=dead[i].name;
            }
        }
        if(tempVoter!=null){
            System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"voter already dead"+ColorPrint.ANSI_RESET);
        }
        else if(tempVoted!=null){
            System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"voted already dead"+ColorPrint.ANSI_RESET);
        }
        else{
            for(int i=0; i<size; i++){
                if(Player[i].name.equals(act)){
                    tempVoter=Player[i].name;
                }
                if(Player[i].name.equals(voted)){
                    tempVoted=Player[i].name;
                }
            }
            if(tempVoter==null){
                System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"user not found"+ColorPrint.ANSI_RESET);
            }
            else if(tempVoted==null){
                System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"user not found"+ColorPrint.ANSI_RESET);
            }
            else{
                for(int i=0; i<size; i++){
                    if(Player[i].name.equals(act)){
                        if(Player[i].silenced){
                            System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"voter is silenced"+ColorPrint.ANSI_RESET);
                        }
                        else{
                            for(int j=0; j<size; j++){
                                if(Player[j].name.equals(voted)){
                                    Player[j].DayVoted++;
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
    public static void kill(Players input){
        for(int i=0; i<size; i++){
            if(input==Player[i]){
                if(Player[i].role==Roles.mafia||Player[i].role==Roles.godfather||Player[i].role==Roles.silencer){
                    Mafias.numberOfMafias--;
                }
                else if(Player[i].role!=Roles.Joker){
                    Villagers.numberOfVillagers--;
                }
                dead[sizeOfDead]=Player[i];
                sizeOfDead++;
                Player[i]=null;
                for(int j=i; j<size; j++){
                    Player[j]=Player[j+1];
                }
                size--;
                Player[size]=null;
            }
        }
    }
    public static void endDay(){
        counter++;
        day++;
        System.out.println(ColorPrint.ANSI_RED+"Night "+night+ColorPrint.ANSI_RESET);
        int max=Player[0].DayVoted;
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                if(Player[j].DayVoted>Player[i].DayVoted){
                    max=Player[j].DayVoted;
                }
            }
        }
        int c=0;
        for(int i=0; i<size; i++){
            if(Player[i].DayVoted==max){
                c++;
            }
        }
        if(c>1){
            System.out.println(ColorPrint.ANSI_YELLOW+"nobody died"+ColorPrint.ANSI_RESET);
            nightGuide();
        }
        else{
            for(int i=0; i<size; i++){
                if(max==Player[i].DayVoted){
                    if(Player[i].role==Roles.Joker){
                        System.out.println(ColorPrint.ANSI_PURPLE+"Joker won!"+ColorPrint.ANSI_RESET);
                        ans="Joker won!";
                    }
                    else{
                        System.out.println(ColorPrint.ANSI_YELLOW+Player[i].name+" died"+ColorPrint.ANSI_RESET);
                        System.out.println(ColorPrint.ANSI_YELLOW+Player[i].name+" was "+Player[i].role+ColorPrint.ANSI_RESET);
                        if(Player[i].role==Roles.bomb){
                            System.out.println("enter the player bomb wants to kill:");
                            String kill=scanner.next();
                            for(int j=0; j<size; j++){
                                if(Player[j].name.equals(kill)){
                                    System.out.println(ColorPrint.ANSI_YELLOW+"bomb killed "+Player[j].name+ColorPrint.ANSI_RESET);
                                    System.out.println(ColorPrint.ANSI_YELLOW+Player[j].name+" died"+ColorPrint.ANSI_RESET);
                                    ((Bomb)Player[i]).action(kill);
                                    break;
                                }
                            }
                        }
                        kill(Player[i]);
                        if(Villagers.numberOfVillagers<=Mafias.numberOfMafias){
                            System.out.println(ColorPrint.ANSI_PURPLE+"Mafia won!"+ColorPrint.ANSI_RESET);
                            ans="Mafia won!";
                        }
                        else if(Mafias.numberOfMafias==0){
                            System.out.println(ColorPrint.ANSI_PURPLE+"Villagers won!"+ColorPrint.ANSI_RESET);
                            ans="Villagers won!";
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
    public static void dayReset(){
        for(int i=0; i<size; i++){
            Player[i].DayVoted=0;
            Player[i].silenced=false;
        }
    }
    public static void nightWake(){
        playerTable();
        for(int i=0; i<size; i++){
            if(Player[i].role==Roles.mafia||Player[i].role==Roles.godfather||Player[i].role==Roles.silencer||Player[i].role==Roles.bomb||Player[i].role==Roles.detective||Player[i].role==Roles.doctor||Player[i].role==Roles.freemason){
                int backTemp, frontTemp;
                int nameLength=Player[i].name.length();
                int roleLength=Player[i].role.toString().length();
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
                System.out.print(ColorPrint.ANSI_BLUE+Player[i].name+ColorPrint.ANSI_RESET);
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
                System.out.print(ColorPrint.ANSI_BLUE+Player[i].role.toString()+ColorPrint.ANSI_RESET);
                for(int j=0; j<frontTemp; j++){
                    System.out.print(" ");
                }
                System.out.println(" |");
                playerTable();
            }
        }
    }
    public static void nightGuide(){
        line();
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
    public static void nightVoting(String act){
        String voted=scanner.next();
        String tempVoter=null;
        String tempVoted=null;
        for(int i=0; i<sizeOfDead; i++){
            if(dead[i].name.equals(act)){
                tempVoter=dead[i].name;
            }
            if(dead[i].name.equals(voted)){
                tempVoted=dead[i].name;
            }
        }
        if(tempVoter!=null){
            System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"user is dead"+ColorPrint.ANSI_RESET);
        }
        else if(tempVoted!=null){
            System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"voted already dead"+ColorPrint.ANSI_RESET);
        }
        else{
            for(int i=0; i<size; i++){
                if(Player[i].name.equals(act)){
                    tempVoter=Player[i].name;
                }
                if(Player[i].name.equals(voted)){
                    tempVoted=Player[i].name;
                }
            }
            if(tempVoter==null){
                System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"user not joined"+ColorPrint.ANSI_RESET);
            }
            else if(tempVoted==null){
                System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"user not joined"+ColorPrint.ANSI_RESET);
            }
            else{
                boolean keepGoing=false;
                for(int i=0; i<size; i++){
                    if(Player[i].name.equals(act)){
                        if(Player[i].role!=Roles.mafia&&Player[i].role!=Roles.godfather&&Player[i].role!=Roles.silencer&&Player[i].role!=Roles.bomb&&Player[i].role!=Roles.detective&&Player[i].role!=Roles.doctor&&Player[i].role!=Roles.freemason){
                            System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"user can not wake up during night"+ColorPrint.ANSI_RESET);
                            keepGoing=true;
                        }
                    }
                }
                if(!keepGoing){
                    for(int i=0; i<size; i++){
                        if(Player[i].name.equals(act)){
                            //mafia wake up
                            if(Player[i].role==Roles.godfather||Player[i].role==Roles.mafia||Player[i].role==Roles.bomb){
                                if(!Player[i].called){
                                    for(int j=0; j<size; j++){
                                        if(Player[j].name.equals(voted)){
                                            Player[j].NightVoted++;
                                            if(Player[i].role==Roles.mafia){
                                                votedByMafia=Player[j];
                                            }
                                            else{
                                                votedByGodfather=Player[j];
                                            }
                                            tryToKill[sizeOfTry]=Player[i];
                                            sizeOfTry++;
                                        }
                                    }
                                    Player[i].called=true;
                                }
                                else{
                                    if(Player[i].role==Roles.mafia){
                                        for(int j=0; j<size; j++){
                                            if(Player[j]==votedByMafia){
                                                Player[j].NightVoted--;
                                            }
                                        }
                                        for(int j=0; j<size; j++){
                                            if(Player[j].name.equals(voted)){
                                                Player[j].NightVoted++;
                                                for(int k=0; k<sizeOfTry; k++){
                                                    if(tryToKill[k]==votedByMafia){
                                                        tryToKill[k]=Player[j];
                                                    }
                                                }
                                                votedByMafia=Player[j];
                                            }
                                        }
                                    }
                                    else{
                                        for(int j=0; j<size; j++){
                                            if(Player[j]==votedByGodfather){
                                                Player[j].NightVoted--;
                                            }
                                        }
                                        for(int j=0; j<size; j++){
                                            if(Player[j].name.equals(voted)){
                                                Player[j].NightVoted++;
                                                for(int k=0; k<sizeOfTry; k++){
                                                    if(tryToKill[k]==votedByGodfather){
                                                        tryToKill[k]=Player[j];
                                                    }
                                                }
                                                votedByGodfather=Player[j];
                                            }
                                        }
                                    }
                                }
                            }
                            //silencer wake up
                            if(Player[i].role==Roles.silencer){
                                if(!Player[i].called){
                                    for(int j=0; j<size; j++){
                                        if(Player[j].name.equals(voted)){
                                            Player[j].silenced=true;
                                        }
                                    }
                                    Player[i].called=true;
                                }
                                else{
                                    if(votedBySilencer.role==Roles.unknown){
                                        for(int j=0; j<size; j++){
                                            if(Player[j].name.equals(voted)){
                                                Player[j].NightVoted++;
                                                votedBySilencer=Player[j];
                                                tryToKill[sizeOfTry]=Player[i];
                                                sizeOfTry++;
                                            }
                                        }
                                    }
                                    else{
                                        for(int j=0; j<size; j++){
                                            if(Player[j]==votedBySilencer){
                                                Player[j].NightVoted--;
                                            }
                                        }
                                        for(int j=0; j<size; j++){
                                            if(Player[j].name.equals(voted)){
                                                Player[j].NightVoted++;
                                                for(int k=0; k<sizeOfTry; k++){
                                                    if(tryToKill[k]==votedBySilencer){
                                                        tryToKill[k]=Player[j];
                                                    }
                                                }
                                                votedBySilencer=Player[j];
                                            }
                                        }
                                    }
                                }
                            }
                            //doctor wake up
                            if(Player[i].role==Roles.doctor){
                                Doctor temp=(Doctor)Player[i];
                                temp.action(voted);
                                saved=temp.choose(voted);
                            }
                            //detective wake up
                            if(Player[i].role==Roles.detective){
                                if(!ask){
                                    ask=true;
                                    ((Detective)Player[i]).action(voted);
                                }
                                else{
                                    System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"detective has already asked"+ColorPrint.ANSI_RESET);
                                }
                            }
                            //freemason wakeup
                            if(Player[i].role==Roles.freemason){
                                for(int j=0; j<size; j++){
                                    if(Player[j].name.equals(voted)){
                                        if(Player[j].role==Roles.godfather||Player[j].role==Roles.mafia||Player[j].role==Roles.bomb||Player[j].role==Roles.silencer){
                                            freemasonKilled=Player[i];
                                            kill(Player[i]);
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
    public static void bulletproofDead(Players max){
        if(max.dead){
            kill(max);
            System.out.println(ColorPrint.ANSI_YELLOW+"mafia tried to kill "+max.name+ColorPrint.ANSI_RESET);
            System.out.println(ColorPrint.ANSI_YELLOW+max.name+" was killed"+ColorPrint.ANSI_RESET);
        }
        else{
            for(int j=0; j<size; j++){
                if(Player[j]==max){
                    Player[j].dead=true;
                    break;
                }
            }
            System.out.println(ColorPrint.ANSI_YELLOW+"mafia tried to kill "+max.name+ColorPrint.ANSI_RESET);
            System.out.println(ColorPrint.ANSI_YELLOW+"nobody died"+ColorPrint.ANSI_RESET);
        }
    }
    public static void swap(){
        while(array==null){
            System.out.println("swap two character with "+ColorPrint.ANSI_BLUE+"swap_character"+ColorPrint.ANSI_RESET);
            scanner.next();
            String p1=scanner.next();
            String p2=scanner.next();
            if(freemasonKilled!=null){
                if(p1.equals(freemasonKilled.name) || p2.equals(freemasonKilled.name)){
                    System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"user was killed last night"+ColorPrint.ANSI_RESET);
                }
            }
            else if(p1.equals(findKilled().name)||p2.equals(findKilled().name)){
                System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"user was killed last night"+ColorPrint.ANSI_RESET);
            }
            else{
                String tempP1=null;
                String tempP2=null;
                for(int i=0; i<sizeOfDead; i++){
                    if(dead[i].name.equals(p1)){
                        tempP1=dead[i].name;
                    }
                    if(dead[i].name.equals(p2)){
                        tempP2=dead[i].name;
                    }
                }
                if(tempP1!=null||tempP2!=null){
                    System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"user is dead"+ColorPrint.ANSI_RESET);
                }
                else{
                    Outer: for(int i=0; i<size; i++){
                        if(Player[i].name.equals(p1)){
                            String temp=Player[i].name;
                            for(int j=0; j<size; j++){
                                if(Player[j].name.equals(p2)){
                                    Player[i].name=Player[j].name;
                                    Player[j].name=temp;
                                    break Outer;
                                }
                            }
                        }
                    }
                    array= new String[]{p1, p2};
                }
            }
        }
    }
    public static Players findKilled(){
        Players max=Player[0];
        for(int i=0; i<size; i++){
            if(Player[i].NightVoted>=max.NightVoted){
                max=Player[i];
            }
        }
        //find number of players that have max night voted
        int numberOfMax=0;
        for(int i=0; i<size; i++){
            if(Player[i].NightVoted==max.NightVoted){
                numberOfMax++;
            }
        }
        //killed
        if(numberOfMax==1){
            if(max.role==Roles.bulletproof){
                bulletproofDead(max);
            }
            else if(saved!=max){
                return max;
            }
            else{
                return new Players(" ", Roles.unknown);
            }
        }
        else if(numberOfMax==2) {
            Players[] maxes = new Players[2];
            int n = 0;
            for (int i = 0; i < size; i++) {
                if (Player[i].NightVoted == max.NightVoted) {
                    maxes[n] = Player[i];
                    n++;
                }
            }
            if (maxes[0] == saved) {
                if (maxes[1].role != Roles.bulletproof) {
                    return maxes[1];
                }
            } else if (maxes[1] == saved) {
                if (maxes[0].role != Roles.bulletproof) {
                    return maxes[0];
                }
            }
        }
        else if(freemasonKilled!=null){
            return freemasonKilled;
        }
        return new Players(" ", Roles.unknown);
    }
    public static void endNight(){
        swap();
        counter++;
        night++;
        System.out.println(ColorPrint.ANSI_RED+"Day "+day+ColorPrint.ANSI_RESET);
        if(freemasonKilled!=null){
            System.out.println(ColorPrint.ANSI_YELLOW+freemasonKilled.name+" as freemason was wake a mafia up"+ColorPrint.ANSI_RESET);
            System.out.println(ColorPrint.ANSI_YELLOW+freemasonKilled.name+" was killed"+ColorPrint.ANSI_RESET);
            deleteKill(freemasonKilled);
        }
        //find max night voted player
        Players max=Player[0];
        for(int i=0; i<size; i++){
            if(Player[i].NightVoted>=max.NightVoted){
                max=Player[i];
            }
        }
        //find number of players that have max night voted
        int numberOfMax=0;
        for(int i=0; i<size; i++){
            if(Player[i].NightVoted==max.NightVoted){
                numberOfMax++;
            }
        }
        //killed
        if(numberOfMax==1){
            if(max.role==Roles.bulletproof){
                bulletproofDead(max);
            }
            else if(saved!=max){
                kill(max);
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
                if(freemasonKilled==null){
                    System.out.println(ColorPrint.ANSI_YELLOW+"nobody died"+ColorPrint.ANSI_RESET);
                }
            }
        }
        else if(numberOfMax==2){
            Players[] maxes=new Players[2];
            int n=0;
            for(int i=0; i<size; i++){
                if(Player[i].NightVoted==max.NightVoted){
                    maxes[n]=Player[i];
                    n++;
                }
            }
            if(maxes[0]==saved){
                if(maxes[1].role==Roles.bulletproof){
                    bulletproofDead(maxes[1]);
                }
                else{
                    kill(maxes[1]);
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
            else if(maxes[1]==saved){
                if(maxes[0].role==Roles.bulletproof){
                    bulletproofDead(maxes[0]);
                }
                else{
                    kill(maxes[0]);
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
                if(freemasonKilled==null){
                    System.out.println(ColorPrint.ANSI_YELLOW+"nobody died"+ColorPrint.ANSI_RESET);
                }
            }
        }
        else{
            if(freemasonKilled==null){
                System.out.println(ColorPrint.ANSI_YELLOW+"nobody died"+ColorPrint.ANSI_RESET);
            }
        }
        //silenced
        for(int i=0; i<size; i++){
            if(Player[i].silenced){
                System.out.println(ColorPrint.ANSI_YELLOW+"silenced "+Player[i].name+ColorPrint.ANSI_RESET);
            }
        }
        System.out.println(ColorPrint.ANSI_YELLOW+array[0]+" swapped characters with "+array[1]+ColorPrint.ANSI_RESET);
        nightGameFinish();
    }
    public static void nightGameFinish(){
        if(Villagers.numberOfVillagers<=Mafias.numberOfMafias){
            System.out.println(ColorPrint.ANSI_PURPLE+"Mafia won!"+ColorPrint.ANSI_RESET);
            ans="Mafia won!";
        }
        else if(Mafias.numberOfMafias==0){
            System.out.println(ColorPrint.ANSI_PURPLE+"Villagers won!"+ColorPrint.ANSI_RESET);
            ans="Villagers won!";
        }
        else{
            dayGuide();
        }
        nightReset();
    }
    public static void nightReset(){
        for(int i=0; i<size; i++){
            Player[i].NightVoted=0;
            Player[i].called=false;
            saved=new Players(" ", Roles.mafia);
            ask=false;
            freemasonKilled=null;
            votedByMafia= new Players(" ", Roles.unknown);
            votedByGodfather= new Players(" ", Roles.unknown);
            votedBySilencer= new Players(" ", Roles.unknown);
        }
    }
    public static void deleteKill(Players input){
        for(int i=0; i<sizeOfTry; i++){
            if(input==tryToKill[i]){
                tryToKill[i]=null;
                for(int j=i ; j<sizeOfTry; j++){
                    tryToKill[j]=tryToKill[j+1];
                }
                sizeOfTry--;
                tryToKill[sizeOfTry]=null;
            }
        }
    }
}