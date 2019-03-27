# Author : Stephanie Phung, Aaron Williams
# Version 1.1
# usage: 
# >./daily.sh <Day1>
# where <Day1> is the name of the daily folder
# containing all sample input from all the sessions.

# variable for main directory
HOME=$PWD
INPUTS="$HOME/Data Files"
# Enter directory containing sample files.
cd "$INPUTS"
# check for command line arguements
if [ ! -z "$1" ]; then
  if [ -d "$INPUTS/Daily Output" ]; then
    # Clear previous files from the daily output.
    rm -rf merged_DTF.output
    cd "Daily Output"
    rm -rf *
    cd "$INPUTS"
  else
    # create the directory if it does not exist
    mkdir "Daily Output"
  fi
  # Enter the directory for the Day ($1) and run all the 
  # sessions, saving the output into the Daily Output folder.
  echo "Would you like to make a transaction for $1 [y/n]?"
  read PROMPT
  if [ "$PROMPT" == "y" ]; then
    "$HOME/"./front_end "$INPUTS/current_user_accounts.data" "$INPUTS/available_tickets.data" "$INPUTS/Daily Output/session0.output"
  fi
  cd $1
  FILES=$(ls -1 | wc -l)
  for ((i=1; i<=FILES; i++));
  do
    "$HOME/"./front_end "$INPUTS/current_user_accounts.data" "$INPUTS/available_tickets.data" "$INPUTS/Daily Output/session$i.output" < session$i.input
  done
  # Go into the Daily Output folder and merge the files into
  # a daily transaction file.
  cd "$INPUTS/Daily Output"
  for i in $( ls );
  do
    echo "merging $i"
    cat $i >> "$INPUTS/merged_DTF.output"
  done
  echo "00                                                     " >> "$INPUTS/merged_DTF.output"
  echo "Running changes through the Back end"
  cd "$HOME"/back_end
  java Controller "$INPUTS/current_user_accounts.data" "$INPUTS/available_tickets.data" "$INPUTS/merged_DTF.output"
else
  echo "Day argument has not been specified; use './daily.sh <DayX>'"
fi
