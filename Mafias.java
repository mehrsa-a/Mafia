public class Mafias extends Players{
    public static int numberOfMafias;
    //initialize block to number of Mafias
    {
        numberOfMafias+=1;
    }
    //constructor
    public Mafias(String name, Roles role){
        super(name, role);
    }
    //when each mafia vote someone
    public void NightVote(String name) {
        for(int i=0; i<100; i++){
            if(MainGame.Player[i].name==name){
                MainGame.Player[i].NightVoted++;
                break;
            }
        }
    }
}