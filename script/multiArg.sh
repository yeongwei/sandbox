##
## The Shebang has to be set to bash in order to avoid std out message from shift if there is 0 argument.
##

#!/bin/bash

echo "Number of argument: " $#;

firstArg=$1 
shift
echo "First argument: " $firstArg;

secondArg=$1
shift
echo "Second argument: " $secondArg;
