DESCRIPTION = "Base image for Per Vices SDR platforms"
LICENSE = "MIT"
IMAGE_FEATURES += "dev-pkgs \
                   tools-sdk \
                  "
EXTRA_IMAGE_FEATURES += "package-management"
TOOLCHAIN_HOST_TASK_append = " nativesdk-python-cheetah \
                               nativesdk-python-netserver \
                               nativesdk-python-pprint \
                               nativesdk-python-pickle \
                               nativesdk-python-shell \
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
	iperf \
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
	stress \
	stress-ng \
	stressapptest \
	sudo \
	sysfsutils \
	systemd \
	tar \
	tcl \
	tcpdump \
        tmux \
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
CORE_IMAGE_EXTRA_INSTALL += "ntpdate \
                             python-argparse \
                             python-cheetah \
                             python-modules \
                            "

EXTRA_USERS_PARAMS = "usermod -P root root; \
                      usermod -s /bin/bash root; \
                      usermod -a -G dev-grp0 dev0; \
                      usermod -a -G cli-grp0 client; \
                     "

export IMAGE_BASENAME = "sdimage"
