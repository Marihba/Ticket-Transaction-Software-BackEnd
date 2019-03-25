# Author : Stephanie Phung
# Version 1.0

cd "Data Files"
rm merged_DTF.output

../front_end current_user_accounts.data available_tickets.data merged_DTF.output

cd ..
./save_changes.sh
