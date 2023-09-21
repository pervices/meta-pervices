require firmware.inc

do_configure_append() {
        ./configure --prefix=/usr \
                    --host=arm-unknown-linux-gnueabihf \
                    CFLAGS="-Wall -O3 -pipe -fomit-frame-pointer -Wall -march=armv7-a -mtune=cortex-a9 -mfpu=neon -Werror -lm" \
                    CPPFLAGS="-Wall -O3 -pipe -fomit-frame-pointer -Wall -march=armv7-a -mtune=cortex-a9 -mfpu=neon" \
                    CXXFLAGS="-Wall -O3 -pipe -fomit-frame-pointer -Wall -march=armv7-a -mtune=cortex-a9 -mfpu=neon" \
                    PRODUCT=VAUNT HW_REV=RTM6 NRX=R4 NTX=T4 MAX_RATE=S162M5 SPECIAL_FLAGS=F0
}

