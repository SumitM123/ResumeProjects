#!bin/bash
# add the shebang above to run this shell script by the bash shell

# create the cv log file to log code actions
cvlogfile="cv_log_$(date +"%m-%d-%Y_%H-%M-%S").txt"
# create the cv error file to log code errors
cverrorfile="cv_error_$(date +"%m-%d-%Y_%H-%M-%S").txt"

whoami > $cvlogfile

date >> $cvlogfile

# change directory to where data_sender.py is located
cd /home/nvidia/FRC2022-Vision

# if the user does not have executable permission for data_sender.py, add the permission
if [[ ! -x "data_sender.py" ]]
    then
        echo "user does not have permission to execute the file: data_sender.py, adding execute permission" >> $cvlogfile
        chmod +x "data_sender.py"
        echo "execute permission added" >> $cvlogfile
fi

echo "data_sender.py has been started" >> $cvlogfile

# run the data_sender.py file
# append outputs (default, so '1' not specified) to "cvlogfile", and redirect errors ('2') to "cverrorfile"
sudo python3 data_sender.py >> $cvlogfile 2> $cverrorfile