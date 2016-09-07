//
//  Reminder.swift
//  Reminders
//
//  Created by Zaeem Siddiq on 21/04/2016.
//  Copyright © 2016 Zaeem Siddiq. All rights reserved.
//

import Foundation
import CoreData


class Reminder: NSManagedObject {
    
    @NSManaged var reminderDescription: String?
    @NSManaged var reminderDueDate: NSDate?
    @NSManaged var reminderIsCompleted: NSNumber?
    @NSManaged var reminderTitle: String?

}
extension Reminder {
    
}
