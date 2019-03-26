For Linux:

To compile:
> ./quick_compile.sh

To run manually:
> ./front_end current_user_accounts.data available_tickets.data transaction_stream.output


For Windows:

Using the Developer Command Prompt for VS 2017
> cd "into the Raw Source Code folder"
> cl /EHsc Account.cpp BasicTransaction.cpp FrontEnd.cpp LoginTransaction.cpp TicketTransaction.cpp Transaction.cpp RefundTransaction.cpp /link /out:front_end.exe

To run the program:
> front_end.exe current_user_accounts.data available_tickets.data transaction_stream.output

Once you see the > prompt, the only command that is accepted is either "login" or "exit".
