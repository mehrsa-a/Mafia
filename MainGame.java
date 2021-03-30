import java.util.Scanner;

class MainGame{

    //public vars
    public static Players[] Player=new Players[100];
    public static int size=0;

    //vars
    static String[] NameOfPlayers=new String[100];
    static int sizeOf=0;
    static Roles[] roles={ Roles.Joker, Roles.mafia, Roles.godfather, Roles.silencer, Roles.villager, Roles.detective, Roles.bulletproof, Roles.doctor, Roles.unknown};
    //for inner while condition
    static String ans="";
    //the one who doctor save at night
    static Players saved=new Players(" ", Roles.mafia);

    static Scanner scanner=new Scanner(System.in);

    public static void main(String[] args){


        System.out.println(ColorPrint.ANSI_RED+"Hey =)\nthis is a MAFIA version that helps god of game to handle the game\n"+ColorPrint.ANSI_RESET);
        System.out.println("to build a new game please type "+ColorPrint.ANSI_BLUE+"create_game "+ColorPrint.ANSI_RESET+"and type players name after that");
        line();
        System.out.println("enter your action:");
        do{


            String action= scanner.next();

            if(action.equals("create_game")){

                createGame();
                System.out.println("now type "+ColorPrint.ANSI_BLUE+"assign_role "+ColorPrint.ANSI_RESET+"and type mentioned player's name and assign a role to it");
                System.out.println("when assigning role to all players done, type "+ColorPrint.ANSI_BLUE+"start_game "+ColorPrint.ANSI_RESET+"to start the game");
                line();
                introduceRoles();
                System.out.println("enter your action:");
            }

            else if(action.equals("assign_role")){

                assignRole();
                System.out.println("enter your action:");
            }

            else if(action.equals("start_game")){

                //recognize game
                /*for(int i=0; i<sizeOf; i++) {
                    if (NameOfPlayers[i] == null) {
                        System.out.println("no game created");
                        continue;
                    }
                }

                //recognize roles
                for(int i=0; i<size; i++){
                    if(Player[i].role==null){
                        System.out.println("one or more player do not have a role");
                        continue;
                    }
                }*/

                //System.out.println(myToString(Player));
                showPlayers();
                System.out.println(ColorPrint.ANSI_RED+"\n"+"Ready?\nSet!\n\nGO!!!"+ColorPrint.ANSI_RESET);

                int day=1, night=1, counter=0;

                System.out.println(ColorPrint.ANSI_RED+"Day "+day+ColorPrint.ANSI_RESET);
                dayGuide();
                do{

                    String act=scanner.next();

                    if(act.equals("start_game")){
                        System.out.println("game has already started");
                        System.out.println("enter your action:");
                    }

                    else if(act.equals("end_vote")){

                        counter++;
                        day++;

                        System.out.println(ColorPrint.ANSI_RED+"Night "+night+ColorPrint.ANSI_RESET);
                        endDay();

                        //reset number of player's vote
                        for(int i=0; i<size; i++){
                            Player[i].DayVoted=0;
                        }

                    }

                    else if(act.equals("end_night")){

                        counter++;
                        night++;

                        System.out.println(ColorPrint.ANSI_RED+"Day "+day+ColorPrint.ANSI_RESET);

                        endNight();

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

                        //reset
                        for(int i=0; i<size; i++){
                            Player[i].NightVoted=0;
                            Player[i].silenced=false;
                            Player[i].called=false;
                        }
                    }

                    else if(act.equals("get_game_state")){
                        System.out.println("Mafia = "+Mafias.numberOfMafias+"\nVillager = "+Villagers.numberOfVillagers);
                        line();
                        System.out.println("enter your action:");
                    }

                    else{
                        if(counter%2==0){
                            dayVoting();
                            System.out.println("enter your action:");
                        }

                        else{
                            nightVoting(act);
                            System.out.println("enter your action:");
                        }
                    }


                }while(!ans.equals("Joker won!") && !ans.equals("Mafia won!") && !ans.equals("Villagers won!"));


            }


        }while (scanner.hasNext());

    }

    public static void line(){
        for(int i=0; i<149; i++){
            System.out.print("-");
        }
        System.out.println("-");
    }

    public static void table(){
        for(int i=0; i<98; i++){
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
        System.out.println("|"+ColorPrint.ANSI_BLUE+" godfather   "+ColorPrint.ANSI_RESET+"| it's from mafias and if detective ask for his role, he stay unknown              |");
        table();
        System.out.println("|"+ColorPrint.ANSI_BLUE+" silencer    "+ColorPrint.ANSI_RESET+"| it's from mafias and at night time, it can silence one player for next day       |");
        table();
        System.out.println("|"+ColorPrint.ANSI_BLUE+" mafia       "+ColorPrint.ANSI_RESET+"| it's just a simple player from mafias                                            |");
        table();
        System.out.println("|"+ColorPrint.ANSI_BLUE+" detective   "+ColorPrint.ANSI_RESET+"| it's from villagers and at night time he can ask for one player's role           |");
        table();
        System.out.println("|"+ColorPrint.ANSI_BLUE+" doctor      "+ColorPrint.ANSI_RESET+"| it's from villagers and at night time he can cure one player                     |");
        table();
        System.out.println("|"+ColorPrint.ANSI_BLUE+" bulletproof "+ColorPrint.ANSI_RESET+"| it's from villagers and if mafia kill him at night, he doesn't die at first time |");
        table();
        System.out.println("|"+ColorPrint.ANSI_BLUE+" villager    "+ColorPrint.ANSI_RESET+"| it's just a simple player from villagers                                         |");
        table();
        System.out.println("|"+ColorPrint.ANSI_BLUE+" Joker       "+ColorPrint.ANSI_RESET+"| it's alone at game and if players kill it at day time, he won the game           |");
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
    }

    public static void assignRole(){

        String name=scanner.next();
        String role=scanner.next();

                        /*int x=0;
                for(int i=0; i<sizeOf; i++){
                    //recognize game
                    if(NameOfPlayers[i]==null){
                        System.out.println("no game created");
                        continue;
                    }
                    //recognize players
                    if(name!=NameOfPlayers[i]){
                        x++;
                    }
                }
                if(x==sizeOf){
                    System.out.println("user not found");
                    name=scanner.next();
                    role=scanner.next();
                }

                //recognize roles
                x=0;
                for(int i=0; i<9; i++){
                    if(!role.equals(roles[i].toString())){
                        x++;
                    }
                }
                if(x==9){
                    System.out.println("role not found");
                    name=scanner.next();
                    role=scanner.next();
                }*/

        Roles temp=Roles.unknown;
        for(int j=0; j<9; j++){
            if(role.equals(roles[j].toString())){
                temp=roles[j];
                break;
            }
        }
        if(temp==Roles.silencer){
            Player[size]=new Silencer(name, temp);
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
        else{
            Player[size]=new Villagers(name, temp);
            size++;
        }

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
        line();
        System.out.println("enter your action:");
    }

    public static void dayVoting(){
        String voted=scanner.next();
        for(int i=0; i<size; i++){
            if(Player[i].name.equals(voted)){
                Player[i].DayVoted++;
                break;
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
    }

    public static void nightWake(){
        playerTable();
        for(int i=0; i<size; i++){
            if(Player[i].role==Roles.mafia||Player[i].role==Roles.godfather||Player[i].role==Roles.silencer||Player[i].role==Roles.detective||Player[i].role==Roles.doctor){
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
        System.out.println("here is the list of players that should wake at night:");
        //list of players that should wake up at night
        nightWake();
        System.out.println("enter your action:");
    }

    public static void nightVoting(String act){
        String voted=scanner.next();

        for(int i=0; i<size; i++){

            if(Player[i].name.equals(act)){

                Players votedByMafia= new Players(" ", Roles.unknown);
                Players votedByGodfather= new Players(" ", Roles.unknown);
                Players votedBySilencer= new Players(" ", Roles.unknown);
                //mafia wake up
                if(Player[i].role==Roles.godfather||Player[i].role==Roles.mafia){
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
                                    votedBySilencer=Player[j];
                                }
                            }
                        }
                    }
                }

                //doctor wake up
                if(Player[i].role==Roles.doctor){
                    for(int j=0; j<size; j++){
                        if(Player[j].name.equals(voted)){
                            Doctor temp=(Doctor)Player[i];
                            saved=temp.choose(Player[j].name);
                        }
                    }
                }

                //detective wake up
                if(Player[i].role==Roles.detective){
                    for(int j=0; j<size; j++){
                        if(Player[j].name.equals(voted)){
                            ((Detective)Player[i]).asking(Player[j].name);
                        }
                    }
                }

            }

        }
    }

    public static void endNight(){
        Players max=Player[0];
        //silenced
        for(int i=0; i<size; i++){
            if(Player[i].silenced){
                System.out.println(ColorPrint.ANSI_YELLOW+"silenced "+Player[i].name+ColorPrint.ANSI_RESET);
            }
        }
        //find max night voted player
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
            if(saved!=max){
                kill(max);
                System.out.println(ColorPrint.ANSI_YELLOW+"mafia tried to kill "+max.name+ColorPrint.ANSI_RESET);
                System.out.println(ColorPrint.ANSI_YELLOW+max.name+" was killed"+ColorPrint.ANSI_RESET);
            }
            else{
                System.out.println(ColorPrint.ANSI_YELLOW+"mafia tried to kill "+max.name+ColorPrint.ANSI_RESET);
                System.out.println(ColorPrint.ANSI_YELLOW+"nobody died"+ColorPrint.ANSI_RESET);
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
                kill(maxes[1]);
                System.out.println(ColorPrint.ANSI_YELLOW+"mafia tried to kill "+maxes[1].name+ColorPrint.ANSI_RESET);
                System.out.println(ColorPrint.ANSI_YELLOW+maxes[1].name+" was killed"+ColorPrint.ANSI_RESET);
            }
            else if(maxes[1]==saved){
                kill(maxes[0]);
                System.out.println(ColorPrint.ANSI_YELLOW+"mafia tried to kill "+maxes[0].name+ColorPrint.ANSI_RESET);
                System.out.println(ColorPrint.ANSI_YELLOW+maxes[0].name+" was killed"+ColorPrint.ANSI_RESET);
            }
            else{
                System.out.println(ColorPrint.ANSI_YELLOW+"nobody died"+ColorPrint.ANSI_RESET);
            }
        }
        else{
            System.out.println(ColorPrint.ANSI_YELLOW+"nobody died"+ColorPrint.ANSI_RESET);
        }
    }

}


/*
create_game a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13
assign_role a1 mafia
assign_role a2 godfather
assign_role a3 silencer
assign_role a4 villager
assign_role a5 bulletproof
assign_role a6 Joker
assign_role a7 doctor
assign_role a8 detective
assign_role a9 villager
assign_role a10 villager
assign_role a11 villager
assign_role a12 villager
assign_role a13 villager
start_game
 */