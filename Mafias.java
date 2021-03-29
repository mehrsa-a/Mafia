class Mafias extends Players{

    public static int numberOfMafias;
    {
        numberOfMafias+=1;
    }

    //constructor
    public Mafias(){

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
        //its method for deleting a player from game.. i can use it at future
//        for(int i=0; i<MainGame.size; i++){
//            if(MainGame.NameOfPlayers[i].equals(name)){
//                MainGame.NameOfPlayers[i]=null;
//                for(int j=i; j<MainGame.size; j++){
//                    MainGame.NameOfPlayers[j]=MainGame.NameOfPlayers[j+1];
//                }
//                MainGame.size--;
//                MainGame.NameOfPlayers[MainGame.size]=null;
//            }
//        }
    }
}

class Silencer extends Mafias{

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
