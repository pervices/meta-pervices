SRC_URI = "https://download.flashrom.org/releases/flashrom-0.9.6.1.tar.bz2"

do_install_append() {
    bbwarn "AMAC SANITY CHECK"
}
