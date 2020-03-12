DESCRIPTION = "A barebone console-only image with minimal feature support for development/debug \
of hardware peripherals. This image inherits from Altera's GSRD image with additional packages \
to support SDR features."

IMAGE_FEATURES += " \
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
	at \
	autoconf \
	avrdude \
	base-files \
	base-passwd \
	bash \
	bison \
	boost \
	busybox \
	cmake \
	coreutils \
	cyan-filesystem \
	cyan-firmware \
	cyan-fpga \
	cyan-mcu \
	cyan-website \
	cyan-website-hdr \
	cyan-default \
	cronie \
	cups \
	diffutils \
	dosfstools \
	dtc \
	dtbt \
	e2fsprogs \
	elfutils \
	ethtool \
	file \
	findutils \
	flashrom \
	fuse \
	fpga-overlay \
	g++ \
	gawk \
	gcc \
	gdb \
	gdbserver \
	git \
	glibc \
	gnupg \
	gnutls \
	grep \
	gzip \
	i2c-tools \
	init-ifupdown \
	initscripts \
	iproute2 \
	iptables \
	iputils \
	kernel-modules \
	kmod \
	liba52 \
	libftdi \
	libgcrypt \
	libpam \
	libpcap \
	libudev \
	libusb1 \
	libusb-compat \
	libxml2 \
	lshw \
	make \
	memtester \
	man \
	minicom \
	mtd-utils \
	neon \
	netbase \
	net-tools \
	nmap \
	nodejs \
	openssh \
	openssh-misc \
	openssh-sftp \
	openssh-sftp-server \
	openssl \
	oprofile \
	orc \
	patch \
	pciutils \
	perl \
	polkit \
	portmap \
	python-core \
	resolvconf \
	rsu \
	screen \
	sed \
	setserial \
	shadow \
	sshfs-fuse \
	strace \
	subversion \
	sudo \
	sysfsutils \
	systemd \
	tar \
	tcl \
	tcpdump \
	tzdata \
	udev \
	useradd \
	useradd-client \
	usbutils \
	util-linux \
	valgrind \
	vim \
	vim-vimrc \
	watchdog \
	wget \
	wireshark \
	zlib \
	"

##
# OPTIONAL
##
CORE_IMAGE_EXTRA_INSTALL += "\
	ntpdate \
	python-argparse \
	python-cheetah \
	python-modules \
	"

inherit core-image
inherit extrausers
EXTRA_USERS_PARAMS = "\
	usermod -P root root; \
	usermod -s /bin/bash root; \
	usermod -a -G dev-grp0 dev0; \
	usermod -a -G cli-grp0 client; \
	"

export IMAGE_BASENAME = "pervices-sd-image"
