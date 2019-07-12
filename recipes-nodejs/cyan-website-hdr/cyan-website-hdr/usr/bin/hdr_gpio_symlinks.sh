#!/usr/bin/tclsh

set number_of_channels 8
set gpio_per_channel 10
set number_of_gpio_pins 80
set channels "a b c d e f g h"
set gpio_names ""
for { set i 0 } { $i < $number_of_gpio_pins } { incr i } {
    lappend gpio_names [format "/var/cyan/state/gpio/gpio%03d" $i]
}
puts $gpio_names

# Create folders for HDR signals
for { set i 0 } { $i < $number_of_channels } { incr i } { 
    set hdr_folder /var/cyan/state/gpio/hdr/[lindex $channels $i]
    exec mkdir -p $hdr_folder
    for { set j 0 } { $j < $gpio_per_channel } { incr j } {
        set gpio_number [expr {($i*$gpio_per_channel)+$j}]
        set symlink_name ""
        switch $j {
            0 {
                set symlink_name "atten1"
            }
            1 {
                set symlink_name "atten2"
            }
            2 {
                set symlink_name "atten4"
            }
            3 {
                set symlink_name "atten8"
            }
            4 {
                set symlink_name "atten16"
            }
            5 {
                set symlink_name "atten32"
            }
            6 {
                set symlink_name "atten64"
            }
            7 {
                set symlink_name "high_pwr_en"
            }
            8 {
                set symlink_name "pwr_en"
            }
            9 {
                set symlink_name "reserved"
            }
        }
        exec ln -s [lindex $gpio_names $gpio_number] $hdr_folder/$symlink_name
    }
}

