FROM ubuntu:xenial

ARG HOME=/root

WORKDIR /

#
# Common Command-Line Utilities
#
# * Please keep this minimal
# * Dependencies for specific task should be added with the task
#
RUN apt-get update && apt-get install -y lsb-core build-essential autotools-dev autoconf libtool bison flex
RUN apt-get update && apt-get install -y git wget curl ssh
RUN apt-get update && apt-get install -y vim nano
RUN apt-get update && apt-get install -y bash
RUN rm -f /bin/sh && ln -sf /bin/bash /bin/sh

#
# Specific Dependencies
#

# for NAND images
RUN apt-get update && apt-get install -y mtd-utils
# for OpenEmbedded (python2.7)
RUN apt-get update && apt-get install -y python-all
# use supported locales (locales package installed with lsb-core)
RUN locale-gen en_US.UTF-8
RUN echo -e "export LANG=\"en_US.UTF-8\"" >> ${HOME}/.bashrc
RUN echo -e "export LC_ALL=\"en_US.UTF-8\"" >> ${HOME}/.bashrc
RUN echo -e "export LANGUAGE=\"en_US.UTF-8\"" >> ${HOME}/.bashrc
ENV LC_ALL=en_US.UTF-8
ENV LANG=en_US.UTF-8
ENV LANGUAGE=en_US.UTF-8
# additional bitbake requirements
RUN apt-get update && apt-get install -y chrpath diffstat gawk texinfo iputils-ping 

#
# Fetch Per Vices Git
#
RUN mkdir -p ${HOME}/pv
ARG FIRMWARE_TAG="master-testing"
RUN git -C ${HOME}/pv clone -b ${FIRMWARE_TAG} https://github.com/cfriedtpv/firmware.git
ARG WEBSERVER_TAG="master-testing"
RUN git -C ${HOME}/pv clone -b ${WEBSERVER_TAG} https://github.com/pervices/webserver.git

#
# Fetch Yocto (and Meta-Layers) Git
#

ARG POKY_TAG="master"
RUN git -C ${HOME} clone -b ${POKY_TAG} git://git.yoctoproject.org/poky.git
# allow bitbake to be built as root
RUN rm -f ${HOME}/poky/meta/conf/sanity.conf
RUN touch -f ${HOME}/poky/meta/conf/sanity.conf

ARG ALTERA_TAG="master"
RUN git -C ${HOME}/poky clone -b ${ALTERA_TAG} https://github.com/altera-opensource/meta-altera.git
RUN rm -Rf ${HOME}/poky/meta-altera/recipes-images

ARG ANGSTROM_TAG="master"
RUN git -C ${HOME}/poky clone -b ${ANGSTROM_TAG} https://github.com/Angstrom-distribution/meta-angstrom.git

ARG LINARO_TAG="master"
RUN git -C ${HOME}/poky clone -b ${LINARO_TAG} git://git.linaro.org/openembedded/meta-linaro.git

ARG META_PERVICES_TAG="master-testing"
RUN git -C ${HOME}/poky clone -b ${META_PERVICES_TAG} https://github.com/cfriedtpv/meta-pervices.git

ARG NODEJS_TAG="master"
RUN git -C ${HOME}/poky clone -b ${NODEJS_TAG} https://github.com/imyller/meta-nodejs.git

ARG NODEJS_CONTRIB_TAG="master"
RUN git -C ${HOME}/poky clone -b ${NODEJS_CONTRIB_TAG} https://github.com/imyller/meta-nodejs-contrib.git

ARG OE_TAG="master"
RUN git -C ${HOME}/poky clone -b ${OE_TAG} https://github.com/openembedded/meta-openembedded.git

ARG SWU_TAG="master"
RUN git -C ${HOME}/poky clone -b ${SWU_TAG} https://github.com/sbabic/meta-swupdate.git
