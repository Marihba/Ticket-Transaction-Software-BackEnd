
HOME=$PWD
INPUTS="$HOME/Data Files"
RESULTS=""
# reset data files
./init.sh
# prompt user to save daily files
echo "Would you like to save merged dtfs and user files for each day? [y/n]"
read PROMPT
cd "$INPUTS"
if [ "$PROMPT" == "y" ]; then
  if [ ! -d "$INPUTS/Results" ]; then
    # make results directory 
    mkdir "Results"
    RESULTS="$INPUTS/Results"
  fi
fi
# call daily script on each day
for i in $( ls | egrep 'Day[0-9]' ); 
do 
  echo "running $i"
  if [ "$PROMPT" == "y" ]; then
    if [ ! -d "$RESULTS/$i" ]; then
      cd "$RESULTS"
      # make results directory 
      mkdir $i
      cd "$INPUTS"
    fi
    # copy old data files to results
    cp -f *.data "$RESULTS/$i"
    cd "$RESULTS/$i"
    for j in *.data;
    do
      mv $j "old_$j"
    done
  fi
  # run daily script for the Day
  cd "$HOME"
  ./daily.sh $i
  cd "$INPUTS"
  # save results to results directory
  if [ "$PROMPT" == "y" ]; then
    # copy files to results
    cp -f *.data "$RESULTS/$i"
    cp "merged_DTF.output" "$RESULTS/$i"
  fi
done