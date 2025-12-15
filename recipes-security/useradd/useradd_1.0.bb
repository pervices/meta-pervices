DESCRIPTION = "Recipe for adding users"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "CLOSED" 

inherit extrausers
EXTRA_USER_PARAMS = "groupadd -g 880 crimson; useradd -u 880 -g crimson -M -s /usr/sbin/nologin crimson; "
