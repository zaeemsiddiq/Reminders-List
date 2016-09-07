//
//  AddReminderViewController.swift
//  Reminders
//
//  Created by Zaeem Siddiq on 20/04/2016.
//  Copyright © 2016 Zaeem Siddiq. All rights reserved.
//

import UIKit
import CoreData

protocol reminderDelegate{
    func addComplete()
    func updateComplete()
}


class AddReminderViewController: UIViewController {
    var rtitle: String?
    var rdescription: String?
    var rdate: NSDate?
    var rcomplete: Bool?
    var delegate:reminderDelegate?
    var managedObjectContext: NSManagedObjectContext
  
    
    @IBOutlet weak var txtTitle: UITextField!
    @IBOutlet weak var txtDescription: UITextField!
    @IBOutlet weak var dueDate: UIDatePicker!
    @IBOutlet weak var isComplete: UISwitch!
    @IBAction func btnAddReminder(sender: AnyObject) {
        if (delegate != nil) {
            rtitle = txtTitle.text
            rdescription = txtDescription.text
            rdate = dueDate.date
            if(isComplete.on) {
                rcomplete=true
            }
            else {
                rcomplete = false
            }
            
            let data = NSEntityDescription.insertNewObjectForEntityForName("Reminder", inManagedObjectContext: self.managedObjectContext) as? Reminder
            data!.reminderTitle = rtitle!
            data!.reminderDescription = rdescription!
            data!.reminderDueDate = rdate!
            data!.reminderIsCompleted = rcomplete!
            do {
                try self.managedObjectContext.save()
            }
            catch {
                let fetchError = error as NSError
                print(fetchError)
            }
            //let reminder = Reminder(reminderTitle: rtitle!, reminderDescription: rdescription!, )
            
            delegate?.addComplete()
            navigationController?.popViewControllerAnimated(true)   //jump back to the previous screen
        }
    }
    
    required init?(coder aDecoder: NSCoder) {
        let appDelegate = UIApplication.sharedApplication().delegate as! AppDelegate
        self.managedObjectContext = appDelegate.managedObjectContext
        super.init(coder: aDecoder)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

}
