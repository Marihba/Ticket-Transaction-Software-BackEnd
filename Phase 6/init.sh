# Author : Stephanie Phung, Aaron Williams
# Version 1.1
# Run this to reset the entire system,
# before running the daily script.

HOME=$PWD
INPUTS="$HOME/Data Files"
REFRESH="$HOME/Default Data Files"
cd "$REFRESH"
cp * "$INPUTS"
cd "$INPUTS"
rm -rf merged_DTF.output
cd "Daily Output"
rm -rf *
cd ..
rm -rf Results