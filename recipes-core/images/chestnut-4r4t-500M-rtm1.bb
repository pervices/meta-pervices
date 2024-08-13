require base-image.inc
IMAGE_INSTALL += "firmware-${PN}"
IMAGE_INSTALL_remove += "webserver"

# Temporary, remove this line once lily hex files are available
IMAGE_INSTALL_remove += "mcu"
