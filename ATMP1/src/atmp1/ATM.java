/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atmp1;

import static atmp1.Account.sArray;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author MBP-Z
 */
public class ATM 
{    
    Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) throws IOException
    {
        Account macct = new Account();
        macct.initStu();
        
        ATM matm = new ATM();
        matm.loginMenu();     
    }
   
    public void loginMenu() throws IOException 
    {
        Account acct = new Account();
        
        System.out.println("");
        System.out.println("");
        System.out.println("WELCOME TO THE Java BANK");
        System.out.println("");
        System.out.println("1 - Create Account");
        System.out.println("2 - Login");
        System.out.println("");
        System.out.println("Please enter your choice (0 to exit): ");
        
        int input2 = sc.nextInt();
        try
        {
            switch (input2)
            {
                case 1: acct.cAct();
                        break;         
                case 2: acct.login();
                        break;
                case 0: System.exit(0);
                        acct.writeFile();
                        break;  
                default:System.out.println("Please enter a valid input!");
                        break;
            }        
        }
        
        catch (InputMismatchException i)
        {
            System.out.println("Please enter a valid input!");
        }  
    }
    
    public void menu() throws IOException 
    {
        Account acct = new Account();
        int id = acct.getID();

        char mychar = 'm';
        while (mychar != 'd')
        {
            System.out.println("");
            System.out.println("      You are now in Account #" + acct.getID());
            System.out.println("           Type: " + sArray[id].setType());
            System.out.println("           Balance: " + sArray[id].getBalance());
            System.out.println("            1 - Deposit");
            System.out.println("            2 - Withdraw");
            System.out.println("            3 - Check Balance"); 
            System.out.println("            0 - Logout");

            int input = sc.nextInt();
            char choice;
            
            try
            {
                switch (input)
                {
                    case 1: choice = 'd';
                            acct.output(choice);
                            break;
                    case 2: choice = 'w';
                            acct.output(choice);
                            break;
                    case 3: choice = 'c';
                            acct.output(choice);
                            break;
                    case 0: acct.writeFile();
                            loginMenu();
                            break; 
                    default: System.out.println("Please enter a valid input!");
                             break;
                }
            }
            
            catch (InputMismatchException i)
            {
                System.out.println("Please enter a valid input!");
            }  
        }
    }
}

