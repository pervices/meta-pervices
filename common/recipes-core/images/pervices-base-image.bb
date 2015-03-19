DESCRIPTION = "A barebone console-only image with minimal feature support for development/debug \
of hardware peripherals. This image inherits from Altera's GSRD image with additional packages \
to support SDR features."

IMAGE_FEATURES += " \
        debug-tweaks \
        dev-pkgs \
        dbg-pkgs \
	tools-sdk \
        "

EXTRA_IMAGE_FEATURES += "package-management"

LICENSE = "MIT"

TOOLCHAIN_HOST_TASK_append = " nativesdk-python-cheetah \
    nativesdk-python-netserver nativesdk-python-pprint \
    nativesdk-python-pickle nativesdk-python-shell \
    nativesdk-orc nativesdk-swig"

##
# REQUIRED
##
IMAGE_INSTALL += "\
	autoconf \
        base-files \
        base-passwd \
        bash \
        bison \
        boost \
        busybox \
        cmake \
	coreutils \
        diffutils \
        dtc \
        e2fsprogs \ 
        elfutils \
	eglibc \
        ethtool \
        file \
        findutils \
	g++ \
        gator \
        gawk \
        gcc \
        gdb \
        gdbserver \
        git \
	gnutls \
        grep \
        gzip \
        initscripts \
	iproute2 \
	iptables \
        iputils \
        kernel-modules \
        libpcap \
        libudev \
        libusb1 \
        libusb-compat \
        libxml2 \
        lighttpd \
        lighttpd-module-cgi \
        make \
        minicom \
        mtd-utils \
	net-tools \
	nfs-utils-client \
	openssh \
        openssh-sftp \
        openssh-sftp-server \
        openssl \
	patch \
        pciutils \
        perl \
        portmap \
        python-core \
        screen \
        sed \
        setserial \
        strace \
	subversion \
	systemd \
        sysfsutils \
        sysvinit \
        tar \
	tcl \
	tcpdump \
	udev \
        usbutils \
        util-linux \
	valgrind \
        vim \
        vim-vimrc \
        wget \
        zlib \
        flashrom \
	gnupg \
        i2c-tools \
        iperf \
	liba52 \
	libgcrypt \
	netbase \
	neon \
	nodejs \
        orc \
	oprofile \
	avrdude \
    	"

##
# OPTIONAL
##
CORE_IMAGE_EXTRA_INSTALL += "\
        glibc \
        ntpdate \
        python-argparse \
	python-cheetah \
        python-modules \
        sshfs-fuse \
	"

inherit core-image

export IMAGE_BASENAME = "pervices-base-image"
