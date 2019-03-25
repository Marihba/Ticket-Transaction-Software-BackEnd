# Author : Stephanie Phung
# Version 1.0
# Runs the back end to commit the daily changes.

cd back_end
java Controller ../"Data Files"/current_user_accounts.data ../"Data Files"/available_tickets.data ../"Data Files"/merged_DTF.output
