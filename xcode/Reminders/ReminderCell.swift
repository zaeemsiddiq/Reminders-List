//
//  ReminderCell.swift
//  Reminders
//
//  Created by Zaeem Siddiq on 20/04/2016.
//  Copyright © 2016 Zaeem Siddiq. All rights reserved.
//

import UIKit

class ReminderCell: UITableViewCell {

    
    @IBOutlet weak var labelTitle: UILabel!
    @IBOutlet weak var labelDescription: UILabel!    
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
