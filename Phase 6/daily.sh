# Author : Stephanie Phung
# Version 1.0
# usage: 
# >./daily.sh <Day1>
# where <Day1> is the name of the daily folder
# containing all sample input from all the sessions.

# Enter directory containing sample files.
cd "Data Files"

# Clear previous files from the daily output.
rm -rf merged_DTF.output
cd "Daily Output"
rm -rf *
cd ..

# Enter the directory for the Day ($1) and run all the 
# sessions, saving the output into the Daily Output folder.
cd $1
FILES=$(ls -1 | wc -l)
for ((i=1; i<=FILES; i++));
 do
  ../../front_end ../current_user_accounts.data ../available_tickets.data ../"Daily Output"/session$i.output < session$i.input
done

# Go into the Daily Output folder and merge the files into
# a daily transaction file.
cd ../"Daily Output"
FILES=$(ls -1 | wc -l)
for ((i=1; i<=FILES;i++));
 do
  cat session$i.output >> ../merged_DTF.output
done

cd ..
cd ..
./save_changes.sh
