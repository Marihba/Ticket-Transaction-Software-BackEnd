# Author : Stephanie Phung
# Version 1.0

# First step would be good to reinitialize the system
# with the default files
./init.sh

# Go into the directory containing all Day folders 
# and count them
cd "Data Files"
DAYS=$(ls -d ./* | wc)
cd ..

# Run the daily script for every folder
for ((i=1; i<=DAYS; i++));
 do
  ./daily.sh Day$i
done