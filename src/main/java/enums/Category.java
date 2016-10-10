/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enums;

/**
 *
 * @author andre
 */
public enum Category {
    
    CLOTHING, ELECTRONICS, WHITE_WARE, JEWLERRY, GUNS, UNDEFINED;
    
    public static Category fromInt(int x) {
        
        switch(x) {
        case 0:
            return CLOTHING;
        case 1:
            return ELECTRONICS;
        case 2:
            return WHITE_WARE;
        case 3:
            return JEWLERRY;
        case 4: 
            return GUNS;
        }
        
        return UNDEFINED;
    }
    
    public static Category fromString(String s) {
        
        switch(s) {
            case "Clothing":
                return CLOTHING;
            case "Electronics":
                return ELECTRONICS;
            case "White ware":
                return WHITE_WARE;
            case "Jewlerry":
                return JEWLERRY;
        }
        
        return UNDEFINED;
    }
    
    
}
