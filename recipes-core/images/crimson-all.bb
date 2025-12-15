require base-image.inc
IMAGE_INSTALL += "crimson-firmware"

inherit extrausers

EXTRA_USER_PARAMS = "groupadd -g 880 crimson; useradd -u 880 -g crimson -M -s /usr/sbin/nologin crimson;"
