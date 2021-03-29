import java.util.Scanner;

class MainGame{

    public static Players[] Player=new Players[100];
    public static int size=0;

    public static void main(String[] args){

        String[] NameOfPlayers=new String[100];
        Roles[] roles={ Roles.Joker, Roles.mafia, Roles.godfather, Roles.silencer, Roles.villager, Roles.detective, Roles.bulletproof, Roles.doctor, Roles.unknown};

        Scanner scanner=new Scanner(System.in);


        do{

            String action= scanner.next();


            if(action.equals("create_game")){

                String players= scanner.nextLine();
                NameOfPlayers=cut(players);

                for(int i=0; i<players.lastIndexOf(" "); i++){
                    if(players.charAt(i)==' '){
                        size++;
                    }
                }

            }

            else if(action.equals("assign_role")){

                String name=scanner.next();
                String role=scanner.next();

                for(int i=0, k=0; i<size; i++){
                    if(NameOfPlayers[i].equals(name)){
                        for(int j=0; j<9; j++){
                            if(role.equals(roles[j].toString())){
                                Player[k]= new Players(name, roles[j]);
                                k++;
                            }
                        }
                    }
                }

            }

            else if(action.equals("start_game")){

                System.out.println(myToString(Player));
                System.out.println("\n"+"Ready?\nSet!\n\nGO!!!");

                int day=1, night=1, counter=0;


                do{

                    if(counter%2==0){
                        System.out.println("Day "+day);
                    }
                    else{
                        System.out.println("Night "+night);
                    }

                    String act=scanner.next();

                    if(act.equals("end_vote")){

                        counter++;

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
                                    }

                                    else{
                                        System.out.println(Player[i].name+" died");
                                        kill(Player[i]);
                                    }

                                }

                            }

                        }

                    }

                    else if(act.equals("end_night")){

                        counter++;
                    }

                    else if(act.equals("get_game_state")){

                    }

                    else{
                        String voted=scanner.next();
                        if(counter%2==0){
                            for(int i=0; i<size; i++){
                                if(Player[i].name.equals(voted)){
                                    Player[i].DayVoted++;
                                    break;
                                }
                            }
                        }
                        else{

                        }
                    }


                }while(true);


            }


        }while (true);

    }


    public static String[] cut(String input){
        String[] commands;
        commands = input.split(" ");
        return commands;
    }

    public static String myToString(Players[] input){
        String ans="";
        for(int i=0; i<size; i++){
            ans=ans+input[i].name+": "+input[i].role.toString()+"\n";
        }
        return ans;
    }

    public static void kill(Players input){
        for(int i=0; i<size; i++){
            if(input==Player[i]){
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