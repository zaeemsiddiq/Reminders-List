//
//  ViewReminderController.swift
//  Reminders
//
//  Created by Zaeem Siddiq on 20/04/2016.
//  Copyright © 2016 Zaeem Siddiq. All rights reserved.
//

import UIKit
import CoreData

class ViewReminderController: UIViewController {

    var delegate:reminderDelegate?
    var selectedReminder: Reminder?
    
    @IBOutlet weak var txtTitle: UITextField!
    @IBOutlet weak var txtDescription: UITextField!
    @IBOutlet weak var dueDate: UIDatePicker!
    
    @IBOutlet weak var switchComplete: UISwitch!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        txtTitle.text = selectedReminder?.reminderTitle
        txtDescription.text = selectedReminder?.reminderDescription
        
        if let unwrappedDate = selectedReminder?.reminderDueDate! {
            dueDate.setDate(unwrappedDate, animated: false)
        }
        if(selectedReminder!.reminderIsCompleted == 1) {
            switchComplete.setOn(true, animated:true)
        }
        else {
            switchComplete.setOn(false, animated:true)
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    @IBAction func btnUpdate(sender: AnyObject) {
        let appDel: AppDelegate = (UIApplication.sharedApplication().delegate as! AppDelegate)
        let context: NSManagedObjectContext = appDel.managedObjectContext
        
        let fetchRequest = NSFetchRequest(entityName: "Reminder")
        fetchRequest.predicate = NSPredicate(format: "reminderTitle = %@", (selectedReminder?.reminderTitle)!)
        do {
            
            let fetchResults = try appDel.managedObjectContext.executeFetchRequest(fetchRequest) as? [Reminder]
            if fetchResults!.count != 0 {
                
                selectedReminder?.reminderTitle = txtTitle.text
                selectedReminder?.reminderDescription = txtDescription.text
                selectedReminder?.reminderDueDate = dueDate.date
                if(switchComplete.on) {
                    selectedReminder?.reminderIsCompleted = 1
                }
                else {
                    selectedReminder?.reminderIsCompleted = 0
                }
                
                let managedObject = fetchResults![0]
                managedObject.setValue(selectedReminder?.reminderTitle, forKey: "reminderTitle")
                managedObject.setValue(selectedReminder?.reminderDescription, forKey: "reminderDescription")
                managedObject.setValue(selectedReminder?.reminderDueDate, forKey: "reminderDueDate")
                managedObject.setValue(selectedReminder?.reminderIsCompleted, forKey: "reminderIsCompleted")
                try context.save()
            }
        }
        catch {
            let fetchError = error as NSError
            print(fetchError)
        }
        
        
        
        
        
        
        delegate?.updateComplete()
        navigationController?.popViewControllerAnimated(true)   //jump back to the previous screen
    }
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
