#!/bin/bash
dbg_all=0
if [ $# -eq 0 ];
then
    echo "STATUSES ALL JESD RESET CONTROLLERS :"
    dbg_all=1
    jesd_dbg=0
    n_jesd=16
else
    jesd_dbg=$1
    n_jesd=$1+1
    echo "STATUS JESD RST CONTROLLER : $jesd_dbg"
    echo "dbg : $n_jesd"
fi

lossoflock=$(mem rr res_ro20)
#lossoflock='0xf3000000'
lossoflock_per_jesd=$((lossoflock >> 16))
#echo " RDB : $lossoflock_per_jesd "

normal=$(tput sgr0)
green=$(tput setaf 82)
red=$(tput setaf 202)

lossoflock_happened=0
declare -A lossoflock_table 
lossoflock_table[0]="${green}none ${normal}"
lossoflock_table[1]="${green}none ${normal}"
lossoflock_table[2]="${green}none ${normal}"
lossoflock_table[3]="${green}none ${normal}"
lossoflock_table[4]="${green}none ${normal}"
lossoflock_table[5]="${green}none ${normal}"
lossoflock_table[6]="${green}none ${normal}"
lossoflock_table[7]="${green}none ${normal}"
lossoflock_table[8]="${green}none ${normal}"
lossoflock_table[9]="${green}none ${normal}"
lossoflock_table[10]="${green}none ${normal}"
lossoflock_table[11]="${green}none ${normal}"
lossoflock_table[12]="${green}none ${normal}"
lossoflock_table[13]="${green}none ${normal}"
lossoflock_table[14]="${green}none ${normal}"
lossoflock_table[15]="${green}none ${normal}"

declare -A pll0_status 
declare -A pll1_status 
declare -A pll2_status 
declare -A pll3_status 

for ((jesd_idx=$jesd_dbg; jesd_idx<$n_jesd; jesd_idx++))
do
    jesd_idx_hex=$(echo "obase=16; $jesd_idx" | bc)
    mem rw res_rw8 $jesd_idx_hex #select JESD IDX
    stat=$(mem rr res_ro20)      # read statuses
    RstDone=$(($stat & 0x1))
    CorePllLock=$(($stat & 0x2))
    AtxPllLock=$(($stat & 0x4))
    TxRdy=$(($stat & 0x8))
    RxRdy=$(($stat & 0x10))
    current_onehot=$((1 << jesd_idx))
    LossOfLock_current=$((lossoflock_per_jesd & current_onehot))
    lossoflock_stat=
    RstDone_stat="${green}GOOD${normal}"
    CorePllLock_stat="${green}GOOD${normal}"
    AtxPllLock_stat="${green}GOOD${normal}"
    TxRdy_stat="${green}GOOD${normal}"
    RxRdy_stat="${green}GOOD${normal}"
    idx_mod_4=$((jesd_idx%4))
    idx_div_4=$((jesd_idx/4))
    if [ $stat != "0x0000001f" ];
    then
        if [ $RstDone -eq 0 ];
        then
            RstDone_stat="${red}BAD ${normal}"
        fi
        if [ $CorePllLock -eq 0 ];
        then
            CorePllLock_stat="${red}BAD ${normal}"
            if [ $idx_mod_4 -eq 0 ]; 
            then
                pll0_status[$idx_div_4]=0
            elif [ $idx_mod_4 -eq 1 ]; 
            then
                pll1_status[$idx_div_4]=0
            elif [ $idx_mod_4 -eq 2 ];
            then
                pll2_status[$idx_div_4]=0
            else
                pll3_status[$idx_div_4]=0
            fi
        else
            if [ $idx_mod_4 -eq 0 ]; 
            then
                pll0_status[$idx_div_4]=1
            elif [ $idx_mod_4 -eq 1 ]; 
            then
                pll1_status[$idx_div_4]=1
            elif [ $idx_mod_4 -eq 2 ];
            then
                pll2_status[$idx_div_4]=1
            else
                pll3_status[$idx_div_4]=1
            fi
        fi

        if [ $AtxPllLock  -eq 0 ];
        then
            AtxPllLock_stat="${red}BAD ${normal}"
        fi
        if [ $TxRdy       -eq 0 ];
        then
            TxRdy_stat="${red}BAD ${normal}"
        fi
        if [ $RxRdy       -eq 0 ];
        then
            RxRdy_stat="${red}BAD ${normal}"
        fi
        echo -e "JESD [$jesd_idx] : \t RstDone = $RstDone_stat ; CorePllLock = $CorePllLock_stat ; AtxPllLock = $AtxPllLock_stat ; TxRdy = $TxRdy_stat ; RxRdy = $RxRdy_stat"
    else       
        if [ $idx_mod_4 -eq 0 ]; 
        then
            pll0_status[$idx_div_4]=1
        elif [ $idx_mod_4 -eq 1 ]; 
        then
            pll1_status[$idx_div_4]=1
        elif [ $idx_mod_4 -eq 2 ];
        then
            pll2_status[$idx_div_4]=1
        else
            pll3_status[$idx_div_4]=1
        fi
        echo -e "JESD [$jesd_idx] : \t ${green}GOOD${normal}"
    fi
    if [ $LossOfLock_current -ne 0 ];
    then
        lossoflock_table[$jesd_idx]="${red}EVENT${normal}"
        lossoflock_happened=1
        lossoflock_stat="${red}EVENT${normal}"
    fi
    #echo "Loss of Lock event : $lossoflock_stat"
done


#echo " PLL 0 0 = ${pll0_status[0]}"
#echo " PLL 0 1 = ${pll0_status[1]}"
#echo " PLL 0 2 = ${pll0_status[2]}"
#echo " PLL 0 3 = ${pll0_status[3]}"
#echo " PLL 0 stat : ${pll0_status[0]} PLL 0 stat : ${pll0_status[1]} PLL 0 stat : ${pll0_status[2]} PLL 0 stat : ${pll0_status[3]}"
#echo " PLL 1 stat : ${pll1_status[0]} PLL 1 stat : ${pll1_status[1]} PLL 1 stat : ${pll1_status[2]} PLL 1 stat : ${pll1_status[3]}"
#echo " PLL 2 stat : ${pll2_status[0]} PLL 2 stat : ${pll2_status[1]} PLL 2 stat : ${pll2_status[2]} PLL 2 stat : ${pll2_status[3]}"
#echo " PLL 3 stat : ${pll3_status[0]} PLL 3 stat : ${pll3_status[1]} PLL 3 stat : ${pll3_status[2]} PLL 3 stat : ${pll3_status[3]}"


declare -A pll_status_ok
declare -A pll_status
for i in {0..3}
do
    pll_status_ok[$i]=1
    if   [ $i -eq 0 ]; 
    then
        current_pll=(${pll0_status[@]})
    elif [ $i -eq 1 ]; 
    then
        current_pll=(${pll1_status[@]})
    elif [ $i -eq 2 ]; 
    then
        current_pll=(${pll2_status[@]})
    else
        current_pll=(${pll3_status[@]})
    fi
    #Check if all the statuses of the same PLL are coherent:
    for j in {0..3}
    do
        if [ $j -eq 0 ];
        then
            #echo "RDB current_pll = ${current_pll[0]}"
            if [ ${current_pll[$j]} -eq 1 ];
            then
                pll_status[$i]="YES"
            else
                pll_status[$i]="NO "
            fi
            stat=${current_pll[$j]}
        fi
        if [ $stat -ne ${current_pll[$j]} ]; #check if PLL locked of different IP that share the same PLL are the same, if not write 0
        then
            pll_status_ok[$i]=0
        fi
    done
done
#${current_pll[0]}
#echo "RDB current_pll = ${current_pll[$j]}"
#echo "RDB pll status ok 0 = ${pll_status_ok[0]}"
if [ ${pll_status_ok[0]} -eq 0 ] || [ ${pll_status_ok[1]} -eq 0 ] || [ ${pll_status_ok[2]} -eq 0 ] || [ ${pll_status_ok[3]} -eq 0 ];
then
    echo "ERROR: PLL Locked are different for the same PLL??, something might be wrong in this script"
fi

echo "Loss of locks: "
echo " PLL 0 locked : ${pll_status[0]}          PLL 1 locked : ${pll_status[1]}        PLL 2 locked : ${pll_status[2]}          PLL 3 locked : ${pll_status[3]}  "
echo " RFE_0 (Jesd  0) ${lossoflock_table[0]}       RFE_4 (Jesd  1) ${lossoflock_table[4]}     RFE_8  (Jesd  2) ${lossoflock_table[8]}      RFE_12 (Jesd  3) ${lossoflock_table[12]}"
echo " RFE_1 (Jesd  4) ${lossoflock_table[1]}       RFE_5 (Jesd  5) ${lossoflock_table[5]}     RFE_9  (Jesd  6) ${lossoflock_table[9]}      RFE_13 (Jesd  7) ${lossoflock_table[13]}"
echo " RFE_2 (Jesd  8) ${lossoflock_table[2]}       RFE_6 (Jesd  9) ${lossoflock_table[6]}     RFE_10 (Jesd 10) ${lossoflock_table[10]}      RFE_14 (Jesd 11) ${lossoflock_table[14]}"
echo " RFE_3 (Jesd 12) ${lossoflock_table[3]}       RFE_7 (Jesd 13) ${lossoflock_table[7]}     RFE_11 (Jesd 14) ${lossoflock_table[11]}      RFE_15 (Jesd 15) ${lossoflock_table[15]}"

if [ $lossoflock_happened -eq 1 ];
then
    echo "Do you want to reset ALL the Loss of lock event sticky statuses? y or n"
    read answer
    if [ $answer == 'y' ];
    then
        mem rw res_rw8 0x10000
        mem rw res_rw8 0x0
    fi
    exit
fi



######select JESD_0 
#####mem rw res_rw8 0x0;
#####
##### read status
#####mem rr res_ro20
#####
#####
