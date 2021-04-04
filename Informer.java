import java.util.Random;

public class Informer extends Villagers{
    public Informer(String name, Roles role) {
        super(name, Roles.informer);
    }
    public String action(){
        Random rand=new Random();
        int upperbound=4;
        int random=rand.nextInt(upperbound);
        //maybe there is nobody that mafia tried to kill him but it didn't die
        if(random==1&&MainGame.sizeOfTry==0){
            random++;
        }
        //maybe there is no joker at game
        boolean joker=false;
        for(int i=0; i<MainGame.size; i++){
            if (MainGame.Player[i].role == Roles.Joker) {
                joker = true;
                break;
            }
        }
        if(random==3&&!joker){
            random--;
        }
        if(random==0){
            int r=rand.nextInt(Mafias.numberOfMafias);
            int counter=0;
            for(int i=0; i<MainGame.size; i++){
                if(MainGame.Player[i].role==Roles.mafia||MainGame.Player[i].role==Roles.godfather||MainGame.Player[i].role==Roles.bomb||MainGame.Player[i].role==Roles.silencer){
                    counter++;
                }
                if(counter==r){
                    return "There is a mafia who’s name starts with "+MainGame.Player[i].name.charAt(0);
                }
            }
        }
        else if(random==1){
            int r=rand.nextInt(MainGame.sizeOfTry);
            return MainGame.tryToKill[r].name+" was voted to be killed";
        }
        else if(random==2){
            return "Number of alive mafia : "+String.valueOf(Mafias.numberOfMafias);
        }
        else{
            for(int i=0; i<MainGame.size; i++){
                if(MainGame.Player[i].role==Roles.Joker){
                    return "There is a joker who’s name starts with "+MainGame.Player[i].name.charAt(0);
                }
            }
        }
        return "";
    }
}