import java.util.Arrays;
import java.util.Scanner;

public class SmartBanking {
  static final String clear = "\033[H\033[2J";
  static final String bold = "\033[;1m";
  static final String reset = "\033[0m";
  static final String blue = "\033[034m";
  static final String red_bold = "\033[31;1m";

  static String[][] accountDetails = new String[0][3];
  static final String DASHBOARD = "💰" + " Welcome to Smart Banking App";
  static final String OPEN_NEW_ACCOUNT = "➕"+ " Open New Account";
  static final String DEPOSITS = "🔺"+" Deposit Money";
  static final String WITHDRAW = "🔻"+" Withdraw Money";
  static final String TRANSFER = "🔁"+" Transfer Money";
  static final String CHECK_BALANCE = "💸"+" Check Account Balance";
  static final String DELETE = "➖"+" Drop Existing Account";
  static final String EXIT = "Exit";
  static String screen = DASHBOARD;

  private static final Scanner scanner = new Scanner(System.in);

  static int index=0;
  static int deposit=0;


  public static void main(String[] args) {
    

    
    // String[][] accountDetails = {{"SDB-00001","Hasara","52000"},{"SDB-00002","Gimhani","85000"}};

    mainLoop:
    do {
      final String APP_TITLE = String.format(
        "%s%s%s%s",
        bold,
        blue,
        screen,
        reset
      );
      System.out.println(clear);
      System.out.println("-".repeat(50));
      System.out.println(
        " ".repeat((50 - APP_TITLE.length() + 8) / 2).concat(APP_TITLE)
      );

      System.out.println("-".repeat(50));

      switch (screen) {
        case DASHBOARD:
          System.out.println("\n[1]. Open New Account");
          System.out.println("[2]. Deposit Money");
          System.out.println("[3]. Withdraw Money");
          System.out.println("[4]. Transfer Money");
          System.out.println("[5]. Check Account Balance");
          System.out.println("[6]. Drop Existing Account");
          System.out.println("[7]. Exit\n");
          System.out.print("Enter an option to continue > ");
          int option = scanner.nextInt();
          scanner.nextLine();

          switch (option) {
            case 1:
              screen = OPEN_NEW_ACCOUNT;
              break;
            case 2:
              screen = DEPOSITS;
              break;
            case 3:
              screen = WITHDRAW;
              break;
            case 4:
              screen = TRANSFER;
              break;
            case 5:
              screen = CHECK_BALANCE;
              break;
            case 6:
              screen = DELETE;
              break;
            case 7:
              screen = EXIT;
              break;
            default:
              continue;
          }

          break;
        case OPEN_NEW_ACCOUNT:
          String name;
          
          System.out.printf(
            "\nNew Account Holder's ID: SDB-%05d \n",
            (accountDetails.length + 1)
          );

          String id = String.format("SDB-%05d", (accountDetails.length + 1));

          boolean valid;

          do {
            valid = true;
            System.out.print("Enter Account Holder's Name: ");
            name = scanner.nextLine().strip();
            if (name.isBlank()) {
              System.out.printf("%sName can't be empty%s\n", red_bold, reset);
              valid = false;
              continue;
            }
            for (int i = 0; i < name.length(); i++) {
              if (
                !(
                  Character.isLetter(name.charAt(i)) ||
                  Character.isSpaceChar(name.charAt(i))
                )
              ) {
                System.out.printf("%sInvalid Name%s\n", red_bold, reset);
                valid = false;
                break;
              }
            }
          } while (!valid);

          do {
            valid = true;
            System.out.print("Enter Initial Deposit Value: ");
            deposit = scanner.nextInt();
            scanner.nextLine();
            if (!(deposit > 5000)) {
              System.out.printf("%sInsufficient Amount%s\n", red_bold, reset);
              valid = false;
              continue;
            }
          } while (!valid);

          String[][] tempDetails = new String[accountDetails.length + 1][3];

          for (int i = 0; i < accountDetails.length; i++) {
            tempDetails[i] = accountDetails[i];
          }

          tempDetails[tempDetails.length - 1][0] = id;
          tempDetails[tempDetails.length - 1][1] = name;
          tempDetails[tempDetails.length - 1][2] = deposit + "";

          accountDetails = tempDetails;

          System.out.println();
          System.out.printf(
            "%s : %s added sucessfully.\nDo you want to add new customer (Y/n)? ",
            id,
            name
          );
          if (scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
          screen = DASHBOARD;
          break;

        case DEPOSITS:
          
         
         accountNumValidation();

         do{ 
          valid=true;
          System.out.print("Enter the amount to deposit: ");
          deposit = scanner.nextInt();
          scanner.nextLine();
          
          if(deposit<500){
              System.out.printf("%sInsufficient Amount. %s\n", red_bold, reset); 
              valid=false;
          }
          }while(!valid);

          int temp = Integer.valueOf(accountDetails[index][2]);
          temp+=deposit;
          accountDetails[index][2] = temp + "";
          System.out.println(accountDetails[index][2]);

          System.out.println("Do you want to deposit again (Y/n)?");
          if(scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
          screen = DASHBOARD;
          break;
        
        case WITHDRAW:

        accountNumValidation();
          int withdrawal;

        do{
          valid =true;
          System.out.print("Enter the amount to withdraw: ");
          withdrawal = scanner.nextInt();
          scanner.nextLine();

          if(withdrawal<100){
            System.out.printf("%sInsufficient Amount. %s\n", red_bold, reset); 
            valid=false;
          }

        }while(!valid);
          temp = Integer.valueOf(accountDetails[index][2]);
          temp-=withdrawal;
          accountDetails[index][2] = temp + "";
          System.out.println(accountDetails[index][2]);


          default:
          System.exit(0);
      }
    } while (true);
  }
  
  public static void accountNumValidation(){
    boolean valid;
    mainLoop:
          do{
            valid = true;
            System.out.print("Enter the Account No.: ");
          String account = scanner.nextLine().strip();

          if(account.isBlank()){
            System.out.printf("%sAccount Number can't be empty. Do you want to try again (Y/n)? %s", red_bold, reset); 
            if(scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
            screen = DASHBOARD; 
            break; 
          }

          if(account.length()!=9 || !account.startsWith("SDB-")){
            System.out.printf("%sInvalid format. Do you want to try again (Y/n)? %s", red_bold, reset); 
            if(scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
            screen = DASHBOARD;     
            break;
          }
          int index=0;
    loop1:
          for (int i = 0; i < accountDetails.length; i++) {
            for (int j = 0; j < accountDetails.length; j++) {
              if(accountDetails[j][0].equals(account)){
                index = j;
              break loop1;
            }
            } 
              System.out.printf("%sAccount Number is not found. Do you want to try again (Y/n)? %s", red_bold, reset); 
              if(scanner.nextLine().strip().toUpperCase().equals("Y")) continue mainLoop ;
              screen = DASHBOARD;     
              break;
          }
          }while(!valid);
          System.out.printf("Current Balance: %s\n",accountDetails[index][2]);
  }
}
