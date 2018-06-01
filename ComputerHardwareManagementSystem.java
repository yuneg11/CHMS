public class ComputerHardwareManagementSystem {
    private static final String databaseFileName = "database.txt";

    public static void main(String[] args) {
        FileManagement fm = new FileManagement(databaseFileName);
        DataManagement dm = new DataManagement(fm.loadDatabase());


        /* Do What You Want */

        System.out.println("Program over");
        fm.saveDatabase(dm.getProducts());
    }
}