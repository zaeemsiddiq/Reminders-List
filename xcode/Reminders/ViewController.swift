//
//  ViewController.swift
//  Reminders
//
//  Created by Zaeem Siddiq on 19/04/2016.
//  Copyright © 2016 Zaeem Siddiq. All rights reserved.
//

import UIKit
import CoreData

class ViewController: UITableViewController, reminderDelegate {
    
    var managedObjectContext: NSManagedObjectContext
    
    var remindersList: NSMutableArray   // this array will hold freshly created objects unsorted
    var remindersSortedList: NSMutableArray // sorted array list, also used for displaying
    
    var currentReminder: Reminder?
    
    required init?(coder aDecoder: NSCoder) {
        self.remindersList = NSMutableArray()
        self.remindersSortedList = NSMutableArray()
        let appDelegate = UIApplication.sharedApplication().delegate as! AppDelegate
        self.managedObjectContext = appDelegate.managedObjectContext
        
        super.init(coder: aDecoder)
        
        }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        tableView.rowHeight = UITableViewAutomaticDimension
        
       loadData()
        
    }
    
    func loadData() {
        let fetchRequest = NSFetchRequest()
        let entityDescription = NSEntityDescription.entityForName("Reminder", inManagedObjectContext: self.managedObjectContext)
        fetchRequest.entity = entityDescription
        var result = NSArray?()
        do
        {
            result = try self.managedObjectContext.executeFetchRequest(fetchRequest)
            if result!.count > 0
            {
                self.remindersList.removeAllObjects()
                for res in result! {
                    self.currentReminder = res as? Reminder
                    self.remindersList.addObject(self.currentReminder!)
                }
                
                sortList()
                self.tableView.reloadData()
            }
            
        }
        catch
        {
            let fetchError = error as NSError
            print(fetchError)
        }
    }
    
    func sortList() {
        let dateFormatter: NSDateFormatter = NSDateFormatter()
        dateFormatter.dateFormat = "EEE, dd MMM yyyy HH:mm:ss z"
        let arr = self.remindersList.sort {($0.valueForKey("reminderDueDate") as! NSDate).compare($1.valueForKey("reminderDueDate") as! NSDate) == .OrderedDescending }
        self.remindersSortedList = arr as! NSMutableArray
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if (segue.identifier == "AddReminderSegue") {
            let addReminderVC:AddReminderViewController = segue.destinationViewController as! AddReminderViewController
            addReminderVC.delegate = self
        }
        else if(segue.identifier == "ViewReminderSegue") {
            // Get the cell that generated this segue.
            if let selectedReminderCell = sender as? ReminderCell {
                let indexPath = tableView.indexPathForCell(selectedReminderCell)!
                let selectedReminder = self.remindersSortedList[indexPath.row]
                let viewVC:ViewReminderController = segue.destinationViewController as! ViewReminderController
                viewVC.delegate = self
                viewVC.selectedReminder = selectedReminder as? Reminder
            }
        }
    }
    
    func addComplete() {
        loadData()
    }
    
    func updateComplete() {
        loadData()
    }
    
    override func tableView(tableView: UITableView, canEditRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        return true
    }
    
    override func tableView(tableView: UITableView, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath) {
        if (editingStyle == UITableViewCellEditingStyle.Delete) {
            let appDel: AppDelegate = (UIApplication.sharedApplication().delegate as! AppDelegate)
            let context: NSManagedObjectContext = appDel.managedObjectContext
            
            let r: Reminder = self.remindersSortedList[indexPath.row] as! Reminder
            
            
            
            
            let fetchRequest = NSFetchRequest(entityName: "Reminder")
            fetchRequest.predicate = NSPredicate(format: "reminderTitle = %@", (r.reminderTitle)!)
            do {
                
                let fetchResults = try appDel.managedObjectContext.executeFetchRequest(fetchRequest) as? [Reminder]
                if fetchResults!.count != 0 {
                    
                    let managedObject = fetchResults![0]
                    try context.deleteObject(managedObject)
                    try context.save()
                    loadData()
                }
            }
            catch {
                let fetchError = error as NSError
                print(fetchError)
            }
        }
    }
    
    override func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return 2
    }
    
    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        switch(section)
        {
        case 0: return self.remindersSortedList.count
        case 1: return 1
        default: return 0
        }
    }
    
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        
        if indexPath.section == 0
        {
            let cellIdentifier = "ReminderCell"
            let cell = tableView.dequeueReusableCellWithIdentifier(cellIdentifier, forIndexPath: indexPath) as! ReminderCell
            // Configure the cell
            let r: Reminder = self.remindersSortedList[indexPath.row] as! Reminder
            cell.labelTitle.text = r.reminderTitle
            cell.labelDescription.text = r.reminderDescription
            return cell

        }
        else{
            let cell = tableView.dequeueReusableCellWithIdentifier("TotalCell", forIndexPath: indexPath)
            
            cell.textLabel!.text = "Total Reminders: \(remindersSortedList.count)"
            return cell
        }
    }
    
    override func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        //let selectedReminder: Reminder = self.remindersList[indexPath.row] as! Reminder
    }
    

}

