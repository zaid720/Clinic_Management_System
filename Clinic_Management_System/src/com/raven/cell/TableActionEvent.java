/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cell;

/**
 *
 * @author RAVEN
 */
public interface TableActionEvent {

    public void onUpdate(int row);

    public void onRemove(int row);
    
}