#
# ~/.bashrc
#
# Normal Colors

if [ -x /etc/crimson/motd ]; then
    /etc/crimson/./motd   # Print welcome message
fi

# If not running interactively, don't do anything
[[ $- != *i* ]] && return
export PS1='[\[\e[1;32m\]\u\[\e[1;0m\]@\h \W]\$ '
