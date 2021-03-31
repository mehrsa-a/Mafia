public class  Detective extends UsefulVillagers{
    int k=0;
    //constructor
    public Detective(String name, Roles role) {
        super(name, role);
    }

    @Override
    public void action(String name){
        for(int i=0; i<100; i++){
            if(MainGame.Player[i].name==name){
                if(MainGame.Player[i].role==Roles.mafia||MainGame.Player[i].role==Roles.silencer){
                    System.out.println("yes");
                }
                else{
                    System.out.println("no");
                }
                break;
            }
        }
    }
}
