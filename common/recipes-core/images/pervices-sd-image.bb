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
	crimson-firmware \
	diffutils \
	dtc \
	e2fsprogs \ 
	elfutils \
	glibc \
	ethtool \
	file \
	findutils \
	fuse \
	g++ \
	gawk \
	gcc \
	gdb \
	gdbserver \
	git \
	gnutls \
	grep \
	gzip \
	init-ifupdown \
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
	lshw \
	make \
	memtester \
	minicom \
	mtd-utils \
	net-tools \
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
	sshfs-fuse \
	strace \
	subversion \
	swupdate \
	systemd \
	sysfsutils \
	sysvinit \
	tar \
	tcl \
	tcpdump \
	tzdata \
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
	nmap \
	orc \
	oprofile \
	avrdude \
	crimson-webserver \
	crimson-firmware \
	crimson-filesystem \
	crimson-mcu \
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
	"

inherit core-image

export IMAGE_BASENAME = "pervices-sd-image"
