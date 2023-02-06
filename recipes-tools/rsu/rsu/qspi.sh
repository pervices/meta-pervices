#!/bin/bash
if [ $# -eq 0 ];
then
    echo "What .rpd file to program QSPI With?"
    read file 
else
    file=$1
fi

if [[ "$file" != *.rpd ]];
then
    echo "File must be a .rpd!"
    exit 1
fi

echo "rsu_client --erase 1"
rsu_client --erase 1;
echo "rsu_client --slot 1 --add $file"
rsu_client --slot 1 --add $file;
echo "rsu_client --request 1"
rsu_client --request 1;
echo "rsu_client --enable 1"
rsu_client --enable 1

echo "Done"
