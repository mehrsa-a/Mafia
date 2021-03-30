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


        do{


            String action= scanner.next();

            if(action.equals("create_game")){

                createGame();

            }

            else if(action.equals("assign_role")){

                assignRole();

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

                System.out.println(myToString(Player));
                System.out.println("\n"+"Ready?\nSet!\n\nGO!!!");

                int day=1, night=1, counter=0;

                System.out.println("Day "+day);
                do{

                    String act=scanner.next();

                    if(act.equals("start_game")){
                        System.out.println("game has already started");
                    }

                    else if(act.equals("end_vote")){

                        counter++;
                        day++;

                        endDay();

                        System.out.println("Night "+night);
                        //list of players that shoud wake up at night
                        for(int i=0; i<size; i++){
                            if(Player[i].role==Roles.mafia||Player[i].role==Roles.godfather||Player[i].role==Roles.silencer||Player[i].role==Roles.detective||Player[i].role==Roles.doctor){
                                System.out.println(Player[i].name+": "+Player[i].role.toString());
                            }
                        }

                    }

                    else if(act.equals("end_night")){

                        counter++;
                        night++;

                        Players max=Player[0];
                        boolean killedInThisDay=false;
                        for(int i=0; i<size; i++){

                            //silenced
                            if(Player[i].silenced){
                                System.out.println("silenced "+Player[i].name);
                            }

                            //killed
                            for(int j=0; j<size; j++){
                                if((Player[i]).NightVoted==(Player[j]).NightVoted){
                                    if(saved==Player[i]){
                                        kill(Player[j]);
                                        System.out.println("mafia tried to kill"+Player[j].name);
                                        System.out.println(Player[j].name+"was killed");
                                        killedInThisDay=true;
                                    }
                                    else if(saved==Player[j]){
                                        kill(Player[i]);
                                        System.out.println("mafia tried to kill"+Player[j].name);
                                        System.out.println(Player[j].name+"was killed");
                                        killedInThisDay=true;
                                    }
                                    else{
                                        System.out.println("nobody died");
                                        killedInThisDay=true;
                                    }
                                    break;
                                }
                                else if(((Villagers)Player[i]).NightVoted>=((Villagers)Player[j]).NightVoted){
                                    max=Player[i];
                                }
                            }

                        }

                        if(!killedInThisDay && saved!=max){
                            if(max.role==Roles.bulletproof){
                                if(max.dead){
                                    kill(max);
                                    System.out.println("mafia tried to kill"+max.name);
                                    System.out.println(max.name+"was killed");
                                }
                                else{
                                    for(int i=0; i<size; i++){
                                        if(Player[i]==max){
                                            Player[i].dead=true;
                                        }
                                    }
                                }
                            }
                            else{
                                kill(max);
                                System.out.println("mafia tried to kill"+max.name);
                                System.out.println(max.name+"was killed");
                            }
                        }

                        if(Villagers.numberOfVillagers<=Mafias.numberOfMafias){
                            System.out.println("Mafia won!");
                            ans="Mafia won!";
                        }
                        else if(Mafias.numberOfMafias==0){
                            System.out.println("Villagers won!");
                            ans="Villagers won!";
                        }

                        System.out.println("Day "+day);
                    } //it has problem

                    else if(act.equals("get_game_state")){
                        System.out.println("Mafia = "+Mafias.numberOfMafias+"\nVillager = "+Villagers.numberOfVillagers);
                    }

                    else{
                        if(counter%2==0){
                            dayVoting();
                        }

                        else{

                            String voted=scanner.next();

                            for(int i=0; i<size; i++){

                                if(Player[i].name.equals(act)){

                                    //mafia wake up
                                    if(Player[i].role==Roles.godfather||Player[i].role==Roles.mafia){
                                        for(int j=0; j<size; j++){
                                            if(Player[j].name.equals(voted)){
                                                (Player[j]).NightVoted++;
                                            }
                                        }
                                    }

                                    //silencer wake up
                                    if(Player[i].role==Roles.silencer){
                                        Silencer temp=(Silencer) Player[i];
                                        if(!temp.called){
                                            for(int j=0; j<size; j++){
                                                if(Player[j].name.equals(voted)){
                                                    Player[j].silenced=true;
                                                }
                                            }
                                            ((Silencer)Player[i]).called=true;
                                        }
                                        else{
                                            for(int j=0; j<size; j++){
                                                if(Player[j].name.equals(voted)){
                                                    (Player[j]).NightVoted++;
                                                }
                                            }
                                        }
                                    }

                                    //doctor wake up
                                    if(Player[i].role==Roles.doctor){
                                        for(int j=0; j<size; j++){
                                            if(Player[j].name.equals(voted)){
                                                saved=((Doctor)Player[i]).choose(Player[j].name);
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

                        } //it has problem
                    }


                }while(!ans.equals("Joker won!") && !ans.equals("Mafia won!") && !ans.equals("Villagers won!"));


            }


        }while (scanner.hasNext());

    }


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

    public static String myToString(Players[] input){
        String ans="";
        for(int i=0; i<size; i++){
            ans=ans+input[i].name+": "+input[i].role.toString()+"\n";
        }
        return ans;
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
            System.out.println("nobody died");
        }

        else{

            for(int i=0; i<size; i++){

                if(max==Player[i].DayVoted){

                    if(Player[i].role==Roles.Joker){
                        System.out.println("Joker won!");
                        ans="Joker won!";
                    }

                    else{
                        System.out.println(Player[i].name+" died");
                        kill(Player[i]);

                        if(Villagers.numberOfVillagers<=Mafias.numberOfMafias){
                            System.out.println("Mafia won!");
                            ans="Mafia won!";
                        }
                        else if(Mafias.numberOfMafias==0){
                            System.out.println("Villagers won!");
                            ans="Villagers won!";
                        }
                    }

                }

            }

        }
    }

}