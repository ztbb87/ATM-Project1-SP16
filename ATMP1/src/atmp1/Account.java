/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atmp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author MBP-Z
 */
public class Account implements Serializable
{
    protected double balance;
    protected int firstdate;
    protected int seconddate;
    private Calendar date1 = new GregorianCalendar();
    private Calendar date2 = new GregorianCalendar();
    private boolean dateflag = false;
    static int ID;
    protected double rate;
    static Scanner law = new Scanner(System.in);
    static Account[] sArray = new Account[3];
    Account[] saArray = new Account[3];

    public void output(char choice) throws IOException
    {
        ATM at = new ATM();
        getBalance();
        int id = getID();

        if (dateflag == true)
        {
            getDate2();
            sArray[id].getInterest();

            if (choice == 'd')
                deposit();
            
            else if (choice == 'w')
                withdraw();
            
            else if (choice == 'c')
                checkBalance();
        }

        else
        {
            getDate1();
            
            if (choice == 'd')
                deposit();
            
            else if (choice == 'w')
                withdraw();
            
            else if (choice == 'c')
                checkBalance();
        }
    }

    public void initStu() throws IOException 
    {
        File f = new File("accounts.ser");
        if(!f.exists()) 
        {
            for( int i = 0; i < sArray.length; i++)
            {
                sArray[i] = null;
                writeFile();
            }
        }
        
        if(f.exists())
          readFile();
    }  
    
    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }

    public int getID()
    {
        return ID;
    }

    public void getInterest()
    {
        
    }
    
    public String setType()
    {
        String type = "";
        return type;
    }

    public void login() throws IOException
    {
        ATM at = new ATM();
        try
        {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter ID # [0, 1, or 2]: ");
            int x = sc.nextInt();
            
            if (sArray[x] == null)
            {
                System.out.println("That account does not exist!");
                System.out.println("Would you like to create an account? (y/n)");
                char achoice = law.nextLine().toLowerCase().charAt(0); 
                
                if (achoice == 'y')
                    cAct();
                
                else
                    logReturn();      
            }

            if (sArray[x] != null)
            {
                setID(x); 
                at.menu();
            }
         }
        
         catch (ArrayIndexOutOfBoundsException i)
         {
            System.out.println("That account does not exist!");
            logReturn();
        }
    }

    public void cAct() throws IOException
    {
        System.out.println("    Which account would you like?");
        System.out.println("          1. Checking");
        System.out.println("          2. Savings");
        System.out.println("");
        System.out.println("Please enter your choice: ");

        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();

        for (int i = 0; i < sArray.length; i++)
        {
            if (sArray[i] == null && input == 1)
            {
                System.out.println("Your ID is: " + i);
                sArray[i] = new Checking();
                sArray[i].balance = 100;
                sArray[i].ID = i;
                logReturn(); 
                break;
                
            }
            
//            if (sArray[i] == null && input == 2)
//            {
//                System.out.println("Your ID is: " + i);
//                sArray[i] = new Savings();
//                sArray[i].balance = 100;
//                sArray[i].ID = i;
//                logReturn();
//                break;            
//            }   

            if (sArray[i] == null && input == 2)
            {
                System.out.println("Your ID is: " + i);
                sArray[i] = new Savings();
                sArray[i].balance = 0;
                sArray[i].ID = i;
                logReturn();
                break;            
            }   
        }
        
        logReturn();
    }      

    public String getBalance()
    {
        NumberFormat currencyFormatter;
        String currencyOut;
        currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        currencyOut = currencyFormatter.format(balance);
        return currencyOut;
    }

    public void logReturn() throws IOException
    {
        System.out.println("Press any key to return to the login menu...");
        law.nextLine();
        ATM atm = new ATM();
        atm.loginMenu();
        writeFile();
    }

    public void menuReturn() throws IOException
    {
        ATM at = new ATM();
        System.out.println("Enter any key to continue...");
        law.nextLine();
        at.menu();
    }

    public void checkBalance()
    {
        int id = getID();
        System.out.println("Your balance is now: " + sArray[id].getBalance());
    }

    public void deposit() throws IOException
    {
        try
        {
            BufferedReader br;
            String entered_amount;

            System.out.print("How much would you like to deposit? :");
            br = new BufferedReader(new InputStreamReader(System.in));
            entered_amount = br.readLine();
            double amount = Double.valueOf(entered_amount);
            int id = getID();
            sArray[id].balance += amount;
            checkBalance();
        }
        
        catch(NumberFormatException i)
        {
            System.out.println("Please enter a valid input!");
            deposit();
        }
    }

    public void withdraw() throws IOException
    {
        BufferedReader br;
        String entered_amount;
        int id = getID();
        balance = sArray[id].balance;
        System.out.print("How much would you like to withdraw? :");
        br = new BufferedReader(new InputStreamReader(System.in));
        entered_amount = br.readLine();
        double amount = Double.valueOf(entered_amount).doubleValue();

        if (balance < amount)
        {
            System.out.println("Insufficient funds!");
            menuReturn();
        }
        
        else
            sArray[id].balance += - amount;
        
        checkBalance();
    }

    public void getDate1() throws IOException
    {
        try 
        {
            System.out.print("Enter today's date (mm/dd/yyyy): ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String inputText = br.readLine();
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            ParsePosition pos = new ParsePosition(0);
            Date date = new Date();
            date = formatter.parse(inputText, pos);

            date1.setTime(date);
            int id = getID();
            firstdate = date1.get(date1.DAY_OF_YEAR);
            sArray[id].firstdate = firstdate;
            dateflag = true;
        }
        
        catch(NullPointerException i)
        {
            System.out.println("Please enter a valid date!");
            getDate1();
        }  
    }

    public void getDate2() throws IOException
    {
        try 
        {
            System.out.print("Enter transaction date (mm/dd/yyyy): ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String inputText = br.readLine();
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            ParsePosition pos = new ParsePosition(0);
            Date date = new Date();
            date = formatter.parse(inputText, pos);

            date2.setTime(date);
        }
        
        catch(NullPointerException i)
        {
            System.out.println("Please enter a valid date!");
            getDate2();
        }  
        
        int id = getID();
        seconddate = date2.get(date2.DAY_OF_YEAR);
        sArray[id].seconddate = seconddate;

        if (firstdate > seconddate)
        {
            System.out.println("You must enter a future date!");
            getDate2();
        }     
    }

    public void writeFile() throws FileNotFoundException
    {
        try
        {
            FileOutputStream fos = new FileOutputStream("accounts.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(sArray); 
            oos.close();
            fos.close();
        }
        
        catch(IOException i)
        {
            i.printStackTrace();
        }             
    }

    public void readFile() 
    {
        try
        {
            FileInputStream fileIn = new FileInputStream("accounts.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            saArray = (Account[]) in.readObject();
            sArray = saArray;
            in.close();
            fileIn.close();
        }
        
        catch(IOException | NullPointerException | ClassNotFoundException i)
        {
            i.printStackTrace();
        }
    }
}
