import java.util.Scanner;

public class Show {
    public static void show() {
        System.out.println("|-----------------------------------------------------------|");
        System.out.println("|                  Welcome to the Library !                 |");
        System.out.println("|                  What do you want to do ?                 |");
        System.out.println("|-----------------------------------------------------------|");
        System.out.println("|1.Add Resources              |         2.Remove Resources  |");
        System.out.println("|-----------------------------------------------------------|");
        System.out.println("|3.Add User                   |         4.Remove User       |");
        System.out.println("|-----------------------------------------------------------|");
        System.out.println("|5.Borrow Resources           |         6.Return Resources  |");
        System.out.println("|-----------------------------------------------------------|");
        System.out.println("|7.Show Resources   |   8.Show Users   |     9.Show Borrows |");
        System.out.println("|-----------------------------------------------------------|");
        System.out.println("|10.Find what you have borrowed        |     11.Rating      |");
        System.out.println("|-----------------------------------------------------------|");
        System.out.println("|0.Exit                                                     |");
        System.out.println("|-----------------------------------------------------------|");
    }
    public static void showResources() {
        System.out.println("|-------------------------------------------------------|");
        System.out.println("|           We have many resources which one ?          |");
        System.out.println("|-------------------------------------------------------|");
        System.out.println("|1.Book                                                 |");
        System.out.println("|2.Audiobook                                            |");
        System.out.println("|3.Comic                                                |");
        System.out.println("|4.Magazine                                             |");
        System.out.println("|0.Go Back To Menu                                      |");
        System.out.println("|-------------------------------------------------------|");
    }
    public String getString(String prompt, Scanner scanner) {
        System.out.print(prompt + "-> ");
        return scanner.nextLine();
    }
    public int getInt(String prompt, Scanner scanner) {
        System.out.print(prompt + "-> ");
        return scanner.nextInt();
    }
}
