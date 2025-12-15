DESCRIPTION = "Base image for Per Vices SDR platforms"
LICENSE = "MIT"
IMAGE_FEATURES += "dev-pkgs \
                   tools-sdk \
                  "
EXTRA_IMAGE_FEATURES += "package-management"
TOOLCHAIN_HOST_TASK:append = " nativesdk-python3-cheetah \
                               nativesdk-python3-netserver \
                               nativesdk-python3-pprint \
                               nativesdk-python3-pickle \
                               nativesdk-python3-shell \
                               nativesdk-orc nativesdk-swig \
                              "
inherit core-image
inherit extrausers

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
	filesystem \
	fpga \
	mcu \
	webserver \
	fsoverlay \
	cronie \
	cups \
	diffutils \
	dosfstools \
	dtc \
	dtbt \
	e2fsprogs \
	e2fsprogs-resize2fs \
	edac-utils \
	elfutils \
	ethtool \
	file \
	findutils \
	flashrom \
	fuse \
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
        libnewt \
	libpcap \
	libudev \
	libusb1 \
	libusb-compat \
	libxml2 \
	logrotate \
	lshw \
	lsof \
	make \
	memtester \
	man \
	minicom \
	mtd-utils \
	neon \
	netbase \
	net-tools \
	nmap \
	ntp \
	ntp-utils \
	nodejs \
	openssh \
	openssh-misc \
	openssh-sftp \
	openssh-sftp-server \
	openssl \
	oprofile \
	orc \
	patch \
	parted \
	pciutils \
	perl \
	picocom \
	python3-core \
	resolvconf \
	rsu \
	screen \
	sed \
	setserial \
	shadow \
	sshfs-fuse \
	strace \
	stress \
	stress-ng \
	stressapptest \
	sudo \
	sysfsutils \
	tar \
	tcl \
	tcpdump \
        tmux \
	tzdata \
	udev \
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

IMAGE_INSTALL:append:cyan = " sequencer"

##
# OPTIONAL
##
CORE_IMAGE_EXTRA_INSTALL += "sntp \
                             python3-configargparse \
                             python3-cheetah \
                             python3-pyasn1-modules \
                            "

export IMAGE_BASENAME = "sdimage"
