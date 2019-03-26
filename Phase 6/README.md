For Linux:

To run front end manually:
> ./front_end <userFile> <ticketFile> <outputFile>

To run back end manually:

> cd back_end
> java Controller <userFile> <ticketFile> <inputFile>

---

Before running the daily (and subsequently weekly) script, reset the system using:
> ./init

To run the daily script, you should first create a folder inside "Data Files" (e.g. "Day1") containing all session.input files, and include the folder name as the first and only argument:
> ./daily.sh Day1

Alternatively, you can run a live session that will automatically go through the back end upon exit:
> ./manual_input.sh
