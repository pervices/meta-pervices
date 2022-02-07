#!/bin/bash

#Bash script that changes the cyan unit fan speeds depending on 
#the temperatures of the RF boards
#Any log or info flag output can be found in 
# /var/log/cyan_temp

#Board Power Status Variables
BOARD_ON='on'

#Tempurature Case Values
HRD_MIN=25
MIN=30
MED=35
MAX=40

#Logs File location:
LOG_LOCATION='/var/log/cyan_temp'


########
# Main Program
########

#Will be running in the background
#checking the temperatures of the boards every 15s
while true; do
    
    #Temp counter
    HRD_MIN_cnt=0
    MIN_cnt=0
    MED_cnt=0
    HIGH_cnt=0
    MAX_cnt=0
    
    #Checking Rx (slots; 0 1 4 5 8 9 12 13)
    for i in 0 1 4 5 8 9 12 13
        do
            #RFE Command to check status of boards
            ON_CHECK=$(rfe_control $i check) > /dev/null
            
            #Checking which boards are turned on
            if [[ "$ON_CHECK" == *"$BOARD_ON"* ]]; then
#                 echo "$ON_CHECK"
                
                #Checking the temp on the RX Board
                tempr=$(echo "board -u" | mcu -f $i) > /dev/null
#                 echo "$tempr"
                
                #Getting the temp on the RX board
                NUM_TEMP_RX=${tempr//[^0-9]/}  > /dev/null
#                 echo "RX Board Temp: $NUM_TEMP_RX"
                
                #Checking Temp, Adding to temp counts
                if [[ "$NUM_TEMP_RX" -lt "$HRD_MIN" ]]; then
#                     echo "Temp is less than 25"
                    ((HRD_MIN_cnt=HRD_MIN_cnt+1))
                    
                elif [[ "$NUM_TEMP_RX" -gt "$HARD_MIN" && "$NUM_TEMP_RX" -lt "$MIN" ]]; then
#                     echo "Temp is greater than 25"
                    ((MIN_cnt=MIN_cnt+1))
                    
                elif [[ "$NUM_TEMP_RX" -gt "$MIN" && "$NUM_TEMP_RX" -lt "$MED" ]]; then
#                     echo "Temp is greater than 30"
                    ((MED_cnt=MED_cnt+1))
                
                elif [[ "$NUM_TEMP_RX" -gt "$MED" && "$NUM_TEMP_RX" -lt "$MAX" ]]; then
#                     echo "Temp is greater than 35"
                    ((HIGH_cnt=HIGH_cnt+1))
                
                elif [[ "$NUM_TEMP_RX" -gt "$MAX" ]]; then
#                     echo "Temp is greater than 40"
                    ((MAX_cnt=MAX_cnt+1))
                fi
                
            else
                :
            fi
        done
        
    #Checking Tx (slots; 2 3 6 7 10 11 14 15)
    for i in 2 3 6 7 10 11 14 15
        do
            #Tx Temp Array
            tx_temp_arr=()
        
            #RFE Command to check status of boards
            ON_CHECK=$(rfe_control $i check) > /dev/null
            
            #Checking which boards are turned on
            if [[ "$ON_CHECK" == *"$BOARD_ON"* ]]; then
#                 echo "$ON_CHECK"
                
                #Checking the temp on the TX Board
                mapfile -t tx_temp_arr < <( echo "board -t" | mcu -f $i ) > /dev/null
                
                #Getting the Temp Values for each thermistor
                TX_TEMP_A0=${tx_temp_arr[0]//[^0-9]/}
                TX_TEMP_A0="${TX_TEMP_A0:1}"
                TX_TEMP_A1=${tx_temp_arr[1]//[^0-9]/}
                TX_TEMP_A1="${TX_TEMP_A1:1}"
                
#                 echo "TX A0 temp: $TX_TEMP_A0"
#                 echo "TX A0 temp: $TX_TEMP_A1"
                
                #Checking Temp, Adding to temp counts
                #Channel A 0
                if [[ "$TX_TEMP_A0" -lt "$HRD_MIN"  || "$TX_TEMP_A1" -lt "$HRD_MIN" ]]; then
#                     echo "Temp is less than 25"
                    ((HRD_MIN_cnt=HRD_MIN_cnt+1))
                    
                elif [[ "$TX_TEMP_A0" -gt "$HARD_MIN" && "$TX_TEMP_A0" -lt "$MIN" || "$TX_TEMP_A1" -gt "$HARD_MIN" && "$TX_TEMP_A1" -lt "$MIN" ]]; then
#                     echo "Temp is greater than 25"
                    ((MIN_cnt=MIN_cnt+1))
                    
                elif [[ "$TX_TEMP_A0" -gt "$MIN" && "$TX_TEMP_A0" -lt "$MED" || "$TX_TEMP_A1" -gt "$MIN" && "$TX_TEMP_A1" -lt "$MED" ]]; then
#                     echo "Temp is greater than 30"
                    ((MED_cnt=MED_cnt+1))
                
                elif [[ "$TX_TEMP_A0" -gt "$MED" && "$TX_TEMP_A0" -lt "$MAX" || "$TX_TEMP_A1" -gt "$MED" && "$TX_TEMP_A1" -lt "$MAX"  ]]; then
#                     echo "Temp is greater than 35"
                    ((HIGH_cnt=HIGH_cnt+1))
                
                elif [[ "$TX_TEMP_A0" -gt "$MAX" || "$TX_TEMP_A1" -gt "$MAX" ]]; then
#                     echo "Temp is greater than 40"
                    ((MAX_cnt=MAX_cnt+1))
                fi
            else
                :
            fi
        done
    
#     echo "HRD_MIN_cnt: $HRD_MIN_cnt"
#     echo "MIN_cnt: $MIN_cnt"
#     echo "MED_cnt: $MED_cnt"
#     echo "HIGH_cnt: $HIGH_cnt"
#     echo "MAX_cnt: $MAX_cnt"
    
    if [[ $MAX_cnt -gt 0 ]]; then
#         echo "set fan MAX"
        echo "$(./fanctl full)"
    
    elif [[ $HRD_MIN_cnt -gt $MIN_cnt && $HRD_MIN_cnt -gt $MED_cnt && $HRD_MIN_cnt -gt $HIGH_cnt && $HRD_MIN_cnt -gt $MAX_cnt ]]; then
#         echo "set fan to HARD MIN"
        echo "$(./fanctl low)"
    
    elif [[ $MIN_cnt -gt $HRD_MIN_cnt && $MIN_cnt -gt $MED_cnt && $MIN_cnt -gt $HIGH_cnt && $MIN_cnt -gt $MAX_cnt ]]; then
#         echo "set fan to MIN"
        echo "$(./fanctl medium)"
        
    elif [[ $MED_cnt -gt $HRD_MIN_cnt && $MED_cnt -gt $MIN_cnt && $MED_cnt -gt $HIGH_cnt && $MED_cnt -gt $MAX_cnt ]]; then
#         echo "set fan to MED"
        echo "$(./fanctl medium-high)"
        
    elif [[ $HIGH_cnt -gt $HRD_MIN_cnt && $HIGH_cnt -gt $MIN_cnt && $HIGH_cnt -gt $MED_cnt && $HIGH_cnt -gt $MAX_cnt ]]; then
#         echo "set fan to HIGH"
        echo "$(./fanctl high)"
    
    fi
        
    sleep 15
done
