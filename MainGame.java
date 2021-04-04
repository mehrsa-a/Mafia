import java.util.Scanner;
public class MainGame{
    //public vars
    //players of the game
    public static Players[] Player=new Players[100];
    public static int size=0;
    //players that we should start with them
    static String[] NameOfPlayers=new String[100];
    static int sizeOf=0;
    //roles array
    static Roles[] roles={ Roles.Joker, Roles.mafia, Roles.godfather, Roles.silencer, Roles.bomb, Roles.villager, Roles.detective, Roles.bulletproof, Roles.doctor, Roles.informer, Roles.freemason, Roles.unknown};
    //for inner while condition
    static String ans="";
    //the one who doctor save at night
    static Players saved=new Players(" ", Roles.mafia);
    //array for players who killed
    static Players[] dead=new Players[100];
    static  int sizeOfDead=0;
    //array for players that mafia want to kill but they don't voted enough(because of informer)
    static Players[] tryToKill=new Players[100];
    static int sizeOfTry=0;
    //check create game situation
    static boolean gameCreating=false;
    //check freemason live
    static Players freemasonKilled=null;
    //check detective asking situation
    static boolean ask=false;
    //the one who mafias vote at night(mafias can take their vote back from them)
    static Players votedByMafia= new Players(" ", Roles.unknown);
    static Players votedByGodfather= new Players(" ", Roles.unknown);
    static Players votedBySilencer= new Players(" ", Roles.unknown);
    //array for swap characters
    static String[] array;
    //number of day and night and check time of the game
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
                        NightHappenings.dayGuide();
                        do {
                            String act = scanner.next();
                            switch (act) {
                                case "start_game":
                                    System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"game has already started"+ColorPrint.ANSI_RESET);
                                    System.out.println("enter your action:");
                                    break;
                                case "end_vote":
                                    DayHappenings.endDay();
                                    break;
                                case "end_night":
                                    NightHappenings.endNight();
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
                                        DayHappenings.dayVoting(act);
                                    } else {
                                        NightHappenings.nightVoting(act);
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
    //create game part methods
    public static void createGuide(){
        System.out.println(ColorPrint.ANSI_RED+"Hey =)\nthis is a MAFIA version that helps god of game to handle the game\n"+ColorPrint.ANSI_RESET);
        System.out.println("to build a new game please type "+ColorPrint.ANSI_BLUE+"create_game "+ColorPrint.ANSI_RESET+"and type players name after that");
        line();
        System.out.println("enter your action:");
    }
    //name of players that we should start game with all of them
    public static String[] cut(String input){
        String[] commands;
        commands = input.split(" ");
        return commands;
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
    //this is line for roles features table
    public static void table(){
        for(int i=0; i<110; i++){
            System.out.print("-");
        }
        System.out.print("\n");
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
    //assign role part method
    public static void assignRole(){
        String name=scanner.next();
        String role=scanner.next();
        if(!gameCreating){
            System.out.println(ColorPrint.ANSI_WHITE_BACKGROUND+ColorPrint.ANSI_BLACK+"no game created"+ColorPrint.ANSI_RESET);
        }
        //recognize players
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
            //recognize roles
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
                //assign roles
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
    //start game part methods
    //this is line for players table
    public static void playerTable(){
        for(int i=0; i<33; i++){
            System.out.print("-");
        }
        System.out.print("\n");
    }
    //show players name and role
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
    //kill someone
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
}