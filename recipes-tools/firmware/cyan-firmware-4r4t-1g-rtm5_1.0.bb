require firmware.inc

do_configure_append() {
	./configure --prefix=/usr \
                    --host=arm-unknown-linux-gnueabihf \
                    CFLAGS="-Wall -O3 -pipe -fomit-frame-pointer -Wall -march=armv8-a -mtune=cortex-a53 -Werror -lm" \
                    CPPFLAGS="-Wall -O3 -pipe -fomit-frame-pointer -Wall -march=armv8-a -mtune=cortex-a53" \
                    CXXFLAGS="-Wall -O3 -pipe -fomit-frame-pointer -Wall -march=armv8-a -mtune=cortex-a53" \
	            PRODUCT=TATE_NRNT HW_REV=RTM5 NRX=R4 NTX=T4 MAX_RATE=S1000 SPECIAL_FLAGS=F0
}
