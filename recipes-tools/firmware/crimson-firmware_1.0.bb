require firmware.inc
INSANE_SKIP:${PN} += " buildpaths debug-files ldflags "
do_configure:append() {
        ./configure --prefix=/usr \
                    --host=arm-unknown-linux-gnueabihf \
                    CFLAGS="-Wall -O3 -pipe -fomit-frame-pointer -Wall -march=armv7-a -mtune=cortex-a9 -mfpu=neon -lm -pthread -Wno-error=format-security" \
                    CPPFLAGS="-Wall -O3 -pipe -fomit-frame-pointer -Wall -march=armv7-a -mtune=cortex-a9 -mfpu=neon" \
                    CXXFLAGS="-Wall -O3 -pipe -fomit-frame-pointer -Wall -march=armv7-a -mtune=cortex-a9 -mfpu=neon" \
                    PRODUCT=VAUNT HW_REV=RTM9 NRX=R4 NTX=T4 MAX_RATE=SNA RX_40GHZ_FE=0 USE_3G_AS_1G=0 USER_LO=0
}
