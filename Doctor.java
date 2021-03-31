public class Doctor extends UsefulVillagers{
    //constructor
    public Doctor(String name, Roles role) {
        super(name, role);
    }

    @Override
    public void action(String name) {
        int i;
        for(i=0; i<100; i++){
            if(MainGame.Player[i].name.equals(name)){
                break;
            }
        }
    }

    public Players choose(String name){
        int i;
        for(i=0; i<100; i++){
            if(MainGame.Player[i].name.equals(name)){
                break;
            }
        }
        return MainGame.Player[i];
    }
}