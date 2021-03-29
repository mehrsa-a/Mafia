class Mafias extends Players{

    public static int numberOfMafias;
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
                NightVoting temp= (NightVoting) MainGame.Player[i];
                temp.NightVoted++;
                break;
            }
        }
    }
}

class Silencer extends Mafias{

    public Silencer(String name, Roles role) {
        super(name, role);
    }

    //silencer silence a player with this method
    public void silence(String name){
        for(int i=0; i<100; i++){
            if(MainGame.Player[i].name==name){
                MainGame.Player[i].silenced=true;
                break;
            }
        }
    }
}