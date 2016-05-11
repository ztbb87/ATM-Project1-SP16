/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atmp1;

import java.io.Serializable;

/**
 *
 * @author MBP-Z
 */
public class Checking extends Account implements Serializable
{
    @Override
    public void getInterest()
    {
        int id = getID();
        int datediff = seconddate - firstdate;
        rate = 0.5 / 365;
        double ratetime = Math.pow(1 + rate, datediff);
        balance *= ratetime;
        firstdate = seconddate;
    } 
    
    @Override
    public String setType()
    {
        String type = "Checking";
        return type;
    }
}
