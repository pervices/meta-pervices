FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

inherit deploy

SRC_URI:append:stratix10_htile = " file://stratix10.patch"

do_compile[deptask] = "do_deploy"