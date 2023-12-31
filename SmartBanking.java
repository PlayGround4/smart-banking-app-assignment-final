import java.util.Arrays;
import java.util.Scanner;

public class SmartBanking {
  static final String clear = "\033[H\033[2J";
  static final String bold = "\033[;1m";
  static final String reset = "\033[0m";
  static final String blue = "\033[034m";
  static final String red_bold = "\033[31;1m";
  static final String green_bold = "\033[32;1m";

  static String[][] accountDetails = new String[0][3];

  static final String DASHBOARD = "💰" + " Welcome to Smart Banking App";
  static final String OPEN_NEW_ACCOUNT = "➕"+ " Open New Account";
  static final String DEPOSITS = "🔺"+" Deposit Money";
  static final String WITHDRAW = "🔻"+" Withdraw Money";
  static final String TRANSFER = "🔁"+" Transfer Money";
  static final String CHECK_BALANCE = "💸"+" Check Account Balance";
  static final String DELETE = "➖"+" Drop Existing Account";
  static final String EXIT = "🔚"+" Exit";

  static final String ERROR_MSG = String.format("%s%s%s\n", red_bold, "%s", reset);
  static final String SUCCESS_MSG = String.format("%s%s%s\n", green_bold, "%s", reset);
  static final String THANKYOU_MSG = "%sThank you for banking with Smart Banking App %s" + "📱\n\n";
  
  static String screen = DASHBOARD;

  private static final Scanner scanner = new Scanner(System.in);

  static int index=0;
  static double deposit=0;
  static String account;
  static String name;
  static String id;
  static boolean valid =true;

  public static void main(String[] args) {

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
          
          System.out.printf(
            "\nNew Account Holder's ID: SDB-%05d \n",
            (accountDetails.length + 1)
          );

          id = String.format("SDB-%05d", (accountDetails.length + 1));
         
          accountNameValidation();

          if(!valid) continue;

          do {
            valid = true;
            System.out.print("Enter Initial Deposit Value: ");
            deposit = scanner.nextDouble();
            scanner.nextLine();
            if (deposit < 5000) {
              System.out.printf(ERROR_MSG, "Insufficient Amount");
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
            "%s%s : %s added sucessfully.\nDo you want to add new customer (Y/n)?%s ",green_bold,
            id,
            name,reset
          );
          if (scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
          screen = DASHBOARD;
          break;

        case DEPOSITS:

          accountNumValidation();
          if(!valid) continue;

          System.out.printf("Current Balance: Rs. %,.2f\n",Float.valueOf(accountDetails[index][2]));

          do{ 
            valid=true;
            System.out.print("Enter the amount to deposit: ");
            deposit = scanner.nextDouble();
            scanner.nextLine();
            
            if(deposit<500){
                System.out.printf(ERROR_MSG, "Insufficient Amount."); 
                valid=false;
            }
            }while(!valid);

            double temp = Double.valueOf(accountDetails[index][2]);
            temp+=deposit;
            accountDetails[index][2] = temp + "";

            System.out.printf("New Account Balance        : Rs. %,.2f\n",Float.valueOf(accountDetails[index][2]));

            System.out.printf("\n%sRs. %,.2f depositd to account no. %s successfully.Do you want to deposit again (Y/n)?%s",green_bold,deposit,accountDetails[index][0],reset);
            if(scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
            screen = DASHBOARD;
            break;
        
        case WITHDRAW:

          accountNumValidation();
          if(!valid) continue;

          System.out.printf("Current Balance: Rs. %,.2f\n",Float.valueOf(accountDetails[index][2]));

          double withdrawal;

          do{
            valid =true;
            System.out.print("Enter the amount to withdraw: ");
            withdrawal = scanner.nextDouble();
            scanner.nextLine();

            if(withdrawal<100){
              System.out.printf(ERROR_MSG, "Insufficient Amount."); 
              valid=false;
            }

          }while(!valid);
            temp = Double.valueOf(accountDetails[index][2]);
            temp-=withdrawal;
            accountDetails[index][2] = temp + "";
            System.out.printf("New Account Balance: Rs. %,.2f\n",Float.valueOf(accountDetails[index][2]));

            System.out.printf(
              "\n%sWithdrawal from account %s is sucessfull.\nDo you want to withdraw again (Y/n)? %s",green_bold,
              accountDetails[index][0],reset
              
            );
            if (scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
            screen = DASHBOARD;
            break;


        case TRANSFER:

          System.out.println("From Account: ");
          accountNumValidation();
          if(!valid) continue;

          System.out.println("-".repeat(50));
          System.out.printf("From Account Number         : %s\n",account);

          for (int i = 0; i < accountDetails.length; i++) {
            if(account.equals(accountDetails[i][0])){
              index = i;
              break;
            }
          }

          int fromIndex = index;
          System.out.printf("From Account Name           : %s\n",accountDetails[fromIndex][1]);
          System.out.printf("From Account Current Balance: Rs. %,.2f\n",Float.valueOf(accountDetails[fromIndex][2]));
          double fromBalance = Double.valueOf(accountDetails[fromIndex][2]);

          System.out.println("-".repeat(50));

          System.out.println("To Account:   ");
          accountNumValidation();
          System.out.println(index + " "+ fromIndex);
          if(!valid) continue;
          if(index==fromIndex){
            System.out.printf("%sOwn account transfer is invalid. Do you want to try again?%s",red_bold,reset);
            if(scanner.nextLine().toUpperCase().strip().equals("Y")) {accountNumValidation();if(!valid) continue;}
            screen = DASHBOARD;
            break;
          }


          System.out.println("-".repeat(50));
          System.out.printf("To Account Number           : %s\n",account);
          for (int i = 0; i < accountDetails.length; i++) {
            if(account.equals(accountDetails[i][0])){
              index = i;
              break;
            }
          }
          System.out.printf("To Account Name             : %s\n",accountDetails[index][1]);
          System.out.printf("To Account Current Balance  : Rs. %,.2f\n",Float.valueOf(accountDetails[index][2]));
          double toBalance = Double.valueOf(accountDetails[index][2]);

          System.out.println("-".repeat(50));

          do{
            valid = true;
            System.out.print("Enter the amount to transfer: ");
            double transfer = scanner.nextDouble();
            scanner.nextLine();

          if(transfer<100){
            System.out.printf(ERROR_MSG,"Transfer amount should be more than Rs. 100.00");
            valid = false;
            continue ;
          } 

          fromBalance-=(transfer + 0.02 * transfer);
          accountDetails[fromIndex][2] = fromBalance+"";

          toBalance +=transfer;
          accountDetails[index][2] = toBalance + "";
          
          if(fromBalance<500){
            System.out.printf("%sInsufficient account balance in %s account%s\n",red_bold,accountDetails[fromIndex][0],reset);
            valid = false;
            continue ;
          } 


          }while(!valid);

          System.out.printf("New From Account Balance: %s\n",fromBalance);
          System.out.printf("New To Account Balance: %s\n",toBalance);

          System.out.printf("%sTransfer from account %s to account %s is successfull.Do you want to continue (Y/n)? %s",green_bold,accountDetails[fromIndex][0],accountDetails[index][0],reset);
          if(scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
          screen = DASHBOARD;
          break;

        case CHECK_BALANCE:

          accountNumValidation();
          if(!valid) continue;
          
          for (int i = 0; i < accountDetails.length; i++) {
            if(account.equals(accountDetails[i][0])){
              index = i;
              break;
            }
          }
          System.out.printf("Account holder's name: %s\n",accountDetails[index][1]);
          System.out.println("-".repeat(50));
          System.out.printf("Current Account Balance     : Rs. %,.2f\n",Float.valueOf(accountDetails[index][2]));
          System.out.printf("Available amount to withdraw: Rs. %,.2f\n",Float.valueOf(accountDetails[index][2])-500);

          System.out.print("\n\nDo you want to check balance again (Y/n)? ");
          if (scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
          screen = DASHBOARD;
          break;

        case DELETE:

          accountNumValidation();
          if(!valid) continue;

          System.out.printf("Account holder's name: %s\n",accountDetails[index][1]);
          System.out.printf("Current Account Balance: Rs. %,.2f\n",Float.valueOf(accountDetails[index][2]));

          String deleteNum = accountDetails[index][0];
          String deleteName = accountDetails[index][1];

          System.out.printf(ERROR_MSG,"Are you sure you want to delete (Y/n)? ");
          if (scanner.nextLine().strip().toUpperCase().equals("Y")){
            tempDetails = new String[accountDetails.length-1][3];
            for (int i = 0; i < index; i++) {
              tempDetails[i]=accountDetails[i];
            }

            for (int i = index,j=i-1; i < accountDetails.length-1; i++) {
              tempDetails[i] = accountDetails[i+1];
            }

            accountDetails = tempDetails;

            System.out.printf("%s : %s has been deleted successfully. Do you want to continue (Y/n)? ",deleteNum,deleteName);
          if (scanner.nextLine().strip().toUpperCase().equals("Y"))
          continue;
          screen = DASHBOARD;
          break;
            
          }
          screen = DASHBOARD;
          break;

          default:
          
          System.out.printf(THANKYOU_MSG,green_bold,reset);
          System.exit(0);
      }
    } while (true);
  }
  
  public static void accountNumValidation(){
    do{
      valid = true;
      System.out.print("Enter the Account No.       : ");
      account = scanner.nextLine().strip();

      if(account.isBlank()){
        System.out.printf("%sAccount Number can't be empty.%s\n", red_bold, reset); 
        valid=false;
      }

      else if(account.length()!=9 || !account.startsWith("SDB-")){
        System.out.printf("%sInvalid format.%s\n", red_bold, reset); 
        valid = false;
      }


      else{
        boolean exists = false;
      loop1:
        for (int j = 0; j < accountDetails.length; j++) {
          if(accountDetails[j][0].equals(account)){
            index = j;
            exists = true;
            break loop1;
          }
        } 
         
        if(!exists){
          System.out.printf("%sAccount Number is not found.%s\n", red_bold, reset); 
          valid=false;
        }
      }

      if(!valid){
        System.out.printf("%sDo you want to try again (Y/n)?%s",red_bold,reset);
        if(scanner.nextLine().strip().toUpperCase().equals("Y"))
          continue;
          screen = DASHBOARD;
          return;
      }

    }while(!valid);
  }
  public static void accountNameValidation(){
    do {
      valid = true;
      System.out.print("Enter Account Holder's Name: ");
      name = scanner.nextLine().strip();
            
      if (name.isBlank()) {
        System.out.printf(ERROR_MSG, "Name can't be empty");
        valid = false;
        continue;
      }

      for (int i = 0; i < name.length(); i++) {
        if (!(Character.isLetter(name.charAt(i)) || Character.isSpaceChar(name.charAt(i)))) {
          System.out.printf(ERROR_MSG, "Invalid Name");
          valid = false;
          break;
        }
      }
    } while (!valid);
  }
}
