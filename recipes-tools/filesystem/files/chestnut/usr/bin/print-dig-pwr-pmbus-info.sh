#!/bin/bash
set -Eeuo pipefail
# ------------------------------------------------------------------
#          PMBus Reader for DIG-PWR Board
#          Reads the DIG-PWR board PMBus-enabled power regulators
# ------------------------------------------------------------------

# Text colors
RED='\e[1;31m%-6s\e[m'
GREEN='\e[1;32m%-6s\e[m'
YELLOW='\e[1;33m%-6s\e[m'
BLUE='\e[1;34m%-6s\e[m'
MAGNETA='\e[1;35m%-6s\e[m'
CYAN='\e[1;36m%-6s\e[m'

# ------------------------------------------------------------------
# Convert a Linear11 integer to a double
# Input: $1: 32bit unsigned integer
# Output: float to printf
# Usage: result=$(linear11_to_double "$input_number")
# ------------------------------------------------------------------
linear11_to_double() {
    local input
    local mantissa
    local exponent
    local signed_mantissa
    local signed_exponent
    
    input=$1
    # Lower 11 bits
    mantissa=$(( input & ((1 << 11)-1) ))
    # Upper 5 bits
    exponent=$(( input >> 11 & ((1 << 5)-1) ))

    # Conversion for 2s complement
    if ((mantissa & (1 << 10))); then
        signed_mantissa=$((mantissa | ~ ((1 << 11 )-1) ))
    else
        signed_mantissa=$mantissa
    fi

    if ((exponent & (1 << 4))); then
        signed_exponent=$((exponent | ~((1 << 5)-1) ))
    else
        signed_exponent=$exponent
    fi

    awk "BEGIN{ printf \"%.4f\n\",$signed_mantissa*2^$signed_exponent}"
}

# ------------------------------------------------------------------
# Convert a Signed Linear16 integer to a double
# Input: $1: 32bit integer
# Output: float to printf
# Usage: result=$(slinear16_to_double "$input_number")
# For LTM4680 signed Linear 16 format
# ------------------------------------------------------------------
slinear16_to_double() {
    local input
    input=$1
    awk "BEGIN{ printf \"%.4f\n\",$input*2^-12}"
}

# ------------------------------------------------------------------
# Read a 2-byte word from a I2C device address
# Input: $1: I2C bus number, $2: I2C device address, $3: I2C read address, $4: optional page number
# Output: printf
# Usage: result=$(i2c_device_read_word 1 4c 0x96 1)
# Note: inputs must be in small letters, e.g. 4c
# ------------------------------------------------------------------
i2c_device_read_word() {
    local i2c_bus_number
    local i2c_device_address
    local i2c_read_address
    local page_number
    local POWER_I2C_BUS

    if [ $# -lt 3 ]; then
        printf "$RED%s" "[ ERROR ] " 
        printf 'Incomplete arguments passed to i2c_device_read_word\n'
        exit 1
    fi

    i2c_bus_number=$1
    i2c_device_address=$2
    i2c_read_address=$3
    
    if [ $# -gt 3 ]; then
        page_number=$4
    else
        page_number=0
    fi

    # Find if I2C bus exists
    POWER_I2C_BUS=/sys/bus/i2c/devices/i2c-"$i2c_bus_number"
    if [ ! -d "$POWER_I2C_BUS" ]; then
        printf "$RED%s" "[ ERROR ] " 
        printf 'I2C bus %s does not exist\n' "$i2c_bus_number"
        return 1
    fi

    # Set page, page register is always 0x00
    ret=$(i2cset -y "$i2c_bus_number" 0x"$i2c_device_address" 0x00 0x"$page_number")
    if [[ "$ret" -eq 1 ]]; then
        printf "$RED%s" "[ ERROR ] " 
        printf 'I2Cset command failed (%s)\n' "$ret"
        exit 1
    fi

    # Read the value from 0x96
    ret=$(i2cget -y "$i2c_bus_number" 0x"$i2c_device_address" 0x"$i2c_read_address" w)
    printf "%s" "$ret"
}

i2c_check_if_device_exists() {
    local i2c_bus_number
    local i2c_device_address

    i2c_bus_number=$1
    i2c_device_address=$2

    # Optional: Find if the device exists
    # If the device does not exist, i2cget would fail anyway
    if [[ ! $(/usr/sbin/i2cdetect -y -r "$i2c_bus_number") == *"$i2c_device_address"* ]]; then
        printf "$RED%s" "[ ERROR ] " 
        printf "Device %s does not exist on I2C bus %s\n" "$i2c_device_address" "$i2c_bus_number"
        exit 1
    fi
}


# ------------------------------------------------------------------
# Print rail info
# ------------------------------------------------------------------
print_single_rail_info() {
    local i2c_bus_number
    local device_address 
    i2c_bus_number=$1
    device_address=$2

    printf "%-20s %-10s Vrms\n"  "VIN Voltage:"   "$(linear11_to_double "$(i2c_device_read_word "$i2c_bus_number" "$device_address" 88 0)")"
    printf "%-20s %-10s Irms\n"  "IIN Current:"   "$(linear11_to_double "$(i2c_device_read_word "$i2c_bus_number" "$device_address" 89 0)")"
    printf "%-20s %-10s Watts\n" "PIN Power:"     "$(linear11_to_double "$(i2c_device_read_word "$i2c_bus_number" "$device_address" 97 0)")"
    printf "%-20s %-10s degree Celsius\n" "Temperature:" "$(linear11_to_double "$(i2c_device_read_word "$i2c_bus_number" "$device_address" 8D 0)")"
    printf "%-20s %-10s Vdc\n"   "VOUT0 Voltage:" "$(slinear16_to_double "$(i2c_device_read_word "$i2c_bus_number" "$device_address" 8B 0)")"
    printf "%-20s %-10s Idc\n"   "IOUT0 Current:" "$(linear11_to_double "$(i2c_device_read_word "$i2c_bus_number" "$device_address" 8C 0)")"
    printf "%-20s %-10s Watts\n" "POUT0 Power:"   "$(linear11_to_double "$(i2c_device_read_word "$i2c_bus_number" "$device_address" 96 0)")"
    printf "%-20s %-10s Vdc\n"   "VOUT1 Voltage:" "$(slinear16_to_double "$(i2c_device_read_word "$i2c_bus_number" "$device_address" 8B 1)")"
    printf "%-20s %-10s Idc\n"   "IOUT1 Current:" "$(linear11_to_double "$(i2c_device_read_word "$i2c_bus_number" "$device_address" 8C 1)")"
    printf "%-20s %-10s Watts\n" "POUT1 Power:"   "$(linear11_to_double "$(i2c_device_read_word "$i2c_bus_number" "$device_address" 96 1)")"
}

# ------------------------------------------------------------------
# Main Function
# ------------------------------------------------------------------
main() {
    printf "Cyan RTM7 Digital Board PMBus Rail Info\n"
    printf '%*s\n' "${COLUMNS:-$(tput cols)}" '' | tr ' ' -
    printf "0.9V and 1.2V Rail: LTM4680 at 0x4E\n" 
    i2c_check_if_device_exists 1 4e
    print_single_rail_info 1 4e
    printf '%*s\n' "${COLUMNS:-$(tput cols)}" '' | tr ' ' -
    printf "1.12V Rail: LTM4680 at 0x40\n" 
    i2c_check_if_device_exists 1 40
    print_single_rail_info 1 40
    printf '%*s\n' "${COLUMNS:-$(tput cols)}" '' | tr ' ' -
    printf "1.8V Rail: LTM4680 at 0x4C\n" 
    i2c_check_if_device_exists 1 4c
    print_single_rail_info 1 4c
    printf '%*s\n' "${COLUMNS:-$(tput cols)}" '' | tr ' ' -
}

main
