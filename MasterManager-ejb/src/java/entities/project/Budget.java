/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.project;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Miguel
 */
public class Budget implements Serializable {
    
    private List<BudgetItem> budgetItems;

    public Budget() {
        this.budgetItems = new LinkedList<>();
    }

    public void addBudgetItem(double value, String desription) {
        this.budgetItems.add(new BudgetItem(value, desription));
    }
    
    public void removeBudgetItem(int index) {
    
        if(this.budgetItems.size() > index) {
            this.budgetItems.remove(index);
        }
        
    }
    
}
