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

                for(int i=0; i<size; i++){
                    Player[i].name=NameOfPlayers[i];
                }

            }

            else if(action.equals("assign_role")){

                String name=scanner.next();
                String role=scanner.next();
                for(int i=0; i<size; i++){
                    if(Player[i].name==name){
                        for(int j=0; j<9; j++){
                            if(role.equals(roles[i].toString())){
                                Player[i].role=roles[i];
                            }
                        }
                    }
                }
            }

            else if(action.equals("start_game")){

            }

            else if(action.equals("end_vote")){

            }

            else if(action.equals("end_night")){

            }

            else if(action.equals("get_game_state")){

            }


        }while (true);

    }


    public static String[] cut(String input){
        String[] commands = {};
        commands = input.split(" ");
        return commands;
    }

}