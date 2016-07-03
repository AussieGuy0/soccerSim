public class Simple {
    
   public static void main(String[] args) {
       Team spain = new Team("Spain",9,11,14,6,3,5,2,"433","normal");
       spain.loadPlayers("src/data/spain.txt");
       Team italy = new Team("Italy",11,14,17,4,5,4,5,"451","normal");
       italy.loadPlayers("src/data/italy.txt");

       new Match(spain,italy);
   }
}
