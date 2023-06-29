#
# ~/.bashrc
#
# Normal Colors

if [ -x /etc/crimson/motd ]; then
    /etc/crimson/./motd   # Print welcome message
elif [ -x /etc/cyan/motd ]; then
    /etc/cyan/./motd   # Print welcome message
fi

# If not running interactively, don't do anything
[[ $- != *i* ]] && return
export PS1='[\[\e[1;32m\]\u\[\e[1;0m\]@\h \[\e[1;34m\]\W\[\e[1;0m\]]\$ '

alias ls='ls --color'
alias ll='ls -al'
export LS_COLORS='di=1;34:fi=0:ln=1;36:ex=1;32:bd=1;93:cd=1;93'
export TERM='xterm'
