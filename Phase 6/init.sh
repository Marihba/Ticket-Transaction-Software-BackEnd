# Author : Stephanie Phung
# Version 1.0
# Run this to reset the entire system,
# before running the daily script.

cd "Default Data Files"
cp * ../"Data Files"
cd ../"Data Files"
rm -rf merged_DTF.output
cd "Daily Output"
rm -rf *
